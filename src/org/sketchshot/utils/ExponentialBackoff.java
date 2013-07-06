package org.sketchshot.utils;

/**
 * This class is used to implement functionality of 
 * "exponential backoff" (exponential delay between retries).
 * 
 * USAGE:
 * <p>
 * 1) Initialize and set parameters<br>
 * {@code ExponentialBackoff eb = new ExponentialBackoff(2.0, 30, true); }<br>
 * {@code // doubles time every time we fail and maximum length of delay is 30 seconds
 * and set verbose to true (for debugging purposes)}
 * </p>
 * <p>
 * 2) Before every attempt to connect: test whether we're ready. (Also possible to test before 
 * 1st 
 * </p>
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class ExponentialBackoff {
    
    /**
     * mLastStatusSuccess this status tells the result of the last 
     * attempt. Depending on this status - EB will wait or tell to act.
     * As we start: we assume that last status was success, so that next
     * operation is not-touched.
     */
    private boolean mLastStatusSuccess = true; 
    private long mLastAttemptTime = -1;
    
    private long mCurrentWaitTimeMillis = 0;
    private float mWaitTimeFactor = 1.0f; // 
    private long mMaxDelayMillis = -1;
    
    private final static long C_MIN_WAIT_TIME_MILLIS = 3000; // 3 seconds minimum time.
    
    boolean mVerbose = false;
    
    
    /**
     * Initializes EB object.
     * @param multiplier    by which factor we should multiply the time every time?
     * @param maxDelaySeconds  we can limit max delay by this time.
     * @param verboseFlag   set to true if you want to get extra debug messages to console.
     *                      // the question is: how to send messages to console if this
     *                      // class doesn't know what a console is? or can be run on a
     *                      // device w/o console?
     *                      // TODO: add some println() or "console" or "logger" interface or smth.
     */
    public ExponentialBackoff(float multiplier, int maxDelaySeconds, boolean verboseFlag) {
        mWaitTimeFactor = multiplier;
        mMaxDelayMillis = maxDelaySeconds * 1000;
        mVerbose = verboseFlag;
        resetWaitTime(); 
    }
    
    /**
     * Returns flag of whether we're ready to retry.
     * if last status success - runs straight away.
     * if last status failure: checks if delay has passed
     * @return 
     */
    public boolean isReadyToRetry(){
         
         if(  mLastStatusSuccess ) { return true; }
         
         // otherwise 
         long timeSinceLastAttempt  = millis() - mLastAttemptTime;
         
         if ( timeSinceLastAttempt >= mCurrentWaitTimeMillis ){ // passed time
             return true;
         }
         
         return false; // time  still hasn't passed.
    }
    
    
    /**
     * To be called upon successful connection. So that next time we want reconnect,
     * we do not wait.
     */
    public void registerSuccess(){
        mLastStatusSuccess = true;
        mLastAttemptTime = millis();
        resetWaitTime(); // 
    }
    
    
    /**
     * Resets wait time.
     * ? how ? first to minimum delay???
     */
    private void resetWaitTime(){
        mCurrentWaitTimeMillis = C_MIN_WAIT_TIME_MILLIS;
    }
    
    /**
     * Saves time of failure, and increments the delay.
     */
    public void registerFailure(){
        mLastStatusSuccess = false;
        mLastAttemptTime = millis();
        // obviously we just have to increase delay.
        increaseWaitTime();
    }
    
    
    private long millis(){
         return System.currentTimeMillis();
    }

    
    private void increaseWaitTime() {
        long newExpDelay = (long) ( mCurrentWaitTimeMillis * mWaitTimeFactor);
        if ( newExpDelay > mMaxDelayMillis ){
            newExpDelay = mMaxDelayMillis;
        }
        mCurrentWaitTimeMillis  = newExpDelay;
    }
    
}
