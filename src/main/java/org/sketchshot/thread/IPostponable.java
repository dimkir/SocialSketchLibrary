/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sketchshot.thread;

/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public interface IPostponable {
    
    /**
     * Returns the "earliest" time for action.
     * Which means that action CANNOT happen EARLER, 
     * than this time, but is ok to happen shortly after.
     * 
     */
    long actionTime();
    
}
