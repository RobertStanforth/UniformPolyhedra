package xyz.stanforth.algebra;

public interface Split<T>
{
  Ratio<? extends T> cpt(int i);
}
