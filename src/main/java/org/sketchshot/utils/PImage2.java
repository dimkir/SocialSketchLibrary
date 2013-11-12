package org.sketchshot.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import processing.core.PImage;

/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class PImage2 extends PImage {

  public PImage2(){
      super();
  }
    
  public PImage2(int width, int height) {
        super(width, height);
  }
  
  public static final int INT_MAX = 0xFFFFFFFF;
  
  /**
   * @var C_PATTERN_BLACK_WHITE sample pixel pattern
   */
  public static final int[] C_PATTERN_BLACK_WHITE = { 0 , INT_MAX , 0, INT_MAX , 0, INT_MAX };
  
  /**
   * Initializes PImage of given size and fills it with given pixel pattern.
   * (So that we can be sure that there's something there.
   * @param width
   * @param height
   * @param dummyPattern 
   */
  public PImage2(int width, int height, int[] dummyPattern){
      super(width, height);
      loadPixels();
      int dpPointer = 0;
      int dpLen = dummyPattern.length;
      for(int i = 0 ; i < pixels.length ; i++){
           pixels[i] = dummyPattern[dpPointer];
          // move to next pattern
           dpPointer++;
           if ( i > 0 && ( i % (width -1) == 0 )){
               dpPointer++;
           }
           dpPointer %= dpLen;
      }
      updatePixels();
  }
   
    
  /**
   * This takes as a parameter another PImage (the original) class, 
   * and returns InputStream which is in compressed format.
   * @param img
   * @param formatExtension
   * @return 
   */
  public ByteArrayInputStream getPImageAsInputStream(PImage img, String formatExtension){
      resize(img.width, img.height);
      copy(img, 0, 0, img.width, img.height, 0, 0, img.width, img.height);
      return getImageAsInputStream(formatExtension);
  }
  
  
  /**
   * Another helper wrapper, which takes the input stream and puts it into the file.
   * This one is for testing purposes.
   * @param filename
   * @param img
   * @param formatExtension 
   * @return true on success
   *         false on error.
   */
  public boolean putPImageAsInputStreamToFile(String filename, PImage img, String formatExtension){
      InputStream is = getPImageAsInputStream(img, formatExtension);
      return saveInputStreamToFile(filename, is);
  }
  
  
/**
 * Helper method, just saves InputStream to file.
 * @param filename
 * @param is
 * @return 
 */ 
private boolean  saveInputStreamToFile(String filename, InputStream is){
      OutputStream os = null;
      try {
          File f = new File(filename);
          os = new FileOutputStream(f);
          int b;
          while ( ( b = is.read() ) != -1 ){
              os.write(b);
          }
          os.flush();
          os.close();
          return true;

      
      } catch (FileNotFoundException ex) {
          Logger.getLogger(PImage2.class.getName()).log(Level.SEVERE, null, ex);
          return false; // failure saving.
      } 
      catch(IOException ioex){
          Logger.getLogger(PImage2.class.getName()).log(Level.SEVERE, null, ioex);
          return false; // failure saving.
      }
      finally {
          try {
              os.close();
          } catch (IOException ex) {
              Logger.getLogger(PImage2.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
}       
    
  /**
   * Returns contents of the image as input stream.
   * @param formatExtension
   * @return NULL on error.
   */
  public ByteArrayInputStream getImageAsInputStream(String formatExtension){
      try {
          ByteArrayOutputStream barrOutputStream = new ByteArrayOutputStream();
          BufferedOutputStream buffOutputStream = new BufferedOutputStream(barrOutputStream);
          saveImageToStream(formatExtension, buffOutputStream);
          
          ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(barrOutputStream.toByteArray());
          
          return byteArrayInputStream;
          
      } catch (IOException ex) {
          Logger.getLogger(PImage2.class.getName()).log(Level.SEVERE, null, ex);
          return null;
      }
  }
    
  /**
   * Use ImageIO functions from Java 1.4 and later to handle image save.
   * Various formats are supported, typically jpeg, png, bmp, and wbmp.
   * To get a list of the supported formats for writing, use: <BR>
   * <TT>println(javax.imageio.ImageIO.getReaderFormatNames())</TT>
   */
  public boolean saveImageToStream(String extension, BufferedOutputStream outStream) throws IOException {
    try {
      int outputFormat = (format == ARGB) ?
        BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
      
      
      extension = extension.toLowerCase();


      // JPEG and BMP images that have an alpha channel set get pretty unhappy.
      // BMP just doesn't write, and JPEG writes it as a CMYK image.
      // http://code.google.com/p/processing/issues/detail?id=415
      if (extension.equals("bmp") || extension.equals("jpg") || extension.equals("jpeg")) {
        outputFormat = BufferedImage.TYPE_INT_RGB;
      }

      BufferedImage bimage = new BufferedImage(width, height, outputFormat);
      bimage.setRGB(0, 0, width, height, pixels, 0, width);

      ImageWriter writer = null;
      ImageWriteParam param = null;
      IIOMetadata metadata = null;

      if (extension.equals("jpg") || extension.equals("jpeg")) {
        if ((writer = imageioWriter("jpeg")) != null) {
          // Set JPEG quality to 90% with baseline optimization. Setting this
          // to 1 was a huge jump (about triple the size), so this seems good.
          // Oddly, a smaller file size than Photoshop at 90%, but I suppose
          // it's a completely different algorithm.
          param = writer.getDefaultWriteParam();
          param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
          param.setCompressionQuality(0.9f);
        }
      }

      if (extension.equals("png")) {
        if ((writer = imageioWriter("png")) != null) {
          param = writer.getDefaultWriteParam();
          if (false) {
            metadata = imageioDPI(writer, param, 100);
          }
        }
      }

      if (writer != null) {
        BufferedOutputStream output = outStream;
//          new BufferedOutputStream(PApplet.createOutput(file));
        writer.setOutput(ImageIO.createImageOutputStream(output));
//        writer.write(null, new IIOImage(bimage, null, null), param);
        writer.write(metadata, new IIOImage(bimage, null, metadata), param);
        writer.dispose();

        output.flush();
        output.close();
        return true;
      }
      // If iter.hasNext() somehow fails up top, it falls through to here
      return true; // kinda success

    } catch (Exception e) {
      e.printStackTrace();
      throw new IOException("image save failed.");
    }
  }    
    
  
  
 private ImageWriter imageioWriter(String extension) {
    Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(extension);
    if (iter.hasNext()) {
      return iter.next();
    }
    return null;
  }  
 
 
 private IIOMetadata imageioDPI(ImageWriter writer, ImageWriteParam param, double dpi) {
    // http://stackoverflow.com/questions/321736/how-to-set-dpi-information-in-an-image
    ImageTypeSpecifier typeSpecifier =
      ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
    IIOMetadata metadata =
      writer.getDefaultImageMetadata(typeSpecifier, param);

    if (!metadata.isReadOnly() && metadata.isStandardMetadataFormatSupported()) {
      // for PNG, it's dots per millimeter
      double dotsPerMilli = dpi / 25.4;

      IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
      horiz.setAttribute("value", Double.toString(dotsPerMilli));

      IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
      vert.setAttribute("value", Double.toString(dotsPerMilli));

      IIOMetadataNode dim = new IIOMetadataNode("Dimension");
      dim.appendChild(horiz);
      dim.appendChild(vert);

      IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
      root.appendChild(dim);

      try {
        metadata.mergeTree("javax_imageio_1.0", root);
        return metadata;

      } catch (IIOInvalidTreeException e) {
        System.err.println("Could not set the DPI of the output image");
        e.printStackTrace();
      }
    }
    return null;
  }
 
}
