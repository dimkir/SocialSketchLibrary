package org.sketchshot.thread;

import java.util.Map;
import org.sketchshot.utils.ILogging;

/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public interface IThreadParameters {

    // here go the method to
    // do the things:
    // ???
    Map<String, String> getCredentials();

    
    ILogging getLogger();
   
    /**
     * Thread will need to know via whom to share messages.
     * @return 
     */
    IBlockingMessageSharer getBlockingMessageSharer();
}
