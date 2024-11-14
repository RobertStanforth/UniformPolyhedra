package xyz.stanforth.polyhedra.rendering;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Plotting shapes to a canvas.
 */
public final class Plotting
{
  /**
   * Plots the shapes to the given canvas. The canvas corresponds to projected coordinates (±1, ±1).
   * @param canvas Surface upon which to plot.
   * @param shapes Polygonal shapes to plot.
   * @param xorCompound Whether to use alternating-fill for star polygons.
   */
  public static void plot(
          final Canvas canvas,
          final List<? extends Shape> shapes,
          final boolean xorCompound)
  {
    final int width = canvas.width();
    final double scale = (double)width / 2.;
    final int i_origin = width/2;
    final int j_origin = width/2;

    final List<? extends ProjectedShape> projectedShapes = shapes.stream()
            .map(Plotting::projectedShape).toList();

    new Runnable()
    {
      @Override
      public void run()
      {
        final Map<ProjectedShape, ClipStatus> initialShapes = new LinkedHashMap<>();
        for (final ProjectedShape shape : projectedShapes)
          initialShapes.put(shape, ClipStatus.PARTIAL);

        quadTree(0, 0, width, width, initialShapes, 0);

        canvas.close();
      }

      private void quadTree(
              final int i_lo, final int j_lo, final int i_hi, final int j_hi,
              final Map<? extends ProjectedShape, ? extends ClipStatus> currentShapes, final int orientation)
      {
        if (i_lo >= i_hi || j_lo >= j_hi)
          return;

        if (i_hi - i_lo == 1 && j_hi - j_lo == 1)
          {
            quadTreePixel(i_lo, j_lo, currentShapes);
          }
        else
          {
            quadTreeRectangle(i_lo, j_lo, i_hi, j_hi, currentShapes, orientation);
          }
      }

      private void quadTreeRectangle(
              final int i_lo, final int j_lo, final int i_hi, final int j_hi,
              final Map<? extends ProjectedShape, ? extends ClipStatus> currentShapes, final int orientation)
      {
        final double x_lo = (i_lo + .5 - i_origin)/scale;
        final double x_hi = (i_hi - .5 - i_origin)/scale;
        final double y_lo = (j_hi - .5 - j_origin)/scale;
        final double y_hi = (j_lo + .5 - j_origin)/scale;

        final double[] x_corner = {x_lo, x_hi, x_lo, x_hi};
        final double[] y_corner = {y_lo, y_lo, y_hi, y_hi};

        // Run through list of shapes that intersected the previous (larger)
        // rectangle, and determine whether they still intersect the current
        // (smaller) rectangle.
        final Map<ProjectedShape, ClipStatus> visibleShapes = new LinkedHashMap<>();
        for (final Map.Entry<? extends ProjectedShape, ? extends ClipStatus> shapeClipStatus : currentShapes.entrySet())
          {
            final ProjectedShape projectedShape = shapeClipStatus.getKey();
            final ClipStatus clipStatus = shapeClipStatus.getValue();

            final ClipStatus nextClipStatus = clipStatus == ClipStatus.PARTIAL
                    ? clipStatus(xorCompound, x_corner, y_corner, projectedShape) : clipStatus;
            if (nextClipStatus != ClipStatus.UNSEEN)
              visibleShapes.put(projectedShape, nextClipStatus);
          }

        // We have now determined which shapes intersect the rectangle when
        // projected. Next we remove those shapes that are hidden.

        // Find the 'least deep' shape that covers the whole rectangle.
        // Small positive depth values are deep; large positive depth values are shallow.
        double leastDeep = 0.;
        final Map<ProjectedShape, Double> shallowestByShape = new HashMap<>();
        for (final Map.Entry<? extends ProjectedShape, ? extends ClipStatus> shapeClipStatus : visibleShapes.entrySet())
          {
            final ProjectedShape projectedShape = shapeClipStatus.getKey();
            final ClipStatus clipStatus = shapeClipStatus.getValue();

            double deepest = 1./EPS;
            double shallowest = 0.;
            for (int a = 0; a < 4; a += 1)
              {
                final double cornerDepth = projectedShape.depth(x_corner[a], y_corner[a]);
                deepest = Math.min(deepest, cornerDepth);
                shallowest = Math.max(shallowest, cornerDepth);
              }
            if (clipStatus == ClipStatus.FULL || clipStatus == ClipStatus.FULLALT)
              leastDeep = Math.max(leastDeep, deepest);
            shallowestByShape.put(projectedShape, shallowest);
          }

        // Remove shapes that are hidden by the 'least deep' one.
        final Map<ProjectedShape, ClipStatus> nextShapes = new LinkedHashMap<>();
        for (final Map.Entry<? extends ProjectedShape, ? extends ClipStatus> shapeClipStatus : visibleShapes.entrySet())
          {
            final ProjectedShape projectedShape = shapeClipStatus.getKey();
            final ClipStatus clipStatus = shapeClipStatus.getValue();

            if (shallowestByShape.get(projectedShape) >= leastDeep)
              nextShapes.put(projectedShape, clipStatus);
          }

        if (nextShapes.size() > 1 || nextShapes.size() == 1 && nextShapes.values().iterator().next() == ClipStatus.PARTIAL)
          {
            // Subdivide.
            // Recurse over four subrectangles, following a fractal Hilbert curve.
            for (int q = 3; q >= 0; q -= 1)
              {
                int i_c = (i_lo + i_hi) / 2;
                int j_c = (j_lo + j_hi) / 2;
                final int nextOrientation = orientation ^ ((q == 3 ? 2 : 0) | (q == 0 || q == 3 ? 4 : 0));
                switch (((orientation & 3) + q * ((orientation & 4) == 0 ? 1 : -1)) & 3)
                {
                case 0:
                  quadTree(i_lo, j_c, i_c, j_hi, nextShapes, nextOrientation);
                  break;
                case 1:
                  quadTree(i_lo, j_lo, i_c, j_c, nextShapes, nextOrientation);
                  break;
                case 2:
                  quadTree(i_c, j_lo, i_hi, j_c, nextShapes, nextOrientation);
                  break;
                case 3:
                  quadTree(i_c, j_c, i_hi, j_hi, nextShapes, nextOrientation);
                  break;
                }
              }
          }
        else if (nextShapes.size() == 1)
          {
            // Just draw the rectangle.
            final Map.Entry<ProjectedShape, ClipStatus> shapeClipStatus = nextShapes.entrySet().iterator().next();
            final Shape shape = shapeClipStatus.getKey().shape();
            final ClipStatus clipStatus = shapeClipStatus.getValue();

            final int colour = clipStatus == ClipStatus.FULLALT ? shape.colourAlt() : shape.colour();
            canvas.drawRectangle(i_lo, j_lo, i_hi, j_hi, colour);
          }
      }

      private void quadTreePixel(
              final int i, final int j,
              final Map<? extends ProjectedShape, ? extends ClipStatus> currentShapes)
      {
        final double x = (i + .5 - i_origin)/scale;
        final double y = (j + .5 - j_origin)/scale;

        final double[] x_corner = {x};
        final double[] y_corner = {y};

        // Run through list of shapes that intersected the previous (larger)
        // rectangle, and determine whether they contain the current point.
        final Map<ProjectedShape, ClipStatus> visibleShapes = new LinkedHashMap<>();
        for (final Map.Entry<? extends ProjectedShape, ? extends ClipStatus> shapeClipStatus : currentShapes.entrySet())
          {
            final ProjectedShape projectedShape = shapeClipStatus.getKey();
            final ClipStatus clipStatus = shapeClipStatus.getValue();

            final ClipStatus nextClipStatus = clipStatus == ClipStatus.PARTIAL
                    ? clipStatus(xorCompound, x_corner, y_corner, projectedShape) : clipStatus;
            if (nextClipStatus != ClipStatus.UNSEEN)
              visibleShapes.put(projectedShape, nextClipStatus);
          }

        // We have now determined which shapes intersect the rectangle when
        // projected. Next we remove those shapes that are hidden.

        // Find the 'least deep' shape that covers the whole rectangle.
        // Small positive depth values are deep; large positive depth values are shallow.
        double leastDeep = 0.;
        ProjectedShape leastDeepProjectedShape = null;
        ClipStatus leastDeepClipStatus = null;
        for (final Map.Entry<? extends ProjectedShape, ? extends ClipStatus> shapeClipStatus : visibleShapes.entrySet())
          {
            final ProjectedShape projectedShape = shapeClipStatus.getKey();
            final ClipStatus clipStatus = shapeClipStatus.getValue();

            final double depth = projectedShape.depth(x, y);
            if (depth > leastDeep)
              {
                leastDeep = depth;
                leastDeepProjectedShape = projectedShape;
                leastDeepClipStatus = clipStatus;
              }
          }
        if (leastDeepProjectedShape != null && leastDeepClipStatus != null)
          {
            // Draw the pixel.
            final Shape shape = leastDeepProjectedShape.shape();
            final ClipStatus clipStatus = leastDeepClipStatus;

            final int colour = clipStatus == ClipStatus.FULLALT ? shape.colourAlt() : shape.colour();
            canvas.drawPixel(i, j, colour);
          }
      }
    }.run();
  }

  /**
   * Projection of a polygon.
   * The nth edge is stored as the equation  ea_n x + eb_n y = ec_n .
   */
  private interface ProjectedShape
  {
    Shape shape();
    double depth(double x, double y);
    double isInside(int n, double x, double y);
  }

  /**
   * Projects a polygon.
   * @param shape Polygonal shape to project.
   * @return Projected polygon.
   */
  private static ProjectedShape projectedShape(final Shape shape)
  {
    // Determine depth coefficients.
    // Depth is modelled by the equation  z = da x + db y + dc .
    // Solve linear system of equations with Cramer's rule.
    final double[] lhs_a = new double[3];  // First column.
    final double[] lhs_b = new double[3];
    final double[] lhs_c = new double[3];  // Last column.
    final double[] rhs = new double[3];
    for (int i = 0; i < 3; i += 1)
      {
        lhs_a[i] = shape.vertices().get(i).elt(0);
        lhs_b[i] = shape.vertices().get(i).elt(1);
        lhs_c[i] = 1.;
        rhs[i] = shape.vertices().get(i).elt(2);
      }

    final double det = det(lhs_a, lhs_b, lhs_c);
    final double da = det(rhs, lhs_b, lhs_c) / det;
    final double db = det(lhs_a, rhs, lhs_c) / det;
    final double dc = det(lhs_a, lhs_b, rhs) / det;

    // In a compound polygon:
    //         n = number of vertices
    //   winding = winding number, of edges about centre
    //        ni = number of vertices in each component shape
    // since number of component shapes is hcf(n,winding).
    // E.g. for two pentagrams: nv=10, winding=4, hcf=2, ni=5.

    final int nv = shape.vertices().size();
    final int ni = nv / hcf(nv, shape.winding() + shape.windingAlt());
    final double[] ea = new double[nv];
    final double[] eb = new double[nv];
    final double[] ec = new double[nv];
    for (int i = 0; i < nv; i += ni)
      for (int j = 0; j < ni; j += 1)
        {
          // Calculate equation of edge:  ea x + eb y = ec .
          final Vector4 v = shape.vertices().get(i + j);
          final Vector4 w = shape.vertices().get(i + (j + 1) % ni);
          final double s;
          if ((v.elt(2) < 0.) ^ (w.elt(2) < 0.))
            {
              // Reverse orientation of edge as it crosses the viewer's plane.
              s = -1.;
            }
          else
            {
              s = 1.;
            }
          ea[i+j] = s * (+ v.elt(1) - w.elt(1));
          eb[i+j] = s * (- v.elt(0) + w.elt(0));
          ec[i+j] = s * (- v.elt(0)*w.elt(1) + w.elt(0)*v.elt(1));
        }

    return new ProjectedShape()
    {
      @Override public Shape shape() { return shape; }
      @Override public double depth(final double x, final double y) { return da * x + db * y + dc; }
      @Override public double isInside(final int n, final double x, final double y) { return ea[n] * x + eb[n] * y - ec[n]; }
    };
  }

  /**
   * @param a First column.
   * @param b Second column.
   * @param c Third column.
   * @return Determine of the matrix consisting of the given columns.
   */
  private static double det(final double[] a, final double[] b, final double[] c)
  {
    double det = 0.;
    for (int j = 0; j < 3; j += 1)
      det += a[j] * (b[(j+1)%3] * c[(j+2)%3] - b[(j+2)%3] * c[(j+1)%3]);
    return det;
  }

  /**
   * Visibility of a shape when clipped to a certain region.
   */
  private enum ClipStatus
  {
    /**
     * Partially visible.
     */
    PARTIAL,

    /**
     * Not at all visible.
     */
    UNSEEN,

    /**
     * Fully visible.
     */
    FULL,

    /**
     * Fully visible, with the alternative coloured part showing.
     */
    FULLALT
  }

  private static ClipStatus clipStatus(
          final boolean xorCompound,
          final double[] x_corner,
          final double[] y_corner,
          final ProjectedShape projectedShape
  )
  {
    final Shape shape = projectedShape.shape();
    final int nv = shape.vertices().size();
    final int w = shape.winding() + shape.windingAlt();
    // Count the number of edges for which the rectangle is
    // completely outside or inside.
    // Keep separate counts for shapes of the alternative colour.
    int hits = 0;
    int misses = 0;
    int missesAlt = 0;
    for (int vertexIndex = 0; vertexIndex < nv; vertexIndex += 1)
      {
        final boolean alt = (shape.altVertices() & (1<<vertexIndex)) != 0;
        // Determine whether any corner is inside or outside the edge.
        boolean out = false;
        boolean in = false;
        for (int a = 0; a < x_corner.length; a += 1)
          {
            if (projectedShape.isInside(vertexIndex, x_corner[a], y_corner[a]) < 0.)
              out = true;
            else
              in = true;
          }
        if (!out)
          {
            // Complete hit.
            hits += 1;
          }
        if (!in)
          {
            // Complete miss.
            if (alt)
              missesAlt += 1;
            else
              misses += 1;
          }
        // A point is outside the shape provided it is outside
        //   at least w edges, where w is the winding number.
        if (misses + missesAlt >= w)
          return ClipStatus.UNSEEN;
        if (!xorCompound)
          {
            // Here compound shapes are not XORed, so a point is
            // inside the shape if it is inside more than nv-w edges,
            // where w is the winding number.
            if (hits > nv - w)
              return ClipStatus.FULL;
            if (vertexIndex+1 - (misses+missesAlt) > nv-w // can't be UNSEEN
                    && vertexIndex+1 - hits >= w)   // can't be FULL
              return ClipStatus.PARTIAL;
          }
      }
    if (xorCompound)
      {
        // Here compound shapes are XORed, so a point is
        // inside the shape only if the number of edges it is
        // inside is of the correct parity.
        if (misses + missesAlt < w
                && hits + misses + missesAlt == nv)  // no edge is straddled by the rectangle
          {
            if ((w - (misses+missesAlt)) % 2 == 0)
              return ClipStatus.UNSEEN;
            else if ((shape.windingAlt() - missesAlt) % 2 != 0)
              return ClipStatus.FULLALT;
            else
              return ClipStatus.FULL;
          }
      }
    return ClipStatus.PARTIAL;
  }

  /**
   * @param a First argument.
   * @param b Second argument.
   * @return Highest common factor of the given arguments.
   */
  private static int hcf(final int a, final int b)
  {
    return b != 0 ? hcf(b,a%b) : a;
  }

  private static final double EPS = 1.e-7;

  private Plotting() {}
}
