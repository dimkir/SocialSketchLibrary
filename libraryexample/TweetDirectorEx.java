package libraryexample;
/**
 * This is exception thrown by TweetDirector.
 */

class TweetDirectorEx extends LibraryLoadException
{
  TweetDirectorEx(String s){
    super(s);
  }
}