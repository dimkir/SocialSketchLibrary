package org.twitshot.helper;

import java.util.Map;
import org.twitshot.helper.ShareDirector;
import org.twitshot.thread.IThreadParameters;
import org.twitshot.thread.IThreadParameters;
import org.twitshot.utils.ILogging;

/**
 * This class is like a tunnell for TwitThread accessing
 * informatino it needs. TweetDirector
 * the purpose of communicating with TweetThread
 */
public class ShareDirectorParamsForThread implements IThreadParameters {
    private final ShareDirector outerX;

    public ShareDirectorParamsForThread(final ShareDirector outerX) {
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
