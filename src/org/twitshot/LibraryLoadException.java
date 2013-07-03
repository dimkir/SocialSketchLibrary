package org.twitshot;
/**
 * Base exception for the library
 */
public class LibraryLoadException extends Exception
{
  LibraryLoadException(String s){
     super(s);
  }

  LibraryLoadException(Throwable cause) {
        super(cause);
  }

  LibraryLoadException(String message, Throwable cause) {
        super(message, cause);
  }
}