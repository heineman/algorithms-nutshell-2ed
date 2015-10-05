package algs.model.tests.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.gametree.MoveEvaluation;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.DefaultEvaluation;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.RandomPlayer;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

public class DefaultEvaluationTest {

	// help initialize boards in test cases.
	static char o = 'O';
	static char x = 'X';
	static char _ = ' ';
	
	@Test
	public void testBoardEvaluation() {
		// X is the random player
		RandomPlayer xPlayer = new RandomPlayer(Player.XMARK);
		StraightLogic logic = new StraightLogic();

		xPlayer.logic(logic);
		xPlayer.score(new BoardEvaluation());
		TicTacToeBoard board = new TicTacToeBoard();
		TicTacToeState state = new TicTacToeState(board, logic);

		// 2-move lookahead, for O.
		RandomPlayer oPlayer = new RandomPlayer(Player.OMARK);		
		oPlayer.logic(logic);
		oPlayer.score(new DefaultEvaluation());

		new PlaceMark(0,0, (Player) xPlayer).execute(state);
		new PlaceMark(1,0, (Player) oPlayer).execute(state);
		
		// no win yet
		assertEquals (1, xPlayer.eval(state));
		assertEquals (0, oPlayer.eval(state));
		
		new PlaceMark(1,1, (Player) xPlayer).execute(state);
		new PlaceMark(2,1, (Player) oPlayer).execute(state);
		
		// nothing yet
		assertEquals (2, xPlayer.eval(state));
		assertEquals (0, oPlayer.eval(state));

		new PlaceMark(2,2, (Player) xPlayer).execute(state);
		
		// we at least have a win.
		assertEquals (MoveEvaluation.maximum(), xPlayer.eval(state));
		assertEquals (MoveEvaluation.minimum(), oPlayer.eval(state));

	}

	
	@Test
	public void testScore() {
		// X is the random player
		RandomPlayer xPlayer = new RandomPlayer(Player.XMARK);
		StraightLogic logic = new StraightLogic();

		xPlayer.logic(logic);
		xPlayer.score(new BoardEvaluation());
		TicTacToeBoard board = new TicTacToeBoard();
		TicTacToeState state = new TicTacToeState(board, logic);

		// 2-move lookahead, for O.
		Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
		oPlayer.logic(logic);
		oPlayer.score(new DefaultEvaluation());

		new PlaceMark(1,1, (Player) xPlayer).execute(state);

		// shows inability of DefaultEvaluation to recognize bad state, whereas BoardEvaluation shows
		// x is in good position.
		assertEquals (0, oPlayer.eval(state));
		assertEquals (4, xPlayer.eval(state));

	}
	
	@Test
	public void testDefaultEvalCases() {
		Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
		StraightLogic logic = new StraightLogic();
		oPlayer.logic(logic);
		DefaultEvaluation de = new DefaultEvaluation();
		oPlayer.score(de);
		
		Object[][] cases = {
				{ new TicTacToeBoard(new char[][]{{x,x,o},{x,x,o},{o,o,_}}), 10000 },
				{ new TicTacToeBoard(new char[][]{{x,x,o},{_,o,_},{_,_,_}}), 1000 } ,
				{ new TicTacToeBoard(new char[][]{{x,x,o},{o,o,x},{x,x,o}}), 0 },
				{ new TicTacToeBoard(new char[][]{{x,x,o},{x,x,o},{o,_,o}}), Integer.MAX_VALUE },
		};
		
		for (int i = 0; i < cases.length; i++) {
			assertEquals (cases[i][1], de.score(new TicTacToeState((TicTacToeBoard)cases[i][0], logic), oPlayer));
		}
	}

}
