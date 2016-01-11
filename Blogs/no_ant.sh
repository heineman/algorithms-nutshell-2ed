#!/bin/bash -x
#
# Use this bash script to compile all Blog files.
VERSION=2.0

# It uses existing JARs in JUnit-4.0 directory (version 4.12 retrieved
# on Tue Feb 17 16:00:02 2015)

# Will be utilized within 'src' directory, hence ../../..
JUNIT=../../../junit4.0/hamcrest-core-1.3.jar:../../../junit4.0/junit-4.12.jar


# Note: The code still requires a JDK 1.6-compliant javac compiler.
# -----------------------------------------------------------------

which javac > /dev/null
if [ $? -eq 1 ]
then
   exit 1
fi

# determine path separator to use
# -------------------------------
echo "public class Sep { public static void main (String args[]) { System.out.print (java.io.File.pathSeparatorChar);}}" >> Sep.java
javac Sep.java
SEP=`java Sep`
rm -f Sep.java Sep.class

# bring in libraries (will be utilized within 'src' hence ../..)
export CLASSPATH=$JUNIT:../../dist/ADK-2.0.jar

# Create JAR file
# ---------------
if [ ! -d dist ]
then
  mkdir dist  
fi

# compile all blog java source files
(cd src; javac `find . -name "*.java"`)

# Make the various JAR files
# --------------------------------------------------
(cd src; echo $PWD; jar cf ../dist/ADK-$VERSION-Blog.jar `find . -name "*.class"`; rm `find . -name "*.class"`)

# -----------------------
rm -f `find . -name "*.class"`

