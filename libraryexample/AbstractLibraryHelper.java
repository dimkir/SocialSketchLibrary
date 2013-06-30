/**
 * This is just abstract superclass which all library-helpers are inheriting.
 * Incuim it only obligates to implements toString();
 */
abstract class AbstractLibraryHelper extends AbstractLibraryBase // incuim the base only saves .parent and defines println()
{
  
   @Override
   public void toString(){
      throw new RuntimeException("The abstract library helper: toString() should be OBLIGATORY overridden by the subclass");
   }
  
}