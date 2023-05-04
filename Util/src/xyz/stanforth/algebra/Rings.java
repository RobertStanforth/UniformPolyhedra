package xyz.stanforth.algebra;

public final class Rings
{
  public static <T> T multiple(final Ring<T> ring, final T a, final long i)
  {
    T res = ring.zero();
    for (long j = Long.highestOneBit(i); j > 0L; j >>= 1L)
      {
        res = ring.add(res, res);
        if ((i & j) != 0L)
          res = ring.add(a, res);
      }
    return res;
  }
  
  public static <T> T pow(final Ring<T> ring, final T a, final long i)
  {
    T res = ring.identity();
    for (long j = Long.highestOneBit(i); j > 0L; j >>= 1L)
      {
        res = ring.multiply(res, res);
        if ((i & j) != 0L)
          res = ring.multiply(a, res);
      }
    return res;
  }
  
  public static Field<Double> doubleField()
  {
    return new Field<Double>()
    {
      @Override
      public Double zero()
      {
        return 0.;
      }
      
      @Override
      public Double negate(final Double q)
      {
        return -q;
      }
      
      @Override
      public Double add(final Double q0, final Double q1)
      {
        return q0+q1;
      }
      
      @Override
      public Double subtract(final Double q0, final Double q1)
      {
        return q0-q1;
      }

      @Override
      public Double identity()
      {
        return 1.;
      }
      
      @Override
      public Double inverse(final Double q)
      {
        return 1./q;
      }
      
      @Override
      public Double multiply(final Double q0, final Double q1)
      {
        return q0 * q1;
      }
      
      @Override
      public Double divide(final Double q0, final Double q1)
      {
        return q0 / q1;
      }
      
      @Override
      public double norm(final Double q)
      {
        return Math.abs(q);
      }
      
      @Override
      public boolean equals(final Double q0, final Double q1)
      {
        return q0.equals(q1);
      }

      @Override
      public int hashCode(final Double q)
      {
        return q.hashCode();
      }
      
      @Override
      public String toString(final Double q)
      {
        return q.toString();
      }
    };
  }

  private Rings() { }
}
