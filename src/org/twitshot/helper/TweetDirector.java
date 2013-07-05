package org.twitshot.helper;
// import some twitter library here.

import java.util.Map;
import org.twitshot.utils.FixedStringLog;
import org.twitshot.ex.TweetDirectorEx;
import org.twitshot.thread.TweetThread;
import org.twitshot.utils.IConfigXmlSpecification;
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
public class TweetDirector extends AbstractLibraryHelper
  implements IConfigXmlSpecification
{
  
   private Map<String, String> mLogOnCredentials;
   
   /**
    * This is log object, holding last status messages
    */
   private FixedStringLog mDirectorLog;
   private final static int C_LOG_SIZE = 10;
  
   /**
    * @var mTweetThread thread where all the stuff takes b
    */
   private TweetThread mTweetThread; 
  
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
   public TweetDirector(PApplet parent, Map<String,String> logOnCredentials) throws TweetDirectorEx
   {
      super(parent);
      // make quick verification of the credentials. 
      // at least they shoudln't be null, because I don't want to 
      // null pointer exception show up inside of thread and mislead me of
      // error cause
      if ( logOnCredentials == null){
         throw new TweetDirectorEx("Log on credentials cannot be null");
      }
      
      mLogOnCredentials = logOnCredentials;
      mDirectorLog = new FixedStringLog(C_LOG_SIZE);
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
    *  Tweets the message (actually submits to thread to tweet)
    */
   public void tweetTheMessage(String tweetMsg, PImage img){
        mTweetThread.submitMessage(tweetMsg, img);
        log("Submitted message [" + tweetMsg + "] and PImage");
        
   }
   
   /**
    * Logs to our fixed string log object
    */
   void log(String s){
       mDirectorLog.put(s);
   }
      
    /**
     *  Starts the thread which is going to send tweets asynchronousely.
     */
    public void start(){
        // i probably should pass some parametes to the thread???
        mTweetThread = new TweetThread(new TweetDirectorGate(this));
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
    
    
    /**
     * Returns FixedStringLog with last status messages.
     * (the i0 is the freshiest log message and the iN is the oldest message)
     * 
     * @returns  ?? can we return NULL?? what do we do in case there's no log messages?
     *           is it actually a good structure to return? because it appears that we only need the few latest items. This can be
     *           implemented with String[], but we also need two more variables "qty and start". this way we will be able to contain log messages in fixed size-array.
     */
    public FixedStringLog getLogObject(){
      // should there always be at least one message in the log??
      return mDirectorLog;
    }
    
}