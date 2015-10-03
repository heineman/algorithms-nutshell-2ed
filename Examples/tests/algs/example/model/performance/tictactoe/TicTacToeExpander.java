package algs.example.model.performance.tictactoe;


import algs.example.gui.problems.tictactoe.controller.InteractivePlayer;
import algs.model.gametree.IGameState;
import algs.model.gametree.MoveEvaluation;
import algs.model.gametree.Pair;
import algs.model.list.List;
import algs.model.list.Node;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.Logic;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

/**
 * Identify the full Game Tree for 3x3 TicTacToe board and print some 
 * statistics.
 * 
 * @author George Heineman
 */
public class TicTacToeExpander {

	public static final boolean verbose = false;
	
	/** Reduced unique set of positions. */
	public static List<TicTacToeBoard> unique; 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameTreeExpander expander = new GameTreeExpander();

		Logic logic = new StraightLogic();
		TicTacToeState gameState = new TicTacToeState(new TicTacToeBoard(), logic);
		
		Player xPlayer = new InteractivePlayer(Player.XMARK);
		xPlayer.logic(logic);
		xPlayer.score(new BoardEvaluation());
		Player oPlayer = new InteractivePlayer(Player.OMARK);
		oPlayer.logic(logic);
		oPlayer.score(new BoardEvaluation());
		
		// look nine moves into the future.
		MoveEvaluation move = expander.computeBest(9, gameState, xPlayer, oPlayer);

		System.out.println ("Best move:" + move.move + " with score " + move.score);
		System.out.println (expander.getNumComputationalStates() + " states computed.");
		System.out.println (expander.getStatesSeenSoFar().size() + " distinct states visited.");
		
		// Compute unique board states [may take a while]
		unique = new List<TicTacToeBoard>(); 
		
		// for each state seen...
		Node<Pair> n = expander.getStatesSeenSoFar().head();
		int idx = 0;
		while (n != null) {
			IGameState state = n.value().state;
			TicTacToeBoard tb = ((TicTacToeState) state).board();
			
			Node<TicTacToeBoard> p = unique.head();
			
			if (idx++ %100 == 0) { System.out.println (idx); }
			
			// check to see if the board is already in the unique 
			boolean add = true;
			while (p != null) {
				
				if (tb.sameBoard(p.value())) {
					add = false;
					break;
				}
				
				p = p.next();
			}
			
			if (add) {
				if (verbose) {
					if (unique.size() % 100 == 0) { System.out.println ("Unique size: " + unique.size() + "..."); }
				}
				unique.append(tb);
			}
			
			n = n.next();
		}
		
		System.out.println (unique.size() + " distinct states visited.");
		
	}
}
