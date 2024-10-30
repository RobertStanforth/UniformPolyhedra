package xyz.stanforth.polyhedra.plotting;

import xyz.stanforth.util.Pair;
import xyz.stanforth.util.Ref;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.*;
import java.util.function.Supplier;

/**
 * Generates PNG images of polyhedra.
 */
public final class GenerateImage
{
  public static void main(final String[] args) throws IOException
  {
    generate("Uniform\\r{52%5}.ph", 0, 1, false, "r{52%5}.png");
  }

  /**
   * Generates a PNG image of a polyhedron.
   * @param inputFileName Filename to read the polyhedron's .ph file.
   * @param combination Combination within the .ph file to render.
   * @param palette Palette to use for rendering.
   * @param xorCompound Whether to use "alternating fill" for star polygons.
   * @param outputFileName Filename to write the rendered polyhedron.
   */
  public static void generate(
          final String inputFileName,
          final int combination,
          final int palette,
          final boolean xorCompound,
          final String outputFileName) throws IOException
  {
    System.out.println("Processing " + outputFileName + " ...");
    final PHPolyhedron polyhedron = loadPolyhedron("C:\\Users\\Robert\\Projects\\Cpp\\Polyhedra\\" + inputFileName);

    final List<? extends PHShape> worldShapes = WorldBuilding.generateWorld(polyhedron, 1<<combination, palette);

    final Transform eye = Viewing.inverseEye(Math.PI - 1.1, Math.PI + 0.2, 10.);
    final List<? extends PHShape> projectedShapes = Viewing.view(
            worldShapes,
            eye,
            0x999999,
            0x666666,
            Vector4.of(0., 0., -1., 0.),
            0.7);

    final Pair<? extends Canvas, ? extends Supplier<? extends RenderedImage>> canvasWithImageSupplier
            = PngFormat.supersampledPngCanvas(1024, 4);
    final Canvas canvas = canvasWithImageSupplier.first();
    final Supplier<? extends RenderedImage> imageSupplier = canvasWithImageSupplier.second();

    Plotting.plot(canvas, projectedShapes, xorCompound, 0.102);
    final RenderedImage image = imageSupplier.get();

    saveImage("C:\\Users\\Robert\\Projects\\Polyhedra\\UniformPolyhedra\\Images\\" + outputFileName, image);
  }

  /**
   * Loads a polyhedron from file.
   * @param fileName File name from which to load.
   * @return Loaded polyhedron.
   */
  public static PHPolyhedron loadPolyhedron(final String fileName) throws IOException
  {
    final Ref<PHPolyhedron> polyhedron = new Ref<>();
    try (Reader reader = new BufferedReader(new FileReader(fileName)))
      {
        final Ref<Integer> ch = new Ref<>();
        ch.set(reader.read());
        PHPolyhedron.read(polyhedron, reader, ch);
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
