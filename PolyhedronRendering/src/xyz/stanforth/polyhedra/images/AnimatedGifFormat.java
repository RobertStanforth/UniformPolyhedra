package xyz.stanforth.polyhedra.images;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.List;

/**
 * Building animated GIFs from individual frames.
 */
public final class AnimatedGifFormat
{
  /**
   * Generates an animated GIF.
   * @param imageOutputStream Stream to which to write the animation.
   * @param renderedImages Individual frames to animate.
   * @param frameTimeMillis Time between frames in milliseconds.
   */
  public static void write(final ImageOutputStream imageOutputStream, final List<? extends RenderedImage> renderedImages, final int frameTimeMillis) throws IOException
  {
    final ImageWriter gifImageWriter = ImageIO.getImageWritersBySuffix("gif").next();
    gifImageWriter.setOutput(imageOutputStream);

    final ImageWriteParam imageWriteParam = gifImageWriter.getDefaultWriteParam();
    final IIOMetadata metadata = imageMetadata(gifImageWriter, imageWriteParam, frameTimeMillis);

    gifImageWriter.prepareWriteSequence(null);
    for (final RenderedImage renderedImage : renderedImages)
      gifImageWriter.writeToSequence(new IIOImage(renderedImage, null, metadata), imageWriteParam);
    gifImageWriter.endWriteSequence();
  }

  private static IIOMetadata imageMetadata(final ImageWriter gifImageWriter, final ImageWriteParam imageWriteParam, final int frameTimeMillis) throws IOException
  {
    final ImageTypeSpecifier imageType = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
    final IIOMetadata metadata = gifImageWriter.getDefaultImageMetadata(imageType, imageWriteParam);

    final IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree(metadata.getNativeMetadataFormatName());

    final IIOMetadataNode applicationExtensions = lazilyCreateChildNode(root, "ApplicationExtensions");
    final IIOMetadataNode applicationExtension = createChildNode(applicationExtensions, "ApplicationExtension");
    applicationExtension.setAttribute("applicationID", "NETSCAPE");
    applicationExtension.setAttribute("authenticationCode", "2.0");
    applicationExtension.setUserObject(new byte[]{ 1, 0, 0 }); // Last two bytes are number of repeats (little endian). Zero means forever.

    final IIOMetadataNode graphicControlExtension = lazilyCreateChildNode(root, "GraphicControlExtension");
    graphicControlExtension.setAttribute("delayTime", String.valueOf(frameTimeMillis / 10));

    metadata.setFromTree(metadata.getNativeMetadataFormatName(), root);
    return metadata;
  }

  private static IIOMetadataNode lazilyCreateChildNode(final IIOMetadataNode parent, final String name)
  {
    for (int i = 0; i < parent.getChildNodes().getLength(); i += 1)
      {
        final IIOMetadataNode child = (IIOMetadataNode)parent.getChildNodes().item(i);
        if (child.getNodeName().equalsIgnoreCase(name))
          return child;
      }

    return createChildNode(parent, name);
  }

  private static IIOMetadataNode createChildNode(final IIOMetadataNode parent, final String name)
  {
    final IIOMetadataNode node = new IIOMetadataNode(name);
    parent.appendChild(node);
    return node;
  }
}