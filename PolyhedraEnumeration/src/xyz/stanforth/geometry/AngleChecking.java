package xyz.stanforth.geometry;

import xyz.stanforth.algebra.OrderedRing;
import xyz.stanforth.util.Function;

public final class AngleChecking
{
  public static <T> AngleChecker<T> standardChecker()
  {
    return new AngleChecker<T>()
    {
      @Override
      public <U> PolygonType polygonType(final OrderedRing<U> ring, final Function<? super T, ? extends U> converter,
                                         final U scalarProd, final U edgeLength2)
      {
        // Calculate -2 times the scalar product for ease of subsequent use.
        final U denom = edgeLength2;
        final U num = ring.multiply(ring.valueOf(-2L), scalarProd);
        
        final int degree;
        final int winding;
        if (ring.equals(num, ring.multiply(ring.valueOf(-2L), denom)))
          {
            // Digon.
            throw new RuntimeException("degenerate vertex set");
          }
        else if (ring.equals(num, ring.negate(denom)))
          {
            // Triangle.
            degree = 3;
            winding = 1;
          }
        else if (ring.equals(num, ring.zero()))
          {
            // Square.
            degree = 4;
            winding = 1;
          }
        else if (ring.equals(num, denom))
          {
            // Hexagon.
            degree = 6;
            winding = 1;
          }
        else if (ring.equals(ring.multiply(num, num), ring.multiply(ring.valueOf(2L), ring.multiply(denom, denom))))
          {
            // Octagon or octagram.
            degree = 8;
            final int compare = ring.compare(num, ring.zero());
            winding = compare == 0 ? 0 : compare > 0 ? 1 : 3;
          }
        else if (ring.equals(ring.multiply(num, num), ring.multiply(ring.valueOf(3L), ring.multiply(denom, denom))))
          {
            // Dodecagon or dodecagram.
            degree = 12;
            final int compare = ring.compare(num, ring.zero());
            winding = compare == 0 ? 0 : compare > 0 ? 1 : 5;
          }
        else if (ring.equals(ring.add(ring.multiply(num, num),
                             ring.add(ring.multiply(ring.valueOf(+1L), ring.multiply(num, denom)),
                                      ring.multiply(ring.valueOf(-1L), ring.multiply(denom, denom)))), ring.zero()))
          {
            // Pentagon or pentagram.
            degree = 5;
            final int compare = ring.compare(num, ring.zero());
            winding = compare == 0 ? 0 : compare > 0 ? 1 : 2;
          }
        else if (ring.equals(ring.add(ring.multiply(num, num),
                             ring.add(ring.multiply(ring.valueOf(-1L), ring.multiply(num, denom)),
                                      ring.multiply(ring.valueOf(-1L), ring.multiply(denom, denom)))), ring.zero()))
          {
            // Decagon or decagram.
            degree = 10;
            final int compare = ring.compare(num, ring.zero());
            winding = compare == 0 ? 0 : compare < 0 ? 3 : 1;
          }
        else if (ring.equals(ring.add(ring.multiply(num, ring.multiply(num, num)),
                             ring.add(ring.multiply(ring.multiply(ring.valueOf(+1L), num), ring.multiply(num, denom)),
                             ring.add(ring.multiply(ring.multiply(ring.valueOf(-2L), num), ring.multiply(denom, denom)),
                                      ring.multiply(ring.multiply(ring.valueOf(-1L), denom), ring.multiply(denom, denom))))), ring.zero()))
          {
            // Heptagon or heptagram.
            degree = 7;
            final int compare = ring.compare(num, ring.zero());
            final int compare_mel = ring.compare(num, ring.negate(denom));
            winding = compare == 0 ? 0 : compare > 0 ? 1 : compare_mel > 0 ? 2 : 3;
          }
        else if (ring.equals(ring.add(ring.multiply(num, ring.multiply(num, num)),
                             ring.add(ring.multiply(ring.multiply(ring.valueOf(-1L), num), ring.multiply(num, denom)),
                             ring.add(ring.multiply(ring.multiply(ring.valueOf(-2L), num), ring.multiply(denom, denom)),
                                      ring.multiply(ring.multiply(ring.valueOf(+1L), denom), ring.multiply(denom, denom))))), ring.zero()))
          {
            // 14-gon or 14-gram.
            degree = 14;
            final int compare = ring.compare(num, ring.zero());
            final int compare_pel = ring.compare(num, denom);
            winding = compare == 0 ? 0 : compare < 0 ? 5 : compare_pel < 0 ? 3 : 1;
          }
        else if (ring.equals(ring.add(ring.multiply(num, ring.multiply(num, num)),
                             ring.add(ring.multiply(ring.multiply(ring.valueOf(-3L), num), ring.multiply(denom, denom)),
                                      ring.multiply(ring.multiply(ring.valueOf(+1L), denom), ring.multiply(denom, denom)))), ring.zero()))
          {
            // Enneagon or enneagram.
            degree = 9;
            final int compare = ring.compare(num, ring.zero());
            final int compare_pel = ring.compare(num, denom);
            winding = compare == 0 ? 0 : compare_pel > 0 ? 1 : compare > 0 ? 2 : 4;
          }
        else if (ring.equals(ring.add(ring.multiply(num, ring.multiply(num, num)),
                             ring.add(ring.multiply(ring.multiply(ring.valueOf(-3L), num), ring.multiply(denom, denom)),
                                      ring.multiply(ring.multiply(ring.valueOf(-1L), denom), ring.multiply(denom, denom)))), ring.zero()))
          {
            // 18-gon or 18-gram.
            degree = 18;
            final int compare = ring.compare(num, ring.zero());
            final int compare_mel = ring.compare(num, ring.negate(denom));
            winding = compare == 0 ? 0 : compare_mel < 0 ? 7 : compare < 0 ? 5 : 1;
          }
        else
          {
            // Unknown.
            degree = 11;
            winding = 0;
          }

        return new PolygonType()
        {
          @Override public int degree() { return degree; }
          @Override public int winding() { return winding; }
        };
      }
    };
  }

  public static <T> AngleChecker<T> goldenChecker(final OrderedRing<T> goldenRing, final T phi)
  {
    final AngleChecker<T> standardChecker = standardChecker();
    final T phi_conj = goldenRing.subtract(goldenRing.identity(), phi);
    return new AngleChecker<T>()
    {
      @Override
      public <U> PolygonType polygonType(final OrderedRing<U> ring, final Function<? super T, ? extends U> converter,
              final U scalarProd, final U edgeLength2)
      {
        // Calculate -2 times the scalar product for ease of subsequent use.
        final U denom = edgeLength2;
        final U num = ring.multiply(ring.valueOf(-2L), scalarProd);
        
        final int degree;
        final int winding;
        if (ring.equals(num, ring.multiply(ring.negate(converter.apply(phi_conj)), denom)))
          {
            // Pentagon.
            degree = 5;
            winding = 1;
          }
        else if (ring.equals(num, ring.multiply(ring.negate(converter.apply(phi)), denom)))
          {
            // Pentagram.
            degree = 5;
            winding = 2;
          }
        else if (ring.equals(num, ring.multiply(converter.apply(phi), denom)))
          {
            // Decagon.
            degree = 10;
            winding = 1;
          }
        else if (ring.equals(num, ring.multiply(converter.apply(phi_conj), denom)))
          {
            // Decagram.
            degree = 10;
            winding = 3;
          }
        else if (ring.equals(ring.add(ring.multiply(num, num),
                             ring.add(ring.multiply(ring.negate(converter.apply(phi)), ring.multiply(num, denom)),
                                      ring.multiply(ring.subtract(converter.apply(phi), ring.valueOf(2L)), ring.multiply(denom, denom)))), ring.zero()))
          {
            // 15-gon or 15-gram.
            degree = 15;
            final int compare = ring.compare(num, ring.zero());
            winding = compare == 0 ? 0 : compare > 0 ? 1 : 4;
          }
        else if (ring.equals(ring.add(ring.multiply(num, num),
                             ring.add(ring.multiply(ring.negate(converter.apply(phi_conj)), ring.multiply(num, denom)),
                                      ring.multiply(ring.subtract(converter.apply(phi_conj), ring.valueOf(2L)), ring.multiply(denom, denom)))), ring.zero()))
          {
            // 15-gon or 15-gram.
            degree = 15;
            final int compare = ring.compare(num, ring.zero());
            winding = compare == 0 ? 0 : compare > 0 ? 2 : 7;
          }
        else if (ring.equals(ring.add(ring.multiply(num, num),
                             ring.add(ring.multiply(converter.apply(phi), ring.multiply(num, denom)),
                                      ring.multiply(ring.subtract(converter.apply(phi), ring.valueOf(2L)), ring.multiply(denom, denom)))), ring.zero()))
          {
            // 30-gon or 30-gram.
            degree = 30;
            final int compare = ring.compare(num, ring.zero());
            winding = compare == 0 ? 0 : compare < 0 ? 13 : 7;
          }
        else if (ring.equals(ring.add(ring.multiply(num, num),
                             ring.add(ring.multiply(converter.apply(phi_conj), ring.multiply(num, denom)),
                                      ring.multiply(ring.subtract(converter.apply(phi_conj), ring.valueOf(2L)), ring.multiply(denom, denom)))), ring.zero()))
          {
            // 30-gon or 30-gram.
            degree = 30;
            final int compare = ring.compare(num, ring.zero());
            winding = compare == 0 ? 0 : compare < 0 ? 11 : 1;
          }
        else
          {
            // Unknown.
            return standardChecker.polygonType(ring, converter, scalarProd, edgeLength2);
          }

        return new PolygonType()
        {
          @Override public int degree() { return degree; }
          @Override public int winding() { return winding; }
        };
      }
    };
  }
  
  private AngleChecking() { }
}
