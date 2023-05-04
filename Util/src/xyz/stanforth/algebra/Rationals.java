package xyz.stanforth.algebra;

public final class Rationals
{
  public static Rational reduce(final long numerator, final long denominator)
  {
    if (denominator == 0L)
      throw new IllegalArgumentException();
    final long d = Euclid.hcf(numerator, denominator);
    final long sgn = (denominator < 0L) ^ (d < 0L) ? -1L : +1L;
    return of(sgn*numerator/d, sgn*denominator/d);
  }
  
  private static Rational of(final long numerator, final long denominator)
  {
    return new Rational()
      {
        @Override public long numerator() { return numerator; }
        @Override public long denominator() { return denominator; }
        @Override public int hashCode() { return Rationals.hashCode(this); }
        @Override public boolean equals(final Object o) { return o instanceof Rational && Rationals.equals(this, (Rational)o); }
        @Override public String toString() { return Rationals.toString(this); }
      };
  }
  
  public static boolean equals(final Rational q0, final Rational q1)
  {
    return q0.numerator() == q1.numerator() && q0.denominator() == q1.denominator();
  }

  public static int hashCode(final Rational q)
  {
    return ((Long)q.numerator()).hashCode() + 3*((Long)q.denominator()).hashCode();
  }

  public static int compare(final Rational q0, final Rational q1)
  {
    return ((Long)(q0.numerator() * q1.denominator())).compareTo(q0.denominator() * q1.numerator())
      * ((Long)q0.denominator()).compareTo(0L) * ((Long)q1.denominator()).compareTo(0L);
  }

  public static String toString(final Rational q)
  {
    return ((Long)q.numerator()).toString() + "/" + ((Long)q.denominator()).toString();
  }
  
  public static Field<Rational> field()
  {
    return new Field<Rational>()
    {
      @Override
      public Rational zero()
      {
        return of(0L, 1L);
      }
      
      @Override
      public Rational negate(final Rational q)
      {
        return of(-q.numerator(), q.denominator());
      }
      
      @Override
      public Rational add(final Rational q0, final Rational q1)
      {
        final long h = Euclid.hcf(q0.denominator(), q1.denominator());
        return reduce(
                q0.numerator() * (q1.denominator()/h) + (q0.denominator()/h) * q1.numerator(),
                (q0.denominator()/h) * q1.denominator());
      }
      
      @Override
      public Rational subtract(final Rational q0, final Rational q1)
      {
        final long h = Euclid.hcf(q0.denominator(), q1.denominator());
        return reduce(
                q0.numerator() * (q1.denominator()/h) - (q0.denominator()/h) * q1.numerator(),
                (q0.denominator()/h) * q1.denominator());
      }

      @Override
      public Rational identity()
      {
        return of(1L, 1L);
      }
      
      @Override
      public Rational inverse(final Rational q)
      {
        return of(q.denominator(), q.numerator());
      }
      
      @Override
      public Rational multiply(final Rational q0, final Rational q1)
      {
        final long h = Euclid.hcf(q0.numerator(), q1.denominator());
        final long k = Euclid.hcf(q0.denominator(), q1.numerator());
        return reduce(
                (q0.numerator()/h) * (q1.numerator()/k),
                (q0.denominator()/k) * (q1.denominator()/h));
      }
      
      @Override
      public Rational divide(final Rational q0, final Rational q1)
      {
        final long h = Euclid.hcf(q0.denominator(), q1.denominator());
        final long k = Euclid.hcf(q0.numerator(), q1.numerator());
        return reduce(
                (q0.numerator()/k) * (q1.denominator()/h),
                (q0.denominator()/h) * (q1.numerator()/k));
      }
      
      @Override
      public double norm(final Rational q)
      {
        return q.numerator() == 0L ? 0. : 1.;
      }
      
      @Override
      public boolean equals(final Rational q0, final Rational q1)
      {
        return Rationals.equals(q0, q1);
      }

      @Override
      public int hashCode(final Rational q)
      {
        return Rationals.hashCode(q);
      }
      
      @Override
      public String toString(final Rational q)
      {
        return Rationals.toString(q);
      }
    };
  }
  
  private Rationals() { }
}
