package org.twitshot;
/**
 * Base exception for the library
 */
class LibraryLoadException extends Exception
{
  LibraryLoadException(String s){
     super(s);
  }
}