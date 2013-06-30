/**
 * This class encapsulates and fluentizes operations with twitter.
 */
class TweetDirector extends AbstractLibraryHelper
  implements TweetConstants
{
   
   private PApplet mParent;
   
   
   /**
    * What's the contract? In case the credendials contain "invalid" information (like gibberish or 
    * unauthorized id's?
    */
   TweetDirector(PApplet parent, String[] logOnCredentials){
      mParent = parent;
      ??? how to process logon credenditals?
   }
  
   /**
    *  Tweets the message (actually submits to thread to tweet)
    */
   void tweetTheMessage(String tweetMsg, ??? Image img){
      ??
        submit to queue
        make log message "submitted log message"
   }
      
      
      
    /**
     *  Starts the thread which is going to send tweets asynchronousely.
     */
    void start(){
       ???
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
    
}