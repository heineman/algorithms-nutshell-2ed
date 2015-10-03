Version: 2.0
Date: 6-24-2015

Figure 10-1: Bins approach for locating nearest neighbor
Figure 10-2: Division of two-dimensional plane using kd-tree
Figure 10-3: Quadtree using Region-based partitioning
Figure 10-4: Quadtree using Point-based partitioning
Figure 10-5: R-Tree example
Figure 10-6: Nearest Neighbor example

Example 10-1: Nearest Neighbor Queries implemented with kd-tree (method in KDTree)

  find in algs.model.kdtree.KDTree

Table 10-1: Ratio of double recursion invocations to single [Makefile]

Figure 10-7: Number of double recursions as n and d increase [Makefile]

  source: 2ed-10-7.ps and 2ed-10-7.pdf
  
Figure 10-8: Circular data set leads to inefficient kd-tree

Figure 10-9: Comparing kd-tree versus brute-force implementation [Makefile]

  source: 2ed-10-9.ps and 2ed-10-9.pdf

Example 10-2: Recursively construct a balanced kd-tree

  find in algs.model.kdtree.KDFactory
  
Figure 10-10: Range Query example

Example 10-3: Range Query implementation

  find in algs.model.kdtree.DimensionalNode

Figure 10-11: Expected performance for O(n^1-1/3) algorithm [Makefile]

  source: 2ed-10-11.ps and 2ed-10-11.pdf
  
Table 10-2: Comparing Range Query execution times in milliseconds (kd-tree versus brute force) for All Points scenario [Makefile]
            
Figure 10-12: Comparing kd-tree versus brute force for Fractional Region

  source: 2ed-10-12.ps and 2ed-10-12.pdf
  
Table 10-3: Brute force Range Query execution times in milliseconds for Empty Region [Makefile]

Figure 10-13: Range searching using a quadtree
Figure 10-14: Collision detection using a quadtree

Example 10-4: Quadtree QuadNode implementation

  find in PythonCode/adk.kd and PythonCode/adk.region
  
Example 10-5: Quadtree add implementation

  find in PythonCode/adk.kd
  
Example 10-6: Quadtree Range Query implementation

  find in PythonCode/adk.kd
  
Example 10-7: Quadtree Collision Detection implementation

  find in PythonCode/adk.kd

Figure 10-15: Degenerate quadtree

Figure 10-16: Sample B-Tree

Figure 10-17: R-Tree insert example
  
Example 10-8: RTree RNode implementation

  find in PythonCode/adk.R
  
Example 10-9: RTree RTree implementation

  find in PythonCode/adk.R

Example 10-10: RTree/RNode implementation of Range Query

  find in PythonCode/adk.R
  
Example 10-11: RNode implementation of search query

  find in PythonCode/adk.R
  
Example 10-11: RNode implementation of remove operation

  find in PythonCode/adk.R
  
Table 10-4: R-Tree build performance on dense and sparse data sets
Table 10-5: R-Tree search performance on dense and sparse data sets
Table 10-6: R-Tree delete performance on dense and sparse data sets
Table 10-7: R-Tree search and delete performance on sparse data set as n doubles
