package xyz.stanforth.algebra;

import xyz.stanforth.util.Function;

public final class Quads
{
  public static <T> Quad<? extends T> of(final OrderedRing<T> ring, final T root, final T a)
  {
    return of(ring, root, a, ring.zero());
  }
  
  public static <T> Quad<? extends T> of(final OrderedRing<T> ring, final T root, final T a, final T b)
  {
    return new Quad<T>()
    {
      @Override public T a() { return a; }
      @Override public T b() { return b; }
      @Override public int hashCode() { return Quads.hashCode(ring, root, this); }
      @SuppressWarnings("unchecked") @Override public boolean equals(final Object o) { return (o instanceof Quad<?>) && Quads.equals(ring, root, this, (Quad<T>)o); }
      @Override public String toString() { return Quads.toString(ring, root, this); }
    };
  }
  
  private static <T> int hashCode(final OrderedRing<T> ring, final T root, final Quad<? extends T> q)
  {
    return 37 * ring.hashCode(q.a()) + ring.hashCode(q.b());
  }

  private static <T> boolean equals(final OrderedRing<T> ring, final T root, final Quad<? extends T> q0, final Quad<? extends T> q1)
  {
    return ring.equals(q0.a(), q1.a()) && ring.equals(q0.b(), q1.b());
  }

  private static <T> String toString(final OrderedRing<T> ring, final T root, final Quad<? extends T> q)
  {
    return "(" + ring.toString(q.a()) + "+" + ring.toString(q.b()) + "\\sqrt{" + ring.toString(root) + "})";
  }
  
  public static <T> OrderedRing<Quad<? extends T>> ring(final OrderedRing<T> ring, final T root)
  {
    return new OrderedRing<Quad<? extends T>>()
    {
      @Override public Quad<? extends T> valueOf(final long n) { return of(ring, root, ring.valueOf(n)); }
      @Override public Quad<? extends T> zero() { return of(ring, root, ring.zero()); }
      @Override public Quad<? extends T> negate(final Quad<? extends T> q) { return of(ring, root, ring.negate(q.a()), ring.negate(q.b())); }
      @Override public Quad<? extends T> add(final Quad<? extends T> q0, final Quad<? extends T> q1) { return of(ring, root, ring.add(q0.a(), q1.a()), ring.add(q0.b(), q1.b())); }
      @Override public Quad<? extends T> subtract(final Quad<? extends T> q0, final Quad<? extends T> q1) { return of(ring, root, ring.subtract(q0.a(), q1.a()), ring.subtract(q0.b(), q1.b())); }
      @Override public Quad<? extends T> identity() { return of(ring, root, ring.identity()); }
      @Override public Quad<? extends T> multiply(final Quad<? extends T> q0, final Quad<? extends T> q1) { return of(ring, root, ring.add(ring.multiply(q0.a(), q1.a()), ring.multiply(root, ring.multiply(q0.b(), q1.b()))), ring.add(ring.multiply(q0.a(), q1.b()), ring.multiply(q0.b(), q1.a()))); }
      
      @Override public int hashCode(final Quad<? extends T> q) { return Quads.hashCode(ring, root, q); }
      @Override public boolean equals(final Quad<? extends T> q0, final Quad<? extends T> q1) { return Quads.equals(ring, root, q0, q1); }
      @Override public String toString(final Quad<? extends T> q) { return Quads.toString(ring, root, q); }

      @Override
      public int compare(final Quad<? extends T> q0, final Quad<? extends T> q1)
      {
        final Quad<? extends T> d = subtract(q0, q1);
        return ring.compare(d.a(), ring.zero()) > 0
                ? ring.compare(d.b(), ring.zero()) >= 0 ? +1 : ring.compare(ring.multiply(d.a(), d.a()), ring.multiply(root, ring.multiply(d.b(), d.b())))
                : ring.compare(d.b(), ring.zero()) < 0 ? -1 : ring.compare(ring.multiply(root, ring.multiply(d.b(), d.b())), ring.multiply(d.a(), d.a()));
      }
    };
  }
  
  public static <T> Function<? super T, ? extends Quad<? extends T>> converter(final OrderedRing<T> ring, final T root)
  {
    return (final T t) -> of(ring, root, t);
  }

  private Quads() { }
}
