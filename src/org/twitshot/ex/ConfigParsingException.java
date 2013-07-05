package org.twitshot.ex;
/**
 *  Is thrown if there's error when LibConfig is loading.
 * TODO: rather should be named ConfigLoadException , because
 * it can also occur when "not only parsing" but do any config loading operations.
 */
public class ConfigParsingException extends LibraryLoadException
{
  public ConfigParsingException(String s){
    super(s);
  }
}