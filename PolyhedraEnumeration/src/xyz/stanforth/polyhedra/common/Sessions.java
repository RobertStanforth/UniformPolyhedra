package xyz.stanforth.polyhedra.common;

import xyz.stanforth.algebra.*;
import xyz.stanforth.polyhedra.axes.Axis;
import xyz.stanforth.polyhedra.axes.AxisSet;

import java.util.*;

public final class Sessions
{
  public static <T> Session<T> session(final AxisSet<T> axisSet)
  {
    final Domain<T> ring = axisSet.ring();
    
    final Map<String, Axis<T>> axesByName = new HashMap<>();
    for (final Axis<T> axis : axisSet.axes())
      {
        validate(ring, axis);
        axesByName.put(axis.name(), axis);
      }
    
    return new Session<T>()
    {
      @Override public AxisSet<T> axes() { return axisSet; }
      @Override public Axis<T> axisForName(final String name) { return axesByName.get(name); }

      @Override
      public RingMatrix<? extends T> axisRotation(final Axis<T> axis, final int power)
      {
        final RingMatrix<? extends T> powR = RingMatrices.power(ring, 3, axis.rotation(), power);
        final T pow2 = Rings.pow(ring, ring.valueOf(2L), power);
        final RingMatrices.Builder<T> rotationBuilder = RingMatrices.builder(ring, 3);
        for (int i = 0; i < 3; i ++)
          for (int j = 0; j < 3; j ++)
            rotationBuilder.add(i, j, Domains.divide(ring, ring.multiply(ring.valueOf(2L), powR.elt(i, j)), pow2));
        return rotationBuilder.build();
      }

      @Override
      public RingMatrix<? extends T> identityTransform()
      {
        return Matrices.scalarMultiply(ring, ring.valueOf(2L), RingMatrices.identity(ring, 3));
      }

      @Override
      public RingMatrix<? extends T> diff(final RingMatrix<? extends T> transform)
      {
        return Matrices.subtract(ring, transform, Matrices.scalarMultiply(ring, ring.valueOf(2L), RingMatrices.identity(ring, 3)));
      }
      
      @Override
      public Equation<T> equation(final int index, final Axis<T> axis, final int power, final boolean inverse)
      {
        final RingMatrix<? extends T> rotation = axisRotation(axis, power);
        final RingMatrix<? extends T> diff = diff(inverse ? Matrices.negate(ring, rotation) : rotation);
        final RingMatrix<? extends T> coeffs = Matrices.add(ring, diff, Matrices.transpose(diff));
        final T axx = ring.negate(Domains.divide(ring, ring.add(diff.elt(0, 0), diff.elt(0, 0)), ring.valueOf(2L)));
        final T ayy = ring.negate(Domains.divide(ring, ring.add(diff.elt(1, 1), diff.elt(1, 1)), ring.valueOf(2L)));
        final T azz = ring.negate(Domains.divide(ring, ring.add(diff.elt(2, 2), diff.elt(2, 2)), ring.valueOf(2L)));
        final T a2xy = ring.negate(Domains.divide(ring, ring.add(diff.elt(0, 1), diff.elt(1, 0)), ring.valueOf(2L)));
        final T a2yz = ring.negate(Domains.divide(ring, ring.add(diff.elt(1, 2), diff.elt(2, 1)), ring.valueOf(2L)));
        final T a2zx = ring.negate(Domains.divide(ring, ring.add(diff.elt(2, 0), diff.elt(0, 2)), ring.valueOf(2L)));
        return new Equation<T>()
        {
          @Override public int index() { return index; }
          @Override public String name() { return (inverse ? "-" : "") + axis.name() + powerSuffix[power]; }
          @Override public RingMatrix<? extends T> coeffs() { return coeffs; }
          @Override public T axx() { return axx; }
          @Override public T ayy() { return ayy; }
          @Override public T azz() { return azz; }
          @Override public T a2xy() { return a2xy; }
          @Override public T a2yz() { return a2yz; }
          @Override public T a2zx() { return a2zx; }
        };
      }
      
      @Override
      public void forIdentityAndAxisRotations(final RunnableWithTransform<? super T> transformCollector)
      {
        transformCollector.run(identityTransform());
        for (final Axis<T> axis : axes().axes())
          for (int power = 1; power < axis.degree(); power ++)
            transformCollector.run(axisRotation(axis, power));
      }
    };
  }
  
  private static <T> void validate(final Domain<T> ring, final Axis<T> axis)
  {
    // Validate that the matrix is orthogonal.
    if (!Matrices.equals(ring, 3,
            RingMatrices.product(ring, 3, axis.rotation(), Matrices.transpose(axis.rotation())),
            Matrices.scalarMultiply(ring, ring.valueOf(4L), RingMatrices.identity(ring, 3))))
      throw new RuntimeException();

    // Validate that the matrix has the stated degree.
    if (!Matrices.equals(ring, 3,
            RingMatrices.power(ring, 3, axis.rotation(), axis.degree()),
            Matrices.scalarMultiply(ring, Rings.pow(ring, ring.valueOf(2L), axis.degree()), RingMatrices.identity(ring, 3))))
      throw new RuntimeException();
  }

  private static final String[] powerSuffix = { null, "", "\u00A2", "\u00A3" };

  private Sessions() { }
}
