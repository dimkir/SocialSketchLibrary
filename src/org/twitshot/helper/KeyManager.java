package org.twitshot.helper;
import org.twitshot.utils.KeyConstants;
import processing.core.*;
import processing.event.*;
/**
 * This class just encapsulates operations with the keys.
 * Fluentizes access to them from my library code.
 * 
 * 
 */
public class KeyManager extends AbstractLibraryHelper
{
  
   private boolean mPressedMenuExitKey = false;
   private boolean mPressedMenuOpenKey = false;
   private boolean mPressedTweetKey = false;
//   private boolean mPressedKey = false;
   /**
    * @var khelper this is inner class instance which simply
    * encapsulates helper methods to analyze and display info about KeyEvent s
    */
   private KeyEventHelper khelper;
   
   
   public KeyManager(PApplet papp){
      super(papp);
      khelper = new KeyEventHelper();
   }

   /**
    * Just returns status of the keys. 
    * To be used after call to keyEvent() to figure out the status.
    * @return 
    */
    @Override
    public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("MENU_EXIT: " + mPressedMenuExitKey);
         sb.append("\n");
         sb.append("MENU_OPEN: " + mPressedMenuOpenKey);
         sb.append("\n");
         sb.append("TWEET_KEY: " + mPressedTweetKey);
         sb.append("\n");
         return sb.toString();
    }
  
   
   
   
   public boolean  pressedMenuExitKey()
   {
      return mPressedMenuExitKey;
   }
    
   /**
    * Returns status of the key. And RESETS the status to false.
    * @return 
    */
   public boolean pressedMenuOpenKey(){
      boolean ret = mPressedMenuOpenKey;
     // mPressedMenuOpenKey = false;
      return mPressedMenuOpenKey;
   }
  
   /**
    * Registers and tests the key event
    * what's the heck is this event.
    */
   public void keyEvent(KeyEvent evt){
      //printKeyEvent(evt);
       khelper.printKeyEventBar(evt);
      
      // now here I should set all the key flags.
      mPressedMenuExitKey = isReleasedEventForKey(evt, KeyConstants.VK_DELETE);
      mPressedMenuOpenKey = isReleasedEventForKey(evt, KeyConstants.VK_INSERT);
      mPressedTweetKey = isReleasedEventForKey(evt, KeyConstants.VK_ENTER); // there's no return key definitinon. but it should be dec(36)  || checkKey(KeyConstants.VK_RETURN); // on Mac it may be RETURN key
                                                           // {@link http://web.archive.org/web/20100501161453/http://www.classicteck.com/rbarticles/mackeyboard.php}
     // println(this.toString());
   }
   
   
   /**
    * Checks if the key was pressed.
    * The trick is that it has to keep some 
    * tracking of which keys are pressed/released. 
    * Or what's happening in case the key wasp pressed/or released
    * outside of processing window... we won't receive this evnt??? no?
    * @param evt event caught. (Dunno how it works with pressing/releasing events).
    */
   boolean isReleasedEventForKey(KeyEvent evt, int matchingKeyCode){
     //TODO: implement check key. Dunno if this is correct implementation or not.
   //    println("Checking if keycode(" + evt.getKeyCode() + "/ " + evt.getKey() + ") matches keycode(" + matchingKeyCode + ")");
       return ( evt.getAction() == evt.RELEASE && evt.getKeyCode() == matchingKeyCode );
   }
   
   public boolean pressedTweetKey()
   {
     return mPressedTweetKey;
   }
   
   
 /**
 * Just collection of utility methods to simplify printing of 
 * key event information to screen.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
class KeyEventHelper {

  /**
    * Just prints key event details to the screen. (For debugging).
    */
   private void printKeyEvent(KeyEvent evt){
      println("=========== KeyEvent =============");
      println("========" + sketch().millis() + "=======");
      println("KeyManager: evt.getAction() = " + actionString(evt));
      println("KeyManager: evt.PRESS =  " + evt.PRESS);
      println("KeyManager: evt.RELEASE=  " + evt.RELEASE);
      println("KeyManager: evt.TYPE =  " + evt.TYPE);
      println("KeyManager: evt.getKey() = "  + evt.getKey());
      println("KeyManager: evt.getKeyCode() = "  + evt.getKeyCode());
      println("");
   }

   /**
    * This method prints to console string corresponding to
    * each event. This is meant for visualizing events.
    * Sample output can be:
    * ppp
    * rrrrrrrrrrr
    * tttttttttttttttt
    * ppp
    * tttttttttttttttt
    * ppp
    * tttttttttttttttt
    * @param evt 
    */
   private void printKeyEventBar(KeyEvent evt){
       int len = evt.getAction() + 1;
       len *= 3;
       String bar = actionCharChain(evt, len);
       println(bar + "--" +  evt.getKey());
   }
  

   /**
    * Helper method which returns string corresponding to the action of KeyEvent:
    * PRESS
    * RELEASE
    * TYPE
    * @param evt
    * @return "PRESS" "RELEASE" or "TYPE"
    */
   

    private String actionString(KeyEvent evt) {
         switch( evt.getAction() ){
             case KeyEvent.PRESS: 
                  // PRESS
                  return "PRESS";
             case KeyEvent.RELEASE:
                  // ???
                  return "RELEASE";
             case KeyEvent.TYPE:
                 // ???
                 return "TYPE";
             default: 
                 println("Unknown action in KeyManager::actionString().");
                 return "ACTION_" + evt.getAction();
         }
    }
    
    private char actionChar(KeyEvent evt){
         switch( evt.getAction() ){
             case KeyEvent.PRESS: 
                  // PRESS
                  return 'p';
             case KeyEvent.RELEASE:
                  // ???
                  return 'r';
             case KeyEvent.TYPE:
                 // ???
                 return 't';
             default: 
                 println("Unknown action in KeyManager::actionChar(). Returning '*'");
                 return '*';
         }
    }
    
    /**
     * Helper. 
     * @return "pppppppppppppppppppppppp"
     *         "rrrrrrrrrr"
     *         "ttttttttttttttttttt"
     */
    private  String actionCharChain(KeyEvent evt, int chainLength){
         StringBuilder sb = new StringBuilder();
         char actionCh = actionChar(evt);
         for(int i = 0 ; i < chainLength ; i++){
             sb.append(actionCh);
         }
         return sb.toString();
    }
        
    
}
   
   
 
}