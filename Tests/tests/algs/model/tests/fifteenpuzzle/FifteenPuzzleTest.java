package algs.model.tests.fifteenpuzzle;

import junit.framework.TestCase;
import org.junit.Test;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.fifteenpuzzle.FifteenPuzzleNode;
import algs.model.problems.fifteenpuzzle.GoodEvaluator;
import algs.model.searchtree.Solution;
import algs.model.searchtree.debug.AStarSearch;
import algs.model.searchtree.debug.BreadthFirstSearch;
import algs.model.searchtree.debug.DepthFirstSearch;
import algs.model.searchtree.states.StateStorageFactory;

public class FifteenPuzzleTest extends TestCase {

	@Test
	public void testState() {

		// more complex sequence of moves: just not able to make progress
		// on this one.
		FifteenPuzzleNode start = new FifteenPuzzleNode(new int[][]{
				{ 2,10, 8, 3},
				{ 1, 6, 0, 4},
				{ 5, 9, 7,11},
				{13,14,15,12}
		});
		
		FifteenPuzzleNode goal = new FifteenPuzzleNode(new int[][]{
				{ 1, 2, 3, 4},
				{ 5, 6, 7, 8},
				{ 9,10,11,12},
				{13,14,15, 0}
		});
		
		// strange cases
		assertFalse (goal.equivalent(null));
		assertFalse (goal.equals(null));
		assertFalse (goal.equals("George"));
		
		AStarSearch as = new AStarSearch(new GoodEvaluator());
		DottyDebugger std = new DottyDebugger();
		as.debug(std);
		as.search(start, goal);
		
		// false. First has no empty; second is diagonal.
		assertFalse (start.swap(0,0,0,1));
		assertFalse (start.swap(2,0,1,1));
		 
		assertTrue (start.isAdjacentAndEmpty(0, 2, 1, 2));
		assertTrue (start.isAdjacentAndEmpty(2, 2, 1, 2));
		assertFalse (start.isAdjacentAndEmpty(2, 1, 1, 2));
		assertTrue (start.isAdjacentAndEmpty(1, 1, 1, 2));
		
		assertEquals (0, start.compareTo(start));
		assertFalse (start.compareTo(goal) == 0);
		
		assertEquals (" 2 10  8  3 \n 1  6     4 \n 5  9  7 11 \n13 14 15 12 \n", 
					  start.toString());
		
		EightPuzzleNode.debug = false;
		assertEquals ("{2|1|5|13}|{10|6|9|14}|{8| |7|15}|{3|4|11|12}", start.nodeLabel());
		EightPuzzleNode.debug = true;
		start.score(99);
		assertEquals ("{2|1|5|13}|{10|6|9|14}|{8| |7|15}|{3|4|11|12}|{score: 99}", start.nodeLabel());
		EightPuzzleNode.debug = false;  // reset to standard default
		
		// completion
		as.storageType(StateStorageFactory.HASH);
	}
	
	@Test
	public void testInvalid() {

		// invalid state is detectable. Here the array has the wrong dimensions
		try {
			new FifteenPuzzleNode(new int[][]{
					{ 2, 2, 8, 3},
					{ 1, 6, 0, 4},
					{13,14,15,12}
			});
			fail ("Should have detected invalid Fifteen puzzle state");
		} catch (Exception e) {
			
		}
		
		// invalid state is detectable. Here there are missing digits.
		try {
			new FifteenPuzzleNode(new int[][]{
					{ 2, 2, 8, 3},
					{ 1, 6, 0, 4},
					{ 1, 6, 0, 4},
					{13,14,15,12}
			});
			fail ("Should have detected invalid Fifteen puzzle state");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testBFS() {

		// more complex sequence of moves: just not able to make progress
		// on this one.
		FifteenPuzzleNode start = new FifteenPuzzleNode(new int[][]{
				{ 1, 2, 3, 4},
				{ 0, 5, 7, 8},
				{ 9, 6,11,12},
				{13,10,14,15}
		});
		
		FifteenPuzzleNode goal = new FifteenPuzzleNode(new int[][]{
				{ 1, 2, 3, 4},
				{ 5, 6, 7, 8},
				{ 9,10,11,12},
				{13,14,15, 0}
		});
		
		// Test find same as goal
		BreadthFirstSearch bs = new BreadthFirstSearch();
		DottyDebugger std = new DottyDebugger();
		bs.debug(std);
		Solution sol = bs.search(goal, goal);
		assertTrue (sol.succeeded());
		
		bs = new BreadthFirstSearch();
		std = new DottyDebugger();
		bs.debug(std);
		sol = bs.search(start, goal);
		
		// found solution
		assertTrue (sol.succeeded());
		
		// for completion
		bs.storageType(StateStorageFactory.QUEUE);		
	}
	
	@Test
	public void testDFS() {

		// more complex sequence of moves: just not able to make progress
		// on this one.
		FifteenPuzzleNode start = new FifteenPuzzleNode(new int[][]{
				{ 1, 2, 3, 4},
				{ 0, 5, 7, 8},
				{ 9, 6,11,12},
				{13,10,14,15}
		});
		
		FifteenPuzzleNode goal = new FifteenPuzzleNode(new int[][]{
				{ 1, 2, 3, 4},
				{ 5, 6, 7, 8},
				{ 9,10,11,12},
				{13,14,15, 0}
		});
		
		// Test find same as goal
		DepthFirstSearch ds = new DepthFirstSearch(10);
		DottyDebugger std = new DottyDebugger();
		ds.useLegend(true);
		ds.debug(std);
		Solution sol = ds.search(goal, goal);
		assertTrue (sol.succeeded());
		
		ds = new DepthFirstSearch(10);
		ds.useLegend(true);
		std = new DottyDebugger();
		ds.debug(std);
		sol = ds.search(start, goal);
		
		// found solution
		assertTrue (sol.succeeded());
		
		// deny solution by not looking for enough ahead.
		ds = new DepthFirstSearch(2);
		std = new DottyDebugger();
		ds.debug(std);
		sol = ds.search(start, goal);
		
		// found solution
		assertFalse (sol.succeeded());
		
		// complete the set (don't use this one because it has infinite depth. Only here to get max covergae. 
		ds = new DepthFirstSearch();
		ds.storageType(StateStorageFactory.HASH);
		
	}
}

