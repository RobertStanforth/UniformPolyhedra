package xyz.stanforth.polyhedra.rendering;

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
    final String projectPath = "C:\\Users\\Robert\\Projects\\Polyhedra\\UniformPolyhedra\\";
    final String inputPath = projectPath + "Assets\\";
    final String outputPath = projectPath + "Images\\";
    generateUniform(inputPath + "Uniform\\", outputPath + "simple\\");
    generateCompounds(inputPath + "Compound\\", outputPath + "compound\\");
  }

  /**
   * Generates images of the uniform polyhedra.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void generateUniform(final String inputPath, final String outputPath) throws IOException
  {
    generate(inputPath + "{3,3}.ph", 0, 0, false, outputPath + "{3,3}.png");
    generate(inputPath + "{4,3}.ph", 0, 0, false, outputPath + "{4,3}.png");
    generate(inputPath + "{3,4}.ph", 0, 0, false, outputPath + "{3,4}.png");
    generate(inputPath + "{5,3}.ph", 0, 0, false, outputPath + "{5,3}.png");
    generate(inputPath + "{52,3}.ph", 0, 0, false, outputPath + "{52,3}.png");
    generate(inputPath + "{3,5}.ph", 0, 0, false, outputPath + "{3,5}.png");
    generate(inputPath + "{3,52}.ph", 0, 0, false, outputPath + "{3,52}.png");
    generate(inputPath + "{52,5}.ph", 0, 0, false, outputPath + "{52,5}.png");
    generate(inputPath + "{5,52}.ph", 0, 0, false, outputPath + "{5,52}.png");

    generate(inputPath + "{3%3}.ph", 0, 0, false, outputPath + "{3h2}.png");
    generate(inputPath + "{3%4}.ph", 0, 0, false, outputPath + "{3%4}.png");
    generate(inputPath + "{3%4}.ph", 1, 0, false, outputPath + "{3h3}.png");
    generate(inputPath + "{3%4}.ph", 2, 0, false, outputPath + "{4h3}.png");
    generate(inputPath + "{3%5}.ph", 0, 0, false, outputPath + "{3%5}.png");
    generate(inputPath + "{3%5}.ph", 1, 0, false, outputPath + "{3h5}.png");
    generate(inputPath + "{3%5}.ph", 2, 0, false, outputPath + "{5h5}.png");
    generate(inputPath + "{3%52}.ph", 0, 0, false, outputPath + "{3%52}.png");
    generate(inputPath + "{3%52}.ph", 1, 0, false, outputPath + "{3h52}.png");
    generate(inputPath + "{3%52}.ph", 2, 0, false, outputPath + "{52h52}.png");
    generate(inputPath + "{52%5}.ph", 0, 0, false, outputPath + "{52%5}.png");
    generate(inputPath + "{52%5}.ph", 1, 0, false, outputPath + "{52h3}.png");
    generate(inputPath + "{52%5}.ph", 2, 0, false, outputPath + "{5h3}.png");
    generate(inputPath + "{3%52,3}.ph", 0, 0, false, outputPath + "{3%52,3}.png");
    generate(inputPath + "{3%52,3}.ph", 1, 0, false, outputPath + "{3%5,3}.png");
    generate(inputPath + "{3%52,3}.ph", 2, 0, false, outputPath + "{52%5,3}.png");

    generate(inputPath + "t{3,3}.ph", 0, 0, false, outputPath + "t{3,3}.png");
    generate(inputPath + "t{4,3}.ph", 0, 0, false, outputPath + "t{4,3}.png");
    generate(inputPath + "t`{4,3}.ph", 0, 0, false, outputPath + "t`{4,3}.png");
    generate(inputPath + "t{3,4}.ph", 0, 0, false, outputPath + "t{3,4}.png");
    generate(inputPath + "t{5,3}.ph", 0, 0, false, outputPath + "t{5,3}.png");
    generate(inputPath + "t`{52,3}.ph", 0, 0, false, outputPath + "t`{52,3}.png");
    generate(inputPath + "t{3,5}.ph", 0, 0, false, outputPath + "t{3,5}.png");
    generate(inputPath + "t{3,52}.ph", 0, 0, false, outputPath + "t{3,52}.png");
    generate(inputPath + "t{5,52}.ph", 0, 0, false, outputPath + "t{5,52}.png");
    generate(inputPath + "t`{52,5}.ph", 0, 0, false, outputPath + "t`{52,5}.png");
    generate(inputPath + "r{3%4}.ph", 0, 0, false, outputPath + "r{3%4}.png");
    generate(inputPath + "r{3%4}.ph", 1, 0, false, outputPath + "h`{3%4,4}.png");
    generate(inputPath + "r{3%4}.ph", 2, 0, false, outputPath + "r{3%4}_h`{3%4,4}.png");
    generate(inputPath + "r`{3%4}.ph", 0, 0, false, outputPath + "r`{3%4}.png");
    generate(inputPath + "r`{3%4}.ph", 1, 0, false, outputPath + "h{3%4,4}.png");
    generate(inputPath + "r`{3%4}.ph", 2, 0, false, outputPath + "r`{3%4}_h{3%4,4}.png");
    generate(inputPath + "r{3%5}.ph", 0, 0, false, outputPath + "r{3%5}.png");
    generate(inputPath + "r{3%5}.ph", 1, 0, false, outputPath + "h`{3%5,5}.png");
    generate(inputPath + "r{3%5}.ph", 2, 0, false, outputPath + "r{3%5}_h`{3%5,5}.png");
    generate(inputPath + "r`{3%52}.ph", 0, 0, false, outputPath + "r`{3%52}.png");
    generate(inputPath + "r`{3%52}.ph", 1, 0, false, outputPath + "h{3%52,52}.png");
    generate(inputPath + "r`{3%52}.ph", 2, 0, false, outputPath + "r`{3%52}_h{3%52,52}.png");
    generate(inputPath + "r{52%5}.ph", 0, 0, false, outputPath + "r{52%5}.png");
    generate(inputPath + "r{52%5}.ph", 1, 0, false, outputPath + "h`{52%5,3}.png");
    generate(inputPath + "r{52%5}.ph", 2, 0, false, outputPath + "r{52%5}_h`{52%5,3}.png");
    generate(inputPath + "h{3%52,3}.ph", 0, 0, false, outputPath + "h{3%52,3}.png");
    generate(inputPath + "h{3%52,3}.ph", 1, 0, false, outputPath + "h`{3%52,5}.png");
    generate(inputPath + "h{3%52,3}.ph", 2, 0, false, outputPath + "h{3%52,3}_h`{3%52,5}.png");
    generate(inputPath + "h`{3%5,3}.ph", 0, 0, false, outputPath + "h`{3%5,3}.png");
    generate(inputPath + "h`{3%5,3}.ph", 1, 0, false, outputPath + "h{3%5,52}.png");
    generate(inputPath + "h`{3%5,3}.ph", 2, 0, false, outputPath + "h`{3%5,3}_h{3%5,52}.png");

    generate(inputPath + "t{3%4}.ph", 0, 0, false, outputPath + "t{3%4}.png");
    generate(inputPath + "t`{3%4}.ph", 0, 0, false, outputPath + "t`{3%4}.png");
    generate(inputPath + "t{3%5}.ph", 0, 0, false, outputPath + "t{3%5}.png");
    generate(inputPath + "t`{3%52}.ph", 0, 0, false, outputPath + "t`{3%52}.png");
    generate(inputPath + "t`{52%5}.ph", 0, 0, false, outputPath + "t`{52%5}.png");
    generate(inputPath + "t`{3%4%4}.ph", 0, 0, false, outputPath + "t`{3%4%4}.png");
    generate(inputPath + "t`{3%5%52}.ph", 0, 0, false, outputPath + "t`{3%5%52}.png");

    generate(inputPath + "s{3%4}.ph", 0, 0, false, outputPath + "s{3%4}.png");
    generate(inputPath + "s{3%5}.ph", 0, 0, false, outputPath + "s{3%5}.png");
    generate(inputPath + "s{3%52}.ph", 0, 0, false, outputPath + "s{3%52}.png");
    generate(inputPath + "s`{3%52}.ph", 0, 0, false, outputPath + "s`{3%52}.png");
    generate(inputPath + "s``{3%52}.ph", 0, 0, false, outputPath + "s``{3%52}.png");
    generate(inputPath + "s{52%5}.ph", 0, 0, false, outputPath + "s{52%5}.png");
    generate(inputPath + "s`{52%5}.ph", 0, 0, false, outputPath + "s`{52%5}.png");
    generate(inputPath + "s{3%3%52}.ph", 0, 0, false, outputPath + "s{3%3%52}.png");
    generate(inputPath + "s``{3%3%52}.ph", 0, 0, false, outputPath + "s``{3%3%52}.png");
    generate(inputPath + "s`{3%52%5}.ph", 0, 0, false, outputPath + "s`{3%52%5}.png");
    generate(inputPath + "s`{3%52%52}.ph", 0, 0, false, outputPath + "s`{3%52%52}.png");
    generate(inputPath + "s`{3%52%52}.ph", 1, 0, false, outputPath + "dr{3%52}.png");
    generate(inputPath + "s`{3%52%52}.ph", 2, 0, false, outputPath + "sdr{3%52}.png");
  }

  /**
   * Generates images of the uniform compounds of uniform polyhedra.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void generateCompounds(final String inputPath, final String outputPath) throws IOException
  {
    generate(inputPath + "2{3,3}.ph", 0, 0, true, outputPath + "2{3,3}.png");
    generate(inputPath + "2t{3,3}.ph", 0, 0, true, outputPath + "2t{3,3}.png");
    generate(inputPath + "2{3,5}.ph", 0, 0, true, outputPath + "2{3,5}.png");
    generate(inputPath + "2{3,52}.ph", 0, 0, true, outputPath + "2{3,52}.png");
    generate(inputPath + "2{52,5}.ph", 0, 0, true, outputPath + "2{52,5}.png");
    generate(inputPath + "2{5,52}.ph", 0, 0, true, outputPath + "2{5,52}.png");
    generate(inputPath + "2s{3%4}.ph", 0, 0, true, outputPath + "2s{3%4}.png");
    generate(inputPath + "2s{3%5}.ph", 0, 0, true, outputPath + "2s{3%5}.png");
    generate(inputPath + "2s{3%52}.ph", 0, 0, true, outputPath + "2s{3%52}.png");
    generate(inputPath + "2s`{3%52}.ph", 0, 0, true, outputPath + "2s`{3%52}.png");
    generate(inputPath + "2s``{3%52}.ph", 0, 0, true, outputPath + "2s``{3%52}.png");
    generate(inputPath + "2s{52%5}.ph", 0, 0, true, outputPath + "2s{52%5}.png");
    generate(inputPath + "2s`{52%5}.ph", 0, 0, true, outputPath + "2s`{52%5}.png");
    generate(inputPath + "2s`{3%52%5}.ph", 0, 0, true, outputPath + "2s`{3%52%5}.png");

    generate(inputPath + "5{3,3}.ph", 0, 1, true, outputPath + "5{3,3}.png");
    generate(inputPath + "10{3,3}.ph", 0, 1, true, outputPath + "10{3,3}.png");
    generate(inputPath + "5{4,3}.ph", 0, 1, true, outputPath + "5{4,3}.png");
    generate(inputPath + "5{3,4}.ph", 0, 1, true, outputPath + "5{3,4}.png");
    generate(inputPath + "5{3%3}.ph", 0, 1, true, outputPath + "5{3h2}.png");
    generate(inputPath + "5{3,5}.ph", 0, 1, true, outputPath + "5{3,5}.png");
    generate(inputPath + "5{3,52}.ph", 0, 1, true, outputPath + "5{3,52}.png");
    generate(inputPath + "5{52,5}.ph", 0, 1, true, outputPath + "5{52,5}.png");
    generate(inputPath + "5{5,52}.ph", 0, 1, true, outputPath + "5{5,52}.png");
    generate(inputPath + "5{3%4}.ph", 0, 1, true, outputPath + "5{3%4}.png");
    generate(inputPath + "5{3%4}.ph", 1, 1, true, outputPath + "5{3h3}.png");
    generate(inputPath + "5{3%4}.ph", 2, 1, true, outputPath + "5{4h3}.png");
    generate(inputPath + "5t{3,3}.ph", 0, 1, true, outputPath + "5t{3,3}.png");
    generate(inputPath + "10t{3,3}.ph", 0, 1, true, outputPath + "10t{3,3}.png");
    generate(inputPath + "5t{4,3}.ph", 0, 1, true, outputPath + "5t{4,3}.png");
    generate(inputPath + "5t`{4,3}.ph", 0, 1, true, outputPath + "5t`{4,3}.png");
    generate(inputPath + "5r{3%4}.ph", 0, 1, true, outputPath + "5r{3%4}.png");
    generate(inputPath + "5r{3%4}.ph", 1, 1, true, outputPath + "5h`{3%4,4}.png");
    generate(inputPath + "5r{3%4}.ph", 2, 1, true, outputPath + "5r{3%4}_h`{3%4,4}.png");
    generate(inputPath + "5r`{3%4}.ph", 0, 1, true, outputPath + "5r`{3%4}.png");
    generate(inputPath + "5r`{3%4}.ph", 1, 1, true, outputPath + "5h{3%4,4}.png");
    generate(inputPath + "5r`{3%4}.ph", 2, 1, true, outputPath + "5r`{3%4}_h{3%4,4}.png");

    generate(inputPath + "3{4,3}.ph", 0, 2, true, outputPath + "3{4,3}.png");
    generate(inputPath + "6{3,3}.ph", 0, 2, true, outputPath + "6{3,3}.png");
    generate(inputPath + "3a{4}.ph", 0, 2, true, outputPath + "3a{4}.png");
    generate(inputPath + "6a{4}.ph", 0, 2, true, outputPath + "6a{4}.png");
    generate(inputPath + "4p{3}.ph", 0, 1, true, outputPath + "4p{3}.png");
    generate(inputPath + "8p{3}.ph", 0, 1, true, outputPath + "8p{3}.png");
    generate(inputPath + "4p{6}.ph", 0, 1, true, outputPath + "4p{6}.png");
    generate(inputPath + "4{3,4}.ph", 0, 1, true, outputPath + "4{3,4}.png");
    generate(inputPath + "6p{5}.ph", 0, 2, true, outputPath + "6p{5}.png");
    generate(inputPath + "12p{5}.ph", 0, 2, true, outputPath + "12p{5}.png");
    generate(inputPath + "6p{52}.ph", 0, 2, true, outputPath + "6p{52}.png");
    generate(inputPath + "12p{52}.ph", 0, 2, true, outputPath + "12p{52}.png");
    generate(inputPath + "6a{52}.ph", 0, 2, true, outputPath + "6a{52}.png");
    generate(inputPath + "12a{52}.ph", 0, 2, true, outputPath + "12a{52}.png");
    generate(inputPath + "6p{X}.ph", 0, 2, true, outputPath + "6p{X}.png");
    generate(inputPath + "6p{X3}.ph", 0, 2, true, outputPath + "6p{X3}.png");
    generate(inputPath + "6a{5}.ph", 0, 2, true, outputPath + "6a{5}.png");
    generate(inputPath + "6a`{52}.ph", 0, 2, true, outputPath + "6a`{52}.png");
    generate(inputPath + "10p{3}.ph", 0, 0, true, outputPath + "10p{3}.png");
    generate(inputPath + "20p{3}.ph", 0, 0, true, outputPath + "20p{3}.png");
    generate(inputPath + "10p{6}.ph", 0, 0, true, outputPath + "10p{6}.png");
    generate(inputPath + "10{3,4}a.ph", 0, 0, true, outputPath + "10{3,4}a.png");
    generate(inputPath + "10{3,4}b.ph", 0, 0, true, outputPath + "10{3,4}b.png");
    generate(inputPath + "20{3,4}.ph", 0, 0, true, outputPath + "20{3,4}.png");
    generate(inputPath + "20{3%3}.ph", 0, 0, true, outputPath + "20{3h2}.png");
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
    final PHPolyhedron polyhedron = loadPolyhedron(inputFileName);

    final List<? extends PHShape> worldShapes = WorldBuilding.generateWorld(polyhedron, 1<<combination, palette);

    final Transform eye = Viewing.inverseEye(Math.PI - 1.1, Math.PI + 0.12, 10.);
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

    saveImage(outputFileName, image);
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
