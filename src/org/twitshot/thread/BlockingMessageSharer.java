package org.twitshot.thread;

import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.twitshot.utils.IConfigXmlSpecification.C_CONSUMER_KEY;
import static org.twitshot.utils.IConfigXmlSpecification.C_CONSUMER_SECRET;
import static org.twitshot.utils.IConfigXmlSpecification.C_OAUTH_SECRET;
import static org.twitshot.utils.IConfigXmlSpecification.C_OAUTH_TOKEN;
import org.twitshot.utils.ILogging;
import org.twitshot.utils.PImage2;
import processing.core.PImage;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Encapsulates operations with twitter, allowing us higher level of
 * abstraction: we just send to him MessageRecords and it returns us error
 * codes.
 *
 * What's the convention in terms of authenticating? These may be "remote"
 * services... different ones... and we want to have some kind of reasonable
 * message... to return... Also on some errors - we may want to retry again
 * (timeout or no internet or smth). whereas on some (Invalid credentials or
 * smth) we want to return kinda "fail forever" message.
 *
 * Actually this doesn't even have to be only Twitter, it can be any PUBLISHER.
 * (Facebook? Flickr? Tumblr?)
 */
class BlockingMessageSharer {

    /**
     * This is object which will be used to covert PImage to compressed input
     * stream.
     */
    private PImage2 pimage2;
    /**
     * C_IMAGE_COMPRESSION_FORMAT_EXT when we compress PImage and represent as
     * InputStream this extension determins format into which we can compress.
     * For more details see
     *
     * @see PImage2.saveImageToStream and the compression method.
     */
    private static final String C_IMAGE_COMPRESSION_FORMAT_EXT = "png";
    private Twitter tw;
    private ILogging mLogger;

    BlockingMessageSharer(ILogging iLogger) {
        mLogger = iLogger;
        pimage2 = new PImage2(100, 100);
    }

    private void println(String s) {
        mLogger.println(s);
    }

    /**
     * Attempts to initialize (authenticate the MessageSharer
     * Let's first for simplicity assume it just returns error code 
     * @return NEGATIVE number on erorr
     *         0 on success
     */
    public int initBlocking(Map<String, String> cred) {
        // let's get initialization params;
        // perform authentication.
        //try to connect to the 
        println("TweetThread::init(): performing authentication");
        //Map<String, String> cred = getTwitterCredentials();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(cred.get(C_CONSUMER_KEY));
        cb.setOAuthConsumerSecret(cred.get(C_CONSUMER_SECRET));
        cb.setOAuthAccessToken(cred.get(C_OAUTH_TOKEN));
        cb.setOAuthAccessTokenSecret(cred.get(C_OAUTH_SECRET));

        tw = new TwitterFactory(cb.build()).getInstance();
        try {
            println("Trying veryfy credentials");
            tw.verifyCredentials();
            println("Registered successfully, userid: " + tw.getId());
            return 0; // success

        } catch (TwitterException twex) {
            if (twex.getStatusCode() == 401) {
                println("Cannot authenticate, probably wrong credentials: " + twex.getMessage());
            } else {
                println("Some other error attempting authenticate twitter: " + twex.getErrorMessage());
            }
            return -1; // error
        }
    }

    /**
     * Performs blocking call to share message (incuim: tweeting). What is the
     * contract? What kind of messages/situations should the client receive when
     * calling this?
     *
     * @return ?? should return some success status? no?
     */
    public void shareMessageBlocking(MessageRecord mr) {
        try {
            if (mr.img == null) {
                println("TweetThread::tweetMessageBlocking(): " + mr.msg + " image: null");
                Status rez = tw.updateStatus(mr.msg);
                println("Updated successfully, status: " + rez.toString());
            } else {
                StatusUpdate supdate = new StatusUpdate(mr.msg);
//                supdate.setMedia(C_PROFILE_TAG, null);
                addImageToUpdate(supdate, mr.img);
                //throw new UnsupportedOperationException("Sending statuses with images is not supported currently");
                Status rez = tw.updateStatus(supdate);
                println("Updated successfully, status: " + rez.toString());
                //println("TweetThread::tweetMessageBlocking(): " + mr.msg + " image:" + mr.img.toString());
            }
        } catch (TwitterException ex) {
            Logger.getLogger(MessageShareThread.class.getName()).log(Level.SEVERE, null, ex);
            println("Twitter exception: " + ex.getMessage());
        }
    }

    /**
     * This is internal utility, which inserts PImage data into twitter4j
     * StatusUpdate object. Doing behind the scenes compression into png or
     * smth.
     *
     * @param supdate
     * @param img
     */
    private void addImageToUpdate(StatusUpdate supdate, PImage img) {
        InputStream is = pimage2.getPImageAsInputStream(img, C_IMAGE_COMPRESSION_FORMAT_EXT);
        supdate.media("MyImage", is);
    }
}
