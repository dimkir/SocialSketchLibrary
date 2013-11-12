/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package experiment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
class Bos {
    
        
        
    
    public static void main(String[] args){
        System.out.println("Starting main");
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BufferedOutputStream bos  = new BufferedOutputStream(os);
            writeBytesToStream(bos, 1024);
            
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            //typeInputStream(is);
            
            
            saveInputStreamToFile("c:\\tmp\\output_stream.txt", is);
            
            
        } catch (IOException ex) {
            Logger.getLogger(Bos.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        
    }

    private static void writeBytesToStream(BufferedOutputStream bos, int countBytes) throws IOException {
        for(int i=  0 ; i < countBytes ; i++){
            bos.write(128);
        }
        bos.flush();
        
    }

    /**
     * Types to console contents of the input stream.
     * @param is 
     */
    private static void typeInputStream(InputStream is) throws IOException {
        int b;
        while ( ( b = is.read() ) != -1 ){
            System.out.print(b + ", ");
        }
    }
    
    

/**
* Saves input stream to given filename.
* @return success
*/
static boolean  saveInputStreamToFile(String filename, InputStream is) throws IOException{
    File f = new File(filename);
    OutputStream os = new FileOutputStream(f);
    int b;
    while ( ( b = is.read() ) != -1 ){
        os.write(b);
    }
    os.flush();
    os.close();
    return true;
}     
}
