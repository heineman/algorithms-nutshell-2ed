package algs.model.performance.gametree;

import algs.model.gametree.IGameMove;
import algs.model.problems.tictactoe.debug.TicTacToeDebugger;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.RandomPlayer;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

/** Validate that NegMax produces same results as MiniMax. */
public class ComboMain {
	
	static RandomPlayer xPlayer;
	static RandomPlayer oPlayer;
	static StraightLogic logic;
	
	public static void setUp () {
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
	
	public static void main(String[] args) {
		setUp();
	
		for (int i = 0; i < 50; i++) {
			TicTacToeBoard board = new TicTacToeBoard();
			TicTacToeState state = new TicTacToeState(board, logic);
			TicTacToeDebugger std = new TicTacToeDebugger();
		    String minMaxString;
		    String negMaxString;
		    String alphaBetaString;
		    
			// move X then O then X again.
			xPlayer.decideMove(state).execute(state);
			oPlayer.decideMove(state).execute(state);
			xPlayer.decideMove(state).execute(state);

			// two ply lookahead for MiniMax
			algs.model.gametree.debug.MinimaxEvaluation mme =
				new algs.model.gametree.debug.MinimaxEvaluation(2);
		    mme.debug(std);
		    
			IGameMove move1 = mme.bestMove (state, oPlayer, xPlayer);
			minMaxString = std.getInputString();
		    
			// two ply look ahead for NegMax
			algs.model.gametree.debug.NegMaxEvaluation nme =
				new algs.model.gametree.debug.NegMaxEvaluation(2);

			std = new TicTacToeDebugger();
			nme.debug(std);
		    IGameMove move2 = nme.bestMove (state, oPlayer, xPlayer);
			negMaxString = std.getInputString();

			// two ply look ahead for AlphaBeta
			algs.model.gametree.debug.AlphaBetaEvaluation abe =
				new algs.model.gametree.debug.AlphaBetaEvaluation(2);

			std = new TicTacToeDebugger();
			abe.debug(std);
		    IGameMove move3 = abe.bestMove (state, oPlayer, xPlayer);
		    alphaBetaString = std.getInputString();
			
			if (!move1.equals(move2)) {
				System.err.println ("failed at " + i);
				System.err.println (state);
				System.err.println ("minimax: " + move1);
				System.err.println ("negmax: " + move2);
				System.err.println ("--------------------------------------");
				System.err.println (minMaxString);
				System.err.println ("--------------------------------------");
				System.err.println (negMaxString);
				System.err.println ("--------------------------------------");
				System.exit(-1);
			}
			
			if (!move2.equals(move3)) {
				System.err.println ("failed at " + i);
				System.err.println (state);
				System.err.println ("minimax: " + move2);
				System.err.println ("alphaBeta: " + move3);
				System.err.println ("--------------------------------------");
				System.err.println (minMaxString);
				System.err.println ("--------------------------------------");
				System.err.println (alphaBetaString);
				System.err.println ("--------------------------------------");
				System.exit(-1);
			}
		}
	}
	
}
