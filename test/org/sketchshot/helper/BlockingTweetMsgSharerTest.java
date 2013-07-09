package org.sketchshot.helper;

import junit.framework.TestCase;
import org.sketchshot.ProxySetter;
import org.sketchshot.SysoutLogger;
import org.sketchshot.TwitterCredentials;

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
        assert(rez == 0);
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
    public void testShareMessageBlocking_TEST_VALID_MESSAGE() {
        
    }
    
    /**
     * Tests what happens when duplicate message is tweeted to
     * twitter.
     */
    public void testShareMessageBlocking_TEST_DUPLICATE_MESSAGE(){
        
    }
}
