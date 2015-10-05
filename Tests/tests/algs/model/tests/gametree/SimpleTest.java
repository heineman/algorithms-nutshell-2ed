package algs.model.tests.gametree;

import org.junit.Test;

import algs.model.gametree.IGameState;
import algs.model.gametree.MoveEvaluation;
import algs.model.gametree.Pair;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;
import junit.framework.TestCase;

public class SimpleTest extends TestCase {

	@Test
	public void testSimple() {
		MoveEvaluation me = new MoveEvaluation();
		assertEquals ("null for " + MoveEvaluation.minimum(), me.toString());
	}
	
	@Test
	public void testPair() {
		TicTacToeBoard board = new TicTacToeBoard();
		IGameState gameState = new TicTacToeState(board, new StraightLogic());
		MoveEvaluation me = new MoveEvaluation();
		Pair p = new Pair(gameState, me);
		assertEquals (gameState, p.state);
		assertEquals (me, p.move);
	}
}
