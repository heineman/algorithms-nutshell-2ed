Version: 2.0
Date: 3-4-2016


Some images found in this chapter are generated. If the figure is 
drawn manually then it is not included in this list. For examples, 
we show where in the code repository to locate the code.

Table 9-1: Computational Geometry problems and their application

Figure 9-1: Sample set of points in plane with its convex hull drawn

Figure 9-2: Computing determinant decides whether right turn exists

Figure 9-3: Incremental construction of upper partial convex hull

Example 9-1: Convex Hull Scan solution to convex hull

  find in algs.model.problems.convexhull.andrew.ConvexHullScan

Figure 9-4: The Akl-Toussaint heuristic at work

Table 9-2: Example showing running times and applies Akl-Toussaint heuristic [Makefile]

Figure 9-5: Performance of convex hull variations [Makefile]

Table 9-3: Timing comparison (in milliseconds) with highly skewed data [Makefile]

Figure 9-6: Three line segments with two intersections (by hand)

Example 9-2: Brute Force Intersection implementation

  find in algs.model.problems.segmentIntersection.BruteForceAlgorithm

Figure 9-7: Detecting Seven Intersections for six line segments (by hand)

  java algs.chapter9.figure7.Main and read the generated output. Note that
  the output shows the final set of intersections; you will have to debug the
  code to see the intersections detected in order.
  
Example 9-3: LineSweep Java Implementation

  find in algs.model.problems.segmentIntersection.LineSweep
  
Table 9-4: Timing comparison (in milliseconds) between algorithms on Buffon’s needle problem [Makefile]
 
Table 9-5: Worst-case comparison of LineSweep versus BruteForce (in ms) [MAKE]

  java algs.chapter9.table5.Main
 
Figure 9-8: Sample Voronoi Diagram (by hand)

Figure 9-9: Elements of Fortune Sweep

Figure 9-10: Definition of parabola

Figure 9-11: Parabolas change shape as the sweep line moves down

  source: parabolaExplorer.xlsx
  
Figure 9-12: Circle formed by three points

Figure 9-13: Beach line after two points

Figure 9-14: Beach line after three points

Figure 9-15: Beach line after processing circle event

Figure 9-16: Classes supporting the code

Example 9-4: Voronoi Python implementation

  find in PythonCode/adk.fortune
  
Example 9-5: Voronoi process site event

  find in PythonCode/adk.fortune

Example 9-6: Voronoi process circle event

  find in PythonCode/adk.fortune

Example 9-7: Voronoi generate new circle event

  find in PythonCode/adk.fortune
  
