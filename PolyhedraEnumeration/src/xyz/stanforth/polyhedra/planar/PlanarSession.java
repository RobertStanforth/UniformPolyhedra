package xyz.stanforth.polyhedra.planar;

import java.util.*;

import xyz.stanforth.algebra.OrderedRing;
import xyz.stanforth.util.Function;

public interface PlanarSession<T>
{
  boolean obliqueXY();
  List<? extends PlanarEquation<T>> equations();
  <U> boolean isXZViable(OrderedRing<U> ratioRing, Function<? super T, ? extends U> converter, U rxz);
}
