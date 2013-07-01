package libraryexample;
import processing.core.*;
/**
 * Encapsulates library drawing effects 
 * Eg. drawing red background and also drawing grid on top of sketch.
 
 * REMEMBER: Drawing happens with "brush" settings of the sketch.
 
 * Some sketches may be dependent on the fact that fill/stroke params 
 * DO NOT change frame to frame. Thus we have to save and restore them before and after.
 * TODO: this saveing/storing should be implemented yet
 */
class LibraryDrawing extends AbstractLibraryHelper
{
  /**
   * @var C_LINE_PADDING - spacing between lines when .displayStringLog() is called
   */
  private final static int C_LINE_PADDING = 10;
  
  
  private FontBoss mFontBoss;
  
  
  LibraryDrawing(PApplet p, FontBoss fboss){
     super(p);
     mFontBoss = fboss;
  }
  
  
  // here we may want to implement some "saving" or "storing" of brush params (fill/stroke)
  void pushBrush(){
     // TODO: save brush somehow
  }
  
  void popBrush(){
     // TODO: pop brush settings somehow.
  }
  
  /**
   * Draws red rectangle with width equal to 80% of the display screen.
   */
  void drawRedRectangle80(){
     // we're going to draw red rect, sized 80% of the width/height of the screen.
     float scale = 0.8f;
     int newWidth = (int) (sketch().width * scale);
     int newHeight = (int) (sketch().height * scale);
     
     int x0 = (sketch().width - newWidth) / 2;
     int y0 = (sketch().height - newHeight) / 2;
     
     pushBrush();
     // fill(#FF0000) 
     //               becomes  
     parent().fill(0xFFFF0000);
     //          ^^^^ note that '0xFF' has replaced the '#' character
     //          also remember that there will be in total 4 pairs of two letters 0xFF FF 00 00
     parent().rect(x0, y0, newWidth, newHeight);
     popBrush();
  }
  
  
  
  void drawLineGrid(){
        // here we'll just draw grid of lines on top of what's already in the image.
    int lineY = 0;
    int lineStep = 20;
    while ( lineY < sketch().height ){
      parent().line(0, lineY, 
                   parent().width, lineY);
      lineY += lineStep;
                     
    }
  }  
  
  
  /**
   * Displays on screen several lines of text. 
   * Usually used to show log of operations.
   * @param 
   */
  void displayStringLog(FixedStringLog fixStrLog, float xx, float yy){
      float fontSz = mFontBoss.getFontSize();
      
      for(int i = 0 ;  i <  fixStrLog.size() ; i++){
        text(fixStrLog.get(i), xx, yy);
        yy += fontSz + C_LINE_PADDING;
      }
      
  }
  
  void displayFrameRate(){
     int bottomY = sketch().height - 180;
     int bottomX = 20;
     text("Framerate: " + (int) sketch().frameRate, bottomX, bottomY);
     
  }
  
  /**
   *  Fluentizer, to make it simplier.
   */
  private void text(String s, float xx, float yy){
     mFontBoss.text(s, xx, yy);
  }
  
}