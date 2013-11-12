package org.sketchshot.helper;

import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sketchshot.thread.IBlockingMessageSharer;
import static org.sketchshot.thread.IBlockingMessageSharer.ERROR_FATAL;
import org.sketchshot.thread.MessageRecord;
import org.sketchshot.thread.MessageShareThread;
import static org.sketchshot.utils.IConfigXmlSpecification.C_CONSUMER_KEY;
import static org.sketchshot.utils.IConfigXmlSpecification.C_CONSUMER_SECRET;
import static org.sketchshot.utils.IConfigXmlSpecification.C_OAUTH_SECRET;
import static org.sketchshot.utils.IConfigXmlSpecification.C_OAUTH_TOKEN;
import org.sketchshot.utils.ILogging;
import org.sketchshot.utils.PImage2;
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
class BlockingTweetMsgSharer implements IBlockingMessageSharer {

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

    BlockingTweetMsgSharer(ILogging iLogger) {
        mLogger = iLogger;
        pimage2 = new PImage2(100, 100);
    }

    private void println(String s) {
        mLogger.println(s);
    }

    /**
     * Attempts to initialize (authenticate the MessageSharer
     * Let's first for simplicity assume it just returns error code
     * @param map with the authentication parameters. NULL is NOT ALLOWED. 
     * @throws IllegalArgumentException (which is unchecked) when null is supplierd.
     * @return NEGATIVE number on errorr
     *              #ERROR_FATAL
     *              #ERROR_RETRIABLE
     *         0 on success (#SUCCESS)
     */
    @Override
    public int initBlocking(Map<String, String> cred) {
        if ( cred == null ){ throw new IllegalArgumentException("Null is not allowed to be passed. Logic error in code"); }
        // this try block is to catch the IllegalArgument exceptions thwon by 
        // getNotNullOrThrow
        try {
                    // let's get initialization params;
                    // perform authentication.
                    //try to connect to the 
                    println("BlockingTweetMsgSharer::initBlocking(): performing authentication");
                    //Map<String, String> cred = getTwitterCredentials();

                    ConfigurationBuilder cb = new ConfigurationBuilder();
                    cb.setOAuthConsumerKey(getNotNullOrThrow(C_CONSUMER_KEY,cred));
                    cb.setOAuthConsumerSecret(getNotNullOrThrow(C_CONSUMER_SECRET,cred));
                    cb.setOAuthAccessToken(getNotNullOrThrow(C_OAUTH_TOKEN, cred));
                    cb.setOAuthAccessTokenSecret(getNotNullOrThrow(C_OAUTH_SECRET, cred));

                    tw = new TwitterFactory(cb.build()).getInstance();
                    try {
                        println("Trying veryfy credentials");
                        tw.verifyCredentials();
                        println("Registered successfully, userid: " + tw.getId());
                        return SUCCESS; // success

                    } catch (TwitterException twex) {
                        int statusCode = twex.getStatusCode();
                        int errorCode = twex.getErrorCode();
                        printTwitterEx(twex);
                        if ( statusCode == 401) {
                            println("Cannot authenticate, probably wrong credentials: " + twex.getMessage());
                            return ERROR_FATAL; // error
                        } 
                        else if ( statusCode == 429 ){
                            // rate limit exceeded (yes even for login)
                            return ERROR_RETRIABLE;
                        }
                        else if ( statusCode == -1){
                            // obviously this is something like connection error 
                            // which happened on our side (without getting a reply from twitter)
                            // or smth.
                            return ERROR_RETRIABLE;
                        }
                        else {
                            println("Some other error attempting authenticate twitter, status(" + statusCode + ") " +
                                      twex.getMessage());
                            // todo: which errors should these be? Retriable or not?
                            return ERROR_RETRIABLE; // error
                        }
                    }
        }
        //TODO We can't be catching here Unchecked exceptoin. There
        // should be different local exception introduced to sort this thing.
        // because this exception is meant to crash everythig informing about 
        // program logic errror.
        // this is unchecked exception and I shouldn't use them like this.
        catch(IllegalArgumentException iaex){
            // this should have been
            println(iaex.getMessage());
            return ERROR_FATAL;
        }
    }

    /**
     * TODO: this one shouldn't throw this illegal argument exception (but rather something
     * checked).
     * @param key
     * @param map
     * @return 
     */
    private String getNotNullOrThrow(String key, Map<String,String> map){
        String value = map.get(key);
        if ( value == null){
            throw new IllegalArgumentException("map doesn't have key named [" + key + "]");
            
        }
        return value;
    }
    /**
     * Performs blocking call to share message (incuim: tweeting). What is the
     * contract? What kind of messages/situations should the client receive when
     * calling this?
     *
     * @return ?? should return some success status? no?
     */
    @Override
    public int shareMessageBlocking(MessageRecord mr) {
        try {
            if (mr.img == null) {
                println("BlockingTweetMsgSharer::shareMessageBlocking(): " + mr.msg + " image: null");
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
            return SUCCESS; // SUCCESS
        } catch (TwitterException ex) {
            // TODO: add split between retriable and non-retriable errors
            //Logger.getLogger(MessageShareThread.class.getName()).log(Level.SEVERE, null, ex);
            int statusCode = ex.getStatusCode();
            int errorCode = ex.getErrorCode();
            
            printTwitterEx(ex);


            if ( statusCode == 403 ){
                // message understood but refused.
                // that would result in fatal errro
                if ( errorCode == 183 ){
                    // message too long
                }
                else if ( errorCode == 187 ){
                    // duplicate status message
                }
                return ERROR_FATAL;
            }
            else if ( statusCode == 429 ){
                // rate limit is there
                return ERROR_RETRIABLE;
            }
            else if ( statusCode == -1 ){
                // we probably didn't even get reply from twitter server.
                // probalby some error with connection on our side.
                return ERROR_RETRIABLE;
            }
            // all others as well are fatal?
            return ERROR_FATAL;
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

    /**
     * Convenience method to print out information about twitter exception in 
     * easy readable and glancable format.
     * @param ex 
     */
    private void printTwitterEx(TwitterException ex) {
            int statusCode = ex.getStatusCode();
            int errorCode = ex.getErrorCode();
            println("Twitter exception with statusCode(" + statusCode + ")" + "ExceptionMessage:[\n"
                    + ex.getMessage() + "]"  + "ErrorMessage:[" + ex.getErrorMessage() + "], errorCode(" + errorCode +")");        
    }
}
