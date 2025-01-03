package xyz.stanforth.polyhedra.rendering;

import xyz.stanforth.util.Ref;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Assembles triangles into a symmetric "world" of polygonal shapes.
 */
public final class WorldBuilding
{
  /**
   * Generates a scene of the specified components of the given polyhedron.
   * @param polyhedron Polyhedron to construct as a scene.
   * @param components Bitset indicating the components of the polyhedron to use.
   * @param palette Palette.
   * @return Generated scene.
   */
  public static List<? extends Shape> generateWorld(final Polyhedron polyhedron, final int components, final Palette palette)
  {
    final List<Triangle> sceneTriangles = new ArrayList<>();
    for (final Triangle triangle : polyhedron.triangles())
      {
        if (Integer.bitCount(triangle.components() & components) % 2 != 0)
          {
            compileTriangleSet(sceneTriangles, triangle);
          }
      }

    return gatherShapes(sceneTriangles, palette);
  }

  /**
   * Replicates the triangle over its symmetry group.
   * @param sceneTriangles Populated on exit with the replicated triangles.
   * @param triangle Triangle to replicate.
   */
  private static void compileTriangleSet(final List<? super Triangle> sceneTriangles, final Triangle triangle)
  {
    if (triangle.symmetryGroup() < 0)
      {
        // No symmetry.
        compileTriangle(sceneTriangles, Transform.identity(), triangle);
      }
    else if (triangle.symmetryGroup() == 0 || triangle.symmetryGroup() == 2)
      {
        // Cuboctahedral or tetrahedral symmetry.
        Transform transform3 = Transform.identity();
        for (int n3 = 0; n3 < 3; n3 += 1)
          {
            Transform transform4 = Transform.identity();
            for (int n4 = 0; n4 < 4; n4 += 1)
              {
                if (triangle.symmetryGroup() == 0 || n4 % 2 == 0)
                  {
                    compileTriangle(sceneTriangles, Transform.compose(
                            transform4, transform3), triangle);
                    compileTriangle(sceneTriangles, Transform.compose(
                            transform4, Transform.compose(
                                    cuboctahedral3, Transform.compose(
                                            cuboctahedral4, Transform.compose(
                                                    cuboctahedral4, transform3)))), triangle);
                  }
                transform4 = Transform.compose(cuboctahedral4, transform4);
              }
            transform3 = Transform.compose(cuboctahedral3, transform3);
          }
      }
    else if (triangle.symmetryGroup() == 1)
      {
        // Icosidodecahedral symmetry.
        Transform transform3 = Transform.identity();
        for (int n3 = 0; n3 < 3; n3 += 1)
          {
            Transform transform5 = Transform.identity();
            for (int n5 = 0; n5 < 5; n5 += 1)
              {
                compileTriangle(sceneTriangles, Transform.compose(
                        transform5, transform3), triangle);
                compileTriangle(sceneTriangles, Transform.compose(
                        transform5, Transform.compose(
                                icosidodecahedral3, Transform.compose(
                                        icosidodecahedral5, transform3))), triangle);
                compileTriangle(sceneTriangles, Transform.compose(
                        transform5, Transform.compose(
                                icosidodecahedral3, Transform.compose(
                                        icosidodecahedral5, Transform.compose(
                                                icosidodecahedral5, transform3)))), triangle);
                compileTriangle(sceneTriangles, Transform.compose(
                        transform5, Transform.compose(
                                icosidodecahedral3, Transform.compose(
                                        icosidodecahedral5, Transform.compose(
                                                icosidodecahedral3, Transform.compose(
                                                        icosidodecahedral5, Transform.compose(
                                                                icosidodecahedral5, transform3)))))), triangle);
                transform5 = Transform.compose(icosidodecahedral5, transform5);
              }
            transform3 = Transform.compose(icosidodecahedral3, transform3);
          }
      }
    else if (triangle.symmetryGroup() <= Triangle.MAX_PRISM)
      {
        // Dihedral symmetry.
        final Transform dihedral2 = dihedral2(triangle.symmetryGroup());
        final Transform dihedralN = dihedralN(triangle.symmetryGroup());
        Transform transform = Transform.identity();
        for (int n2 = 0; n2 < 2; n2 += 1)
          {
            for (int n = 0; n < triangle.symmetryGroup(); n += 1)
              {
                compileTriangle(sceneTriangles, transform, triangle);
                transform = Transform.compose(dihedralN, transform);
              }
            transform = Transform.compose(dihedral2, transform);
          }
      }
    else
      {
        throw new RuntimeException();
      }
  }

  /**
   * Collects a transformed replica of the triangle.
   * @param sceneTriangles Populated on exit with the replicated triangles.
   * @param transform Symmetry transform to apply.
   * @param triangle Triangle to replicate.
   */
  private static void compileTriangle(final List<? super Triangle> sceneTriangles, final Transform transform, final Triangle triangle)
  {
    // Create front and back, as separate triangles.
    for (int b = 0; b < 2; b += 1)
      {
        // Transform the vertices.
        final List<Vector4> vertices = new ArrayList<>();
        for (int n = 0; n < 3; n += 1)
          vertices.add(transform.apply(triangle.vertices().get(b == 0 || n == 2 ? n : 1-n)));

        final int colourIndex = b == 0 ? triangle.frontColourIndex() : triangle.backColourIndex();
        sceneTriangles.add(new Triangle()
        {
          @Override public List<? extends Vector4> vertices() { return vertices; }
          @Override public int symmetryGroup() { return triangle.symmetryGroup(); }
          @Override public int frontColourIndex() { return transform.apply(colourIndex); }
          @Override public int backColourIndex() { throw new UnsupportedOperationException(); }
          @Override public boolean isCompound() { return triangle.isCompound(); }
          @Override public boolean alternatesColours() { return triangle.alternatesColours(); }
          @Override public int components() { throw new UnsupportedOperationException(); }
        });
      }
  }

  /**
   * Rotation by one third turn about a hexahedral vertex.
   */
  private static final Transform cuboctahedral3 = Transform.of(
          new double[][] {
                  {0., 1., 0., 0.},
                  {0., 0., 1., 0.},
                  {1., 0., 0., 0.},
                  {0., 0., 0., 1.},
          },
          new int[] {0,2,3,1, 6,4,5, 7,8, 9,10, 11, 12,13,14,
                  15,16,17,18, 21,19,20, 22,23,24}
  );

  /**
   * Rotation by one quarter turn about z axis (an octahedral vertex).
   */
  private static final Transform cuboctahedral4 = Transform.of(
          new double[][] {
                  {0.,-1., 0., 0.},
                  {1., 0., 0., 0.},
                  {0., 0., 1., 0.},
                  {0., 0., 0., 1.},
          },
          new int[] {1,2,3,0, 4,6,5, 8,7, 9,10, 11, 12,13,14,
                  15,16,17,18, 19,21,20, 22,23,24}
  );

  /**
   * Rotation by one third turn about a dodecahedral vertex.
   */
  private static final Transform icosidodecahedral3 = Transform.of(
          new double[][] {
                  {-.5         , .8090169944,-.3090169944, 0.},
                  {-.8090169944,-.3090169944, .5         , 0.},
                  { .3090169944, .5         , .8090169944, 0.},
                  { 0.         , 0.         , 0.         , 1.},
          },
          new int[] {0,2,4,3,1, 7,5,6,9,10,8, 11, 12,13,14,
                  15,20,23,21,16,19,22,18,24,17}
  );

  /**
   * Rotation by one fifth turn about an icosahedral vertex.
   */
  private static final Transform icosidodecahedral5 = Transform.of(
          new double[][] {
                  { .5         ,-.8090169944,-.3090169944, 0.},
                  { .8090169944, .3090169944, .5         , 0.},
                  {-.3090169944,-.5         , .8090169944, 0.},
                  { 0.         , 0.         , 0.         , 1.},
          },
          new int[] {1,2,3,4,0, 5,7,8,9,10,6, 11, 12,13,14,
                  16,17,18,19,15,21,22,23,24,20}
  );

  /**
   * Rotation by a half turn about a prismatic square face.
   * @param symmetryGroup Degree of the prism; must be at least 3.
   * @return Half-turn transform.
   */
  private static Transform dihedral2(final int symmetryGroup)
  {
    final int[] colours = new int[25];
    for (int j = 0; j < colours.length; j += 1)
      colours[j] = j;
    // Sequence of colours with period N.
    for (int j = 0; j < symmetryGroup; j += 1)
      colours[j] = (symmetryGroup - j) % symmetryGroup;
    // Alternating pair of colours.
    colours[15] = 18;
    colours[18] = 15;
    if (symmetryGroup % 2 == 0)
      {
        // Alternative sequence of colours with period N/2.
        for (int j = 0; j < symmetryGroup / 2; j += 1)
          colours[20 + j] = 20 + (symmetryGroup / 2 - j) % (symmetryGroup / 2);
      }

    return Transform.of(
            new double[][] {
                    {1., 0., 0., 0.},
                    {0.,-1., 0., 0.},
                    {0., 0.,-1., 0.},
                    {0., 0., 0., 1.},
            },
            colours
    );
  }

  /**
   * Rotation by a 1/N turn about the z axis, where N is the degree of the prism.
   * @param symmetryGroup Degree of the prism; must be at least 3.
   * @return N-fold rotation transform.
   */
  private static Transform dihedralN(final int symmetryGroup)
  {
    final int[] colours = new int[25];
    for (int j = 0; j < colours.length; j += 1)
      colours[j] = j;
    // Sequence of colours with period N.
    for (int j = 0; j < symmetryGroup; j += 1)
      colours[j] = (j + 1) % symmetryGroup;
    // Alternating pair of colours.
    colours[15] = 15;
    colours[18] = 18;
    if (symmetryGroup % 2 == 0)
      {
        // Alternative sequence of colours with period N/2.
        for (int j = 0; j < symmetryGroup / 2; j += 1)
          colours[20 + j] = 20 + (j + 1) % (symmetryGroup / 2);
      }

    final double c = Math.cos(2.*Math.PI/symmetryGroup);
    final double s = Math.sin(2.*Math.PI/symmetryGroup);
    return Transform.of(
            new double[][] {
                    { c ,-s , 0., 0.},
                    { s , c , 0., 0.},
                    { 0., 0., 1., 0.},
                    { 0., 0., 0., 1.},
            },
            colours
    );
  }

  private static List<? extends Shape> gatherShapes(final List<? extends Triangle> sceneTriangles, final Palette palette)
  {
    final List<Shape> shapes = new ArrayList<>();
    final Set<Triangle> consumedTriangles = new HashSet<>();
    for (final Triangle sceneTriangle : sceneTriangles)
      {
        if (!consumedTriangles.contains(sceneTriangle))
          {
            // Not already identified as part or all of a PHShape.
            if (sceneTriangle.isCompound())
              {
                final List<Vector4> vertices = new ArrayList<>();
                final int[] altVertices = {0};
                final ColourAlternator colourIndexAlternator = colourAlternator(sceneTriangle.alternatesColours());

                // Outer loop - over polygons in the shape.
                double angle = 0.;
                double angleAlt = 0.;
                for (Triangle polygonTriangle = sceneTriangle; polygonTriangle != null; polygonTriangle = anotherTriangle(
                        sceneTriangles, consumedTriangles, polygonTriangle))
                  {
                    colourIndexAlternator.register(polygonTriangle.frontColourIndex());

                    // Inner loop - over triangles in polygon.
                    for (Triangle currentTriangle = polygonTriangle; currentTriangle != null; currentTriangle = nextTriangleInPolygon(
                            sceneTriangles, consumedTriangles, polygonTriangle.vertices().get(0), currentTriangle))
                      {
                        final int curVert = vertices.size();
                        consumedTriangles.add(currentTriangle);
                        vertices.add(currentTriangle.vertices().get(0));
                        if (colourIndexAlternator.isAlternateColour(polygonTriangle.frontColourIndex()))
                          altVertices[0] |= 1 << curVert;
                        if (currentTriangle.frontColourIndex() != polygonTriangle.frontColourIndex())
                          throw new RuntimeException("Colour mismatch");

                        if (colourIndexAlternator.isAlternateColour(polygonTriangle.frontColourIndex()))
                          angleAlt += angle(currentTriangle);
                        else
                          angle += angle(currentTriangle);
                      }
                  }

                final int winding = (int)(angle/(2.*Math.PI) + .5);
                final int windingAlt = (int)(angleAlt/(2.*Math.PI) + .5);
                shapes.add(new Shape()
                {
                  @Override public List<? extends Vector4> vertices() { return vertices; }
                  @Override public int winding() { return winding; }
                  @Override public int windingAlt() { return windingAlt; }
                  @Override public int colour() { return palette.entry(sceneTriangle.symmetryGroup(), colourIndexAlternator.colour(false)); }
                  @Override public int colourAlt() { return sceneTriangle.alternatesColours()
                          ? palette.entry(sceneTriangle.symmetryGroup(), colourIndexAlternator.colour(true))
                          : palette.background(); }
                  @Override public int altVertices() { return altVertices[0]; }
                });
              }
            else
              {
                // Not a compound triangle.
                consumedTriangles.add(sceneTriangle);
                shapes.add(new Shape()
                {
                  @Override public List<? extends Vector4> vertices() { return sceneTriangle.vertices(); }
                  @Override public int winding() { return 1; }
                  @Override public int windingAlt() { return 0; }
                  @Override public int colour() { return palette.entry(sceneTriangle.symmetryGroup(), sceneTriangle.frontColourIndex()); }
                  @Override public int colourAlt() { return palette.background(); }
                  @Override public int altVertices() { return 0; }
                });
              }
          }

      }
    return shapes;
  }

  /**
   * Finds another triangle belonging to the same compound shape as the current one.
   * @param sceneTriangles All triangles in the scene.
   * @param consumedTriangles Triangles no longer available.
   * @param polygonTriangle Triangle whose whose centre and plane are to be matched.
   * @return An available triangle with the same centre and plane as given, or {@code null} if there are none left.
   */
  private static Triangle anotherTriangle(
          final List<? extends Triangle> sceneTriangles,
          final Set<? extends Triangle> consumedTriangles,
          final Triangle polygonTriangle)
  {
    for (final Triangle nextTriangle : sceneTriangles)
      if (!consumedTriangles.contains(nextTriangle)
              && nextTriangle.isCompound()
              && nextTriangle.alternatesColours() == polygonTriangle.alternatesColours())
        {
          // Match centre and normal.
          if (dist2(nextTriangle.vertices().get(2), polygonTriangle.vertices().get(2)) < EPS
                  && dist2(normal(nextTriangle), normal(polygonTriangle)) < EPS)
            {
              return nextTriangle;
            }
        }
    return null;
  }

  /**
   * Finds the next triangle belonging to the same compound shape as the current one.
   * @param sceneTriangles All triangles in the scene.
   * @param consumedTriangles Triangles no longer available.
   * @param polygonBeginVertex First vertex of the polygon, for closure detection.
   * @param currentTriangle Triangle for which a neighbour is to be found.
   * @return An available triangle with the same centre and plane as given, matching another vertex to continue the sweep,
   *    or {@code null} if the polygon has just been closed.
   */
  private static Triangle nextTriangleInPolygon(
          final List<? extends Triangle> sceneTriangles,
          final Set<? extends Triangle> consumedTriangles,
          final Vector4 polygonBeginVertex,
          final Triangle currentTriangle)
  {
    if (dist2(polygonBeginVertex, currentTriangle.vertices().get(1)) < EPS)
      {
        // Polygon closed.
        return null;
      }

    for (final Triangle nextTriangle : sceneTriangles)
      if (!consumedTriangles.contains(nextTriangle)
              && nextTriangle.isCompound()
              && nextTriangle.alternatesColours() == currentTriangle.alternatesColours())
        {
          if (dist2(nextTriangle.vertices().get(2), currentTriangle.vertices().get(2)) < EPS
                  && dist2(nextTriangle.vertices().get(0), currentTriangle.vertices().get(1)) < EPS
                  && dist2(normal(nextTriangle), normal(currentTriangle)) < EPS)
            {
              // Neighbour found.
              return nextTriangle;
            }
        }
    throw new RuntimeException("Polygon not closed.");
  }

  /**
   * Registers the principal and alternate colour of a compound polygon.
   */
  private interface ColourAlternator
  {
    /**
     * @param alt Whether to consider the alternate colour of the polygon (assumed to use alternating colours).
     * @return Principal colour of the polygon.
     */
    int colour(boolean alt);

    /**
     * Registers the given colour, if not already registered.
     * If another colour has already been registered as the principal colour, registers it as the alternate colour,
     * or raises an error if the polygon doesn't use alternating colours.
     * If other colours have already been registered for both, raises an error.
     * @param colour Colour to register.
     */
    void register(int colour);

    /**
     * @param colour Colour to look up.
     * @return Whether the given colour is the alternate colour of the polygon.
     */
    boolean isAlternateColour(int colour);
  }

  /**
   * @param isAlternating Whether the polygon uses alternating colours.
   * @return Registry of principal and alternate colour.
   */
  private static ColourAlternator colourAlternator(final boolean isAlternating)
  {
    return new ColourAlternator()
    {
      private final Ref<Integer> colour = new Ref<>();
      private final Ref<Integer> colourAlt = new Ref<>();

      @Override public int colour(final boolean alt) { return alt ? colourAlt.value() : colour.value(); }

      @Override
      public void register(final int colour)
      {
        if (!this.colour.isSet())
          {
            // Set the compound's colour to be this triangle's colour.
            this.colour.set(colour);
          }
        else if (colour == this.colour.value())
          {
            // This triangle's colour matches the compound's colour.
          }
        else if (isAlternating && !colourAlt.isSet())
          {
            // Set the compound's alternative colour to be this triangle's colour.
            this.colourAlt.set(colour);
          }
        else if (isAlternating && colour == this.colourAlt.value())
          {
            // This triangle's colour matches the compound's alternative colour.
          }
        else
          {
            throw new RuntimeException();
          }
      }

      @Override
      public boolean isAlternateColour(final int colour)
      {
        if (colour == this.colour.value())
          return false;
        else if (isAlternating && colour == this.colourAlt.value())
          return true;
        else
          throw new RuntimeException();
      }
    };
  }

  /**
   * @param a First vector.
   * @param b Second vector.
   * @return Squared Euclidean distance (in 3D) between the two vectors.
   */
  private static double dist2(final Vector4 a, final Vector4 b)
  {
    double sdd = 0.;
    for (int j = 0; j < 3; j += 1)
      {
        final double dj = a.elt(j) - b.elt(j);
        sdd += dj * dj;
      }
    return sdd;
  }

  /**
   * @param triangle Triangle.
   * @return Angle swept by the triangle about its last vertex.
   */
  private static double angle(final Triangle triangle)
  {
    double saa = 0.;
    double sab = 0.;
    double sbb = 0.;
    for (int j = 0; j < 3; j += 1)
      {
        final double aj = triangle.vertices().get(0).elt(j) - triangle.vertices().get(2).elt(j);
        final double bj = triangle.vertices().get(1).elt(j) - triangle.vertices().get(2).elt(j);
        saa += aj * aj;
        sab += aj * bj;
        sbb += bj * bj;
      }
    return Math.acos(sab / Math.sqrt(saa * sbb));
  }

  /**
   * @param triangle Triangle.
   * @return Normal vector to the triangle, computed as (v_0 - v_2)×(v_1 - v_2).
   */
  private static Vector4 normal(final Triangle triangle)
  {
    final double[] vab = new double[4];
    for (int j = 0; j < 3; j += 1)
      {
        final double ak = triangle.vertices().get(0).elt((j+1)%3) - triangle.vertices().get(2).elt((j+1)%3);
        final double al = triangle.vertices().get(0).elt((j+2)%3) - triangle.vertices().get(2).elt((j+2)%3);
        final double bk = triangle.vertices().get(1).elt((j+1)%3) - triangle.vertices().get(2).elt((j+1)%3);
        final double bl = triangle.vertices().get(1).elt((j+2)%3) - triangle.vertices().get(2).elt((j+2)%3);
        vab[j] = ak*bl - al*bk;
      }
    vab[3] = 0.;
    return Vector4.of(vab);
  }

  private static final double EPS = 1.e-6;

  private WorldBuilding() {}
}
