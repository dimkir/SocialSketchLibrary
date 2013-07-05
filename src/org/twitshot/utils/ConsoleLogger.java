package org.twitshot.utils;

import processing.core.PApplet;

/**
 * This is trivial implementation of ILogging, which just 
 * redirects all messages to sketch console.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class ConsoleLogger implements ILogging
{
    private PApplet mSketch;
    
    public ConsoleLogger(PApplet sketch){
        mSketch = sketch;
    }
    
    @Override
    public void println(String msg) {
        mSketch.println(msg);
    }
    
}
