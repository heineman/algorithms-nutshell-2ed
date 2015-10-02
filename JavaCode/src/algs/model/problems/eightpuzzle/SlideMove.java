package algs.model.problems.eightpuzzle;

import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Slide a numbered tile from (r,c) to (r', c').
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SlideMove implements IMove {

	/** tile being moved. */
	public final int tile;
	
	/** row coordinate of the move's source. */
	public final int fromR;
	
	/** column coordinate of the move's source. */
	public final int fromC;
	
	/** row coordinate of the move's destination. */
	public final int toR;
	
	/** column coordinate of the move's destination. */
	public final int toC;
	
	/**
	 * Move from (fromC, fromR) to (toC, toR)
	 * 
	 * @param tile   tile to move.
	 * @param fromR  row coordinate of the move's source.
	 * @param fromC  column coordinate of the move's source.
	 * @param toR    row coordinate of the move's destination
	 * @param toC    column coordinate of the move's destination
	 */
	public SlideMove (int tile, int fromR, int fromC, int toR, int toC) {
		this.tile = tile;
		this.fromR = fromR;
		this.fromC = fromC;
		this.toR = toR;
		this.toC = toC;
	}
	
	/**
	 * Execute the move on the given board state.
	 * 
	 * @param n   state on which to execute the move.
	 */
	public boolean execute(INode n) {
		if (!(n instanceof EightPuzzleNode)) {
			throw new IllegalArgumentException("SlideMove expects state objects of class EightPuzzleNode");
		}
		
		EightPuzzleNode state = (EightPuzzleNode) n;
		if (state.isAdjacentAndEmpty(fromR, fromC, toR, toC)) {
			return state.swap(fromR, fromC, toR, toC);
		}
		
		// no move possible
		return false;
	}

	/**
	 * Determine if move is valid for the given state.
	 * 
	 * @param n     game state in which move is evaluated.
	 * @exception   IllegalArgumentException if n is not an EightPuzzleNode
	 */
	public boolean isValid(INode n) {
		if (!(n instanceof EightPuzzleNode)) {
			throw new IllegalArgumentException("SlideMove expects state objects of class EightPuzzleNode");
		}
		
		EightPuzzleNode state = (EightPuzzleNode) n;
		if (fromR < 0 || fromR > EightPuzzleNode.MaxC) { return false; }
		if (fromC < 0 || fromC > EightPuzzleNode.MaxR) { return false; }
		if (toR < 0 || toR > EightPuzzleNode.MaxC) { return false; }
		if (toC < 0 || toC > EightPuzzleNode.MaxR) { return false; }
		
		return state.isAdjacentAndEmpty(fromR, fromC, toR, toC);
	}

	/** 
	 * Assume move had been valid, so the undo is a straightforward swap.
	 *  
	 * Note that we simply swap the fromC/fromR and toC/toR in the invocation.
	 * @param n    game state whose move is to be undone.  
	 */
	public boolean undo(INode n) {
		if (!(n instanceof EightPuzzleNode)) {
			throw new IllegalArgumentException("SlideMove expects state objects of class EightPuzzleNode");
		}
		
		EightPuzzleNode state = (EightPuzzleNode) n;
		return state.swap(toR, toC, fromR, fromC);
	}
	
	/** Reasonable implementation. */
	public String toString () {
		return "move " + tile;
	}
}
