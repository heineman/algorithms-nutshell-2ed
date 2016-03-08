package algs.chapter7.figure24;

import algs.debug.DottyDebugger;
import algs.model.problems.fifteenpuzzle.GoodEvaluator;
import algs.model.problems.fifteenpuzzle.FifteenPuzzleNode;
import algs.model.searchtree.debug.AStarSearch;
import algs.model.searchtree.debug.BreadthFirstSearch;
import algs.model.searchtree.debug.DepthFirstSearch;
import algs.model.searchtree.Solution;

// experiment with different values as well as different approaches.
/**
 * Still running without a found solution...
  
 54683 A* Search tree size (closed:23738, open:30945)
 55186 A* Search tree size (closed:23976, open:31210)
 114870 A* Search tree size (closed:49435, open:65435)
 */
public class Main  {

	public static void main(String[] args) {
		testAStarExample();
		//testDFSExample();
		
		// run this if you want. it exceeds memory
		//testBFSExample();
	}
	
	public static void testAStarExample() {
		
		// DFS fails after 23,130 nodes searched. A* finds in 94 nodes.
		// BFS runs out of memory.
		FifteenPuzzleNode start = new FifteenPuzzleNode(new int[][]{
				{2,10,8,3},{1,6,0,4},{5,9,7,11},{13,14,15,12}
		});
		
		// more complex sequence of moves: just not able to make progress
		// on this one.
		FifteenPuzzleNode impossibleStart = new FifteenPuzzleNode(new int[][]{
				{5,1,2,4},{14,9,3,7},{13,10,12,6},{15,11,8,0}
		});

		// runs out of memory...
		// start = impossibleStart;
		
		FifteenPuzzleNode goal = new FifteenPuzzleNode(new int[][]{
				{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}
		});
		
		AStarSearch as = new AStarSearch(new GoodEvaluator());
		DottyDebugger std = new DottyDebugger();
		as.debug(std);
		Solution sol = as.search(start, goal);
		
		System.out.println (std.getInputString());
		System.err.println ("AStar Moves:");
		System.err.println (std.numNodes() + " nodes in the tree.");
		System.err.println (sol.toString());		
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
			System.err.println("DFS succeeeded. Stats are: closed=" + dfs.numClosed + ",open:" + dfs.numOpen);
		} else {
			System.err.println("DFS failed to locate a solution after exploring " + dfs.numClosed+ " states.");
		}
		System.err.println (sol.toString());		
	}
	
	public static void testBFSExample() {
		//System.out.println (Runtime.getRuntime().freeMemory());
		//System.out.println(Runtime.getRuntime().totalMemory());
		//System.out.println(Runtime.getRuntime().maxMemory());
		//System.out.println(Runtime.getRuntime().availableProcessors());
		
		// BFS runs out of memory.
		FifteenPuzzleNode start = new FifteenPuzzleNode(new int[][]{
				{2,10,8,3},{1,6,0,4},{5,9,7,11},{13,14,15,12}
		});


		FifteenPuzzleNode goal = new FifteenPuzzleNode(new int[][]{
				{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}
		});
		
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		DottyDebugger std = new DottyDebugger();
		bfs.debug(std);
		Solution sol = bfs.search(start, goal);
		
		if (sol.succeeded()) { 
			System.out.println("BFS succeeeded!"); 
		} else {
			System.out.println("BFS failed to locate a solution!");
		}
		System.out.println (bfs.numClosed + "," + bfs.numOpen + " = " + (bfs.numClosed + bfs.numOpen));
		System.out.println (sol.toString());		
	}
	
}
