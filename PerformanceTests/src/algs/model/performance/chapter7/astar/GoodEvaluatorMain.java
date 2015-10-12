package algs.model.performance.chapter7.astar;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.debug.AStarSearch;
import algs.model.searchtree.Solution;

// experiment with different values as well as different approaches.

public class GoodEvaluatorMain {

	// 17 nodes in the tree for GoodEvaluator: 7,6,5,4,2,8,1,2
	public static void main(String[] args) {
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{1,4,8},{7,3,0},{6,5,2}
		});
		
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		//BreadthFirstSearch<EightPuzzleNode> bfs = new BreadthFirstSearch<EightPuzzleNode>(start, goal);
		//DepthFirstSearch<EightPuzzleNode> bfs = new DepthFirstSearch<EightPuzzleNode>(start, goal, 16);
		AStarSearch as = new AStarSearch(new GoodEvaluator());
		//OrderedSearch<EightPuzzleNode> bfs = new OrderedSearch<EightPuzzleNode>(start, goal);
		DottyDebugger std = new DottyDebugger();
		//std.ordering(std.DepthFirstOrdering);
		as.debug(std);
		//start.score(new GoodEvaluator());
		Solution sol = as.search(start, goal);
		
		System.out.println (std.getInputString());
		System.out.println (std.numNodes() + " nodes in the tree.");
		System.out.println (sol.toString());		
	}
}
