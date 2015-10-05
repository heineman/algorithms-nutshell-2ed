package algs.model.tests.gametree;

import org.junit.Test;

import algs.model.gametree.IGameMove;
import algs.model.problems.tictactoe.debug.TicTacToeDebugger;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

import junit.framework.TestCase;

public class NegMaxDebugTest extends TestCase {
	
	@Test
	public void testNegmax() {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
	    Player xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.NegMax, Player.XMARK, 2);
	    xPlayer.logic(logic);
	    xPlayer.score(new BoardEvaluation());
	    
	    // 2-move lookahead, for O.
	    Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.NegMax, Player.OMARK, 2);
	    oPlayer.logic(logic);
	    oPlayer.score(new BoardEvaluation());
	    
	    TicTacToeBoard board = new TicTacToeBoard();
	    TicTacToeState state = new TicTacToeState(board, logic);
	    
	    // pre-initialize board to 2.5 moves
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(0,0, (Player) oPlayer).execute(state);
	    new PlaceMark(0,2, (Player) xPlayer).execute(state);

	    // two ply lookahead.
	    algs.model.gametree.NegMaxEvaluation me =
	    	new algs.model.gametree.NegMaxEvaluation(2);
	    IGameMove move = me.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		
		// we really know that this move is a placemark
		assertEquals (2, ((PlaceMark)move).getColumn());
		assertEquals (0, ((PlaceMark)move).getRow());
		
		// for closure of test cases
		me.toString();
	}
	
	@Test
	public void testNegmaxDebug() {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
	    Player xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.NegMax, Player.XMARK, 2);
	    xPlayer.logic(logic);
	    xPlayer.score(new BoardEvaluation());
	    
	    // 2-move lookahead, for O.
	    Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.NegMax, Player.OMARK, 2);
	    oPlayer.logic(logic);
	    oPlayer.score(new BoardEvaluation());
	    
	    TicTacToeBoard board = new TicTacToeBoard();
	    TicTacToeState state = new TicTacToeState(board, logic);
	    
	    // pre-initialize board to 2.5 moves
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(0,0, (Player) oPlayer).execute(state);
	    new PlaceMark(0,2, (Player) xPlayer).execute(state);

	    // two ply lookahead.
	    algs.model.gametree.debug.NegMaxEvaluation me =
	    	new algs.model.gametree.debug.NegMaxEvaluation(2);
	    TicTacToeDebugger std = new TicTacToeDebugger();
	    me.debug(std);
		IGameMove move = me.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		System.out.println (std.getInputString());
		
		// we really know that this move is a placemark
		assertEquals (2, ((PlaceMark)move).getColumn());
		assertEquals (0, ((PlaceMark)move).getRow());
		
		// for closure of test cases
		me.toString();
	}
}
