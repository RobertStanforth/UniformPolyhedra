package xyz.stanforth.polyhedra.rendering;

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
    new File(outputPath).mkdir();

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

    generateDihedral(inputPath + "Dihedral\\", outputPath + "dihedral\\");
  }

  /**
   * Generates images of the dihedral (prismatic) uniform polyhedra.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void generateDihedral(final String inputPath, final String outputPath) throws IOException
  {
    new File(outputPath).mkdir();

    generate(inputPath + "p{3}.ph", 0, 0, false, outputPath + "p{3}.png");
    generate(inputPath + "p{5}.ph", 0, 0, false, outputPath + "p{5}.png");
    generate(inputPath + "p{52}.ph", 0, 0, false, outputPath + "p{52}.png");
    generate(inputPath + "p{52}.ph", 0, 0, true, outputPath + "p{52}_xor.png");
    generate(inputPath + "p{6}.ph", 0, 0, false, outputPath + "p{6}.png");
    generate(inputPath + "p{7}.ph", 0, 0, false, outputPath + "p{7}.png");
    generate(inputPath + "p{72}.ph", 0, 0, false, outputPath + "p{72}.png");
    generate(inputPath + "p{72}.ph", 0, 0, true, outputPath + "p{72}_xor.png");
    generate(inputPath + "p{73}.ph", 0, 0, false, outputPath + "p{73}.png");
    generate(inputPath + "p{73}.ph", 0, 0, true, outputPath + "p{73}_xor.png");
    generate(inputPath + "p{8}.ph", 0, 0, false, outputPath + "p{8}.png");
    generate(inputPath + "p{83}.ph", 0, 0, false, outputPath + "p{83}.png");
    generate(inputPath + "p{83}.ph", 0, 0, true, outputPath + "p{83}_xor.png");
    generate(inputPath + "p{9}.ph", 0, 0, false, outputPath + "p{9}.png");
    generate(inputPath + "p{92}.ph", 0, 0, false, outputPath + "p{92}.png");
    generate(inputPath + "p{92}.ph", 0, 0, true, outputPath + "p{92}_xor.png");
    generate(inputPath + "p{94}.ph", 0, 0, false, outputPath + "p{94}.png");
    generate(inputPath + "p{94}.ph", 0, 0, true, outputPath + "p{94}_xor.png");
    generate(inputPath + "p{X}.ph", 0, 0, false, outputPath + "p{X}.png");
    generate(inputPath + "p{X3}.ph", 0, 0, false, outputPath + "p{X3}.png");
    generate(inputPath + "p{X3}.ph", 0, 0, true, outputPath + "p{X3}_xor.png");
    generate(inputPath + "p{Y}.ph", 0, 0, false, outputPath + "p{Y}.png");
    generate(inputPath + "p{Y2}.ph", 0, 0, false, outputPath + "p{Y2}.png");
    generate(inputPath + "p{Y2}.ph", 0, 0, true, outputPath + "p{Y2}_xor.png");
    generate(inputPath + "p{Y3}.ph", 0, 0, false, outputPath + "p{Y3}.png");
    generate(inputPath + "p{Y3}.ph", 0, 0, true, outputPath + "p{Y3}_xor.png");
    generate(inputPath + "p{Y4}.ph", 0, 0, false, outputPath + "p{Y4}.png");
    generate(inputPath + "p{Y4}.ph", 0, 0, true, outputPath + "p{Y4}_xor.png");
    generate(inputPath + "p{Y5}.ph", 0, 0, false, outputPath + "p{Y5}.png");
    generate(inputPath + "p{Y5}.ph", 0, 0, true, outputPath + "p{Y5}_xor.png");

    generate(inputPath + "a{4}.ph", 0, 0, false, outputPath + "a{4}.png");
    generate(inputPath + "a{5}.ph", 0, 0, false, outputPath + "a{5}.png");
    generate(inputPath + "a{52}.ph", 0, 0, false, outputPath + "a{52}.png");
    generate(inputPath + "a{52}.ph", 0, 0, true, outputPath + "a{52}_xor.png");
    generate(inputPath + "a1{52}.ph", 0, 0, false, outputPath + "a1{52}.png");
    generate(inputPath + "a1{52}.ph", 0, 0, true, outputPath + "a1{52}_xor.png");
    generate(inputPath + "a{6}.ph", 0, 0, false, outputPath + "a{6}.png");
    generate(inputPath + "a{7}.ph", 0, 0, false, outputPath + "a{7}.png");
    generate(inputPath + "a{72}.ph", 0, 0, false, outputPath + "a{72}.png");
    generate(inputPath + "a{72}.ph", 0, 0, true, outputPath + "a{72}_xor.png");
    generate(inputPath + "a{73}.ph", 0, 0, false, outputPath + "a{73}.png");
    generate(inputPath + "a{73}.ph", 0, 0, true, outputPath + "a{73}_xor.png");
    generate(inputPath + "a1{73}.ph", 0, 0, false, outputPath + "a1{73}.png");
    generate(inputPath + "a1{73}.ph", 0, 0, true, outputPath + "a1{73}_xor.png");
    generate(inputPath + "a{8}.ph", 0, 0, false, outputPath + "a{8}.png");
    generate(inputPath + "a{83}.ph", 0, 0, false, outputPath + "a{83}.png");
    generate(inputPath + "a{83}.ph", 0, 0, true, outputPath + "a{83}_xor.png");
    generate(inputPath + "a1{83}.ph", 0, 0, false, outputPath + "a1{83}.png");
    generate(inputPath + "a1{83}.ph", 0, 0, true, outputPath + "a1{83}_xor.png");
    generate(inputPath + "a{9}.ph", 0, 0, false, outputPath + "a{9}.png");
    generate(inputPath + "a{92}.ph", 0, 0, false, outputPath + "a{92}.png");
    generate(inputPath + "a{92}.ph", 0, 0, true, outputPath + "a{92}_xor.png");
    generate(inputPath + "a{94}.ph", 0, 0, false, outputPath + "a{94}.png");
    generate(inputPath + "a{94}.ph", 0, 0, true, outputPath + "a{94}_xor.png");
    generate(inputPath + "a1{94}.ph", 0, 0, false, outputPath + "a1{94}.png");
    generate(inputPath + "a1{94}.ph", 0, 0, true, outputPath + "a1{94}_xor.png");
    generate(inputPath + "a{X}.ph", 0, 0, false, outputPath + "a{X}.png");
    generate(inputPath + "a{X3}.ph", 0, 0, false, outputPath + "a{X3}.png");
    generate(inputPath + "a{X3}.ph", 0, 0, true, outputPath + "a{X3}_xor.png");
    generate(inputPath + "a{Y}.ph", 0, 0, false, outputPath + "a{Y}.png");
    generate(inputPath + "a{Y2}.ph", 0, 0, false, outputPath + "a{Y2}.png");
    generate(inputPath + "a{Y2}.ph", 0, 0, true, outputPath + "a{Y2}_xor.png");
    generate(inputPath + "a{Y3}.ph", 0, 0, false, outputPath + "a{Y3}.png");
    generate(inputPath + "a{Y3}.ph", 0, 0, true, outputPath + "a{Y3}_xor.png");
    generate(inputPath + "a{Y4}.ph", 0, 0, false, outputPath + "a{Y4}.png");
    generate(inputPath + "a{Y4}.ph", 0, 0, true, outputPath + "a{Y4}_xor.png");
    generate(inputPath + "a{Y5}.ph", 0, 0, false, outputPath + "a{Y5}.png");
    generate(inputPath + "a{Y5}.ph", 0, 0, true, outputPath + "a{Y5}_xor.png");
    generate(inputPath + "a1{Y5}.ph", 0, 0, false, outputPath + "a1{Y5}.png");
    generate(inputPath + "a1{Y5}.ph", 0, 0, true, outputPath + "a1{Y5}_xor.png");
    generate(inputPath + "a1{Y4}.ph", 0, 0, false, outputPath + "a1{Y4}.png");
    generate(inputPath + "a1{Y4}.ph", 0, 0, true, outputPath + "a1{Y4}_xor.png");
  }

  /**
   * Generates images of the uniform compounds of uniform polyhedra.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void generateCompounds(final String inputPath, final String outputPath) throws IOException
  {
    new File(outputPath).mkdir();

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
    generateRotating(inputPath + "6{4_3}.phr", Math.PI/2., 40, 0, 0, true, outputPath + "6{4_3}.gif");
    generateRotating(inputPath + "12{3_3}.phr", Math.PI/2., 40, 0, 0, true, outputPath + "12{3_3}.gif");
    generateRotating(inputPath + "6{3_3}.phr", Math.PI, 40, 0, 0, true, outputPath + "6{3_3}.gif");
    generate(inputPath + "3a{4}.ph", 0, 2, true, outputPath + "3a{4}.png");
    generate(inputPath + "6a{4}.ph", 0, 2, true, outputPath + "6a{4}.png");
    generate(inputPath + "4p{3}.ph", 0, 1, true, outputPath + "4p{3}.png");
    generate(inputPath + "8p{3}.ph", 0, 1, true, outputPath + "8p{3}.png");
    generate(inputPath + "4p{6}.ph", 0, 1, true, outputPath + "4p{6}.png");
    generate(inputPath + "4{3_4}.ph", 0, 1, true, outputPath + "4{3_4}.png");
    generateRotating(inputPath + "4{3_4}.phr", 2.*Math.PI/3., 40, 0, 0, true, outputPath + "4{3_4}.gif");
    generateRotating(inputPath + "8{3_4}.phr", 2.*Math.PI/3., 40, 0, 0, true, outputPath + "8{3_4}.gif");
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
    generateRotating(inputPath + "12a{5}.phr", 2.*Math.PI/5., 40, 0, 0, true, outputPath + "12a{5}.gif");
    generateRotating(inputPath + "12a1{52}.phr", 2.*Math.PI/5., 40, 0, 0, true, outputPath + "12a1{52}.gif");
    generate(inputPath + "10p{3}.ph", 0, 0, true, outputPath + "10p{3}.png");
    generate(inputPath + "20p{3}.ph", 0, 0, true, outputPath + "20p{3}.png");
    generate(inputPath + "10p{6}.ph", 0, 0, true, outputPath + "10p{6}.png");
    generate(inputPath + "10{3_4}a.ph", 0, 0, true, outputPath + "10{3_4}a.png");
    generate(inputPath + "10{3_4}b.ph", 0, 0, true, outputPath + "10{3_4}b.png");
    generateRotating(inputPath + "20{3_4}.phr", 2.*Math.PI/3., 40, 0, 0, true, outputPath + "20{3_4}.gif");
    generate(inputPath + "20{3_4}.ph", 0, 0, true, outputPath + "20{3_4}.png");
    generate(inputPath + "20{3x3}.ph", 0, 0, true, outputPath + "20{3h2}.png");

    generateDihedralCompounds(inputPath + "Dihedral\\", outputPath + "dihedral\\");
  }

  /**
   * Generates images of the dihedral (prismatic) uniform compounds of uniform polyhedra.
   * @param inputPath Path to read the polyhedra's .ph files.
   * @param outputPath Path to write the rendered polyhedra.
   */
  public static void generateDihedralCompounds(final String inputPath, final String outputPath) throws IOException
  {
    new File(outputPath).mkdir();

    generate(inputPath + "2p{3}.ph", 0, 0, true, outputPath + "2p{3}.png");
    generate(inputPath + "2{4_3}.ph", 0, 0, true, outputPath + "2{4_3}.png");
    generate(inputPath + "2p{5}.ph", 0, 0, true, outputPath + "2p{5}.png");
    generate(inputPath + "2p{52}.ph", 0, 0, true, outputPath + "2p{52}.png");
    generate(inputPath + "3p{3}.ph", 0, 0, true, outputPath + "3p{3}.png");
    generateRotating(inputPath + "2p{3}.phr", 2.*Math.PI, 60, 0, 0, true, outputPath + "2p{3}.gif");
    generateRotating(inputPath + "2{4_3}.phr", 2.*Math.PI, 80, 0, 0, true, outputPath + "2{4_3}.gif");
    generateRotating(inputPath + "2p{5}.phr", 2.*Math.PI, 100, 0, 0, true, outputPath + "2p{5}.gif");
    generateRotating(inputPath + "2p{52}.phr", 2.*Math.PI, 100, 0, 0, true, outputPath + "2p{52}.gif");

    generateRotating(inputPath + "2{3_3}.phr", Math.PI, 40, 0, 0, true, outputPath + "2{3_3}.gif");
    generate(inputPath + "2{3_4}.ph", 0, 0, true, outputPath + "2{3_4}.png");
    generate(inputPath + "2a{4}.ph", 0, 0, true, outputPath + "2a{4}.png");
    generate(inputPath + "2a{5}.ph", 0, 0, true, outputPath + "2a{5}.png");
    generate(inputPath + "2a{52}.ph", 0, 0, true, outputPath + "2a{52}.png");
    generate(inputPath + "2a1{52}.ph", 0, 0, true, outputPath + "2a1{52}.png");
    generate(inputPath + "3{3_3}.ph", 0, 0, true, outputPath + "3{3_3}.png");
    generate(inputPath + "3{3_4}.ph", 0, 0, true, outputPath + "3{3_4}.png");
    generate(inputPath + "4{3_3}.ph", 0, 0, true, outputPath + "4{3_3}.png");
    generate(inputPath + "5{3_3}.ph", 0, 0, true, outputPath + "5{3_3}.png");
    generateRotating(inputPath + "2{3_4}.phr", 2.*Math.PI, 60, 0, 0, true, outputPath + "2{3_4}.gif");
    generateRotating(inputPath + "2a{4}.phr", 2.*Math.PI, 80, 0, 0, true, outputPath + "2a{4}.gif");
    generateRotating(inputPath + "4{3_3}.phr", 2.*Math.PI, 80, 0, 0, true, outputPath + "4{3_3}.gif");
    generateRotating(inputPath + "2a{5}.phr", 2.*Math.PI, 100, 0, 0, true, outputPath + "2a{5}.gif");
    generateRotating(inputPath + "2a{52}.phr", 2.*Math.PI, 100, 0, 0, true, outputPath + "2a{52}.gif");
    generateRotating(inputPath + "2a1{52}.phr", 2.*Math.PI, 100, 0, 0, true, outputPath + "2a1{52}.gif");
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
   * Generates a GIF image of a rotating polyhedron.
   * @param inputFileName Filename to read the polyhedron's .phr file.
   * @param maxAngle Maximum angle of rotation to use, before the cycle starts repeating.
   * @param numSteps Number of animation frames to sweep maxAngle.
   * @param combination Combination within the .ph file to render.
   * @param palette Palette to use for rendering.
   * @param xorCompound Whether to use "alternating fill" for star polygons.
   * @param outputFileName Filename to write the rendered polyhedron.
   */
  public static void generateRotating(
          final String inputFileName,
          final double maxAngle,
          final int numSteps,
          final int combination,
          final int palette,
          final boolean xorCompound,
          final String outputFileName) throws IOException
  {
    final List<RenderedImage> renderedImages = new ArrayList<>();
    for (int step = 0; step < numSteps; step += 1)
      {
        System.out.println(String.format("Processing %s ... (%d/%d)", outputFileName, step, numSteps));
        final PHPolyhedron polyhedron = loadRotatingPolyhedron(inputFileName).apply((.25+step)*maxAngle/numSteps);

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
                = PngFormat.supersampledPngCanvas(1024, 1);
        final Canvas canvas = canvasWithImageSupplier.first();
        final Supplier<? extends RenderedImage> imageSupplier = canvasWithImageSupplier.second();

        Plotting.plot(canvas, projectedShapes, xorCompound, 0.102);
        renderedImages.add(imageSupplier.get());
      }

    try (ImageOutputStream imageOutputStream = new FileImageOutputStream(new File(outputFileName)))
    {
      AnimatedGifFormat.write(imageOutputStream, renderedImages, 50);
    }
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
   * Loads a rotating polyhedron from file.
   * @param fileName File name from which to load.
   * @return Loaded polyhedron.
   */
  public static DoubleFunction<? extends PHPolyhedron> loadRotatingPolyhedron(final String fileName) throws IOException
  {
    final Ref<DoubleFunction<? extends PHPolyhedron>> polyhedron = new Ref<>();
    try (Reader reader = new BufferedReader(new FileReader(fileName)))
      {
        final Ref<Integer> ch = new Ref<>();
        ch.set(reader.read());
        PHPolyhedron.readRotating(polyhedron, reader, ch);
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
