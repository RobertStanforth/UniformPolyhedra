package xyz.stanforth.algebra;

public interface Field<T> extends Ring<T>
{
  T inverse(T t);
  T divide(T t0, T t1);
  
  double norm(T t);
}
