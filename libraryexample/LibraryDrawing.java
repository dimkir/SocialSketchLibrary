/**
 * Encapsulates library drawing effects (like drawing red background and also drawing grid on top of sketch.
 * Remember that this drawing happens with "brush" settings of the sketch. 
 * Some sketches may be dependent on the fact that fill/stroke params DO NOT change frame to frame. Thus we have to save 
 * and restore them before and after.
 */
class LibraryDrawing extends AbstractLibraryHelper
{
  private final PApplet parent; // through this one all drawing happens.
  
  LibraryDrawing(PApplet p){
     parent = p;
  }
  
  
  // here we may want to implement some "saving" or "storing" of brush params (fill/stroke)
  ?pushBrushParameters()
  
  /**
   * So that you can use drawing methods, init it with instnace of PApplet which carries drawing interface (like line() and fill());
  LibraryDrawing(PApplet p){
     parent = p;
  }
  
  /**
   * Draws red rectangle with width equal to 80% of the display screen.
   */
  void drawRedRectangle80(){
     // we're going to draw red rect, sized 80% of the width/height of the screen.
     float scale = 0.8f;
     int newWidth = (int) (parent.width * scale);
     int newHeight = (int) (parent.height * scale);
     
     int x0 = (parent.width - newWidth) / 2;
     int y0 = (parent.height - newHeight) / 2;
     
     // fill(#FF0000) 
     //               becomes  
     parent.fill(0xFFFF0000);
     //          ^^^^ note that '0xFF' has replaced the '#' character
     //          also remember that there will be in total 4 pairs of two letters 0xFF FF 00 00
     parent.rect(x0, y0, newWidth, newHeight);
  }
  
  
  
  void drawLineGrid(){
        // here we'll just draw grid of lines on top of what's already in the image.
    int lineY = 0;
    int lineStep = 20;
    while ( lineY < parent.height ){
      parent.line(0, lineY, 
                   parent.width, lineY);
      lineY += lineStep;
                     
    }
  }  
  
  
  /**
   *  Displays on screen several lines of text. 
   * Usually used to show log of operations.
   */
  void displayStringLog(String[] logLines){
     ???
  }
  
  void displayFrameRate(){
     int bottomY = parent.height - 180;
     int bottomX = 20;
     // textFont(font);
     // text("Framerate: " + frameRate, bottomX, bottomY);
     
     parent.textFont(font);
     parent.fill(255);
     parent.text("Framerate: " + (int) parent.frameRate, bottomX, bottomY);
     
  }
  
}