package org.twitshot.ex;
/**
 * This exception occurs when there's problem within {@link FontBoss} 
 * whilst loading font.
 */
public class FontLoadEx extends LibraryLoadException
{
    public FontLoadEx(String s) {
        super(s);
    }

    public FontLoadEx(Throwable cause) {
        super(cause);
    }
}