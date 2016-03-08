Version: 2.0
Date: 3-4-2016

Figure 10-1: Bins approach for locating nearest neighbor (by hand)
Figure 10-2: Division of two-dimensional plane using k-d tree (by hand)
Figure 10-3: Quadtree using Region-based partitioning (by hand)
Figure 10-4: Quadtree using Point-based partitioning (by hand)
Figure 10-5: R-Tree example (by hand)
Figure 10-6: Nearest Neighbor example (by hand)

Example 10-1: Nearest Neighbor Queries implemented with k-d tree (method in KDTree)

  find in algs.model.kdtree.KDTree

Table 10-1: Ratio of double recursion invocations to single [Makefile]

Figure 10-7: Number of double recursions as n and d increase [Makefile]

Figure 10-8: Circular data set leads to inefficient k-d tree (screenshot)

  PythonCode/demo/demo_circle_kd.py

Figure 10-9: Comparing k-d tree versus brute-force implementation [Makefile]

Example 10-2: Recursively construct a balanced k-d tree

  find in algs.model.kdtree.KDFactory
  
Figure 10-10: Range Query example

Example 10-3: Range Query implementation

  find in algs.model.kdtree.DimensionalNode

Figure 10-11: Expected performance for O(n^1-1/d) algorithm [Makefile]
  
Table 10-2: Comparing Range Query execution times in milliseconds (k-d tree versus brute force) for all points in the tree [Makefile]
            
Figure 10-12: Comparing k-d tree versus brute force for fractional regions [Makefile]

  Needs GnuPlot 4.6

Table 10-3: Brute force Range Query execution times in milliseconds for Empty Region [Makefile]

Figure 10-13: Range searching using a quadtree (screenshot)

  Execute PythonCode/demo/app_quad_range

Figure 10-14: Collision detection using a quadtree (screenshot)

  Execute PythonCode/demo/app_quad_collision

Example 10-4: Quadtree QuadNode implementation

  find in PythonCode/adk.quad and PythonCode/adk.region
  
Example 10-5: Quadtree add implementation

  find in PythonCode/adk.quad
  
Example 10-6: Quadtree Range Query implementation

  find in PythonCode/adk.quad
  
Example 10-7: Quadtree Collision Detection implementation

  find in PythonCode/adk.quad

Figure 10-15: Degenerate quadtree (screenshot)

Figure 10-16: Sample B-Tree (by hand)

Figure 10-17: R-Tree insert example (by hand)
  
Example 10-8: RNode implementation

  find in PythonCode/adk.R
  
Example 10-9: RTree and corresponding add implementation

  find in PythonCode/adk.R

Example 10-10: RTree/RNode implementation of Range Query

  find in PythonCode/adk.R
  
Example 10-11: RNode implementation of search query

  find in PythonCode/adk.R
  
Example 10-12: RNode implementation of remove operation

  find in PythonCode/adk.R

Table 10-4: R-Tree build performance on dense and sparse data sets
Table 10-5: R-Tree search performance on dense and sparse data sets
Table 10-6: R-Tree delete performance on dense and sparse data sets
Table 10-7: R-Tree search and delete performance on sparse data set as n doubles
