package algs.model.searchtree;

/**
 * Stores the move and the previous state that was present when the move was made. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Transition {
	/** The move which caused the board state transition. */
	public final IMove move;
	
	/** The previous board state. */
	public final INode prev;
	
	/**
	 * Record the move and previous state of this transition.
	 * 
	 * @param move   Move which caused the transition
	 * @param prev   The previous board state
	 */
	public Transition (IMove move, INode prev) {
		this.move = move;
		this.prev = prev;
	}

}
