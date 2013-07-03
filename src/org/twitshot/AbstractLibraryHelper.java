package org.twitshot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
 
}