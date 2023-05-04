package xyz.stanforth.algebra;

public final class Matrices
{
  public static <T> RingMatrix<? extends T> transpose(final RingMatrix<? extends T> matrix)
  {
    return new RingMatrix<T>()
    {
      @Override public T elt(final int i, final int j) { return matrix.elt(j, i); }
    };
  }
  
  public static <T> RingMatrix<? extends T> zero(final Ring<T> ring)
  {
    return new RingMatrix<T>()
    {
      @Override public T elt(final int i, final int j) { return ring.zero(); }
    };
  }
  
  public static <T> RingMatrix<? extends T> negate(final Ring<T> ring, final RingMatrix<? extends T> matrix)
  {
    return new RingMatrix<T>()
    {
      @Override public T elt(final int i, final int j) { return ring.negate(matrix.elt(i, j)); }
    };
  }
  
  public static <T> RingMatrix<? extends T> add(final Ring<T> ring, final RingMatrix<? extends T> matrix0, final RingMatrix<? extends T> matrix1)
  {
    return new RingMatrix<T>()
    {
      @Override public T elt(final int i, final int j) { return ring.add(matrix0.elt(i, j), matrix1.elt(i, j)); }
    };
  }
  
  public static <T> RingMatrix<? extends T> subtract(final Ring<T> ring, final RingMatrix<? extends T> matrix0, final RingMatrix<? extends T> matrix1)
  {
    return new RingMatrix<T>()
    {
      @Override public T elt(final int i, final int j) { return ring.subtract(matrix0.elt(i, j), matrix1.elt(i, j)); }
    };
  }
  
  public static <T> RingMatrix<? extends T> scalarMultiply(final Ring<T> ring, final T scalar, final RingMatrix<? extends T> matrix)
  {
    return new RingMatrix<T>()
    {
      @Override public T elt(final int i, final int j) { return ring.multiply(scalar, matrix.elt(i, j)); }
    };
  }
  
  public static <T> boolean equals(final Ring<T> ring, final int size, final RingMatrix<? extends T> m0, final RingMatrix<? extends T> m1)
  {
    for (int i = 0; i < size; i ++)
      for (int j = 0; j < size; j ++)
        if (!ring.equals(m0.elt(i, j), m1.elt(i, j)))
          return false;
    return true;
  }
}
