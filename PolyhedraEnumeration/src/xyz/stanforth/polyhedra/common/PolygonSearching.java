package xyz.stanforth.polyhedra.common;

import xyz.stanforth.algebra.Domain;
import xyz.stanforth.algebra.Matrices;
import xyz.stanforth.algebra.OrderedRing;
import xyz.stanforth.algebra.RingMatrix;
import xyz.stanforth.geometry.Geometry;
import xyz.stanforth.geometry.PolygonType;
import xyz.stanforth.util.Function;
import xyz.stanforth.util.Ref;

import java.util.*;
import java.util.stream.*;

public final class PolygonSearching
{
  public static <T, U> List<? extends FaceCandidate> candidates(final Session<T> session, final boolean useInverse,
                                                                final OrderedRing<U> ring, final Function<? super T, ? extends U> converter,
                                                                final List<? extends U> vertex, final U edgeLength2)
  {
    final Domain<T> groundRing = session.axes().ring();
    final List<List<? extends U>> eq = new ArrayList<>();
    final List<List<? extends U>> neq = new ArrayList<>();
    final Ref<Boolean> degenerate = new Ref<>(false);
    session.forIdentityAndAxisRotations(new Session.RunnableWithTransform<T>()
    {
      @Override
      public void run(final RingMatrix<? extends T> transform)
      {
        collectVertexForTransform(transform);
        if (useInverse)
          collectVertexForTransform(Matrices.negate(groundRing, transform));
      }
      
      private boolean gotZero = false;
      
      private void collectVertexForTransform(final RingMatrix<? extends T> transform)
      {
        // Obtain the displacement to the vertex.
        final RingMatrix<? extends T> diff = session.diff(transform);
        
        final List<U> v = new ArrayList<>(3);
        for (int i = 0; i < 3; i ++)
          {
            U cpt = ring.zero();
            for (int j = 0; j < 3; j ++)
              cpt = ring.add(cpt, ring.multiply(converter.apply(diff.elt(i, j)), vertex.get(j)));
            v.add(cpt);
          }
        
        final U d = Geometry.scalarProduct(ring, 3, v, v);
        
        // Check for degenerate vertex set.
        if (ring.equals(d, ring.zero()))
          {
            if (gotZero)
              degenerate.set(true);
            gotZero = true;
          }
        
        // Check for edge candidacy.
        if (ring.equals(d, edgeLength2))
          eq.add(v);
        else
          neq.add(v);
      }
    });

    if (degenerate.value())
      return null;
    
    return prune(eq.size(), faceCandidates(session, ring, converter, edgeLength2, eq, neq));
  }

  private static <T, U> List<? extends FaceCandidate> faceCandidates(final Session<T> session, final OrderedRing<U> ring, final Function<? super T, ? extends U> converter,
          final U edgeLength2, final List<? extends List<? extends U>> eq, final List<? extends List<? extends U>> neq)
  {
    // Loop through pairs of incident candidate edges.
    final List<FaceCandidate> candidates = new ArrayList<>();
    for (int e1 = 0; e1 < eq.size(); e1 ++)
      for (int e0 = 0; e0 < e1; e0 ++)
        {
          final List<? extends U> v0 = eq.get(e0);
          final List<? extends U> v1 = eq.get(e1);
          
          final PolygonType polygonType = session.axes().angleChecker().polygonType(ring, converter,
                  Geometry.scalarProduct(ring, 3, v0, v1), edgeLength2);
          
          // Count the vertices in the same plane.
          int numVerticesInPlane = 2;
          final List<? extends U> vectorProduct = Geometry.vectorProduct(ring, v0, v1);
          for (final List<? extends U> vn : neq)
            if (ring.equals(Geometry.scalarProduct(ring, 3, vectorProduct, vn), ring.zero()))
              ++ numVerticesInPlane;

          // Only include cases in which there are sufficiently many vertices in the plane to fulfil the face degree.
          final int indexA = e0;
          final int indexB = e1;
          if (numVerticesInPlane >= polygonType.degree())
            candidates.add(new FaceCandidate()
              {
                @Override public PolygonType polygonType() { return polygonType; }
                @Override public int indexA() { return indexA; }
                @Override public int indexB() { return indexB; }
              });
        }
    return candidates;
  }

  /**
   * @param numEdges
   *          number of edges, upon which candidates may be incident
   * @param candidates
   *          candidates
   * @return candidates, omitting any candidate that is not involved in a cycle (by edge incidence)
   */
  private static List<? extends FaceCandidate> prune(final int numEdges, final List<? extends FaceCandidate> candidates)
  {
    // Determine those candidates that are connected to other candidates at each end.
    final int[] numIncidentOnEdge = new int[numEdges];
    for (int index = 0; index < numEdges; index ++)
      numIncidentOnEdge[index] = 0;
    for (final FaceCandidate candidate : candidates)
      {
        ++ numIncidentOnEdge[candidate.indexA()];
        ++ numIncidentOnEdge[candidate.indexB()];
      }

    final List<? extends FaceCandidate> pruned = candidates.stream().filter((final FaceCandidate candidate)
                -> numIncidentOnEdge[candidate.indexA()] > 1
                && numIncidentOnEdge[candidate.indexB()] > 1
        ).collect(Collectors.toList());

    // See if this has reduced the number of candidates. If so, recurse.
    if (pruned.size() < candidates.size())
      return prune(numEdges, pruned);
    else
      return pruned;
  }
  
  private PolygonSearching() { }
}
