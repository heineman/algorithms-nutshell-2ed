package algs.model.tests.tictactoe;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import junit.framework.TestCase;

public class EightPuzzleNodeTest extends TestCase {
	 public void testSlideMove() {
		 EightPuzzleNode start = new EightPuzzleNode(new int[][]{
					{8,1,3},
					{2,4,5},
					{0,7,6}
			});
		 EightPuzzleNode two = new EightPuzzleNode(new int[][]{
					{8,1,3},
					{2,4,5},
					{0,7,6}
			});
		 
		 assertEquals (0, start.compareTo(two));
		 assertFalse (start.equals(null));
		 assertFalse (start.equals ("GEORGE"));
		 assertFalse (start.equivalent(null));
		 assertTrue (start.equivalent(two));
		 
		 // false. First has no empty; second is diagonal.
		 assertFalse (start.swap(0,0,0,1));
		 assertFalse (start.swap(2,0,1,1));
		 
		 assertTrue (start.isAdjacentAndEmpty(2, 1, 2, 0));
		 assertTrue (start.isAdjacentAndEmpty(1, 0, 2, 0));
		 assertFalse (start.isAdjacentAndEmpty(1, 1, 2, 0));
		 
		 EightPuzzleNode.debug = false;
		 assertEquals ("{8|2| }|{1|4|7}|{3|5|6}", start.nodeLabel());
		 EightPuzzleNode.debug = true;
		 start.score(99);
		 assertEquals ("{8|2| }|{1|4|7}|{3|5|6}|{score: 99}", start.nodeLabel());
		 EightPuzzleNode.debug = false;  // reset to standard default
		 
	}
}
