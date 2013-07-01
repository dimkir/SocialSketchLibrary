/**
 * This is just abstract superclass which all library-helpers are inheriting.
 * Incuim it only obligates to implements toString();
 */
abstract class AbstractLibraryHelper extends AbstractLibraryBase // incuim the base only saves .parent and defines println()
{
  private PApplet mParent; 
   
  
  AbstractLibraryHelper(PApplet parent){
    if ( parent == null ){
       throw new IllegalParameterException("When initializing library Parent cannot be null");
    }
    
     mParent = parent;
  }
  
  protected PApplet sketch(){
     return mParent;
  }
  
  protected PApplet parent(){
     return mParent;
  }
  
   @Override
   public void toString(){
      // todo: if we don't override, then the client may be calling toString and may be failing.. haha
      throw new RuntimeException("The abstract library helper: toString() should be OBLIGATORY overridden by the subclass");
   }
   
   
   protected void log(String s){
      mParent.println(s);
   }
  
}