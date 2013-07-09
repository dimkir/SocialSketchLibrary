package org.sketchshot.helper;

import java.util.Map;
import org.sketchshot.utils.FixedStringLog;
import org.sketchshot.ex.ShareDirectorEx;
import org.sketchshot.thread.MessageShareThread;
import org.sketchshot.utils.IConfigXmlSpecification;
import processing.core.*;
/**
 * Provides abstract object with API convenient for the library to share
 * sketch output. As we assume that most of those shares will be done
 * via remote services, this object (thread) should be started / shutdown.
 * Hence methods:
 * <pre>
 *  shutdown();
 *  start();
 * </pre>
 * 
 * In order to avoid "delay" whilst starting sketch (delay induced by 
 * attempting to perform OAuth authentication, the authentication is 
 * only happening when .start() method was called.
 */
public class ShareDirector extends AbstractLibraryHelper
  implements IConfigXmlSpecification
{
  
   private Map<String, String> mLogOnCredentials;
   
   /**
    * mTweetThread thread where all the stuff takes b
    */
   private MessageShareThread mTweetThread; 
  
   /**
    * What's the contract? In case the credentials contain "invalid" information (like gibberish or 
    * unauthorized id's?
    * @param parent reference to Sketch
    * @param logOnCredentials is Map which is providing twitter log on credentials. 
    * NOT NULL. 
    * 
    * @throws TweetDirectorEx some kind of exception saying that tweet director couldn't initialize.
    * 
    */
   public ShareDirector(PApplet parent, Map<String,String> logOnCredentials) throws ShareDirectorEx
   {
      super(parent);
      // make quick verification of the credentials. 
      // at least they shoudln't be null, because I don't want to 
      // null pointer exception show up inside of thread and mislead me of
      // error cause
      if ( logOnCredentials == null){
         throw new ShareDirectorEx("Log on credentials cannot be null");
      }
      mLogOnCredentials = logOnCredentials;
   }
   
   /**
    * Returns logon credentials supplied by the caller.
    * Because credentials are passed as string-map,
    * they should suit for any type of MessageSharer which
    * operates behind ShareDirector..
    * @return 
    */
   public Map<String, String> getLogOnCredentials(){
       return mLogOnCredentials;
   }
   
   /**
    *  Shares the message with selected element (actually submits to thread to tweet)
    */
   public void shareMessage(String tweetMsg, PImage img){
        mTweetThread.submitMessage(tweetMsg, img);
        println("Submitted message [" + tweetMsg + "] and PImage");
        
   }
      
    /**
     *  Starts the thread which is going to share messages asynchronously.
     */
    public void start(){
        // i probably should pass some parametes to the thread???
        mTweetThread = new MessageShareThread(new ShareDirectorParamsForThread(this, new BlockingTweetMsgSharer(getLogger())));
        mTweetThread.start();
    }
      
    /**
     * Is called by main library class to shutdown worker thread.
     */
    public void shutdown()
    {
       // ?? shutdown background thread.. but what do we do in case the tweeting process is still on?
      if  ( mTweetThread != null ){
         mTweetThread.setRunning(false); // thread will shutdown eventually.
      }
      else{
         println("TweetDirector::shutdown() :: TweetThread is null");
      }
    }
}