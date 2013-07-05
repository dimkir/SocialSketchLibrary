package org.twitshot.helper;
import processing.core.*;
import processing.data.XML;
import java.util.HashMap;
import java.util.Map;
import org.twitshot.ex.ConfigParsingException;
import org.twitshot.utils.IConfigXmlSpecification;

/**
 *  Loads and stores config info for the library.
 *  Always loads it from config file.
 *  Maybe for constructor which doesn't have filename, we can use ENVIROMENT_VARIABLE?
 *  The trick is that then - sketches won't work properly when exported! (But this library shouldn't really 
 *  be for export.)
 * 
 * TODO: how do we insure that all the configuration is valid?? (for example that all twitter config fields are there?
 * can we?  or should we bother? or just make twitterDirector constructor a failable and handle that error.
 * Has convenience getters to fluently access to the data.
 * On failure? - class is unusable. And constructor throws exception, means noone ever receives instance reference.
 */
public class LibConfig extends AbstractLibraryHelper
  implements IConfigXmlSpecification
  // this is so that we can construct the stringar to return TwitterConfiguration parameters.
{
  
  /**
   * @var This string contains helping infomration about where configuration was loaded form. 
   * Usually it is filepath to xml-file from which configuration was loaded.
   */
  private String mConfigurationAnnotation;

  /**
   * This is name of environment variable which holds the path to the default
   * config xml file. 
   * I made it public, so that I can query it in other methods to give
   * information to users or warning.s
   * This environment variable is used in case there's no 
   * config xml specified when instantiating LibConfig.
   */
  public static final String C_ENVIRONMENT_VARIABLE_NAME = "PROCESSING_TWITSHOT_CONFIGXML";
  
  
  private Map<String, String> mMap = new HashMap<String, String>();
  
  
  
  /**
   * This is default constructor, 
   * which USES ENVIRONMENT VARIABLE {@code C_ENVIRONMENT_VARIABLE_NAME} 
   * to retrieve path to the default constructor.
   */
  public LibConfig(PApplet papp) throws ConfigParsingException
  {
     super(papp);
     // now WHERE DO WE GET THE CONFIGURATION FROM?
     String configFilePath = getConfigFilepathFromEnvironment(); // can throw ConfigParsingException
     loadConfig(papp, configFilePath); 
  }
  
  /**
   * Loads configuration from the xml file. After successful run of this method,
   * this instance will contain all the necessary configuration variables.
   * This is failable
   * @param papp 
   * @param xmlFilename VALID filename. NULL NOT ALLOWED. If want to use configuration
   *        from envrionment variable #C_ENVIRONMENT_VARIABLE then call #LibConfig(PApplet p);
   *        
   * @throws ConfigParsingException (LibraryLoadException)
   */
  public LibConfig(PApplet papp, String xmlFilename) throws ConfigParsingException
  {
     super(papp);
     loadConfig(papp, xmlFilename);   // this one throws ConfigParsingException
  }
  
  
  private String getConfigFilepathFromEnvironment() throws ConfigParsingException
  {
      String val = System.getenv(C_ENVIRONMENT_VARIABLE_NAME); // PROCESSING_TWITSHOT_CONFIGFILE
      if ( val == null ){
         throw new ConfigParsingException("Twitshot error: when you initialized library with the default " +
                                           "twitter configuration, we tried getting file-path from envrionment " +
                                          "variable '" + C_ENVIRONMENT_VARIABLE_NAME + "' and this variable is not set");
                                           
      }
      return val;
  }
  
  /**
   * Loads library configuration from xml file. 
   * Dependencies: Uses PApplet.loadXML() to load xml.
   * @throws ConfigParsingException (which is subclass of LibraryLoadException)
   */
  private void loadConfig(PApplet papp, String xmlConfigFile) throws ConfigParsingException 
  { 
     // can it throw some fancy "null" exceptions or other stuff?
      XML xml = papp.loadXML(xmlConfigFile);
      if ( xml == null){
         throw new ConfigParsingException("loadXML() returns null when loading the xml file. Probably no xml config file avaible at location [" + xmlConfigFile + "]");
      }
      
      setConfigAnnotation("Loaded configurarion from [" + xmlConfigFile +"]");
      loadConfigurationFromXml(xml);  // throws ConfigParsingException, in case there's some problem
  }  
  
  
  /**
   * Returns where the configuration was obtained from. (Usually shows filepath and commnet)
   */
  String getConfigAnnotation(){
     return mConfigurationAnnotation;
  }
  
  /**
   * This is comment of where the configuration was obtained from
   */
  private void setConfigAnnotation(String ann){
    mConfigurationAnnotation = ann;
  }
  

  /**
   * Bee-method. This is actually the one which is scanning xml-tree and populating the fields.
   * failable.
   * @throws ConfigParsingException (for example if not all obligatory fields are available in xml tree)
   */
  private void loadConfigurationFromXml(XML xmlRootNode) throws ConfigParsingException
  {
     XML[] children = xmlRootNode.getChildren(C_PROFILE_TAG); // let's ask if there're any "profile" nodes.
     
     if ( children == null){
        throw new ConfigParsingException("Error, cannot find '<" + C_PROFILE_TAG + ">' tag inside of xml root." +
                                       "That tag usually contains all the credentials for twitter");
     }
     
     if ( children.length != 1 ){
        throw new ConfigParsingException("Error, found (" + children.length + ") <" + C_PROFILE_TAG + "> tags. Only 1 is allowed.");
     }
      
     XML profileNode = children[0];
     
     
     // let's save data into data structure.
     // we'll use fluentizers.
     
     saveData( C_CONSUMER_KEY,      getDetailOrThrow(C_CONSUMER_KEY, profileNode) );
     saveData( C_CONSUMER_SECRET,   getDetailOrThrow(C_CONSUMER_SECRET, profileNode) );
     saveData( C_OAUTH_TOKEN,       getDetailOrThrow(C_OAUTH_TOKEN, profileNode));
     saveData( C_OAUTH_SECRET,      getDetailOrThrow(C_OAUTH_SECRET, profileNode));
    
  }

  
  /**
   * Retrieves detail from the XML node or throws exception if 
   * subnode with such name doesn't exist.
   * @return String with the value of the subnode.
   * @throws ConfigParsingException
   */
  private String getDetailOrThrow(String key, XML node) throws ConfigParsingException
  {
      XML[] children = node.getChildren(key);
      if ( children == null){
         throw new ConfigParsingException("node <" + node.getName() + "> doesn't have subnode <" + key +">");
      }
      
      if ( children.length != 1 ){
         throw new ConfigParsingException("node <" + node.getName() + "> has diffrent value than 1 (value:" + children.length + 
                                          "of subnodes <" + key + ">");
      }
      
      return children[0].getContent(); // get content value of the subnode
  }
  
  /**
   *  This is fluentizer and abstract way of saying that i wnat to 
   * save some data.
   */
  private void saveData( String key, String value){
     mMap.put(key, value);
  }
  
  
  /**
   * Returns data associated with the key, or returns null in case
   * there's no such value.
   */
  private String getData(String key){
    if ( mMap.containsKey(key) ){
       return mMap.get(key);
    }
    return null;
  }
  
  
  
  
  /*********************************
   *  Below are public getters for the configuration.
   *  If all the flow of methods works fine,
   * they will always returns some value.
   * However may we have incomplete set of configuration
   * values set - they may return null.
   *********************************/
  public String getUserToken(){
    return getData(C_CONSUMER_KEY);
  }
  
  public String getUserSecret(){
     return getData(C_CONSUMER_SECRET);
  }
  
  public String getOAuthToken(){
     return getData(C_OAUTH_TOKEN);
  }
  
  public String getOAuthSecret(){
     return getData(C_OAUTH_SECRET);
  }
  
  
  
    
  /**
   * Returns Map<String,String> with the twitter configuration 
   * data like (APP_KEY, APP_SECRET) and all this stuff.
   */
    public Map<String,String> getTwitterConfiguration(){
    Map<String, String> configMap = new HashMap<String, String>();
    String[] keys = { C_OAUTH_SECRET, C_OAUTH_TOKEN, C_CONSUMER_SECRET, C_CONSUMER_KEY };
    for( String key : keys){
       configMap.put(key, getData(key));
    }
    return configMap;
  }
   
  /**
   * Returns configuration status.
   */
  @Override
  public String toString(){
     return "LibConfig data and loading status: " + mMap.toString();
  }
}