This file contains a listing of all examples supplied with the
repository. With a few exceptions as noted below, these examples can all be
run by typing the following within the ADK/Deployment directory (but you
can also place the ADK JAR file in a well known location to your CLASSPATH
in which case the classes will be found automatically).

    java -cp dist/ADK-2.0-ExamplesAndFigures.jar <<MainClassName>>

algs.example.chapter2.Sorting32vs64

   Demonstrates the extra cost of sorting 64-bit integers vs. 32-bit longs.

algs.example.chapter3.MaxDivideConquer

   Demonstrate 

algs.example.chapter4.BinaryIntegerFile

   Demonstrates the time to sort file on disk using MergeSort. Relies
   on MMAP capability for performance.

algs.example.chapter5.ModuloSurprise

   Show that the expression "a % b" in Java might produce a negative
   number.  Show that hashCode() method might also produce a negative
   number.
   
algs.example.chapter7.fifteenSolitaire

   Implementation contains a "fixed" approach towards solving the 15-peg
   solitaire game which consists of a triangular arrangement of holes
   containing pegs with one missing. The intent is to jump pegs until only
   one remains.
   
   algs.example.chapter7.fifteenSolitaire.fixed.Main shows the immediate
   (and non extensible) solution for 15 pegs.
   
   algs.example.chapter7.fifteenSolitaire.Main runs a generic solution that
   works for any number of triangular peg boards but runs out of memory for
   n>= 7 rows. Uses depth-first search
   
   algs.example.chapter7.fifteenSolitaire.MainAStar shows how to use an
   evaluation heuristic to improve on the blind search. Unfortunately, also
   runs out of memory also for n >= 7 rows.
   
   algs.example.chapter7.fifteenSolitaire.OrderedMain shows how to generate
   an ordering class to ensure the valid moves are tried in a specific
   ordering.
   
algs.example.chapter9.Main

  MUST BE LAUNCHED FROM WITHIN Examples DIRECTORY

  Batch-launches the line segment algorithms from a defined data set
  containing the line segments. This example program was invaluable during
  testing because it took a while to understand there was a discrepancy
  between Brute Force line segment intersection and the LineSweep
  algorithm. Once a (large) data set was randomly generated to cause the
  difference, individual line segments were manually removed from the data
  set until it was as small as it could be while still returning the
  discrepancy.
  
algs.example.convexhull.imageBound.Main
algs.example.convexhull.imageBound.OneMore
algs.example.convexhull.imageBound.Another

  MUST BE LAUNCHED FROM WITHIN Examples DIRECTORY

  The imageBound package contains sample code to read in ASCII text files
  to be interpreted as containing imagery. The code locates the convex hull
  of these ASCII files assuming they contain images of a fixed size (width
  and height). Three examples are given (Main, OneMore, and Another).
  
algs.example.convexhull.rings.Rings

  Contains sample code to explore the ring-like structure of the nested
  convex hulls within a sample data set. The class Rings contains the main
  executable class for this example which generates a random data set and
  computes Rings information.
  
algs.example.gui 

  contains a large amount of code to showcase several GUI applications
  showing computational geometry algorithms and a small TicTacToe
  application.
  
  algs.example.gui.problems.nearestNeighbor contains the full set of
  packages to enable users to generate random input sets and compute
  Nearest Neighbor queries. The primary application to run is

     algs.example.gui.problems.nearestNeighbor.Launcher which offers a
     GUI-based experience.
  
  algs.example.gui.problems.rangeQuery defines packages to enable users to
  try out the Range Query algorithms. The primary application to run is

     algs.example.gui.problems.rangeQuery.Launcher which offers a GUI-based
     experience. 
  
     Choose "uniform" and dimensions must be 2 for kd-tree to work. Select 
     max of "100" to make room for the points. Select generate "500" and
     then optionally select "show Kd-Tree" and "balanced Kd-Tree".  Be sure
     to try other distributions, such as "Circle"
  
  algs.example.gui.problems.segmentIntersection.LaunchSegmentIntersection
  shows how to compute the intersections found in an input set of lines.

    Choose "uniform" and ratio "20" (larger ratios reduces number of 
    intersections) and choose "scale points" and generate 1024 line
    segments. LineSweep is about 50% faster than Brute Force.

  algs.example.gui.problems.segmentIntersection.LaunchCircleIntersection
  shows how to compute the intersections between an input set of circles.
  
    Choose "scale points" and radius of "0.5" and generate 32 circles.

  algs.example.gui.problems.tictactoe.TicTacToeApplet contains an applet to
  allow the user to set up tournaments of play between human players and/or
  automatic agents that use a variety of search techniques described in
  Chapter 7, including: NegMax, MinMax, and AlphaBeta at a variety of
  Ply-depths. Note that all tournament information is directed to the Java
  Console window when run as an applet. 

  To see this applet run, you must do the following steps:

    1. Create a directory called "TicTacToe" within a publicly available
       location on your web site (i.e., somewhere beneath
       public_html). Make sure TicTacToe directory has sufficient visible
       permissions so it can be viewed using a Browser (typically, 0x555).

    2. Into this TicTacToe directory, copy the
       "ADK-2.0-ExamplesAndFigures.jar" file that you find within the
       ADK/dist directory. Make sure permissions are at least 0x444

    3. Into this TicTacToe directory, create a file "applet.html" with
       permissions at least 0x444 and set the contents of this file to the
       following HTML fragment:

<applet archive="ADK-2.0-ExamplesAndFigures.jar"
   code="algs.example.gui.problems.tictactoe.TicTacToeApplet"
   width=800 height=800>
</applet>

    4. Within a Browser URL, enter
    "http://YOURHOST/URL-to-TicTacToe/applet.html" and you will be able to
    view the GUI that enables you to select a variety of TicTacToe
    variations as well as possible combinations of players.
 
algs.example.model.network.mincostmaxflow.TestMatchingLargeExample shows
how to use Ford-Fulkerson on a Matching problem

  MUST BE LAUNCHED FROM WITHIN Examples DIRECTORY 

algs.example.model.network.mincostmaxflow.TestMaxFlowMinCost example shows
use of ShortestPathArray on an Assignment problem. There is no output since
the code contains assert statements that would throw an exception on
failure. Inspect the code to see the details of the problem.
  
algs.example.model.problems.pseudocodeExample 

  This package contains code defining the TinyPuzzle example used in Fact
  Sheets within Chapter 7 in the first edition of the book. There is no 
  executable content here. Look to the Figures code to see examples for 
  how it is used.
  
algs.example.scheduler.Main

  A small example showing the use of a segment interval tree to help
  schedule when employees should work.
