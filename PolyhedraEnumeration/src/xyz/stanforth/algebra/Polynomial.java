package xyz.stanforth.algebra;

import java.util.*;

public interface Polynomial<T>
{
  List<? extends T> coeffs();
  int degree();
  int numRealRoots();
}
