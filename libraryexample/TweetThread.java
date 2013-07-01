package libraryexample;
import java.lang.Thread;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import libraryexample.TweetDirector.TweetDirectorGate;
import processing.core.*;
/**
 * This is the thread where TweetDirector work is going
 * to happen. (Oauth authenticatin and sending tweets if necesary)
 */
class TweetThread extends Thread
{
 
  private TweetDirectorGate mTweetDirectorGate;
  
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
  private boolean mIsRunning = false;
  
  /**
   * Has status of whether we already initialized or not.
   */
  private boolean mIsInitialized = false;
  
  /**
   * Passes TDG for if we want to communicate
   */
  TweetThread(TweetDirectorGate twDirectorGate){
     mTweetDirectorGate = twDirectorGate;
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
    while ( getRunning() ){
        sleep(100);
        if ( !isInitialized() ){
           init();
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
   * 
   */
  private void init(){
       // let's get initialization params;
       // perform authentication.
       //try to connect to the 
       println("TweetThread::init(): performing authentication");
       mIsInitialized = true;
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
  void tweetMessageBlocking(MessageRecord mr){
     println("TweetThread::tweetMessageBlocking(): " + mr.msg + " image:" + mr.img.toString());
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
  }
  
  /**
   * This is wrapper for the results being returned
   * from TweetThread.
   */
  class ResultRecord
  {
  }
  
}