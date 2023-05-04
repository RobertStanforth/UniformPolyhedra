package xyz.stanforth.polyhedra.nonplanar;

import java.io.*;
import java.util.*;

import xyz.stanforth.algebra.*;
import xyz.stanforth.polyhedra.common.*;
import xyz.stanforth.util.Function;

public final class NonPlanarChecking
{
  public static <T> RunnableWithTriple<? super Equation<T>> checker(final Session<T> session, final PrintStream out)
  {
    return new RunnableWithTriple<Equation<T>>()
      {
        @Override
        public void run(final Equation<T> eqn0, final Equation<T> eqn1, final Equation<T> eqn2)
        {
          String label = "eqns:";
          label += "  " + eqn0.name();
          label += "  " + eqn1.name();
          label += "  " + eqn2.name();
          //out.println(label);
          
          final Domain<T> ring = session.axes().ring();
          
          // Subtract equations to form two homogeneous equations of the form:
          //  ay²+2bxy+2czy+dx²+2exz+fz² (k) k=0,1
          final T a_0 = ring.subtract(eqn0.ayy(), eqn2.ayy());
          final T a_1 = ring.subtract(eqn1.ayy(), eqn2.ayy());
          final T b_0 = ring.subtract(eqn0.a2xy(), eqn2.a2xy());
          final T b_1 = ring.subtract(eqn1.a2xy(), eqn2.a2xy());
          final T c_0 = ring.subtract(eqn0.a2yz(), eqn2.a2yz());
          final T c_1 = ring.subtract(eqn1.a2yz(), eqn2.a2yz());
          final T d_0 = ring.subtract(eqn0.axx(), eqn2.axx());
          final T d_1 = ring.subtract(eqn1.axx(), eqn2.axx());
          final T e_0 = ring.subtract(eqn0.a2zx(), eqn2.a2zx());
          final T e_1 = ring.subtract(eqn1.a2zx(), eqn2.a2zx());
          final T f_0 = ring.subtract(eqn0.azz(), eqn2.azz());
          final T f_1 = ring.subtract(eqn1.azz(), eqn2.azz());
          
          // Take a linear combination to formulate:
          //  (px+qz)y + rx²+2sxz+tz² (2)
          final T a0 = a_0;
          final T a1 = ring.equals(a_0, ring.zero()) ? ring.identity() : a_1;
          final T p = ring.multiply(ring.valueOf(2L), ring.subtract(ring.multiply(a1, b_0), ring.multiply(a0, b_1)));
          final T q = ring.multiply(ring.valueOf(2L), ring.subtract(ring.multiply(a1, c_0), ring.multiply(a0, c_1)));
          final T r = ring.subtract(ring.multiply(a1, d_0), ring.multiply(a0, d_1));
          final T s = ring.subtract(ring.multiply(a1, e_0), ring.multiply(a0, e_1));
          final T t = ring.subtract(ring.multiply(a1, f_0), ring.multiply(a0, f_1));
          if (ring.equals(a_0, ring.zero()))
            run(eqn0, eqn1, eqn2, a_1, b_1, c_1, d_1, e_1, f_1, p, q, r, s, t);
          else
            run(eqn0, eqn1, eqn2, a_0, b_0, c_0, d_0, e_0, f_0, p, q, r, s, t);
        }
        
        @SuppressWarnings("unchecked")
        private void run(final Equation<T> eqn0, final Equation<T> eqn1, final Equation<T> eqn2,
                final T a, final T b, final T c, final T d, final T e, final T f,
                final T p, final T q, final T r, final T s, final T t)
        {
          final Domain<T> ring = session.axes().ring();
          
          if (!(ring.equals(p, ring.zero()) && ring.equals(q, ring.zero())))
            {
              // Case A.
              final List<T> poly_xz = new ArrayList<T>(5);
              if (!ring.equals(a, ring.zero()))
                {
                  // Construct the quartic in x/z derived by substituting (2) into (px+qz)²(k):
                  poly_xz.add(ring.add(ring.multiply(a,
                                  ring.multiply(t, t)),
                          ring.add(ring.multiply(ring.valueOf(-2L),
                                  ring.multiply(c, ring.multiply(q, t))),
                          ring.multiply(f, ring.multiply(q, q)))));
                  poly_xz.add(ring.add(ring.multiply(a,
                                  ring.multiply(ring.valueOf(4L), ring.multiply(s, t))),
                          ring.add(ring.multiply(ring.valueOf(-2L),
                                  ring.add(ring.multiply(b, ring.multiply(q, t)), ring.multiply(c,
                                              ring.add(ring.multiply(p, t), ring.multiply(ring.valueOf(2L), ring.multiply(q, s)))))),
                          ring.add(ring.multiply(ring.valueOf(2L), ring.multiply(e, ring.multiply(q, q))),
                                              ring.multiply(ring.valueOf(2L), ring.multiply(f, ring.multiply(p, q)))))));
                  poly_xz.add(ring.add(ring.multiply(a,
                                  ring.add(ring.multiply(ring.valueOf(2L), ring.multiply(r, t)), ring.multiply(ring.valueOf(4L), ring.multiply(s, s)))),
                          ring.add(ring.multiply(ring.valueOf(-2L),
                                  ring.add(ring.multiply(b,
                                              ring.add(ring.multiply(p, t), ring.multiply(ring.valueOf(2L), ring.multiply(q, s)))),
                                          ring.multiply(c,
                                              ring.add(ring.multiply(q, r), ring.multiply(ring.valueOf(2L), ring.multiply(p, s)))))),
                          ring.add(ring.multiply(d, ring.multiply(q, q)),
                                              ring.add(ring.multiply(ring.valueOf(4L), ring.multiply(e, ring.multiply(p, q))),
                                              ring.multiply(f, ring.multiply(p, p)))))));
                  poly_xz.add(ring.add(ring.multiply(a,
                                  ring.multiply(ring.valueOf(4L), ring.multiply(r, s))),
                          ring.add(ring.multiply(ring.valueOf(-2L),
                                  ring.add(ring.multiply(c, ring.multiply(p, r)), ring.multiply(b,
                                              ring.add(ring.multiply(q, r), ring.multiply(ring.valueOf(2L), ring.multiply(p, s)))))),
                          ring.add(ring.multiply(ring.valueOf(2L), ring.multiply(e, ring.multiply(p, p))),
                                              ring.multiply(ring.valueOf(2L), ring.multiply(d, ring.multiply(p, q)))))));
                  poly_xz.add(ring.add(ring.multiply(a,
                                  ring.multiply(r, r)),
                          ring.add(ring.multiply(ring.valueOf(-2L),
                                  ring.multiply(b, ring.multiply(p, r))),
                          ring.multiply(d, ring.multiply(p, p)))));
                }
              else
                {
                  // Construct the cubic in x/z derived by substituting (2) into (px+qz)(k):
                  poly_xz.add(ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(c, t)),
                          ring.multiply(f, q)));
                  poly_xz.add(ring.add(ring.multiply(ring.valueOf(-2L), ring.add(ring.multiply(b, t), ring.multiply(ring.valueOf(2L), ring.multiply(c, s)))),
                          ring.add(ring.multiply(f, p), ring.multiply(ring.valueOf(2L), ring.multiply(e, q)))));
                  poly_xz.add(ring.add(ring.multiply(ring.valueOf(-2L), ring.add(ring.multiply(c, r), ring.multiply(ring.valueOf(2L), ring.multiply(b, s)))),
                          ring.add(ring.multiply(d, q), ring.multiply(ring.valueOf(2L), ring.multiply(e, p)))));
                  poly_xz.add(ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(b, r)),
                          ring.multiply(d, p)));
                }
              
              forEachFactor(poly_xz, new RunnableWithSplittingField<T>()
              {
                @Override
                public void run(final Polynomial<? extends T> factor, final SplittingField<T> splittingField)
                {
                  final Function<? super T, ? extends Split<? extends T>> converter = splittingField.converter();
                  // Assemble the vertex. Recall that y=-(rx²+2sxz+tz²)/(px+qz).
                  final Split<? extends T> x = splittingField.root();
                  final Split<? extends T> ydenom = splittingField.add(splittingField.multiply(converter.apply(p), x), converter.apply(q));
                  final Split<? extends T> ynum = splittingField.add(
                          splittingField.multiply(converter.apply(ring.multiply(ring.valueOf(-1L), r)), splittingField.multiply(x, x)),
                          splittingField.add(
                                  splittingField.multiply(converter.apply(ring.multiply(ring.valueOf(-2L), s)), x),
                                  converter.apply(ring.multiply(ring.valueOf(-1L), t))));
                  // px+qz=0 handled separately below.
                  if (!splittingField.equals(ydenom, splittingField.zero()))
                    {
                      final List<? extends Split<? extends T>> vertex = Arrays.asList(splittingField.multiply(x, ydenom), ynum, ydenom);
                      // Check it for solutions.
                      checkVertex(eqn0, eqn1, eqn2,
                              splittingField, splittingField.converter(), factor.numRealRoots(),
                              vertex);
                    }
                }
              });
              
              // Separately consider solutions for which px+qz = 0.
              // First determine whether (2) would be satisfied.
              if (ring.equals(ring.add(
                      ring.multiply(r, ring.multiply(q, q)),
                      ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(s, ring.multiply(p, q))),
                              ring.multiply(t, ring.multiply(p, p)))), ring.zero()))
                {
                  // Use x=-qz/p in (1) to solve for y/z.
                  final List<T> poly_yz = new ArrayList<T>(3);
                  poly_yz.add(ring.add(ring.multiply(d, ring.multiply(q, q)),
                          ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(e, ring.multiply(p, q))),
                                  ring.multiply(f, ring.multiply(p, p)))));
                  poly_yz.add(ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(b, ring.multiply(p, q))),
                                  ring.multiply(ring.valueOf(2L), ring.multiply(c, ring.multiply(p, p)))));
                  poly_yz.add(ring.multiply(a, ring.multiply(p, p)));
                  
                  forEachFactor(poly_yz, new RunnableWithSplittingField<T>()
                  {
                    @Override
                    public void run(final Polynomial<? extends T> factor, final SplittingField<T> splittingField)
                    {
                      final Function<? super T, ? extends Split<? extends T>> converter = splittingField.converter();
                      // Assemble the vertex. Recall that x=-qz/p.
                      final Split<? extends T> y = splittingField.root();
                      final Split<? extends T> xdenom = converter.apply(p);
                      final Split<? extends T> xnum = converter.apply(ring.negate(q));
                      final List<? extends Split<? extends T>> vertex = Arrays.asList(xnum, splittingField.multiply(y, xdenom), xdenom);
                      // Check it for solutions.
                      checkVertex(eqn0, eqn1, eqn2,
                              splittingField, splittingField.converter(), factor.numRealRoots(),
                              vertex);
                    }
                  });
                }
            }
          else
            {
              // Solve for x/z using (2).
              final List<T> poly_xz = new ArrayList<T>(3);
              poly_xz.add(t);
              poly_xz.add(ring.multiply(ring.valueOf(2L), s));
              poly_xz.add(r);
              
              forEachFactor(poly_xz, new RunnableWithSplittingField<T>()
              {
                @Override
                public void run(final Polynomial<? extends T> factor_xz, final SplittingField<T> splittingField_xz)
                {
                  if (factor_xz.degree() == 2)
                    {
                      // Case B.
                      // p=q=0  r!=0
                      // Construct the quadratic in y/z derived by substituting (-s+sqrt{delta})z for rx in r²(1),
                      // where delta=s²-rt, the discriminant of (2), not a perfect square in T.
                      // Substitution leads to Ay² + (B+C sqrt{delta})yz + (E+F sqrt{delta})z² = 0 where:
                      // A = ar²
                      // B = -2brs + 2cr²
                      // C = 2br
                      // E = d(2s²-rt) - 2ers + fr²
                      // F = -2ds + 2er
                      final T delta = ring.subtract(ring.multiply(s, s), ring.multiply(r, t));
                      final T A = ring.multiply(a, ring.multiply(r, r));
                      final T B = ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(b, ring.multiply(r, s))),
                              ring.multiply(ring.valueOf(2L), ring.multiply(c, ring.multiply(r, r))));
                      final T C = ring.multiply(ring.valueOf(2L), ring.multiply(b, r));
                      final T E = ring.add(ring.multiply(d,
                                  ring.subtract(ring.multiply(ring.valueOf(2L), ring.multiply(s, s)), ring.multiply(r, t))),
                              ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(e, ring.multiply(r, s))),
                              ring.multiply(f, ring.multiply(r, r))));
                      final T F = ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(d, s)),
                              ring.multiply(ring.valueOf(2L), ring.multiply(e, r)));
                      // Construct the quartic in y/z derived by isolating sqrt{delta} terms, and squaring.
                      // A²y²² + 2ABy³z + (B²+2AE-dC²)y²z² + (2BE-2dCF)yz³ + (E²-dF²)z²² = 0
                      final List<T> poly_yz = new ArrayList<T>(5);
                      poly_yz.add(ring.add(ring.multiply(E, E), ring.multiply(ring.valueOf(-1L), ring.multiply(delta, ring.multiply(F, F)))));
                      poly_yz.add(ring.add(ring.multiply(ring.valueOf(2L), ring.multiply(B, E)),
                              ring.multiply(ring.valueOf(-2L), ring.multiply(delta, ring.multiply(C, F)))));
                      poly_yz.add(ring.add(ring.multiply(B, B),
                              ring.add(ring.multiply(ring.valueOf(2L), ring.multiply(A, E)),
                              ring.multiply(ring.valueOf(-1L), ring.multiply(delta, ring.multiply(C, C))))));
                      poly_yz.add(ring.multiply(ring.valueOf(2L), ring.multiply(A, B)));
                      poly_yz.add(ring.multiply(A, A));
                      
                      forEachFactor(poly_yz, new RunnableWithSplittingField<T>()
                      {
                        @Override
                        public void run(final Polynomial<? extends T> factor_yz, final SplittingField<T> splittingField_yz)
                        {
                          final Function<? super T, ? extends Split<? extends T>> converter_yz = splittingField_yz.converter();
                          // Assemble the vertex. Take d(2) - r(1) to form a linear equation for x/z.
                          // Note that the denominator is -(C(y/z)+F), where (y/z) is the root of the quartic. This may be zero, requiring separate handling.
                          final Split<? extends T> y = splittingField_yz.root();
                          final Split<? extends T> xdenom = splittingField_yz.add(
                                  splittingField_yz.multiply(converter_yz.apply(ring.multiply(ring.valueOf(-2L), ring.multiply(b, r))), y),
                                  converter_yz.apply(ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(e, r)), ring.multiply(ring.valueOf(2L), ring.multiply(d, s)))));
                          final Split<? extends T> xnum = splittingField_yz.add(
                                  splittingField_yz.multiply(converter_yz.apply(ring.multiply(ring.valueOf(1L), ring.multiply(a, r))), splittingField_yz.multiply(y, y)),
                                  splittingField_yz.add(
                                          splittingField_yz.multiply(converter_yz.apply(ring.multiply(ring.valueOf(2L), ring.multiply(c, r))), y),
                                          converter_yz.apply(ring.add(ring.multiply(ring.valueOf(1L), ring.multiply(f, r)), ring.multiply(ring.valueOf(-1L), ring.multiply(d, t))))));
                          if (!splittingField_yz.equals(xdenom, splittingField_yz.zero()))
                            {
                              final List<? extends Split<? extends T>> vertex = Arrays.asList(xnum, splittingField_yz.multiply(y, xdenom), xdenom);
                              // Check it for solutions.
                              checkVertex(eqn0, eqn1, eqn2,
                                      splittingField_yz, converter_yz, factor_yz.numRealRoots(),
                                      vertex);
                            }
                          else if (factor_yz.degree() == 1)
                            {
                              // Work in the splitting field for x/z.
                              final Function<? super T, ? extends Split<? extends T>> converter_xz = splittingField_xz.converter();
                              // Assemble the vertex. Obtain y from the linear factor in y/z.
                              final Split<? extends T> x = splittingField_xz.root();
                              final Split<? extends T> ydenom = converter_xz.apply(factor_yz.coeffs().get(1));
                              final Split<? extends T> ynum = converter_xz.apply(ring.negate(factor_yz.coeffs().get(0)));
                              final List<? extends Split<? extends T>> vertex = Arrays.asList(splittingField_xz.multiply(x, ydenom), ynum, ydenom);
                              // Check it for solutions.
                              checkVertex(eqn0, eqn1, eqn2,
                                      splittingField_xz, converter_xz, factor_xz.numRealRoots(),
                                      vertex);
                            }
                          else
                            {
                              // Must have C,F=0 (because otherwise C(y/z)+F=0 is a linear minimal polynomial for y/z).
                              if (!ring.equals(C, ring.zero()) || !ring.equals(F, ring.zero()))
                                throw new RuntimeException();
                              final T disc_xz = ring.subtract(ring.multiply(factor_xz.coeffs().get(1), factor_xz.coeffs().get(1)), ring.multiply(ring.valueOf(4L), ring.multiply(factor_xz.coeffs().get(2), factor_xz.coeffs().get(0))));
                              final T disc_yz = ring.subtract(ring.multiply(factor_yz.coeffs().get(1), factor_yz.coeffs().get(1)), ring.multiply(ring.valueOf(4L), ring.multiply(factor_yz.coeffs().get(2), factor_yz.coeffs().get(0))));
                              final List<T> poly_cross = new ArrayList<T>(3);
                              poly_cross.add(disc_yz);
                              poly_cross.add(ring.zero());
                              poly_cross.add(ring.negate(disc_xz));
                              final List<? extends Polynomial<? extends T>> factors_cross = Polynomials.factors(ring, poly_cross);
                              if (factors_cross.size() == 2)
                                {
                                  // Can work within splitting field for x/z.
                                  final Polynomial<? extends T> factor_cross = factors_cross.get(0);
                                  final Function<? super T, ? extends Split<? extends T>> converter_xz = splittingField_xz.converter();
                                  final T sqrt_disc_xz = factor_cross.coeffs().get(1);
                                  final T sqrt_disc_yz = factor_cross.coeffs().get(0);
                                  // 2 factor_x_2 x = -factor_x_1 + sqrt{disc_x}
                                  // 2 factor_y_2 y = -factor_y_1 + sqrt{disc_y}
                                  // 2 factor_y_2 y + factor_y_1 = sqrt{disc_y/disc_x} (2 factor_x_2 x + factor_x_1) 
                                  final Split<? extends T> x = splittingField_xz.root();
                                  final Split<? extends T> ydenom = converter_xz.apply(ring.multiply(ring.valueOf(2L), ring.multiply(factor_yz.coeffs().get(2), sqrt_disc_xz)));
                                  final Split<? extends T> ynum = splittingField_xz.add(
                                          splittingField_xz.multiply(converter_xz.apply(ring.multiply(ring.valueOf(2L), ring.multiply(factor_xz.coeffs().get(2), sqrt_disc_yz))), x),
                                          converter_xz.apply(ring.subtract(ring.multiply(sqrt_disc_yz, factor_xz.coeffs().get(1)), ring.multiply(sqrt_disc_xz, factor_yz.coeffs().get(1)))));
                                  final List<? extends Split<? extends T>> vertex = Arrays.asList(splittingField_xz.multiply(x, ydenom), ynum, ydenom);
                                  // Check it for solutions.
                                  checkVertex(eqn0, eqn1, eqn2,
                                          splittingField_xz, converter_xz, factor_xz.numRealRoots(),
                                          vertex);
                                }
                              else
                                {
                                  // Need to work in a larger splitting field.
                                  // Let w = sqrt{disc_xz} + sqrt{disc_yz}
                                  // w² = disc_xz + disc_yz + 2 sqrt{disc_xz} sqrt{disc_yz}
                                  // w³ = (disc_xz + 3 disc_yz} sqrt{disc_xz} + (3 disc_xz + disc_yz) sqrt{disc_yz}
                                  // w²² = disc_xz² + 6 disc_xz disc_yz + disc_yz² + 4(disc_xz + disc_yz) sqrt{disc_xz} sqrt{disc_yz}
                                  //     = 2(disc_xz + disc_yz) w² - (disc_xz - disc_yz)²
                                  // sqrt{disc_xz) = (w³ - (3 disc_xz + disc_yz) w) / 2(disc_yz - disc_xz);
                                  // sqrt{disc_yz) = (w³ - (disc_xz + 3 disc_yz) w) / 2(disc_xz - disc_yz);
                                  final List<T> poly_w = new ArrayList<T>(5);
                                  poly_w.add(ring.multiply(ring.subtract(disc_xz, disc_yz), ring.subtract(disc_xz, disc_yz)));
                                  poly_w.add(ring.zero());
                                  poly_w.add(ring.multiply(ring.valueOf(-2L), ring.add(disc_xz, disc_yz)));
                                  poly_w.add(ring.zero());
                                  poly_w.add(ring.identity());
                                  forEachFactor(poly_w, new RunnableWithSplittingField<T>()
                                  {
                                    @Override
                                    public void run(final Polynomial<? extends T> factor_w, SplittingField<T> splittingField_w)
                                    {
                                      if (factor_w.degree() != 4)
                                        throw new RuntimeException();
                                      final Function<? super T, ? extends Split<? extends T>> converter_w = splittingField_w.converter();
                                      final Split<? extends T> w = splittingField_w.root();
                                      final Split<? extends T> w3 = splittingField_w.multiply(w, splittingField_w.multiply(w, w));
                                      final Split<? extends T> sqrt_disc_denom = converter_w.apply(ring.multiply(ring.valueOf(2L), ring.subtract(disc_yz, disc_xz)));
                                      final Split<? extends T> sqrt_disc_xz_num = splittingField_w.add(w3, splittingField_w.multiply(converter_w.apply(
                                              ring.add(ring.multiply(ring.valueOf(-3L), disc_xz), ring.multiply(ring.valueOf(-1L), disc_yz))), w));
                                      final Split<? extends T> sqrt_disc_yz_num = splittingField_w.negate(splittingField_w.add(w3, splittingField_w.multiply(converter_w.apply(
                                              ring.add(ring.multiply(ring.valueOf(-1L), disc_xz), ring.multiply(ring.valueOf(-3L), disc_yz))), w)));
                                      // 2 factor_x_2 x = -factor_x_1 + sqrt{disc_x}
                                      // 2 factor_y_2 y = -factor_y_1 + sqrt{disc_y}
                                      final Split<? extends T> xdenom = converter_w.apply(ring.multiply(ring.valueOf(2L), factor_xz.coeffs().get(2)));
                                      final Split<? extends T> xnum = splittingField_w.subtract(sqrt_disc_xz_num, splittingField_w.multiply(sqrt_disc_denom, converter_w.apply(factor_xz.coeffs().get(1))));
                                      final Split<? extends T> ydenom = converter_w.apply(ring.multiply(ring.valueOf(2L), factor_yz.coeffs().get(2)));
                                      final Split<? extends T> ynum = splittingField_w.subtract(sqrt_disc_yz_num, splittingField_w.multiply(sqrt_disc_denom, converter_w.apply(factor_yz.coeffs().get(1))));
                                      final List<? extends Split<? extends T>> vertex = Arrays.asList(splittingField_w.multiply(xnum, ydenom), splittingField_w.multiply(ynum, xdenom), splittingField_w.multiply(sqrt_disc_denom, splittingField_w.multiply(xdenom, ydenom)));
                                      // Check it for solutions.
                                      checkVertex(eqn0, eqn1, eqn2,
                                              splittingField_w, converter_w, factor_w.numRealRoots(),
                                              vertex);
                                    }
                                  });
                                }
                            }
                        }
                      });
                    }
                  else if (factor_xz.degree() == 1)
                    {
                      // Case C.
                      final T g = factor_xz.coeffs().get(1);
                      final T h = factor_xz.coeffs().get(0);
                      // gx+hz = 0
                      // Construct the quadratic in y/z derived by substituting -hz for gx in g²(1).
                      // ag²x² + (-2bgh+2cg²)xz + (dh²-2egh+fg²)z² = 0
                      final List<T> poly_yz = new ArrayList<T>(3);
                      poly_yz.add(ring.add(ring.multiply(d, ring.multiply(h, h)),
                              ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(e, ring.multiply(g, h))),
                                      ring.multiply(f, ring.multiply(g, g)))));
                      poly_yz.add(ring.add(ring.multiply(ring.valueOf(-2L), ring.multiply(b, ring.multiply(g, h))),
                              ring.multiply(ring.valueOf(2L), ring.multiply(c, ring.multiply(g, g)))));
                      poly_yz.add(ring.multiply(a, ring.multiply(g, g)));
                      
                      forEachFactor(poly_yz, new RunnableWithSplittingField<T>()
                      {
                        @Override
                        public void run(final Polynomial<? extends T> factor_yz, final SplittingField<T> splittingField_yz)
                        {
                          final Function<? super T, ? extends Split<? extends T>> converter = splittingField_yz.converter();
                          // Assemble the vertex. Recall that x = -hz/g.
                          final Split<? extends T> y = splittingField_yz.root();
                          final Split<? extends T> xdenom = converter.apply(g);
                          final Split<? extends T> xnum = converter.apply(ring.negate(h));
                          final List<? extends Split<? extends T>> vertex = Arrays.asList(xnum, splittingField_yz.multiply(y, xdenom), xdenom);
                          // Check it for solutions.
                          checkVertex(eqn0, eqn1, eqn2,
                                  splittingField_yz, splittingField_yz.converter(), factor_yz.numRealRoots(),
                                  vertex);
                        }
                      });
                    }
                  else
                    throw new RuntimeException();
                }
              });
            }
        }
        
        private void forEachFactor(final List<? extends T> poly, final RunnableWithSplittingField<T> body)
        {
          final Domain<T> ring = session.axes().ring();
          // This disregards roots with z=0, but these will be degenerate (planar) anyway.
          List<? extends T> subPoly = poly;
          while (!subPoly.isEmpty() && ring.equals(subPoly.get(subPoly.size()-1), ring.zero()))
            subPoly = subPoly.subList(0, subPoly.size()-1);
          if (!subPoly.isEmpty())
            {
              // TODO only need distinct factors.
              for (final Polynomial<? extends T> factor : Polynomials.factors(ring, subPoly))
                {
                  // Construct a splitting field from this factor.
                  final SplittingField<T> splittingField = SplittingFields.field(ring, factor);
                  body.run(factor, splittingField);
                }
            }
        }
        
        private <U> void checkVertex(final Equation<T> eqn0, final Equation<T> eqn1, final Equation<T> eqn2,
                                     final OrderedRing<U> splittingField, final Function<? super T, ? extends U> converter,
                                     final int numRealRoots, final List<? extends U> vertex)
        {
          if (splittingField.equals(vertex.get(0), splittingField.zero())
                  && splittingField.equals(vertex.get(1), splittingField.zero())
                  && splittingField.equals(vertex.get(2), splittingField.zero()))
            throw new RuntimeException("zero solution");
          
          final U edgeLength = edgeLength(eqn0, splittingField, converter, vertex);
          if (!splittingField.equals(edgeLength, edgeLength(eqn1, splittingField, converter, vertex))
                  || !splittingField.equals(edgeLength, edgeLength(eqn2, splittingField, converter, vertex)))
            throw new RuntimeException("not a solution");
         
          if (numRealRoots > 0)
            {
              final List<? extends FaceCandidate> candidates = PolygonSearching.candidates(session, true, splittingField, converter, vertex, splittingField.multiply(splittingField.valueOf(4L), edgeLength));
              if (candidates != null)
                Reporting.print(out, numRealRoots, candidates);
            }
        }
        
        private <U> U edgeLength(final Equation<T> eqn,
                        final OrderedRing<U> splittingField, final Function<? super T, ? extends U> converter,
                        final List<? extends U> vertex)
        {
          final U vx = vertex.get(0);
          final U vy = vertex.get(1);
          final U vz = vertex.get(2);
          U el = splittingField.multiply(converter.apply(eqn.ayy()), splittingField.multiply(vy, vy));
          el = splittingField.add(el, splittingField.multiply(splittingField.multiply(splittingField.valueOf(2L), converter.apply(eqn.a2xy())), splittingField.multiply(vx, vy)));
          el = splittingField.add(el, splittingField.multiply(splittingField.multiply(splittingField.valueOf(2L), converter.apply(eqn.a2yz())), splittingField.multiply(vz, vy)));
          el = splittingField.add(el, splittingField.multiply(converter.apply(eqn.axx()), splittingField.multiply(vx, vx)));
          el = splittingField.add(el, splittingField.multiply(splittingField.multiply(splittingField.valueOf(2L), converter.apply(eqn.a2zx())), splittingField.multiply(vx, vz)));
          el = splittingField.add(el, splittingField.multiply(converter.apply(eqn.azz()), splittingField.multiply(vz, vz)));
          return el;
        }
      };
  }

  private interface RunnableWithSplittingField<T>
  {
    void run(Polynomial<? extends T> poly, SplittingField<T> splittingField);
  }
  
  private NonPlanarChecking() { }
}
