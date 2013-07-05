package org.twitshot.ex;
/**
 * Base exception for the library
 */
public class LibraryLoadException extends Exception
{
  public LibraryLoadException(String s){
     super(s);
  }

  public LibraryLoadException(Throwable cause) {
        super(cause);
  }

  public LibraryLoadException(String message, Throwable cause) {
        super(message, cause);
  }
}