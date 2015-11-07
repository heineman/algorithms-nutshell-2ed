package algs.chapter7.figure21;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.Solution;
import algs.model.searchtree.debug.AStarSearch;

public class Main {
	public static void main(String[] args) {
		// A bit of a hack. NOW show the Scores in the generated nodes.
		EightPuzzleNode.debug = true;
		
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{0,4,5},{2,7,6}
		});

		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		DottyDebugger std = new DottyDebugger();
		GoodEvaluator ge = new GoodEvaluator();
		AStarSearch as = new AStarSearch(ge);
		as.debug(std);
		
		Solution sol = as.search(start, goal);
		System.out.println (std.getInputString());
		
		// show moves on stderr
		System.err.println ("Moves:");
		System.err.println (sol.toString());
	}
}
