package org.twitshot;
import processing.core.*;
/**
 * Just encapsulates saving of the current screen of the sketch. (So that we can tweet it later).
 * What's the behaviour? How to handle errors? Can we recover from errors? 
 * Do we want to crash all the sketch? Or we just want to give the sketch the priority?
 * 
 * Also how do we want to make the shrunk size of the image?  How does it know which is going to be the "shrunk" version of the image?
 */
class ScreenshotManager extends AbstractLibraryHelper
{
  private PImage mScreenshot;  // this needs to be created at the first drawing loop. As when library is called the size of screen may not be known yet.
  private final int C_IMAGE_COLOR_MODE =  PApplet.RGB; // PApplet.ARGB; because screen is not image, it has no opacity! (Sure, what are you to see if the screen is transparent? wall behind screen?)
  
  /**
   * We just create object, but no memory yet is allocated for image.
   * because this is going to be "per-frame" operation, we want to allocate memory once and forever.
   * use allocateMemoryFor() method 
   */
  ScreenshotManager(PApplet papp){
     super(papp);
  }

  /**
   * Allocates screen buffer to save future screenshots. If memory not allocated, 
   * there will be null-pointer exception. This allocation is as separate method (not in constructor), because
   * we only can be sure of the size of screen when first frame is drawn (not when sketch is setup)
   */
  private void allocateMemoryFor(int width, int height){
      mScreenshot = mParent.createImage(width, height, C_IMAGE_COLOR_MODE); 
  }
  
  void allocateMemoryFor(PGraphics pg){
    allocateMemoryFor(pg.width, pg.height);
  }
  
  /**
   * Allocates memory if it is not yet allocated. This is a convenience method, can be called from each draw.
   * It only allocates memory if PImage is null.
   */
  void allocateMemoryIfNotAllocatedFor(PGraphics pg){
    if ( mScreenshot == null ){
       allocateMemoryFor(pg);
    }
  }
  

  /**
   * Saves image from graphics surface (what sketch has drawn)
   */
  void saveImageFrom(PImage imageOrGraphicsSurfaceWhichToSave){
     PImage img  = imageOrGraphicsSurfaceWhichToSave;
     assertDimensionsSame(mScreenshot, img);
     mScreenshot.copy(img, 0, 0, img.width ,img.height, 0, 0, img.width, img.height); // ? what happens if the dimensions are wrong?
     // now we kinda need to shrink it as well??
     // or do we shrink it at first request??
  }
  
  /**
   * Gets original size screenshot. 
   * There should be a method to retrive the "shrunk" size of the screenshot. (may we need it for displaying).
   */
  PImage getScreenShotOriginalSize(){
    return mScreenshot;
  }
  
  /**
   * Just debugging method. To make sure the dimensions are correct.
   */
  private void assertDimensionsSame(PImage shot, PImage curscreen){
    String errorMsg = "";
    boolean hasError = false;
    if ( shot.width != curscreen.width  ){
        errorMsg += "Error, width(" + shot.width + ") of shot is not matching width of curscreen(" + curscreen.width + ")\n";
        hasError = true;
    }
    
    if ( shot.height != curscreen.height ){
        errorMsg += "Error, height(" + shot.height + ") of shot is not matching height of the curscreen(" + curscreen.height + ")\n";
         hasError = true;
    }
    
    if ( hasError ){
       throw new RuntimeException(errorMsg);
    }
    
    // if were're here - all ok
  }
  
  
 
}