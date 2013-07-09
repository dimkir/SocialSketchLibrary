package org.sketchshot.thread;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 * This test file is supposed to test the 
 * tweeting thread.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class TestMessageShareThread extends TestCase {
    
    /**
     * This is point through which Thread is receiving parameters
     * from the outside.
     */
    
    public TestMessageShareThread(String testName) {
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
     * Test initialization of the thread.
     */
    public void testInitialize()    {
        
        final IThreadParameters threadParams = new FakeIThreadParams(FakeIThreadParams.CR_VALID);
        Thread testThread = new Thread() {
            @Override
            public void run() {
                    MessageShareThread msThread = new MessageShareThread(threadParams);
                    msThread.start();
                    msThread.submitMessage("This is test message to share " + millis() , null);
                    while ( true ){
                        mySleep(100);
                        ResultRecord rez = msThread.pollResultRecord();
                        if ( rez != null){
                           // we got succes
                            System.out.println("Successfully polled success result saying about message.");
                        }
                    }
            }
            
            /** just shorthand */
            private void mySleep(int millis) {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MessageShareThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        testThread.start();
        
        theSleep(1000);
        
        testThread.interrupt();
 
        if ( testThread.isInterrupted() ){
            fail("Test took too long to complete. This is wrong test message anyways. it doesn't work like this..");
        }
        

        
    }

   private void theSleep(int millis){
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MessageShareThread.class.getName()).log(Level.SEVERE, null, ex);
                }       
   }
    
   public void testNullCredentials(){
        // how the hell can i test null credentials?
   }
    

    /**
     * Test what happens if invalid credentials are supplied.
     * What do I want to test?
     * That if credentials are invalid, then we continue looping??
     */
    public void testInvalidCredentials(){
        FakeIThreadParams threadParam = new FakeIThreadParams(FakeIThreadParams.CR_INVALID);
        MessageShareThread thread = new MessageShareThread(threadParam);
       
    }   
   
   /**
    * ?
    */
   public void testNoInternetConnection(){
       // maybe we set up invalid proxy for the time being.
       fail("Not impelemented");
   }
    
    

    
    
    /**
     * 
     */
    public void testSubmissionOfTweetFailed(){
        
    }
    
    
    private long millis(){
         return System.currentTimeMillis();
    }
}
