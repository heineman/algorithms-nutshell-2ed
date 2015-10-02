package algs.model.searchtree;

/**
 * Records the depth of the transition between board states.
 * <p>
 * Used by DepthFirstSearch when deciding whether to advance a board state.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DepthTransition extends Transition {

	/** Depth away from the initial board state. */
	public final int depth;
	
	/**
	 * Record the move and previous state of this transition.
	 * <p>
	 * Since this is a depth transition, also record the depth.
	 * 
	 * @param move   Move which caused the transition
	 * @param prev   The previous board state
	 * @param depth  The distance from the initial board state
	 */
	public DepthTransition(IMove move, INode prev, int depth) {
		super(move, prev);

		this.depth = depth;
	}

}