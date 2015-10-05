package algs.model.tests.tictactoe;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.SlideMove;
import algs.model.problems.fifteenpuzzle.FifteenPuzzleNode;
import junit.framework.TestCase;

public class SlideMoveTest extends TestCase {
	 public void testSlideMove() {
		 EightPuzzleNode start = new EightPuzzleNode(new int[][]{
					{8,1,3},
					{2,4,5},
					{0,7,6}
			});
		 
		 // false.
		 assertFalse (start.swap(0,0,0,1));
		
		// all boundaries.
		 assertFalse (new SlideMove(2,1,9,0,2).isValid(start));
		 assertFalse (new SlideMove(2,9,0,0,2).isValid(start));
		 assertFalse (new SlideMove(2,1,0,9,2).isValid(start));
		 assertFalse (new SlideMove(2,1,0,2,9).isValid(start));
		
		 
		SlideMove sm = new SlideMove(2,1,0,2,0);
		assertTrue (sm.isValid(start));
		assertTrue (sm.execute(start));
		
		// no longer available.
		assertFalse (sm.execute(start));
		
		assertEquals (2, start.cell(2, 0));
		assertTrue (sm.undo(start));
		
		FifteenPuzzleNode fifteenStart = new FifteenPuzzleNode(new int[][]{
				{8,1,3,9},
				{2,4,5,10},
				{0,7,6,11},
				{12,13,14,15}});
		
		try {
			sm.isValid(fifteenStart);
			fail ("Fails to detect invalid node.");
		} catch (IllegalArgumentException iae) {
			
		}
	
		try {
			sm.undo(fifteenStart);
			fail ("Fails to detect invalid node.");
		} catch (IllegalArgumentException iae) {
			
		}
		
		try {
			sm.execute(fifteenStart);
			fail ("Fails to detect invalid node.");
		} catch (IllegalArgumentException iae) {
			
		}
	 }
}
