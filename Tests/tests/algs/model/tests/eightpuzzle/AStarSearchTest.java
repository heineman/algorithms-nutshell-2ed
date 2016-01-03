package algs.model.tests.eightpuzzle;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.BadEvaluator;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.searchtree.debug.ClosedHeuristic;
import algs.model.searchtree.Solution;
import algs.model.searchtree.debug.AStarSearch;

public class AStarSearchTest {

	@Test
	public void testSearch() {
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{1,4,8},{7,3,0},{6,5,2}
		});
		
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		AStarSearch as = new AStarSearch(new BadEvaluator());
		DottyDebugger std = new DottyDebugger();
		std.ordering(DottyDebugger.DepthFirstOrdering);
		as.debug(std);
		
		//start.score(new GoodEvaluator());
		Solution sol = as.search(start.copy(), goal.copy());
		
		// this is going to be too large, and will result in a file
		String fileName = std.getInputString();
		File file = new File (fileName);
		assertTrue (file.exists());
		
		file.delete();
		System.out.println (std.numNodes() + " nodes in the tree.");
		assertEquals (19, sol.numMoves());
		
		ClosedHeuristic ch = new ClosedHeuristic(new BadEvaluator());
		std = new DottyDebugger();
		std.ordering(DottyDebugger.DepthFirstOrdering);
		ch.debug(std);
		
		//start.score(new GoodEvaluator());
		sol = ch.search(start.copy(), goal.copy());
		
		// this is going to be too large, and will result in a file
		fileName = std.getInputString();
		file = new File (fileName);
		assertTrue (file.exists());
		
		// noticeably WORSE and was early sign that there was mistake in 1st edition
		// AStar implementation.
		file.delete();
		System.out.println (std.numNodes() + " nodes in the tree.");
		assertEquals (421, sol.numMoves());
	}
}
