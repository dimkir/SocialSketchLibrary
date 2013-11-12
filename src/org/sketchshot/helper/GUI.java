package org.sketchshot.helper;
import processing.core.*;
/**
 *  This class is to abstract displaying of the on screen menu.
 */
public class GUI extends AbstractLibraryHelper
{
  
  private LibraryDrawing mLD;
  private boolean mIsDisplayed;
  private PImage tempImage;
  private FontBoss mFB;
  
  public GUI(PApplet parent, LibraryDrawing ld, FontBoss fb){
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
  public void draw(int scrWidth, int scrHeight){
    
      // draw Rectangle in center.
      mLD.rectCenter(0.8f, 0xFF00FF00, scrWidth, scrHeight); // green color
      
      if ( tempImage != null ){
          // draw the image
          mLD.image(tempImage, 0, 0);
      }
      
      // draw buttons comments.
      // draw button shape
      float b1_x = 10;
      float b1_y = 100;
      float b2_x = 300;
      mLD.drawButtonShape("INSERT", b1_x , b1_y);
      mLD.drawButtonShape("ENTER", b2_x, b1_y);
      
      mFB.text("MENU TEXT", sketch().width / 2, sketch().height /2 );
  }

  /**
   * Just sets image to display. The image should not be a "live" graphics, 
   * but some safely stored static image. This class is going to save reference
   * to the image (no defensive copying here).
   * @param screenShotOriginalSize  is the client actually live PGraphics???
   */
    public void setImageToDisplay(PImage screenshot) {
           tempImage = screenshot;
    }
}