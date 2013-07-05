package org.twitshot.helper;
import java.io.IOException;
import java.io.InputStream;
import org.twitshot.ex.FontLoadEx;
import processing.core.*;
/**
 *  Just fancy name class which encapsulates operations with fonts.
 * 
 * Fonts will be included into JAR. So far works only with vlw-fonts.
 */
public class FontBoss extends AbstractLibraryHelper
{
//  private static final String C_FONT_PATH_WITHIN_JAR = "/fonts/commando.ttf";
//  private static final String C_FONT_PATH_WITHIN_JAR = "/fonts/28 Days Later.ttf";
  // new PFont(InputStream) only works with VLW fonts.
  private static final String C_FONT_PATH_WITHIN_JAR = "/res/fonts/Commando-32.vlw";
  private static final String C_DEFAULT_FONT_NAME = "Arial"; // incuim createFont() param
  private static final int    C_DEFAULT_FONT_SIZE = 32;      // incuim createFont() param  
  private PFont mFont; // the library inits PFont to use. So far default is createFont() from "Arial" but we need to supply our own font.  
  
  
  /**
   * Initializes our font-related API and loads font from the library-jar.
   * @throws FontLoadEx in case there was problem loading font.
   * During normal usage of the library, as the font is packaged inside of jar, 
   * this exception shouldn't be thrown.
   */
  public FontBoss(PApplet papp) throws FontLoadEx{
     super(papp);
     testReadingFileFromResources("/res/strings/angelina.xml");
     initFont();
  }
  
  /**
   *  Delegate to initialize fonts. 
   * Incuim uses "createFont()" for Arial
   */
  private void initFont() throws FontLoadEx 
  {
  //   mFont = parent().createFont(C_DEFAULT_FONT_NAME, C_DEFAULT_FONT_SIZE); 
     try {
        InputStream inputStream = this.getClass().getResourceAsStream(C_FONT_PATH_WITHIN_JAR);
        if ( inputStream == null ){
            println("InputStream: is null");
        }
        mFont = new PFont(inputStream);
     }
     catch( IOException ioex){
         throw new FontLoadEx(ioex);
     }
  }  
  
  
  float getFontSize(){
     return C_DEFAULT_FONT_SIZE;
  }
  
  
  
  /**
   * this is convenience method to output the text (and be sure that we have size and stuff set
   * TODO: this interferes with the state of the sketch (changes it's font etc and 
   *  it DOESN'T change font color/outline. Thus wa may not render it correctly.
   */
  void text(String s, float xx , float yy){
       // there's a problem,
       // we can't really save the parameters of the fonts.
       // thus we interfere with the sketch state and results may
       // not be as we want :)
       sketch().textFont(mFont);
       sketch().textSize(C_DEFAULT_FONT_SIZE);
       //?? and what about color?
       sketch().text(s, xx ,yy);
  }
  
    
}