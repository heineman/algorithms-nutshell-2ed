package algs.model.tests.searchtree;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.searchtree.Solution;
import algs.model.searchtree.states.StateStorageFactory;

public class BreadthFirstSearchTest {

	@Test
	public void testFailedBFS() {
		algs.model.searchtree.BreadthFirstSearch bfs = new algs.model.searchtree.BreadthFirstSearch();
		
		// select default one...
		bfs.storageType(StateStorageFactory.HASH);
		
		// invalid target position. Can never get here
		OnePuzzle initialNode = new OnePuzzle(5);
		OnePuzzle neverReachedNode = new OnePuzzle(25);
		
		Solution sol = bfs.search(initialNode, neverReachedNode);
		assertFalse (sol.succeeded());

		assertEquals (5, bfs.numMoves);  // 5 moves to be made
		assertEquals (0, bfs.numOpen);
		assertEquals (6, bfs.numClosed);  // states {5,6,7,8,9,10}
	}
	
	// same in debugger.
	@Test
	public void testFailedDebugBFS() {
		algs.model.searchtree.debug.BreadthFirstSearch bfs = new algs.model.searchtree.debug.BreadthFirstSearch();
		
		// select default one...
		bfs.storageType(StateStorageFactory.HASH);
		
		// invalid target position. Can never get here
		OnePuzzle initialNode = new OnePuzzle(5);
		OnePuzzle neverReachedNode = new OnePuzzle(25);
		
		Solution sol = bfs.search(initialNode, neverReachedNode);
		assertFalse (sol.succeeded());

		assertEquals (5, bfs.numMoves);  // 5 moves to be made
		assertEquals (0, bfs.numOpen);
		assertEquals (6, bfs.numClosed);  // states {5,6,7,8,9,10}
	}

}
