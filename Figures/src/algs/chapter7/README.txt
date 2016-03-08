Version: 2.0
Date: 3-5-2016

Some images found in this chapter are generated. If the figure is 
drawn manually then it is not included in this list. For examples, 
we show where in the code repository to locate the code.

In numerous of the figures in this chapter, we use the freely available
DOTTY program (www.graphviz.org) to produce high quality images for the
expanded game and search trees. For the final book production, we
used a professional artist to reconstruct images that were too big
to view on a single page.

Finally, there are parallel debugging classes for the major search classes
which can be found in packages algs.model.searchtree.debug and
algs.model.gametree.debug; these contain special logic to properly generate
the images that we see in this chapter. When computing the performance of
an algorithm, make sure you don't use these specialized debug classes. 

Figure 7-1: Partial game tree given an initial tic-tac-toe game state

Figure 7-2: Core interfaces for game-tree algorithms

  These UML diagrams describe the following interfaces:
  
     algs.model.gametree.IGameState
     algs.model.gametree.IGameScore
     algs.model.gametree.IPlayer
     algs.model.gametree.IGameMove
     
Example 7-1: Common interface for game-tree path finding

  Find class in algs.model.gametree.IEvaluation
  
Figure 7-3: Minimax sample game tree (by hand)

Example 7-2: Minimax implementation

  find in algs.model.gametree.MinimaxEvaluation
  
Figure 7-4: IComparator interface abstracts MAX and MIN operators

  find in algs.model.gametree.IComparator  
  
Figure 7-5: Sample Minimax exploration [Makefile]

  This image was too large to include in the book, so it was redrawn/excerpted by 
  hand. Creates a Postscript file. Convert into a PNG file to see information 
  in full resolution (2,546px × 476px)
    
Figure 7-6: NegMax sample game tree (by hand)

Example 7-3: NegMax implementation

  find in algs.model.gametree.NegMaxEvaluation
  
Figure 7-7: Sample NegMax exploration [Makefile]

  This image was too large to include in the book, so it was redrawn/excerpted by 
  hand. Convert into a PNG file to see information in full resolution (2,546px × 476px)
  
Figure 7-8: AlphaBeta sample game tree (by hand)

Figure 7-9: AlphaBeta two-ply search [Makefile]

  This image was too large to include in the book, so it was redrawn/excerpted by 
  hand. Convert into a PNG file to see information in full resolution (1,186px × 484px)

Figure 7-10: AlphaBeta three-ply search [Makefile]

  This image was too large to include in the book, so it was redrawn/excerpted by 
  hand. Convert into a PNG file to see information in full resolution (3,853px × 656px)

Example 7-4: AlphaBeta implementation

  find in algs.model.gametree.AlphaBetaEvaluation

Table 7-1: Statistics comparing Minimax versus AlphaBeta [Makefile]

Figure 7-11: Sample tic-tac-toe board after two plays (by hand)

Figure 7-12: Sample tic-tac-toe board after two plays, rotated

Figure 7-13: Sample 8-puzzle search [Makefile]

Figure 7-14: Core interfaces and classes for search-tree algorithms (UML)

 UML diagrams describe the following interfaces:
  
     algs.model.searchtree.INode
     algs.model.searchtree.IMove
     algs.model.searchtree.Solution
     algs.model.searchtree.INodeSet

Example 7-5: Common interface for search-tree path finding

  find in algs.model.searchtree
   
Figure 7-15: Sample Depth-First Search tree for 8-puzzle [Makefile]
 
Example 7-6: Depth-First Search implementation

  Find source code in package algs.model.searchtree.DepthFirstSearch
  
  The only piece of the code 'simplified' for the presentation is that
  in the real code the closed storage can be configured to one of a variety
  of possible structures (Stack, Queue, Hash Table, etc...). In the figure
  we just state straight up that HASH is the default storage structure, 
  although this can be configured once the Depth First Search object is
  constructed.

Figure 7-16: Initial position N2 (by hand)

Figure 7-17: Search tree size for Depth-First Search as depth increases [Makefile]

Figure 7-18: Starting board for Breadth-First Search (by hand)

Figure 7-19: Sample Breadth-first search tree for 8-puzzle [Makefile]

  This image was too large to include in the book, so it was redrawn/excerpted by 
  hand. Convert into a PNG file to see information in full resolution (1,550px × 548px)

Example 7-7: Breadth-First search implementation

  Find source code in package algs.model.searchtree.BreadthFirstSearch
  
  The only piece of the code 'simplified' for the presentation is that
  in the real code the closed storage can be configured to one of a variety
  of possible structures (Stack, Queue, Hash Table, etc...). In the figure
  we just state straight up that HASH is the default storage structure, 
  although this can be configured once the Depth First Search object is
  constructed.

Figure 7-20: Starting board state for A*Search (by hand)

Figure 7-21: Sample A*Search tree in 8-puzzle using GoodEvaluator f*(n) [Makefile]
  
  This image was too large to include in the book, so it was redrawn by hand. Convert
  into PNG file to see information in full resolution (980px × 932px) 
  
Figure 7-22: Sample A*Search tree in 8-puzzle using WeakEvaluator f*(n) [Makefile]
 
Example 7-8: A*Search implementation

  Find source code in package algs.model.searchtree.AStarSearch
  
  The only piece of the code 'simplified' for the presentation is that
  in the real code the closed storage can be configured to one of a variety
  of possible structures (Stack, Queue, Hash Table, etc...). In the figure
  we just state straight up that HASH is the default storage structure, 
  although this can be configured once the Depth First Search object is
  constructed.

Figure 7-23: Sample board state for evaluation functions

Table 7-2: Comparing three evaluation h(n) functions [Makefile]

Figure 7-24: Goal for 15-puzzle AND sample starting board (by-hand)
  
Figure 7-25: Complicated starting board for 15-puzzle (by-hand)
  
Table 7-3: Comparing search algorithms [Makefile]

  java algs.chapter7.table4.Main
  java algs.chapter7.table4.Extended
  
  The first provides all information for the table; the second is used to show
  the extended range afforded by A*Search.
  
  To see a case where DFS fails, even though it ought to find the board, consider
  running 'algs.chapter7.table4.FailedDFSSearch'
  
Figure 7-26: Comparing search tree size for random positions [Makefile]
