package org.twitshot;

import junit.framework.TestCase;
import processing.core.PImage;

/**
 * This test file is supposed to test the 
 * tweeting thread.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class TweetThreadTest extends TestCase {
    
    public TweetThreadTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of run method, of class TweetThread.
     */
    public void testRun() {
        System.out.println("run");
        TweetThread instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pollResultRecord method, of class TweetThread.
     */
    public void testPollResultRecord() {
        System.out.println("pollResultRecord");
        TweetThread instance = null;
        TweetThread.ResultRecord expResult = null;
        TweetThread.ResultRecord result = instance.pollResultRecord();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of submitMessage method, of class TweetThread.
     */
    public void testSubmitMessage() {
        System.out.println("submitMessage");
        String msg = "";
        PImage img = null;
        TweetThread instance = null;
        instance.submitMessage(msg, img);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tweetMessageBlocking method, of class TweetThread.
     */
    public void testTweetMessageBlocking() {
        System.out.println("tweetMessageBlocking");
        TweetThread.MessageRecord mr = null;
        TweetThread instance = null;
        instance.tweetMessageBlocking(mr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRunning method, of class TweetThread.
     */
    public void testSetRunning() {
        System.out.println("setRunning");
        boolean running = false;
        TweetThread instance = null;
        instance.setRunning(running);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRunning method, of class TweetThread.
     */
    public void testGetRunning() {
        System.out.println("getRunning");
        TweetThread instance = null;
        boolean expResult = false;
        boolean result = instance.getRunning();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
