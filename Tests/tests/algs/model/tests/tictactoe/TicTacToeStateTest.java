package algs.model.tests.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

public class TicTacToeStateTest {

	@Test
	public void testState() {
		TicTacToeBoard board = new TicTacToeBoard();
		StraightLogic logic = new StraightLogic();
		TicTacToeState tts = new TicTacToeState(board, logic);
		board.set(1, 1, Player.XMARK);
		assertTrue (logic == tts.logic());
		assertFalse (tts.equivalent(null));
		
		TicTacToeBoard board2 = new TicTacToeBoard();
		StraightLogic logic2 = new StraightLogic();
		TicTacToeState tts2 = new TicTacToeState(board2, logic2);
		
		board2.set(1, 1, Player.XMARK);
		
		tts.equivalent(tts2);
		
		// store some data
		Object o = new Object();
		tts.storedData(o);
		assertTrue (o == tts.storedData());
		
		// final reset works.
		TicTacToeBoard ttb = new TicTacToeBoard();
		tts.reset(ttb);
		assertTrue (ttb == tts.board());
	}


}
