package algs.model.problems.tictactoe.debug;

import java.util.Hashtable;

import algs.debug.DottyDebugger;
import algs.debug.IGraphEntity;
import algs.model.gametree.debug.AlphaBetaDebugNode;
import algs.model.problems.tictactoe.model.TicTacToeState;

/**
 * Extends GameTreeDebugger to work with TicTacToe nodes.
 * 
 * Since the state is mutated by the algorithm, we must maintain our local copies in 
 * a hash table.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TicTacToeDebugger extends DottyDebugger {
	
	// copies are stored here, for node purposes.
	Hashtable<String,TicTacToeState> copies = new Hashtable<String,TicTacToeState>(); 
	
	/** 
	 * Mark node as being visited. 
	 * 
	 * Special case for our purposes is to use generated state number from 
	 * search process. While this is a bit of a "hack", it makes it easy to
	 * generate effective visualizations of the search.
	 */
	public void visitNode(IGraphEntity n) {
		if (n instanceof TicTacToeState) {
			TicTacToeState state = (TicTacToeState) n;
			String id = "s" + state.counter();
			nodes.put(id, state.copy());
			return;
		}
		
		if (n instanceof AlphaBetaDebugNode) {
			AlphaBetaDebugNode node = (AlphaBetaDebugNode) n;
			String id = "a" + node.counter();
			nodes.put(id, node.copy());
			return;
		}
		
		super.visitNode(n);
	}
	
	/** Helper function to reverse locate the key. */
	protected String getKey (IGraphEntity value) {
		if (value instanceof TicTacToeState) {
			TicTacToeState state = (TicTacToeState) value;
			String id = "s" + state.counter();
			return id;
		}
		
		if (value instanceof AlphaBetaDebugNode) {
			AlphaBetaDebugNode state = (AlphaBetaDebugNode) value;
			String id = "a" + state.counter();
			return id;
		}
		return super.getKey(value);
	}

}
