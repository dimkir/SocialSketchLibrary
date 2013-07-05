package org.twitshot;

import org.twitshot.thread.TweetThread;
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
    FakeTweetDirectorGate mStubTwDirectorGate;
    
    public TweetThreadTest(String testName) {
        super(testName);
        mStubTwDirectorGate = new FakeTweetDirectorGate();
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
        
        mStubTwDirectorGate.setCredentials(FakeTweetDirectorGate.VALID);
        TweetThread tt = new TweetThread(mStubTwDirectorGate);
        
        tt.start();
        
        tt.submitMessage("This is my message " + millis(), null);
        
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TweetThreadTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
   public void testNullCredentials(){
            mStubTwDirectorGate.setCredentials(FakeTweetDirectorGate.NULLL);       
            
            
            ???
   }
    

    /**
     * Test what happens if invalid credentials are supplied.
     */
    public void testInvalidCredentials(){
        mStubTwDirectorGate.setCredentials(FakeTweetDirectorGate.INVALID);  
    }   
   
   /**
    * ?
    */
   public void testNoInternetConnection(){
       
   }
    
    

    
    
    /**
     * 
     */
    public void testSubmissionOfTweetFailed(){
        
    }
    
    
    long millis(){
         return System.currentTimeMillis();
    }
}
