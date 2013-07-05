package org.twitshot.thread;

import java.util.Map;
import org.twitshot.utils.ILogging;

/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public interface ITweetDirectorGate {

    // here go the method to
    // do the things:
    // ???
    Map<String, String> getCredentials();

    
    ILogging getLogger();
    
}
