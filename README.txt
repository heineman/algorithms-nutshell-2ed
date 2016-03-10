Welcome
=======

Welcome to the Algorithm Development Kit, the associated code repository to
the second edition of the "Algorithms in a Nutshell" book published by
O'Reilly Media, Inc.

  http://shop.oreilly.com/product/0636920032885.do

For historical reasons, the 1st edition web page can be found here

  http://oreilly.com/catalog/9780596516246

This repository is freely available for you to use (as per the "Using Code
Examples" from the preface of the book. We quote:

  This book is here to help you get your job done. In general, if example
  code is offered with this book, you may use it in your programs and
  documentation. You do not need to contact us for permission unless you.re
  reproducing a significant portion of the code. For example, writing a
  program that uses several chunks of code from this book does not require
  permission. Selling or distributing a CD-ROM of examples from O.Reilly
  books does require permission. Answering a question by citing this book
  and quoting example code does not require permission. Incorporating a
  significant amount of example code from this book into your product.s
  documentation does require permission.

  We appreciate, but do not require, attribution. An attribution usually
  includes the title, author, publisher, and ISBN. For example: "Algorithms
  in a Nutshell, Second Edition by George T. Heineman, Gary Pollice, and
  Stanley Selkow. Copyright 2016 George Heineman, Gary Pollice and Stanley
  Selkow, 978-1-4919-4892-7.

Within this repository you will find the following directories:

  README.txt         This file
  Code               Implementation of algorithms using C/C++
  PerformanceTests   Tests to stress-test JavaCode
  Tests              JUnit tests cases for JavaCode
  dist               compiled JAR files for JavaCode
  Examples           Examples showing JavaCode in action
  Figures            Shows how to generate nearly all Book Figures
  JavaCode           Implementation of algorithms using Java
  no_ant.sh          Shell script to compile JavaCode and package dist
  no_ant_build.bat   Windows batch file that packages dist 
  build.xml          Ant buildfile to compile, test, and package JavaCode
  VERSION.txt        Version information about this Development Kit
  Makefile           Makefile to compile C/C++ code using gcc/g++


Primary Task
============

1. Make sure JAVA_HOME is set to your JDK installation. For example the
   following shows how to set JAVA_HOME using C-Shell:

     setenv JAVA_HOME /usr/local/jdk1.6.0_07

   Note that the code repository will not compile using JDK 1.5 because
   some code relies on the java.util.Arrays.copyOf() method which was
   introduced with the JDK 1.6; we also use "@Override" annotation tags for
   classes that implement an interface. Compiling the repository requires a
   JDK-1.6 compatible javac compiler. Download the latest JDK from
   [http://java.sun.com/javase]

   All code examples have been tested using the latest JDK 1.8.0_74
   release.

   Make sure that $JAVA_HOME/bin is in your PATH so the proper java and
   javac executables can be located.

2. Make sure you have a python installation of at least 2.7.6 otherwise
   the module structure will not be executable. In particular, we use
   conditional expressions (1 PEP 308) added to Python 2.5.  

   It is expected that you have numpy installed. Review the instructions
   at http://sourceforge.net/projects/numpy/
  
3. Make sure ant is in your PATH. This installation was tested with:

      Apache Ant version 1.7.1 compiled on June 27 2008

   Download the latest version from:

      http://ant.apache.org/
 
   If you don't have 'ant' available, you may be able to use the
   "no_ant.sh" script to compile all Java classes. Read that file for
   instructions on its use. If you are on a Microsoft XP Windows machine,
   you can launch the "no_ant.bat" batch file and it will provide
   instructions on how to build the code (provided you have an available
   JDK of version 1.6 or higher).

4. Make sure that JUnit is available.

   The ant build script assumes there is a directory ../junit4.0 which
   contains the JUnit installation as found from [http://www.junit.org].

   Note that this directory exists *above* the ADK source tree which means
   that we don't need to include these files with our release.

   We have tested the installation with both JUnit 4.0 and a more recent
   release, JUnit 4.12.  In all cases, the directory "junit4.0" is
   required (you can install the latest version of JUnit and simply
   create a symbolic link to that directory with the name "junit4.0").

   If your JUnit installation is in a different location, simply modify the
   line defining

      <property name="junit.home"   value="${basedir}/../junit4.0"/>

   If you are using ant version 1.6.5 (and possibly others) you will have
   to also ensure that the junit-4.0.jar JAR file is already in your
   CLASSPATH before launching ant. To do this using Bash, you would type
   something like:

      export CLASSPATH=.:/path/to/junit/junit-4.0.jar

   Apparently, ant 1.7 does not have this second CLASSPATH requirement.

5. cd into 'Task' and type 'ant' to build special ant task

   go back to main directory (cd ..) and continue. At this point you 
   may have to ensure that the ant libraries ${ant.home}/lib/ant.jar
   are in your CLASSPATH.

6. Type 'ant' to compile all Java code (in JavaCode, Examples, Figures,
   and PerformanceTests).

   This will compile all sources and execute the JUnit test cases

7. Type 'make' to compile all C/C++ code (in Code directory)

   You will need both a 'gcc' C compiler and a 'g++' C++ compiler. Tested
   on 'g++' version 3.4.6 and 'gcc' version. Also tested on versions 4.2.1
   of both.

Review Figures and Examples
===========================

You can reproduce all of the computer-generated figures in the book using
code provided with this deployment.  Within the Figures/ directory, type
"make" to generate all of the computed tables and figures. Please be aware
that this process may take several days to compute (especially for Chapter
5). Should you wish to regenerate the figures for a specific chapter only,
change directory to Figures/scripts/chapterN and then type 'make'

Please review the information found in

  * Figures/src/algs/chapterN/README.txt        for N=2..10
  * Figures/src/algs/appendixA/README.txt       benchmark code contained here

To run any of the Java examples, type:

  java -cp dist/ADK-2.0-ExamplesAndFigures.jar [ExampleMain]

For example, to regenerate the information found in chapter 2, table 2, type:

  java -cp dist/ADK-2.0-ExamplesAndFigures.jar algs.chapter2.table2.BisectionMethod

And to show a code example, type:

  java -cp dist/ADK-2.0-ExamplesAndFigures.jar algs.example.chapter5.ModuloSurprise

Some figures and examples require resources that are stored on the
disk. Please use the Makefiles to generate these figures.


We also provide a number of example programs showing the algorithms in
practice. For examples, review the information found in

  * Examples/src/algs/example/README.txt

Optional Tasks
==============

1. Type 'ant junitBlogs' to execute the JUnit test cases covering 
   the blog code.

2. Type 'ant junitreport' to produce a full Report on the JUnit
   progress. Only works if you have valid JAVA_HOME set to locate
   tools.jar. Find the compiled JUnit report in:

       ./Tests/report/ 

   This option is likely only available for ant 1.7 (and higher).

3. To generate javadoc, execute 'ant javadoc'

4. To generate doxygen documentation, type 'make doxygen' in the Code
   directory.

Final Words
===========

Should you find an issue in either the code or the book, please let us know
using the book's web site:

  http://www.oreilly.com/catalog/errata.csp?isbn=0636920032885

You can post Errata and see the list of errors/corrections that were found
after the book was printed.  Should you wish to also post a review of the
book (and this code) visit the book's webpage:

  http://shop.oreilly.com/product/0636920032885.do
