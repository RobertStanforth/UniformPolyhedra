package xyz.stanforth.polyhedra.nonplanar;

import xyz.stanforth.algebra.Matrices;
import xyz.stanforth.algebra.OrderedRing;
import xyz.stanforth.algebra.RingMatrices;
import xyz.stanforth.algebra.RingMatrix;
import xyz.stanforth.polyhedra.axes.Axis;
import xyz.stanforth.polyhedra.common.Equation;
import xyz.stanforth.polyhedra.common.Session;

import java.util.*;

public final class NonPlanarEquations
{
  public static <T> void forEachEquationTriple(final Session<T> session, final RunnableWithTriple<? super Equation<T>> runnableWithTriple)
  {
    final List<? extends Equation<T>> equations = equations(session);
    final int numOrbits = numOrbits(session);
    final Map<Equation<T>, ? extends List<? extends Equation<T>>> orbits = orbits(session, equations);

    final int n = equations.size();
    final boolean[] done = new boolean[numKeys(n)];
    for (final Equation<T> eqn2 : equations)
      for (final Equation<T> eqn1 : equations)
        for (final Equation<T> eqn0 : equations)
          if (eqn0 != eqn1 && eqn1 != eqn2 && eqn2 != eqn0)
            {
              final int key = key(n, eqn0.index(), eqn1.index(), eqn2.index());
              if (!done[key])
                {
                  for (int i = 0; i < numOrbits; i ++)
                    runPermutuations(orbits.get(eqn0).get(i), orbits.get(eqn1).get(i), orbits.get(eqn2).get(i),
                      (final Equation<T> eqna, final Equation<T> eqnb, final Equation<T> eqnc) ->
                        {
                          final int orbitKey = key(n, eqna.index(), eqnb.index(), eqnc.index());
                          done[orbitKey] = true;
                        });
                  
                  runnableWithTriple.run(eqn0, eqn1, eqn2);
                }
            }
  }
  
  private static <U> void runPermutuations(final U u0, final U u1, final U u2, final RunnableWithTriple<? super U> runnable)
  {
    runnable.run(u0, u1, u2);
    runnable.run(u0, u2, u1);
    runnable.run(u2, u0, u1);
    runnable.run(u2, u1, u0);
    runnable.run(u1, u2, u0);
    runnable.run(u1, u0, u2);
  }
  
  private static int numKeys(final int n)
  {
    return n * n * n;
  }
  
  private static int key(final int n, final int a, final int b, final int c)
  {
    return (a * n + b) * n + c;
  }
  
  private static <T> List<? extends Equation<T>> equations(final Session<T> session)
  {
    final List<Equation<T>> equations = new ArrayList<>();
    for (final Axis<T> axis : session.axes().axes())
      for (int power = 1; 2 * power <= axis.degree(); power ++)
        {
          equations.add(session.equation(equations.size(), axis, power, false));
          equations.add(session.equation(equations.size(), axis, power, true));
        }
    return equations;
  }

  private static <T> int numOrbits(final Session<T> session)
  {
    final int[] count = { 0 };
    session.forIdentityAndAxisRotations(new Session.RunnableWithTransform<T>()
    {
      @Override
      public void run(final RingMatrix<? extends T> transform)
      {
        ++ count[0];
      }
    });
    return count[0];
  }

  private static <T> Map<Equation<T>, ? extends List<? extends Equation<T>>> orbits(final Session<T> session, final List<? extends Equation<T>> equations)
  {
    final OrderedRing<T> ring = session.axes().ring();
    final Map<Equation<T>, List<Equation<T>>> orbits = new HashMap<>();
    for (final Equation<T> equation : equations)
      orbits.put(equation, new ArrayList<>());
    session.forIdentityAndAxisRotations(new Session.RunnableWithTransform<T>()
    {
      @Override
      public void run(final RingMatrix<? extends T> transform)
      {
        for (final Equation<T> equation : equations)
          {
            final RingMatrix<? extends T> conjugate = RingMatrices.product(ring, 3, Matrices.transpose(transform),
                    RingMatrices.product(ring, 3, equation.coeffs(), transform));
            Equation<T> target = null;
            for (final Equation<T> otherEquation : equations)
              if (Matrices.equals(ring, 3, conjugate, RingMatrices.sum(ring, 3, RingMatrices.zero(ring, 3), ring.valueOf(4L), otherEquation.coeffs())))
                {
                  if (target != null)
                    throw new RuntimeException();
                  target = otherEquation;
                }
            if (target == null)
              throw new RuntimeException();
            orbits.get(equation).add(target);
          }
      }
    });
    return orbits;
  }

  private NonPlanarEquations() { }
}
