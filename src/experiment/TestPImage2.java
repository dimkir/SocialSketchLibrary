/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package experiment;

import org.twitshot.PImage2;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
class TestPImage2 {

    public TestPImage2() {
        try {
            int w = 200;
            int h = 100;
            int sz = w * h;
            PImage2 img2 = new PImage2(w, h, PImage2.C_PATTERN_BLACK_WHITE);
            
//            for(int i = 0 ; i<sz ; i++){
//                    img2.pixels[i] = 0;
//            }
            
            ByteArrayOutputStream byteArrOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream buffOutputStream  =  new BufferedOutputStream(byteArrOutputStream);
            
            img2.saveImageToStream("png", buffOutputStream);  // throws exception
            
            
            InputStream is = new ByteArrayInputStream(byteArrOutputStream.toByteArray());
                Bos.saveInputStreamToFile("d:\\tmp\\out.png", is);

        
        
        
        } catch (IOException ex) {
            Logger.getLogger(TestPImage2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    
    
    
    public static void main(String[] args) {
        new TestPImage2();
    }
}
