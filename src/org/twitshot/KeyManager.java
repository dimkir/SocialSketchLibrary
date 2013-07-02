package org.twitshot;
import processing.core.*;
import processing.event.*;
/**
 * This class just encapsulates operations with the keys.
 * Fluentizes access to them from my library code.
 * 
 * 
 */
class KeyManager extends AbstractLibraryHelper
{
  
   private boolean mPressedMenuExitKey = false;
   private boolean mPressedMenuOpenKey = false;
   private boolean mPressedTweetKey = false;
//   private boolean mPressedKey = false;
   
   
   
   KeyManager(PApplet papp){
      super(papp);
   }
  
   boolean  pressedMenuExitKey()
   {
      return mPressedMenuExitKey;
   }
    
   boolean pressedMenuOpenKey(){
      return mPressedMenuOpenKey;
   }
  
   /**
    * Registers and tests the key event
    */
   void keyEvent(KeyEvent evt){
      printKeyEvent(evt);
      
      // now here I should set all the key flags.
      mPressedMenuExitKey = checkKey(KeyConstants.VK_ESCAPE);
      mPressedMenuOpenKey = checkKey(KeyConstants.VK_INSERT);
      mPressedTweetKey = checkKey(KeyConstants.VK_ENTER); // there's no return key definitinon. but it should be dec(36)  || checkKey(KeyConstants.VK_RETURN); // on Mac it may be RETURN key
                                                           // {@link http://web.archive.org/web/20100501161453/http://www.classicteck.com/rbarticles/mackeyboard.php}
   }
   
   
   /**
    * Checks if the key was pressed.
    * The trick is that it has to keep some 
    * tracking of which keys are pressed/released. 
    * Or what's happening in case the key wasp pressed/or released
    * outside of procesing window... we won't receive this evnt??? no?
    */
   boolean checkKey(int someKeyCode){
     //TODO: implement check key
     return false;
   }
   
   
   
   /**
    * Just prints key event details to the screen. (For debugging).
    */
   private void printKeyEvent(KeyEvent evt){
      println("KeyManager: evt.PRESS =  " + evt.PRESS);
      println("KeyManager: evt.RELEASE=  " + evt.RELEASE);
      println("KeyManager: evt.TYPE =  " + evt.TYPE);
      
   }
  
   boolean pressedTweetKey()
   {
     return mPressedTweetKey;
   }
}