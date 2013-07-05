package org.twitshot;

import java.util.Map;

/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public interface ITweetDirectorGate {

    // here go the method to
    // do the things:
    // ???
    Map<String, String> getCredentials();

    /**
     * Just way (for TwitterThread to communicate with log console)
     */
    void println(String s);
    
}
