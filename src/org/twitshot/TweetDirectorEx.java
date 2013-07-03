package org.twitshot;
/**
 * This is exception thrown by TweetDirector.
 */

public class TweetDirectorEx extends LibraryLoadException
{
  TweetDirectorEx(String s){
    super(s);
  }
}