Version: 2.0
Date: 3-5-2016

Figure 8-1: Relationship between network flow problems (by hand)

Figure 8-2: Sample flow network graph

  find in algs.chapter8.figure2.Main

Example 8-1: Sample Java Ford-Fulkerson implementation

  find in algs.model.network.FordFulkerson
  
Figure 8-3: Ford-Fulkerson example (by-hand)  

  find in algs.chapter8.figure3.Main 
  
Figure 8-4: Modeling information for Ford-Fulkerson (UML)

Figure 8-5: Search capability (UML)

  Find these classes in package algs.model.network

Example 8-2: Using Depth-First Search to locate augmenting path

  find in algs.model.network.DFS_SearchList
  
  In Figure 8-3 the augmenting paths found using DFS are: <s,2,4,t>, <s,1,3,t>, 
  then <s,1,4,2,3,t>. Validate by setting breakpoints within FordFulkerson.
  
Example 8-3: Using Breadth-First Search to locate augmenting path

  find in algs.model.network.BFS_SearchList
  
  In the text (p.234) there is a list of the augmenting paths found using BFS. 
  They are: <s,1,3,t>, <s,1,4,t>, <s,2,3,t>, <s,2,4,t>. 
  Validate by setting breakpoints within FordFulkerson.
 
Example 8-4: Optimized Ford-Fulkerson implementation

  find in algs.model.network.Optimized
  
Figure 8-6: Bipartite Matching reduces to Maximum Flow (by hand)
  
Example 8-5: Bipartite Matching using Ford-Fulkerson

  find in algs.model.network.matching.BipartiteMatching
  
Example 8-6: Shortest path (in costs) search for Ford-Fulkerson

  find in algs.model.network.ShortestPathArray  
  
Figure 8-7: Side-by-side comparison showing difference when considering the
    minimum cost flow (by hand)
    
  java algs.chapter8.figure7.Main
  
  Executing shows the final cost of 3300 and flow of 600. Must step through with
  breakpoints individually to see each step of the animation.
  
Figure 8-8: Sample Transshipment problem instance converted to Minimum Cost Flow
   problem instance (by hand)
   
   java algs.chapter8.figure8.Main;
   
Example 8-7: Maple commands to apply minimization to Transportation problem

  find in algs/chapter8/example7/Commands.mpl
  
