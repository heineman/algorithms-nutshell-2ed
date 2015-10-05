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
public class DebugAndNonDebugTest extends TestCase {
	
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
	    
	    // 2-move lookahead, for O.
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
		    String dbgAlphaBetaString;
		    
			// move X then O then X again.
			xPlayer.decideMove(state);
			oPlayer.decideMove(state);
			xPlayer.decideMove(state);

			// two ply lookahead for MiniMax
			algs.model.gametree.debug.AlphaBetaEvaluation dabe =
				new algs.model.gametree.debug.AlphaBetaEvaluation(2);
			dabe.debug(std);
		    
			IGameMove move1 = dabe.bestMove (state, oPlayer, xPlayer);
			dbgAlphaBetaString = std.getInputString();
			
			// two ply look ahead for AlphaBeta
			algs.model.gametree.AlphaBetaEvaluation abe =
				new algs.model.gametree.AlphaBetaEvaluation(2);

			IGameMove move2 = abe.bestMove (state, oPlayer, xPlayer);
		    
			if (!move1.equals(move2)) {
				System.err.println ("failed at " + i);
				System.err.println (state);
				System.err.println ("minimax: " + move1);
				System.err.println ("negmax: " + move2);
				System.err.println ("--------------------------------------");
				System.err.println (dbgAlphaBetaString);
				System.err.println ("--------------------------------------");
				fail ("invalid result");
			}
		}
	}
	
}
