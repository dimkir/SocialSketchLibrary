package org.sketchshot.utils;

import static junit.framework.Assert.fail;
import junit.framework.TestCase;

/**
 * Here we need to test that ExponentialBackoff works as intended.
 * 
 * So far the main trick is to make sure that delays work as expected.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class ExponentialBackoffTest extends TestCase {
    
    private static final float C_DEF_MULTIPLIER = 2.0f;
    private static final int C_DEF_DELAY_IN_SECONDS = 30;
    private static final boolean C_DEF_VERBOSE_FLAG = true;
    
    
    
    
    public ExponentialBackoffTest(String testName) {
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
     * Make sure delays are happening the way they should.
     * How? Let's create, make failed attempt, and see if we wait or not?
     * Later let's see if every atempt lasts expected amount of time.
     * ??
     */
    public void testDelay(){
        ExponentialBackoff eb = genDefaultEB();
        eb.registerFailure();
        
        // now we need to make sure that we wait... 
        
        
    }
    
    
    public void testDelayResetAfterRegisteringSuccess(){
        ExponentialBackoff eb = genDefaultEB();
        eb.registerFailure();
//        while ( eb.isReadyToRetry() ){
//            Thread.sleep(100);
//        }
        assert(!eb.isReadyToRetry());
        eb.registerSuccess();
        assert(eb.isReadyToRetry());
        
    }
    
    
    /**
     * Makes sure that exception is thrown if incorrect multiplicator
     * is supplied.
     */
    public void testInvalidMultiplicator(){
        try{
            ExponentialBackoff eb = new ExponentialBackoff(0.1f, C_DEF_DELAY_IN_SECONDS, C_DEF_VERBOSE_FLAG);
            fail("On ivalid multiplier value, it hasn't thrown exception");
        }
        catch(IllegalArgumentException iaex){
            System.out.println("Caught exception: " + iaex.getMessage());
        }
    }
    
    /**
     * Makes sure that with wrong delay in seconds
     * throws exception.
     */
    public void testInvalidDelaySeconds(){
        try{
            int invalidDelaySeconds = ExponentialBackoff.C_MIN_WAIT_TIME_SECONDS - 1;
            ExponentialBackoff eb = new ExponentialBackoff(C_DEF_MULTIPLIER, invalidDelaySeconds, C_DEF_VERBOSE_FLAG);
            fail("On ivalid delay time in seconds(" + invalidDelaySeconds + " it hasn't thrown exception, when minimum" +
                    " allowed is " + ExponentialBackoff.C_MIN_WAIT_TIME_SECONDS);
        }
        catch(IllegalArgumentException iaex){
            System.out.println("Caught exception: " + iaex.getMessage());
        }
    }    
    
    
    /**
     * Tests that once the class initialized it returns
     * true when called readyToRetry()
     */
    public void testOnInitReadyToRetry(){
        ExponentialBackoff eb = new ExponentialBackoff(C_DEF_MULTIPLIER, C_DEF_DELAY_IN_SECONDS, C_DEF_VERBOSE_FLAG);
        assert(eb.isReadyToRetry());
    }
    
    /**
     * Test of isReadyToRetry method, of class ExponentialBackoff.
     */
    public void DISABLED_testIsReadyToRetry() {
        System.out.println("isReadyToRetry");
        ExponentialBackoff instance = null;
        boolean expResult = false;
        boolean result = instance.isReadyToRetry();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerSuccess method, of class ExponentialBackoff.
     */
    public void DISABLED_testRegisterSuccess() {
        System.out.println("registerSuccess");
        ExponentialBackoff instance = null;
        instance.registerSuccess();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerFailure method, of class ExponentialBackoff.
     */
    public void DISABLED_testRegisterFailure() {
        System.out.println("registerFailure");
        ExponentialBackoff instance = null;
        instance.registerFailure();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Helper method, instantiates EB with default parameters.
     * @return 
     */
    private ExponentialBackoff genDefaultEB() {
        return new ExponentialBackoff(C_DEF_MULTIPLIER, C_DEF_DELAY_IN_SECONDS, C_DEF_VERBOSE_FLAG);
    }
}
