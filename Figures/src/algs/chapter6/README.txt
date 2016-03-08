Version: 2.0
Date: 3-6-2016

Figure 6-1: Graph Examples (by hand)
    
Figure 6-2: Sample directed, weighted graph (by hand)

Figure 6-3: Adjacency list representation of directed, weighted graph (by hand)

Figure 6-4: Adjacency matrix representation of directed, weighted graph (by hand)

Figure 6-5: A small maze to get from s to t (by hand)  
  
Figure 6-6: Graph representation of maze from Figure 6-5 (by hand)

Figure 6-7: Depth-First Search example (by hand)

Figure 6-8: Computed d, f, and pred data for a sample undirected graph; [Makefile]

Example 6-1: Depth-First Search implementation
     
  cd Code/Graph/DepthFirstSearch
  find code in dfs.cxx
  
Figure 6-9: Breadth-First Search example (by hand)
  
Figure 6-10: Breadth-First Search progress after five nodes marked completed [Makefile]
  
Example 6-2: Breadth-First Search implementation

  cd Code/Graph/BreadthFirstSearch
  find code in bfs.cxx
  
Figure 6-11: Dijkstra's Algorithm example (by hand)

Figure 6-12: Dijkstra's Algorithm expands the set S (by hand)

  cd Code/Graph/SingleSourceShortestPath
  ./testGraph -v -f Graphs/figure6-12.dat
  
Example 6-3: Dijkstra's Algorithm implementation

  cd Code/Graph/SingleSourceShortestPath
  find code in singleSourceShortest.cxx

Example 6-4: Optimized Dijkstra's Algorithm for dense graphs

  cd Code/Graph/SingleSourceShortestPath
  find code in rawDense.cxx
    
Figure 6-13: Dijkstra's Algorithm Dense Graph example (by hand)

Figure 6-14: Bellman-Ford example (by hand)
    
Example 6-5: Bellman-Ford algorithm for Single Source Shortest Path

  cd Code/Graph/SingleSourceShortestPath
  find code in bellmanFord.cxx
 
Table 6-1: Time (in seconds) to compute single source shortest path on benchmark graphs [Makefile]
  
Table 6-2: Time (in seconds) to compute single source shortest path on dense graphs [Makefile]
     
Table 6-3: Time (in seconds) to compute single source shortest path on large sparse graphs [Makefile]
  
Figure 6-15: Floyd-Warshall example (by hand)

  cd Code/Graph/AllPairsShortestPath
  ./testGraph pseudoCodeFigure.dat   
  
Example 6-6: Floyd-Warshall algorithm for computing all pairs shortest path

  cd Code/Graph/AllPairsShortestPath
  find code in allPairsShortest.cxx
  
Example 6-7: Code to recover shortest path from the computed pred[][]

  cd Code/Graph/AllPairsShortestPath
  find code in allPairsShortest.cxx
  
Figure 6-16: Prim's Algorithm example

  cd Code/Graph/MinimumSpanningTree
  ./process -v -f figure6-16.dat
  
Example 6-8: Prim's Algorithm implementation with binary heap

  cd Code/Graph/MinimumSpanningTree
  find code in mst.cxx
  
Table 6-4: Performance comparison of two algorithm variations (by hand)

