package xyz.stanforth.polyhedra.common;

import xyz.stanforth.algebra.RingMatrix;
import xyz.stanforth.polyhedra.axes.Axis;
import xyz.stanforth.polyhedra.axes.AxisSet;

public interface Session<T>
{
  AxisSet<T> axes();
  Axis<T> axisForName(String name);
  RingMatrix<? extends T> identityTransform();
  RingMatrix<? extends T> axisRotation(Axis<T> axis, int power);
  RingMatrix<? extends T> diff(RingMatrix<? extends T> transform);
  Equation<T> equation(int index, Axis<T> axis, int power, boolean inverse);
  void forIdentityAndAxisRotations(RunnableWithTransform<? super T> vertexCollector);

  public interface RunnableWithTransform<T>
  {
    void run(RingMatrix<? extends T> transform);
  }
}
