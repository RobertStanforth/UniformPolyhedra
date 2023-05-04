package xyz.stanforth.polyhedra.planar;

import xyz.stanforth.algebra.OrderedRing;
import xyz.stanforth.algebra.Ring;
import xyz.stanforth.polyhedra.axes.Axis;
import xyz.stanforth.polyhedra.common.Equation;
import xyz.stanforth.polyhedra.common.Session;
import xyz.stanforth.util.Function;

import java.util.*;

public final class PlanarSessions
{
  public static <T> PlanarSession<T> planarSession(final Session<T> session, final boolean obliqueXY)
  {
    final Ring<T> ring = session.axes().ring();
    
    // Loop through the available axes, determining which are to be included.
    final List<PlanarEquation<T>> equations = new ArrayList<>();
    for (final Axis<T> axis : session.axes().axes())
      if (!axis.isRedundant(obliqueXY))
        for (int power = 1; 2 * power <= axis.degree(); power ++)
          {
            final Equation<T> equation = session.equation(equations.size(), axis, power, false);
            equations.add(new PlanarEquation<T>()
            {
              @Override public int index() { return equation.index(); }
              @Override public String name() { return equation.name(); }
              @Override public T axx() { return obliqueXY ? ring.add(ring.add(equation.axx(), equation.a2xy()), ring.add(equation.a2xy(), equation.ayy())) : equation.axx(); }
              @Override public T a2xz() { return obliqueXY ? ring.add(equation.a2zx(), equation.a2yz()) : equation.a2zx(); }
              @Override public T azz() { return equation.azz(); }
            });
          }
    
    return new PlanarSession<T>()
    {
      @Override public boolean obliqueXY() { return obliqueXY; }
      @Override public List<? extends PlanarEquation<T>> equations() { return equations; }
      @Override public <U> boolean isXZViable(final OrderedRing<U> ratioRing, final Function<? super T, ? extends U> converter, final U rxz) { return session.axes().isXZViable(obliqueXY, ratioRing, converter, rxz); }
    };
  }
  
  private PlanarSessions() { }
}
