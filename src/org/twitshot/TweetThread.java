package org.twitshot;
import java.io.InputStream;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.*;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
/**
 * This is the thread where TweetDirector work is going
 * to happen. (Oauth authenticatin and sending tweets if necesary)
 */
class TweetThread extends Thread
implements IConfigXmlSpecification // for constants of the credentials fields.
{
 
  private ITweetDirectorGate mTweetDirectorGate;
  
  /**
   * @var mMessageRecordQueu this one holds queue of tweets to be sent. As it will be accessed from
   * different threads, we only access it from 2 synchronized methods.
   * .submitMessage(??)
   * .popMessage()
   * No "unsynched" access from anywhere 
   * else.
   */
  private Queue<MessageRecord> mMessageRecordQueue = new LinkedList<MessageRecord>(); // is this good implemenation of
  // the queue?
  
  
  /**
   * @var mResultRecordQueue - this is where we want to store the result.
   * of the tweet. And this one will be polled every frame or so to check if everything is ok.
   */
  private Queue<ResultRecord> mResultRecordQueue = new LinkedList<ResultRecord>(); 
  
  
  /**
   * @var mIsRunning This is flag of whether we should continue iterating thread or finish it.
   * SYNCHRONIZING: this variable only accessed from synched methods.
   * .getRunning()
   * .setRunning(??)
   */
  // we start with running flag equal to true
  private boolean mIsRunning = true;
  
  /**
   * Has status of whether we already initialized or not.
   */
  private boolean mIsInitialized = false;
  
  
  private ExponentialBackoff mExponentialBackoff;
  private Twitter tw;
  
  /**
   * @var pimage2 this is object which will be used to covert PImage to
   * compressed input stream.
   */
  private PImage2 pimage2;
  
  /**
   * @var C_IMAGE_COMPRESSION_FORMAT_EXT when we compress PImage and
   * represent as InputStream this extension determins format 
   * into which we can compress. For more details see
   * @see PImage2.saveImageToStream and the compression method.
   */
  private static final String C_IMAGE_COMPRESSION_FORMAT_EXT = "png";
  
  /**
   * Passes TDG for if we want to communicate
   */
  TweetThread(ITweetDirectorGate twDirectorGate){
     mTweetDirectorGate = twDirectorGate;
     mExponentialBackoff = new ExponentialBackoff( 2.0f, 30, true);
     pimage2 = new PImage2(100,100); 
  }
  
  
  /**
   * This is where the action is going to happen.
   */
  @Override
  public void run(){
    //TODO: what happens to the thread if initialization was blown???
    // and we didn't get? should we continue looping? 
    
    
    // TODO: this loop is crap: what's our behaviour in case initialization doesn't work???
    // by the way it may be blown because of different reasons... let's say 
    // communication error: this means that we may want to retry,
    // whereas in case there's "credentials invalid" error: that probalby means
    // that we are not gonna authenticate... :S
    println("Started run() method");
    while ( getRunning() ){
        println("Running thread loop");
        sleep(100);
        if ( !isInitialized() ){
           initBlocking();
        }
        if ( isInitializationSuccessful() ) {
            iterate(); // perform what we have to perform
        }
        else{
            // we do nothing here. as initialization wasn't performed successfully.
        }
    }
  }
  
 
  /**
   *  In order to connect two threads, what we need to do is to poll from
   * other thread  (as we can't really send callback to other. Thus client
   * will be polling status on each frame. (or actually we can perform callback, 
   * but it will be based on the fact that we call each frame to here to 
   * poll status.
   * 
   * @returns NULL in case there's no results.
   */
  synchronized ResultRecord  pollResultRecord(){
      return mResultRecordQueue.poll();  // Retrieves and removes the head of this queue, or returns null if this queue is empty.
  }
  
  
  /**
   * Pushes result record to the result queu.
   */
  synchronized private void pushResultRecord(ResultRecord rr){
    mResultRecordQueue.add(rr);
  }
  
  
  
  
  
  /**
   * Here we should perform authentication.
   * This is called on worker thread (in the run() loop).
   * // TODO: should implement here some kind of 
   * exponential back off in case ther was an error initializing or smth.
   * 
   */
  private void initBlocking(){
       
       // this method is gated by exponential backoff
       if  ( !mExponentialBackoff.isReadyToRetry() ){
           // if not enough time has passed since last failure.
           return; 
       }
       // let's get initialization params;
       // perform authentication.
       //try to connect to the 
       println("TweetThread::init(): performing authentication");
       Map<String, String> cred = getTwitterCredentials();
       
       ConfigurationBuilder cb = new ConfigurationBuilder();
       cb.setOAuthConsumerKey(cred.get(C_CONSUMER_KEY));
       cb.setOAuthConsumerSecret(cred.get(C_CONSUMER_SECRET));
       cb.setOAuthAccessToken(cred.get(C_OAUTH_TOKEN));
       cb.setOAuthAccessTokenSecret(cred.get(C_OAUTH_SECRET));
       
       tw = new TwitterFactory(cb.build()).getInstance();
       try{
             println("Trying veryfy credentials");
             tw.verifyCredentials();
             
             mIsInitialized = true;
             mExponentialBackoff.registerSuccess();
             println("Registered successfully, userid: " + tw.getId());
             
       }
       catch (TwitterException twex){
            if ( twex.getStatusCode() == 401 ){
                println("Cannot authenticate, probably wrong credentials: " + twex.getMessage());
            }
            else{
                println("Some other error attempting authenticate twitter: " + twex.getErrorMessage());
            }
            mExponentialBackoff.registerFailure();
       }
  }
  
  
  /**
   * Returns true if initialization was performed successfully.
   * @returns false 
   *             in case no initializatgion was performed yet
   *             or in case there was failure initializing. (that's a good question)
   */
  private boolean isInitialized(){
     return mIsInitialized;
  }
  
  
  /**
   * returns whether initialization was successful
   */
  private boolean isInitializationSuccessful(){
     //TODO: here there's stub which just returns shite.
     return true;
  }
  
  /**
   *  Submit to message queue. (Wraps message in MesssageRecord
   * who is the client??? Sketch is the client!
   * and puts it to the queue. 
   * 
   * It is synchronized because we 
   * 
   */
  synchronized void submitMessage(String msg, PImage img){
      MessageRecord mr = new MessageRecord(msg, img);
      mMessageRecordQueue.add(mr);
      println("Added to messageQueue: " + mr.toString());
  }
  
  
  /**
   *  Pops message from queue.
   *  @returns NULL if message queue is empty
   */
  synchronized private MessageRecord popMessage(){
    if ( mMessageRecordQueue.isEmpty() ){
       return null;
    }
    return mMessageRecordQueue.poll();  // poll() returns null if
                                        // queue is empty
  }
  
  
  
  /**
   * Peform all the job we have to peform in this iteration.
   */
  private void iterate(){
      // poll tweet queue
      MessageRecord mr = popMessage(); // here we can check if messages are avaialble.
     
      //TODO: this is just dummy stub. need to implement error checking and other things.
      
      if ( mr != null){
         // TODO: need to add return value to this method.
         tweetMessageBlocking(mr);
      }
      else{
         println("TweetThread::iterate(): no messages in queue");
      }
//      ?? = tweetMessageBlocking(mr); // performs this
//      // ? what can it return?? again there may be different reasons for 
//      // not being able to perform the thing... 
//      // like connection error or smth.
//      // add succees to the queue of successes
//      if ( ??? 
//      
//      pushResultRecord(

      
  }
  
  
  /**
   * Performs blocking call to tweet message
   * @return ?? should return some success status? no?
   */
    private void tweetMessageBlocking(MessageRecord mr) {
        try {
            if (mr.img == null) {
                println("TweetThread::tweetMessageBlocking(): " + mr.msg + " image: null");
                Status rez = tw.updateStatus(mr.msg);
                println("Updated successfully, status: " +  rez.toString());
            } else {
                StatusUpdate supdate = new StatusUpdate(mr.msg);
//                supdate.setMedia(C_PROFILE_TAG, null);
                addImageToUpdate(supdate, mr.img);
                //throw new UnsupportedOperationException("Sending statuses with images is not supported currently");
                Status rez = tw.updateStatus(supdate);
                println("Updated successfully, status: " +  rez.toString());                
                //println("TweetThread::tweetMessageBlocking(): " + mr.msg + " image:" + mr.img.toString());
            }
        } catch (TwitterException ex) {
            Logger.getLogger(TweetThread.class.getName()).log(Level.SEVERE, null, ex);
            println("Twitter exception: " +  ex.getMessage());
        }
    }
  
  
  /**
   * Just simple sleep method.
   */
  private void sleep(int milliseconds){
    try{
      Thread.sleep(milliseconds);
    }
    catch( InterruptedException intex){
       println("Interrupted exception has happened: " + intex.getMessage());
    }
  }
  
  /**
   * This is method which provides variable 
   * which is returning whether the thread should continue
   * running or not.
   */
  synchronized void setRunning(boolean running){
     mIsRunning = running;
  }
  
  
  synchronized boolean getRunning(){
     return mIsRunning;
  }
  
  /**
   * Fluentizer.
   */
  private void println(String s ){
     mTweetDirectorGate.println(s);
  }

  
  /**
   * Just fluentizer to get credentials from the 
   * credentials supplier which is TweetDirctorGate.
   * @return 
   */
  private Map<String, String> getTwitterCredentials() {
    return mTweetDirectorGate.getCredentials();
  }

    private void addImageToUpdate(StatusUpdate supdate, PImage img) {
        InputStream is =  pimage2.getPImageAsInputStream(img, C_IMAGE_COMPRESSION_FORMAT_EXT);
        supdate.media("MyImage", is);
    }
  
  
  /**
   *  POJO for keeping info about tweet.
   */
  private class MessageRecord
  {
    
    String msg;
    PImage img;
    
    /**
     * 
     */
    MessageRecord(String s, PImage vImg){
       msg = s;
       img = vImg;
    }

    
    /**
     * Returns string with info about this mr.
     */
    @Override
    public String toString() {
        return "MessageRecord['" + msg + "', {" + img + "}]";
    }
    
    
    
  }
  
  /**
   * This is wrapper for the results being returned
   * from TweetThread.
   */
  class ResultRecord
  {
  }
  
}