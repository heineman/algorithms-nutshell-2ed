package algs.model.gametree;

/**
 * Used to represent the Comparable score for a given Move.
 * <p>
 * The score for a move is not stored with the move since we believe that 
 * responsibility is not its responsibility. 
 * <p>
 * Note that the minimum() and maximum() values are true negations of each 
 * other, which is essential for {@link NegMaxEvaluation} and {@link AlphaBetaEvaluation}.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MoveEvaluation {

	/** The score. */
	public final int score;
	
	/** The Move. */
	public final IGameMove move;
	
	/** Constructs empty Move Evaluation with null move and -infinity score. */
	public MoveEvaluation () {
		this.move = null;
		this.score = minimum();
	}
	
	/**
	 * Constructs the evaluation object representing straight board evaluation.
	 * <p>
	 * Used within AlphaBeta when no more moves are available.
	 * @param score    score to associate with this move
	 */
	public MoveEvaluation (int score) {
		this.move = null;
		this.score = score;
	}
	
	/** 
	 * Constructs the evaluation object.
	 *  
	 * @param m        The move to make
	 * @param score    The associated score of the resulting game state.
	 */
	public MoveEvaluation (IGameMove m, int score) {
		this.move = m;
		this.score = score;
	}
	
	/**
	 * Return the minimum score, so a move's score can be compared. Ensure it is negation of maximum()
	 * @return minimum possible score 
	 */
	public static final int minimum() { return Integer.MIN_VALUE+1; }
	
	/** 
	 * Return the maximum score, so a move's score can be compared. Ensure it is the negation of minimum()
	 * @return maximum possible score 
	 */
	public static final int maximum() { return Integer.MAX_VALUE; }
	
	/** 
	 * Reasonable toString method.
	 * @return human readable string. 
	 */
	public String toString () {
		return move + " for " + score;
	}	
}
