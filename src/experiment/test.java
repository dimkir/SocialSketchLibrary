package experiment;
import java.io.File;
import java.io.FilenameFilter;
/**
 *
 * @author Dimitry Alhambra <dimitry@languagekings.com>
 */
class test extends TestBase {
//
//void updateFiles() {
//  deleteFileFromDataDirIfExists("SongList.txt");
//  deleteFileFromDataDirIfExists("SongMeta.txt");
//
//  songList = createWriter(dataPath("SongList.txt"));
//  songMeta = createWriter(dataPath("SongMeta.txt"));
//
//  musicLocations = loadStrings(dataPath("Music Locations.txt"));
//
//  fileNames.clear();
//  search.clear();
//  search.append(musicLocations);  // why do we append our locations to it?
//  filesListed.clear();
//
//  File musicFileDir = new File(dataPath("/musicFiles"));
//
//  addFilteredFilenamesToStringList(jpgFilterwav, musicFileDir, fileNames);  
//  addFilteredFilenamesToStringList(jpgFiltermp3, musicFileDir, fileNames);
//  addFilteredFilenamesToStringList(jpgFilteraiff, musicFileDir, fileNames);                
//  addFilteredFilenamesToStringList(jpgFilterau, musicFileDir, fileNames);
//  addFilteredFilenamesToStringList(jpgFiltersnd, musicFileDir, fileNames);
//
//
//
//  // what was musicLocations ??
//  // that's the list of pathnames 
//  // which are loaded from file. obviuosly this is 
//  // the other locations where the stuff is.
//  
//  // is it array? it's String[]   
//  // if there's no music locations? How come there's none - if we loaded them from file?
//
//   scanMusicLocations(locations);
//    
//  // now we're saving something to song list.    
//  // printing the number of songs available
//    
//    // this is loop which just writes filenames to songList (via songWriter)
//                        searchNumber = 0;
//                        println("Number of Songs Available: " + fileNames.size());
//                        while (searchNumber < fileNames.size ()) {
//                          songList.println(fileNames.get(searchNumber));
//                          fileNames.remove(searchNumber);
//                        }
//                        songList.println("END");
//
//                        fileNames.clear();
//                        songList.flush();
//                        songList.close();
//
//
//  // this part reads SongList.txt  (which was created?                        
//  searchNumber = 0;
//  songListReader = createReader(dataPath("SongList.txt"));
//  while (true) {
//                if (songListReader.readLine().equals("END")) {
//                  break;
//                } 
//                else {
//                  player = null;
//                  player = minim.loadFile(songListReader.readLine(), 340);
//                  meta = null;
//                  meta = player.getMetaData();
//                  println(meta.title() + "," + meta.album() + "," + meta.author() + "," + meta.length());
//                  songMeta.println(meta.title() + "," + meta.album() + "," + meta.author() + "," + meta.length());
//                  searchNumber++;
//                }
//  }
//  println("Got Meta Data!");
//  println(metaData.array());
//}
//
//
//
///**
// * Deletes file from data directory.
// * @param filePath should be relative path to filename
// * in data directory. Eg. "myfile.txt" or "myfile.mp3" or "au_files\\music.au"
// */
//void deleteFileFromDataDirIfExists(String filePath) {
//  File f = new File(dataPath(filePath));
//  if ( f.exists() ) {
//    f.delete();
//  }
//}
//
//
///**
// * Scans directory for music filenames (mp3, au, wav... etc)
// * and adds their full path to the fileNames collection
// *
// */
//void appendToCollectionAllMusicFilesFromDir(File musicDir, SomeCollection fileNames)
//{
//  FilenameFilter[] musicFileFilters = generateMusicFileFilters(); 
//
//  for (FilenameFilter ff : musicFileFilters) {
//    addFilteredFilenamesToStringList(ff, musicDir, fileNames);
//  }
//}
//
//
//
///**
// * Just convenience method, which returns all our music file filters.
// * @return array of filtes. Can be used in foreach statement.
// */
//FilenameFilter[] generateMusicFileFilters() {
//  // first we're gonna prepare array of music file extensions 
//  String[] musicFileExtensions = { 
//    "wav", "mp3", "au"
//  }; 
//  // TODO: add all supported music files.
//  FilenameFilter[] musicFileFiters = createFileFiltersFromStringArray(musicFileExtensions);
//  return musicFileFiters;
//}
//
//
//
///**
//* This method creates array of FilenameFilter s which we can later 
//* use to filter contents of the directory. Filters will be case-insensitive.
//* @param allowedExtensions is string array of extensions, eg. "wav", "mp3".
//* <pre>
//* USAGE:
//* FilenameFilter[] filters = createFiltersFromStringArray(new String[] { "mp3" , "wav" });
//* or 
//* String[] myAllowedExtensions = { "mp3", "wav", "au" };
//* FilenameFilter[] filters = createFiltersFromStringArray(myAllowedExtensions);
//* </pre>
//*/
//FilenameFilter[] createFileFiltersFromStringArray(String[] allowedExtensions){
//  
//  FilenameFilter[] resultingFilters = new FilenameFilter[allowedExtensions.length];
//  
//  for(String extension : allowedExtensions ){
//       final String ext  = extension;
//        FilenameFilter filter = new FilenameFilter() {
//                public boolean accept(File dir, String name) {
//                      return name.toLowerCase().endsWith("." + ext);
//                }
//            };
//  }// for  
//  
//  return resultingFilters;
//}// createFiltersFromStringArray
//
//
//
//
//void scanMusicLocations(String[] locs){
//    if ( musicLocations.length == 0 ){ 
//        return;
//    }
//    
//    // for( String location : musicLocations)
//    for(String location : search){
//        music = new File(location);
//        if (music.isDirectory() == false) {
//            // remove from queue
//            search.remove(musicLoaded);
//            skip = 1;
//        }
//        // we didn't skip it.
//        if (skip == 0) {
//            filesListed.clear();
//            filesListed.append(music.list());
//
//            appendToCollectionAllMusicFilesFromDir(music, fileNames);
//
//            // we add directories from filesListed to search somewhere
//            for (int i = 0; i < filesListed.size(); i++) {
//                File sFile = new File(filesListed.get(i));
//                if (sFile.isDirectory() == true) {
//                    String absolutePath = music.getAbsolutePath() + "/" + filesListed.get(i);
//                    search.append(absolutePath);
//                }
//            }
//            // what is this?
//            musicLoaded++;
//        } // if skip
//        skip = 0;
//    }//
//
//                                // remove rubbish substring. 
//                                // Actually as we will fix interating over StringList - we can skip this part.
//                //                                for(int i = fileNames.size() -1 ; i >= 0; i--){
//                //                                     String fname = fileNames.get(i);
//                //                                     if ( fname.contains("[Ljava.lang.String;") ){ // what's the hell is that?
//                //                                         fileNames.remove(i);
//                //                                     }
//                //                                }
//  
//}
    
}
