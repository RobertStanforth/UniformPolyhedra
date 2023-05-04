package xyz.stanforth.algebra;

import xyz.stanforth.util.Function;

public interface SplittingField<T> extends OrderedRing<Split<? extends T>>
{
  Function<? super T, ? extends Split<? extends T>> converter();
  Split<? extends T> root();
}
