/**
 * Copyright (C) 1993-2011 ID Business Solutions Limited
 * All rights reserved
 *  
 * Created by: Robert
 * Created Date: 10 Jul 2011
 *  
 * Last changed: $Header: $ 
 */
package xyz.stanforth.util;

import java.util.*;

public final class Pairs
{
  public static <First, Second> Pair<? extends First, ? extends Second> of(final First first, final Second second)
  {
    return new Pair<First, Second>()
      {
        @Override
        public First first()
        {
          return first;
        }
        
        @Override
        public Second second()
        {
          return second;
        }

        @Override
        public boolean equals(final Object other)
        {
          return other instanceof Pair<?, ?>
            && first().equals(((Pair<?, ?>)other).first())
            && second().equals(((Pair<?, ?>)other).second());
        }
        
        @Override
        public int hashCode()
        {
          return first().hashCode() + 37 * second().hashCode();
        }
        
        @Override
        public String toString()
        {
          return "[" + first().toString() + ", " + second().toString() + "]";
        }
        
      };
  }
  
  public static <First, Second> Comparator<? super Pair<? extends First, ? extends Second>>
    lexicalComparator(final Comparator<? super First> compFirst, final Comparator<? super Second> compSecond)
  {
    return Comparator.<Pair<? extends First, ? extends Second>, First>comparing(Pair::first, compFirst).thenComparing(Pair::second, compSecond);
  }
  
  private Pairs() {}
}