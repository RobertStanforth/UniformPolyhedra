package xyz.stanforth.polyhedra.images;

import xyz.stanforth.polyhedra.rendering.*;
import xyz.stanforth.util.Pair;
import xyz.stanforth.util.Ref;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.*;
import java.util.function.DoubleFunction;
import java.util.function.Supplier;

/**
 * Generates PNG images of polyhedra.
 */
public final class GenerateImage
{
  public static void main(final String[] args) throws IOException
  {
    final String projectPath = "C:\\Users\\Robert\\Projects\\Polyhedra\\UniformPolyhedra\\";
    SiteImages.enumerate(imageGenerator(), projectPath + "Assets\\", projectPath + "Images\\");
//    imageGenerator().generate(projectPath + "Assets\\Uniform\\r{52x5}.ph", 0, Palette.ALT_A, false, projectPath + "Images\\r{52x5}.png");
  }

  /**
   * Constructs an end-to-end image generator that generates PNG, and animated GIFs for rotating cases.
   * @return End-to-end image generator.
   */
  public static ImageGenerator imageGenerator()
  {
    final double distance = 10.;
    final Transform eye = Viewing.inverseEye(Math.PI - 1.1, Math.PI + 0.12, distance);
    final double zoom = distance / 1.02;

    return new ImageGenerator()
    {
      @Override
      public void prepareDirectory(final String outputPath)
      {
        new File(outputPath).mkdir();
      }

      @Override
      public void generate(
              final String inputFileName,
              final int combination,
              final Palette palette,
              final boolean xorCompound,
              final String outputFileName) throws IOException
      {
        System.out.println("Processing " + outputFileName + " ...");
        final Polyhedron polyhedron = loadPolyhedron(inputFileName);

        final List<? extends Shape> worldShapes = WorldBuilding.generateWorld(polyhedron, 1<<combination, palette);

        final List<? extends Shape> projectedShapes = Viewing.view(
                worldShapes,
                eye,
                zoom,
                0x999999,
                0x666666,
                Vector4.of(0., 0., -1., 0.),
                0.7);

        final Pair<? extends Canvas, ? extends Supplier<? extends RenderedImage>> canvasWithImageSupplier
                = PngFormat.supersampledPngCanvas(1024, 4);
        final Canvas canvas = canvasWithImageSupplier.first();
        final Supplier<? extends RenderedImage> imageSupplier = canvasWithImageSupplier.second();

        Plotting.plot(canvas, projectedShapes, xorCompound);
        final RenderedImage image = imageSupplier.get();

        saveImage(outputFileName, image);
      }

      @Override
      public void generateRotating(
              final String inputFileName,
              final double maxAngle,
              final int combination,
              final Palette palette,
              final boolean xorCompound,
              final String outputFileName) throws IOException
      {
        final int numSteps = 40;
        final List<RenderedImage> renderedImages = new ArrayList<>();
        for (int step = 0; step < numSteps; step += 1)
          {
            System.out.println(String.format("Processing %s ... (%d/%d)", outputFileName, step, numSteps));
            final Polyhedron polyhedron = loadRotatingPolyhedron(inputFileName).apply((.25+step)*maxAngle/numSteps);

            final List<? extends Shape> worldShapes = WorldBuilding.generateWorld(polyhedron, 1<<combination, palette);

            final List<? extends Shape> projectedShapes = Viewing.view(
                    worldShapes,
                    eye,
                    zoom,
                    0x999999,
                    0x666666,
                    Vector4.of(0., 0., -1., 0.),
                    0.7);

            final Pair<? extends Canvas, ? extends Supplier<? extends RenderedImage>> canvasWithImageSupplier
                    = PngFormat.supersampledPngCanvas(1024, 1);
            final Canvas canvas = canvasWithImageSupplier.first();
            final Supplier<? extends RenderedImage> imageSupplier = canvasWithImageSupplier.second();

            Plotting.plot(canvas, projectedShapes, xorCompound);
            renderedImages.add(imageSupplier.get());
          }

        System.out.println("Processing " + outputFileName + " ...");
        try (ImageOutputStream imageOutputStream = new FileImageOutputStream(new File(outputFileName)))
        {
          AnimatedGifFormat.write(imageOutputStream, renderedImages, 50);
        }
      }
    };
  }

  /**
   * Loads a polyhedron from file.
   * @param fileName File name from which to load.
   * @return Loaded polyhedron.
   */
  public static Polyhedron loadPolyhedron(final String fileName) throws IOException
  {
    final Ref<Polyhedron> polyhedron = new Ref<>();
    try (Reader reader = new BufferedReader(new FileReader(fileName)))
      {
        final Ref<Integer> ch = new Ref<>();
        ch.set(reader.read());
        Polyhedron.read(polyhedron, reader, ch);
      }
    return polyhedron.value();
  }

  /**
   * Loads a rotating polyhedron from file.
   * @param fileName File name from which to load.
   * @return Loaded polyhedron.
   */
  public static DoubleFunction<? extends Polyhedron> loadRotatingPolyhedron(final String fileName) throws IOException
  {
    final Ref<DoubleFunction<? extends Polyhedron>> polyhedron = new Ref<>();
    try (Reader reader = new BufferedReader(new FileReader(fileName)))
      {
        final Ref<Integer> ch = new Ref<>();
        ch.set(reader.read());
        Polyhedron.readRotating(polyhedron, reader, ch);
      }
    return polyhedron.value();
  }

  /**
   * Saves an image to file.
   * @param fileName File name to which to save.
   * @param image Image to save.
   */
  public static void saveImage(final String fileName, final RenderedImage image) throws IOException
  {
    try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileName)))
      {
        ImageIO.write(image, "png", outputStream);
      }
  }

  private GenerateImage() {}
}
