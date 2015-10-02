package algs.model.problems.fifteenpuzzle;

import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Slide a numbered tile from (c,r) to (c', r').
 * 
 * @author George Heineman
 */
public class SlideMove implements IMove {

	/** tile being moved. */
	public final int tile;
	
	/** column coordinate of the move's source. */
	public final int fromC;
	
	/** row coordinate of the move's source. */
	public final int fromR;
	
	/** column coordinate of the move's destination. */
	public final int toC;
	
	/** row coordinate of the move's destination. */
	public final int toR;
	
	/**
	 * Move from (fromC, fromR) to (toC, toR)
	 * 
	 * @param tile   tile being moved
	 * @param fromC  column coordinate of the move's source.
	 * @param fromR  row coordinate of the move's source.
	 * @param toC    column coordinate of the move's destination
	 * @param toR    row coordinate of the move's destination
	 */
	public SlideMove (int tile, int fromC, int fromR, int toC, int toR) {
		this.tile = tile;
		this.fromC = fromC;
		this.fromR = fromR;
		this.toC = toC;
		this.toR = toR;
	}
	

	/**
	 * Execute the move on the given board state.
	 * 
	 * @param staten   state on which to execute the move.
	 * @exception   IllegalArgumentException if staten is not a FifteenPuzzleNode
	 */
	public boolean execute(INode staten) {
		if (!(staten instanceof FifteenPuzzleNode)) {
			throw new IllegalArgumentException("SlideMove expects state objects of class EightPuzzleNode");
		}
		
		FifteenPuzzleNode state = (FifteenPuzzleNode) staten;
		if (state.isAdjacentAndEmpty(fromC, fromR, toC, toR)) {
			return state.swap(fromC, fromR, toC, toR);
		}
		
		return false;
	}

	/**
	 * Determine if move is valid for the given state.
	 * 
	 * @param staten      state on which to execute the move.
	 * @exception   IllegalArgumentException if staten is not a FifteenPuzzleNode
	 */
	public boolean isValid(INode staten) {
		if (!(staten instanceof FifteenPuzzleNode)) {
			throw new IllegalArgumentException("SlideMove expects state objects of class EightPuzzleNode");
		}
		
		FifteenPuzzleNode state = (FifteenPuzzleNode) staten;
		if (fromC < 0 || fromC > FifteenPuzzleNode.MaxR) { return false; }
		if (fromR < 0 || fromR > FifteenPuzzleNode.MaxC) { return false; }
		if (toC < 0 || toC > FifteenPuzzleNode.MaxR) { return false; }
		if (toR < 0 || toR > FifteenPuzzleNode.MaxC) { return false; }
		
		return state.isAdjacentAndEmpty(fromC, fromR, toC, toR);
	}

	/** 
	 * Assume move had been valid, so the undo is a straightforward swap.
	 *  
	 * Note that we simply swap the fromC/fromR and toC/toR in the invocation.
	 * 
	 * @param staten   state on which to execute the move.
	 * @exception   IllegalArgumentException if staten is not a FifteenPuzzleNode
	 */
	public boolean undo(INode staten) {
		if (!(staten instanceof FifteenPuzzleNode)) {
			throw new IllegalArgumentException("SlideMove expects state objects of class EightPuzzleNode");
		}
		
		FifteenPuzzleNode state = (FifteenPuzzleNode) staten;
		return state.swap(toC, toR, fromC, fromR);
	}
	
	/** Reasonable implementation. */
	public String toString () {
		return "move " + tile;
	}

}
