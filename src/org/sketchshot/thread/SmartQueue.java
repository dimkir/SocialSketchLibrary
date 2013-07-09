package org.sketchshot.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Smart queue can accumulate objects of any type,
 * provided they implement IPostponable interface. 
 * This interface should allow the smartqueue to decide
 * if the item is already ready to be popped out, or it still
 * needs to sit somewhere in the back and wait.
 * 
 * TODO: this is very simple and dumb and inefficient implementation, 
 * but it's here to be simple.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class SmartQueue<T extends IPostponable> {
    
    
    /**
     * This is queue of elements, 
     * The last element is the element is the element which should
     * be pushed off the queue the first.
     * So they're sorted in reverse order of their time.
     */
    private ArrayList<T> mElements;
    
    // now this comparable is to sort them in reverse order.
    private ReverseComparePostponables ppComparator = new ReverseComparePostponables();
    
    SmartQueue(){
        mElements = new ArrayList<T>();
    }
    
    /**
     * Sorts elements according to their time
     */
    private void sortElements(){
        Collections.sort(mElements, ppComparator);
    }
    
    /**
     * Pushes item into the queue
     * @param item 
     */
    void push(T item){
        mElements.add(item);
        // kinda inefficient, but we don't care now.
        sortElements();
    }

    
    /**
     * This should return all the elements of the queue and time to action
     * (how much time (in seconds) is left till their action,
     * this way we will have this queue information in human-readable form.
     * 
     * @return 
     */
    @Override
    public String toString() {
        // TODO: make string of elements to more human readable form.
        StringBuilder sb = new StringBuilder();
        for( IPostponable t : mElements ){
            sb.append('[');
            sb.append(t.actionTime());
            sb.append("], ");
        }
        return sb.toString();
    }
    
    
    
    
    /**
     * Returns number of items waiting on queue.
     * Maybe the action time of the first element
     * is far away in the future, but 
     * here we still display amount of items.
     * @return 
     */
    int itemsOnQueue(){
        return mElements.size();
    }

    /**
     * Pops the element if it is available.
     * Pre-condition: assumes that array is properly sorted,
     * meaning that last element will be the one
     * with the shortest time.
     * 
     * @return 
     */
    T pop(){
        if ( mElements.size() < 1 ){
            return null;
        }
        T lastElement  = last(mElements);
        
        
        if ( lastElement.actionTime() > millis() ){
            // not ready yet.
            return null;
        }
        mElements.remove(mElements.size() -1);
        return lastElement;
    }
    
    
    /**
     * Takes non-empty arlis.
     * @param arlis
     * @return 
     */
    T last(ArrayList<T> arlis){
        return arlis.get(arlis.size() -1);
    }
    
    
    // methods
    
    
    // we want to insert something into the queue.
    
    // and we want to pop things off the queue.
    
    
    // what's the usual application of the smartqueue
    /*     
        create SmartQueue
         
        insert some item??
        
        loop continuously to fetch the items,
        sometimes there will be something avialble,
        sometimes not. 
        Some items are avaialble instantly, some items where
        postponing time was specified: will not be available readily.
         
        Shoud it use priority queue under the hood?
     */

    
    /**
     * Returns system time in millis.
     * @return 
     */
    private long millis() {
        return System.currentTimeMillis();
                
    }
    
    
    
    
    class ReverseComparePostponables implements Comparator<T>{

        /**
         * Compares two of the objects. Inside of SmartQueue
         * the type T is defined as <T extends IPosponable> so 
         * we can assume it implements interface. 
         * @param nailed   which one is nailed? and which one is rotating element?
         * @param rotating   this one is rotating element.
         * @return 
         */
        @Override
        public int compare(T nailed, T rotating) {
            // anyone with 0 time will always appear BEFORE any specific time.
            // thus this comparator will work well on both
            // 0 items and items with set time.
            
            // !!!!! THIS WILL SORT THEM IN REVERSE ORDER!!!!
            // 
            if ( rotating.actionTime() < nailed.actionTime() ){
                return 1;
            }
            if ( rotating.actionTime() > nailed.actionTime() ){
                return -1;
            }
            return 0;
        }

        
    }
    
}
