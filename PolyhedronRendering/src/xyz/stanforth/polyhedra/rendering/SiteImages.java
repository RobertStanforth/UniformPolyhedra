package xyz.stanforth.polyhedra.rendering;

import java.io.IOException;

public final class SiteImages
{
  /**
   * Generates images of the uniform polyhedra.
   * @param generator Image generator.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void enumerate(final ImageGenerator generator, final String inputPath, final String outputPath) throws IOException
  {
    generator.prepareDirectory(outputPath);

    generateUniform(generator, inputPath + "Uniform\\", outputPath + "simple\\");
    generateCompounds(generator, inputPath + "Compound\\", outputPath + "compound\\");
  }

  /**
   * Generates images of the uniform polyhedra.
   * @param generator Image generator.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void generateUniform(final ImageGenerator generator, final String inputPath, final String outputPath) throws IOException
  {
    generator.prepareDirectory(outputPath);

    generator.generate(inputPath + "{3_3}.ph", 0, Palette.DEFAULT, false, outputPath + "{3_3}.png");
    generator.generate(inputPath + "{4_3}.ph", 0, Palette.DEFAULT, false, outputPath + "{4_3}.png");
    generator.generate(inputPath + "{3_4}.ph", 0, Palette.DEFAULT, false, outputPath + "{3_4}.png");
    generator.generate(inputPath + "{5_3}.ph", 0, Palette.DEFAULT, false, outputPath + "{5_3}.png");
    generator.generate(inputPath + "{52_3}.ph", 0, Palette.DEFAULT, false, outputPath + "{52_3}.png");
    generator.generate(inputPath + "{3_5}.ph", 0, Palette.DEFAULT, false, outputPath + "{3_5}.png");
    generator.generate(inputPath + "{3_52}.ph", 0, Palette.DEFAULT, false, outputPath + "{3_52}.png");
    generator.generate(inputPath + "{52_5}.ph", 0, Palette.DEFAULT, false, outputPath + "{52_5}.png");
    generator.generate(inputPath + "{5_52}.ph", 0, Palette.DEFAULT, false, outputPath + "{5_52}.png");

    generator.generate(inputPath + "{3x3}.ph", 0, Palette.DEFAULT, false, outputPath + "{3h2}.png");
    generator.generate(inputPath + "{3x4}.ph", 0, Palette.DEFAULT, false, outputPath + "{3x4}.png");
    generator.generate(inputPath + "{3x4}.ph", 1, Palette.DEFAULT, false, outputPath + "{3h3}.png");
    generator.generate(inputPath + "{3x4}.ph", 2, Palette.DEFAULT, false, outputPath + "{4h3}.png");
    generator.generate(inputPath + "{3x5}.ph", 0, Palette.DEFAULT, false, outputPath + "{3x5}.png");
    generator.generate(inputPath + "{3x5}.ph", 1, Palette.DEFAULT, false, outputPath + "{3h5}.png");
    generator.generate(inputPath + "{3x5}.ph", 2, Palette.DEFAULT, false, outputPath + "{5h5}.png");
    generator.generate(inputPath + "{3x52}.ph", 0, Palette.DEFAULT, false, outputPath + "{3x52}.png");
    generator.generate(inputPath + "{3x52}.ph", 1, Palette.DEFAULT, false, outputPath + "{3h52}.png");
    generator.generate(inputPath + "{3x52}.ph", 2, Palette.DEFAULT, false, outputPath + "{52h52}.png");
    generator.generate(inputPath + "{52x5}.ph", 0, Palette.DEFAULT, false, outputPath + "{52x5}.png");
    generator.generate(inputPath + "{52x5}.ph", 1, Palette.DEFAULT, false, outputPath + "{52h3}.png");
    generator.generate(inputPath + "{52x5}.ph", 2, Palette.DEFAULT, false, outputPath + "{5h3}.png");
    generator.generate(inputPath + "{3x52_3}.ph", 0, Palette.DEFAULT, false, outputPath + "{3x52_3}.png");
    generator.generate(inputPath + "{3x52_3}.ph", 1, Palette.DEFAULT, false, outputPath + "{3x5_3}.png");
    generator.generate(inputPath + "{3x52_3}.ph", 2, Palette.DEFAULT, false, outputPath + "{52x5_3}.png");

    generator.generate(inputPath + "t{3_3}.ph", 0, Palette.DEFAULT, false, outputPath + "t{3_3}.png");
    generator.generate(inputPath + "t{4_3}.ph", 0, Palette.DEFAULT, false, outputPath + "t{4_3}.png");
    generator.generate(inputPath + "t1{4_3}.ph", 0, Palette.DEFAULT, false, outputPath + "t1{4_3}.png");
    generator.generate(inputPath + "t{3_4}.ph", 0, Palette.DEFAULT, false, outputPath + "t{3_4}.png");
    generator.generate(inputPath + "t{5_3}.ph", 0, Palette.DEFAULT, false, outputPath + "t{5_3}.png");
    generator.generate(inputPath + "t1{52_3}.ph", 0, Palette.DEFAULT, false, outputPath + "t1{52_3}.png");
    generator.generate(inputPath + "t{3_5}.ph", 0, Palette.DEFAULT, false, outputPath + "t{3_5}.png");
    generator.generate(inputPath + "t{3_52}.ph", 0, Palette.DEFAULT, false, outputPath + "t{3_52}.png");
    generator.generate(inputPath + "t{5_52}.ph", 0, Palette.DEFAULT, false, outputPath + "t{5_52}.png");
    generator.generate(inputPath + "t1{52_5}.ph", 0, Palette.DEFAULT, false, outputPath + "t1{52_5}.png");
    generator.generate(inputPath + "r{3x4}.ph", 0, Palette.DEFAULT, false, outputPath + "r{3x4}.png");
    generator.generate(inputPath + "r{3x4}.ph", 1, Palette.DEFAULT, false, outputPath + "h1{3x4_4}.png");
    generator.generate(inputPath + "r{3x4}.ph", 2, Palette.DEFAULT, false, outputPath + "r{3x4}_h1{3x4_4}.png");
    generator.generate(inputPath + "r1{3x4}.ph", 0, Palette.DEFAULT, false, outputPath + "r1{3x4}.png");
    generator.generate(inputPath + "r1{3x4}.ph", 1, Palette.DEFAULT, false, outputPath + "h{3x4_4}.png");
    generator.generate(inputPath + "r1{3x4}.ph", 2, Palette.DEFAULT, false, outputPath + "r1{3x4}_h{3x4_4}.png");
    generator.generate(inputPath + "r{3x5}.ph", 0, Palette.DEFAULT, false, outputPath + "r{3x5}.png");
    generator.generate(inputPath + "r{3x5}.ph", 1, Palette.DEFAULT, false, outputPath + "h1{3x5_5}.png");
    generator.generate(inputPath + "r{3x5}.ph", 2, Palette.DEFAULT, false, outputPath + "r{3x5}_h1{3x5_5}.png");
    generator.generate(inputPath + "r1{3x52}.ph", 0, Palette.DEFAULT, false, outputPath + "r1{3x52}.png");
    generator.generate(inputPath + "r1{3x52}.ph", 1, Palette.DEFAULT, false, outputPath + "h{3x52_52}.png");
    generator.generate(inputPath + "r1{3x52}.ph", 2, Palette.DEFAULT, false, outputPath + "r1{3x52}_h{3x52_52}.png");
    generator.generate(inputPath + "r{52x5}.ph", 0, Palette.DEFAULT, false, outputPath + "r{52x5}.png");
    generator.generate(inputPath + "r{52x5}.ph", 1, Palette.DEFAULT, false, outputPath + "h1{52x5_3}.png");
    generator.generate(inputPath + "r{52x5}.ph", 2, Palette.DEFAULT, false, outputPath + "r{52x5}_h1{52x5_3}.png");
    generator.generate(inputPath + "h{3x52_3}.ph", 0, Palette.DEFAULT, false, outputPath + "h{3x52_3}.png");
    generator.generate(inputPath + "h{3x52_3}.ph", 1, Palette.DEFAULT, false, outputPath + "h1{3x52_5}.png");
    generator.generate(inputPath + "h{3x52_3}.ph", 2, Palette.DEFAULT, false, outputPath + "h{3x52_3}_h1{3x52_5}.png");
    generator.generate(inputPath + "h1{3x5_3}.ph", 0, Palette.DEFAULT, false, outputPath + "h1{3x5_3}.png");
    generator.generate(inputPath + "h1{3x5_3}.ph", 1, Palette.DEFAULT, false, outputPath + "h{3x5_52}.png");
    generator.generate(inputPath + "h1{3x5_3}.ph", 2, Palette.DEFAULT, false, outputPath + "h1{3x5_3}_h{3x5_52}.png");

    generator.generate(inputPath + "t{3x4}.ph", 0, Palette.DEFAULT, false, outputPath + "t{3x4}.png");
    generator.generate(inputPath + "t1{3x4}.ph", 0, Palette.DEFAULT, false, outputPath + "t1{3x4}.png");
    generator.generate(inputPath + "t{3x5}.ph", 0, Palette.DEFAULT, false, outputPath + "t{3x5}.png");
    generator.generate(inputPath + "t1{3x52}.ph", 0, Palette.DEFAULT, false, outputPath + "t1{3x52}.png");
    generator.generate(inputPath + "t1{52x5}.ph", 0, Palette.DEFAULT, false, outputPath + "t1{52x5}.png");
    generator.generate(inputPath + "t1{3x4x4}.ph", 0, Palette.DEFAULT, false, outputPath + "t1{3x4x4}.png");
    generator.generate(inputPath + "t1{3x5x52}.ph", 0, Palette.DEFAULT, false, outputPath + "t1{3x5x52}.png");

    generator.generate(inputPath + "s{3x4}.ph", 0, Palette.DEFAULT, false, outputPath + "s{3x4}.png");
    generator.generate(inputPath + "s{3x5}.ph", 0, Palette.DEFAULT, false, outputPath + "s{3x5}.png");
    generator.generate(inputPath + "s{3x52}.ph", 0, Palette.DEFAULT, false, outputPath + "s{3x52}.png");
    generator.generate(inputPath + "s1{3x52}.ph", 0, Palette.DEFAULT, false, outputPath + "s1{3x52}.png");
    generator.generate(inputPath + "s2{3x52}.ph", 0, Palette.DEFAULT, false, outputPath + "s2{3x52}.png");
    generator.generate(inputPath + "s{52x5}.ph", 0, Palette.DEFAULT, false, outputPath + "s{52x5}.png");
    generator.generate(inputPath + "s1{52x5}.ph", 0, Palette.DEFAULT, false, outputPath + "s1{52x5}.png");
    generator.generate(inputPath + "s{3x3x52}.ph", 0, Palette.DEFAULT, false, outputPath + "s{3x3x52}.png");
    generator.generate(inputPath + "s2{3x3x52}.ph", 0, Palette.DEFAULT, false, outputPath + "s2{3x3x52}.png");
    generator.generate(inputPath + "s1{3x52x5}.ph", 0, Palette.DEFAULT, false, outputPath + "s1{3x52x5}.png");
    generator.generate(inputPath + "s1{3x52x52}.ph", 0, Palette.DEFAULT, false, outputPath + "s1{3x52x52}.png");
    generator.generate(inputPath + "s1{3x52x52}.ph", 1, Palette.DEFAULT, false, outputPath + "dr{3x52}.png");
    generator.generate(inputPath + "s1{3x52x52}.ph", 2, Palette.DEFAULT, false, outputPath + "sdr{3x52}.png");

    generateDihedral(generator, inputPath + "Dihedral\\", outputPath + "dihedral\\");
  }

  /**
   * Generates images of the dihedral (prismatic) uniform polyhedra.
   * @param generator Image generator.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void generateDihedral(final ImageGenerator generator, final String inputPath, final String outputPath) throws IOException
  {
    generator.prepareDirectory(outputPath);

    generator.generate(inputPath + "p{3}.ph", 0, Palette.DEFAULT, false, outputPath + "p{3}.png");
    generator.generate(inputPath + "p{5}.ph", 0, Palette.DEFAULT, false, outputPath + "p{5}.png");
    generator.generate(inputPath + "p{52}.ph", 0, Palette.DEFAULT, false, outputPath + "p{52}.png");
    generator.generate(inputPath + "p{52}.ph", 0, Palette.DEFAULT, true, outputPath + "p{52}_xor.png");
    generator.generate(inputPath + "p{6}.ph", 0, Palette.DEFAULT, false, outputPath + "p{6}.png");
    generator.generate(inputPath + "p{7}.ph", 0, Palette.DEFAULT, false, outputPath + "p{7}.png");
    generator.generate(inputPath + "p{72}.ph", 0, Palette.DEFAULT, false, outputPath + "p{72}.png");
    generator.generate(inputPath + "p{72}.ph", 0, Palette.DEFAULT, true, outputPath + "p{72}_xor.png");
    generator.generate(inputPath + "p{73}.ph", 0, Palette.DEFAULT, false, outputPath + "p{73}.png");
    generator.generate(inputPath + "p{73}.ph", 0, Palette.DEFAULT, true, outputPath + "p{73}_xor.png");
    generator.generate(inputPath + "p{8}.ph", 0, Palette.DEFAULT, false, outputPath + "p{8}.png");
    generator.generate(inputPath + "p{83}.ph", 0, Palette.DEFAULT, false, outputPath + "p{83}.png");
    generator.generate(inputPath + "p{83}.ph", 0, Palette.DEFAULT, true, outputPath + "p{83}_xor.png");
    generator.generate(inputPath + "p{9}.ph", 0, Palette.DEFAULT, false, outputPath + "p{9}.png");
    generator.generate(inputPath + "p{92}.ph", 0, Palette.DEFAULT, false, outputPath + "p{92}.png");
    generator.generate(inputPath + "p{92}.ph", 0, Palette.DEFAULT, true, outputPath + "p{92}_xor.png");
    generator.generate(inputPath + "p{94}.ph", 0, Palette.DEFAULT, false, outputPath + "p{94}.png");
    generator.generate(inputPath + "p{94}.ph", 0, Palette.DEFAULT, true, outputPath + "p{94}_xor.png");
    generator.generate(inputPath + "p{X}.ph", 0, Palette.DEFAULT, false, outputPath + "p{X}.png");
    generator.generate(inputPath + "p{X3}.ph", 0, Palette.DEFAULT, false, outputPath + "p{X3}.png");
    generator.generate(inputPath + "p{X3}.ph", 0, Palette.DEFAULT, true, outputPath + "p{X3}_xor.png");
    generator.generate(inputPath + "p{Y}.ph", 0, Palette.DEFAULT, false, outputPath + "p{Y}.png");
    generator.generate(inputPath + "p{Y2}.ph", 0, Palette.DEFAULT, false, outputPath + "p{Y2}.png");
    generator.generate(inputPath + "p{Y2}.ph", 0, Palette.DEFAULT, true, outputPath + "p{Y2}_xor.png");
    generator.generate(inputPath + "p{Y3}.ph", 0, Palette.DEFAULT, false, outputPath + "p{Y3}.png");
    generator.generate(inputPath + "p{Y3}.ph", 0, Palette.DEFAULT, true, outputPath + "p{Y3}_xor.png");
    generator.generate(inputPath + "p{Y4}.ph", 0, Palette.DEFAULT, false, outputPath + "p{Y4}.png");
    generator.generate(inputPath + "p{Y4}.ph", 0, Palette.DEFAULT, true, outputPath + "p{Y4}_xor.png");
    generator.generate(inputPath + "p{Y5}.ph", 0, Palette.DEFAULT, false, outputPath + "p{Y5}.png");
    generator.generate(inputPath + "p{Y5}.ph", 0, Palette.DEFAULT, true, outputPath + "p{Y5}_xor.png");

    generator.generate(inputPath + "a{4}.ph", 0, Palette.DEFAULT, false, outputPath + "a{4}.png");
    generator.generate(inputPath + "a{5}.ph", 0, Palette.DEFAULT, false, outputPath + "a{5}.png");
    generator.generate(inputPath + "a{52}.ph", 0, Palette.DEFAULT, false, outputPath + "a{52}.png");
    generator.generate(inputPath + "a{52}.ph", 0, Palette.DEFAULT, true, outputPath + "a{52}_xor.png");
    generator.generate(inputPath + "a1{52}.ph", 0, Palette.DEFAULT, false, outputPath + "a1{52}.png");
    generator.generate(inputPath + "a1{52}.ph", 0, Palette.DEFAULT, true, outputPath + "a1{52}_xor.png");
    generator.generate(inputPath + "a{6}.ph", 0, Palette.DEFAULT, false, outputPath + "a{6}.png");
    generator.generate(inputPath + "a{7}.ph", 0, Palette.DEFAULT, false, outputPath + "a{7}.png");
    generator.generate(inputPath + "a{72}.ph", 0, Palette.DEFAULT, false, outputPath + "a{72}.png");
    generator.generate(inputPath + "a{72}.ph", 0, Palette.DEFAULT, true, outputPath + "a{72}_xor.png");
    generator.generate(inputPath + "a{73}.ph", 0, Palette.DEFAULT, false, outputPath + "a{73}.png");
    generator.generate(inputPath + "a{73}.ph", 0, Palette.DEFAULT, true, outputPath + "a{73}_xor.png");
    generator.generate(inputPath + "a1{73}.ph", 0, Palette.DEFAULT, false, outputPath + "a1{73}.png");
    generator.generate(inputPath + "a1{73}.ph", 0, Palette.DEFAULT, true, outputPath + "a1{73}_xor.png");
    generator.generate(inputPath + "a{8}.ph", 0, Palette.DEFAULT, false, outputPath + "a{8}.png");
    generator.generate(inputPath + "a{83}.ph", 0, Palette.DEFAULT, false, outputPath + "a{83}.png");
    generator.generate(inputPath + "a{83}.ph", 0, Palette.DEFAULT, true, outputPath + "a{83}_xor.png");
    generator.generate(inputPath + "a1{83}.ph", 0, Palette.DEFAULT, false, outputPath + "a1{83}.png");
    generator.generate(inputPath + "a1{83}.ph", 0, Palette.DEFAULT, true, outputPath + "a1{83}_xor.png");
    generator.generate(inputPath + "a{9}.ph", 0, Palette.DEFAULT, false, outputPath + "a{9}.png");
    generator.generate(inputPath + "a{92}.ph", 0, Palette.DEFAULT, false, outputPath + "a{92}.png");
    generator.generate(inputPath + "a{92}.ph", 0, Palette.DEFAULT, true, outputPath + "a{92}_xor.png");
    generator.generate(inputPath + "a{94}.ph", 0, Palette.DEFAULT, false, outputPath + "a{94}.png");
    generator.generate(inputPath + "a{94}.ph", 0, Palette.DEFAULT, true, outputPath + "a{94}_xor.png");
    generator.generate(inputPath + "a1{94}.ph", 0, Palette.DEFAULT, false, outputPath + "a1{94}.png");
    generator.generate(inputPath + "a1{94}.ph", 0, Palette.DEFAULT, true, outputPath + "a1{94}_xor.png");
    generator.generate(inputPath + "a{X}.ph", 0, Palette.DEFAULT, false, outputPath + "a{X}.png");
    generator.generate(inputPath + "a{X3}.ph", 0, Palette.DEFAULT, false, outputPath + "a{X3}.png");
    generator.generate(inputPath + "a{X3}.ph", 0, Palette.DEFAULT, true, outputPath + "a{X3}_xor.png");
    generator.generate(inputPath + "a{Y}.ph", 0, Palette.DEFAULT, false, outputPath + "a{Y}.png");
    generator.generate(inputPath + "a{Y2}.ph", 0, Palette.DEFAULT, false, outputPath + "a{Y2}.png");
    generator.generate(inputPath + "a{Y2}.ph", 0, Palette.DEFAULT, true, outputPath + "a{Y2}_xor.png");
    generator.generate(inputPath + "a{Y3}.ph", 0, Palette.DEFAULT, false, outputPath + "a{Y3}.png");
    generator.generate(inputPath + "a{Y3}.ph", 0, Palette.DEFAULT, true, outputPath + "a{Y3}_xor.png");
    generator.generate(inputPath + "a{Y4}.ph", 0, Palette.DEFAULT, false, outputPath + "a{Y4}.png");
    generator.generate(inputPath + "a{Y4}.ph", 0, Palette.DEFAULT, true, outputPath + "a{Y4}_xor.png");
    generator.generate(inputPath + "a{Y5}.ph", 0, Palette.DEFAULT, false, outputPath + "a{Y5}.png");
    generator.generate(inputPath + "a{Y5}.ph", 0, Palette.DEFAULT, true, outputPath + "a{Y5}_xor.png");
    generator.generate(inputPath + "a1{Y5}.ph", 0, Palette.DEFAULT, false, outputPath + "a1{Y5}.png");
    generator.generate(inputPath + "a1{Y5}.ph", 0, Palette.DEFAULT, true, outputPath + "a1{Y5}_xor.png");
    generator.generate(inputPath + "a1{Y4}.ph", 0, Palette.DEFAULT, false, outputPath + "a1{Y4}.png");
    generator.generate(inputPath + "a1{Y4}.ph", 0, Palette.DEFAULT, true, outputPath + "a1{Y4}_xor.png");
  }

  /**
   * Generates images of the uniform compounds of uniform polyhedra.
   * @param generator Image generator.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void generateCompounds(final ImageGenerator generator, final String inputPath, final String outputPath) throws IOException
  {
    generator.prepareDirectory(outputPath);

    generator.generate(inputPath + "2{3_3}.ph", 0, Palette.DEFAULT, true, outputPath + "2{3_3}.png");
    generator.generate(inputPath + "2t{3_3}.ph", 0, Palette.DEFAULT, true, outputPath + "2t{3_3}.png");
    generator.generate(inputPath + "2{3_5}.ph", 0, Palette.DEFAULT, true, outputPath + "2{3_5}.png");
    generator.generate(inputPath + "2{3_52}.ph", 0, Palette.DEFAULT, true, outputPath + "2{3_52}.png");
    generator.generate(inputPath + "2{52_5}.ph", 0, Palette.DEFAULT, true, outputPath + "2{52_5}.png");
    generator.generate(inputPath + "2{5_52}.ph", 0, Palette.DEFAULT, true, outputPath + "2{5_52}.png");
    generator.generate(inputPath + "2s{3x4}.ph", 0, Palette.DEFAULT, true, outputPath + "2s{3x4}.png");
    generator.generate(inputPath + "2s{3x5}.ph", 0, Palette.DEFAULT, true, outputPath + "2s{3x5}.png");
    generator.generate(inputPath + "2s{3x52}.ph", 0, Palette.DEFAULT, true, outputPath + "2s{3x52}.png");
    generator.generate(inputPath + "2s1{3x52}.ph", 0, Palette.DEFAULT, true, outputPath + "2s1{3x52}.png");
    generator.generate(inputPath + "2s2{3x52}.ph", 0, Palette.DEFAULT, true, outputPath + "2s2{3x52}.png");
    generator.generate(inputPath + "2s{52x5}.ph", 0, Palette.DEFAULT, true, outputPath + "2s{52x5}.png");
    generator.generate(inputPath + "2s1{52x5}.ph", 0, Palette.DEFAULT, true, outputPath + "2s1{52x5}.png");
    generator.generate(inputPath + "2s1{3x52x5}.ph", 0, Palette.DEFAULT, true, outputPath + "2s1{3x52x5}.png");

    generator.generate(inputPath + "5{3_3}.ph", 0, Palette.ALT_A, true, outputPath + "5{3_3}.png");
    generator.generate(inputPath + "10{3_3}.ph", 0, Palette.ALT_A, true, outputPath + "10{3_3}.png");
    generator.generate(inputPath + "5{4_3}.ph", 0, Palette.ALT_A, true, outputPath + "5{4_3}.png");
    generator.generate(inputPath + "5{3_4}.ph", 0, Palette.ALT_A, true, outputPath + "5{3_4}.png");
    generator.generate(inputPath + "5{3x3}.ph", 0, Palette.ALT_A, true, outputPath + "5{3h2}.png");
    generator.generate(inputPath + "5{3_5}.ph", 0, Palette.ALT_A, true, outputPath + "5{3_5}.png");
    generator.generate(inputPath + "5{3_52}.ph", 0, Palette.ALT_A, true, outputPath + "5{3_52}.png");
    generator.generate(inputPath + "5{52_5}.ph", 0, Palette.ALT_A, true, outputPath + "5{52_5}.png");
    generator.generate(inputPath + "5{5_52}.ph", 0, Palette.ALT_A, true, outputPath + "5{5_52}.png");
    generator.generate(inputPath + "5{3x4}.ph", 0, Palette.ALT_A, true, outputPath + "5{3x4}.png");
    generator.generate(inputPath + "5{3x4}.ph", 1, Palette.ALT_A, true, outputPath + "5{3h3}.png");
    generator.generate(inputPath + "5{3x4}.ph", 2, Palette.ALT_A, true, outputPath + "5{4h3}.png");
    generator.generate(inputPath + "5t{3_3}.ph", 0, Palette.ALT_A, true, outputPath + "5t{3_3}.png");
    generator.generate(inputPath + "10t{3_3}.ph", 0, Palette.ALT_A, true, outputPath + "10t{3_3}.png");
    generator.generate(inputPath + "5t{4_3}.ph", 0, Palette.ALT_A, true, outputPath + "5t{4_3}.png");
    generator.generate(inputPath + "5t1{4_3}.ph", 0, Palette.ALT_A, true, outputPath + "5t1{4_3}.png");
    generator.generate(inputPath + "5r{3x4}.ph", 0, Palette.ALT_A, true, outputPath + "5r{3x4}.png");
    generator.generate(inputPath + "5r{3x4}.ph", 1, Palette.ALT_A, true, outputPath + "5h1{3x4_4}.png");
    generator.generate(inputPath + "5r{3x4}.ph", 2, Palette.ALT_A, true, outputPath + "5r{3x4}_h1{3x4_4}.png");
    generator.generate(inputPath + "5r1{3x4}.ph", 0, Palette.ALT_A, true, outputPath + "5r1{3x4}.png");
    generator.generate(inputPath + "5r1{3x4}.ph", 1, Palette.ALT_A, true, outputPath + "5h{3x4_4}.png");
    generator.generate(inputPath + "5r1{3x4}.ph", 2, Palette.ALT_A, true, outputPath + "5r1{3x4}_h{3x4_4}.png");

    generator.generate(inputPath + "3{4_3}.ph", 0, Palette.ALT_B, true, outputPath + "3{4_3}.png");
    generator.generate(inputPath + "6{3_3}.ph", 0, Palette.ALT_B, true, outputPath + "6{3_3}.png");
    generator.generateRotating(inputPath + "6{4_3}.phr", Math.PI/2., 0, Palette.ALT_B, true, outputPath + "6{4_3}.gif");
    generator.generateRotating(inputPath + "12{3_3}.phr", Math.PI/2., 0, Palette.ALT_B, true, outputPath + "12{3_3}.gif");
    generator.generateRotating(inputPath + "6{3_3}.phr", Math.PI, 0, Palette.ALT_B, true, outputPath + "6{3_3}.gif");
    generator.generate(inputPath + "3a{4}.ph", 0, Palette.ALT_B, true, outputPath + "3a{4}.png");
    generator.generate(inputPath + "6a{4}.ph", 0, Palette.ALT_B, true, outputPath + "6a{4}.png");
    generator.generate(inputPath + "4p{3}.ph", 0, Palette.ALT_A, true, outputPath + "4p{3}.png");
    generator.generate(inputPath + "8p{3}.ph", 0, Palette.ALT_A, true, outputPath + "8p{3}.png");
    generator.generate(inputPath + "4p{6}.ph", 0, Palette.ALT_A, true, outputPath + "4p{6}.png");
    generator.generate(inputPath + "4{3_4}.ph", 0, Palette.ALT_A, true, outputPath + "4{3_4}.png");
    generator.generateRotating(inputPath + "4{3_4}.phr", 2.*Math.PI/3., 0, Palette.ALT_A, true, outputPath + "4{3_4}.gif");
    generator.generateRotating(inputPath + "8{3_4}.phr", 2.*Math.PI/3., 0, Palette.ALT_A, true, outputPath + "8{3_4}.gif");
    generator.generate(inputPath + "6p{5}.ph", 0, Palette.ALT_B, true, outputPath + "6p{5}.png");
    generator.generate(inputPath + "12p{5}.ph", 0, Palette.ALT_B, true, outputPath + "12p{5}.png");
    generator.generate(inputPath + "6p{52}.ph", 0, Palette.ALT_B, true, outputPath + "6p{52}.png");
    generator.generate(inputPath + "12p{52}.ph", 0, Palette.ALT_B, true, outputPath + "12p{52}.png");
    generator.generate(inputPath + "6a{52}.ph", 0, Palette.ALT_B, true, outputPath + "6a{52}.png");
    generator.generate(inputPath + "12a{52}.ph", 0, Palette.ALT_B, true, outputPath + "12a{52}.png");
    generator.generate(inputPath + "6p{X}.ph", 0, Palette.ALT_B, true, outputPath + "6p{X}.png");
    generator.generate(inputPath + "6p{X3}.ph", 0, Palette.ALT_B, true, outputPath + "6p{X3}.png");
    generator.generate(inputPath + "6a{5}.ph", 0, Palette.ALT_B, true, outputPath + "6a{5}.png");
    generator.generate(inputPath + "6a1{52}.ph", 0, Palette.ALT_B, true, outputPath + "6a1{52}.png");
    generator.generateRotating(inputPath + "12a{5}.phr", 2.*Math.PI/5., 0, Palette.ALT_B, true, outputPath + "12a{5}.gif");
    generator.generateRotating(inputPath + "12a1{52}.phr", 2.*Math.PI/5., 0, Palette.ALT_B, true, outputPath + "12a1{52}.gif");
    generator.generate(inputPath + "10p{3}.ph", 0, Palette.DEFAULT, true, outputPath + "10p{3}.png");
    generator.generate(inputPath + "20p{3}.ph", 0, Palette.DEFAULT, true, outputPath + "20p{3}.png");
    generator.generate(inputPath + "10p{6}.ph", 0, Palette.DEFAULT, true, outputPath + "10p{6}.png");
    generator.generate(inputPath + "10{3_4}a.ph", 0, Palette.DEFAULT, true, outputPath + "10{3_4}a.png");
    generator.generate(inputPath + "10{3_4}b.ph", 0, Palette.DEFAULT, true, outputPath + "10{3_4}b.png");
    generator.generateRotating(inputPath + "20{3_4}.phr", 2.*Math.PI/3., 0, Palette.DEFAULT, true, outputPath + "20{3_4}.gif");
    generator.generate(inputPath + "20{3_4}.ph", 0, Palette.DEFAULT, true, outputPath + "20{3_4}.png");
    generator.generate(inputPath + "20{3x3}.ph", 0, Palette.DEFAULT, true, outputPath + "20{3h2}.png");

    generateDihedralCompounds(generator, inputPath + "Dihedral\\", outputPath + "dihedral\\");
  }

  /**
   * Generates images of the dihedral (prismatic) uniform compounds of uniform polyhedra.
   * @param generator Image generator.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void generateDihedralCompounds(final ImageGenerator generator, final String inputPath, final String outputPath) throws IOException
  {
    generator.prepareDirectory(outputPath);

    generator.generate(inputPath + "2p{3}.ph", 0, Palette.DEFAULT, true, outputPath + "2p{3}.png");
    generator.generate(inputPath + "2{4_3}.ph", 0, Palette.DEFAULT, true, outputPath + "2{4_3}.png");
    generator.generate(inputPath + "2p{5}.ph", 0, Palette.DEFAULT, true, outputPath + "2p{5}.png");
    generator.generate(inputPath + "2p{52}.ph", 0, Palette.DEFAULT, true, outputPath + "2p{52}.png");
    generator.generate(inputPath + "3p{3}.ph", 0, Palette.DEFAULT, true, outputPath + "3p{3}.png");
    generator.generateRotating(inputPath + "2p{3}.phr", 2.*Math.PI/3., 0, Palette.DEFAULT, true, outputPath + "2p{3}.gif");
    generator.generateRotating(inputPath + "2{4_3}.phr", Math.PI/2., 0, Palette.DEFAULT, true, outputPath + "2{4_3}.gif");
    generator.generateRotating(inputPath + "2p{5}.phr", 2.*Math.PI/5., 0, Palette.DEFAULT, true, outputPath + "2p{5}.gif");
    generator.generateRotating(inputPath + "2p{52}.phr", 2.*Math.PI/5., 0, Palette.DEFAULT, true, outputPath + "2p{52}.gif");

    generator.generate(inputPath + "2{3_4}.ph", 0, Palette.DEFAULT, true, outputPath + "2{3_4}.png");
    generator.generate(inputPath + "2a{4}.ph", 0, Palette.DEFAULT, true, outputPath + "2a{4}.png");
    generator.generate(inputPath + "2a{5}.ph", 0, Palette.DEFAULT, true, outputPath + "2a{5}.png");
    generator.generate(inputPath + "2a{52}.ph", 0, Palette.DEFAULT, true, outputPath + "2a{52}.png");
    generator.generate(inputPath + "2a1{52}.ph", 0, Palette.DEFAULT, true, outputPath + "2a1{52}.png");
    generator.generate(inputPath + "3{3_3}.ph", 0, Palette.DEFAULT, true, outputPath + "3{3_3}.png");
    generator.generate(inputPath + "3{3_4}.ph", 0, Palette.DEFAULT, true, outputPath + "3{3_4}.png");
    generator.generate(inputPath + "4{3_3}.ph", 0, Palette.DEFAULT, true, outputPath + "4{3_3}.png");
    generator.generate(inputPath + "5{3_3}.ph", 0, Palette.DEFAULT, true, outputPath + "5{3_3}.png");
    generator.generateRotating(inputPath + "2{3_3}.phr", Math.PI, 0, Palette.DEFAULT, true, outputPath + "2{3_3}.gif");
    generator.generateRotating(inputPath + "2{3_4}.phr", 2.*Math.PI/3., 0, Palette.DEFAULT, true, outputPath + "2{3_4}.gif");
    generator.generateRotating(inputPath + "2a{4}.phr", Math.PI/2., 0, Palette.DEFAULT, true, outputPath + "2a{4}.gif");
    generator.generateRotating(inputPath + "4{3_3}.phr", Math.PI/2., 0, Palette.DEFAULT, true, outputPath + "4{3_3}.gif");
    generator.generateRotating(inputPath + "2a{5}.phr", 2.*Math.PI/5., 0, Palette.DEFAULT, true, outputPath + "2a{5}.gif");
    generator.generateRotating(inputPath + "2a{52}.phr", 2.*Math.PI/5., 0, Palette.DEFAULT, true, outputPath + "2a{52}.gif");
    generator.generateRotating(inputPath + "2a1{52}.phr", 2.*Math.PI/5., 0, Palette.DEFAULT, true, outputPath + "2a1{52}.gif");
  }

  private SiteImages() {}
}
