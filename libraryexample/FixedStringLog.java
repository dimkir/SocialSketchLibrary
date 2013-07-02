package libraryexample;
/**
 * Implements concept of FixedStringLog (log of last N String messages)
 * 
 * It has few parameters: 
 * size()  : amount of strings available for display
 * maxSize() : maximum amount of strings which will be kept (before overwriting happens)
 */
class FixedStringLog
{
  private String[] mStringar;
  /**
   * The way we're going to operate - is that we're going to add new itemns
   * and they will be overwriting the old ones.
   */
  private int mPointer;
  private int mLength;
  
    
  /**
   * FixedString log with maximum given amount of strings.
   * @param maxSize maximum amout of strings allowed for store
   *        should be above 0
   */
  FixedStringLog(int maxSize){
     mStringar = new String[maxSize];
     mPointer = 0;
     mLength = 0;
  }
    
  int size(){
     return mLength;
  }
  
  int capacity(){
     return mStringar.length;
  }
  
  /**
   * Returns last available index (from the client's view)
   * @return -1 in case there's no available index.
   */
  int lastIndex(){
     return mLength - 1;
  }
  
  
  /**
   * Returns string value of given index
   * @throws IllegalArgumetnException if value is not correcgt
   */
  String get(int i){
    if ( i > lastIndex() || i < 0  ){
       throw new IllegalArgumentException("Index should be between 0 and " + lastIndex() + " supplied:" + i );
    }
    
    int index = mPointer + i;
    index %= mStringar.length; // we wrap the value if it is around the array
    return mStringar[index]; 
     
  }
  
  /**
   * Adds message.
   * Technically it may overwrite the old message.
   */
  void put(String s){
       // get next pointer
       int nextPointer = mPointer + mLength;
       nextPointer %= mStringar.length;
       
       if ( nextPointer == mPointer){
          mPointer++; // we will overwrite the oldest on the next step
       }
       //
       mStringar[nextPointer] = s;
       if ( mLength < mStringar.length ){
          mLength++;
       }
  }

    /**
     * This outputs status and contents of the FSL
     * @return 
     */
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("Size(): " + size());
        for(int i = 0 ; i < size(); i++){
             sb.append("(" + i + "): "  + get(i));
        }
        return sb.toString();
        
    }
  
  
  
  
}