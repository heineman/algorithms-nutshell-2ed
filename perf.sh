#!/bin/bash
#
# Launch a specific performance *Main file from PerformanceTests/
#
#   Usage: ./perf.sh JavaMain
#
# Where JavaMain is the name of one of the Main classes from within PerformanceTests/

VERSION=2.0

# Note: The code still requires a JDK 1.6-compliant javac compiler.
# -----------------------------------------------------------------

CLASSPATH=$PWD/dist/ADK-$VERSION-PerformanceTests.jar:$PWD/dist/ADK-$VERSION.jar
CLASSPATH=$JUNIT:$CLASSPATH

export CLASSPATH

java $1 
