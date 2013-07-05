package org.twitshot.helper;

import java.util.Map;
import org.twitshot.helper.TweetDirector;
import org.twitshot.thread.ITweetDirectorGate;
import org.twitshot.thread.ITweetDirectorGate;
import org.twitshot.utils.ILogging;

/**
 * This class is like a tunnell for TwitThread accessing
 * informatino it needs. TweetDirector
 * the purpose of communicating with TweetThread
 */
public class TweetDirectorGate implements ITweetDirectorGate {
    private final TweetDirector outerX;

    public TweetDirectorGate(final TweetDirector outerX) {
        this.outerX = outerX;
    }

    // here go the method to
    // do the things:
    // ???
    @Override
    public Map<String, String> getCredentials() {
        return outerX.getLogOnCredentials();
    }

    /**
     * Via this method thread will request logger.
     * @return
     */
    @Override
    public ILogging getLogger() {
        // for simplicity we just return default logger.
        return outerX.getLogger();
    }
    
}
