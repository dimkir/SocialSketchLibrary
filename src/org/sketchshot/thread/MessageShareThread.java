package org.sketchshot.thread;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import org.sketchshot.utils.ExponentialBackoff;
import org.sketchshot.utils.IConfigXmlSpecification;
import org.sketchshot.utils.ILogging;
import processing.core.*;
/**
 * This is the thread where TweetDirector work is going
 * to happen. (Oauth authenticatin and sending tweets if necesary)
 */
public class MessageShareThread extends Thread
implements IConfigXmlSpecification // for constants of the credentials fields.
{
 
  private ILogging mLogger;
  /**
   * Object which encapsulates attempts to send tweet.
   */
  private IBlockingMessageSharer blockingMessageSharer;
    
  private IThreadParameters mTweetDirectorGate;
  
//  /**
//   * @var mMessageRecordQueu this one holds queue of tweets to be sent. As it will be accessed from
//   * different threads, we only access it from 2 synchronized methods.
//   * .submitMessage(??)
//   * .popMessage()
//   * No "unsynched" access from anywhere 
//   * else.
//   */
//  private Queue<MessageRecord> mMessageRecordQueue = new LinkedList<MessageRecord>(); // is this good implemenation of
//  // the queue?
  
  /**
   * This is the queue which holds messages for the thread to feed on.
   * As messages implement IPostponable interface, they will be scheduled
   * in a proper way. 
   * If polling message returns null, it doesn't mean that there's no messges in the 
   * queue. It may mean that they're not scheduled yet for appearance.
   */
  private SmartQueue<MessageRecord> mSmartQueue = new SmartQueue<MessageRecord>();
  
  
  /**
   * This is where we want to store the result.
   * of the tweet. And this one will be polled every frame or so to check if everything is ok.
   */
  private Queue<ResultRecord> mResultRecordQueue = new LinkedList<ResultRecord>(); 
  
  
  /**
   * This is flag of whether we should continue iterating thread or finish it.
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
  
  /**
   * Passes TDG for if we want to communicate
   */
  public MessageShareThread(IThreadParameters treadParams){
     mTweetDirectorGate = treadParams;
     
     // Initializing logggers.
     mLogger = treadParams.getLogger();
     blockingMessageSharer =  treadParams.getBlockingMessageSharer(); 

     mExponentialBackoff = new ExponentialBackoff( 2.0f, 30, true);
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
           initAttempt();
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
  synchronized public ResultRecord  pollResultRecord(){
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
  private void initAttempt(){
       
       // this method is gated by exponential backoff
       if  ( !mExponentialBackoff.isReadyToRetry() ){
           // if not enough time has passed since last failure.
           return; 
       }
       
       if ( blockingMessageSharer.initBlocking(mTweetDirectorGate.getCredentials()) < 0 ){
           // failure
           mExponentialBackoff.registerFailure();
       }
       else{
           // success 
           mExponentialBackoff.registerSuccess();
           mIsInitialized = true;
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
  synchronized public void submitMessage(String msg, PImage img){
      MessageRecord mr = new MessageRecord(msg, img);
      mSmartQueue.push(mr);
      println("Added to messageQueue: " + mr.toString());
  }
  
  
  /**
   *  Pops message from queue.
   *  @returns NULL if message queue is empty
   */
  synchronized private MessageRecord popMessage(){
     return mSmartQueue.pop();
      // poll() returns null if
                                        // queue is empty
  }
  
  
  
  /**
   * Peform all the job we have to peform in this iteration.
   * What's our policy in terms of failures on send?
   * Honestly I have no chance to know what kind of errors can be there
   * thus i need to implement SOME (ANY) policy and then test it and see
   * how it fits.
   */
  private void iterate(){
      // poll tweet queue
      MessageRecord mr = popMessage(); // here we can check if messages are avaialble.
      // what happens here is that we pop the message,
      // and if it fails to send the message, the message
      // will go off the queue.
      // this may be caused by serious causes (authentication) or by temporary
      // (like problem with wifi-signal or internet access point is temporarily down).
     
      //TODO: this is just dummy stub. need to implement error checking and other things.
      
      if ( mr != null){
         // TODO: need to add return value to this method.
         int rez = blockingMessageSharer.shareMessageBlocking(mr);
         if ( rez < 0 ){
             // some kind of failure
             
             // we want to reintroduce the message.
             if ( rez == IBlockingMessageSharer.ERROR_RETRIABLE ){
                 // we need to insert item back into queue, but make sure it doesn't appear
                 // up until delay has passed.
                 logFailedShare(mr, "Retriable error has happened, we will retry");
                 
             }
             else if ( rez == IBlockingMessageSharer.ERROR_FATAL ){
                 String errorMsg = "Non retriable error has happened, we will report this as failed result";
                 logFailedShare(mr, errorMsg );
                 ResultRecord rr  = new ResultRecord(mr, errorMsg, -1);
                 pushResultRecord(rr);
             }
             else{
                 throw new IllegalStateException("Logic error, negative result of " + 
                                    "shareMessageBlocking() can only be ERROR_FATAL or ERROR_RETRIABLE. Now:" + rez );
             }
         }
         else{
             // success. What do we do with success??
             logSuccessfulShare(mr);
         }
      }
      else{
         println("MessageShareThread::iterate(): no messages in queue");
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
  synchronized public void setRunning(boolean running){
     mIsRunning = running;
  }
  
  
  synchronized boolean getRunning(){
     return mIsRunning;
  }
  
  /**
   * Fluentizer.
   */
  private void println(String s ){
     mLogger.println(s);
  }

  
  /**
   * Just fluentizer to get credentials from the 
   * credentials supplier which is TweetDirctorGate.
   * @return 
   */
  private Map<String, String> getTwitterCredentials() {
    return mTweetDirectorGate.getCredentials();
  }

  
  
  
  /**
   * Just logs to console (incuim).
   * Basically we want just to "log" successful share.
   * 
   */
  private void logSuccessfulShare(MessageRecord mr) {
     // get that  
      println("Thead: successfully shared message: " + mr.toString());
  }

  /**
   * Just logs information, which happens inside of iterate() when
   * there's a failed share.
   * @param mr
   * @param errorMsg 
   */
    private void logFailedShare(MessageRecord mr, String errorMsg) {
        println("Thread:iterate(): attempt to perform blockingshare of the " + mr.toString() + " has failed with error: " + errorMsg);
    }

}