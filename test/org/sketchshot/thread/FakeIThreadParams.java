package org.sketchshot.thread;

import org.sketchshot.utils.IConfigXmlSpecification;
import java.util.HashMap;
import java.util.Map;
import org.sketchshot.SysoutLogger;
import static org.sketchshot.utils.IConfigXmlSpecification.C_CONSUMER_KEY;
import static org.sketchshot.utils.IConfigXmlSpecification.C_CONSUMER_SECRET;
import static org.sketchshot.utils.IConfigXmlSpecification.C_OAUTH_SECRET;
import static org.sketchshot.utils.IConfigXmlSpecification.C_OAUTH_TOKEN;
import org.sketchshot.utils.ILogging;

/**
 * This is implementation of thread parameters. Credential type is supplied 
 * with the constructor.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
class FakeIThreadParams 
implements 
        IThreadParameters , IConfigXmlSpecification
{
    public static final int CR_VALID = 1;
    public static final int CR_INVALID = 2;
    public static final int CR_MAP_NULL = 3;
    
    private int PARAM_FLAG = CR_VALID; // -1 uninitialized.
    private final ILogging mLogger;

    /**
     * Initializes params to given type of credentials and DEFAULT Sysout logger.
     */
    public FakeIThreadParams(int whichParamsToUse) {
        verifyParamOrThrow(whichParamsToUse);
        PARAM_FLAG = whichParamsToUse;
        mLogger = new SysoutLogger();
    }
    
    /**
     * Initializes with given type of credentials, and 
     * receives logger as parameter.
     */
    public FakeIThreadParams(int whichParamsToUse, ILogging logger){
        verifyParamOrThrow(whichParamsToUse);
        PARAM_FLAG = whichParamsToUse;
        mLogger = logger;
    }
    
    /**
     * Verifies that parameters are in given range
     */
    private void verifyParamOrThrow(int param) throws IllegalArgumentException
    {
          switch( param){
              case CR_VALID:
              case CR_INVALID:
              case CR_MAP_NULL:
                  break;
              default:
                  throw new IllegalArgumentException("Parameter cannot be (" + param + "). Only valid constants CR_XXX are allowed");
          }
    }

    /**
     * Returns credentials previously set by parameter.
     */
    @Override
    public Map<String, String> getCredentials() {
            switch ( PARAM_FLAG ){
                case (CR_VALID):
                    return fetchValidCredentials();
                case (CR_INVALID):
                    return fetchINVALIDCredentials();
                case (CR_MAP_NULL):
                    return fetchNullMapCredentials();
                default: 
                    throw new IllegalStateException("Logic error. PARAM_FLAG canot take value: " + PARAM_FLAG);
                            
            }
    }

    
    private Map<String, String> fetchINVALIDCredentials(){
        Map<String, String>  cred = new HashMap<String, String>();
        cred.put(C_CONSUMER_KEY, "VctBzerp3P3Wg3oFFBA8NA");
        cred.put(C_CONSUMER_SECRET, "d4oliVwCrwiO5QO8p95kSygZ6Q4C6pQNXJn1IAyag");
        cred.put(C_OAUTH_TOKEN, "1413163736-swd4b1RCjMJZLQOmmOJ4zkWKixwcYIAG3LinfVs");
        cred.put(C_OAUTH_SECRET, "eG5gnsMj3UTxu9Q96GvzWaetgG4mOFlHYBJCVGCmPg");
        return cred;        
    }
    
    private Map<String, String> fetchValidCredentials(){
        Map<String, String>  cred = new HashMap<String, String>();
        cred.put(C_CONSUMER_KEY, "VctBzerp3P3Wg3oFFBA8NA");
        cred.put(C_CONSUMER_SECRET, "d4oliVwCrwiO5QO8p95kSygZ6Q4C6pQNXJn1IAyag");
//        cred.put(C_OAUTH_TOKEN, "1413163736-swd4b1RCjMJZLQOmmOJ4zkWKixwcYIAG3LinfVs");
        cred.put(C_OAUTH_TOKEN, "INVALID");
//        cred.put(C_OAUTH_SECRET, "eG5gnsMj3UTxu9Q96GvzWaetgG4mOFlHYBJCVGCmPg");
        cred.put(C_OAUTH_SECRET, "INVALID");
        return cred;        
    }
    
    /**
     * Returns instead of all map NULL.
     * @return 
     */
    private Map<String, String> fetchNullMapCredentials(){
         return null;
    }
    
    @Override
    public ILogging getLogger() {
        return mLogger;
    }

    
    @Override
    public IBlockingMessageSharer getBlockingMessageSharer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
