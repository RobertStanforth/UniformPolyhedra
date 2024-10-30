package xyz.stanforth.polyhedra.plotting;

/**
 * 4-vector.
 */
public interface Vector4
{
  /**
   * @param i Index.
   * @return Component of the vector at the given index.
   */
  double elt(int i);

  /**
   * @param v Components of the vector.
   * @return Vector constructed from the given components.
   */
  static Vector4 of(final double... v)
  {
    return new Vector4()
    {
      @Override public double elt(int i) { return v[i]; }
    };
  }
}
