package libraryexample;
import processing.core.*;
/**
 * This is just abstract superclass which all library-helpers are inheriting.
 * Incuim it only obligates to implements toString();
 */
abstract class AbstractLibraryHelper extends AbstractLibraryBase // incuim the base only saves .parent and defines println()
{
  //private PApplet mParent; 
   
  
  AbstractLibraryHelper(PApplet parent){
    super(parent);
    
     //mParent = parent;
  }
  
  protected PApplet sketch(){
     return mParent;
  }
  
  protected PApplet parent(){
     return mParent;
  }
  
 
}