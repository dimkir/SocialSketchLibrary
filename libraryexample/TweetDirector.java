// import some twitter library here.

/**
 * This class encapsulates and fluentizes operations with twitter.
 * It uses background thread, this is why it should be initialized and then
 * .start() should be called to start the thread.
 * In order to avoid "delay" whilst starting sketch (delay induced by 
 * attempting to perform OAuth authentication, the authentication is 
 * only happening when .start() method was called.
 */
class TweetDirector extends AbstractLibraryHelper
  implements IConfigXmlSpecification
{
  
   /**
    * @var mTweetThread thread where all the stuff takes b
    */
   private TweetThread mTweetThread; 
  
   /**
    * What's the contract? In case the credendials contain "invalid" information (like gibberish or 
    * unauthorized id's?
    * @param parent reference to Sketch
    * @param logOnCredentials is Map which is providing twitter log on credentials. 
    * NOT NULL. 
    * 
    * @throws ??? some kind of exception saying that tweet director couldn't initialize.
    * 
    */
   TweetDirector(PApplet parent, Map<String,String> logOnCredentials){
      super(parent);
      // make quick verification of the credentials. 
      // at least they shoudln't be null, because I don't want to 
      // null pointer exception show up inside of thread and mislead me of
      // error cause
      if ( logOnCredentials == null){
         throw new TweetDirectorEx("Log on credentials cannot be null");
      }
   }
   
   
   /**
    * @param listener listener. Or null in case you want to reset listener.
    */
   void setOnAuthenticationFinishedListener(IOnAuthenticationFinished listener){
     
      ??? 
   }
   
   
   /**
    *  Tweets the message (actually submits to thread to tweet)
    */
   void tweetTheMessage(String tweetMsg, ??? Image img){
        mTweetThread.submitMessage(tweetMsg, Image img);
        log("Submitted message [" + tweetMsg + "] and PImage");
        
   }
      
      
      
    /**
     *  Starts the thread which is going to send tweets asynchronousely.
     */
    void start(){
        // i probably should pass some parametes to the thread???
        mTweetThread = new TweetThread(new TweetDirectorGate());
        mTweetThread.start();
    }
      
    /**
     * Is called when sketch is to shut down. Basically stops the upload thread.
     */
    void shutdown()
    {
       ?? shutdown background thread.. but what do we do in case the tweeting process is still on?
    }
    
    
    /**
     * Returns stringar with last status messages (the i0 is the freshiest log message and the iN is the oldest message)
     * @returns  ?? can we return NULL?? what do we do in case there's no log messages?
     *           is it actually a good structure to return? because it appears that we only need the few latest items. This can be
     *           implemented with String[], but we also need two more variables "qty and start". this way we will be able to contain log messages in fixed size-array.
     */
    String[] getLogStrings(){
      // should there always be at least one message in the log??
       ???
    }
    
    
    /**
     * This innert class provides API to access info from within the TweetDirector
     * the purpose of communicating with TweetThread
     */
    class TweetDirectorGate
    {
       
      TweetDirectorGate(){
      }
       
      // here go the method to 
      // do the things:
      // ???
      
    }
    
}