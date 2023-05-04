package xyz.stanforth.algebra;

import java.util.*;

public interface OrderedRing<T> extends Ring<T>, Comparator<T>
{
  T valueOf(long n);
}
