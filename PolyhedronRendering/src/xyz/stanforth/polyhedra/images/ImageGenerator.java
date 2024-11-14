package xyz.stanforth.polyhedra.images;

import xyz.stanforth.polyhedra.rendering.Palette;

import java.io.IOException;

/**
 * End-to-end image generator.
 */
public interface ImageGenerator
{
  /**
   * Prepares to create images in the specified output directory.
   * @param outputPath Path to which rendered polyhedra will be written.
   */
  void prepareDirectory(String outputPath) throws IOException;

  /**
   * Generates an image of a polyhedron.
   * @param inputFileName Filename to read the polyhedron's .ph file.
   * @param combination Combination within the .ph file to render.
   * @param palette Palette to use for rendering.
   * @param xorCompound Whether to use "alternating fill" for star polygons.
   * @param outputFileName Filename to write the rendered polyhedron.
   */
  void generate(
          String inputFileName,
          int combination,
          Palette palette,
          boolean xorCompound,
          String outputFileName) throws IOException;

  /**
   * Generates an animated image of a rotating polyhedron.
   * @param inputFileName Filename to read the polyhedron's .phr file.
   * @param maxAngle Maximum angle of rotation to use, before the cycle starts repeating.
   * @param combination Combination within the .ph file to render.
   * @param palette Palette to use for rendering.
   * @param xorCompound Whether to use "alternating fill" for star polygons.
   * @param outputFileName Filename to write the rendered polyhedron.
   */
  void generateRotating(
          final String inputFileName,
          final double maxAngle,
          int combination,
          Palette palette,
          boolean xorCompound,
          String outputFileName) throws IOException;
}
