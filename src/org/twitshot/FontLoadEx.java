package org.twitshot;
/**
 * This exception occurs when there's problem within {@link FontBoss} 
 * whilst loading font.
 */
public class FontLoadEx extends LibraryLoadException
{
    FontLoadEx(String s) {
        super(s);
    }

    FontLoadEx(Throwable cause) {
        super(cause);
    }
}