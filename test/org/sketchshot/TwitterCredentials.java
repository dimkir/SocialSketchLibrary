/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sketchshot;

import java.util.HashMap;
import java.util.Map;
import static org.sketchshot.utils.IConfigXmlSpecification.C_CONSUMER_KEY;
import static org.sketchshot.utils.IConfigXmlSpecification.C_CONSUMER_SECRET;
import static org.sketchshot.utils.IConfigXmlSpecification.C_OAUTH_SECRET;
import static org.sketchshot.utils.IConfigXmlSpecification.C_OAUTH_TOKEN;

/**
 * Just utility class which returns different types of credentials.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class TwitterCredentials {
    public static Map<String, String> getValidCredentials(){
        Map<String, String>  cred = new HashMap<String, String>();
        cred.put(C_CONSUMER_KEY, "VctBzerp3P3Wg3oFFBA8NA");
        cred.put(C_CONSUMER_SECRET, "d4oliVwCrwiO5QO8p95kSygZ6Q4C6pQNXJn1IAyag");
        cred.put(C_OAUTH_TOKEN, "1413163736-swd4b1RCjMJZLQOmmOJ4zkWKixwcYIAG3LinfVs");
        cred.put(C_OAUTH_SECRET, "eG5gnsMj3UTxu9Q96GvzWaetgG4mOFlHYBJCVGCmPg");
        return cred;           
    }
    
    public static Map<String, String> getINVALIDCredentials(){
        Map<String, String>  cred = new HashMap<String, String>();
        cred.put(C_CONSUMER_KEY, "VctBzerp3P3Wg3oFFBA8NA");
        cred.put(C_CONSUMER_SECRET, "d4oliVwCrwiO5QO8p95kSygZ6Q4C6pQNXJn1IAyag");
//        cred.put(C_OAUTH_TOKEN, "1413163736-swd4b1RCjMJZLQOmmOJ4zkWKixwcYIAG3LinfVs");
        cred.put(C_OAUTH_TOKEN, "INVALID");
//        cred.put(C_OAUTH_SECRET, "eG5gnsMj3UTxu9Q96GvzWaetgG4mOFlHYBJCVGCmPg");
        cred.put(C_OAUTH_SECRET, "INVALID");
        return cred;                 
    }
    
    
    /**
     * Returns initialized Map structure, which is empty
     */
    public static Map<String, String> getEMPTYCredentials(){
        return new HashMap<String, String>();
    }
}
