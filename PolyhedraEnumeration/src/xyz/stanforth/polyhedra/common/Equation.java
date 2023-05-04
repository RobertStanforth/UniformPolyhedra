package xyz.stanforth.polyhedra.common;

import xyz.stanforth.algebra.RingMatrix;

public interface Equation<T>
{
  int index();
  String name();
  RingMatrix<? extends T> coeffs();
  T axx();
  T ayy();
  T azz();
  T a2xy();
  T a2yz();
  T a2zx();
} 
