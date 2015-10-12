package algs.model.performance.chapter7;

import algs.debug.DottyDebugger;
import algs.model.problems.fifteenpuzzle.GoodEvaluator;
import algs.model.problems.fifteenpuzzle.FifteenPuzzleNode;
import algs.model.searchtree.debug.AStarSearch;
import algs.model.searchtree.debug.DepthFirstSearch;
import algs.model.searchtree.Solution;

/**
 * DFS fails after 23,130 nodes searched.
 * A*search finds in 94 
 * BFS runs out of memory.
 * 
 * note: most initial boards won't admit a solution even for A*Search. For 
 * example, try:
 * 
 * {5,1,2,4},{14,9,3,7},{13,10,12,6},{15,11,8,0}
 */
public class Figure7_13Main {

	public static void main(String[] args) {
		testAStarExample();
		testDFSExample();
	}
	
	public static void testAStarExample() {
		System.out.println (Runtime.getRuntime().freeMemory());
		System.out.println(Runtime.getRuntime().totalMemory());
		System.out.println(Runtime.getRuntime().maxMemory());
		System.out.println(Runtime.getRuntime().availableProcessors());
		
		
		// DFS fails after 23,130 nodes searched. A* finds in 94 nodes.
		// BFS runs out of memory.
		FifteenPuzzleNode start = new FifteenPuzzleNode(new int[][]{
				{2,10,8,3},{1,6,0,4},{5,9,7,11},{13,14,15,12}
		});
		
		FifteenPuzzleNode goal = new FifteenPuzzleNode(new int[][]{
				{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}
		});
		
		AStarSearch as = new AStarSearch(new GoodEvaluator());
		DottyDebugger std = new DottyDebugger();
		as.debug(std);
		Solution sol = as.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (sol.toString());		
	}
	
	public static void testDFSExample() {
		// DFS fails after 23,130 nodes searched. A* finds in 94 nodes.
		// BFS runs out of memory.
		FifteenPuzzleNode start = new FifteenPuzzleNode(new int[][]{
				{2,10,8,3},{1,6,0,4},{5,9,7,11},{13,14,15,12}
		});


		FifteenPuzzleNode goal = new FifteenPuzzleNode(new int[][]{
				{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}
		});
		
		DepthFirstSearch dfs = new DepthFirstSearch(15);
		DottyDebugger std = new DottyDebugger();
		dfs.debug(std);
		Solution sol = dfs.search(start, goal);
		
		if (sol.succeeded()) { 
			System.out.println("DFS succeeeded!"); 
		} else {
			System.out.println("DFS failed to locate a solution!");
		}
		System.out.println (dfs.numClosed + "," + dfs.numOpen + " = " + (dfs.numClosed + dfs.numOpen));
		System.out.println (sol.toString());		
	}
}
