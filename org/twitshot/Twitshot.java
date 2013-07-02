package org.twitshot;
import processing.core.*;
import processing.event.*;

/**
 * TWITSHOT (TODO: to change package and classname to this package
 * This is main class of library which allows to take screenshots
 * of your sketches and tweet them
 */
public class Twitshot  extends AbstractLibraryBase // the AbstractBase just holds some basic methods like prinltn() and other convenience methods. As well as protected field .parent)
{

  /**
   * Coordinates of where FixedLog of the twitter status will be shown
   */
  private static final int C_STRING_LOG_X   = 20;
  private static final int C_STRING_LOG_Y   = 40;
  
  /**
   * Generally, I encapsulated all of the functionality related to some domains
   * into those classes.
   * They're named a bit weirdly, but still explain what they're doing.
   */
  private ScreenshotManager screenshotManager; // manages saving of the screen for further tweeting.
  
  private LibConfig mLibConfig;                // here we load and keep parameters loaded from the ? config file?
  private FontBoss fontBoss;
  private KeyManager keyManager;               // handles keystrokes
  private MouseManager mouseManager;           // handles mouse movements

  private LibraryDrawing libraryDrawing;       // handles drawing (basic concepts)
  private Menu menu;                           // more appropriate name should be "gui"
  private TweetDirector tweetDirector;     // offers convenience twitter messaging API
  
  
  
  
  /**
   * Inits library and loads twitter credentials from the xmlConfigFile {@link xmlConfigFileSpec}.
   * In case there was a failure loading: 
   * @throws LibraryLoadException()
   */
  public Twitshot(PApplet parent, String xmlConfigFile) throws LibraryLoadException
  {
     super(parent);
      // we first want to try to load file. Because if this fails - 
     // we then shouldn't proceed with method registration and further initialization.
     mLibConfig = new LibConfig(parent, xmlConfigFile); // throws ConfigParsingException::LibraryLoadException
     
     initLibrary(parent); // here we properly init library and print the status.
     
     // if we're here, that means that background thread is running. We need to register "stop()" so that 
     // we stop that thread on exit
     registerMethodsWithParent(parent);    // this one should be NOT FAILABLE.
                                // here we register events, when we know that 
                                // there will be no failure during initialization of library.
                                // but if we throw???
                                // if we throw - doesn't matter: we throw and crash and burn.??? (what if we dynamically load library?)
                                // not in this scenario? (actually here we load libraries in the sketch in setup(), so technically
                                // we may want to proceed loading even if the library has failed.
     displayInitStatus();
  }

  
  
  /**
   * Failable: Initializes all the "helpers" (like FontBoss and TweetDirector). Launches thread for TweetDirector.
   * Contract:
   *  Loads helpers or throws.
   * can it throw?
   * @throws TwitterDirectorEx in case error initializing twitterDirector. And MAYBE(?) some other exceptions?
   */
  private final void initLibrary(PApplet parent) throws TweetDirectorEx
  {
    // *** init all our convenience objects ***
    fontBoss = new FontBoss(parent);
    libraryDrawing = new LibraryDrawing(parent, fontBoss); // what does this library do : draws lines and shit and framerate
    menu = new Menu(parent, libraryDrawing);
    keyManager = new KeyManager(parent); // ?? should it get this parameter?
    mouseManager = new MouseManager(parent);
    
    tweetDirector = new TweetDirector(parent, mLibConfig.getTwitterConfiguration() );  // can throw TwitterDirectorEx
                                                                                       //if twitterConfiguration is NULL
    
    // so if we got here - we have start.
    tweetDirector.start(); // starts the background tweeting thread (may it be necessary). 
    
    //  once we get to here, it means there were no errors in the failables from above.
      // we only register ourselves in case there was failre from the classes above (all failables first).
  }
  

  
  /**
   * Displays library init(load) status. What configuration was loaded, whether font was loaded and so on.
   * Assumes this.parent is already set.
   */
  private void displayInitStatus()
  {
     println("Started the library");
     println("Font loaded is: " + fontBoss.toString());
     println("Loaded config: " + mLibConfig.toString());
     //TODO: add other helper statuses.
  }


  
  /**
   * Registers library methods to be callbacks from the PApplet.
   */
  private void registerMethodsWithParent(PApplet parent){
    parent.registerMethod("dispose", this);
    parent.registerMethod("pre", this);
    parent.registerMethod("draw", this);
    parent.registerMethod("post", this);         // maybe we don't need this as it's not run
    
    parent.registerMethod("keyEvent", this);    // should they be camel case?
    parent.registerMethod("mouseEvent", this);  // should they be camel case?
    
    
    
  }
  

  /**
   * This is pure delgate method. 
   * Here we just pass the event to the appropriate manager.
   */
  public void keyEvent(KeyEvent evt){
     keyManager.keyEvent(evt);
  }
  
  
  /**
   * This is a pure delgate method as well, where we just pass the event to the appropriate manger.
   */
  public void mouseEvent(MouseEvent evt){
    // here we catch the event and must pass it to the mouse manager?
     mouseManager.mouseEvent(evt);
  }
  
  
  

  public void dispose() {
    // Anything in here will be called automatically when 
    // the parent sketch shuts down. For instance, this might
    // shut down a thread used by this library.
    println("Dispose method called for library");
    // we don't use any threads (aren't we? and what about tweetDirector?)
    tweetDirector.shutdown();
  }
  
  
  /**
   * This method is called BEFORE youre sketches draw(), but already allowing drawing.
   * We can use it for example to set something on the background.
   * Remember that this method will be called EVERY FRAME (which depending on your computer speed may be 30 or even 90 frames per second!)
   * DO NOT use println() in this method or other slow calculations, as those heavy calculations may slow down your sketch a lot.
   */
  public void pre(){
    
    libraryDrawing.drawRedRectangle80();
  }
  
  /**
   * Is called after your sketches draw() method.
   * From this method it is possible to draw (remember to draw through the "parent".
   */
  public void draw(){
    screenshotManager.allocateMemoryIfNotAllocatedFor(mParent.g); // just makes sure that screenshot can work
    
    if ( menu.isDisplayedFlag() ){
       // *************** DISPLAY MENU *********************
      menu.draw();
      // here we handle keyboard whilst menu opened
      if ( keyManager.pressedMenuExitKey() ){
        menu.setDisplayedFlag(false);
      }
      else if ( keyManager.pressedTweetKey() ){
        PImage picture = screenshotManager.getScreenShotOriginalSize();
        tweetDirector.tweetTheMessage("my processing sketch draws cool things ", picture);
        menu.setDisplayedFlag(false);
      }
    }
    else{
      // *************** REGULAR SKETCH RUNNING HANDLING *********************
      // here we handle keyboard whilst menu closed
      if ( keyManager.pressedMenuOpenKey() ){
         screenshotManager.saveImageFrom(mParent.g); // saves to some storage
         menu.setDisplayedFlag(true);
      }
    }
    
    
    // ************** STANDARD OVERLAYS **************************
    libraryDrawing.drawLineGrid();
    libraryDrawing.displayFrameRate();
    libraryDrawing.displayStringLog( tweetDirector.getLogObject() , C_STRING_LOG_X, C_STRING_LOG_Y );
    

  }
  
  /**
   *  Here we can't draw. This is already after draw() and endDraw() has called. ( we can use it I don't know for what)
   */
  public void post(){
     // i don't even know why i registered this.
  }
  
}