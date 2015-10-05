package algs.model.tests.searchtree;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.problems.eightpuzzle.EightPuzzleNode;

// experiment with different values as well as different approaches.

public class NodeExpansionTest extends TestCase {

	@Test
	public void testAStarExample() {
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{0,4,3},{1,8,5},{2,7,6}
		});
		
		assertEquals (2, start.validMoves().size());
	}

}
