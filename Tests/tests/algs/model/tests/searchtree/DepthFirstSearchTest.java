package algs.model.tests.searchtree;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.searchtree.DepthFirstSearch;
import algs.model.searchtree.Solution;
import algs.model.searchtree.states.StateStorageFactory;

public class DepthFirstSearchTest {

	@Test
	public void testDepthFirstSearch() {
		DepthFirstSearch dfs = new DepthFirstSearch();

		// select default one...
		dfs.storageType(StateStorageFactory.HASH);

		// invalid target position. Can never get here
		OnePuzzle initialNode = new OnePuzzle(5);
		OnePuzzle neverReachedNode = new OnePuzzle(25);

		Solution sol = dfs.search(initialNode, neverReachedNode);
		assertFalse (sol.succeeded());

		assertEquals (5, dfs.numMoves);  // 5 moves to be made
		assertEquals (0, dfs.numOpen);
		assertEquals (6, dfs.numClosed);  // states {5,6,7,8,9,10}
	}


}
