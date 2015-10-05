package algs.model.tests.chapter7;

import org.junit.Test;

import algs.model.gametree.IGameState;
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

public class AlphaBetaExampleTest extends TestCase {

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
	    IGameState state = new TicTacToeState(board, logic);
	    
	     // pre-initialize board to 1.5 moves
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(2,0, (Player) oPlayer).execute(state);
	    new PlaceMark(2,2, (Player) xPlayer).execute(state);

	    algs.model.gametree.debug.AlphaBetaEvaluation ae = 
	    	new algs.model.gametree.debug.AlphaBetaEvaluation(2);
	    TicTacToeDebugger std = new TicTacToeDebugger();
		ae.debug(std);
			
		IGameMove move = ae.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		System.out.println (std.getInputString());
		
		// Only looking ahead, this is the best it can come up with.
		assertEquals (0, ((PlaceMark)move).getColumn());
		assertEquals (0, ((PlaceMark)move).getRow());
	}
	
}
