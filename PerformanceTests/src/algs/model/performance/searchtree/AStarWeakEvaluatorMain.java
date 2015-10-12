package algs.model.performance.searchtree;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.WeakEvaluator;
import algs.model.searchtree.debug.AStarSearch;
import algs.model.searchtree.Solution;

// experiment with different values as well as different approaches.

public class AStarWeakEvaluatorMain {

	public static void main(String[] args) {
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});
		
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		AStarSearch as = new AStarSearch(new WeakEvaluator());
		DottyDebugger std = new DottyDebugger();
		as.debug(std);
		Solution sol = as.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (sol.toString());
	}
}
