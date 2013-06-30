javac -source 1.6 -target 1.6 -d . -classpath d:\Dropbox\shared.java.lib\processing64\core.jar libraryexample/*.java
jar -cf libraryexample.jar libraryexample
copy libraryexample.jar d:\Dropbox\Processing\libraries\libraryexample\library\libraryexample.jar
pause