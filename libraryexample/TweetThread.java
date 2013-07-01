import java.util.Thread;
import java.util.Map;
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
   * Passes TDG for if we want to communicate
   */
  TweetThread(TweetDirectorGate twDirectorGate){
     mTweetDirectorGate = twDirectorGate;
  }
  
  
  /**
   * This is where the action is going to happen.
   */
  void run(){
    //TODO: what happens to the thread if initialization was blown???
    // and we didn't get? should we continue looping? 
    
    
    // TODO: this loop is crap: what's our behaviour in case initialization doesn't work???
    // by the way it may be blown because of different reasons... let's say 
    // communication error: this means that we may want to retry,
    // whereas in case there's "credentials invalid" error: that probalby means
    // that we are not gonna authenticate... :S
    while ( getRunning() ){
        sleep(100);
        if ( !isInitialized ){
           init();
        }
        if ( isInitializationSuccessful() {
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
  ResultRecord synchronized pollResultRecord(){
      return mResultRecordQueue.pop(); 
  }
  
  
  /**
   * Pushes result record to the result queu.
   */
  private void synchronized pushResultRecord(ResultRecord rr){
    mResultRecordQueue.push(rr);
  }
  
  
  
  
  
  /**
   * Here we should perform authentication.
   * 
   */
  private void init(){
       perform authentication.
       try to connect to the 
       
  }
  
  
  /**
   * Returns true if initialization was performed successfully.
   * @returns false 
   *             in case no initializatgion was performed yet
   *             or in case there was failure initializing. (that's a good question)
   */
  private boolean isInitialized(){
     
  }
  
  
  /**
   *  Submit to message queue. (Wraps message in MesssageRecord
   * who is the client??? Sketch is the client!
   * and puts it to the queue. 
   * 
   * It is synchronized because we 
   * 
   */
  void synchronized submitMessage(String msg, PImage img){
      MessageRecord mr = new MessageRecord(msg, img);
      mMessageRecordQueue.push(mr);
  }
  
  
  /**
   *  Pops message from queue.
   *  @returns NULL if message queue is empty
   */
  private MessageRecord synchronized popMessage(){
    if ( mMessageRecordQueue.isEmpty() ){
       return null;
    }
    return mMessageRecordQueue.pop();
  }
  
  
  
  /**
   * Peform all the job we have to peform in this iteration.
   */
  private void iterate(){
      // poll tweet queue
      MessageRecord mr = popMessage(); // here we can check if messages are avaialble.
      
      ?? = tweetMessageBlocking(mr); // performs this
      // ? what can it return?? again there may be different reasons for 
      // not being able to perform the thing... 
      // like connection error or smth.
      // add succees to the queue of successes
      if ( ??? 
      
      pushResultRecord(

      
  }
  
  
  /**
   * Performs blocking call to tweet message
   * @return ?? should return some success status? no?
   */
  void tweetMessageBlocking(MessageRecord mr){
     ???
    
  }
  
  
  /**
   * Just simple sleep method.
   */
  private void sleep(int milliseconds){
    try{
      Thread.sleep(milliseconds);
    }
    catch( OnInterruptedException intex){
       println("Interrupted exception has happened");
    }
  }
  
  /**
   * This is method which provides variable 
   * which is returning whether the thread should continue
   * running or not.
   */
  void synchronized boolean setRunning(boolean running){
     mIsRunning = running;
  }
  
  
  void synchronized boolean getRunning(){
     return mIsRunning;
  }
  
  
  /**
   *  POJO for keeping info about tweet.
   */
  private class MessageRecord
  {
    
    String msg;
    PImage image;
    
    /**
     * 
     */
    MessageRecord(String s, PImage img){
       msg = s;
       image = img;
    }
  }
  
}