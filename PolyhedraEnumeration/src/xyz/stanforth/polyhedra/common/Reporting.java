package xyz.stanforth.polyhedra.common;

import java.io.*;
import java.util.*;

public final class Reporting
{
  public static void print(final PrintStream out, final int numRealRoots, final Collection<? extends FaceCandidate> candidates)
  {
    if (candidates.size() >= 3)
      {
        String s = "  " + String.valueOf(numRealRoots) + " solution(s): ";
        for (final FaceCandidate candidate : candidates)
          {
            s += "   " + indexString(candidate.indexA()) + indexString(candidate.indexB()) + ": ";
            s += String.valueOf(candidate.polygonType().degree()) + "/" + String.valueOf(candidate.polygonType().winding());
          }
        out.println(s);
      }
  }

  private static String indexString(final int index)
  {
    return chars.substring(index, index+1);
  }

  private static final String chars = "abcdefghijklmnop";
  
  private Reporting() { }
}
