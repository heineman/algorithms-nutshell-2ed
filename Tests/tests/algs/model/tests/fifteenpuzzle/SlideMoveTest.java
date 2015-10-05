package algs.model.tests.fifteenpuzzle;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.fifteenpuzzle.FifteenPuzzleNode;
import algs.model.problems.fifteenpuzzle.SlideMove;
import junit.framework.TestCase;

public class SlideMoveTest extends TestCase {
	 public void testSlideMove() {
		 FifteenPuzzleNode start = new FifteenPuzzleNode(new int[][]{
					{2,10, 8,  3},
					{1, 6, 0,  4},
					{5, 9, 7, 11},
					{13,14,15,12}
			});
		 
		 // false.
		 assertFalse (start.swap(0,0,0,1));
		
		// all boundaries.
		 assertFalse (new SlideMove(8,9,2,1,2).isValid(start));
		 assertFalse (new SlideMove(8,0,9,1,2).isValid(start));
		 assertFalse (new SlideMove(8,0,2,9,2).isValid(start));
		 assertFalse (new SlideMove(8,0,2,1,9).isValid(start));
		 
		SlideMove sm = new SlideMove(8,0,2,1,2);
		assertEquals ("move 8", sm.toString());
		assertTrue (sm.isValid(start));
		assertTrue (sm.execute(start));
		
		// no longer available.
		assertFalse (sm.execute(start));
		
		assertEquals (8, start.cell(1,2));
		assertTrue (sm.undo(start));
		
		EightPuzzleNode eightStart = new EightPuzzleNode(new int[][]{
				{8,1,3},
				{2,4,5},
				{0,7,6}});
		
		try {
			sm.isValid(eightStart);
			fail ("Fails to detect invalid node.");
		} catch (IllegalArgumentException iae) {
			
		}
	
		try {
			sm.undo(eightStart);
			fail ("Fails to detect invalid node.");
		} catch (IllegalArgumentException iae) {
			
		}
		
		try {
			sm.execute(eightStart);
			fail ("Fails to detect invalid node.");
		} catch (IllegalArgumentException iae) {
			
		}
	 }
}
