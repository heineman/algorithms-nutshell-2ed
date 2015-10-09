package algs.blog.example.model.problems;

import algs.debug.DottyDebugger;
import algs.model.searchtree.debug.AStarSearch;
import algs.model.searchtree.Solution;
import algs.model.searchtree.debug.BreadthFirstSearch;
import algs.model.searchtree.debug.DepthFirstSearch;

public class PrepareFigures {
	
	public static void main(String[] args) {
		PrepareFigures m = new PrepareFigures();
		
		// this runs out of memory. Check it and sett
		//m.runDFS(-1);
		
		//m.runDFS(100);
		
		//m.runBFS();
		
		m.runAStar2();
	}
	
	public void runAStar1 () {
		SmallPuzzle start = new SmallPuzzle(new int[] {0,0});
		
		int[] target = new int[] {7,3};
		SmallPuzzle goal = new SmallPuzzle(target);
		
		// go eight deep
		AStarSearch as = new AStarSearch(new PuzzleEvaluator1(target));
		DottyDebugger std = new DottyDebugger();
		as.debug(std);
		std.ordering(DottyDebugger.DepthFirstOrdering);  // needed to ensure proper ordering for depth-first search.
		Solution sol = as.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (as.numOpen + " open, " + as.numClosed + " closed");
		
		System.out.println (sol.toString());	
	}
	
	public void runAStar2 () {
		SmallPuzzle start = new SmallPuzzle(new int[] {315,713});
		
		int[] target = new int[] {7,3};
		SmallPuzzle goal = new SmallPuzzle(target);
		
		// go eight deep
		AStarSearch as = new AStarSearch(new PuzzleEvaluator2(target));
		DottyDebugger std = new DottyDebugger();
		as.debug(std);
		std.ordering(DottyDebugger.DepthFirstOrdering);  // needed to ensure proper ordering for depth-first search.
		Solution sol = as.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (as.numOpen + " open, " + as.numClosed + " closed");
		
		System.out.println (sol.toString());	
	}
	public void runDFS(int depth) {
		SmallPuzzle start = new SmallPuzzle(new int[] {0,0});
		
		SmallPuzzle goal = new SmallPuzzle(new int[] {7,3});
		
		// when < 0 use now depth
		DepthFirstSearch dfs;
		if (depth < 0) {
			dfs = new DepthFirstSearch();
		} else {
			dfs = new DepthFirstSearch(depth);
		}
		
		DottyDebugger std = new DottyDebugger();
		dfs.debug(std);
		std.ordering(DottyDebugger.DepthFirstOrdering);  // needed to ensure proper ordering for depth-first search.
		Solution sol = dfs.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (dfs.numOpen + " open, " + dfs.numClosed + " closed");
		
		System.out.println (sol.toString());	
	}
	
	public void runBFS() {
		SmallPuzzle start = new SmallPuzzle(new int[] {0,0});
		
		SmallPuzzle goal = new SmallPuzzle(new int[] {7,3});
		
		// go eight deep
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		DottyDebugger std = new DottyDebugger();
		bfs.debug(std);
		Solution sol = bfs.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (bfs.numOpen + " open, " + bfs.numClosed + " closed");
		
		System.out.println (sol.toString());	
	}
}
