package algs.model.gametree.debug;

import java.util.*;

import algs.debug.IDebugSearch;
import algs.debug.Legend;
import algs.model.gametree.*;

/**
 * Initiate MinimaxEvaluation over the given game state and ply.
 * 
 * Uses dotty debugging output for viewing. Note that the implementation provided
 * here is quite different from the non-debug version, since its aim is not for
 * speed but for the accurate portrayal of the path finding algorithm as it 
 * executes.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MinimaxEvaluation implements IEvaluation {
	
	/** Game state. */
	IGameState state;
	
	/** Ply depth. */
	int ply;
	
	/** Player from whose perspective all board evaluations are carried out. */
	IPlayer original;
	
	/** Debugger to use. */
	IDebugSearch debug;
	
	/** Debug information across bestMove/minimax. */
	Stack<MinMaxNode> debugHome = new Stack<MinMaxNode>();
	
	/**
	 * Create an evaluator with the given state. It is important that
	 * the same player evaluate the board regardless of MIN and MAX. The
	 * player will be known when <code>bestMove</code> is invoked.
	 * 
	 * @param ply       Depth to search.
	 */
	public MinimaxEvaluation (int ply) {
		this.ply = ply;
	}
	
	/**
	 * Initiates the MiniMax computations by determining the maximum number of
	 * moves in advance to look.
	 * 
	 * Note that player is the INTELLIGENT player making the move; the opponent may
	 * be a human player but also may be an intelligent player.
	 * 
	 * @param state              Game state being evaluated
	 * @param player             The player making the next move
	 * @param opponent           The player's opponent
	 */
	public IGameMove bestMove (IGameState state, IPlayer player, IPlayer opponent) {
		this.original = player;
		this.state = state;
		
		MinMaxNode myHome = null;
		if (debug != null) {
			IGameState debugCopy = state.copy();
			debug.visitNode(new Legend("MinMax (ply:" + ply + ", player:" + player + ")"));
			debug.visitNode(debugCopy); debug.markStart(debugCopy);
		
			myHome = new MinMaxNode (IComparator.MAX);
			debug.visitNode(myHome); debug.visitEdge(debugCopy, myHome);
		}
		
		// For each available move, evaluate and compute maximum.
		IGameMove response = null;
		int best = IComparator.MAX.initialValue();
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
			
			//  children are MIN boards...
			int score = minimax(ply-1, IComparator.MIN, opponent, player);
			move.undo(state);
			
			if (IComparator.MAX.compare(best, score) < 0) {
				response = move;
				best = score;
			}			
		}
		
		if (debug != null) {
			MinMaxNode mmn = debugHome.pop();
			mmn.value(best);
		}
		return response;
	}
	
	/**
	 * Given the board state, use minimax algorithm to locate best move 
	 * for original player. 
	 * <p>
	 * Note that the initial invocation of this method will be on MIN boards
	 * <p>
	 * 
	 * @param ply        the fixed depth to look ahead.
	 * @param selector   the type (MIN or MAX) of this level, to evaluate moves. MAX selects
	 *                   the best move while MIN selects the worst moves.
	 * @param player     the current player.
	 * @param opponent   the opponent.
	 */
	private int minimax (int ply, 
			IComparator selector, 
			IPlayer player, IPlayer opponent) {

		// If no allowed moves or a leaf node, return board state score.
		Iterator<IGameMove> it = player.validMoves(state).iterator();
		if (ply == 0 || !it.hasNext()) {
			return original.eval(state); 
		}

		// Debug information 
		MinMaxNode minmax = null;
		if (debug != null) { 
			minmax = new MinMaxNode (selector);
			debug.visitNode(minmax); debug.visitEdge(state.copy(), minmax);
			debugHome.push(minmax);
		}
	
		// get all moves for this player and generate the boards that result
		// from making these moves. Use the type evaluator (either MAX or MIN)
		// to select a move. If none is available, then null will be returned.
		int best = selector.initialValue();
		while (it.hasNext()) {
			IGameMove move = it.next();
			
			if (move.execute(state)) {
				state.incrementCounter();
				int moveScore;
				IGameState nextCopy = state.copy();
				
				if (debug != null) {
					nextCopy = state.copy();
					debug.visitNode(nextCopy); debug.visitEdge(debugHome.peek(), nextCopy);
				}				
				
				// Recursively evaluate position. Compute MiniMax and swap
				// player and opponent, synchronously with MIN and MAX.
				moveScore = minimax (ply-1, selector.opposite(),
									 opponent, player);

				// only show leaf node scores...
				if (debug != null && ply == 1) {
					ScoreNode scoreNode = new ScoreNode(moveScore);
					debug.visitNode(scoreNode); debug.visitEdge(nextCopy, scoreNode);
				}
				
				// Only record the evaluation for this board state.
				if (selector.compare(best, moveScore) < 0) {
					best = moveScore;
				}
			}
			
			// undo the move to prepare for the next move to make.
			move.undo(state);
		}
		
		if (debug != null) {
			MinMaxNode mmn = debugHome.pop();
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
	 * @param debug    debugger to use
	 */
	public void debug(IDebugSearch debug) {
		this.debug = debug;
	}
}
