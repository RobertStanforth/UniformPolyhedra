package xyz.stanforth.svg;

public final class SvgBuilder
{
  private final StringBuilder stringBuilder;

  public SvgBuilder()
  {
    stringBuilder = new StringBuilder();
    stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    stringBuilder.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n");
  }

  public void line(final double x1, final double y1, final double x2, final double y2, final String stroke, final int strokeWidth)
  {
    stringBuilder.append(String.format("  <line x1=\"%.08f\" y1=\"%.08f\" x2=\"%.08f\" y2=\"%.08f\" stroke=\"%s\" stroke-width=\"%d\"/>\n",
            x1, y1, x2, y2, stroke, strokeWidth));
  }

  public void line(final double x1, final double y1, final double x2, final double y2, final String stroke, final int strokeWidth, final String strokeLinecap)
  {
    stringBuilder.append(String.format("  <line x1=\"%.08f\" y1=\"%.08f\" x2=\"%.08f\" y2=\"%.08f\" stroke=\"%s\" stroke-width=\"%d\" stroke-linecap=\"%s\"/>\n",
            x1, y1, x2, y2, stroke, strokeWidth, strokeLinecap));
  }

  public void circle(final double cx, final double cy, final double r, final String stroke, final int strokeWidth, final String fill)
  {
    stringBuilder.append(String.format("  <circle cx=\"%.08f\" cy=\"%.08f\" r=\"%.08f\" stroke=\"%s\" stroke-width=\"%d\" fill=\"%s\"/>\n",
            cx, cy, r, stroke, strokeWidth, fill));
  }

  public void text(final double x, final double y, final String fill, final int fontSize, final String fontStyle, final String text)
  {
    stringBuilder.append(String.format("  <text x=\"%.08f\" y=\"%.08f\" fill=\"%s\" font-size=\"%d\" font-style=\"%s\">%s</text>\n",
            x, y, fill, fontSize, fontStyle, text));
  }

  public String build()
  {
    return stringBuilder.toString() + "</svg>";
  }
}
