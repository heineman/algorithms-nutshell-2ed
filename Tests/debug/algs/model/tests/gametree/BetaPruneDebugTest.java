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

public class BetaPruneDebugTest extends TestCase {
	
	
	@Test
	public void testAlphaBeta() {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
	    Player xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.XMARK, 2);
	    xPlayer.logic(logic);
	    xPlayer.score(new BoardEvaluation());
	    
	    // 2-move lookahead, for O.
	    Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
	    oPlayer.logic(logic);
	    oPlayer.score(new BoardEvaluation());
	    
	    TicTacToeBoard board = new TicTacToeBoard();
	    TicTacToeState state = new TicTacToeState(board, logic);
	    
	    // two ply lookahead.
	    algs.model.gametree.debug.AlphaBetaEvaluation ae = 
	    	new algs.model.gametree.debug.AlphaBetaEvaluation(0);
	    TicTacToeDebugger std = new TicTacToeDebugger();
		ae.debug(std);
		
		IGameMove move = ae.bestMove (state, xPlayer, oPlayer);
		System.out.println ("best move:" + move);
		System.out.println (std.getInputString());
		
		// we really know that this move is a placemark
		assertEquals (1, ((PlaceMark)move).getColumn());
		assertEquals (1, ((PlaceMark)move).getRow());
		System.out.println (std.numNodes() + " nodes expanded");
	}
	
}
