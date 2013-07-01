package libraryexample;
import processing.core.*;
/**
 *  Just fancy name class which encapsulates operations with fonts.
 * 
 * ? how do we distribute fonts with the library? 
 * or actually how do we access actual font with the library? 
 * should I incorportate it as a resource inside of the JAR?
 */
class FontBoss extends AbstractLibraryHelper
{
  private static final String C_DEFAULT_FONT_NAME = "Arial"; // incuim createFont() param
  private static final int    C_DEFAULT_FONT_SIZE = 46;      // incuim createFont() param  
  private PFont mFont; // the library inits PFont to use. So far default is createFont() from "Arial" but we need to supply our own font.  
  
  
  
  FontBoss(PApplet papp){
     super(papp);
     initFont();
  }
  
  /**
   *  Delegate to initialize fonts. 
   * Incuim uses "createFont()" for Arial
   */
  private void initFont(){
     mFont = parent().createFont(C_DEFAULT_FONT_NAME, C_DEFAULT_FONT_SIZE); 
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