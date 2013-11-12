package org.sketchshot.utils;

import org.sketchshot.utils.FixedStringLog;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 * This is sample test suite for class FixedStringLogTest.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
public class FixedStringLogTest extends TestCase {
    
    public FixedStringLogTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of size method, of class FixedStringLog.
     * Here we test that the size of the FSL grows proportionately to the 
     * added items and as well that it stops when max-size is reached.
     */
    public void testSize() {
        System.out.println("** FixedStringLogText: >>size");
        int maxSize = 3;
        FixedStringLog instance = new FixedStringLog(maxSize);
        int actualSize = 0;
        // now to fill and overflow.
        for( int i = 0 ; i < maxSize + 10 ; i++ ){
            instance.put("Line " +  i);
            if ( i < maxSize ){
                actualSize++;
            }
            assertEquals(instance.size(), actualSize);
        }
        System.out.println(instance);
    }

    
    /**
     * Tests behaviour of the class when it's empty.
     */
    public void testGetEmpty(){
        FixedStringLog fsl = new FixedStringLog(10);
        try {
            String s = fsl.get(0);
        }
        catch ( IllegalArgumentException ilae){
            System.out.println("Caught: " + ilae.getMessage());
        }
    }
    
    
    public void testGetNegative(){
        FixedStringLog fsl = new FixedStringLog(10);
        try {
                String s = fsl.get(-1);
        }
        catch( IllegalStateException ilse){
            System.out.println("Caought: " + ilse.getMessage());
        }
    }
    
    public void testGetOutOfRangeLessThanCapacity(){
        FixedStringLog fsl = new FixedStringLog(10);
        populateFslWithRandomStrings(fsl, 5);
        
        fsl.get(6);
    }
    
    public void testGetOutOfRangeMoreThanCapacity(){
        FixedStringLog fsl = new FixedStringLog(10);
        populateFslWithRandomStrings(fsl, 15);
        fsl.get(20);
    }
    
    /**
     * Test of maxSize method, of class FixedStringLog.
     */
    public void testMaxSize() {
        System.out.println("maxSize");
        FixedStringLog instance = null;
        int expResult = 0;
        int result = instance.capacity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class FixedStringLog.
     * Testing get whilst sequentially adding items.
     */
    public void testGet() {
        // first we create sample string array, agains which we're going to compare
        // FSL contents
        int sampleSourceSz = 10;
        String[] sampleArray = createSampleStringArray(sampleSourceSz);

        
        int fslCapacity = 5;
        FixedStringLog fsl = new FixedStringLog(fslCapacity);
        // add incrementally and compare FSL with the corresponding interval in FSL

        for( int i = 0 ; i < sampleSourceSz ; i++){
            fsl.put(sampleArray[i]);
            // now we need to check if FSL contains correct strings (interval)
            // if we're below overflow of capacity, then it's easy: 
                    // just check interval from start to max capacity
            // invariant: index of elements which fit into capacity will always be
            // smaller then capacity
            if ( i < fslCapacity ){
                assertTrue(  checkFslMatchesInterval(fsl, sampleArray, 0, i) );
            }
            else{ // each of these inserts (where i >= fslCapacity) will result in overflow.
                assertTrue( checkFslMatchesInterval(fsl, sampleArray, i - fslCapacity + 1, i)  ) ;
                 
            }
        }
                
    }

    /**
     * Test of put method, of class FixedStringLog.
     */
    public void testPut() {
        System.out.println("put");
        String s = "";
        FixedStringLog instance = null;
        instance.put(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Checks whether FSL contents matches specified interval of strings.
     * Interval is specified via sampleArray and pointers to starting and ending indices of the interval,
     * namely iStart and iEnd.
     * @param fsl
     * @param sampleArray           array with sample data. What's it's size?? is it larget thatn capacity?
     * @param iStart    i0th index of interval
     * @param iEnd      last index of interval (yes last, not the one after like in .substring())
     * @return TRUE on contents being fine!
     */
    private boolean checkFslMatchesInterval(FixedStringLog fsl, String[] sampleArray, int iStart, int iEnd) {
        System.out.println("checkFslMatchesInterval(iStart:" + iStart + ", iEnd:" + iEnd + ")");
        // check size
        // calcualte size
        int sz = iEnd - iStart + 1; 
        
        if ( sz != fsl.size() ){
            return false;
        }
        
        // check matching interval
        int curInterval = iStart;  // pointer to current element in interval
        for(int i = 0 ; i < sz; i++){
            System.out.println("Comparing two strings: from sampleArray[" + curInterval +"] (" + sampleArray[curInterval] + ") vs fsl.get(" + i + ") (" + fsl.get(i) + ")");
            if ( !sampleArray[curInterval++].equals(fsl.get(i)) ){
                return false;
            }
        }
        return true;
    }

    /**
     * Just creates sample stringar of size sampleSourceSz with (different strings)
     * @param sampleSourceSz
     * @return 
     */
    private String[] createSampleStringArray(int sampleSourceSz) {
        String[] rez = new String[sampleSourceSz];
        for(int i = 0 ; i < rez.length ; i++){
            rez[i] = "String number " + i;
        }
        return rez;
    }

    /**
     * Populates with random stings
     * @param fsl
     * @param stringCount 
     */
    private void populateFslWithRandomStrings(FixedStringLog fsl, int stringCount) {
        String[] sampels = createSampleStringArray(stringCount);
        for(String s : sampels)
        {
            fsl.put(s);
        }
    }
}
