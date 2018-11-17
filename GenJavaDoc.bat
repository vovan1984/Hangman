dir /b /s *.java > _file_javadoc.lst
javadoc -author -version -html5 -encoding UTF-8 -d doc @_file_javadoc.lst
del /f _file_javadoc.lst