package xyz.stanforth.algebra;

public final class Euclid
{
  public static int hcf(int a, int b)
  {
    while (b != 0)
    {
      final int r = a%b;
      a = b;
      b = r;
    }
    return a;
  }

  public static long hcf(long a, long b)
  {
    while (b != 0)
    {
      final long r = a%b;
      a = b;
      b = r;
    }
    return a;
  }

  public static long inverse(final long mod, final long n)
  {
    long a = mod;
    long b = n;
    long up = 1;
    long vp = 0;
    long u = 0;
    long v = 1;
    while (b != 0)
    {
      final long q = a/b;
      final long r = a%b;
      final long un = up - q*u;
      final long vn = vp - q*v;
      a = b; b = r;
      up = u; u = un;
      vp = v; v = vn;
    }
    return (vp + mod) % mod;
  }

  public static long quotient(final long mod, final long m, final long n)
  {
    return (m * inverse(mod, n)) % mod;
  }

  public static long chineseRemainder(final long mod0, final long r0, final long mod1, final long r1)
  {
    long remainder = ((r0 * Euclid.inverse(mod0, mod1))%mod0 * mod1 + (r1 * Euclid.inverse(mod1, mod0))%mod1 * mod0) % (mod0 * mod1);
    if (remainder < 0L)
      remainder += mod0 * mod1;
    return remainder;
  }

  private Euclid() { }
}
