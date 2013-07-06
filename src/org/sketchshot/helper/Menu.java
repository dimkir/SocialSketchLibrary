package org.sketchshot.helper;
import processing.core.*;
/**
 *  This class is to abstract displaying of the on screen menu.
 */
public class Menu extends AbstractLibraryHelper
{
  
  private LibraryDrawing mLD;
  private boolean mIsDisplayed;
  private PImage tempImage;
  private FontBoss mFB;
  
  public Menu(PApplet parent, LibraryDrawing ld, FontBoss fb){
     super(parent);
     mLD = ld;
     mFB = fb; // font boss
  }
  
  
  /**
   * Returns flag.
   */
  public boolean isDisplayedFlag(){
    //TODO: what does this do?
    return mIsDisplayed;
  }
  
  
  public void setDisplayedFlag(boolean vIsDisplayed){
     mIsDisplayed = vIsDisplayed;
  }
  
  
  /**
   * Draws to default rednerer, stated in the beginning.
   */
  public void draw(){
    // TODO: implement some drawing of the menu.
      // if temp image is available: then 
      // do things.
      if ( tempImage != null ){
          // draw the image
          mLD.image(tempImage, 0, 0);
      }
      
      mFB.text("MENU TEXT", sketch().width / 2, sketch().height /2 );
  }

  /**
   * Just sets image to display. The image should not be a "live" graphics, 
   * but some safely stored static image. This class is going to save reference
   * to the image (no defensive copying here).
   * @param screenShotOriginalSize  is the client actually live PGraphics???
   */
    public void setImageToDisplay(PImage screenShotOriginalSize) {
           tempImage = screenShotOriginalSize;
    }
}