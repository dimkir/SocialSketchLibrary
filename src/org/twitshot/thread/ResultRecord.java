/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.twitshot.thread;

/**
 * This is wrapper for the results being returned
 * from TweetThread.
 */
class ResultRecord {
    private final TweetThread outerX;

    ResultRecord(final TweetThread outerX) {
        this.outerX = outerX;
    }
    
}
