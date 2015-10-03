Version: 2.0
Date: 6-24-2015

Some images found in this chapter are generated. If the figure is 
drawn manually then it is not included in this list. For examples, 
we show where in the code repository to locate the code.

In numerous of the figures in this chapter, we use the freely available
DOTTY program (www.graphviz.org) to produce high quality images for the
expanded game and search trees. In general, there are programs for each
figure that produce text suitable for input to DOTTY. We execute the
conversion manually using the PC version of the software, though I expect
that a batch-oriented Linux version would have worked as well. Using the
graphical interface provided by GraphViz we converted the DOTTY input into
postscript format. Then we ran Adobe Distiller (again on a PC) to convert
the Postscript into high quality PDF documents with embedded fonts (the
specific font was TimesNewRomanPSMT). For the final book production, it is
expected that the font will be converted into 'Myriad Pro Condensed'. 

Finally, there are parallel debugging classes for the major search classes
which can be found in packages algs.model.searchtree.debug and
algs.model.gametree.debug; these contain special logic to properly generate
the images that we see in this chapter. When computing the performance of
an algorithm, make sure you don't use these specialized debug classes. 

Figure 7-1: Partial game tree given an initial tic-tac-toe game state

Figure 7-2: Core concepts for game tree algorithms

  These UML diagrams describe the following interfaces:
  
     algs.model.gametree.IGameState
     algs.model.gametree.IScore
     algs.model.gametree.IPlayer
     algs.model.gametree.IGameMove
     
Example 7-1: Common interface for game tree path finding

  Find class in algs.model.gametree.IEvaluation
  
Figure 7-3: Minimax sample game tree

Example 7-2: Minimax Java implementation

  find in algs.model.gametree.MinimaxEvaluation
  
Figure 7-4: IComparator interface abstracts MAX and MIN operators

  find in algs.model.gametree.IComparator  
  
Figure 7-5: Sample Minimax exploration [Makefile]]

  This image was too large to include in the book, so it was redrawn/excerpted by 
  hand. Must do this again. 
    
Figure 7-6: NegMax sample game tree

Example 7-3: Negmax implementation

  find in algs.model.gametree.NegMaxEvaluation
  
Figure 7-7: Sample Negmax exploration [Makefile]

  This image was too large to include in the book, so it was redrawn/excerpted by 
  hand. 
  
Figure 7-8: AlphaBeta two-ply search [Makefile]

  java algs.chapter7.figure21.Main           # produce DOTTY output

  This image was too large to include in the book, so it was redrawn/excerpted by 
  hand. 

Figure 7-9: AlphaBeta sample game tree

Figure 7-10: AlphaBeta three-ply search [Makefile]

  This image was too large to include in the book, so it was redrawn/excerpted by 
  hand. 

Example 7-4: AlphaBeta implementation

  find in algs.model.gametree.AlphaBetaEvaluation

Table 7-1: Statistics comparing Minimax versus AlphaBeta [Makefile]

Figure 7-11: Sample tic-tac-toe board after two plays

Figure 7-12: Sample tic-tac-toe board after two plays, rotated

Figure 7-13: Sample 8-puzzle search [Makefile]

Figure 7-14: Core classes for search tree algorithms

 UML diagrams describe the following interfaces:
  
     algs.model.searchtree.INode
     algs.model.searchtree.IMove
     algs.model.searchtree.Solution
     algs.model.searchtree.INodeSet

Example 7-5: Common interface for search tree path finding

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

Figure 7-16: Sample board state

Figure 7-17: Sample board state rotated counterclockwise

Figure 7-18: Initial position N2

Figure 7-19: Search tree size for Depth-First Search as depth increases [Makefile]

Figure 7-20: Starting board for Breadth-First Search

Figure 7-21: Sample Breadth-first search tree for 8-puzzle [Makefile]

Example 7-7: Breadth-First search implementation

  Find source code in package algs.model.searchtree.BreadthFirstSearch
  
  The only piece of the code 'simplified' for the presentation is that
  in the real code the closed storage can be configured to one of a variety
  of possible structures (Stack, Queue, Hash Table, etc...). In the figure
  we just state straight up that HASH is the default storage structure, 
  although this can be configured once the Depth First Search object is
  constructed.

Figure 7-22: Starting board state for A*Search

Figure 7-23: Sample A*Search tree in 8-puzzle using GoodEvaluator f*(n) [Makefile]
  
Figure 7-24: Sample A*Search tree in 8-puzzle using WeakEvaluator f*(n) [Makefile]
 
Example 7-8: A*Search implementation

  Find source code in package algs.model.searchtree.AStarSearch
  
  The only piece of the code 'simplified' for the presentation is that
  in the real code the closed storage can be configured to one of a variety
  of possible structures (Stack, Queue, Hash Table, etc...). In the figure
  we just state straight up that HASH is the default storage structure, 
  although this can be configured once the Depth First Search object is
  constructed.

Figure 7-25: Sample board state for evaluation functions

Table 7-2: Comparing three evaluation h*(n) functions [Makefile]

Figure 7-26: Goal for 15-puzzle

Figure 7-27: Sample starting board for 15-puzzle

Figure 7-28: Sample A*Search tree for 15-puzzle [Makefile]
  
Figure 7-29: Complicated starting board for 15-puzzle
  
Table 7-3: Comparing search algorithms [Makefile]

  java algs.chapter7.table4.Main
  java algs.chapter7.table4.Extended
  
  The first provides all information for the table; the second is used to show
  the extended range afforded by A*Search.
  
  To see a case where DFS fails, even though it ought to find the board, consider
  running 'algs.chapter7.table4.FailedDFSSearch'
  
Figure 7-30: Comparing Search tree size for random positions [Makefile]
