#!/bin/bash
#
# Use this bash script to compile all files if you don't have ant. This
# is meant as a poor substitute since it only compiles the code and
# tries to launch the JUnit test cases that it can find in `Tests`
VERSION=2.0

# It uses existing JARs in JUnit-4.0 directory (version 4.12 retrieved
# on Tue Feb 17 16:00:02 2015)

# Note: The code still requires a JDK 1.6-compliant javac compiler.
# -----------------------------------------------------------------

# Adjust as needed to your JUnit location. If you prefer not to runt
# JUnit test then comment out this line
JUNIT=$PWD/../junit4.0/hamcrest-core-1.3.jar:$PWD/../junit4.0/junit-4.12.jar

which javac > /dev/null
if [ $? -eq 1 ]
then
   exit 1
fi

# Store for later
OLDCP=$CLASSPATH

# determine path separator to use
# -------------------------------
echo "public class Sep { public static void main (String args[]) { System.out.print (java.io.File.pathSeparatorChar);}}" >> Sep.java
javac Sep.java
SEP=`java Sep`
rm -f Sep.java Sep.class

# all compilation within the 'src' directories
# --------------------------------------------
DIRS=`echo JavaCode Examples Figures PerformanceTests`
CLASSPATH=.
for i in $DIRS
do
  CLASSPATH=../$i/src$SEP$CLASSPATH
done

export CLASSPATH
echo "CLASSPATH: $CLASSPATH"

for i in $DIRS
do
  echo "compiling $i..."
  cd $i
  javac `find src -name "*.java"`
  cd ..
done

# Create JAR file
# ---------------
if [ ! -d dist ]
then
  mkdir dist
fi

# Make the various JAR files
# --------------------------------------------------
(cd JavaCode/src; jar cf ../../dist/ADK-$VERSION.jar `find . -name "*.class"`; rm `find . -name "*.class"`)
cp dist/ADK-$VERSION.jar dist/ADK-$VERSION-ExamplesAndFigures.jar 

(cd Examples/src; jar uf ../../dist/ADK-$VERSION-ExamplesAndFigures.jar `find . -name "*.class"`; rm `find . -name "*.class"`)

(cd Figures/src; jar uf ../../dist/ADK-$VERSION-ExamplesAndFigures.jar `find . -name "*.class"`; rm `find . -name "*.class"`)

(cd PerformanceTests/src; jar cf ../../dist/ADK-$VERSION-PerformanceTests.jar `find . -name "*.class"`; rm `find . -name "*.class"`)


# Compile and Run JUnit test cases (if allowed)
# --------------------------------------------
if [ "x$JUNIT" == "x" ]
then
  echo "Done. Not compiling or running JUnit test cases"
  exit
fi

CLASSPATH=$OLDCP:$PWD/dist/ADK-$VERSION-ExamplesAndFigures.jar
CLASSPATH=$JUNIT:$CLASSPATH

# compile all test cases
echo "running JUnit test cases..."
cd Tests/tests
javac `find . -name "*Test.java"` > /dev/null

# Make sure we can run even in the Tests directory because some 
# test cases access the 'resources' directory.
CLASSPATH=$CLASSPATH:./tests

for i in `find . -name "*Test.class"`
do
  NAM=`echo $i | sed 's/.class$//' | sed 's+/+\.+g' | cut -c3-`

  # run each test in the Tests directory
  (cd ..; java org.junit.runner.JUnitCore $NAM 2>/dev/null 1>/dev/null)
  if [ $? -eq 1 ]
  then
     echo "FAILURE $NAM"
     exit
  else
     echo "SUCCESS $NAM"
  fi
done

# clean up all test cases
# -----------------------
rm -f `find . -name "*.class"`

