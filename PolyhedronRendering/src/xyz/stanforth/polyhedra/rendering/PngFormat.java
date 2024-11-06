package xyz.stanforth.polyhedra.rendering;

import xyz.stanforth.util.Pair;
import xyz.stanforth.util.Pairs;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.util.function.Supplier;

/**
 * Canvases for plotting to PNG.
 */
public final class PngFormat
{
  /**
   * Sets up a canvas for plotting the polyhedron to a PNG image.
   * @param width Image width and height.
   * @return Canvas on which to plot the polyhedron, and a callable to receive the resulting PNG image.
   */
  public static Pair<? extends Canvas, ? extends Supplier<? extends RenderedImage>> pngCanvas(final int width)
  {
    final int[] pixels = new int[width*width*3];
    final Canvas canvas = new Canvas()
    {
      @Override public int width() { return width; }

      @Override
      public void drawPixel(final int i, final int j, final int colour)
      {
        for (int k = 0; k < 3; k += 1)
          {
            final int colourCpt = (colour >> ((2 - k) * 8)) & 255;
            pixels[((width-1-j)*width + i)*3 + k] = colourCpt;
          }
      }

      @Override
      public void drawRectangle(final int i_lo, final int j_lo, final int i_hi, final int j_hi, final int colour)
      {
        for (int i = i_lo; i < i_hi; i += 1)
          for (int j = j_lo; j < j_hi; j += 1)
            drawPixel(i, j, colour);
      }

      @Override
      public void close()
      {
      }
    };

    final Supplier<? extends RenderedImage> imageSupplier = () -> {
      final BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
      final WritableRaster raster = (WritableRaster)image.getData();
      raster.setPixels(0, 0, width, width, pixels);
      image.setData(raster);
      return image;
    };

    return Pairs.of(canvas, imageSupplier);
  }

  /**
   * Sets up a canvas for plotting the polyhedron to a PNG image.
   * The image is rendered at a higher resolution then averaged over each physical pixel.
   * @param width Image width and height.
   * @param d Super-sampling factor.
   * @return Canvas on which to plot the polyhedron, and a callable to receive the resulting PNG image.
   */
  public static Pair<? extends Canvas, ? extends Supplier<? extends RenderedImage>> supersampledPngCanvas(
          final int width, final int d)
  {
    final int[] pixels = new int[width*width*3];
    final Canvas canvas = new Canvas()
    {
      @Override public int width() { return width * d; }

      @Override
      public void drawPixel(final int i, final int j, final int colour)
      {
        for (int k = 0; k < 3; k += 1)
          {
            final int colourCpt = (colour >> ((2 - k) * 8)) & 255;
            pixels[((width-1 - j/d)*width + i/d)*3 + k] += colourCpt;
          }
      }

      @Override
      public void drawRectangle(final int i_lo, final int j_lo, final int i_hi, final int j_hi, final int colour)
      {
        final int[] colourCpts = new int[3];
        for (int k = 0; k < 3; k += 1)
          colourCpts[k] = (colour >> ((2 - k) * 8)) & 255;

        int i = i_lo;

        while (i % d != 0)
          {
            int j = j_lo;

            while (j % d != 0)
              {
                for (int k = 0; k < 3; k += 1)
                  pixels[((width-1 - j/d)*width + i/d)*3 + k] += colourCpts[k];
                j += 1;
              }
            while (j/d < j_hi/d)
              {
                for (int k = 0; k < 3; k += 1)
                  pixels[((width-1 - j/d)*width + i/d)*3 + k] += d*colourCpts[k];
                j += d;
              }
            while (j < j_hi)
              {
                for (int k = 0; k < 3; k += 1)
                  pixels[((width-1 - j/d)*width + i/d)*3 + k] += colourCpts[k];
                j += 1;
              }

            i += 1;
          }

        while (i/d < i_hi/d)
          {
            int j = j_lo;

            while (j % d != 0)
              {
                for (int k = 0; k < 3; k += 1)
                  pixels[((width-1 - j/d)*width + i/d)*3 + k] += d*colourCpts[k];
                j += 1;
              }
            while (j/d < j_hi/d)
              {
                for (int k = 0; k < 3; k += 1)
                  pixels[((width-1 - j/d)*width + i/d)*3 + k] += d*d*colourCpts[k];
                j += d;
              }
            while (j < j_hi)
              {
                for (int k = 0; k < 3; k += 1)
                  pixels[((width-1 - j/d)*width + i/d)*3 + k] += d*colourCpts[k];
                j += 1;
              }

            i += d;
          }

        while (i < i_hi)
          {
            int j = j_lo;

            while (j % d != 0)
              {
                for (int k = 0; k < 3; k += 1)
                  pixels[((width-1 - j/d)*width + i/d)*3 + k] += colourCpts[k];
                j += 1;
              }
            while (j/d < j_hi/d)
              {
                for (int k = 0; k < 3; k += 1)
                  pixels[((width-1 - j/d)*width + i/d)*3 + k] += d*colourCpts[k];
                j += d;
              }
            while (j < j_hi)
              {
                for (int k = 0; k < 3; k += 1)
                  pixels[((width-1 - j/d)*width + i/d)*3 + k] += colourCpts[k];
                j += 1;
              }

            i += 1;
          }
      }

      @Override
      public void close()
      {
        for (int k = 0; k < pixels.length; k += 1)
          pixels[k] /= d*d;
      }
    };

    final Supplier<? extends RenderedImage> imageSupplier = () -> {
      final BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
      final WritableRaster raster = (WritableRaster)image.getData();
      raster.setPixels(0, 0, width, width, pixels);
      image.setData(raster);
      return image;
    };

    return Pairs.of(canvas, imageSupplier);
  }

  private PngFormat() {}
}
