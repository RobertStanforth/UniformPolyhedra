package xyz.stanforth.geometry;

import xyz.stanforth.algebra.OrderedRing;
import xyz.stanforth.util.Function;

public interface AngleChecker<T>
{
  <U> PolygonType polygonType(OrderedRing<U> ring, Function<? super T, ? extends U> converter, U scalarProd, U edgeLength2);
}
