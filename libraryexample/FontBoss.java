/**
 *  Just fancy name class which encapsulates operations with fonts.
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
     mFont = parent.createFont(C_DEFAULT_FONT_NAME, C_DEFAULT_FONT_SIZE); 
  }  
    
}