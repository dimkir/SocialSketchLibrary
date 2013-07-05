package org.twitshot;

import java.util.HashMap;
import java.util.Map;

/**
 * This is implementation of the gate for testing purposes.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
class FakeTweetDirectorGate implements 
        ITweetDirectorGate , IConfigXmlSpecification
{

    public FakeTweetDirectorGate() {
    }

    @Override
    public Map<String, String> getCredentials() {
        Map<String, String>  cred = new HashMap<String, String>();
        cred.put(C_CONSUMER_KEY, "VctBzerp3P3Wg3oFFBA8NA");
        cred.put(C_CONSUMER_SECRET, "d4oliVwCrwiO5QO8p95kSygZ6Q4C6pQNXJn1IAyag");
        cred.put(C_OAUTH_TOKEN, "1413163736-swd4b1RCjMJZLQOmmOJ4zkWKixwcYIAG3LinfVs");
        cred.put(C_OAUTH_SECRET, "eG5gnsMj3UTxu9Q96GvzWaetgG4mOFlHYBJCVGCmPg");
        return cred;
    }

    @Override
    public void println(String s) {
        System.out.println(s);
    }
    
}
