package xyz.stanforth.util;

public interface CachedIterator<T>
{
  boolean hasItem();
  T item();
  void next();
}
