package algs.example.model.performance.tictactoe;

import org.junit.Test;

import algs.model.list.Node;
import algs.model.problems.tictactoe.model.TicTacToeBoard;

import junit.framework.TestCase;

public class TestBoardComputation extends TestCase {

	/**
	 * http://www.mathrec.org/old/2002jan/solutions.html
	 */
	@Test
	public void testCountsAtEachLevel() {
		int[] expectedLevelCounts = {1, 3, 12, 38, 108, 174, 204, 153, 57, 15};
		int[] expectedTerminalCounts =   {0, 0, 0,   0,   0,  21,  21,  58, 23, 15};
		
		int[] actualLevelCounts = new int [expectedLevelCounts.length];
		int[] actualTerminalCounts = new int [expectedTerminalCounts.length];
		
		TicTacToeExpander.main(new String[]{});
		
		Node<TicTacToeBoard> n = TicTacToeExpander.unique.head();
		
		while (n != null) {
			TicTacToeBoard board = n.value();
			
			int ct = 0;
			for (int c = 0; c <= 2; c++) {
				for (int r = 0; r <= 2; r++) {
					if (!board.isClear(c,r)) {
						ct++;
					}
				}
			}
			
			if (board.gameWon() || board.isDraw()) {
				actualTerminalCounts[ct]++;
			}
				
			actualLevelCounts[ct]++;				
			
			n = n.next();
		}
		
		
		for (int i = 0; i < expectedTerminalCounts.length; i++) {
			assertEquals (expectedTerminalCounts[i], actualTerminalCounts[i]);
		}
		for (int i = 0; i < expectedLevelCounts.length; i++) {
			assertEquals (expectedLevelCounts[i], actualLevelCounts[i]);
		}

	}
	
	@Test
	public void testSame() {
		TicTacToeBoard b1 = new TicTacToeBoard();
		TicTacToeBoard b2 = new TicTacToeBoard();
		
		// empty boards are the same.
		assertTrue (b1.sameBoard(b2));
		
		// verify corners show same symmetry
		b1.set(0, 0, 'X');
		
		b2.set(2, 0, 'X');
		assertTrue (b1.sameBoard(b2));
		b2.clear(2, 0);
		
		b2.set(0, 2, 'X');
		assertTrue (b1.sameBoard(b2));
		b2.clear(0, 2);
		
		b2.set(2, 2, 'X');
		assertTrue (b1.sameBoard(b2));
		b2.clear(2, 2);
		
		
	}
}
