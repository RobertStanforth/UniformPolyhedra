package xyz.stanforth.algebra;

import xyz.stanforth.util.Function;
import xyz.stanforth.util.Ref;

import java.util.*;

public final class Polynomials
{
  public static <T> Polynomial<? extends T> reduced(final Domain<T> ring, final List<? extends T> coeffs)
  {
    final int degree = coeffs.size() - 1;
    if (coeffs.isEmpty() || ring.equals(coeffs.get(degree), ring.zero()))
      throw new IllegalArgumentException("leading coefficient must be non-zero");

    // Remove common factors amongst coefficients, and ensure that leading coefficient is positive.
    int i = degree;
    T hcf = coeffs.get(i);
    while (i > 0)
      {
        -- i;
        hcf = Domains.hcf(ring, hcf, coeffs.get(i));
      }
    if ((ring.compare(hcf, ring.zero()) > 0) ^ (ring.compare(coeffs.get(degree), ring.zero()) > 0))
      hcf = ring.negate(hcf);
    final List<T> reduced = new ArrayList<T>(coeffs.size());
    for (final T coeff : coeffs)
      reduced.add(Domains.divide(ring, coeff, hcf));

    return of(reduced);
  }
  
  public static <T> Polynomial<? extends T> of(final List<? extends T> coeffs)
  {
    final int degree = coeffs.size() - 1;
    
    return new Polynomial<T>()
    {
      @Override public List<? extends T> coeffs() { return coeffs; }
      @Override public int degree() { return degree; }
      @Override public int numRealRoots() { throw new UnsupportedOperationException(); }
      @Override public String toString() { return coeffs().toString(); }
    };
  }
  
  public static <T> T evalLinear(final Domain<T> ring, final Polynomial<? extends T> poly, final T a, final T b)
  {
    T eval = ring.zero();
    T bn = ring.identity();
    for (final T coeff : poly.coeffs())
      {
        eval = ring.multiply(eval, a);
        eval = ring.add(eval, ring.multiply(bn, coeff));
        bn = ring.multiply(bn, b);
      }
    return eval;
  }
  
  public static <T> Polynomial<? extends T> divide(final Domain<T> ring, final Polynomial<? extends T> poly, final Polynomial<? extends T> factor)
  {
    final List<T> remainder = new ArrayList<T>(poly.coeffs());
    final int fdeg = factor.degree();
    final T b = factor.coeffs().get(fdeg);
    final int qdeg = poly.degree() - factor.degree();
    final List<T> quotient = new ArrayList<T>(qdeg+1);
    for (int i = 0; i <= qdeg; i ++)
      quotient.add(null);
    for (int i = qdeg; i >= 0; i --)
      {
        final T a = remainder.get(i + fdeg);
        final T q = Domains.divide(ring, a, b);
        quotient.set(i, q);
        for (int j = 0; j <= fdeg; j ++)
          remainder.set(i + j, ring.subtract(remainder.get(i + j), ring.multiply(q, factor.coeffs().get(j))));
      }
    for (final T rcoeff : remainder)
      if (!ring.equals(rcoeff, ring.zero()))
        throw new IllegalArgumentException("not a divisor");
    return of(quotient);
  }
  
  public static <T> List<? extends Polynomial<? extends T>> factors(final Domain<T> ring, final List<? extends T> coeffs)
  {
    return factors(ring, reduced(ring, coeffs));
  }
  
  public static <T> List<? extends Polynomial<? extends T>> factors(final Domain<T> ring, final Polynomial<? extends T> poly)
  {
    final List<Polynomial<? extends T>> factors = new ArrayList<Polynomial<? extends T>>();
    // First search for linear factors.
    final Polynomial<? extends T> linearFactor = linearFactor(ring, poly);
    if (linearFactor != null)
      {
        factors.add(withRealRoots(ring, linearFactor));
        final Polynomial<? extends T> cofactor = divide(ring, poly, linearFactor);
        // Recursively add the other factors.
        factors.addAll(factors(ring, cofactor));
      }
    else
      {
        if (poly.degree() == 4)
          {
            // Search for quadratic factors.
            // First form the resolvant cubic.
            final T o = poly.coeffs().get(4);
            final T a = poly.coeffs().get(3);
            final T b = poly.coeffs().get(2);
            final T c = poly.coeffs().get(1);
            final T d = poly.coeffs().get(0);
            final List<T> resolvant = new ArrayList<T>(4);
            T r0 = ring.multiply(ring.valueOf(-1L), Rings.pow(ring, a, 6));
            r0 = ring.add(r0, ring.multiply(ring.valueOf(8L), ring.multiply(Rings.pow(ring, a, 4), ring.multiply(b, o))));
            r0 = ring.add(r0, ring.multiply(ring.valueOf(-16L), ring.multiply(Rings.pow(ring, a, 3), ring.multiply(c, ring.multiply(o, o)))));
            r0 = ring.add(r0, ring.multiply(ring.valueOf(-16L), ring.multiply(ring.multiply(a, a), ring.multiply(ring.multiply(b, b), ring.multiply(o, o)))));
            r0 = ring.add(r0, ring.multiply(ring.valueOf(64L), ring.multiply(a, ring.multiply(b, ring.multiply(c, Rings.pow(ring, o, 3))))));
            r0 = ring.add(r0, ring.multiply(ring.valueOf(-64L), ring.multiply(ring.multiply(c, c), Rings.pow(ring, o, 4))));
            resolvant.add(r0);
            T r1 = ring.multiply(ring.valueOf(12L), ring.multiply(Rings.pow(ring, a, 4), ring.multiply(o, o)));
            r1 = ring.add(r1, ring.multiply(ring.valueOf(-64L), ring.multiply(ring.multiply(a, a), ring.multiply(b, Rings.pow(ring, o, 3)))));
            r1 = ring.add(r1, ring.multiply(ring.valueOf(64L), ring.multiply(a, ring.multiply(c, Rings.pow(ring, o, 4)))));
            r1 = ring.add(r1, ring.multiply(ring.valueOf(64L), ring.multiply(ring.multiply(b, b), Rings.pow(ring, o, 4))));
            r1 = ring.add(r1, ring.multiply(ring.valueOf(-256L), ring.multiply(d, Rings.pow(ring, o, 5))));
            resolvant.add(r1);
            T r2 = ring.multiply(ring.valueOf(128L), ring.multiply(b, Rings.pow(ring, o, 5)));
            r2 = ring.add(r2, ring.multiply(ring.valueOf(-48L), ring.multiply(ring.multiply(a, a), Rings.pow(ring, o, 4))));
            resolvant.add(r2);
            T r3 = ring.multiply(ring.valueOf(64L), Rings.pow(ring, o, 6));
            resolvant.add(r3);
            final Polynomial<? extends T> quadraticFactor = quadraticFactorOfQuartic(ring, poly, resolvant);
            if (quadraticFactor != null)
              {
                factors.add(withRealRoots(ring, quadraticFactor));
                final Polynomial<? extends T> cofactor = divide(ring, poly, quadraticFactor);
                factors.add(withRealRoots(ring, cofactor));
              }
            else
              {
                // Quartic is irreducible over the ring.
                // Use the resolvant again, to determine whether it has any real roots.
                // Note that the leading coefficient of the resolvant is positive.
                // Determine the number of roots from the discriminant.
                T discriminant = ring.multiply(ring.valueOf(27L), ring.multiply(r3, ring.multiply(r3, ring.multiply(r0, r0))));
                discriminant = ring.add(discriminant, ring.multiply(ring.valueOf(-18L), ring.multiply(r3, ring.multiply(r2, ring.multiply(r1, r0)))));
                discriminant = ring.add(discriminant, ring.multiply(ring.valueOf(4L), ring.multiply(r2, ring.multiply(r2, ring.multiply(r2, r0)))));
                discriminant = ring.add(discriminant, ring.multiply(ring.valueOf(-1L), ring.multiply(r2, ring.multiply(r2, ring.multiply(r1, r1)))));
                discriminant = ring.add(discriminant, ring.multiply(ring.valueOf(4L), ring.multiply(r3, ring.multiply(r1, ring.multiply(r1, r1)))));
                final int ds = ring.compare(discriminant, ring.zero());
                if (ds > 0)
                  {
                    // Resolvant has only one real root.
                    // Quartic has two distinct real roots and a complex conjugate pair.
                    factors.add(withRealRoots(poly, 2));
                  }
                else if (ds == 0)
                  {
                    // Resolvant has a repeated real root.
                    if (ring.compare(r2, ring.zero()) > 0
                            && ring.equals(r1, ring.zero())
                            && ring.equals(r0, ring.zero()))
                      {
                        // Resolvant has a negative real root and a repeated zero root.
                        // Quartic has a repeated complex conjugate pair.
                        factors.add(withRealRoots(poly, 0));
                      }
                    else
                      {
                        // Quartic has a repeated real root.
                        factors.add(withRealRoots(poly, 1));
                      }
                    // Actually, if there is a repeated root then we should have been able to factorise the quartic.
                    throw new RuntimeException("repeated factor missed");
                  }
                else
                  {
                    // Resolvant has three distinct real roots.
                    if (!(ring.compare(r2, ring.zero()) <= 0
                            && ring.compare(r1, ring.zero()) >= 0)
                            && ring.compare(r0, ring.zero()) <= 0)
                      {
                        // Resolvant has two negative (or zero) roots and one positive (or zero) root.
                        // Quartic has two distinct complex conjugate pairs.
                        factors.add(withRealRoots(poly, 0));
                      }
                    else
                      {
                        // Quartic has four real roots.
                        factors.add(withRealRoots(poly, 4));
                      }
                  }
              }
          }
        else
          {
            if (poly.degree() > 0)
              factors.add(withRealRoots(ring, poly));
          }
      }
    return factors;
  }

  /**
   * @param ring ring
   * @param poly polynomial
   * @return linear factor of the polynomial, or {@code null} if none
   */
  private static <T> Polynomial<? extends T> linearFactor(final Domain<T> ring, final Polynomial<? extends T> poly)
  {
    if (ring.equals(poly.coeffs().get(0), ring.zero()))
      {
        final List<T> factor = new ArrayList<T>(2);
        factor.add(ring.zero());
        factor.add(ring.identity());
        return of(factor);
      }
    
    // Factorise the leading and trailing coefficient.
    final Map<? extends T, ? extends Integer> primeFactorsN = Domains.primeFactors(ring, poly.coeffs().get(poly.degree()));
    final Map<? extends T, ? extends Integer> primeFactors0 = Domains.primeFactors(ring, poly.coeffs().get(0));
    // Determine the distinct primes that occur in either leading or trailing coefficient.
    final Set<T> primesSet = new HashSet<T>();
    primesSet.addAll(primeFactorsN.keySet());
    primesSet.addAll(primeFactors0.keySet());
    final List<? extends T> primes = new ArrayList<T>(primesSet);
    // Iterate over all coprime pairs of divisors of leading and trailing coefficient (up to unit multiply).
    final Ref<Polynomial<? extends T>> factorRef = new Ref<Polynomial<? extends T>>();
    new Runnable()
    {
      @Override
      public void run()
      {
        final T coeff1First = ring.identity();
        final T coeff0First = ring.identity();
        process(0, coeff1First, coeff0First);
      }
      
      private void process(final int i, final T coeff1, final T coeff0)
      {
        if (i == primes.size())
          {
            // Determine possibilities for unit.
            for (final T unit : ring.possibleUnits(poly, coeff1, coeff0))
              {
                final T a = coeff1;
                final T b = ring.multiply(unit, coeff0);
                if (ring.equals(evalLinear(ring, poly, a, b), ring.zero()))
                  {
                    final List<T> factor = new ArrayList<T>(2);
                    factor.add(ring.negate(b));
                    factor.add(a);
                    factorRef.set(of(factor));
                    return;
                  }
              }
          }
        else
          {
            final T prime = primes.get(i);
            T coeff1Next = coeff1;
            T coeff0Next = coeff0;
            process(i+1, coeff1Next, coeff0Next);
            for (int j = primeFactorsN.containsKey(prime) ? primeFactorsN.get(prime) : 0; j > 0; j --)
              {
                coeff1Next = ring.multiply(coeff1Next, prime);
                process(i+1, coeff1Next, coeff0Next);
                if (factorRef.isSet())
                  return;
              }
            coeff1Next = coeff1;
            coeff0Next = coeff0;
            for (int j = primeFactors0.containsKey(prime) ? primeFactors0.get(prime) : 0; j > 0; j --)
              {
                coeff0Next = ring.multiply(coeff0Next, prime);
                process(i+1, coeff1Next, coeff0Next);
                if (factorRef.isSet())
                  return;
              }
          }
      }
    }.run();
    
    return factorRef.value();
  }

  /**
   * @param ring ring
   * @param poly quartic polynomial
   * @param resolvant resolvant cubic of the quartic
   * @return quartic factor of the polynomial, or {@code null} if none
   */
  private static <T> Polynomial<? extends T> quadraticFactorOfQuartic(final Domain<T> ring, final Polynomial<? extends T> poly, final List<T> resolvant)
  {
    if (poly.degree() != 4)
      throw new IllegalArgumentException();
    final T o = poly.coeffs().get(4);
    final T a = poly.coeffs().get(3);
    final T b = poly.coeffs().get(2);
    final T c = poly.coeffs().get(1);
    final T d = poly.coeffs().get(0);
    
    final Ratio<? extends T> u = squareRootOfFactor(ring, resolvant);
    if (u != null)
      {
        final List<T> factor = new ArrayList<T>(3);
        T f0 = ring.multiply(ring.valueOf(8L), ring.multiply(u.num(), ring.multiply(u.denom(), ring.multiply(u.denom(),
                ring.multiply(o, ring.multiply(o, b))))));
        f0 = ring.add(f0, ring.multiply(ring.valueOf(8L), ring.multiply(u.num(), ring.multiply(u.num(), ring.multiply(u.num(),
                ring.multiply(o, ring.multiply(o, o)))))));
        f0 = ring.add(f0, ring.multiply(ring.valueOf(-2L), ring.multiply(u.num(), ring.multiply(u.denom(), ring.multiply(u.denom(),
                ring.multiply(o, ring.multiply(a, a)))))));
        f0 = ring.add(f0, ring.multiply(ring.valueOf(-8L), ring.multiply(u.denom(), ring.multiply(u.denom(), ring.multiply(u.denom(),
                ring.multiply(o, ring.multiply(o, c)))))));
        f0 = ring.add(f0, ring.multiply(ring.valueOf(-1L), ring.multiply(u.denom(), ring.multiply(u.denom(), ring.multiply(u.denom(),
                ring.multiply(a, ring.multiply(a, a)))))));
        f0 = ring.add(f0, ring.multiply(ring.valueOf(4L), ring.multiply(u.num(), ring.multiply(u.num(), ring.multiply(u.denom(),
                ring.multiply(o, ring.multiply(o, a)))))));
        f0 = ring.add(f0, ring.multiply(ring.valueOf(4L), ring.multiply(u.denom(), ring.multiply(u.denom(), ring.multiply(u.denom(),
                ring.multiply(o, ring.multiply(a, b)))))));
        factor.add(f0);
        T f1 = ring.multiply(ring.valueOf(16L), ring.multiply(u.num(), ring.multiply(u.num(), ring.multiply(u.denom(),
                ring.multiply(o, ring.multiply(o, o))))));
        f1 = ring.add(f1, ring.multiply(ring.valueOf(8L), ring.multiply(u.num(), ring.multiply(u.denom(), ring.multiply(u.denom(),
                ring.multiply(o, ring.multiply(o, a)))))));
        factor.add(f1);
        T f2 = ring.multiply(ring.valueOf(16L), ring.multiply(u.num(), ring.multiply(u.denom(), ring.multiply(u.denom(),
                ring.multiply(o, ring.multiply(o, o))))));
        factor.add(f2);
        return reduced(ring, factor);
      }
    else if (ring.equals(resolvant.get(0), ring.zero()))
      {
        // Resolvant has zero root, and no other perfect-square roots.
        // This case must be treated separately, by forming a quadratic for the factor's constant coefficient.
        // Factorise original (uncentred) polynomial as:
        // (x² + ax/2 + v)(x² + ax/2 + w)
        // Matching powers gives:
        // v+w = b - a²/4
        // vw = d
        final List<T> quad = new ArrayList<T>(3);
        quad.add(ring.multiply(ring.valueOf(4L), ring.multiply(o, d)));
        quad.add(ring.subtract(ring.multiply(a, a), ring.multiply(ring.valueOf(4L), ring.multiply(o, b))));
        quad.add(ring.multiply(ring.valueOf(4L), ring.multiply(o, o)));
        final List<? extends Polynomial<? extends T>> quadFactors = factors(ring, quad);
        if (quadFactors.size() == 2)
          {
            final List<T> factor = new ArrayList<T>(3);
            factor.add(ring.multiply(ring.valueOf(-2L), ring.multiply(o, quadFactors.get(0).coeffs().get(0))));
            factor.add(ring.multiply(a, quadFactors.get(0).coeffs().get(1)));
            factor.add(ring.multiply(ring.valueOf(2L), ring.multiply(o, quadFactors.get(0).coeffs().get(1))));
            return reduced(ring, factor);
          }
      }
    return null;
  }

  /**
   * @param ring ring
   * @param poly polynomial
   * @return square root of a non-zero root of the polynomial, or {@code null} if no such root is a perfect square
   */
  private static <T> Ratio<? extends T> squareRootOfFactor(final Domain<T> ring, final List<T> poly)
  {
    for (final Polynomial<? extends T> resolvantFactor : factors(ring, poly))
      if (resolvantFactor.degree() == 1)
        {
          // No need to remove common factors of the coefficients: this was already done before factorising the resolvant.
          for (final T nonSquareUnit : ring.nonSquareUnits())
            {
              final T sqrt1 = ring.squareRoot(ring.multiply(nonSquareUnit, resolvantFactor.coeffs().get(1)));
              if (ring.compare(sqrt1, ring.zero()) > 0)
                {
                  final T sqrt0 = ring.squareRoot(ring.multiply(ring.negate(nonSquareUnit), resolvantFactor.coeffs().get(0)));
                  if (ring.compare(sqrt0, ring.zero()) > 0)
                    {
                      return Ratios.unreduced(ring, sqrt0, sqrt1);
                    }
                }
            }
        }
    return null;
  }
  
  private static <T> Polynomial<? extends T> withRealRoots(final Domain<T> ring, final Polynomial<? extends T> poly)
  {
    if (poly.degree() == 1)
      {
        // Linear always has a root.
        return withRealRoots(poly, 1);
      }
    if (poly.degree() == 2)
      {
        // Use discriminant.
        final T a = poly.coeffs().get(2);
        final T b = poly.coeffs().get(1);
        final T c = poly.coeffs().get(0);
        final T discriminant = ring.subtract(ring.multiply(b, b), ring.multiply(ring.valueOf(4L), ring.multiply(a, c)));
        final int dc = ring.compare(discriminant, ring.zero());
        return withRealRoots(poly, dc > 0 ? 2 : dc == 0 ? 1 : 0);
      }
    else if (poly.degree() == 3)
      {
        // Use discriminant.
        final T a = poly.coeffs().get(3);
        final T b = poly.coeffs().get(2);
        final T c = poly.coeffs().get(1);
        final T d = poly.coeffs().get(0);
        final T A = ring.add(ring.multiply(ring.valueOf(4L), ring.multiply(b, b)),
                            ring.multiply(ring.valueOf(-12L), ring.multiply(a, c)));
        final T B = ring.add(ring.multiply(ring.valueOf(-8L), ring.multiply(b, ring.multiply(b, b))),
                            ring.add(ring.multiply(ring.valueOf(36L), ring.multiply(a, ring.multiply(b, c))),
                                    ring.multiply(ring.valueOf(-108L), ring.multiply(a, ring.multiply(a, d)))));
        final T discriminant = ring.subtract(ring.multiply(B, B), ring.multiply(A, ring.multiply(A, A)));
        final int dc = ring.compare(discriminant, ring.zero());
        return withRealRoots(poly, dc > 0 ? 1 : dc == 0 ? 2 : 3);
      }
    else
      {
        // Don't know.
        throw new UnsupportedOperationException();
      }
  }
  
  private static <T> Polynomial<? extends T> withRealRoots(final Polynomial<? extends T> poly, final int numRealRoots)
  {
    return new Polynomial<T>()
    {
      @Override public int degree() { return poly.degree(); }
      @Override public List<? extends T> coeffs() { return poly.coeffs(); }
      @Override public int numRealRoots() { return numRealRoots; }
      @Override public String toString() { return poly.toString() + (numRealRoots() > 0 ? "!" : ""); }
    };
  }

  public static <T, U> Polynomial<? extends U> convert(final Function<? super T, ? extends U> converter, final Polynomial<? extends T> poly)
  {
    final List<? extends U> coeffs = new AbstractList<U>()
    {
      @Override public int size() { return poly.coeffs().size(); }
      @Override public U get(int index) { return converter.apply(poly.coeffs().get(index)); }
    };
    
    return new Polynomial<U>()
    {
      @Override public int degree() { return poly.degree(); }
      @Override public List<? extends U> coeffs() { return coeffs; }
      @Override public int numRealRoots() { return poly.numRealRoots(); }
      @Override public String toString() { return coeffs().toString(); }
    };
  }
  
  private Polynomials() { }
}
