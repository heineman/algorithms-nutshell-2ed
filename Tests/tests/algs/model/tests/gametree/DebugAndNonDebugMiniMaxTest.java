package algs.model.tests.gametree;

import org.junit.Test;

import algs.model.gametree.IGameMove;
import algs.model.problems.tictactoe.debug.TicTacToeDebugger;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.RandomPlayer;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

import junit.framework.TestCase;

/** Validate that NegMax produces same results as MiniMax. */
public class DebugAndNonDebugMiniMaxTest extends TestCase {
	
	RandomPlayer xPlayer;
	RandomPlayer oPlayer;
	StraightLogic logic;
	
	public void setUp () {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		logic = new StraightLogic();
		
		// Random Player, using the BoardEvaluation function as described in Nilsson.
	    xPlayer = (RandomPlayer) PlayerFactory.createPlayer(PlayerFactory.Random, Player.XMARK);
	    xPlayer.logic(logic);
	    xPlayer.score(new BoardEvaluation());
	    
	    // Random Player, for O.
	    oPlayer = (RandomPlayer) PlayerFactory.createPlayer(PlayerFactory.Random, Player.OMARK);
	    oPlayer.logic(logic);
	    oPlayer.score(new BoardEvaluation());
	}
	
	@Test
	public void testMiniMaxSameAsNegMax() {
		for (int i = 0; i < 50; i++) {
			TicTacToeBoard board = new TicTacToeBoard();
			TicTacToeState state = new TicTacToeState(board, logic);
			TicTacToeDebugger std = new TicTacToeDebugger();
		    String dbgString;
		    
			// move X then O then X again.
			xPlayer.decideMove(state);
			oPlayer.decideMove(state);
			xPlayer.decideMove(state);
			
			// two ply lookahead for MiniMax
			algs.model.gametree.debug.MinimaxEvaluation mme =
				new algs.model.gametree.debug.MinimaxEvaluation(2);
			mme.debug(std);
		    
			IGameMove move1 = mme.bestMove (state, oPlayer, xPlayer);
			dbgString = std.getInputString();
			
			// two ply look ahead for AlphaBeta
			algs.model.gametree.MinimaxEvaluation mme2 =
				new algs.model.gametree.MinimaxEvaluation(2);

			IGameMove move2 = mme2.bestMove (state, oPlayer, xPlayer);
		    
			if (!move1.equals(move2)) {
				System.out.println ("failed at " + i);
				System.out.println (state);
				System.out.println ("minimax-debug: " + move1);
				System.out.println ("minimax: " + move2);
				System.out.println ("--------------------------------------");
				System.out.println (dbgString);
				System.out.println ("--------------------------------------");
				fail ("invalid result");
			}
		}
	}
	
}
