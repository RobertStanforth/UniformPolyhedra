package xyz.stanforth.algebra;

import java.math.*;
import java.util.*;

public final class BigIntegers
{
  public static Domain<BigInteger> ring()
  {
    return new Domain<BigInteger>()
    {
      @Override public BigInteger valueOf(final long n) { return BigInteger.valueOf(n); }
      @Override public BigInteger zero() { return BigInteger.ZERO; }
      @Override public BigInteger negate(final BigInteger t) { return t.negate(); }
      @Override public BigInteger add(final BigInteger t0, final BigInteger t1) { return t0.add(t1); }
      @Override public BigInteger subtract(final BigInteger t0, final BigInteger t1) { return t0.subtract(t1); }
      @Override public BigInteger identity() { return BigInteger.ONE; }
      @Override public BigInteger multiply(final BigInteger t0, final BigInteger t1) { return t0.multiply(t1); }
      
      @Override
      public DivisionResult<? extends BigInteger> divide(final BigInteger t0, final BigInteger t1)
      {
        final BigInteger[] divideAndRemainder = t0.divideAndRemainder(t1);
        return new DivisionResult<BigInteger>()
        {
          @Override public BigInteger quotient() { return divideAndRemainder[0]; }
          @Override public BigInteger remainder() { return divideAndRemainder[1]; }
        };
      }
      
      @Override public BigInteger norm(final BigInteger t) { return t.multiply(t); }
      
      @Override
      public BigInteger squareRoot(final BigInteger t)
      {
        if (t.compareTo(BigInteger.ZERO) <= 0)
          return BigInteger.ZERO;
        BigInteger lo = BigInteger.ZERO;
        BigInteger hi = t.add(BigInteger.ONE);
        while (hi.subtract(lo).compareTo(BigInteger.ONE) > 0)
          {
            final BigInteger mid = lo.add(hi).divide(BigInteger.valueOf(2L));
            if (mid.multiply(mid).compareTo(t) <= 0)
              lo = mid;
            else
              hi = mid;
          }
        if (lo.multiply(lo).equals(t))
          return lo;
        else
          return BigInteger.ZERO;
      }
      
      private List<BigInteger> primes = new ArrayList<>();
      private int primesHi = 2;
      
      @Override
      public List<? extends BigInteger> primesTo(final BigInteger maxSquare)
      {
        final BigInteger absMaxSquare = maxSquare.abs();
        if (absMaxSquare.compareTo(BigInteger.valueOf(1L<<56)) > 0)
          throw new UnsupportedOperationException();
        
        final int primesLo = primesHi;
        while (BigInteger.valueOf(((long)primesHi)*((long)primesHi)).compareTo(absMaxSquare) <= 0)
          ++ primesHi;
        
        final boolean[] sieve = new boolean[primesHi-primesLo];
        for (final BigInteger prime : primes)
          {
            final int p = (int)prime.longValue();
            if (p < primesLo)
              {
                for (int n = (primesLo + p-1)/p * p; n < primesHi; n += p)
                  sieve[n-primesLo] = true;
              }
          }
        for (int p = primesLo; p < primesHi; p ++)
          {
            if (!sieve[p-primesLo])
              {
                primes.add(BigInteger.valueOf((long)p));
                for (int n = 2 * p; n < primesHi; n += p)
                  sieve[n-primesLo] = true;
              }
          }
        
        return primes;
      }
      
      @Override
      public BigInteger canonicalPrime(final BigInteger prime)
      {
        return prime.abs();
      }
      
      @Override
      public Collection<? extends BigInteger> possibleUnits(final Polynomial<? extends BigInteger> poly, final BigInteger a, final BigInteger b)
      {
        return Arrays.asList(BigInteger.ONE, BigInteger.ONE.negate());
      }
      
      @Override
      public Collection<? extends BigInteger> nonSquareUnits()
      {
        return Arrays.asList(BigInteger.ONE, BigInteger.ONE.negate());
      }
      
      @Override public int hashCode(final BigInteger t) { return t.hashCode(); }
      @Override public boolean equals(final BigInteger t0, final BigInteger t1) { return t0.equals(t1); }
      @Override public String toString(final BigInteger t) { return t.toString(); }
      @Override public int compare(final BigInteger t0, final BigInteger t1) { return t0.compareTo(t1); }
    };
  }

  private BigIntegers() { }
}
