package org.twitshot.helper;
import processing.core.*;
import processing.event.*;
import java.util.*;
/**
 * This class just encapsulates operations with the keys. Fluentizes access to them from my library code.
 */
public class MouseManager extends AbstractLibraryHelper
{
   
   private Queue<MouseEvent> mMouseEventQueue = new LinkedList<MouseEvent>();
   
   public MouseManager(PApplet parent){
      super(parent);
   }
   
   /**
    * These may be happening also between the animation thread, 
    * because even capturing is happening also between the threads.
    */
   public void mouseEvent(MouseEvent evt){
       // actually i want to save the event.
      mMouseEventQueue.add(evt);
   }
   
}