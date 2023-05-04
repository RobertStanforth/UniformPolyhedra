package xyz.stanforth.polyhedra.nonplanar;

@FunctionalInterface
public interface RunnableWithTriple<U>
{
  void run(U a, U b, U c);
}