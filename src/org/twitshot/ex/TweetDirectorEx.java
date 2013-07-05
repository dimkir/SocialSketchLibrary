package org.twitshot.ex;
/**
 * This is exception thrown by TweetDirector.
 */

public class TweetDirectorEx extends LibraryLoadException
{
  public TweetDirectorEx(String s){
    super(s);
  }
}