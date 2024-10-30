package xyz.stanforth.polyhedra.plotting;

import java.util.List;

/**
 * Polygon, possibly compound.
 */
public interface PHShape
{
  /**
   * @return Vertices of the polygon.
   */
  List<? extends Vector4> vertices();

  /**
   * @return Winding number of the polygon about its centre.
   */
  int winding();

  /**
   * @return Winding number of the alternate part of the polygon about the centre.
   */
  int windingAlt();

  /**
   * @return RGB colour of the face.
   */
  int colour();

  /**
   * @return RGB colour of the alternate part of the face.
   */
  int colourAlt();

  /**
   * @return Bitset indicating which vertices (by index) belong to the alternate part of the polygon.
   */
  int altVertices();
}
