package algs.model.tests.eightpuzzle;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.BadEvaluator;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
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
		Solution sol = as.search(start, goal);
		
		// this is going to be too large, and will result in a file
		String fileName = std.getInputString();
		File file = new File (fileName);
		assertTrue (file.exists());
		
		System.out.println ();
		
		file.delete();
		System.out.println (std.numNodes() + " nodes in the tree.");
		assertEquals (19, sol.numMoves());
	}
}
