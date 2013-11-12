package org.sketchshot;

/**
 * This is convenience class providing API for easily switching on proxies. Code
 * snippet taken from here
 *
 * @see
 * http://stackoverflow.com/questions/120797/how-do-i-set-the-proxy-to-be-used-by-the-jvm
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class ProxySetter {

    public static void setFakeProxy(){
        new ProxySetter().setProxy();
    }
    
    public static void unsetFakeProxy(){
            System.setProperty("http.proxyHost", "");
            System.setProperty("http.proxyPort", "");
            System.setProperty("https.proxyHost", "");
            System.setProperty("https.proxyPort", "");        
    }
    
    public void setProxy() {
        if (isUseHTTPProxy()) {
            // HTTP/HTTPS Proxy
            System.setProperty("http.proxyHost", getHTTPHost());
            System.setProperty("http.proxyPort", getHTTPPort());
            System.setProperty("https.proxyHost", getHTTPHost());
            System.setProperty("https.proxyPort", getHTTPPort());
            if (isUseHTTPAuth()) {
                throw new UnsupportedOperationException("not impelmented");
            }
        }
        if (isUseSOCKSProxy()) {
            // SOCKS Proxy
            System.setProperty("socksProxyHost", getSOCKSHost());
            System.setProperty("socksProxyPort", getSOCKSPort());
            if (isUseSOCKSAuth()) {
                throw new UnsupportedOperationException("not impelmented");
            }
        }
    }

    private boolean isUseSOCKSProxy() {
        return false;
    }

    private boolean isUseHTTPProxy() {
        return true;
    }

    private boolean isUseSOCKSAuth() {
        return false;
    }

    private String getSOCKSHost() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String getSOCKSPort() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isUseHTTPAuth() {
        return false;
    }

    private String getHTTPHost() {
        return "127.0.0.1";
    }

    private String getHTTPPort() {
        return "41983"; // some random unused port 
    }
}
