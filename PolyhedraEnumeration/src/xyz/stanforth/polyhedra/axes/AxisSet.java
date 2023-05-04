package xyz.stanforth.polyhedra.axes;

import java.util.*;

import xyz.stanforth.algebra.Domain;
import xyz.stanforth.algebra.OrderedRing;
import xyz.stanforth.geometry.AngleChecker;
import xyz.stanforth.util.Function;

public interface AxisSet<T>
{
  Symmetry symmetry();
  Domain<T> ring();
  List<? extends Axis<T>> axes();
  <U> boolean isXZViable(boolean obliqueXY,
                         OrderedRing<U> ratioRing, Function<? super T, ? extends U> converter, U rxz);
  AngleChecker<T> angleChecker();
}
