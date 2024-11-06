package xyz.stanforth.polyhedra.rendering;

import xyz.stanforth.util.Ref;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Triangle of a polyhedron.
 */
public interface PHTriangle
{
  /**
   * @return Three vertices of the triangle, each as [x, y, z] coordinates.
   */
  List<? extends Vector4> vertices();

  /**
   * @return Symmetry group: cuboctahedral (0), icosidodecahedral (1), tetrahedral (2), or prismatic (>=3).
   */
  int symmetryGroup();

  /**
   * @return Colour (palette index) for face's front.
   */
  int frontColour();

  /**
   * @return Colour (palette index) for face's back.
   */
  int backColour();

  /**
   * @return Whether the triangle combines with others to form a larger polygon centred on the triangle's third vertex.
   */
  boolean isCompound();

  /**
   * @return Whether the front and back colours alternate, in the case of a compound polygon drawn in XOR mode.
   */
  boolean alternatesColours();

  /**
   * @return Bitset denoting which components include this face.
   */
  int components();

  int MAX_PRISM = 11;

  /**
   * Parses a triangle, consumed from the given reader.
   * @param triangle Populated on exit with the parsed triangle.
   * @param reader Reader.
   * @param ch Maintains the most recent character taken from the reader, or -1 if EOF.
   */
  static void read(final Ref<? super PHTriangle> triangle, final Reader reader, final Ref<Integer> ch) throws IOException
  {
    final List<Vector4> vertices = new ArrayList<>();
    for (int n = 0; n < 3; n += 1)
      {
        final double[] vertex = new double[4];
        for (int j = 0; j < 3; j += 1)
          {
            final Ref<Double> coordinate = new Ref<>();
            Parsing.readDouble(coordinate, reader, ch);
            vertex[j] = coordinate.value();
          }
        vertex[3] = 1.;
        vertices.add(Vector4.of(vertex));
      }

    final Ref<Integer> symmetryGroup = new Ref<>();
    Parsing.readInt(symmetryGroup, reader, ch);

    final Ref<Integer> frontColour = new Ref<>();
    Parsing.readInt(frontColour, reader, ch);
    final Ref<Integer> backColour = new Ref<>();
    Parsing.readInt(backColour, reader, ch);

    final Ref<Integer> compound = new Ref<>();
    Parsing.readInt(compound, reader, ch);

    final Ref<Integer> components = new Ref<>();
    Parsing.readInt(components, reader, ch);

    triangle.set(new PHTriangle()
    {
      @Override public List<? extends Vector4> vertices() { return vertices; }
      @Override public int symmetryGroup() { return symmetryGroup.value(); }
      @Override public int frontColour() { return frontColour.value(); }
      @Override public int backColour() { return backColour.value(); }
      @Override public boolean isCompound() { return compound.value() != 0; }
      @Override public boolean alternatesColours() { return compound.value() == 2; }
      @Override public int components() { return components.value(); }
    });
  }
}
