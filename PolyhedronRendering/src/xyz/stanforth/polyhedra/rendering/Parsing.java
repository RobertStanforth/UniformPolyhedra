package xyz.stanforth.polyhedra.rendering;

import xyz.stanforth.util.Ref;

import java.io.IOException;
import java.io.Reader;

/**
 * Facilities related to text parsing.
 */
public final class Parsing
{
  /**
   * Parses an integer, consumed from the given reader.
   * @param value Populated on exit with the value parsed.
   * @param reader Reader.
   * @param ch Maintains the most recent character taken from the reader, or -1 if EOF.
   */
  public static void readInt(final Ref<? super Integer> value, final Reader reader, final Ref<Integer> ch) throws IOException
  {
    skipWhitespace(reader, ch);
    int digitWeight = +1;
    if (ch.value() == '+')
      {
        digitWeight = +1;
        ch.set(reader.read());
      }
    else if (ch.value() == '-')
      {
        digitWeight = -1;
        ch.set(reader.read());
      }
    final String digits = "0123456789";
    int n = 0;
    while (digits.indexOf(ch.value()) >= 0)
      {
        n *= 10;
        n += digitWeight * (ch.value() - '0');
        ch.set(reader.read());
      }
    value.set(n);
  }

  /**
   * Parses a floating point value, consumed from the given reader.
   * @param value Populated on exit with the value parsed.
   * @param reader Reader.
   * @param ch Maintains the most recent character taken from the reader, or -1 if EOF.
   */
  public static void readDouble(final Ref<? super Double> value, final Reader reader, final Ref<Integer> ch) throws IOException
  {
    skipWhitespace(reader, ch);
    double digitWeight = +1.;
    if (ch.value() == '+')
      {
        digitWeight = +1.;
        ch.set(reader.read());
      }
    else if (ch.value() == '-')
      {
        digitWeight = -1.;
        ch.set(reader.read());
      }
    final String digits = "0123456789";
    double d = 0.;
    while (digits.indexOf(ch.value()) >= 0)
      {
        d *= 10.;
        d += digitWeight * (ch.value() - '0');
        ch.set(reader.read());
      }
    if (ch.value() == '.')
      {
        ch.set(reader.read());
        while (digits.indexOf(ch.value()) >= 0)
          {
            digitWeight /= 10.;
            d += (ch.value() - '0') * digitWeight;
            ch.set(reader.read());
          }
      }
    value.set(d);
  }

  /**
   * Consumes the current run of whitespace characters.
   * @param reader Reader.
   * @param ch Maintains the most recent character taken from the reader, or -1 if EOF.
   */
  public static void skipWhitespace(final Reader reader, final Ref<Integer> ch) throws IOException
  {
    while ("\t\n\f\r ".indexOf(ch.value()) >= 0)
      {
        ch.set(reader.read());
      }
  }

  private Parsing() {}
}
