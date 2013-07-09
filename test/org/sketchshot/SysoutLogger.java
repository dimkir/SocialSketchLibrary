package org.sketchshot;

import org.sketchshot.utils.ILogging;

/**
 * This is simpliest implementation of logger for testing purposes.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class SysoutLogger implements ILogging{

    @Override
    public void println(String msg) {
        System.out.println(msg);
    }
    
}
