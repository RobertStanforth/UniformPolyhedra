package xyz.stanforth.polyhedra.plotting;

/**
 * Simultaneous symmetry transformation of position and colour.
 */
public interface Transform
{
  /**
   * Applies the symmetry transform to a colour.
   * @param c Colour index.
   * @return Transformed colour index.
   */
  int apply(int c);

  /**
   * Applies the symmetry transform to a vector.
   * @param v Vector.
   * @return Transformed vector.
   */
  Vector4 apply(Vector4 v);

  /**
   * @return Identity transform.
   */
  static Transform identity()
  {
    return new Transform()
    {
      @Override public int apply(final int c) { return c; }
      @Override public Vector4 apply(final Vector4 v) { return v; }
    };
  }

  /**
   * @param t First transform.
   * @param u Second transform.
   * @return Composition of the two transforms.
   */
  static Transform compose(final Transform t, final Transform u)
  {
    return new Transform()
    {
      @Override public int apply(final int c) { return t.apply(u.apply(c)); }
      @Override public Vector4 apply(final Vector4 v) { return t.apply(u.apply(v)); }
    };
  }

  /**
   * @param matrix Elements [a_00, a_01, ...] of the transformation matrix A to apply on the left.
   * @param colours Permutation of colour indices.
   * @return Transformation specified by the arguments.
   */
  static Transform of(final double[][] matrix, int[] colours)
  {
    return new Transform()
    {
      @Override
      public int apply(final int c)
      {
        return colours[c];
      }

      @Override
      public Vector4 apply(final Vector4 v)
      {
        final double[] w = new double[4];
        for (int i = 0; i < 4; i += 1)
          for (int j = 0; j < 4; j += 1)
            w[i] += matrix[i][j] * v.elt(j);
        return Vector4.of(w);
      }
    };
  }
}
