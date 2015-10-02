package algs.model.problems.tictactoe.model;

import algs.model.gametree.*;

/**
 * Represents an Intelligent Tic Tac Toe playing agent that relies on the
 * provided algorithm to select a move.
 * <p>
 * Since it relies on using a search algorithm to locate best move given what
 * opponent is capable of doing, then it must know who the opponent is. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class IntelligentAgent extends Player {
	/** Algorithm to use. */
	IEvaluation algorithm;
	
	/** Opponent. */
	IPlayer opponent;
	
	/** 
	 * Construct an agent that selects the next move using the provided 
	 * logic in the algorithm.
	 * <p>
	 * Note that whoever constructs this player must later set the opponent.
	 * 
	 * @param mark       mark to be used by player.
	 * @param algorithm  Algorithm to use when selecting the move.
	 */
	public IntelligentAgent (char mark, IEvaluation algorithm) {
		super (mark);
		
		this.algorithm = algorithm;
	}
	
	/** 
	 * Set the opponent for this agent, which is needed because the search for
	 * the best move is going to need the opposing player.
	 * 
	 * @param opponent    record opponent.
	 */
	public void opponent (IPlayer opponent) {
		this.opponent = opponent;
	}
	
	/**
	 * Make an intelligent move given the board state, game logic,
	 * and player making the move.
	 * 
	 * If no move can be made (because the game is already DRAWN or
	 * the game has been won) then null is returned. We can only
	 * look ahead 'ply' number of moves.
	 * 
	 * @return    the selected best move to make, or null if game is already won or drawn.
	 */
	public IGameMove decideMove (IGameState gameState) {
		
		// nothing to do in these circumstances.
		if (gameState.isWin()) return null;
		if (gameState.isDraw()) return null;
		
		return algorithm.bestMove(gameState, this, opponent);
	}

}
