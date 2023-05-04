package xyz.stanforth.polyhedra.common;

import xyz.stanforth.geometry.PolygonType;

public interface FaceCandidate
{
  PolygonType polygonType();
  int indexA();
  int indexB();
}
