package org.sketchshot.thread;

import processing.core.PImage;

/**
 *  POJO for keeping info about tweet.
 * When TweetThread is queing request to
 * tweet, it wraps them in this MessageRecord.
 */
public class MessageRecord
 implements IPostponable
{
    public String msg;
    public PImage img;
    
    /** 
     * Default action time is 0 (means immediate execution),
     * whereas later time can be set and message can be inserted again
     * into the queue.
     */
    private long mActionTime = 0;

    /**
     *
     */
    MessageRecord(String s, PImage vImg) {
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
    
    /**
     * Sets action time for this message.
     */
    void setActionTime(long actionTime){
         mActionTime = actionTime;
    }

    @Override
    public long actionTime() {
        return mActionTime;
    }
    
}
