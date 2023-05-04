package xyz.stanforth.util;

public interface CachedIterable<T>
{
  CachedIterator<? extends T> iterator();
}
