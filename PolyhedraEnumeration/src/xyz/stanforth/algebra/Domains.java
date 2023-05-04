package xyz.stanforth.algebra;

import xyz.stanforth.util.Ref;

import java.math.*;
import java.util.*;

public final class Domains
{
  public static <T> T divide(final Domain<T> domain, final T t0, final T t1)
  {
    return domain.quotient(t0, t1);
  }
  
  public static <T> void euclid(final Domain<T> domain, final T a, final T b, final Ref<? super T> dout, final Ref<? super T> uout, final Ref<? super T> vout)
  {
    T d = a;
    T e = b;
    T u = domain.identity();
    T v = domain.zero();
    T un = domain.zero();
    T vn = domain.identity();
    while (!domain.equals(e, domain.zero()))
      {
        if (!domain.equals(domain.add(domain.multiply(u, a), domain.multiply(v, b)), d))
          throw new RuntimeException();
        final Domain.DivisionResult<? extends T> divisionResult = domain.divide(d, e);
        final T unn = domain.subtract(u, domain.multiply(divisionResult.quotient(), un));
        final T vnn = domain.subtract(v, domain.multiply(divisionResult.quotient(), vn));
        d = e;
        e = divisionResult.remainder();
        u = un;
        un = unn;
        v = vn;
        vn = vnn;
      }
    if (!domain.equals(domain.add(domain.multiply(u, a), domain.multiply(v, b)), d))
      throw new RuntimeException();

    dout.set(d);
    uout.set(u);
    vout.set(v);
  }
 
  public static <T> T hcf(final Domain<T> domain, final T a, final T b)
  {
    final Ref<T> d = new Ref<T>();
    final Ref<T> u = new Ref<T>();
    final Ref<T> v = new Ref<T>();
    euclid(domain, a, b, d, u, v);
    return d.value();
  }
  
  public static <T> Map<? extends T, ? extends Integer> primeFactors(final Domain<T> domain, final T n)
  {
    if (domain.equals(n, domain.zero()))
      throw new IllegalArgumentException("cannot factorise zero");
    final Map<T, Integer> primeFactors = new HashMap<T, Integer>();
    T a = n;
    for (final T prime : domain.primesTo(n))
      {
        if (domain.norm(prime).pow(2).compareTo(domain.norm(a)) > 0)
          break;
        while (domain.norm(a).remainder(domain.norm(prime)).equals(BigInteger.ZERO))
          {
            final Domain.DivisionResult<? extends T> divisionResult = domain.divide(a, prime);
            if (!divisionResult.remainder().equals(domain.zero()))
              break;
            a = divisionResult.quotient();
            if (!primeFactors.containsKey(prime))
              primeFactors.put(prime, 0);
            primeFactors.put(prime, primeFactors.get(prime) + 1);
          }
      }
    if (domain.norm(a).compareTo(BigInteger.ONE) > 0)
      {
        final T prime = domain.canonicalPrime(a);
        if (!primeFactors.containsKey(prime))
          primeFactors.put(prime, 0);
        primeFactors.put(prime, primeFactors.get(prime) + 1);
      }
    return primeFactors;
  }
  
  private Domains() { }
}
