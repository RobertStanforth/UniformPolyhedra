package xyz.stanforth.algebra;

public final class Matrices
{
  public interface Builder
  {
    void set(int i, int j, double value);
    void add(int i, int j, double value);
    void add(Matrix m1);
    void add(double c, Matrix m1);
    Matrix build();
  }
  
  public static Builder builder(final int size)
  {
    final double[] data = new double[size*size];
    return new Builder()
    {
      @Override
      public void set(final int i, final int j, final double value)
      {
        data[size*i+j] = value;
      }
      
      @Override
      public void add(final int i, final int j, final double value)
      {
        data[size*i+j] += value;
      }
      
      @Override
      public void add(final Matrix m1)
      {
        for (int i = 0; i < size; i ++)
          for (int j = 0; j < size; j ++)
            add(i, j, m1.elt(i, j));
      }
      
      @Override
      public void add(final double c, final Matrix m1)
      {
        for (int i = 0; i < size; i ++)
          for (int j = 0; j < size; j ++)
            add(i, j, c * m1.elt(i, j));
      }
      
      @Override
      public Matrix build()
      {
        return new Matrix()
        {
          @Override
          public double elt(final int i, final int j)
          {
            return data[size*i+j];
          }
          
          @Override
          public String toString()
          {
            final StringBuilder s = new StringBuilder();
            s.append("[");
            for (int i = 0; i < size; i ++)
              {
                s.append("[");
                for (int j = 0; j < size; j ++)
                  {
                    s.append(String.valueOf(elt(i, j)));
                    s.append("\t");
                  }
                s.append("]\n");
              }
            s.append("]");
            return s.toString();
          }
        };
      }
    };
  }

  public static Matrix sum(final int size, final Matrix m0, final double c, final Matrix m1)
  {
    final Builder builder = builder(size);
    builder.add(m0);
    builder.add(c, m1);
    return builder.build();
  }

  public static Matrix identity(final int size)
  {
    final Builder builder = builder(size);
    for (int i = 0; i < size; i ++)
      builder.add(i, i, 1.);
    return builder.build();
  }

  public static Matrix product(final int size, final Matrix m0, final Matrix m1)
  {
    final Builder builder = builder(size);
    for (int i = 0; i < size; i ++)
      for (int j = 0; j < size; j ++)
        for (int k = 0; k < size; k ++)
          builder.add(i, k, m0.elt(i, j) * m1.elt(j, k));
    return builder.build();
  }
  
  public static Matrix inverse(final int size, final Matrix m)
  {
    if (size > 64)
      throw new RuntimeException("This implementation does not work for matrices larger than 64 by 64.");
    final Builder left = builder(size);
    final Matrix leftM = left.build();
    left.add(m);
    final Builder right = builder(size);
    final Matrix rightM = right.build();
    for (int i = 0; i < size; i ++)
      right.add(i, i, 1.);

    long mask = (1L<<(long)size) - 1L;
    while (mask != 0L)
      {
        // Find the largest remaining element.
        int ip = -1;
        int jp = -1;
        double pivot = 0.;
        for (int i = 0; i < size; i ++)
          if ((mask & (1L<<(long)i)) != 0L)
            for (int j = 0; j < size; j ++)
              if ((mask & (1L<<(long)j)) != 0L)
                if (Math.abs(leftM.elt(i, j)) > Math.abs(pivot))
                  {
                    ip = i;
                    jp = j;
                    pivot = leftM.elt(i, j);
                  }
        if (pivot == 0.)
          throw new RuntimeException("Singular matrix");
        
        // Swap the pivot onto the diagonal.
        for (int j = 0; j < size; j ++)
          {
            final double val = leftM.elt(ip, j);
            left.set(ip, j, leftM.elt(jp, j));
            left.set(jp, j, val/pivot);
          }
        for (int j = 0; j < size; j ++)
          {
            final double val = rightM.elt(ip, j);
            right.set(ip, j, rightM.elt(jp, j));
            right.set(jp, j, val/pivot);
          }
        
        // Subtract the pivot row from all others.
        for (int i = 0; i < size; i ++)
          if (i != jp)
            {
              final double d = leftM.elt(i, jp);
              for (int j = 0; j < size; j ++)
                left.add(i, j, -d*leftM.elt(jp, j));
              for (int j = 0; j < size; j ++)
                right.add(i, j, -d*rightM.elt(jp, j));
            }
        
        mask &= ~(1L << (long)jp);
      }
    
    return rightM;
  }

  public static Matrix inverseNoPivot(final int size, final Matrix m)
  {
    final Builder left = builder(size);
    final Matrix leftM = left.build();
    left.add(m);
    final Builder right = builder(size);
    final Matrix rightM = right.build();
    for (int i = 0; i < size; i ++)
      right.add(i, i, 1.);

    for (int k = 0; k < size; k += 1)
      {
        final double pivot = leftM.elt(k, k);
        if (pivot == 0.)
          throw new RuntimeException("Singular matrix");

        // Swap the pivot onto the diagonal.
        for (int j = 0; j < size; j ++)
          {
            final double val = leftM.elt(k, j);
            left.set(k, j, leftM.elt(k, j));
            left.set(k, j, val/pivot);
          }
        for (int j = 0; j < size; j ++)
          {
            final double val = rightM.elt(k, j);
            right.set(k, j, rightM.elt(k, j));
            right.set(k, j, val/pivot);
          }

        // Subtract the pivot row from all others.
        for (int i = 0; i < size; i ++)
          if (i != k)
            {
              final double d = leftM.elt(i, k);
              for (int j = 0; j < size; j ++)
                left.add(i, j, -d*leftM.elt(k, j));
              for (int j = 0; j < size; j ++)
                right.add(i, j, -d*rightM.elt(k, j));
            }
      }

    return rightM;
  }

  private Matrices() { }

}
