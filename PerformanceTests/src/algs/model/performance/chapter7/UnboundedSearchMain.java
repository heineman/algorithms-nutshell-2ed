package algs.model.performance.chapter7;

import algs.debug.DottyDebugger;
import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.SlideMove;
import algs.model.searchtree.debug.DepthFirstSearch;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;
import algs.model.searchtree.Solution;

public class UnboundedSearchMain {

	
	// Run this trial multiple times. Here are several such runs:
	
	// (130),(784),(652)    14475 nodes in tree, 6333 open, 8140 closed, 8081 moves found
	// (120),(783),(546)    ????
	// (283),(704),(615)	25439 nodes in tree, 11135 open, 14302 closed, 14203 moves found
	// (142),(635),(870)	86628 nodes in tree, 37505 open, 49121 closed, 48543 moves
	// (831),(402),(765)	108918 nodes in tree, 46639 open, 62277 closed, 61125 moves
	// (834),(215),(076)    80 nodes in tree, 34 open, 44 closed, 43 moves
	// (482),(713),(065)	1686 nodes in tree, 740 open, 944 closed, 937 moves
	// (283),(405),(176)	306 nodes in tree, 134 open, 170 closed, 169 moves
	// (012),(783),(546)	97001 nodes in tree, 41752 open, 55247 closed, 54395 moves
	// (123),(657),(840)    34426 nodes in tree, 15040 open, 19384 closed, 19227 moves
	// (283),(156),(740)    104482 nodes in the tree, 44886 open, 5954 closed, 58575 moves
	public static void main (String []args) {
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		INode start = goal.copy();
		
		// make ten random moves without moving the same tile twice in a row.

		int last = -1;
		for (int i = 0; i < 10; i++) {
			DoubleLinkedList<IMove> s = start.validMoves();
			
			// ensure same tile not moved twice in a row by removing its move
			DoubleNode<IMove> node = s.first();
			while (node != null) {
				SlideMove sm = (SlideMove) node.value();
				if (sm.tile == last) {
					s.remove(sm);
					break;
				}
				
				node = node.next();
			}
			
			// select and apply a random move.
			int select = (int) (s.size()*Math.random());
			node = s.first();
			while (select > 0) {
				select--;
				node = node.next();
			}
			
			SlideMove mv = (SlideMove) node.value();
			mv.execute(start);
			last = ((SlideMove)mv).tile;
		}
		
		System.out.println (start);
		
		// go as deep as your memory will allow...
		DepthFirstSearch dfs = new DepthFirstSearch(Integer.MAX_VALUE);
		DottyDebugger std = new DottyDebugger();
		std.ordering(DottyDebugger.DepthFirstOrdering);  // needed to ensure proper ordering for depth-first search.
		dfs.debug(std);
		Solution sol = dfs.search(start, goal);
		
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (dfs.numOpen + " open, " + dfs.numClosed + " closed");
		System.out.println (sol.numMoves() + " moves");
	}
}
