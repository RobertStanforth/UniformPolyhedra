package xyz.stanforth.polyhedra.axes;

import xyz.stanforth.algebra.*;
import xyz.stanforth.geometry.AngleChecker;
import xyz.stanforth.geometry.AngleChecking;
import xyz.stanforth.util.Function;

import java.math.*;
import java.util.*;

public final class CubicAxisSets
{
  public static AxisSet<BigInteger> axes()
  {
    final List<? extends Axis<BigInteger>> axes = Arrays.asList(allAxes);

    final AngleChecker<BigInteger> angleChecker = AngleChecking.standardChecker();

    return new AxisSet<BigInteger>()
    {
      @Override public Symmetry symmetry() { return Symmetry.Cubocta; }
      @Override public Domain<BigInteger> ring() { return ring; }
      @Override public List<? extends Axis<BigInteger>> axes() { return axes; }
      
      @Override
      public <U> boolean isXZViable(final boolean obliqueXY,
                                    final OrderedRing<U> ratioRing, final Function<? super BigInteger, ? extends U> converter,
                                    final U rxz)
      {
        if (ratioRing.compare(rxz, ratioRing.zero()) <= 0)
          return false;
        if (obliqueXY)
          {
            if (ratioRing.equals(rxz, converter.apply(ring().identity())))
              return false;
          }
        else
          {
            if (ratioRing.compare(rxz, converter.apply(ring().identity())) >= 0)
              return false;
          }
        return true;
      }

      @Override public AngleChecker<BigInteger> angleChecker() { return angleChecker; }
    };
  }
  
  private static Domain<BigInteger> ring = BigIntegers.ring();

  @SuppressWarnings("unchecked")
  private static final Axis<BigInteger>[] allAxes = (Axis<BigInteger>[])new Axis<?>[]
   {
     axis("4x", new BigInteger[] {
             val(2), val(0), val(0),
             val(0), val(0), val(2),
             val(0), val(-2), val(0) }, 4, false, false),
     axis("4y", new BigInteger[] {
             val(0), val(0), val(-2),
             val(0), val(2), val(0),
             val(2), val(0), val(0) }, 4, false, true),
     axis("4z", new BigInteger[] {
             val(0), val(2), val(0),
             val(-2), val(0), val(0),
             val(0), val(0), val(2) }, 4, false, false),
     axis("3xyz", new BigInteger[] {
             val(0), val(2), val(0),
             val(0), val(0), val(2),
             val(2), val(0), val(0) }, 3, false, false),
     axis("3xyz'", new BigInteger[] {
             val(0), val(2), val(0),
             val(0), val(0), val(-2),
             val(-2), val(0), val(0) }, 3, false, false),
     axis("3xy'z", new BigInteger[] {
             val(0), val(-2), val(0),
             val(0), val(0), val(-2),
             val(2), val(0), val(0) }, 3, true, false),
     axis("3x'yz", new BigInteger[] {
             val(0), val(-2), val(0),
             val(0), val(0), val(2),
             val(-2), val(0), val(0) }, 3, true, true),
     axis("2xy", new BigInteger[] {
             val(0), val(2), val(0),
             val(2), val(0), val(0),
             val(0), val(0), val(-2) }, 2, false, false),
     axis("2xy'", new BigInteger[] {
             val(0), val(-2), val(0),
             val(-2), val(0), val(0),
             val(0), val(0), val(-2) }, 2, true, false),
     axis("2yz", new BigInteger[] {
             val(-2), val(0), val(0),
             val(0), val(0), val(2),
             val(0), val(2), val(0) }, 2, false, false),
     axis("2yz'", new BigInteger[] {
             val(-2), val(0), val(0),
             val(0), val(0), val(-2),
             val(0), val(-2), val(0) }, 2, true, false),
     axis("2zx", new BigInteger[] {
             val(0), val(0), val(2),
             val(0), val(-2), val(0),
             val(2), val(0), val(0) }, 2, false, true),
     axis("2zx'", new BigInteger[] {
             val(0), val(0), val(-2),
             val(0), val(-2), val(0),
             val(-2), val(0), val(0) }, 2, false, true),
   };

  private static Axis<BigInteger> axis(
          final String name,
          final BigInteger[] rotationElts,
          final int degree,
          final boolean redundant_x0,
          final boolean redundant_xy
          )
  {
    final RingMatrices.Builder<BigInteger> rotationBuilder = RingMatrices.builder(ring, 3);
    for (int i = 0; i < 3; i ++)
      for (int j = 0; j < 3; j ++)
        rotationBuilder.set(i, j, rotationElts[i*3+j]);
    final RingMatrix<? extends BigInteger> rotation = rotationBuilder.build();
    return new Axis<BigInteger>()
    {
      @Override public String name() { return name; }
      @Override public RingMatrix<? extends BigInteger> rotation() { return rotation; }
      @Override public int degree() { return degree; }
      @Override public boolean accept(final Symmetry symmetry) { return true; }
      
      @Override
      public boolean isRedundant(final boolean obliqueXY)
      {
        return obliqueXY ? redundant_xy : redundant_x0;
      }
    };
  }
  
  private static BigInteger val(final long aa)
  {
    return BigInteger.valueOf(aa);
  }
  
  private CubicAxisSets() { }
}
