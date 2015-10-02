package algs.model.searchtree;

/**
 * A valid move in the Search Tree.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IMove {

	/** 
	 * Execute the move on the board state.
	 * 
	 * @param state    the board state to manipulate.
	 * @return         true if move succeeded.
	 */
	boolean execute (INode state);
	
	/** 
	 * Undo the move on the board state.
	 * 
	 * @param state    the board state in which a move is to be undone.
	 * @return         true if undo succeeded.
	 */
	boolean undo (INode state);
	
	/** 
	 * Determine if move is valid in the board state. 
	 * 
	 * @param state    the board state to determine whether move is even valid.
	 * @return         true if move is valid.
	 */
	boolean isValid (INode state);
	
}
