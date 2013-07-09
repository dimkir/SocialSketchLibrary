package org.sketchshot.thread;

/**
 * This is wrapper for the results being returned
 * from TweetThread.
 * When TweetThread accumulates results, it wraps them in this class.
 */
public class ResultRecord {
    
     private int status;
     
     /**
      * Whos' creating result? BlockingMessageSharer or the thread?
      */

    ResultRecord(MessageRecord mr, String errorMsg, int status) {
        this.status = status;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
     public int getStatus(){
         return status;
     }
    
}
