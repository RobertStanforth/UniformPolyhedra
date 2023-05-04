package xyz.stanforth.algebra;

public interface Ring<T>
{
  T zero();
  T negate(T t);
  T add(T t0, T t1);
  T subtract(T t0, T t1);
  T identity();
  T multiply(T t0, T t1);
  
  boolean equals(T t0, T t1);
  int hashCode(T t);
  String toString(T t);
}
