package experiment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import processing.data.StringList;

/**
 * Let's declare some vars here, so that we can be sure
 * that the variables are not forgotten.
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
class TestBase {
    StringList fileNames = new StringList();
    StringList search = new StringList();
    StringList filesListed = new StringList();
    FileWriter songList;
    FileWriter songMeta;
    String[] musicLocations;
    int musicLoaded;
    
    int searchNumber;
    
    FileReader songListReader;
    File music;
    
    String dataPath(String s){
        throw new UnsupportedOperationException("this is just dummy stub");
    }
    
    
    
    FileWriter createWriter(String filePath){
         throw new UnsupportedOperationException("this is just dummy stub");
    }
    
    FileReader createReader(String reader){
            throw new UnsupportedOperationException("this is just dummy stub");
    }

/**
 * Scans directory 'dir' for the files complying with 'filter' and adds
 * their absolute path (ie "c:\\tmp\\blabla\\file.ext" to fileList.
 * @param filter    valid filter
 * @param dir       directory with files.
 * @param fileList  already instantiated stringList. Items will be appended.
 */   
void addFilteredFilenamesToStringList(FilenameFilter filter, File dir, StringList fileList){
        String[] foundFiles = dir.list(filter);
        for(String file : foundFiles){
            String fullFilepath = dir.getAbsolutePath() + File.separator + file; 
            fileList.append(fullFilepath);
        }
}
    


String[] loadStrings(String filename){
    throw new UnsupportedOperationException("just dummy stub");
}
    
}
