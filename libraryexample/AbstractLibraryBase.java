/**
 * Attempt to encapsulate some of the base-functionality of a library into a class.
 * Like? println and some other rubbish.
 */
abstract class AbstractLibraryBase
{
   protected final PApplet parent;
   
   protected AbstractLibraryBase(PApplet papp){
      parent = papp;
   }
   
  
  /**
   * Fluentizer and evolutionary delegate.
   * Assumes this.parent is set in the current implementation.
   */
  protected void println(String s){
     parent.println(s);
  }   
}