package xyz.stanforth.polyhedra.planar;

import java.io.*;
import java.util.*;

import xyz.stanforth.algebra.*;
import xyz.stanforth.polyhedra.common.FaceCandidate;
import xyz.stanforth.polyhedra.common.PolygonSearching;
import xyz.stanforth.polyhedra.common.Reporting;
import xyz.stanforth.polyhedra.common.Session;
import xyz.stanforth.util.Function;
import xyz.stanforth.util.Functions;

public final class PlanarChecking
{
  public static <T> void checkAllPlanar(final Session<T> session, final PlanarSession<T> planarSession, final PrintStream out)
  {
    for (final PlanarEquation<T> equation1 : planarSession.equations())
      for (final PlanarEquation<T> equation0 : planarSession.equations())
        if (equation0.index() < equation1.index())
          checkPlanar(session, planarSession, out, equation0, equation1);
  }

  public static <T> void checkPlanar(final Session<T> session, final PlanarSession<T> planarSession, final PrintStream out,
          final PlanarEquation<T> equation0, final PlanarEquation<T> equation1)
  {
    //out.println("axes:   " + equation0.name() + "   " + equation1.name());
    
    final Domain<T> ring = session.axes().ring();
    
    final T axx = ring.subtract(equation0.axx(), equation1.axx());
    final T a2xz = ring.subtract(equation0.a2xz(), equation1.a2xz());
    final T azz = ring.subtract(equation0.azz(), equation1.azz());
    // Discriminant.
    final T d = ring.subtract(ring.multiply(a2xz, a2xz), ring.multiply(axx, azz));
    final T s = ring.squareRoot(d);
    if (ring.equals(d, ring.zero()) || !ring.equals(s, ring.zero()))
      {
        // Roots in the ring's field of fractions.
        final Function<? super T, ? extends T> converter = Functions.identity();
        final OrderedField<Ratio<? extends T>> field = Ratios.field(ring);
        final Function<? super T, ? extends Ratio<? extends T>> ratioConverter = Ratios.converter(ring);
        if (ring.equals(axx, ring.zero()))
          {
            // Single root.
            if (!ring.equals(a2xz, ring.zero()))
              processVertex(session, planarSession, out, equation0, ring, converter, field, ratioConverter,
                    Ratios.of(ring, ring.negate(azz), ring.add(a2xz, a2xz)));
          }
        else if (ring.equals(d, ring.zero()))
          {
            // Repeated roots.
            processVertex(session, planarSession, out, equation0, ring, converter, field, ratioConverter,
                    Ratios.of(ring, ring.negate(a2xz), axx));
          }
        else
          {
            // Distinct roots.
            processVertex(session, planarSession, out, equation0, ring, converter, field, ratioConverter,
                    Ratios.of(ring, ring.add(ring.negate(a2xz), s), axx));
            processVertex(session, planarSession, out, equation0, ring, converter, field, ratioConverter,
                    Ratios.of(ring, ring.subtract(ring.negate(a2xz), s), axx));
          }
      }
    else if (ring.compare(d, ring.zero()) > 0)
      {
        // Roots in a quadratic splitting field.
        final OrderedRing<Quad<? extends T>> quadRing = Quads.ring(ring, d);
        final Function<? super T, ? extends Quad<? extends T>> converter = Quads.converter(ring, d);
        final OrderedField<Ratio<? extends Quad<? extends T>>> field = Ratios.fieldUnreduced(quadRing);
        final Function<? super Quad<? extends T>, ? extends Ratio<? extends Quad<? extends T>>> ratioConverter = Ratios.converterUnreduced(quadRing);
        processVertex(session, planarSession, out, equation0, quadRing, converter, field, ratioConverter, Ratios.unreduced(quadRing,
                    Quads.of(ring, d, ring.negate(a2xz), ring.valueOf(+1)),
                    Quads.of(ring, d, axx)));
        processVertex(session, planarSession, out, equation0, quadRing, converter, field, ratioConverter, Ratios.unreduced(quadRing,
                    Quads.of(ring, d, ring.negate(a2xz), ring.valueOf(-1)),
                    Quads.of(ring, d, axx)));
      }
  }

  /**
   * @param rxz
   *            ratio x/z
   */
  private static <T, U> void processVertex(final Session<T> session, final PlanarSession<T> planarSession, final PrintStream out, final PlanarEquation<T> equation0,
          final OrderedRing<U> ring, final Function<? super T, ? extends U> converter,
          final OrderedField<Ratio<? extends U>> field, final Function<? super U, ? extends Ratio<? extends U>> ratioConverter,
          final Ratio<? extends U> rxz)
  {
    if (planarSession.isXZViable(field, Functions.compose(converter, ratioConverter), rxz))
      {
        @SuppressWarnings("unchecked")
        final List<? extends U> vertex = Arrays.<U>asList(
                rxz.num(),
                planarSession.obliqueXY() ? rxz.num() : ring.zero(),
                rxz.denom());
        final U edgeLength2 = ring.multiply(ring.valueOf(4L), ring.add(
                ring.add(
                    ring.multiply(converter.apply(equation0.axx()), ring.multiply(rxz.num(), rxz.num())),
                    ring.multiply(converter.apply(equation0.a2xz()), ring.multiply(rxz.num(), rxz.denom()))),
                ring.add(
                    ring.multiply(converter.apply(equation0.a2xz()), ring.multiply(rxz.num(), rxz.denom())),
                    ring.multiply(converter.apply(equation0.azz()), ring.multiply(rxz.denom(), rxz.denom())))));
        final List<? extends FaceCandidate> candidates = PolygonSearching.candidates(session, false, ring, converter, vertex, edgeLength2);
        if (candidates == null)
          throw new RuntimeException("degenerate vertex set");

        Reporting.print(out, 1, candidates);
      }
  }
  
  private PlanarChecking() { }
}
