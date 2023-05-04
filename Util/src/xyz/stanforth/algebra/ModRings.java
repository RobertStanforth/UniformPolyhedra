package xyz.stanforth.algebra;

public final class ModRings
{
  public static Field<Long> modRing(final long mod)
  {
    return new Field<Long>()
      {
        @Override
        public Long zero()
        {
          return 0L;
        }
        
        @Override
        public Long negate(final Long q)
        {
          return (mod-q)%mod;
        }
        
        @Override
        public Long add(final Long q0, final Long q1)
        {
          return (q0+q1)%mod;
        }
        
        @Override
        public Long subtract(final Long q0, final Long q1)
        {
          return (q0-q1)%mod;
        }

        @Override
        public Long identity()
        {
          return 1L;
        }
        
        @Override
        public Long inverse(final Long q)
        {
          return Euclid.inverse(mod, q);
        }
        
        @Override
        public Long multiply(final Long q0, final Long q1)
        {
          return (q0 * q1) % mod;
        }
        
        @Override
        public Long divide(final Long q0, final Long q1)
        {
          return Euclid.quotient(mod, q0, q1);
        }

        @Override
        public double norm(final Long q)
        {
          return q % mod == 0L ? 0. : 1.;
        }

        @Override
        public boolean equals(final Long q0, final Long q1)
        {
          return (q0 - q1) % mod == 0L;
        }

        @Override
        public int hashCode(final Long q)
        {
          return ((Long)((q%mod + mod)%mod)).hashCode();
        }
        
        @Override
        public String toString(final Long q)
        {
          return "[" + String.valueOf(q) + "]";
        }
      };
  }
  
  private ModRings() { }

}
