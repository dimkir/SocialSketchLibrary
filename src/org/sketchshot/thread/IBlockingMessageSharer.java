package org.sketchshot.thread;

import java.util.Map;

/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public interface IBlockingMessageSharer {

    /**
     * We can have two types of errors returned from shareMessageBlocking
     * one FATAL and one RETRIABLE. If the message is retrieable,
     * which means that connection was down or smth - then we 
     * can reschedule the message for later.
     */
    final int SUCCESS = 0;
    final int ERROR_FATAL = -2;
    final int ERROR_RETRIABLE = -3;
    
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
     * @return 0 on success,
     *         NEGATIVE on error (ERROR_FATAL, ERROR_RETRIABLE).
     */
    int shareMessageBlocking(MessageRecord mr);
    
}
