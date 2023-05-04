package xyz.stanforth.geometry;

import xyz.stanforth.algebra.Ring;

import java.util.*;

public final class Geometry
{
  public static <T> T scalarProduct(final Ring<T> ring, final int size, final List<? extends T> v0, final List<? extends T> v1)
  {
    T prod = ring.zero();
    for (int i = 0; i < size; i ++)
      prod = ring.add(prod, ring.multiply(v0.get(i), v1.get(i)));
    return prod;
  }

  public static <T> List<? extends T> vectorProduct(final Ring<T> ring, final List<? extends T> v0, final List<? extends T> v1)
  {
    final List<T> v = new ArrayList<>();
    v.add(ring.subtract(ring.multiply(v0.get(1), v1.get(2)), ring.multiply(v0.get(2), v1.get(1))));
    v.add(ring.subtract(ring.multiply(v0.get(2), v1.get(0)), ring.multiply(v0.get(0), v1.get(2))));
    v.add(ring.subtract(ring.multiply(v0.get(0), v1.get(1)), ring.multiply(v0.get(1), v1.get(0))));
    return v;
  }
  
  private Geometry() { }
}
