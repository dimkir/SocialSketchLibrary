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
    public final String msg;
    public final PImage img;
    
    /** 
     * Default action time is 0 (means immediate execution),
     * whereas later time can be set and message can be inserted again
     * into the queue.
     */
    private final long mActionTime;

    /**
     * Added public visibility, because (who creates this class? usually only the thread)
     * but public constructor is needed for tests.
     */
    public MessageRecord(String s, PImage vImg) {
        mActionTime = 0;
        msg = s;
        img = vImg;
    }

    public MessageRecord(MessageRecord mr, long actionTime){
        msg = mr.msg;
        img = mr.img;
        mActionTime = actionTime;
    }
    
    
    /**
     * As we made this data structure immutable, 
     * i have made this method to create a copy with new action time.
     * @param newActionTime
     * @return 
     */
    MessageRecord getCopy(long newActionTime){
        return new MessageRecord(this, newActionTime);
    }
    /**
     * Returns string with info about this mr.
     */
    @Override
    public String toString() {
        return "MessageRecord['" + msg + "', {" + img + "}]";
    }

//  THIS STRUCTURE SHOULD BE IMMUTABLE    
//    /**
//     * Sets action time for this message.
//     */
//    void setActionTime(long actionTime){
//         mActionTime = actionTime;
//    }

    @Override
    public long actionTime() {
        return mActionTime;
    }
   
}
