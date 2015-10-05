package algs.model.tests.eightpuzzle;

import static org.junit.Assert.*;


import org.junit.Test;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.Solution;
import algs.model.searchtree.debug.AStarSearch;

public class ChallengeToGoodEvaluatorAStarSearchTest {

	@Test
	public void testSearch() {
		// A bit of a hack. NOW show the Scores in the generated nodes.
		EightPuzzleNode.debug = true;
				
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{2,8,3},{6,7,4},{1,0,5}
		});
		
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		AStarSearch as = new AStarSearch(new GoodEvaluator());
		DottyDebugger std = new DottyDebugger();
		std.ordering(DottyDebugger.DepthFirstOrdering);
		
		as.debug(std);
		Solution sol = as.search(start, goal);
		
		// this is going to be too large, and will result in a file
		String solutionDotty = std.getInputString();
		System.out.println (std.numNodes() + " nodes in the tree.");
		assertEquals (11, sol.numMoves());
		System.out.println(solutionDotty);
	}
}
