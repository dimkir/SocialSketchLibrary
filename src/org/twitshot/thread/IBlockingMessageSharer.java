package org.twitshot.thread;

import java.util.Map;

/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public interface IBlockingMessageSharer {

    /**
     * Attempts to initialize (authenticate the MessageSharer
     * Let's first for simplicity assume it just returns error code
     * @return NEGATIVE number on erorr
     *         0 on success
     */
    int initBlocking(Map<String, String> cred);

    /**
     * Performs blocking call to share message (incuim: tweeting). What is the
     * contract? What kind of messages/situations should the client receive when
     * calling this?
     *
     * @return ?? should return some success status? no?
     */
    void shareMessageBlocking(MessageRecord mr);
    
}
