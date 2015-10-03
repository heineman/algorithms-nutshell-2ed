package algs.example.model.pseudocodeExample;

import org.junit.Test;

import algs.debug.DottyDebugger;
import algs.example.model.problems.pseudocodeExample.PuzzleEvaluator;
import algs.example.model.problems.pseudocodeExample.TinyPuzzle;
import algs.model.searchtree.AStarSearch;
import algs.model.searchtree.Solution;
import algs.model.searchtree.BreadthFirstSearch;
import algs.model.searchtree.DepthFirstSearch;
import junit.framework.TestCase;

public class PrepareFigures extends TestCase {
	@Test
	public void testAStar() {
		TinyPuzzle start = new TinyPuzzle(new int[] {0,0});
		
		int[] target = new int[] {2,1};
		TinyPuzzle goal = new TinyPuzzle(target);
		
		// go eight deep
		AStarSearch as = new AStarSearch(new PuzzleEvaluator(target));
		DottyDebugger std = new DottyDebugger();
		std.ordering(DottyDebugger.DepthFirstOrdering);  // needed to ensure proper ordering for depth-first search.
		//dfs.debug(std);
		Solution sol = as.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (as.numOpen + " open, " + as.numClosed + " closed");
		
		System.out.println (sol.toString());	
	}
	
	@Test
	public void testDFS() {
		TinyPuzzle start = new TinyPuzzle(new int[] {0,0});
		
		TinyPuzzle goal = new TinyPuzzle(new int[] {2,1});
		
		// go eight deep
		DepthFirstSearch dfs = new DepthFirstSearch(3);
		DottyDebugger std = new DottyDebugger();
		std.ordering(DottyDebugger.DepthFirstOrdering);  // needed to ensure proper ordering for depth-first search.
		//dfs.debug(std);
		Solution sol = dfs.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (dfs.numOpen + " open, " + dfs.numClosed + " closed");
		
		System.out.println (sol.toString());	
	}
	
	@Test
	public void testBFS() {
		TinyPuzzle start = new TinyPuzzle(new int[] {0,0});
		
		TinyPuzzle goal = new TinyPuzzle(new int[] {2,1});
		
		// go eight deep
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		DottyDebugger std = new DottyDebugger();
		std.ordering(DottyDebugger.DepthFirstOrdering);  // needed to ensure proper ordering for depth-first search.
		//bfs.debug(std);
		Solution sol = bfs.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (bfs.numOpen + " open, " + bfs.numClosed + " closed");
		
		System.out.println (sol.toString());	
	}
}
