package algs.model.tests.tictactoe;

import org.junit.Test;

import algs.model.gametree.debug.AlphaBetaEvaluation;
import algs.model.gametree.IGameMove;
import algs.model.gametree.IPlayer;
import algs.model.problems.tictactoe.debug.TicTacToeDebugger;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

import junit.framework.TestCase;

/** 
 * Specific Worked example, with reduced move sets to showcase just what I want 
 * to show.
 * 
 * @author George
 *
 */
public class ShowBetaPruneDebugTest extends TestCase {
		
	@Test
	public void testAlphaBeta() {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		IPlayer xPlayer = new SpecializedXBPlayer(ShowBetaPruneTest.x, logic);
		xPlayer.score(new BoardEvaluation());
		
		IPlayer oPlayer = new SpecializedOBPlayer (ShowBetaPruneTest.o, logic);
	    oPlayer.score(new BoardEvaluation());
	    
	    ((SpecializedXBPlayer) xPlayer).opponent(oPlayer);
	    ((SpecializedOBPlayer) oPlayer).opponent(xPlayer);

	    // start at this initial state.
	    TicTacToeBoard board = new TicTacToeBoard(ShowBetaPruneTest.boards[0]);
	    TicTacToeState state = new TicTacToeState(board, logic);
	    
	    // two ply lookahead.
	    AlphaBetaEvaluation ae = new AlphaBetaEvaluation(2);
	    TicTacToeDebugger std = new TicTacToeDebugger();
		ae.debug(std);
		
		IGameMove move = ae.bestMove (state, xPlayer, oPlayer);
		System.out.println ("best move:" + move);
		System.out.println (std.getInputString());
	}
	
}
