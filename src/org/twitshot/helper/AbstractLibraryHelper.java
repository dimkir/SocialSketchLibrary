package org.twitshot.helper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.twitshot.utils.ConsoleLogger;
import org.twitshot.utils.ILogging;
import processing.core.*;
/**
 * This is just abstract superclass which all library-helpers are inheriting.
 * Incuim it only obligates to implements toString();
 */
abstract class AbstractLibraryHelper
{
  private PApplet mParent; 
  private ILogging mLogger;
  
  AbstractLibraryHelper(PApplet parent){
      mParent = parent;
      mLogger = new ConsoleLogger(mParent);
  }
  
  protected PApplet sketch(){
     return mParent;
  }
  
  protected PApplet parent(){
     return mParent;
  }
  
  
  /**
   * This is test method, we try here reading a resource 
   * text file and output it to console. 
   * @param resourcePath 
   */
  protected void testReadingFileFromResources(String resourcePath){
      try {
          InputStream is = getClass().getResourceAsStream(resourcePath);
          InputStreamReader isr = new InputStreamReader(is);
          BufferedReader br = new BufferedReader(isr);
          
          String line;
          while ( ( line = br.readLine()) != null ){
              println(line);
          }
          
          br.close();
          is.close();
          
      } catch (IOException ex) {
          println("AbstractLibraryHelper::testReadingFileFromResources: " + ex.getMessage());
      }
  }
  
  
  /**
   * Fluentizer and evolutionary delegate.
   * Assumes this.parent is set in the current implementation.
   */
  protected void println(String s){
      mLogger.println(s);
  }  
  
  /**
   * Returns instance of logger to any subclass
   * thus allowing to write to the same stream.
   * @return 
   */
  public ILogging getLogger(){
       return mLogger;
  }
 
}