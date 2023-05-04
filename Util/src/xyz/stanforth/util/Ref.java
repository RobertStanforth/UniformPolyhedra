package xyz.stanforth.util;

public class Ref<T> extends Object
{
  private T value;
  
  public Ref(final T value)
  {
    this();
    set(value);
  }
  
  public Ref()
  {
    super();
  }
  
  public T value()
  {
    return value;
  }
  
  public boolean isSet()
  {
    return value != null;
  }
  
  public void unset()
  {
    this.value = null;
  }
  
  public void set(final T value)
  {
    this.value = value;
  }
  
  @Override
  public String toString()
  {
    return "[" + value().toString() + "]";
  }
}
