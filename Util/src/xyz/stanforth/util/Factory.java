package xyz.stanforth.util;

@FunctionalInterface
public interface Factory<T>
{
  T create();
}
