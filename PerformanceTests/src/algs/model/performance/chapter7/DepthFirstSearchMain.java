package algs.model.performance.chapter7;


import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.searchtree.debug.DepthFirstSearch;
import algs.model.searchtree.Solution;

public class DepthFirstSearchMain {

	// 8 brings to 38 nodes  (7,6,5,4,2,8,1,2)
	// 10 brings to 92 nodes (7,6,5,4,2,8,1,2)
	// 12 brings to 227 nodes (7,6,5,4,1,8,2,1,8,2,1,8)
	public static void main(String[] args) {
		
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});
		
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		// go eight deep
		DepthFirstSearch dfs = new DepthFirstSearch(10);
		DottyDebugger std = new DottyDebugger();
		std.ordering(DottyDebugger.DepthFirstOrdering);  // needed to ensure proper ordering for depth-first search.
		dfs.debug(std);
		Solution sol = dfs.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (dfs.numOpen + " open, " + dfs.numClosed + " closed");
		
		System.out.println (sol.toString());	
	}
}
