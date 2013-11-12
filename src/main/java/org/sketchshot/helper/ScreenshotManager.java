package org.sketchshot.helper;
import processing.core.*;
/**
 * Just encapsulates saving of the current screen of the sketch. (So that we can tweet it later).
 * What's the behaviour? How to handle errors? Can we recover from errors? 
 * Do we want to crash all the sketch? Or we just want to give the sketch the priority?
 * 
 * Also how do we want to make the shrunk size of the image?  How does it know which is going to be the "shrunk" version of the image?
 */
public class ScreenshotManager extends AbstractLibraryHelper
{
  private PImage mScreenshot;  // this needs to be created at the first drawing loop. As when library is called the size of screen may not be known yet.
  private PImage mThumbnailImage; // ??? this is screenshot half size.
  private final int C_IMAGE_COLOR_MODE =  PApplet.RGB; // PApplet.ARGB; because screen is not image, it has no opacity! 
                                                       // (Sure, what are you to see if the screen is transparent? wall behind screen?)
                                                       // technically this should be RGB, as we don't have opacity parameter on the screen
                                                       // can change it later and see if it improves performance.
  
  /** This flag is set to true inside of saveFrame(). Upon the first run it is empty */
  private boolean mHasSavedFrame = false;
  
  /** This is memory where PreviousFrame will be kept */
  private PImage mPreviousFrame;
  
  /**
   * We just create object, but no memory yet is allocated for image.
   * because this is going to be "per-frame" operation, we want to allocate memory once and forever.
   * use allocateMemoryFor() method 
   */
  public ScreenshotManager(PApplet papp){
     super(papp);
  }

  /**
   * Allocates screen buffer to save future screenshots. If memory not allocated, 
   * there will be null-pointer exception. This allocation is as separate method (not in constructor), because
   * we only can be sure of the size of screen when first frame is drawn (not when sketch is setup)
   */
  private PImage allocateMemoryFor(int width, int height){
      PImage img = sketch().createImage(width, height, C_IMAGE_COLOR_MODE); 
      return img;
  }
  
  private PImage allocateMemoryFor(PGraphics pg){
    return allocateMemoryFor(pg.width, pg.height);
  }
  
  /**
   * Allocates memory if it is not yet allocated. This is a convenience method, can be called from each draw.
   * It only allocates memory if PImage is null.
   */
  public void allocateMemoryIfNotAllocatedFor(PGraphics pg){
    if ( mScreenshot == null ){
       mScreenshot = allocateMemoryFor(pg);
    }
    
    if ( mThumbnailImage == null){
        mThumbnailImage = allocateMemoryFor(pg.width/2, pg.height /2);
    }
    
    if ( mPreviousFrame == null){
        mPreviousFrame = allocateMemoryFor(pg);
    }
    
  }
  
  /**
   * Saves image from graphics surface (what sketch has drawn)
   */
  public void saveImageFrom(PImage imageOrGraphicsSurfaceWhichToSave){
     PImage img  = imageOrGraphicsSurfaceWhichToSave;
     assertDimensionsSame(mScreenshot, img);        // is it throwing anything?
     mScreenshot.copy(img, 0, 0, img.width ,img.height, 0, 0, img.width, img.height); // ? what happens if the dimensions are wrong?
     copyToThumbnail();
     println("Saved screen shot to screenshot manager");
     // now we kinda need to shrink it as well??
     // or do we shrink it at first request??
  }
  
  /**
   * Gets original size screenshot. 
   * There should be a method to retrive the "shrunk" size of the screenshot. (may we need it for displaying).
   */
  public PImage getOriginalSize(){
    return mScreenshot;
  }
  
  /**
   * Returns "thumbnail" version of the image.
   * @return 
   */
  public PImage getThumbnailSize(){
      return mThumbnailImage;
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

    /**
     * Makes copy of the image from #mScreenShot into #mScreenshotHalfSize
     * they should be already initializes. The dimensions for thumbnail
     * are already there (as it is initialized).
     * 
     */
    private void copyToThumbnail() {
        mThumbnailImage.copy(mScreenshot, 0, 0, mScreenshot.width, mScreenshot.height,
                                              0, 0, mThumbnailImage.width, mThumbnailImage.height);
    }

    public void saveFrame(PGraphics g) {
        mHasSavedFrame = true;
        int w = g.width;
        int h = g.height;
        
        mPreviousFrame.copy(g, 0, 0 , w, h, 0 ,0 , w, h);
    }

    /**
     * This one allows only one single read. After
     * reading it resets hasSavedFrame flag,
     * so that it is not updated until again saved.
     * @return 
     */
    public PImage getSavedFrame() {
        mHasSavedFrame = false;
        return mPreviousFrame;
    }

    public boolean hasSavedFrame() {
        return mHasSavedFrame;
    }
  
  
 
}