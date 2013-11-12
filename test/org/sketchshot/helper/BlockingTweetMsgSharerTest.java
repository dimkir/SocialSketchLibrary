package org.sketchshot.helper;

import junit.framework.TestCase;
import org.sketchshot.ProxySetter;
import org.sketchshot.SysoutLogger;
import org.sketchshot.TwitterCredentials;
import org.sketchshot.thread.IBlockingMessageSharer;
import org.sketchshot.thread.MessageRecord;

/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class BlockingTweetMsgSharerTest extends TestCase {
    
    public BlockingTweetMsgSharerTest(String testName) {
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
     * Test of initBlocking method, of class BlockingTweetMsgSharer.
     * Null is not allowed to be passed (meanign that passing null is actually 
     * a logic error in a program) to initBlocking() thus we need to
     * catch this as soon as possible. This is why I use IllegalArgumentException
     * to indicate that.
     */
    public void testInitBlocking_NULL_CREDENTIALS() {
        System.out.println("\n** testInitBlocking_NULL_CREDENTIALS");        
        BlockingTweetMsgSharer twSh = new BlockingTweetMsgSharer(new SysoutLogger());
        try{
            twSh.initBlocking(null);
            fail("Passed on null credentials");
        }
        catch(IllegalArgumentException npex){
            System.out.println(npex.getMessage());
        }
    }

    /**
     * Test of initBlocking method, of class BlockingTweetMsgSharer.
     */
    public void testInitBlocking_VALID_CREDENTIALS() {
        System.out.println("\n** testInitBlocking_VALID_CREDENTIALS");        
        BlockingTweetMsgSharer twSh = new BlockingTweetMsgSharer(new SysoutLogger());
        int rez = twSh.initBlocking(TwitterCredentials.getValidCredentials());
        println("Returned result: " + rez);
        assert(rez == 0);
        assert(rez == twSh.SUCCESS);
    }
    
    
    /**
     * Test of initBlocking method, of class BlockingTweetMsgSharer.
     */
    public void testInitBlocking_NO_CONNECTION_SIMULATION_NON_EXISTING_PROXY() {
        System.out.println("\n** testInitBlocking_NO_CONNECTION_SIMULATION_NON_EXISTING_PROXY");
        ProxySetter.setFakeProxy();
        BlockingTweetMsgSharer twSh = new BlockingTweetMsgSharer(new SysoutLogger());
        int rez = twSh.initBlocking(TwitterCredentials.getValidCredentials());
        ProxySetter.unsetFakeProxy();
        assert(rez < 0);
    }
    
    

    /**
     * Testing "full" (all fields available) but invalid credentials.
     */
    public void testInitBlocking_INVALID_CREDENTIALS() {
        System.out.println("\n** testInitBlocking_INVALID_CREDENTIALS");        
        BlockingTweetMsgSharer twSh = new BlockingTweetMsgSharer(new SysoutLogger());
        int rez = twSh.initBlocking(TwitterCredentials.getINVALIDCredentials());
        assert(rez < 0);
    }    
    
    
    /**
     * Tests versus empty credentials (simply empty map structure)
     */
    public void testInitBlocking_EMPTY_CREDENTIALS() {
        System.out.println("\n** testInitBlocking_EMPTY_CREDENTIALS");        
        BlockingTweetMsgSharer twSh = new BlockingTweetMsgSharer(new SysoutLogger());
        int rez = twSh.initBlocking(TwitterCredentials.getEMPTYCredentials());
        assert(rez < 0);
    }    
    
    

    /**
     * Test of shareMessageBlocking method, of class BlockingTweetMsgSharer.
     */
    public void testShareMessageBlocking_TEST_VALID__TEXT_MESSAGE() {
        System.out.println("\n** testInitBlocking_TEST_VALID__TEXT_MESSAGE");         
        BlockingTweetMsgSharer twSh = getInitializedSharer();
        String tweet = "This is test message: " + millis();
        MessageRecord mr = new MessageRecord(tweet, null);
        int rez  = twSh.shareMessageBlocking(mr);
        assert(rez >= 0);
        assert(rez == twSh.SUCCESS);
    }
    
    
    /**
     * Test of shareMessageBlocking method, of class BlockingTweetMsgSharer.
     */
    public void DISABLED_testShareMessageBlocking_TEST_SIMULATE_NETWORK_ERROR_FAKE_PROXY() {
        fail("This one doesn't seem to work properly. Seems like proxy change after authentication doesn't influence antythinng");
        System.out.println("\n** testInitBlocking_TEST_SIMULATE_NETWORK_ERROR_FAKE_PROXY");         
        BlockingTweetMsgSharer twSh = getInitializedSharer();
        ProxySetter.setFakeProxy();
        String tweet = "This is test message: " + millis();
        MessageRecord mr = new MessageRecord(tweet, null);
        int rez  = twSh.shareMessageBlocking(mr);
        ProxySetter.unsetFakeProxy();
        assert(rez < 0);
        assert(rez == IBlockingMessageSharer.ERROR_RETRIABLE);
        
    }    
    

    /**
     * Test of shareMessageBlocking method, of class BlockingTweetMsgSharer.
     */
    public void testShareMessageBlocking_TEST_TOO_LONG__TEXT_MESSAGE() {
        System.out.println("\n** testInitBlocking_TEST_TOO_LONG__TEXT_MESSAGE");         
        BlockingTweetMsgSharer twSh = getInitializedSharer();
        String tweet = "This is TOOOOOOOOOOOOOOOOOOOOOOOOOO LOOOOOOOOOOOONG " + 
                        "TOOOOOOOOOOOOOOOOOOOOOO LONG" + 
                        "TOOOOOOOOOOOOOOOOOOOOOO LONG" + 
                        "TOOOOOOOOOOOOOOOOOOOOOO LONG" + 
                        " test message: " + millis();
        MessageRecord mr = new MessageRecord(tweet, null);
        int rez  = twSh.shareMessageBlocking(mr);
        assert(rez < 0);
        assert(rez == twSh.ERROR_FATAL);
    }    
    
    /**
     * Tests what happens when duplicate message is tweeted to
     * twitter.
     */
    public void testShareMessageBlocking_TEST_DUPLICATE_MESSAGE(){
        System.out.println("\n** testInitBlocking_TEST_DUPLICATE_MESSAGE");         
        BlockingTweetMsgSharer twSh = getInitializedSharer();
        String tweet = "This is test message: " + millis();
        MessageRecord mr = new MessageRecord(tweet, null);
        
        
        // the question here: CAN I REINSERT THE MESSAGE RECORD???
        int rez  = twSh.shareMessageBlocking(mr);
        if ( rez < 0 ){
            throw new IllegalStateException("First tweet should have passed successfully" + 
                    "We didn't get to assertion even.");
        }
        
        MessageRecord duplicateMr = new MessageRecord(tweet, null);
        rez = twSh.shareMessageBlocking(duplicateMr); // same message?
        assert(rez < 0);
        assert(rez == BlockingTweetMsgSharer.ERROR_FATAL);
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Just returns initialized (with valid credentials) sharer instance
     */
    private BlockingTweetMsgSharer getInitializedSharer() {
        BlockingTweetMsgSharer twSh = new BlockingTweetMsgSharer(new SysoutLogger());
        int rez = twSh.initBlocking(TwitterCredentials.getValidCredentials());
        if ( rez != BlockingTweetMsgSharer.SUCCESS ){
            throw new IllegalStateException("Some kind of mistake. these are valid credentials and" +
                    " they should have resulted in successful log in");
        }
        return twSh;
    }

    private long millis() {
        return System.currentTimeMillis();
    }

    private void println(String string) {
        System.out.println(string);
    }
}
