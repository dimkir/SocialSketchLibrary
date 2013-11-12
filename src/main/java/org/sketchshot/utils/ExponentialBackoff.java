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
    
    /**
     * This is amount of time which we need to wait
     * before the next retry is allowed. This is the value 
     * which will be exponentially increasing.
     */
    private long mCurrentDelayMillis = 0;
    private float mWaitTimeFactor = 1.0f; // 
    private long mMaxDelayMillis = -1;
    public final static int C_MIN_WAIT_TIME_SECONDS = 3;
    private final static long C_MIN_WAIT_TIME_MILLIS = C_MIN_WAIT_TIME_SECONDS * 1000; // 3 seconds minimum time.
    
    boolean mVerbose = false;
    
    
    /**
     * Initializes EB object.
     * @param multiplier    by which factor we should multiply the time every time?
     *                      number should be 1.0 and above? (can't we just make it smaller?
     *                      (it doesn't make any sense).
     * @param maxDelaySeconds  we can limit max delay by this time.
     *                       shouldn't be less than #C_MIN_WAIT_TIME_SECONDS
     * @param verboseFlag   set to true if you want to get extra debug messages to console.
     *                      // the question is: how to send messages to console if this
     *                      // class doesn't know what a console is? or can be run on a
     *                      // device w/o console?
     *                      // TODO: add some println() or "console" or "logger" interface or smth.
     * @throws IllegalArgumentException
     */
    public ExponentialBackoff(float multiplier, int maxDelaySeconds, boolean verboseFlag) {
        verifyMultiplierValueOrThrow(multiplier);
        verifyMaxDelaySecondsOrThrow(maxDelaySeconds);
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
         
         if ( timeSinceLastAttempt >= mCurrentDelayMillis ){ // passed time
             return true;
         }
         
         return false; // time  still hasn't passed.
    }
    
    
    /**
     * This is method for testing/debugging; returns current wait time. 
     * It's visibility is package, so that only test package can see it.
     * @return milliseconds.
     */
    long getCurrentDelayMillis(){
        return mCurrentDelayMillis;
    }
    
    long timeSinceLastAttempt(){
        return millis() - mLastAttemptTime;
    }
    
    /**
     * Returns value if there is, or 0 if no time to wait.
     * @return 
     */
    long timeLeftToWait(){
        long leftToWait = getCurrentDelayMillis() - timeSinceLastAttempt();
        return leftToWait > 0 ? leftToWait : 0;
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
        mCurrentDelayMillis = C_MIN_WAIT_TIME_MILLIS;
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
        long newExpDelay = (long) ( mCurrentDelayMillis * mWaitTimeFactor);
        if ( newExpDelay > mMaxDelayMillis ){
            newExpDelay = mMaxDelayMillis;
        }
        mCurrentDelayMillis  = newExpDelay;
    }

    /**
     * Verifies that multiplier complies with the spec or throws.
     * See comment in the constructor {@link ExponentialBackoff}
     * @param multiplier
     * @throws IllegalArgumentException 
     */
    private void verifyMultiplierValueOrThrow(float multiplier) throws IllegalArgumentException
    {
        if ( multiplier < 1.0f ){
            throw new IllegalArgumentException("Multiplier cannot be less than 1.0, supplied(" + multiplier + ")");
        }
    }

    /**
     * Verifies that max delay parameter fits into spec or throws.
     * See #ExponetialBackoff constructor comment to know the spec.
     * @param maxDelaySeconds
     * @throws IllegalArgumentException 
     */
    private void verifyMaxDelaySecondsOrThrow(int maxDelaySeconds) throws IllegalArgumentException
    {
         if ( maxDelaySeconds < C_MIN_WAIT_TIME_SECONDS ){
             throw new IllegalArgumentException("Max delay(" + maxDelaySeconds + ") cannot be less than C_MIN_WAIT_TIME_SECONDS(" + C_MIN_WAIT_TIME_SECONDS + ")");
         }
    }
    
}
