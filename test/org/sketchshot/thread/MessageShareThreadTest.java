package org.sketchshot.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 * This test file is supposed to test the 
 * tweeting thread.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class MessageShareThreadTest extends TestCase {
    
    /**
     * This is point through which Thread is receiving parameters
     * from the outside.
     */
    
    public MessageShareThreadTest(String testName) {
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
        
        final String[] testMessages = generateTestMessages(100);
        final ArrayList<String> messagesSubmittedToBlockingSharer = new ArrayList<String>();
        
        final FakeIThreadParams threadParams = new FakeIThreadParams(FakeIThreadParams.CR_VALID);
        threadParams.setBlockingSharer(new IBlockingMessageSharer() {

            @Override
            public int initBlocking(Map<String, String> cred) {
                return this.SUCCESS;
            }

            @Override
            public int shareMessageBlocking(MessageRecord mr) {
                messagesSubmittedToBlockingSharer.add(mr.msg);
                return this.SUCCESS;
            }
        });
        
        
        
        Thread testThread = new Thread() {
            @Override
            public void run() {
                    MessageShareThread msThread = new MessageShareThread(threadParams);
                    msThread.start();
                    for(String s : testMessages){
                        msThread.submitMessage( s, null);
                    }
                    
                    while ( !msThread.isQueueEmpty() ){
                        mySleep(50);
                    }
//                    while ( true ){ )
                    
//                        mySleep(100);
//                        ResultRecord rez = msThread.pollResultRecord();
//                        if ( rez != null){
//                           // we got succes
//                            System.out.println("Successfully polled success result saying about message.");
//                        }
//                    }
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
        
        theSleep(10000);
        
        testThread.interrupt();
 
        if ( testThread.isInterrupted() ){
            fail("Test took too long to complete. This is wrong test message anyways. it doesn't work like this..");
        }
        
        boolean allMessagesEqual = compareStringContainers(testMessages, messagesSubmittedToBlockingSharer);
        assert(allMessagesEqual);
        

        
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

    /**
     * Generates n test messages for trying with MessageSharingThread.
     * @param n
     * @return 
     */
    private String[] generateTestMessages(int n) {
        String[] msg = new String[n];
        for(int i = 0 ; i < n ; i++){
            msg[i] = "This is test message " + i;
        }
        return msg;
    }

    /**
     * Compares that stingar and arlis have equal elements.
     * @param strar
     * @param arl
     * @return 
     */
    private boolean compareStringContainers(String[] strar, ArrayList<String> arl) {
        if ( strar.length != arl.size()){
            return false;
        }
        Arrays.sort(strar);
        Collections.sort(arl);
        for(int i = 0 ; i < strar.length ; i++){
            String s = strar[i];
            String a = arl.get(i);
            if ( ! s.equals(a) ){
                return false;
            }
        }
        return true;
    }
}
