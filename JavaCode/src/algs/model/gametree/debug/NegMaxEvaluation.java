package algs.model.gametree.debug;

import java.util.*;

import algs.debug.IDebugSearch;
import algs.debug.Legend;
import algs.model.gametree.*;

/**
 * Represents an Intelligent Tic Tac Toe playing agent that uses the NegMax
 * algorithm to select a move. 
 * 
 * This implementation is quite different from the non-debug version; this was done
 * because of the convoluted nature of the debugging code to generate the game
 * trees. After execution, the debug information is properly invoked on the 
 * provided IDebugSearch interface.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class NegMaxEvaluation implements IEvaluation {
	
	/** Game state. */
	IGameState state;
	
	/** Ply depth. */
	int ply;
	
	/** Debugger to use. */
	IDebugSearch debug;
	
	/** Debug information across bestMove/minimax. */
	Stack<NegMaxNode> debugHome = new Stack<NegMaxNode>();
	
	/**
	 * Create an evaluator with the given state. It is important that
	 * the same player evaluate the board regardless of MIN and MAX. The
	 * player will be known when <code>bestMove</code> is invoked.
	 * 
	 * @param ply       Depth to search.
	 */
	public NegMaxEvaluation (int ply) {
		this.ply = ply;
	}
	
	/**
	 * Initiates the MiniMax computations by determining the maximum number of
	 * moves in advance to look.
	 * 
	 * Note that player is the INTELLIGENT player making the move; the opponent may
	 * be a human player but also may be an intelligent player.
	 * 
	 * @param state              Game state being evaluated.
	 * @param player             The player making the next move.
	 */
	public IGameMove bestMove (IGameState state, IPlayer player, IPlayer opponent) {
		this.state = state;
		
		NegMaxNode myHome = null;
		if (debug != null) {
			IGameState debugCopy = state.copy();
			debug.visitNode(new Legend("NegMax (ply:" + ply + ", player:" + player + ")"));
			debug.visitNode(debugCopy); debug.markStart(debugCopy);
		
			myHome = new NegMaxNode ();
			debug.visitNode(myHome); debug.visitEdge(debugCopy, myHome);
		}
		
		// Select "maximum of the negative scores of children."
		IGameMove response = null;
		int best = Integer.MIN_VALUE;
		for (Iterator<IGameMove> it = player.validMoves(state).iterator(); it.hasNext(); ) {
			IGameMove move = it.next();
			
			if (debug != null) {
				debugHome.push(myHome);
			}
			
			move.execute(state);
			state.incrementCounter();
			
			if (debug != null) {
				IGameState nextCopy = state.copy();
				debug.visitNode(nextCopy); debug.visitEdge(debugHome.peek(), nextCopy);
			}
			
			int score = negmax(ply-1, opponent, player);
			move.undo(state);
			
			if (IComparator.MAX.compare(best, -score) < 0) {
				response = move;
				best = -score;
			}			
		}
		
		if (debug != null) {
			NegMaxNode mmn = debugHome.pop();
			mmn.value(best);
		}
		return response;
	}
	
	/**
	 * Given the board state, use negmax algorithm to locate best move 
	 * for original player. 
	 * <p>
	 * Scoring comparison is "maximum of the negative value of children".
	 * <p>
	 * 
	 * @param ply        the fixed depth to look ahead.
	 * @param player     the current player.
	 * @param opponent   the opponent.
	 */
	private int negmax (int ply, IPlayer player, IPlayer opponent) {

		// If no allowed moves or a leaf node, return board state score.
		Iterator<IGameMove> it = player.validMoves(state).iterator();
		if (ply == 0 || !it.hasNext()) {
			return player.eval(state); 
		}

		// Debug information 
		NegMaxNode negmax = null;
		if (debug != null) { 
			negmax = new NegMaxNode ();
			debug.visitNode(negmax); debug.visitEdge(state.copy(), negmax);
			debugHome.push(negmax);
		}
	
		// get all moves for this player and generate the boards that result
		// from making these moves. Select "maximum of negative value of children".
		int best = Integer.MIN_VALUE;
		while (it.hasNext()) {
			IGameMove move = it.next();
			
			move.execute(state);
			state.incrementCounter();
			int moveScore;
			IGameState nextCopy = null;
			
			if (debug != null) {
				nextCopy = state.copy();
				debug.visitNode(nextCopy); debug.visitEdge(debugHome.peek(), nextCopy);
			}				
				
			// Recursively evaluate position using consistent negmax
			moveScore = -negmax (ply-1, opponent, player);
			
			// undo the move to prepare for the next move to make.
			move.undo(state);
			
			// only show leaf node scores...
			if (debug != null && ply == 1) {
				ScoreNode scoreNode = new ScoreNode(-moveScore);   // must negate to get actual score! See 7 lines above! 
				debug.visitNode(scoreNode); debug.visitEdge(nextCopy, scoreNode);
			}
				
			// Only record the evaluation for this board state.
			if (moveScore > best) {
				best = moveScore;
			}
		}
		
		if (debug != null) {
			NegMaxNode mmn = debugHome.pop();
			mmn.value(best);
		}
		
		return best;
	}
	
	/** 
	 * Expose board state as string (useful for debugging purposes).
	 */
	public String toString () {
		return state.toString();
	}
	
	/**
	 * Install debugger to use.
	 * 
	 * @param debug   debugger to use.
	 */
	public void debug(IDebugSearch debug) {
		this.debug = debug;
	}
}