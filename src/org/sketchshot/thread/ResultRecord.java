package org.sketchshot.thread;

/**
 * This is wrapper for the results being returned
 * from TweetThread.
 * When TweetThread accumulates results, it wraps them in this class.
 */
public class ResultRecord {
    
     private final int mStatus;
     private final MessageRecord mMr;
     private final String mErrorMsg;
     
     /**
      * Whos' creating result? BlockingMessageSharer or the thread?
      */

    ResultRecord(MessageRecord mr, String errorMsg, int status) {
        this.mStatus = status;
        mMr = mr;
        mErrorMsg = errorMsg;
        }
     
    /**
     * Returns status.
     * @return  0 on success. 
     *          NEGATIVE on error
     */
     public int getStatus(){
         return mStatus;
     }

     /**
      * Returns summary of the result. We still have to come up with the format.
      * @return 
      */
    public String getResultSummaryMessage() {
        if ( mStatus >= 0 ){
            return "Success: sent message [" + mMr.msg + "]";
        }
        else{ // mStatus < 0
            return "Error: " + mErrorMsg;
        }
    }
    
}
