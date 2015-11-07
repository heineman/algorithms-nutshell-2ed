package algs.chapter7.figure21;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.Solution;
import algs.model.searchtree.debug.AStarSearch;

/**
 * paper "Complete Solution of the Eight-Puzzle and the Benefit of Node Ordering
 * in IDA*" by Alexander Reinefeld describes the challenging boards for the
 * eight puzzle.
 * 
 * Note the goal states are slightly different from ours. 
 * 
 * This initial board state really shows the limitation of the GoodEvaluator
 * since it pretty much prevents A*Search from finding a solution. When it runs
 * out of memory, there are at least 93,786 nodes in the closed list and 42,086
 * nodes in the open list.
 */
public class InterestingExtension {
	public static void main(String[] args) {
		// A bit of a hack. NOW show the Scores in the generated nodes.
		EightPuzzleNode.debug = true;
		
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,7,6},{0,4,1},{2,5,3}
		});

		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{0,1,2},{3,4,5},{6,7,8}
		});
		
		DottyDebugger std = new DottyDebugger();
		GoodEvaluator ge = new GoodEvaluator();
		AStarSearch as = new AStarSearch(ge);
		as.debug(std);
		
		Solution sol = as.search(start, goal);
		System.out.println (std.getInputString());
		System.out.println (sol.toString());
	}
}
