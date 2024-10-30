package xyz.stanforth.polyhedra.plotting;

import xyz.stanforth.util.Ref;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Polyhedron, as a collection of constituent triangles.
 */
public interface PHPolyhedron
{
  /**
   * @return Constituent triangles.
   */
  List<? extends PHTriangle> triangles();

  /**
   * Parses a polyhedron, consumed from the given reader.
   * @param polyhedron Populated on exit with the parsed polyhedron.
   * @param reader Reader.
   * @param ch Maintains the most recent character taken from the reader, or -1 if EOF.
   */
  static void read(final Ref<? super PHPolyhedron> polyhedron, final Reader reader, final Ref<Integer> ch) throws IOException
  {
    final List<PHTriangle> triangles = new ArrayList<>();
    Parsing.skipWhitespace(reader, ch);
    while (ch.value() >= 0)
      {
        final Ref<PHTriangle> triangle = new Ref<>();
        PHTriangle.read(triangle, reader, ch);
        triangles.add(triangle.value());
        Parsing.skipWhitespace(reader, ch);
      }

    polyhedron.set(new PHPolyhedron() {
      @Override public List<? extends PHTriangle> triangles() { return triangles; }
    });
  }
}
