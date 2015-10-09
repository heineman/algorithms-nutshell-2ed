package algs.blog.graph.search;

import algs.model.searchtree.IMove;
import java.util.Stack;

/**
 * Structure to trace solution of moves from board back to initial state.
 * 
 * @author George Heineman
 */
public class Chain {
	/** What are our sequence of moves? */
	public final Stack<IMove> moves;
	
	/** When our moves above runs out, which is the next chain sequence? */
	public final Chain previous;
	
	/** And what is the boardID at the end of these moves? */
	public final int lastID;
	
	public Chain (Stack<IMove> moves, Chain previous, int lastID) {
		this.moves = moves;
		this.previous = previous;
		this.lastID = lastID;
	}
	
	public Chain (Stack<IMove> moves, Chain previous) {
		this.moves = moves;
		this.previous = previous;
		this.lastID = 0;
	}
	
	public Chain (Stack<IMove> moves) {
		this.previous = null;
		this.moves = moves;
		this.lastID = 0;
	}
	
	public Chain (Stack<IMove> moves, int lastID) {
		this.previous = null;
		this.moves = moves;
		this.lastID = lastID;
	}
	
}
