package xyz.stanforth.polyhedra.plotting;

/**
 * Surface on which pixels and rectangles can be drawn.
 */
public interface Canvas
{
  /**
   * @return width (and height) of the canvas in pixels.
   */
  int width();

  /**
   * Plots a pixel.
   * @param i Column of the pixel, counting from the left.
   * @param j Row of the pixel, counting from the top.
   * @param colour RGB colour to set the pixel.
   */
  void drawPixel(int i, int j, int colour);

  /**
   * Plots a rectangle.
   * @param i_lo First column of the rectangle, counting from the left.
   * @param j_lo First row of the rectangle, counting from the top.
   * @param i_hi One-past-last column of the rectangle, counting from the left.
   * @param j_hi One-past-last row of the rectangle, counting from the top.
   * @param colour RGB colour to set the pixel.
   */
  void drawRectangle(int i_lo, int j_lo, int i_hi, int j_hi, int colour);

  /**
   * Announces that nothing more will be drawn to the canvas.
   */
  void close();
}
