package algs.model.tests.tictactoe;

import org.junit.Test;

import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import junit.framework.TestCase;

public class TicTacToeBoardTest extends TestCase {

	@Test
	public void testBasics() {
		TicTacToeBoard b1 = new TicTacToeBoard(
				new char[][]{
						{Player.XMARK, TicTacToeBoard.EMPTY, Player.OMARK},
						{Player.OMARK, TicTacToeBoard.EMPTY, Player.XMARK},
						{TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY}
				});
		
		TicTacToeBoard b2 = new TicTacToeBoard();
		assertTrue (b2.isClear());
		assertEquals (new TicTacToeBoard().hashCode(), b2.hashCode());
		
		assertFalse (b1.isClear());
		assertTrue (b1.isClear(0, 2));
		
		// validate that set can't overwrite
		assertFalse (b1.set(0, 0, Player.OMARK));
	}
	
	@Test
	public void testSameBoard() {
		TicTacToeBoard b1 = new TicTacToeBoard(
				new char[][]{
						{Player.XMARK, TicTacToeBoard.EMPTY, Player.OMARK},
						{Player.OMARK, TicTacToeBoard.EMPTY, Player.XMARK},
						{TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY}
				});
		
		TicTacToeBoard b2 = new TicTacToeBoard(
				new char[][]{
						{Player.OMARK, TicTacToeBoard.EMPTY, Player.XMARK},
						{Player.XMARK, TicTacToeBoard.EMPTY, Player.OMARK},
						{TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY}
				});
				
		
		TicTacToeBoard b3 = new TicTacToeBoard(
				new char[][]{
						{Player.XMARK, TicTacToeBoard.EMPTY, Player.XMARK},
						{Player.OMARK, TicTacToeBoard.EMPTY, Player.OMARK},
						{TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY}
				});
		
		// same board with respect to rotations...
		assertTrue (b1.sameBoard(b2));
		assertFalse (b1.equals(b2));   // equals must be exact orientation
		assertFalse (b1.equals ("GARBAGE"));  // sanity check.
		assertFalse (b1.equals (null));  // sanity check.
		assertFalse (b1.sameBoard(b3));
	}
	
	@Test
	public void testWinnings() {
		TicTacToeBoard b1 = new TicTacToeBoard(
				new char[][]{
						{Player.XMARK, TicTacToeBoard.EMPTY, Player.OMARK},
						{Player.OMARK, TicTacToeBoard.EMPTY, Player.XMARK},
						{TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY}
				});
		
		// validate this as sanity check.
		assertFalse (b1.gameWon(TicTacToeBoard.EMPTY));
		
		TicTacToeBoard b2 = new TicTacToeBoard(
				new char[][]{
						{Player.XMARK, TicTacToeBoard.EMPTY, Player.OMARK},
						{Player.OMARK, Player.XMARK, TicTacToeBoard.EMPTY},
						{TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY, Player.XMARK}
				});
		
		// validate this as sanity check.
		assertTrue (b2.gameWon(Player.XMARK));
		
	}
	
	@Test
	public void testbadInput() {
		try {
			new TicTacToeBoard(
				new char[][]{
						{Player.XMARK, TicTacToeBoard.EMPTY, Player.OMARK, Player.OMARK}, // BAD LINE
						{Player.OMARK, TicTacToeBoard.EMPTY, Player.XMARK},
						{TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY}
				});
			fail ("TicTacToeBoard constructor should detect invalid input.");
		} catch (IllegalArgumentException iae) {
			// success
		}
		
		try {
			new TicTacToeBoard(
				new char[][]{
						{Player.XMARK, TicTacToeBoard.EMPTY, 'T'}, // BAD LINE
						{Player.OMARK, TicTacToeBoard.EMPTY, Player.XMARK},
						{TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY}
				});
			fail ("TicTacToeBoard constructor should detect invalid character.");
		} catch (IllegalArgumentException iae) {
			// success
		}
		
		try {
			new TicTacToeBoard(
				new char[][]{
						{Player.XMARK, TicTacToeBoard.EMPTY, Player.XMARK}, // BAD LINE
						{Player.OMARK, TicTacToeBoard.EMPTY, Player.XMARK},
						{TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY}
				});
			fail ("TicTacToeBoard constructor should detect invalid state (too many X's.");
		} catch (IllegalArgumentException iae) {
			// success
		}
		
		try {
			new TicTacToeBoard(
				new char[][]{
						{Player.OMARK, TicTacToeBoard.EMPTY, Player.XMARK}, // BAD LINE
						{Player.OMARK, TicTacToeBoard.EMPTY, Player.OMARK},
						{TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY, TicTacToeBoard.EMPTY}
				});
			fail ("TicTacToeBoard constructor should detect invalid state (too many O's.");
		} catch (IllegalArgumentException iae) {
			// success
		}
	}
}
