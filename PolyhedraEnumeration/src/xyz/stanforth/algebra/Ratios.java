package xyz.stanforth.algebra;

import xyz.stanforth.util.Function;

public final class Ratios
{
  public static <T> Ratio<? extends T> unreduced(final OrderedRing<T> ring, final T t)
  {
    return unreduced(ring, t, ring.identity());
  }
  
  public static <T> Ratio<? extends T> unreduced(final OrderedRing<T> ring, final T num, final T denom)
  {
    return of(ring, null, num, denom);
  }
  
  public static <T> Ratio<? extends T> of(final Domain<T> ring, final T t)
  {
    return of(ring, t, ring.identity());
  }
  
  public static <T> Ratio<? extends T> of(final Domain<T> ring, final T num, final T denom)
  {
    return of(ring, ring, num, denom);
  }
  
  private static <T> Ratio<? extends T> of(final OrderedRing<T> ring, final Domain<T> ringAsDomain, T num, T denom)
  {
    if (ringAsDomain != null)
      {
        final T hcf = Domains.hcf(ringAsDomain, num, denom);
        if (ring.equals(hcf, ring.zero()))
          throw new IllegalArgumentException();
        num = Domains.divide(ringAsDomain, num, hcf);
        denom = Domains.divide(ringAsDomain, denom, hcf);
        if (ring.compare(num, ring.zero()) < 0)
          {
            num = ring.negate(num);
            denom = ring.negate(denom);
          }
        return cons(ringAsDomain, num, denom);
      }
    else
      {
        return consUnreduced(ring, num, denom);
      }
  }
  
  private static <T> Ratio<? extends T> consUnreduced(final OrderedRing<T> ring, final T num, final T denom)
  {
    return new Ratio<T>()
    {
      @Override public T num() { return num; }
      @Override public T denom() { return denom; }
      
      @Override
      public int hashCode()
      {
        return 37 * ring.hashCode(num()) + ring.hashCode(denom());
      }
      
      @Override
      @SuppressWarnings("unchecked")
      public boolean equals(final Object o)
      {
        return (o instanceof Ratio<?>)
          && ring.equals(num(), ((Ratio<? extends T>)o).num())
          && ring.equals(denom(), ((Ratio<? extends T>)o).denom());
      }
      
      @Override public String toString() { return Ratios.toString(ring, this); }
    };
  }

  private static <T> Ratio<? extends T> cons(final Domain<T> ring, final T num, final T denom)
  {
    return new Ratio<T>()
    {
      @Override public T num() { return num; }
      @Override public T denom() { return denom; }
      @Override public int hashCode() { return Ratios.hashCode(ring, this); }
      @Override @SuppressWarnings("unchecked") public boolean equals(final Object o) { return (o instanceof Ratio<?>) && Ratios.equals(ring, this, (Ratio<? extends T>)o); }
      @Override public String toString() { return Ratios.toString(ring, this); }
    };
  }

  private static <T> int hashCode(final Domain<T> ring, final Ratio<? extends T> q)
  {
    if (ring == null)
      throw new UnsupportedOperationException();
    return 37 * ring.norm(q.num()).hashCode() + ring.norm(q.denom()).hashCode();
  }
  
  private static <T> boolean equals(final OrderedRing<T> ring, final Ratio<? extends T> q0, final Ratio<? extends T> q1)
  {
    return ring.equals(ring.multiply(q0.num(), q1.denom()), ring.multiply(q1.num(), q0.denom()));
  }
  
  private static <T> String toString(final OrderedRing<T> ring, final Ratio<? extends T> q)
  {
    return "(" + ring.toString(q.num()) + "/" + ring.toString(q.denom()) + ")";
  }
  
  public static <T> OrderedField<Ratio<? extends T>> fieldUnreduced(final OrderedRing<T> ring)
  {
    return field(ring, null);
  }
  
  public static <T> OrderedField<Ratio<? extends T>> field(final Domain<T> ring)
  {
    return field(ring, ring);
  }
  
  private static <T> OrderedField<Ratio<? extends T>> field(final OrderedRing<T> ring, final Domain<T> ringAsDomain)
  {
    return new OrderedField<Ratio<? extends T>>()
    {
      @Override public Ratio<? extends T> valueOf(final long n) { return of(ring, ringAsDomain, ring.valueOf(n), ring.identity()); }
      @Override public Ratio<? extends T> zero() { return of(ring, ringAsDomain, ring.zero(), ring.identity()); }
      @Override public Ratio<? extends T> negate(final Ratio<? extends T> q) { return of(ring, ringAsDomain, ring.negate(q.num()), q.denom()); }
      @Override public Ratio<? extends T> add(final Ratio<? extends T> q0, final Ratio<? extends T> q1) { return of(ring, ringAsDomain, ring.add(ring.multiply(q0.num(), q1.denom()), ring.multiply(q0.denom(), q1.num())), ring.multiply(q0.denom(), q1.denom())); }
      @Override public Ratio<? extends T> subtract(final Ratio<? extends T> q0, final Ratio<? extends T> q1) { return of(ring, ringAsDomain, ring.subtract(ring.multiply(q0.num(), q1.denom()), ring.multiply(q0.denom(), q1.num())), ring.multiply(q0.denom(), q1.denom())); }
      @Override public Ratio<? extends T> identity() { return of(ring, ringAsDomain, ring.identity(), ring.identity()); }
      @Override public Ratio<? extends T> multiply(final Ratio<? extends T> q0, final Ratio<? extends T> q1) { return of(ring, ringAsDomain, ring.multiply(q0.num(), q1.num()), ring.multiply(q0.denom(), q1.denom())); }
      
      @Override
      public Ratio<? extends T> inverse(final Ratio<? extends T> q)
      {
        if (ring.equals(q.num(), ring.zero()))
          throw new IllegalArgumentException("division by zero");
        return of(ring, ringAsDomain, q.denom(), q.num());
      }
      
      @Override public Ratio<? extends T> divide(final Ratio<? extends T> q0, final Ratio<? extends T> q1) { return multiply(q0, inverse(q1)); }
      @Override public double norm(final Ratio<? extends T> q) { return 1.; }

      @Override public int hashCode(final Ratio<? extends T> q) { return Ratios.hashCode(ringAsDomain, q); }
      @Override public boolean equals(final Ratio<? extends T> q0, final Ratio<? extends T> q1) { return Ratios.equals(ring, q0, q1); }
      @Override public String toString(final Ratio<? extends T> q) { return Ratios.toString(ring, q); }

      @Override
      public int compare(final Ratio<? extends T> q0, final Ratio<? extends T> q1)
      {
        return ring.compare(q0.denom(), ring.zero())
            * ring.compare(q1.denom(), ring.zero())
            * ring.compare(ring.multiply(q0.num(), q1.denom()), ring.multiply(q0.denom(), q1.num()));
      }
    };
  }
  
  public static <T> Function<? super T, ? extends Ratio<? extends T>> converterUnreduced(final OrderedRing<T> ring)
  {
    return converter(ring, null);
  }

  public static <T> Function<? super T, ? extends Ratio<? extends T>> converter(final Domain<T> ring)
  {
    return converter(ring, ring);
  }
          
  private static <T> Function<? super T, ? extends Ratio<? extends T>> converter(final OrderedRing<T> ring, final Domain<T> ringAsDomain)
  {
    return (final T t) -> of(ring, ringAsDomain, t, ring.identity());
  }
}
