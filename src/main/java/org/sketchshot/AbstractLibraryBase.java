package org.sketchshot;
import org.sketchshot.utils.ConsoleLogger;
import org.sketchshot.utils.ILogging;
import processing.core.*;

/**
 * Attempt to encapsulate some of the base-functionality of a library into a class.
 * Like? println and some other rubbish.
 */
abstract class AbstractLibraryBase
{
   protected final PApplet mParent;
   private ILogging logger;
   
   protected AbstractLibraryBase(PApplet papp){
     if ( papp == null ){
       throw new IllegalArgumentException("When initializing library Parent cannot be null");
     }
     
      mParent = papp;
      logger = new ConsoleLogger(papp);
   }
   
  
  /**
   * Fluentizer and evolutionary delegate.
   * Assumes this.parent is set in the current implementation.
   */
  protected void println(String s){
     logger.println(s);
  }   
  
  protected long millis(){
       return System.currentTimeMillis();
  }
}