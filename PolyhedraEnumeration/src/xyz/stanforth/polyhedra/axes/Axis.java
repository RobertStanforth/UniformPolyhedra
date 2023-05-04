package xyz.stanforth.polyhedra.axes;

import xyz.stanforth.algebra.RingMatrix;

public interface Axis<T>
{
  String name();
  RingMatrix<? extends T> rotation();
  int degree();
  boolean accept(Symmetry symmetry);
  boolean isRedundant(boolean obliqueXY);
}
