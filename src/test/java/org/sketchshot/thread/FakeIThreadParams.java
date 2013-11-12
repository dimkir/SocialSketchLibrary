package org.sketchshot.thread;

import org.sketchshot.utils.IConfigXmlSpecification;
import java.util.Map;
import org.sketchshot.SysoutLogger;
import org.sketchshot.TwitterCredentials;
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
    private IBlockingMessageSharer mBlockingSharer;

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
            return TwitterCredentials.getValidCredentials();
    }
    
    private Map<String, String> fetchValidCredentials(){
            return TwitterCredentials.getINVALIDCredentials();
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

    
    void setBlockingSharer(IBlockingMessageSharer sharer){
        mBlockingSharer = sharer;
    }
    
    @Override
    public IBlockingMessageSharer getBlockingMessageSharer() {
        return mBlockingSharer;
    }
    
}
