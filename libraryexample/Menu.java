package libraryexample;
import processing.core.*;
/**
 *  This class is to abstract displaying of the on screen menu.
 */
class Menu extends AbstractLibraryHelper
{
  
  private LibraryDrawing mLD;
  
  
  Menu(PApplet parent, LibraryDrawing ld){
     super(parent);
     mLD = ld;
  }
  
  
  /**
   * Returns flag.
   */
  boolean isDisplayedFlag(){
    //TODO: what does this do?
    return false;
  }
  
  /**
   * Draws to default rednerer, stated in the beginning.
   */
  void draw(){
    // TODO: implement some drawing of the menu.
  }
}