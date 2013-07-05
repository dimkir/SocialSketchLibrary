package org.twitshot;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import processing.core.PImage;

/**
 * This test file is supposed to test the 
 * tweeting thread.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class TweetThreadTest extends TestCase {
    
    /**
     * @var mTwDirectorGate this is point through which Thread is communicating 
     * with the outside.
     */
    ITweetDirectorGate mTwDirectorGate;
    
    public TweetThreadTest(String testName) {
        super(testName);
        mTwDirectorGate = new FakeTweetDirectorGate();
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
     * Test initialization of the thread.
     */
    public void testInitialize(){
        
        TweetThread tt = new TweetThread(mTwDirectorGate);
        
        tt.start();
        
        tt.submitMessage("This is my message " + millis(), null);
        
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TweetThreadTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    long millis(){
         return System.currentTimeMillis();
    }
}
