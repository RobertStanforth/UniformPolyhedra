package xyz.stanforth.algebra;

import java.util.*;

public final class RingMatrices
{
  public interface Builder<T>
  {
    void set(int i, int j, T value);
    void add(int i, int j, T value);
    void add(RingMatrix<? extends T> m1);
    void add(T c, RingMatrix<? extends T> m1);
    RingMatrix<? extends T> build();
  }
  
  public static <T> Builder<T> builder(final Ring<T> ring, final int size)
  {
    final List<T> data = new ArrayList<>(Collections.nCopies(size*size, ring.zero()));
    return new Builder<T>()
    {
      @Override
      public void set(final int i, final int j, final T value)
      {
        data.set(size*i+j, value);
      }
      
      @Override
      public void add(final int i, final int j, final T value)
      {
        data.set(size*i+j, ring.add(data.get(size*i+j), value));
      }
      
      @Override
      public void add(final RingMatrix<? extends T> m1)
      {
        for (int i = 0; i < size; i ++)
          for (int j = 0; j < size; j ++)
            add(i, j, m1.elt(i, j));
      }
      
      @Override
      public void add(final T c, final RingMatrix<? extends T> m1)
      {
        for (int i = 0; i < size; i ++)
          for (int j = 0; j < size; j ++)
            add(i, j, ring.multiply(c, m1.elt(i, j)));
      }
      
      @Override
      public RingMatrix<? extends T> build()
      {
        return new RingMatrix<T>()
        {
          @Override
          public T elt(final int i, final int j)
          {
            return data.get(size*i+j);
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
                    s.append(ring.toString(elt(i, j)));
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

  public static <T> RingMatrix<? extends T> zero(final Ring<T> ring, final int size)
  {
    final Builder<T> builder = builder(ring, size);
    return builder.build();
  }
  
  public static <T> RingMatrix<? extends T> sum(final Ring<T> ring, final int size, final RingMatrix<? extends T> m0, final T c, final RingMatrix<? extends T> m1)
  {
    final Builder<T> builder = builder(ring, size);
    builder.add(m0);
    builder.add(c, m1);
    return builder.build();
  }

  public static <T> RingMatrix<? extends T> identity(final Ring<T> ring, final int size)
  {
    final Builder<T> builder = builder(ring, size);
    for (int i = 0; i < size; i ++)
      builder.add(i, i, ring.identity());
    return builder.build();
  }
  
  public static <T> RingMatrix<? extends T> product(final Ring<T> ring, final int size, final RingMatrix<? extends T> m0, final RingMatrix<? extends T> m1)
  {
    final Builder<T> builder = builder(ring, size);
    for (int i = 0; i < size; i ++)
      for (int j = 0; j < size; j ++)
        for (int k = 0; k < size; k ++)
          builder.add(i, k, ring.multiply(m0.elt(i, j), m1.elt(j, k)));
    return builder.build();
  }
  
  public static <T> RingMatrix<? extends T> power(final Ring<T> ring, final int size, final RingMatrix<? extends T> m, final long b)
  {
    long c = 1L << 62L;
    while (c > b)
      c >>= 1L;
    RingMatrix<? extends T> res = identity(ring, size);
    while (c > 0L)
      {
        res = product(ring, size, res, res);
        if ((b&c) != 0L)
          res = product(ring, size, res, m);
        c >>= 1L;
      }
    return res;
  }

  public static <T> RingMatrix<? extends T> inverse(final Field<T> field, final int size, final RingMatrix<? extends T> m)
  {
    if (size > 64)
      throw new RuntimeException("This implementation does not work for matrices larger than 64 by 64.");
    final Builder<T> left = builder(field, size);
    final RingMatrix<? extends T> leftM = left.build();
    left.add(m);
    final Builder<T> right = builder(field, size);
    final RingMatrix<? extends T> rightM = right.build();
    for (int i = 0; i < size; i ++)
      right.add(i, i, field.identity());

    long mask = (1L<<(long)size) - 1L;
    while (mask != 0L)
      {
        // Find the largest remaining element.
        int ip = -1;
        int jp = -1;
        T pivot = field.zero();
        for (int i = 0; i < size; i ++)
          if ((mask & (1L<<(long)i)) != 0L)
            for (int j = 0; j < size; j ++)
              if ((mask & (1L<<(long)j)) != 0L)
                if (field.norm(leftM.elt(i, j)) > field.norm(pivot))
                  {
                    ip = i;
                    jp = j;
                    pivot = leftM.elt(i, j);
                  }
        if (field.equals(pivot, field.zero()))
          throw new RuntimeException("Singular matrix");
        
        // Swap the pivot onto the diagonal.
        for (int j = 0; j < size; j ++)
          {
            final T val = leftM.elt(ip, j);
            left.set(ip, j, leftM.elt(jp, j));
            left.set(jp, j, field.divide(val, pivot));
          }
        for (int j = 0; j < size; j ++)
          {
            final T val = rightM.elt(ip, j);
            right.set(ip, j, rightM.elt(jp, j));
            right.set(jp, j, field.divide(val, pivot));
          }
        
        // Subtract the pivot row from all others.
        for (int i = 0; i < size; i ++)
          if (i != jp)
            {
              final T d = leftM.elt(i, jp);
              for (int j = 0; j < size; j ++)
                left.add(i, j, field.multiply(field.negate(d), leftM.elt(jp, j)));
              for (int j = 0; j < size; j ++)
                right.add(i, j, field.multiply(field.negate(d), rightM.elt(jp, j)));
            }
        
        mask &= ~(1L << (long)jp);
      }
    
    return rightM;
  }

  public static <T> RingMatrix<? extends T> inverseNoPivot(final Field<T> field, final int size, final RingMatrix<? extends T> m)
  {
    final Builder<T> left = builder(field, size);
    final RingMatrix<? extends T> leftM = left.build();
    left.add(m);
    final Builder<T> right = builder(field, size);
    final RingMatrix<? extends T> rightM = right.build();
    for (int i = 0; i < size; i ++)
      right.add(i, i, field.identity());

    for (int k = 0; k < size; k += 1)
      {
        final T pivot = leftM.elt(k, k);
        if (field.equals(pivot, field.zero()))
          throw new RuntimeException("Singular matrix");

        // Swap the pivot onto the diagonal.
        for (int j = 0; j < size; j ++)
          {
            final T val = leftM.elt(k, j);
            left.set(k, j, leftM.elt(k, j));
            left.set(k, j, field.divide(val, pivot));
          }
        for (int j = 0; j < size; j ++)
          {
            final T val = rightM.elt(k, j);
            right.set(k, j, rightM.elt(k, j));
            right.set(k, j, field.divide(val, pivot));
          }

        // Subtract the pivot row from all others.
        for (int i = 0; i < size; i ++)
          if (i != k)
            {
              final T d = leftM.elt(i, k);
              for (int j = 0; j < size; j ++)
                left.add(i, j, field.multiply(field.negate(d), leftM.elt(k, j)));
              for (int j = 0; j < size; j ++)
                right.add(i, j, field.multiply(field.negate(d), rightM.elt(k, j)));
            }
      }

    return rightM;
  }

  public static <T> RingMatrix<? extends T> product_parallel(final int numThreads, final Ring<T> ring, final int size, final RingMatrix<? extends T> m0, final RingMatrix<? extends T> m1)
  {
    final Builder<T> builder = builder(ring, size);
    
    final int[] done = { 0 };
    
    for (int t = 0; t < numThreads; t ++)
      {
        final int thread = t;
        new Thread(new Runnable()
        {
          @Override
          public void run()
          {
            for (int i = thread; i < size; i += numThreads)
              for (int j = 0; j < size; j ++)
                for (int k = 0; k < size; k ++)
                  builder.add(i, k, ring.multiply(m0.elt(i, j), m1.elt(j, k)));
            
            synchronized (done)
            {
              ++ done[0];
              done.notifyAll();
            }
          }
        }).start();
      }
    
    synchronized (done)
    {
      try
      {
        while (done[0] < numThreads)
          done.wait();
      }
      catch (final InterruptedException e)
      {
        throw new RuntimeException(e);
      }
    }
    
    return builder.build();
  }
  
  public static <T> RingMatrix<? extends T> power_parallel(final int numThreads, final Ring<T> ring, final int size, final RingMatrix<? extends T> m, final long b)
  {
    long c = (1L<<62L);
    while (c > b)
      c >>= 1L;
    RingMatrix<? extends T> res = identity(ring, size);
    while (true)
      {
        if ((b&c) != 0L)
          res = product_parallel(numThreads, ring, size, res, m);
        c >>= 1L;
        if (c == 0L)
          return res;
        res = product_parallel(numThreads, ring, size, res, res);
      }
  }

  private RingMatrices() { }
}
