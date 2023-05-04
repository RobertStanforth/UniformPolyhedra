package xyz.stanforth.polyhedra.axes;

import xyz.stanforth.algebra.*;
import xyz.stanforth.geometry.AngleChecker;
import xyz.stanforth.geometry.AngleChecking;
import xyz.stanforth.util.Function;

import java.math.*;
import java.util.*;
import java.util.stream.*;

public final class AxisSets
{
  public static AxisSet<GoldenInteger> axes(final Symmetry symmetry)
  {
    final List<? extends Axis<GoldenInteger>> axes = Arrays.asList(allAxes).stream()
            .filter((final Axis<GoldenInteger> axis) -> axis.accept(symmetry))
            .collect(Collectors.toList());

    final AngleChecker<GoldenInteger> angleChecker = AngleChecking.goldenChecker(goldenRing, GoldenIntegers.of(0, 1));

    return new AxisSet<GoldenInteger>()
    {
      @Override public Symmetry symmetry() { return symmetry; }
      @Override public Domain<GoldenInteger> ring() { return goldenRing; }
      @Override public List<? extends Axis<GoldenInteger>> axes() { return axes; }
      
      @Override
      public <U> boolean isXZViable(final boolean obliqueXY,
                                    final OrderedRing<U> ratioRing, final Function<? super GoldenInteger, ? extends U> converter,
                                    final U rxz)
      {
        if (ratioRing.compare(rxz, ratioRing.zero()) <= 0)
          return false;
        if (symmetry == Symmetry.Cubocta)
          {
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
          }
        else if (symmetry == Symmetry.Icosidodeca)
          {
            if (ratioRing.equals(rxz, converter.apply(gold(1L, 1L))))
              return false;
            if (ratioRing.equals(rxz, converter.apply(gold(-1L, 1L))))
              return false;
          }
        return true;
      }

      @Override public AngleChecker<GoldenInteger> angleChecker() { return angleChecker; }
    };
  }
  
  private static Domain<GoldenInteger> goldenRing = GoldenIntegers.ring();

  @SuppressWarnings("unchecked")
  private static final Axis<GoldenInteger>[] allAxes = (Axis<GoldenInteger>[])new Axis<?>[]
   {
     axis("4x", new GoldenInteger[] {
             gold(2, 0), gold(0, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(2, 0),
             gold(0, 0), gold(-2, 0), gold(0, 0) }, 4, true, false, false, false),
     axis("4y", new GoldenInteger[] {
             gold(0, 0), gold(0, 0), gold(-2, 0),
             gold(0, 0), gold(2, 0), gold(0, 0),
             gold(2, 0), gold(0, 0), gold(0, 0) }, 4, true, false, false, true),
     axis("4z", new GoldenInteger[] {
             gold(0, 0), gold(2, 0), gold(0, 0),
             gold(-2, 0), gold(0, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(2, 0) }, 4, true, false, false, false),
     axis("3xyz", new GoldenInteger[] {
             gold(0, 0), gold(2, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(2, 0),
             gold(2, 0), gold(0, 0), gold(0, 0) }, 3, true, true, false, false),
     axis("3xyz'", new GoldenInteger[] {
             gold(0, 0), gold(2, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(-2, 0),
             gold(-2, 0), gold(0, 0), gold(0, 0) }, 3, true, true, false, false),
     axis("3xy'z", new GoldenInteger[] {
             gold(0, 0), gold(-2, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(-2, 0),
             gold(2, 0), gold(0, 0), gold(0, 0) }, 3, true, true, true, false),
     axis("3x'yz", new GoldenInteger[] {
             gold(0, 0), gold(-2, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(2, 0),
             gold(-2, 0), gold(0, 0), gold(0, 0) }, 3, true, true, true, true),
     axis("2xy", new GoldenInteger[] {
             gold(0, 0), gold(2, 0), gold(0, 0),
             gold(2, 0), gold(0, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(-2, 0) }, 2, true, false, false, false),
     axis("2xy'", new GoldenInteger[] {
             gold(0, 0), gold(-2, 0), gold(0, 0),
             gold(-2, 0), gold(0, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(-2, 0) }, 2, true, false, true, false),
     axis("2yz", new GoldenInteger[] {
             gold(-2, 0), gold(0, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(2, 0),
             gold(0, 0), gold(2, 0), gold(0, 0) }, 2, true, false, false, false),
     axis("2yz'", new GoldenInteger[] {
             gold(-2, 0), gold(0, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(-2, 0),
             gold(0, 0), gold(-2, 0), gold(0, 0) }, 2, true, false, true, false),
     axis("2zx", new GoldenInteger[] {
             gold(0, 0), gold(0, 0), gold(2, 0),
             gold(0, 0), gold(-2, 0), gold(0, 0),
             gold(2, 0), gold(0, 0), gold(0, 0) }, 2, true, false, false, true),
     axis("2zx'", new GoldenInteger[] {
             gold(0, 0), gold(0, 0), gold(-2, 0),
             gold(0, 0), gold(-2, 0), gold(0, 0),
             gold(-2, 0), gold(0, 0), gold(0, 0) }, 2, true, false, false, true),
     axis("5z;x", new GoldenInteger[] {
             gold(1, 0), gold(0, -1), gold(-1, 1),
             gold(0, 1), gold(-1, 1), gold(-1, 0),
             gold(-1, 1), gold(1, 0), gold(0, 1) }, 5, false, true, false, false),
     axis("5z;x'", new GoldenInteger[] {
             gold(1, 0), gold(0, 1), gold(1, -1),
             gold(0, -1), gold(-1, 1), gold(-1, 0),
             gold(1, -1), gold(1, 0), gold(0, 1) }, 5, false, true, false, false),
     axis("5x;y", new GoldenInteger[] {
             gold(0, 1), gold(-1, 1), gold(1, 0),
             gold(-1, 1), gold(1, 0), gold(0, -1),
             gold(-1, 0), gold(0, 1), gold(-1, 1) }, 5, false, true, false, false),
     axis("5x;y'", new GoldenInteger[] {
             gold(0, 1), gold(1, -1), gold(1, 0),
             gold(1, -1), gold(1, 0), gold(0, 1),
             gold(-1, 0), gold(0, -1), gold(-1, 1) }, 5, false, true, true, false),
     axis("5y;z", new GoldenInteger[] {
             gold(-1, 1), gold(-1, 0), gold(0, 1),
             gold(1, 0), gold(0, 1), gold(-1, 1),
             gold(0, -1), gold(-1, 1), gold(1, 0) }, 5, false, true, false, false),
     axis("5y;z'", new GoldenInteger[] {
             gold(-1, 1), gold(-1, 0), gold(0, -1),
             gold(1, 0), gold(0, 1), gold(1, -1),
             gold(0, 1), gold(1, -1), gold(1, 0) }, 5, false, true, true, false),
     axis("3z;y", new GoldenInteger[] {
             gold(-1, 0), gold(0, -1), gold(-1, 1),
             gold(0, 1), gold(1, -1), gold(1, 0),
             gold(1, -1), gold(1, 0), gold(0, 1) }, 3, false, true, false, false),
     axis("3z;y'", new GoldenInteger[] {
             gold(-1, 0), gold(0, 1), gold(-1, 1),
             gold(0, -1), gold(1, -1), gold(-1, 0),
             gold(1, -1), gold(-1, 0), gold(0, 1) }, 3, false, true, true, false),
     axis("3x;z", new GoldenInteger[] {
             gold(0, 1), gold(1, -1), gold(1, 0),
             gold(-1, 1), gold(-1, 0), gold(0, -1),
             gold(1, 0), gold(0, 1), gold(1, -1) }, 3, false, true, false, false),
     axis("3x;z'", new GoldenInteger[] {
             gold(0, 1), gold(1, -1), gold(-1, 0),
             gold(-1, 1), gold(-1, 0), gold(0, 1),
             gold(-1, 0), gold(0, -1), gold(1, -1) }, 3, false, true, false, false),
     axis("3y;x", new GoldenInteger[] {
             gold(1, -1), gold(1, 0), gold(0, 1),
             gold(1, 0), gold(0, 1), gold(1, -1),
             gold(0, -1), gold(-1, 1), gold(-1, 0) }, 3, false, true, false, false),
     axis("3y;x'", new GoldenInteger[] {
             gold(1, -1), gold(-1, 0), gold(0, -1),
             gold(-1, 0), gold(0, 1), gold(1, -1),
             gold(0, 1), gold(-1, 1), gold(-1, 0) }, 3, false, true, true, false),
     axis("2x", new GoldenInteger[] {
             gold(2, 0), gold(0, 0), gold(0, 0),
             gold(0, 0), gold(-2, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(-2, 0) }, 2, false, true, false, false),
     axis("2y", new GoldenInteger[] {
             gold(-2, 0), gold(0, 0), gold(0, 0),
             gold(0, 0), gold(2, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(-2, 0) }, 2, false, true, false, false),
     axis("2z", new GoldenInteger[] {
             gold(-2, 0), gold(0, 0), gold(0, 0),
             gold(0, 0), gold(-2, 0), gold(0, 0),
             gold(0, 0), gold(0, 0), gold(2, 0) }, 2, false, true, false, false),
     axis("2z;y;x", new GoldenInteger[] {
             gold(0, -1), gold(-1, 1), gold(1, 0),
             gold(-1, 1), gold(-1, 0), gold(0, 1),
             gold(1, 0), gold(0, 1), gold(-1, 1) }, 2, false, true, false, false),
     axis("2z;y;x'", new GoldenInteger[] {
             gold(0, -1), gold(1, -1), gold(-1, 0),
             gold(1, -1), gold(-1, 0), gold(0, 1),
             gold(-1, 0), gold(0, 1), gold(-1, 1) }, 2, false, true, false, false),
     axis("2z;y';x", new GoldenInteger[] {
             gold(0, -1), gold(1, -1), gold(1, 0),
             gold(1, -1), gold(-1, 0), gold(0, -1),
             gold(1, 0), gold(0, -1), gold(-1, 1) }, 2, false, true, true, false),
     axis("2z;y';x'", new GoldenInteger[] {
             gold(0, -1), gold(-1, 1), gold(-1, 0),
             gold(-1, 1), gold(-1, 0), gold(0, -1),
             gold(-1, 0), gold(0, -1), gold(-1, 1) }, 2, false, true, true, false),
     axis("2x;z;y", new GoldenInteger[] {
             gold(-1, 1), gold(1, 0), gold(0, 1),
             gold(1, 0), gold(0, -1), gold(-1, 1),
             gold(0, 1), gold(-1, 1), gold(-1, 0) }, 2, false, true, false, false),
     axis("2x;z;y'", new GoldenInteger[] {
             gold(-1, 1), gold(-1, 0), gold(0, 1),
             gold(-1, 0), gold(0, -1), gold(1, -1),
             gold(0, 1), gold(1, -1), gold(-1, 0) }, 2, false, true, true, false),
     axis("2x;z';y", new GoldenInteger[] {
             gold(-1, 1), gold(1, 0), gold(0, -1),
             gold(1, 0), gold(0, -1), gold(1, -1),
             gold(0, -1), gold(1, -1), gold(-1, 0) }, 2, false, true, false, false),
     axis("2x;z';y'", new GoldenInteger[] {
             gold(-1, 1), gold(-1, 0), gold(0, -1),
             gold(-1, 0), gold(0, -1), gold(-1, 1),
             gold(0, -1), gold(-1, 1), gold(-1, 0) }, 2, false, true, true, false),
     axis("2y;x;z", new GoldenInteger[] {
             gold(-1, 0), gold(0, 1), gold(-1, 1),
             gold(0, 1), gold(-1, 1), gold(1, 0),
             gold(-1, 1), gold(1, 0), gold(0, -1) }, 2, false, true, false, false),
     axis("2y;x;z'", new GoldenInteger[] {
             gold(-1, 0), gold(0, 1), gold(1, -1),
             gold(0, 1), gold(-1, 1), gold(-1, 0),
             gold(1, -1), gold(-1, 0), gold(0, -1) }, 2, false, true, false, false),
     axis("2y;x';z", new GoldenInteger[] {
             gold(-1, 0), gold(0, -1), gold(1, -1),
             gold(0, -1), gold(-1, 1), gold(1, 0),
             gold(1, -1), gold(1, 0), gold(0, -1) }, 2, false, true, true, false),
     axis("2y;x';z'", new GoldenInteger[] {
             gold(-1, 0), gold(0, -1), gold(-1, 1),
             gold(0, -1), gold(-1, 1), gold(-1, 0),
             gold(-1, 1), gold(-1, 0), gold(0, -1) }, 2, false, true, true, false),
   };

  private static Axis<GoldenInteger> axis(
          final String name,
          final GoldenInteger[] rotationElts,
          final int degree,
          final boolean co,
          final boolean id,
          final boolean redundant_x0,
          final boolean redundant_xy
          )
  {
    final RingMatrices.Builder<GoldenInteger> rotationBuilder = RingMatrices.builder(goldenRing, 3);
    for (int i = 0; i < 3; i ++)
      for (int j = 0; j < 3; j ++)
        rotationBuilder.set(i, j, rotationElts[i*3+j]);
    final RingMatrix<? extends GoldenInteger> rotation = rotationBuilder.build();
    return new Axis<GoldenInteger>()
    {
      @Override public String name() { return name; }
      @Override public RingMatrix<? extends GoldenInteger> rotation() { return rotation; }
      @Override public int degree() { return degree; }
      
      @Override
      public boolean accept(final Symmetry symmetry)
      {
        switch (symmetry)
        {
        case Cubocta:
          return co;
        case Icosidodeca:
          return id;
        default:
          return false;
        }
      }
      
      @Override
      public boolean isRedundant(final boolean obliqueXY)
      {
        return obliqueXY ? redundant_xy : redundant_x0;
      }
    };
  }
  
  private static GoldenInteger gold(final long aa, final long bb)
  {
    return GoldenIntegers.of(BigInteger.valueOf(aa), BigInteger.valueOf(bb));
  }
  
  private AxisSets() { }
}
