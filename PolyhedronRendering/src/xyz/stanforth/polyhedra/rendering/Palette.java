package xyz.stanforth.polyhedra.rendering;

/**
 * Palette of RGB colours to use, dependent on symmetry group.
 */
public interface Palette
{
  /**
   * @return Background RGB colour.
   */
  default int background() { return PH_BLACK; }

  /**
   * @param symmetryGroup Symmetry group: cuboctahedral (0), icosidodecahedral (1), tetrahedral (2), or prismatic (>=3).
   * @param colourIndex Within-palette colour index.
   * @return RGB colour from the palette.
   */
  int entry(int symmetryGroup, int colourIndex);

  /**
   * Default palette: 11 distinct colors for icosidodecahedral faces.
   */
  Palette DEFAULT = new Palette()
  {
    private final int[] cuboctahedral = {
            PH_RED,PH_BROWN,PH_PINK,PH_GREEN,                         // octahedral
            PH_YELLOW,PH_ORANGE,PH_BLUE,                              // hexahedral
            PH_GREEN,PH_RED,                                          // tetrahedral
            0,0,
            PH_WHITE,PH_RED,PH_YELLOW,PH_BLUE,                        // fixed colors
            0,0,0,0,PH_GREEN,PH_RED,PH_PURPLE,0,0,0
    };

    private final int[] icosidodecahedral = {
            PH_BROWN,PH_PINK,PH_PURPLE,PH_RED,PH_GREEN,               // icosahedral
            PH_YELLOW,PH_CYAN,PH_BLUE,PH_DGREEN,PH_ORANGE,PH_IVORY,   // dodecahedral
            PH_WHITE,PH_RED,PH_YELLOW,PH_BLUE,                        // fixed colors
            PH_RED,PH_PINK,PH_BROWN,PH_GREEN,PH_PURPLE,PH_ORANGE,PH_YELLOW,PH_BLUE,PH_CYAN,PH_IVORY  // icosahedral with one color for each plane
    };

    @Override
    public int entry(final int symmetryGroup, final int colourIndex)
    {
      if (symmetryGroup == 0 || symmetryGroup == 2)
        return cuboctahedral[colourIndex];
      else
        return icosidodecahedral[colourIndex];
    }
  };

  /**
   * Alternative palette: hexahedral and dodecahedral faces are white.
   */
  Palette ALT_A = new Palette()
  {
    private final int[] cuboctahedral = {
            PH_RED,PH_YELLOW,PH_BLUE,PH_GREEN,                        // octahedral
            PH_WHITE,PH_WHITE,PH_WHITE,                               // hexahedral
            PH_GREEN,PH_RED,                                          // tetrahedral
            0,0,
            PH_WHITE,PH_RED,PH_YELLOW,PH_BLUE,                        // fixed colors
            0,0,0,0,PH_GREEN,PH_RED,PH_PURPLE,0,0,0
    };

    private final int[] icosidodecahedral = {
            PH_YELLOW,PH_BLUE,PH_PINK,PH_RED,PH_GREEN,                // icosahedral
            PH_WHITE,PH_WHITE,PH_WHITE,PH_WHITE,PH_WHITE,PH_WHITE,    // dodecahedral
            PH_WHITE,PH_RED,PH_YELLOW,PH_BLUE,                        // fixed colors
            PH_RED,PH_PINK,PH_BROWN,PH_GREEN,PH_PURPLE,PH_ORANGE,PH_YELLOW,PH_BLUE,PH_CYAN,PH_IVORY  // icosahedral with one color for each plane
    };

    @Override
    public int entry(final int symmetryGroup, final int colourIndex)
    {
      if (symmetryGroup == 0 || symmetryGroup == 2)
        return cuboctahedral[colourIndex];
      else
        return icosidodecahedral[colourIndex];
    }
  };

  /**
   * Alternative palette: octahedral and icosahedral faces are white.
   */
  Palette ALT_B = new Palette()
  {
    private final int[] cuboctahedral = {
            PH_WHITE,PH_WHITE,PH_WHITE,PH_WHITE,                      // octahedral
            PH_YELLOW,PH_RED,PH_BLUE,                                 // hexahedral
            PH_GREEN,PH_PINK,                                         // tetrahedral
            0,0,
            PH_WHITE,PH_RED,PH_YELLOW,PH_BLUE,                        // fixed colors
            0,0,0,0,PH_GREEN,PH_RED,PH_PURPLE,0,0,0
    };

    private final int[] icosidodecahedral = {
            PH_WHITE,PH_WHITE,PH_WHITE,PH_WHITE,PH_WHITE,             // icosahedral
            PH_YELLOW,PH_PINK,PH_BLUE,PH_GREEN,PH_ORANGE,PH_RED,      // dodecahedral
            PH_WHITE,PH_RED,PH_YELLOW,PH_BLUE,                        // fixed colors
            PH_RED,PH_PINK,PH_BROWN,PH_GREEN,PH_PURPLE,PH_ORANGE,PH_YELLOW,PH_BLUE,PH_CYAN,PH_IVORY  // icosahedral with one color for each plane
    };

    @Override
    public int entry(final int symmetryGroup, final int colourIndex)
    {
      if (symmetryGroup == 0 || symmetryGroup == 2)
        return cuboctahedral[colourIndex];
      else
        return icosidodecahedral[colourIndex];
    }
  };

  int PH_BLUE      = 0x0000FF;
  int PH_YELLOW    = 0xFFFF00;
  int PH_GREEN     = 0x00FF00;
  int PH_RED       = 0xFF0000;
  int PH_PINK      = 0xFF80BF;
  int PH_ORANGE    = 0xFF4000;
  int PH_DGREEN    = 0x408000;
  int PH_CYAN      = 0x00BFFF;
  int PH_PURPLE    = 0xBF00FF;
  int PH_BROWN     = 0x804000;
  int PH_IVORY     = 0xFFFF80;
  int PH_WHITE     = 0xFFFFFF;
  int PH_BLACK     = 0x000000;
}
