/**
 * This class just encapsulates operations with the keys. Fluentizes access to them from my library code.
 */
class KeyManager extends AbstractLibraryHelper
{
  
   private boolean mPressedMenuExitKey = false;
   private boolean mPressedMenuOpenKey = false;
   private boolean mPressedTweetKey = false;
//   private boolean mPressedKey = false;
   
   
//   KeyManager(PApplet papp){
//      super(papp);
//   }
  
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
      println(evt.toString());
      printKeyEvent();
      
      // now here I should set all the key flags.
      mPressedMenuExit = checkKey(VK.ESCAPE);
      mPressedMenuOpenKey = checkKey(VK.INSERT);
      mPressedTweetKey = checkKey(VK.ENTER) || checkKey(VK.RETURN); // on Mac it may be RETURN key
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