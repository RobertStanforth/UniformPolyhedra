package xyz.stanforth.util;

@FunctionalInterface
public interface Function<T, U>
{
  U apply(T t);
}
