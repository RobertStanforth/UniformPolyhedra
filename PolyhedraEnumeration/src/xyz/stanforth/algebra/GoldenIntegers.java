package xyz.stanforth.algebra;

import java.math.*;
import java.util.*;

public final class GoldenIntegers
{
  public static GoldenInteger of(final long a, final long b)
  {
    return of(BigInteger.valueOf(a), BigInteger.valueOf(b));
  }
  
  public static GoldenInteger of(final BigInteger a)
  {
    return of(a, BigInteger.ZERO);
  }
  
  public static GoldenInteger of(final BigInteger a, final BigInteger b)
  {
    return new GoldenInteger()
    {
      @Override public BigInteger a() { return a; }
      @Override public BigInteger b() { return b; }
      @Override public int hashCode() { return GoldenIntegers.hashCode(this); }
      @Override public boolean equals(final Object o) { return (o instanceof GoldenInteger) && GoldenIntegers.equals(this, (GoldenInteger)o); }
      @Override public String toString() { return GoldenIntegers.toString(this); }
    };
  }

  private static int hashCode(final GoldenInteger t)
  {
    return 37 * t.a().hashCode() + t.b().hashCode();
  }

  private static boolean equals(final GoldenInteger t0, final GoldenInteger t1)
  {
    return t0.a().equals(t1.a()) && t0.b().equals(t1.b());
  }

  private static String toString(final GoldenInteger t)
  {
    return "(" + t.a().toString() + "+" + t.b().toString() + "\\phi" + ")";
  }
  
  public static Domain<GoldenInteger> ring()
  {
    final Domain<BigInteger> intRing = BigIntegers.ring();
    return new Domain<GoldenInteger>()
    {
      @Override public GoldenInteger valueOf(final long n) { return of(BigInteger.valueOf(n)); }
      @Override public GoldenInteger zero() { return of(BigInteger.ZERO); }
      @Override public GoldenInteger negate(final GoldenInteger t) { return of(t.a().negate(), t.b().negate()); }
      @Override public GoldenInteger add(final GoldenInteger t0, final GoldenInteger t1) { return of(t0.a().add(t1.a()), t0.b().add(t1.b())); }
      @Override public GoldenInteger subtract(final GoldenInteger t0, final GoldenInteger t1) { return of(t0.a().subtract(t1.a()), t0.b().subtract(t1.b())); }
      @Override public GoldenInteger identity() { return of(BigInteger.ONE); }

      @Override
      public GoldenInteger multiply(final GoldenInteger t0, final GoldenInteger t1)
      {
        final BigInteger aa = t0.a().multiply(t1.a());
        final BigInteger ab = t0.a().multiply(t1.b()).add(t0.b().multiply(t1.a()));
        final BigInteger bb = t0.b().multiply(t1.b());
        return of(aa.add(bb), ab.add(bb));
      }

      @Override
      public DivisionResult<? extends GoldenInteger> divide(final GoldenInteger n, final GoldenInteger d)
      {
        final GoldenInteger dp = phase(d);
        final GoldenInteger dn = multiply(d, dp);
        
        GoldenInteger r = n;
        GoldenInteger q = zero();
        
        while (norm(r).compareTo(norm(d)) >= 0)
          {
            final GoldenInteger rp = phase(r);
            final GoldenInteger rn = multiply(r, rp);
            final GoldenInteger ra = multiply(of(snorm(rp)), conj(rp)); // inverse of rp
            
            BigInteger mult = rn.a().divide(dn.a());
            if (mult.equals(BigInteger.ZERO))
              mult = BigInteger.ONE;
            
            r = subtract(r, multiply(ra, multiply(of(mult), dn)));
            q = add(q, multiply(ra, of(mult)));
          }
        
        q = multiply(q, dp);
        
        if (!n.equals(add(multiply(q, d), r)))
          throw new RuntimeException("division failed");
        final GoldenInteger quotient = q;
        final GoldenInteger remainder = r;
        return new DivisionResult<GoldenInteger>()
        {
          @Override public GoldenInteger quotient() { return quotient; }
          @Override public GoldenInteger remainder() { return remainder; }
        };
      }

      private GoldenInteger phase(GoldenInteger n)
      {
        GoldenInteger p = identity();
        if (compare(n, zero()) < 0 )
          {
            n = negate(n);
            p = negate(p);
          }

        GoldenInteger n2 = n;
        GoldenInteger p2 = p;
        while (!(n.b().compareTo(BigInteger.ZERO) >= 0 && n.b().compareTo(n.a()) < 0)
                && !(n2.b().compareTo(BigInteger.ZERO) >= 0 && n2.b().compareTo(n2.a()) < 0))
          {
            n = multiply(n, of(BigInteger.ZERO, BigInteger.ONE));
            p = multiply(p, of(BigInteger.ZERO, BigInteger.ONE));
            n2 = multiply(n2, of(BigInteger.ONE.negate(), BigInteger.ONE));
            p2 = multiply(p2, of(BigInteger.ONE.negate(), BigInteger.ONE));
          }
        if (!(n.b().compareTo(BigInteger.ZERO) >= 0 && n.b().compareTo(n.a()) < 0))
          {
            n = n2;
            p = p2;
          }
        
        return p;
      }
      
      @Override public BigInteger norm(final GoldenInteger t) { return snorm(t).abs(); }

      private BigInteger snorm(final GoldenInteger t)
      {
        return t.a().multiply(t.a()).add(t.a().multiply(t.b())).subtract(t.b().multiply(t.b()));
      }
      
      private GoldenInteger conj(final GoldenInteger t)
      {
        return of(t.a().add(t.b()), t.b().negate());
      }
      
      @Override
      public GoldenInteger squareRoot(final GoldenInteger t)
      {
        // No square root if negative.
        if (compare(t, zero()) > 0)
          {
            // No square root if the signed norm is not a perfect square.
            final BigInteger ns = intRing.squareRoot(snorm(t));
            if (ns.compareTo(BigInteger.ZERO) > 0)
              {
                BigInteger integerFactor = Domains.hcf(intRing, t.a().abs(), t.b().abs());
                BigInteger sqrtIntegerFactor = intRing.squareRoot(integerFactor);
                if (integerFactor.remainder(BigInteger.valueOf(5L)).equals(BigInteger.ZERO)
                        && sqrtIntegerFactor.equals(BigInteger.ZERO))
                  {
                    integerFactor = integerFactor.divide(BigInteger.valueOf(5L));
                    sqrtIntegerFactor = intRing.squareRoot(integerFactor);
                  }
                if (sqrtIntegerFactor.compareTo(BigInteger.ZERO) > 0)
                  {
                    final GoldenInteger quotient = of(t.a().divide(integerFactor), t.b().divide(integerFactor));
                    final GoldenInteger sqrtQuotient = squareRootX(quotient);
                    final GoldenInteger s = multiply(of(sqrtIntegerFactor), sqrtQuotient);
                    if (!equals(multiply(s, s), t))
                      throw new RuntimeException("failed to compute square root");
                    return s;
                  }
              }
          }
        return zero();
      }
      
      private GoldenInteger squareRootX(final GoldenInteger t)
      {
        GoldenInteger s = zero();
        BigInteger a = BigInteger.ZERO;
        while (!equals(multiply(s, s), t) && a.multiply(a).compareTo(t.a()) <= 0)
          {
            s = of(a);
            while (!equals(multiply(s, s), t) && multiply(s, s).a().compareTo(t.a()) < 0)
              s = add(s, of(BigInteger.ZERO, BigInteger.ONE));
            if (!equals(multiply(s, s), t))
              s = of(a);
            while (!equals(multiply(s, s), t) && multiply(s, s).a().compareTo(t.a()) < 0)
              s = subtract(s, of(BigInteger.ZERO, BigInteger.ONE));
            if (!equals(multiply(s, s), t))
              a = a.add(BigInteger.ONE);
          }
        if (!equals(multiply(s, s), t))
          throw new RuntimeException("failed to compute square root");
        if (compare(s, zero()) < 0)
          s = negate(s);
        return s;
      }
      
      private List<GoldenInteger> primes = new ArrayList<>();
      private BigInteger primesHi = BigInteger.ONE;
      
      @Override
      public List<? extends GoldenInteger> primesTo(final GoldenInteger maxSquare)
      {
        final BigInteger maxNormSquare = norm(maxSquare);
        final List<? extends BigInteger> intPrimes = intRing.primesTo(maxNormSquare.multiply(BigInteger.valueOf(25L)).divide(BigInteger.valueOf(16L)));
        int intIndex = 0;
        int intSqrIndex = 0;

        BigInteger a = primesHi;
        while (norm(of(a)).pow(2).compareTo(maxNormSquare) <= 0)
          {
            int insertIndex = primes.size();
            final BigInteger norma = norm(of(a));
            while (insertIndex > 0 && norm(primes.get(insertIndex-1)).compareTo(norma) > 0)
              -- insertIndex;
            
            while (intIndex < intPrimes.size() && intPrimes.get(intIndex).compareTo(a) < 0)
              ++ intIndex;
            if (intIndex < intPrimes.size() && intPrimes.get(intIndex).equals(a))
              {
                final long q = a.remainder(BigInteger.valueOf(5L)).longValue();
                if (q == 2L || q == 3L)
                  {
                    primes.add(insertIndex, of(a));
                    ++ insertIndex;
                  }
              }

            while (intSqrIndex < intPrimes.size() && intPrimes.get(intSqrIndex).compareTo(a.multiply(a)) < 0)
              ++ intSqrIndex;
            int intSqrIndex1 = intSqrIndex;
            for (BigInteger b = BigInteger.ONE; b.add(b).compareTo(a) <= 0; b = b.add(BigInteger.ONE))
              {
                final GoldenInteger g = of(a, b);
                final BigInteger normg = norm(g);
                while (insertIndex < primes.size() && norm(primes.get(insertIndex)).compareTo(normg) < 0)
                  ++ insertIndex;
                
                while (intSqrIndex1 < intPrimes.size() && intPrimes.get(intSqrIndex1).compareTo(normg) < 0)
                  ++ intSqrIndex1;
                if (intSqrIndex1 < intPrimes.size() && intPrimes.get(intSqrIndex1).equals(normg))
                  {
                    primes.add(insertIndex, g);
                    ++ insertIndex;
                    if (!b.add(b).equals(a))
                      {
                        primes.add(insertIndex, conj(g));
                        ++ insertIndex;
                      }
                  }
              }
            
            a = a.add(BigInteger.ONE);
          }
        primesHi = a;
        
        return primes;
      }
      
      @Override
      public GoldenInteger canonicalPrime(final GoldenInteger prime)
      {
        GoldenInteger p = prime;
        if (compare(p, zero()) < 0)
          p = negate(p);
        if (snorm(p).compareTo(BigInteger.ZERO) < 0)
          p = multiply(p, of(0L, 1L));
        while (intRing.compare(intRing.multiply(intRing.valueOf(2L), p.b()), p.a()) > 0)
          p = multiply(p, of(2L, -1L));
        while (intRing.compare(intRing.multiply(intRing.valueOf(-3L), p.b()), p.a()) >= 0)
          p = multiply(p, of(1L, 1L));
        return p;
      }
      
      @Override
      public List<? extends GoldenInteger> possibleUnits(final Polynomial<? extends GoldenInteger> poly, final GoldenInteger a, final GoldenInteger b)
      {
        final List<GoldenInteger> units = new ArrayList<>();
        units.add(identity());
        units.add(negate(identity()));
        
        final GoldenInteger pa = abs(a);
        final GoldenInteger pb = abs(b);
        
        final List<GoldenInteger> absCoeffs = new ArrayList<>(poly.degree()+1);
        for (final GoldenInteger coeff : poly.coeffs())
          absCoeffs.add(abs(coeff));
        
        // Apply positive powers of phi until the leading term dominates.
        absCoeffs.set(poly.degree(), negate(absCoeffs.get(poly.degree())));
        final Polynomial<? extends GoldenInteger> polyPlus = Polynomials.of(absCoeffs);
        for (GoldenInteger u = of(0L, 1L); compare(Polynomials.evalLinear(this, polyPlus, pa, multiply(u, pb)), zero()) >= 0; u = multiply(u, of(0L, 1L)))
          {
            units.add(u);
            units.add(negate(u));
          }
        absCoeffs.set(poly.degree(), negate(absCoeffs.get(poly.degree())));

        // Apply negative powers of phi until the trailing term dominates.
        absCoeffs.set(0, negate(absCoeffs.get(0)));
        final Polynomial<? extends GoldenInteger> polyMinus = Polynomials.of(absCoeffs);
        for (GoldenInteger u = of(-1L, 1L); compare(Polynomials.evalLinear(this, polyMinus, pa, multiply(u, pb)), zero()) >= 0; u = multiply(u, of(-1L, 1L)))
          {
            units.add(u);
            units.add(negate(u));
          }
        absCoeffs.set(0, negate(absCoeffs.get(0)));

        return units;
      }
      
      private GoldenInteger abs(final GoldenInteger a)
      {
        return compare(a, zero()) > 0 ? a : negate(a);
      }
      
      @Override
      public Collection<? extends GoldenInteger> nonSquareUnits()
      {
        return Arrays.asList(of(1L, 0L), of(-1L, 0L), of(0L, 1L), of(0L, -1L));
      }
      
      @Override public int hashCode(final GoldenInteger t) { return GoldenIntegers.hashCode(t); }
      @Override public boolean equals(final GoldenInteger t0, final GoldenInteger t1) { return GoldenIntegers.equals(t0, t1); }
      @Override public String toString(final GoldenInteger t) { return GoldenIntegers.toString(t); }
      
      @Override
      public int compare(final GoldenInteger t0, final GoldenInteger t1)
      {
        final GoldenInteger d = subtract(t0, t1);
        final BigInteger c1 = d.a().add(d.a()).add(d.b());
        final BigInteger c5 = d.b();
        return c1.compareTo(BigInteger.ZERO) > 0
                ? c5.compareTo(BigInteger.ZERO) >= 0 ? +1 : c1.multiply(c1).compareTo(BigInteger.valueOf(5L).multiply(c5).multiply(c5))
                : c5.compareTo(BigInteger.ZERO) < 0 ? -1 : BigInteger.valueOf(5L).multiply(c5).multiply(c5).compareTo(c1.multiply(c1));
      }
    };
  }

  private GoldenIntegers() { }
}
