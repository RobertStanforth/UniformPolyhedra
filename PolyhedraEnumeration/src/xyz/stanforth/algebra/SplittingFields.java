package xyz.stanforth.algebra;

import xyz.stanforth.util.Function;

import java.util.*;

public final class SplittingFields
{
  public static <T> SplittingField<T> field(final Domain<T> ring, final Polynomial<? extends T> poly)
  {
    final int degree = poly.degree();
    if (degree <= 0)
      throw new IllegalArgumentException();
    final OrderedField<Ratio<? extends T>> rationalField = Ratios.field(ring);
    
    return new SplittingField<T>()
    {
      private final List<? extends Split<? extends T>> powers;
      {
        final List<Split<? extends T>> powers = new ArrayList<>(2*degree);
        {
          final List<Ratio<? extends T>> cpts = new ArrayList<>(degree);
          for (int i = 0; i < degree; i ++)
            cpts.add(i == 0 ? rationalField.identity() : rationalField.zero());
          powers.add(of(cpts));
        }
        for (int j = 0; j < 2*degree-1; j ++)
          {
            final List<Ratio<? extends T>> cpts = new ArrayList<>(degree);
            for (int i = 0; i < degree; i ++)
              cpts.add(rationalField.subtract(i == 0 ? rationalField.zero() : powers.get(j).cpt(i-1),
                          rationalField.multiply(powers.get(j).cpt(degree-1), Ratios.of(ring, poly.coeffs().get(i), poly.coeffs().get(degree)))));
            powers.add(of(cpts));
          }
        this.powers = powers;
      }
        
      @Override
      public Split<? extends T> valueOf(final long n)
      {
        final List<Ratio<? extends T>> cpts = new ArrayList<>(degree);
        cpts.add(rationalField.valueOf(n));
        for (int i = 1; i < degree; i ++)
          cpts.add(rationalField.zero());
        return of(cpts);
      }

      @Override
      public Split<? extends T> zero()
      {
        final List<Ratio<? extends T>> cpts = new ArrayList<>(degree);
        for (int i = 0; i < degree; i ++)
          cpts.add(rationalField.zero());
        return of(cpts);
      }
      
      @Override
      public Split<? extends T> negate(final Split<? extends T> s)
      {
        final List<Ratio<? extends T>> cpts = new ArrayList<>(degree);
        for (int i = 0; i < degree; i ++)
          cpts.add(rationalField.negate(s.cpt(i)));
        return of(cpts);
      }
      
      @Override
      public Split<? extends T> add(final Split<? extends T> s0, final Split<? extends T> s1)
      {
        final List<Ratio<? extends T>> cpts = new ArrayList<>(degree);
        for (int i = 0; i < degree; i ++)
          cpts.add(rationalField.add(s0.cpt(i), s1.cpt(i)));
        return of(cpts);
      }
      
      @Override
      public Split<? extends T> subtract(final Split<? extends T> s0, final Split<? extends T> s1)
      {
        final List<Ratio<? extends T>> cpts = new ArrayList<>(degree);
        for (int i = 0; i < degree; i ++)
          cpts.add(rationalField.subtract(s0.cpt(i), s1.cpt(i)));
        return of(cpts);
      }
      
      @Override
      public Split<? extends T> identity()
      {
        final List<Ratio<? extends T>> cpts = new ArrayList<>(degree);
        cpts.add(rationalField.identity());
        for (int i = 1; i < degree; i ++)
          cpts.add(rationalField.zero());
        return of(cpts);
      }
      
      @Override
      public Split<? extends T> multiply(final Split<? extends T> s0, final Split<? extends T> s1)
      {
        final List<Ratio<? extends T>> cpts = new ArrayList<>(degree);
        for (int i = 0; i < degree; i ++)
          cpts.add(rationalField.zero());
        for (int j = 0; j < 2*degree; j ++)
          {
            Ratio<? extends T> a = rationalField.zero();
            for (int i0 = 0; i0 < degree && i0 <= j; i0 ++)
              {
                final int i1 = j - i0;
                if (i1 < degree)
                  a = rationalField.add(a, rationalField.multiply(s0.cpt(i0), s1.cpt(i1)));
              }
            for (int i = 0; i < degree; i ++)
              cpts.set(i, rationalField.add(cpts.get(i), rationalField.multiply(powers.get(j).cpt(i), a)));
          }
        return of(cpts);
      }
      
      @Override
      public Function<? super T, ? extends Split<? extends T>> converter()
      {
        return (final T t) -> scalar(t);
      }
      
      private Split<? extends T> scalar(final T t)
      {
        final List<Ratio<? extends T>> cpts = new ArrayList<>(degree);
        cpts.add(Ratios.of(ring, t));
        for (int i = 1; i < degree; i ++)
          cpts.add(rationalField.zero());
        return of(cpts);
      }
      
      @Override
      public Split<? extends T> root()
      {
        return powers.get(1);
      }
      
      private Split<? extends T> of(final List<? extends Ratio<? extends T>> cpts)
      {
        return new Split<T>()
        {
          @Override public Ratio<? extends T> cpt(final int i) { return cpts.get(i); }
          @Override public int hashCode() { return _hashCode(this); }
          @Override @SuppressWarnings("unchecked") public boolean equals(final Object o) { return o instanceof Split<?> && _equals(this, (Split<? extends T>)o); }
          @Override public String toString() { return _toString(this); }
        };
      }
      
      @Override
      public int hashCode(final Split<? extends T> s)
      {
        int hashCode = 0;
        for (int i = 0; i < degree; i ++)
          {
            hashCode *= 61;
            hashCode += rationalField.hashCode(s.cpt(i));
          }
        return hashCode;
      }
      
      @Override
      public boolean equals(final Split<? extends T> s0, final Split<? extends T> s1)
      {
        for (int i = 0; i < degree; i ++)
          if (!rationalField.equals(s0.cpt(i), s1.cpt(i)))
            return false;
        return true;
      }
      
      @Override
      public String toString(final Split<? extends T> s)
      {
        String str = "[" + rationalField.toString(s.cpt(0));
        for (int i = 1; i < degree; i ++)
          {
            str += "+" + rationalField.toString(s.cpt(i)) + "x";
            if (i == 2)
              str += "\u00A2";
            else if (i == 3)
              str += "\u00A3";
            else if (i >= 4)
              str += "^" + String.valueOf(i);
          }
        str += "]";
        return str;
      }
      
      @Override public int compare(final Split<? extends T> s0, final Split<? extends T> s1) { return 0; }
      
      private int _hashCode(final Split<? extends T> s) { return hashCode(s); }
      private boolean _equals(final Split<? extends T> s0, final Split<? extends T> s1) { return equals(s0, s1); }
      private String _toString(final Split<? extends T> s) { return toString(s); }
    };
  }
  
  private SplittingFields() { }
}
