package xyz.stanforth.util;

import java.util.*;

public class Iterators
{
  public static <T> CachedIterator<? extends T> cached(final Iterator<? extends T> iterator)
  {
    return new CachedIterator<T>()
      {
        private boolean gotCache = false;
        private T cachedItem = null;
        
        @Override
        public boolean hasItem()
        {
          return gotCache || iterator.hasNext();
        }
        
        @Override
        public T item()
        {
          prepareItem();
          return cachedItem;
        }
        
        @Override
        public void next()
        {
          prepareItem();
          gotCache = false;
        }
        
        private void prepareItem()
        {
          if (!gotCache)
            {
              cachedItem = iterator.next();
              gotCache = true;
            }
        }
      };
  }
  
  public static <T> Iterable<T> uncached(final CachedIterable<? extends T> cachedIterable)
  {
    return new Iterable<T>()
      {
        @Override
        public Iterator<T> iterator()
        {
          final CachedIterator<? extends T> cachedIterator = cachedIterable.iterator();
          return new Iterator<T>()
            {
              @Override
              public boolean hasNext()
              {
                return cachedIterator.hasItem();
              }
              
              @Override
              public T next()
              {
                final T item = cachedIterator.item();
                cachedIterator.next();
                return item;
              }
              
              @Override
              public void remove()
              {
                throw new UnsupportedOperationException();
              }
            };
        }
      };
  }
}
