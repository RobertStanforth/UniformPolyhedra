package xyz.stanforth.algebra;

import java.math.*;
import java.util.*;

public interface Domain<T> extends OrderedRing<T>, Comparator<T>
{
  BigInteger norm(T t);
  DivisionResult<? extends T> divide(T t0, T t1);
  T squareRoot(T t);
  
  /**
   * @param maxSquare maximum prime square by norm
   * @return primes, including all those whose square does not exceed {@code t} in norm
   */
  List<? extends T> primesTo(T maxSquare);
  
  /**
   * @param prime prime
   * @return {@code prime} multiplied by a unit to reduce to a canonical form
   */
  T canonicalPrime(T prime);
  
  Collection<? extends T> possibleUnits(Polynomial<? extends T> poly, T a, T b);
  Collection<? extends T> nonSquareUnits();
  
  interface DivisionResult<T>
  {
    T quotient();
    T remainder();
  }

  default T quotient(final T t0, final T t1)
  {
    final Domain.DivisionResult<? extends T> divisionResult = divide(t0, t1);
    if (!divisionResult.remainder().equals(zero()))
      throw new IllegalArgumentException("not a divisor");
    return divisionResult.quotient();
  }
}
