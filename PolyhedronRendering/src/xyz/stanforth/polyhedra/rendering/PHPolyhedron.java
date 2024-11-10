package xyz.stanforth.polyhedra.rendering;

import xyz.stanforth.util.Ref;

import java.io.IOException;
import java.io.Reader;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

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

    polyhedron.set(new PHPolyhedron()
    {
      @Override public List<? extends PHTriangle> triangles() { return triangles; }
    });
  }

  /**
   * Parses a rotating polyhedron, consumed from the given reader.
   * @param rotatingPolyhedron Populated on exit with the parsed polyhedron.
   * @param reader Reader.
   * @param ch Maintains the most recent character taken from the reader, or -1 if EOF.
   */
  static void readRotating(final Ref<? super DoubleFunction<? extends PHPolyhedron>> rotatingPolyhedron, final Reader reader, final Ref<Integer> ch) throws IOException
  {
    final List<DoubleFunction<? extends PHTriangle>> triangles = new ArrayList<>();
    Parsing.skipWhitespace(reader, ch);
    while (ch.value() >= 0)
      {
        final Ref<DoubleFunction<? extends PHTriangle>> triangle = new Ref<>();
        PHTriangle.readRotating(triangle, reader, ch);
        triangles.add(triangle.value());
        Parsing.skipWhitespace(reader, ch);
      }

    rotatingPolyhedron.set((DoubleFunction<PHPolyhedron>) (final double angle) -> new PHPolyhedron()
    {
      @Override public List<? extends PHTriangle> triangles() { return new AbstractList<>()
      {
        @Override public PHTriangle get(final int index) { return triangles.get(index).apply(angle); }
        @Override public int size() { return triangles.size(); }
      }; }
    });
  }
}
