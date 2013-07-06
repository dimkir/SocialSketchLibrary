package org.sketchshot.thread;

import processing.core.PImage;

/**
 *  POJO for keeping info about tweet.
 * When TweetThread is queing request to
 * tweet, it wraps them in this MessageRecord.
 */
public class MessageRecord {
    public String msg;
    public PImage img;

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
    
}
