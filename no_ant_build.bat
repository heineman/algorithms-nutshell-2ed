@echo Off
@rem Use this BAT script to compile all files if you don't have ant. This
@rem is meant as a poor substitute since it only compiles the code, but 
@rem doesn't run any of the JUnit test cases. Note: We even exclude any
@rem dependence upon JUnit (http://www.junit.org) so the test cases are 
@rem not even compiled. 
@rem
@rem
@rem Note: You need to modify the following statement for proper Java version
@rem Note: The code still requires a JDK 1.6-compliant javac compiler.
set JHOME=c:\Progra~1\Java\jdk1.7.0_79

@rem
@rem Note: This script was tested on Microsoft Windows 7 Enterprise
@rem -----------------------------------------------------------------

@rem Update as appropriate for your own location. Must at least be
@rem a JDK 1.6 installation
@rem ---------------------------------------------------------------
If Not Exist %JHOME% Goto :NoJava
set PATH=%JHOME%\bin;%PATH%

@rem determine path separator to use
@rem -------------------------------
echo public class Sep { public static void main (String args[]) { System.out.print (java.io.File.pathSeparatorChar);}} > Sep.java
javac Sep.java
java Sep > Sep.output
set /p SEP= < Sep.output
del Sep.java Sep.class Sep.output

@rem all compilation within the 'src' directories
@rem --------------------------------------------
set CLASSPATH=..\..\JavaCode\src\%SEP%..\..\Examples\src\%SEP%..\..\Figures\src

@echo Off
echo compiling JavaCode (this may take a while...)
cd JavaCode\src
for /f %%a IN ('dir /A:D /b /s /x') do If Exist %%a\*.java javac %%a\*.java
cd ..\..

echo compiling Examples (this may take a while...)
cd Examples\src
for /f %%a IN ('dir /A:D /b /s /x') do If Exist %%a\*.java javac %%a\*.java
cd ..\..

echo compiling Figures (this may take a while...)
cd Figures\src
for /f %%a IN ('dir /A:D /b /s /x') do If Exist %%a\*.java javac %%a\*.java
cd ..\..

@rem Create JAR file
@rem ---------------
if Not Exist dist mkdir dist

@rem Make the various JAR files (must create before updating. Randomly
@rem select one of these classes to use to create JAR and then update rest.
@rem ----------------------------------------------------------------------
chdir > chdir.output
set /p HERE= < chdir.output
del chdir.output
set JARFILE=%HERE%\dist\ADK-2.0.jar

cd JavaCode\src

@rem Place within location KNOWN to be in CLASSPATH so it can be retrieved.
@rem ======================================================================
echo public class SS { public static void main (String args[]) { System.out.print (args[1].substring(1+args[0].length()));}} > SS.java
javac SS.java

@echo Building JavaCode jar files...
jar cf %JARFILE% algs\model\IPoint.class
for /f %%a IN ('dir /A:D /b /s /x') do If Exist %%a\*.class Call :Import JavaCode\src %%a 
cd ..\..
copy dist\ADK-2.0.jar dist\ADK-2.0-ExamplesAndFigures.jar

@rem now place within this JAR file
set JARFILE=%HERE%\dist\ADK-2.0-ExamplesAndFigures.jar

@echo Building Example jar files...
cd Examples\src
for /f %%a IN ('dir /A:D /b /s /x') do If Exist %%a\*.class Call :Import Examples\src %%a 
cd ..\..

@echo Building Figure jar files...
cd Figures\src
for /f %%a IN ('dir /A:D /b /s /x') do If Exist %%a\*.class Call :Import Figures\src %%a 
cd ..\..

@rem delete helper class
@rem ===================
del JavaCode\src\SS.java JavaCode\src\SS.class

echo DONE!
echo created JAR files in dist\ directory
echo validating distribution...

java -cp %JARFILE% algs.chapter2.example1.Main > NUL
GOTO:EOF

@rem Need to ensure RELATIVE path names do the trick.
@rem ~1 is the source folder (i.e., JavaCode\src) while ~2 is the dir with *.class to be added.
@rem ========================================
:Import
set FOLDER=%~1
set DIRNAME=%~2

java SS %HERE%\%FOLDER% %DIRNAME% > reduced.output
set /p RED= < reduced.output

jar uf %JARFILE% %RED%\*.class
GOTO:EOF

:NoJava
  echo You must edit the "no_ant_build.bat" script to set JHOME to
  echo a valid JDK of at least 1.6. Current value is %JHOME%
  pause
  goto: EOF

:NotCommand
  echo This script must be run within Command.com
  Call no_ant
  Goto: EOF
