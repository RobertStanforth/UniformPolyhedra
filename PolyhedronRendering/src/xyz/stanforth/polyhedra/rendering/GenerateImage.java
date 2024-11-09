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
    generate(inputPath + "{3_3}.ph", 0, 0, false, outputPath + "{3_3}.png");
    generate(inputPath + "{4_3}.ph", 0, 0, false, outputPath + "{4_3}.png");
    generate(inputPath + "{3_4}.ph", 0, 0, false, outputPath + "{3_4}.png");
    generate(inputPath + "{5_3}.ph", 0, 0, false, outputPath + "{5_3}.png");
    generate(inputPath + "{52_3}.ph", 0, 0, false, outputPath + "{52_3}.png");
    generate(inputPath + "{3_5}.ph", 0, 0, false, outputPath + "{3_5}.png");
    generate(inputPath + "{3_52}.ph", 0, 0, false, outputPath + "{3_52}.png");
    generate(inputPath + "{52_5}.ph", 0, 0, false, outputPath + "{52_5}.png");
    generate(inputPath + "{5_52}.ph", 0, 0, false, outputPath + "{5_52}.png");

    generate(inputPath + "{3x3}.ph", 0, 0, false, outputPath + "{3h2}.png");
    generate(inputPath + "{3x4}.ph", 0, 0, false, outputPath + "{3x4}.png");
    generate(inputPath + "{3x4}.ph", 1, 0, false, outputPath + "{3h3}.png");
    generate(inputPath + "{3x4}.ph", 2, 0, false, outputPath + "{4h3}.png");
    generate(inputPath + "{3x5}.ph", 0, 0, false, outputPath + "{3x5}.png");
    generate(inputPath + "{3x5}.ph", 1, 0, false, outputPath + "{3h5}.png");
    generate(inputPath + "{3x5}.ph", 2, 0, false, outputPath + "{5h5}.png");
    generate(inputPath + "{3x52}.ph", 0, 0, false, outputPath + "{3x52}.png");
    generate(inputPath + "{3x52}.ph", 1, 0, false, outputPath + "{3h52}.png");
    generate(inputPath + "{3x52}.ph", 2, 0, false, outputPath + "{52h52}.png");
    generate(inputPath + "{52x5}.ph", 0, 0, false, outputPath + "{52x5}.png");
    generate(inputPath + "{52x5}.ph", 1, 0, false, outputPath + "{52h3}.png");
    generate(inputPath + "{52x5}.ph", 2, 0, false, outputPath + "{5h3}.png");
    generate(inputPath + "{3x52_3}.ph", 0, 0, false, outputPath + "{3x52_3}.png");
    generate(inputPath + "{3x52_3}.ph", 1, 0, false, outputPath + "{3x5_3}.png");
    generate(inputPath + "{3x52_3}.ph", 2, 0, false, outputPath + "{52x5_3}.png");

    generate(inputPath + "t{3_3}.ph", 0, 0, false, outputPath + "t{3_3}.png");
    generate(inputPath + "t{4_3}.ph", 0, 0, false, outputPath + "t{4_3}.png");
    generate(inputPath + "t1{4_3}.ph", 0, 0, false, outputPath + "t1{4_3}.png");
    generate(inputPath + "t{3_4}.ph", 0, 0, false, outputPath + "t{3_4}.png");
    generate(inputPath + "t{5_3}.ph", 0, 0, false, outputPath + "t{5_3}.png");
    generate(inputPath + "t1{52_3}.ph", 0, 0, false, outputPath + "t1{52_3}.png");
    generate(inputPath + "t{3_5}.ph", 0, 0, false, outputPath + "t{3_5}.png");
    generate(inputPath + "t{3_52}.ph", 0, 0, false, outputPath + "t{3_52}.png");
    generate(inputPath + "t{5_52}.ph", 0, 0, false, outputPath + "t{5_52}.png");
    generate(inputPath + "t1{52_5}.ph", 0, 0, false, outputPath + "t1{52_5}.png");
    generate(inputPath + "r{3x4}.ph", 0, 0, false, outputPath + "r{3x4}.png");
    generate(inputPath + "r{3x4}.ph", 1, 0, false, outputPath + "h1{3x4_4}.png");
    generate(inputPath + "r{3x4}.ph", 2, 0, false, outputPath + "r{3x4}_h1{3x4_4}.png");
    generate(inputPath + "r1{3x4}.ph", 0, 0, false, outputPath + "r1{3x4}.png");
    generate(inputPath + "r1{3x4}.ph", 1, 0, false, outputPath + "h{3x4_4}.png");
    generate(inputPath + "r1{3x4}.ph", 2, 0, false, outputPath + "r1{3x4}_h{3x4_4}.png");
    generate(inputPath + "r{3x5}.ph", 0, 0, false, outputPath + "r{3x5}.png");
    generate(inputPath + "r{3x5}.ph", 1, 0, false, outputPath + "h1{3x5_5}.png");
    generate(inputPath + "r{3x5}.ph", 2, 0, false, outputPath + "r{3x5}_h1{3x5_5}.png");
    generate(inputPath + "r1{3x52}.ph", 0, 0, false, outputPath + "r1{3x52}.png");
    generate(inputPath + "r1{3x52}.ph", 1, 0, false, outputPath + "h{3x52_52}.png");
    generate(inputPath + "r1{3x52}.ph", 2, 0, false, outputPath + "r1{3x52}_h{3x52_52}.png");
    generate(inputPath + "r{52x5}.ph", 0, 0, false, outputPath + "r{52x5}.png");
    generate(inputPath + "r{52x5}.ph", 1, 0, false, outputPath + "h1{52x5_3}.png");
    generate(inputPath + "r{52x5}.ph", 2, 0, false, outputPath + "r{52x5}_h1{52x5_3}.png");
    generate(inputPath + "h{3x52_3}.ph", 0, 0, false, outputPath + "h{3x52_3}.png");
    generate(inputPath + "h{3x52_3}.ph", 1, 0, false, outputPath + "h1{3x52_5}.png");
    generate(inputPath + "h{3x52_3}.ph", 2, 0, false, outputPath + "h{3x52_3}_h1{3x52_5}.png");
    generate(inputPath + "h1{3x5_3}.ph", 0, 0, false, outputPath + "h1{3x5_3}.png");
    generate(inputPath + "h1{3x5_3}.ph", 1, 0, false, outputPath + "h{3x5_52}.png");
    generate(inputPath + "h1{3x5_3}.ph", 2, 0, false, outputPath + "h1{3x5_3}_h{3x5_52}.png");

    generate(inputPath + "t{3x4}.ph", 0, 0, false, outputPath + "t{3x4}.png");
    generate(inputPath + "t1{3x4}.ph", 0, 0, false, outputPath + "t1{3x4}.png");
    generate(inputPath + "t{3x5}.ph", 0, 0, false, outputPath + "t{3x5}.png");
    generate(inputPath + "t1{3x52}.ph", 0, 0, false, outputPath + "t1{3x52}.png");
    generate(inputPath + "t1{52x5}.ph", 0, 0, false, outputPath + "t1{52x5}.png");
    generate(inputPath + "t1{3x4x4}.ph", 0, 0, false, outputPath + "t1{3x4x4}.png");
    generate(inputPath + "t1{3x5x52}.ph", 0, 0, false, outputPath + "t1{3x5x52}.png");

    generate(inputPath + "s{3x4}.ph", 0, 0, false, outputPath + "s{3x4}.png");
    generate(inputPath + "s{3x5}.ph", 0, 0, false, outputPath + "s{3x5}.png");
    generate(inputPath + "s{3x52}.ph", 0, 0, false, outputPath + "s{3x52}.png");
    generate(inputPath + "s1{3x52}.ph", 0, 0, false, outputPath + "s1{3x52}.png");
    generate(inputPath + "s2{3x52}.ph", 0, 0, false, outputPath + "s2{3x52}.png");
    generate(inputPath + "s{52x5}.ph", 0, 0, false, outputPath + "s{52x5}.png");
    generate(inputPath + "s1{52x5}.ph", 0, 0, false, outputPath + "s1{52x5}.png");
    generate(inputPath + "s{3x3x52}.ph", 0, 0, false, outputPath + "s{3x3x52}.png");
    generate(inputPath + "s2{3x3x52}.ph", 0, 0, false, outputPath + "s2{3x3x52}.png");
    generate(inputPath + "s1{3x52x5}.ph", 0, 0, false, outputPath + "s1{3x52x5}.png");
    generate(inputPath + "s1{3x52x52}.ph", 0, 0, false, outputPath + "s1{3x52x52}.png");
    generate(inputPath + "s1{3x52x52}.ph", 1, 0, false, outputPath + "dr{3x52}.png");
    generate(inputPath + "s1{3x52x52}.ph", 2, 0, false, outputPath + "sdr{3x52}.png");
  }

  /**
   * Generates images of the uniform compounds of uniform polyhedra.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void generateCompounds(final String inputPath, final String outputPath) throws IOException
  {
    generate(inputPath + "2{3_3}.ph", 0, 0, true, outputPath + "2{3_3}.png");
    generate(inputPath + "2t{3_3}.ph", 0, 0, true, outputPath + "2t{3_3}.png");
    generate(inputPath + "2{3_5}.ph", 0, 0, true, outputPath + "2{3_5}.png");
    generate(inputPath + "2{3_52}.ph", 0, 0, true, outputPath + "2{3_52}.png");
    generate(inputPath + "2{52_5}.ph", 0, 0, true, outputPath + "2{52_5}.png");
    generate(inputPath + "2{5_52}.ph", 0, 0, true, outputPath + "2{5_52}.png");
    generate(inputPath + "2s{3x4}.ph", 0, 0, true, outputPath + "2s{3x4}.png");
    generate(inputPath + "2s{3x5}.ph", 0, 0, true, outputPath + "2s{3x5}.png");
    generate(inputPath + "2s{3x52}.ph", 0, 0, true, outputPath + "2s{3x52}.png");
    generate(inputPath + "2s1{3x52}.ph", 0, 0, true, outputPath + "2s1{3x52}.png");
    generate(inputPath + "2s2{3x52}.ph", 0, 0, true, outputPath + "2s2{3x52}.png");
    generate(inputPath + "2s{52x5}.ph", 0, 0, true, outputPath + "2s{52x5}.png");
    generate(inputPath + "2s1{52x5}.ph", 0, 0, true, outputPath + "2s1{52x5}.png");
    generate(inputPath + "2s1{3x52x5}.ph", 0, 0, true, outputPath + "2s1{3x52x5}.png");

    generate(inputPath + "5{3_3}.ph", 0, 1, true, outputPath + "5{3_3}.png");
    generate(inputPath + "10{3_3}.ph", 0, 1, true, outputPath + "10{3_3}.png");
    generate(inputPath + "5{4_3}.ph", 0, 1, true, outputPath + "5{4_3}.png");
    generate(inputPath + "5{3_4}.ph", 0, 1, true, outputPath + "5{3_4}.png");
    generate(inputPath + "5{3x3}.ph", 0, 1, true, outputPath + "5{3h2}.png");
    generate(inputPath + "5{3_5}.ph", 0, 1, true, outputPath + "5{3_5}.png");
    generate(inputPath + "5{3_52}.ph", 0, 1, true, outputPath + "5{3_52}.png");
    generate(inputPath + "5{52_5}.ph", 0, 1, true, outputPath + "5{52_5}.png");
    generate(inputPath + "5{5_52}.ph", 0, 1, true, outputPath + "5{5_52}.png");
    generate(inputPath + "5{3x4}.ph", 0, 1, true, outputPath + "5{3x4}.png");
    generate(inputPath + "5{3x4}.ph", 1, 1, true, outputPath + "5{3h3}.png");
    generate(inputPath + "5{3x4}.ph", 2, 1, true, outputPath + "5{4h3}.png");
    generate(inputPath + "5t{3_3}.ph", 0, 1, true, outputPath + "5t{3_3}.png");
    generate(inputPath + "10t{3_3}.ph", 0, 1, true, outputPath + "10t{3_3}.png");
    generate(inputPath + "5t{4_3}.ph", 0, 1, true, outputPath + "5t{4_3}.png");
    generate(inputPath + "5t1{4_3}.ph", 0, 1, true, outputPath + "5t1{4_3}.png");
    generate(inputPath + "5r{3x4}.ph", 0, 1, true, outputPath + "5r{3x4}.png");
    generate(inputPath + "5r{3x4}.ph", 1, 1, true, outputPath + "5h1{3x4_4}.png");
    generate(inputPath + "5r{3x4}.ph", 2, 1, true, outputPath + "5r{3x4}_h1{3x4_4}.png");
    generate(inputPath + "5r1{3x4}.ph", 0, 1, true, outputPath + "5r1{3x4}.png");
    generate(inputPath + "5r1{3x4}.ph", 1, 1, true, outputPath + "5h{3x4_4}.png");
    generate(inputPath + "5r1{3x4}.ph", 2, 1, true, outputPath + "5r1{3x4}_h{3x4_4}.png");

    generate(inputPath + "3{4_3}.ph", 0, 2, true, outputPath + "3{4_3}.png");
    generate(inputPath + "6{3_3}.ph", 0, 2, true, outputPath + "6{3_3}.png");
    generate(inputPath + "3a{4}.ph", 0, 2, true, outputPath + "3a{4}.png");
    generate(inputPath + "6a{4}.ph", 0, 2, true, outputPath + "6a{4}.png");
    generate(inputPath + "4p{3}.ph", 0, 1, true, outputPath + "4p{3}.png");
    generate(inputPath + "8p{3}.ph", 0, 1, true, outputPath + "8p{3}.png");
    generate(inputPath + "4p{6}.ph", 0, 1, true, outputPath + "4p{6}.png");
    generate(inputPath + "4{3_4}.ph", 0, 1, true, outputPath + "4{3_4}.png");
    generate(inputPath + "6p{5}.ph", 0, 2, true, outputPath + "6p{5}.png");
    generate(inputPath + "12p{5}.ph", 0, 2, true, outputPath + "12p{5}.png");
    generate(inputPath + "6p{52}.ph", 0, 2, true, outputPath + "6p{52}.png");
    generate(inputPath + "12p{52}.ph", 0, 2, true, outputPath + "12p{52}.png");
    generate(inputPath + "6a{52}.ph", 0, 2, true, outputPath + "6a{52}.png");
    generate(inputPath + "12a{52}.ph", 0, 2, true, outputPath + "12a{52}.png");
    generate(inputPath + "6p{X}.ph", 0, 2, true, outputPath + "6p{X}.png");
    generate(inputPath + "6p{X3}.ph", 0, 2, true, outputPath + "6p{X3}.png");
    generate(inputPath + "6a{5}.ph", 0, 2, true, outputPath + "6a{5}.png");
    generate(inputPath + "6a1{52}.ph", 0, 2, true, outputPath + "6a1{52}.png");
    generate(inputPath + "10p{3}.ph", 0, 0, true, outputPath + "10p{3}.png");
    generate(inputPath + "20p{3}.ph", 0, 0, true, outputPath + "20p{3}.png");
    generate(inputPath + "10p{6}.ph", 0, 0, true, outputPath + "10p{6}.png");
    generate(inputPath + "10{3_4}a.ph", 0, 0, true, outputPath + "10{3_4}a.png");
    generate(inputPath + "10{3_4}b.ph", 0, 0, true, outputPath + "10{3_4}b.png");
    generate(inputPath + "20{3_4}.ph", 0, 0, true, outputPath + "20{3_4}.png");
    generate(inputPath + "20{3x3}.ph", 0, 0, true, outputPath + "20{3h2}.png");
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
