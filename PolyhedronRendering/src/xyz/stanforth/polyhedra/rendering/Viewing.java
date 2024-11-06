package xyz.stanforth.polyhedra.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Transformation from "world" to "projected view".
 */
public final class Viewing
{
  /**
   * Transforms, shades, and projects the world scene ready for plotting.
   * @param worldShapes World scene to view.
   * @param inverseEye Transformation to apply to the world to line it up with an eye at the origin looking in the -z direction.
   * @param ambientLightColour RGB value of ambient light.
   * @param diffuseLightColour RGB value of diffuse light.
   * @param diffuseLightDirection Direction of diffuse light, relative to the eye.
   * @param gamma Gamma correction.
   * @return Projected scene to plot.
   */
  public static List<? extends PHShape> view(
          final List<? extends PHShape> worldShapes,
          final Transform inverseEye,
          final int ambientLightColour,
          final int diffuseLightColour,
          final Vector4 diffuseLightDirection,
          final double gamma)
  {
    final List<PHShape> projectedShapes = new ArrayList<>();
    for (final PHShape shape : worldShapes)
      {
        final List<? extends Vector4> worldVertices = shape.vertices().stream()
                .map(inverseEye::apply).toList();
        if (orientation(worldVertices) <= EPS)
          {
            final double[] ambientLightDcolour = fromRGB(ambientLightColour);
            final double[] diffuseLightDcolour = fromRGB(diffuseLightColour);
            final double diffuseLightCosine = Math.max(scalarProduct(unitNormal(worldVertices), diffuseLightDirection), 0.);
            final double[] totalLightDcolour = new double[3];
            for (int j = 0; j < 3; j += 1)
              totalLightDcolour[j] = ambientLightDcolour[j] + diffuseLightCosine * diffuseLightDcolour[j];

            final double[] dcolour = fromRGB(shape.colour());
            for (int j = 0; j < 3; j += 1)
              {
                dcolour[j] *= totalLightDcolour[j];
                dcolour[j] = Math.pow(dcolour[j], gamma);
              }
            final int colour = toRGB(dcolour);

            final double[] dcolourAlt = fromRGB(shape.colourAlt());
            for (int j = 0; j < 3; j += 1)
              {
                dcolourAlt[j] *= totalLightDcolour[j];
                dcolourAlt[j] = Math.pow(dcolourAlt[j], gamma);
              }
            final int colourAlt = toRGB(dcolourAlt);

            final List<? extends Vector4> projectedVertices = worldVertices.stream()
                    .map(Viewing::project)
                    .collect(Collectors.toList());

            projectedShapes.add(new PHShape()
            {
              @Override public List<? extends Vector4> vertices() { return projectedVertices; }
              @Override public int winding() { return shape.winding(); }
              @Override public int windingAlt() { return shape.windingAlt(); }
              @Override public int colour() { return colour; }
              @Override public int colourAlt() { return colourAlt; }
              @Override public int altVertices() { return shape.altVertices(); }
            });
          }
      }
    return projectedShapes;
  }

  /**
   * @param theta Colatitude in radians relative to z axis.
   * @param phi Azimuthal in radians relative to x axis.
   * @param distance Distance from the observer to the origin.
   * @return Observer transform.
   */
  public static Transform inverseEye(final double theta, final double phi, final double distance)
  {
    return Transform.of(
            new double[][] {
                    { Math.sin(phi)                ,-Math.cos(phi),                              0., 0.      },
                    {-Math.cos(phi)*Math.cos(theta),-Math.sin(phi)*Math.cos(theta), Math.sin(theta), 0.      },
                    {-Math.cos(phi)*Math.sin(theta),-Math.sin(phi)*Math.sin(theta),-Math.cos(theta),-distance},
                    { 0.,                        0.,                                             0., 1.      }
            },
            null
    );
  }

  /**
   * @param v Vector [x, y, z, 1] to project.
   * @return Projected vector [-x/z, -y/z, -1/z, 1].
   */
  private static Vector4 project(final Vector4 v)
  {
    return Vector4.of(
            v.elt(0) / -v.elt(2),
            v.elt(1) / -v.elt(2),
            v.elt(3) / -v.elt(2),
            -v.elt(2) / -v.elt(2)
    );
  }

  /**
   * @param vertices Vertices u, v, w.
   * @return Unit vector in the direction of (w-v)×(v-u), normal to the plane of u,v,w.
   */
  private static Vector4 unitNormal(final List<? extends Vector4> vertices)
  {
    final double[] vab = new double[4];
    double scc = 0.;
    for (int j = 0; j < 3; j += 1)
      {
        final double ak = vertices.get(2).elt((j+1)%3) - vertices.get(1).elt((j+1)%3);
        final double al = vertices.get(2).elt((j+2)%3) - vertices.get(1).elt((j+2)%3);
        final double bk = vertices.get(1).elt((j+1)%3) - vertices.get(0).elt((j+1)%3);
        final double bl = vertices.get(1).elt((j+2)%3) - vertices.get(0).elt((j+2)%3);
        final double cj = ak*bl - al*bk;
        vab[j] = cj;
        scc += cj * cj;
      }
    for (int j = 0; j < 3; j += 1)
      vab[j] /= Math.sqrt(scc);
    vab[3] = 0.;
    return Vector4.of(vab);
  }

  /**
   * @param vertices Vertices u, v, w
   * @return Scalar triple product u·(v-u)×(w-u), indicating the orientation of the plane of u,v,w relative to the origin.
   */
  private static double orientation(final List<? extends Vector4> vertices)
  {
    double tripleProduct = 0.;
    for (int j = 0; j < 3; j += 1)
      {
        final double ak = vertices.get(1).elt((j+1)%3) - vertices.get(0).elt((j+1)%3);
        final double al = vertices.get(1).elt((j+2)%3) - vertices.get(0).elt((j+2)%3);
        final double bk = vertices.get(2).elt((j+1)%3) - vertices.get(0).elt((j+1)%3);
        final double bl = vertices.get(2).elt((j+2)%3) - vertices.get(0).elt((j+2)%3);
        final double cj = vertices.get(0).elt(j);
        tripleProduct += (ak*bl - al*bk) * cj;
      }
    return tripleProduct;
  }

  /**
   * @param a First vector.
   * @param b Second vector.
   * @return 3D scalar product of the given vectors.
   */
  private static double scalarProduct(final Vector4 a, final Vector4 b)
  {
    double sab = 0.;
    for (int j = 0; j < 3; j += 1)
      {
        final double aj = a.elt(j);
        final double bj = b.elt(j);
        sab += aj * bj;
      }
    return sab;
  }

  /**
   * @param colour RGB hex colour.
   * @return Colour as a vector in the RGB colour cube [0,1]³.
   */
  private static double[] fromRGB(final int colour)
  {
    return new double[] {
            (double)((colour>>16) & 0xFF) / 255.,
            (double)((colour>>8) & 0xFF) / 255.,
            (double)((colour) & 0xFF) / 255.,
    };
  }

  /**
   * @param colour Colour as a vector in the RGB colour cube [0,1]³.
   * @return RGB hex colour.
   */
  private static int toRGB(final double[] colour)
  {
    return ((int)(colour[0]*255. + .5) << 16) | ((int)(colour[1]*255. + .5) << 8) | ((int)(colour[2]*255. + .5));
  }

  private static final double EPS = 1.e-7;

  private Viewing() {}
}
