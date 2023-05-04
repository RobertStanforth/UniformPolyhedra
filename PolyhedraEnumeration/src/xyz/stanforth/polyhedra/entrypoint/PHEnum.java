package xyz.stanforth.polyhedra.entrypoint;

import xyz.stanforth.algebra.GoldenInteger;
import xyz.stanforth.polyhedra.axes.AxisSets;
import xyz.stanforth.polyhedra.axes.CubicAxisSets;
import xyz.stanforth.polyhedra.axes.Symmetry;
import xyz.stanforth.polyhedra.common.Session;
import xyz.stanforth.polyhedra.common.Sessions;
import xyz.stanforth.polyhedra.nonplanar.NonPlanarChecking;
import xyz.stanforth.polyhedra.nonplanar.NonPlanarEquations;
import xyz.stanforth.polyhedra.planar.PlanarChecking;
import xyz.stanforth.polyhedra.planar.PlanarSession;
import xyz.stanforth.polyhedra.planar.PlanarSessions;

import java.math.*;

public final class PHEnum
{
  @SuppressWarnings("all")
  public static void main(final String[] args)
  {
    {
      final Session<BigInteger> session = Sessions.session(CubicAxisSets.axes());

      {
        System.out.println("Candidate symmetric cuboctahedral vertex figures on x=0 plane (e.g. truncated octahedron)");
        final PlanarSession<BigInteger> planarSession = PlanarSessions.planarSession(session, false);
        PlanarChecking.checkAllPlanar(session, planarSession, System.out);
      }

      {
        System.out.println("Candidate symmetric cuboctahedral vertex figures on x=y plane (e.g. truncated hexahedron");
        final PlanarSession<BigInteger> planarSession = PlanarSessions.planarSession(session, true);
        PlanarChecking.checkAllPlanar(session, planarSession, System.out);
      }

      System.out.println("Candidate asymmetric cuboctahedral vertex figures (e.g. truncated cuboctahedron)");
      NonPlanarEquations.forEachEquationTriple(session, NonPlanarChecking.checker(session, System.out));
    }

    {
      final Session<GoldenInteger> session = Sessions.session(AxisSets.axes(Symmetry.Icosidodeca));

      {
        System.out.println("Candidate symmetric icosidodecahedral vertex figures (e.g. truncated dodecahedron)");
        final PlanarSession<GoldenInteger> planarSession = PlanarSessions.planarSession(session, false);
        PlanarChecking.checkAllPlanar(session, planarSession, System.out);
      }

      System.out.println("Candidate asymmetric icosidodecahedral vertex figures (e.g. truncated icosidodecahedron)");
      NonPlanarEquations.forEachEquationTriple(session, NonPlanarChecking.checker(session, System.out));
    }
  }

  private PHEnum() { }
}
