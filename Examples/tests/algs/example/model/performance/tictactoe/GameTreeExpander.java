package algs.example.model.performance.tictactoe;

import java.util.Iterator;

import algs.model.gametree.IComparator;
import algs.model.gametree.IGameMove;
import algs.model.gametree.IGameState;
import algs.model.gametree.IPlayer;
import algs.model.gametree.MoveEvaluation;
import algs.model.gametree.Pair;
import algs.model.list.List;
import algs.model.list.Node;

/**
 * Identify the full Game Tree and prints some statistics. Stores past states
 * viewed during the expansion and, by doing so, reduces the overall computation
 * at the expense (often) of a potentially huge number of states. 
 * <p>
 * Written as a standalone class because it deals with unique state positions
 * in a way that is only important for the chapter.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class GameTreeExpander {

	/** Game state under inspection. */
	private IGameState state;
	
	/** Original player for which all evaluations are measured. */
	private IPlayer original;
	
	/** State count. */
	private int numComputationalStates;
	
	/** 
	 * States seen so far for which a score is present.
	 */
	private List<Pair> list = new List<Pair>();
	
	/** Add state to our list. */
	private void addToList (IGameState state, MoveEvaluation m) {
		list.append(new Pair(state.copy(), m));
	}
	
	/**
	 * Initiates the MiniMax computations by using its ply to determine the maximum
	 * number of moves in advance to look.
	 * <p>
	 * If no moves are available to player, then null is returned.
	 * 
	 * @param s                  Initial game state
	 * @param player             The player making the next move
	 * @param opponent           The player's opponent
	 */
	public MoveEvaluation computeBest (int ply, IGameState s, IPlayer player, IPlayer opponent) {
		this.original = player;
		this.state = s.copy();
		MoveEvaluation move = minimax(ply, IComparator.MAX, player, opponent);
		return move;
	}  
	
	/**
	 * Given the board state, use minimax algorithm to locate best move 
	 * for original player. 
	 * <p>
	 * Note that the initial invocation of this method will be on MIN boards
	 * <p>
	 * 
	 * @param ply        the fixed depth to look ahead.
	 * @param comp       the type (MIN or MAX) of this level, to evaluate moves. MAX selects
	 *                   the best move while MIN selects the worst moves.
	 * @param player     the current player.
	 * @param opponent   the opponent.
	 */
	private MoveEvaluation minimax (int ply, IComparator comp, 
			IPlayer player, IPlayer opponent) {

		// Try to improve on this lower-bound (based on selector). Reflects no move possible.
		MoveEvaluation best = new MoveEvaluation (comp.initialValue());

		// get all moves for this player and generate the boards that result
		// from making these moves. Select maximum of children if we are MAX
		// and minimum of children if we are MIN
		Iterator<IGameMove> it = player.validMoves(state).iterator(); 
		while (it.hasNext()) {
			IGameMove move = it.next();
			
			move.execute(state);
			numComputationalStates++;
			
			// debugging output along the way...
			if (numComputationalStates % 100 == 0) {
				System.out.println (numComputationalStates);
			}
			
			MoveEvaluation pathMove = alreadyDetermined(state);
			
			if (pathMove == null) {
				int trial;
				if (ply <= 0) {
					trial = original.eval(state);
				} else {
					// Recursively evaluate position. Compute MiniMax and swap
					// player and opponent, synchronously with MIN and MAX. If no move is
					// associated, then evaluate the generated board from original player
					MoveEvaluation me = minimax (ply-1, comp.opposite(), opponent, player);
					if (me.move == null) {
						trial = original.eval(state);
					} else {
						trial = me.score;
					}
					
					pathMove = new MoveEvaluation (move, trial);
					
				}
			}
			
			move.undo(state);
			
			// If we are selected as the better move, then update accordingly
			if (comp.compare(best.score, pathMove.score) < 0) {
				best = new MoveEvaluation (move, pathMove.score); 
			}
		}
		
		// this move was selected for the gameState. record it. If it is null, then the game has
		// already been won or this is a draw; in either case, we will let recursive parent take
		// care of the scoring. Even add when no move, so we can keep track of the WON games.
		addToList(state, best);
		
		return best;
	}
	
	/**
	 * If this board has already been seen (ROTATED, REFLECTIONS) then return
	 * the calculated MoveEvaluation, otherwise return null.
	 * 
	 * @param state
	 */
	public MoveEvaluation alreadyDetermined(IGameState state) {
		Node<Pair> node = list.head();
		
		while (node != null) {
			Pair p = node.value();
			if (state.equivalent(p.state)) {
				return p.move;
			}
			
			node = node.next();
		}
		
		return null;
	}
	
	/** Expose the number of computational states seen so far. */
	public int getNumComputationalStates() {
		return numComputationalStates;
	}
	
	/** Expose list of the computed states seen so far. */
	public List<Pair> getStatesSeenSoFar() {
		return list;
	}
	
}
