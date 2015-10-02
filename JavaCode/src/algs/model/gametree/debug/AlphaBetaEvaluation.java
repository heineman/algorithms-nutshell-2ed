package algs.model.gametree.debug;

import java.util.*;

import algs.debug.IDebugSearch;
import algs.debug.Legend;
import algs.model.gametree.*;
import algs.model.gametree.debug.AlphaBetaDebugNode;
import algs.model.gametree.debug.ScoreNode;

/**
 * Initiate AlphaBeta Evaluation over the given game state and ply.
 * <p>
 * This implementation is quite different from the non-debug version; this was done
 * because of the convoluted nature of the debugging code to generate the game
 * trees. After execution, the debug information is properly invoked on the 
 * provided IDebugSearch interface.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AlphaBetaEvaluation implements IEvaluation {
	
	/** Game state. */
	IGameState state;
	
	/** Ply depth. */
	int ply;
	
	/** Debugger to use. */
	IDebugSearch debug;

	/** Debug information across bestMove/minimax. */
	Stack<AlphaBetaDebugNode> debugHome;
	Stack<IGameState> debugGame;
	
	
	/**
	 * Create an evaluator with the given state. It is important that
	 * the same player evaluate the board regardless of MIN and MAX. The
	 * player will be known when <code>bestMove</code> is invoked.
	 * 
	 * @param ply       Depth to search.
	 */
	public AlphaBetaEvaluation (int ply) {
		this.ply = ply;
	}
	
	/**
	 * Initiates the AlphaBeta computations by determining the maximum number of
	 * moves in advance to look.
	 * <p>
	 * The original game state is copied prior to being processed so no external 
	 * effect occurs. This implementation is derived from NegMax and selects moves
	 * accordingly.
	 * 
	 * @param s                  Game state
	 * @param player             The player making the next move
	 * @param opponent           The player's opponent.
	 */
	public IGameMove bestMove (IGameState s, IPlayer player, IPlayer opponent) {
		this.state = s.copy();
		int alpha = MoveEvaluation.minimum();
		int beta = MoveEvaluation.maximum();

		IGameState copy = null;
		if (debug != null) {
			copy = state.copy();
			debug.visitNode(new Legend("AlphaBeta (ply:" + ply + ", player:" + player + ")"));

			debugHome = new Stack<AlphaBetaDebugNode>();
			debugGame = new Stack<IGameState>();
			
			debugGame.push(copy);
			debugHome.push(new AlphaBetaDebugNode (alpha, beta));
			
			debug.visitNode(debugGame.peek());
			debug.markStart(debugGame.peek());
			
			debug.visitNode(debugHome.peek());
			debug.visitEdge(debugGame.peek(), debugHome.peek());
		}
		
		// Select "maximum of the negative scores of children." based on opponent's move.
		// Until there is a game where the opponent can make a move that immediately allows
		// player to win, there is no way that the logic (alpha >= beta) would ever be 
		// exercised. Still, we leave it in because there is always the possibility that someone
		// could construct such a game.
		IGameMove response = null;
		for (Iterator<IGameMove> it = player.validMoves(state).iterator(); it.hasNext(); ) {
			IGameMove move = it.next();
			
			move.execute(state);
			state.incrementCounter();
			
			if (debug != null) {
				debugGame.push (state.copy());
				debug.visitNode(debugGame.peek()); 
				debug.visitEdge(debugHome.peek(), debugGame.peek());
			}
			
			int score = -alphabeta(ply-1, opponent, player, alpha, beta);
			move.undo(state);
			debugGame.pop ();
			
			// record our best move so fat
			if (score > alpha) {
				alpha = score;
				response = move;
			}
			
			// exceeded our threshold? Then leave now. Note that this doesn't happen in 
			// most two player games, that is, where the opponent can make a move and it 
			// will cause player to immediately win. However, because it could always be
			// a possibility, we have to leave this logic in.
			if (alpha >= beta) {
				if (debug != null) {
					AlphaBetaDebugNode mmn = debugHome.pop();
					mmn.value(alpha);
					AlphaPrune apn = new AlphaPrune();
					debug.visitNode(apn); debug.visitEdge(mmn, apn);
				}				
				return response;
			}
		}
		if (debug != null) {
			AlphaBetaDebugNode mmn = debugHome.pop();
			mmn.value(alpha);
			debugGame.pop();
		}
		return response;
	}
	
	/**
	 * Given the board state, use alphaBeta algorithm to locate best move 
	 * for original player. 
	 * 
	 * @param ply        the fixed depth to look ahead.
	 * @param player     the current player
	 * @param opponent   the opponent
	 */
	private int alphabeta (int ply, IPlayer player, IPlayer opponent, int alpha, int beta) {
		// If no allowed moves or a leaf node, return board state score.
		Iterator<IGameMove> it = player.validMoves(state).iterator();
		if (ply <= 0 || !it.hasNext()) {
			int score = player.eval(state);
			if (debug != null) {
				ScoreNode scoreNode = new ScoreNode(score);
				debug.visitNode(scoreNode); debug.visitEdge(debugGame.peek(), scoreNode);
			}
				
			return score; 
		}

		// Debug information  (debugCopy = here, prev = past)
		if (debug != null) {
			debugHome.push(new AlphaBetaDebugNode (alpha, beta));
			debug.visitNode(debugHome.peek());
			debug.visitEdge(debugGame.peek(), debugHome.peek());
		}
		
		// get all moves for this player and generate the boards that result
		// from making these moves. Select "maximum of negative value of children".
		while (it.hasNext()) {
			IGameMove move = it.next();
			
			move.execute(state);
			state.incrementCounter();
			
			if (debug != null) {
				debugGame.push(state.copy());
				debug.visitNode(debugGame.peek()); 
				debug.visitEdge(debugHome.peek(), debugGame.peek());
			}
			
			// Recursively evaluate position. Compute MiniMax and swap
			// player and opponent, synchronously with MIN and MAX.
			int moveScore = -alphabeta (ply-1, opponent, player, -beta, -alpha);
			move.undo(state);
			
			if (moveScore > alpha) {
				alpha = moveScore;
				
				// prune
				if (alpha >= beta) {
					if (debug != null) {
						AlphaPrune apn = new AlphaPrune();
						debug.visitNode(apn); debug.visitEdge(debugGame.pop(), apn);
						AlphaBetaDebugNode abdn = debugHome.pop();
						abdn.value(alpha);
					}
					return alpha;
				}
			}
			if (debug != null) {
				debugGame.pop();
			}
		}
		
		AlphaBetaDebugNode abdn = debugHome.pop();
		abdn.value(alpha);
		return alpha;
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
	 * @param debug   selected debugger to use
	 */
	public void debug(IDebugSearch debug) {
		this.debug = debug;
	}
}
