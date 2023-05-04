package xyz.stanforth.polyhedra.planar;

public interface PlanarEquation<T>
{
  int index();
  String name();
  T axx();
  T a2xz();
  T azz();
}
