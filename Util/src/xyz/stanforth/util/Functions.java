package xyz.stanforth.util;

public final class Functions
{
  public static <T> Function<? super T, ? extends T> identity()
  {
    return (final T t) -> t;
  }

  public static <T, U, V> Function<? super T, ? extends V> compose(final Function<? super T, ? extends U> f, final Function<? super U, ? extends V> g)
  {
    return (final T t) -> g.apply(f.apply(t));
  }
  
  private Functions() { }
}
