package algs.model.gametree.debug;

import algs.debug.Formatter;
import algs.debug.IGraphEntity;
import algs.debug.ISelectFont;

/**
 * This node is used when depicting debugging information about an Alpha/Beta
 * node in the game tree path finding search.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AlphaBetaDebugNode implements IGraphEntity, ISelectFont {
	/** Counter to ensure uniqueness. */ 
	private static int _ctrMaster;

	/** Unique id (an incrementing integer) for this node. */
	private int _ctr;
	
	/** Alpha (lower bound) value. */
	final int alpha;
	
	/** Beta (upper bound) value. */
	final int beta;
	
	/** Computed score value. */
	int value;
	
	/** Until value is set, it isn't shown. */ 
	boolean set = false;
	
	/** 
	 * Represent a node in the search for a solution in alpha beta.
	
	 * @param alpha    known lower bound for game tree node 
	 * @param beta     known upper bound for game tree node
	 */
	public AlphaBetaDebugNode (int alpha, int beta) {
		this.alpha = alpha;
		this.beta = beta;
		_ctr = _ctrMaster++;
	}
	
	/**
	 * Retrieve value for node computed so far.
	 * <p>
	 * Primarily here for testing
	 * @return value associated with AlphaBetaNode
	 */
	public int value() {
		return this.value;
	}
	
	/** 
	 * Set the value for this node based upon computation.
	 * <p>
	 * Once invoked, the 'set' field changes and the value becomes part of 
	 * the visualization.
	 * 
	 * @param v   computed score value for node.
	 */
	public void value(int v) {
		set = true;
		this.value = v;
	}

	/** 
	 * Retrieve the unique identifier for this node.
	 * @return unique identifier associated with this debug node.
	 */
	public int counter() { return _ctr; } 
	
	/** 
	 * Generate copy of this node.
	 * 
	 * Useful when visiting all nodes in the game tree and needing to record progress.
	 * @return copy of {@link AlphaBetaDebugNode}
	 */
	public AlphaBetaDebugNode copy() {
		AlphaBetaDebugNode n = new AlphaBetaDebugNode (alpha, beta);
		n.value = value;
		n._ctr = _ctr;
		return n;
	}

	/** Reasonable toString() method for debugging. */
	public String toString () { return "[" + alpha + "," + beta + "]"; }
	
	/** 
	 * Compute label for Dotty output.
	 * 
	 * To try to maximize utility, we want to show Alpha Beta in symbol font.
	 * However, once score has been set, we can't since then "score 
	 */
	public String nodeLabel() {
		StringBuilder sb = new StringBuilder();
		sb.append("{a: ");   // in symbol font, this becomes "Greek alpha"
		sb.append(Formatter.convert(alpha));
		sb.append("|b: ");   // in symbol font, this becomes "Greek beta"
		sb.append(Formatter.convert(beta));
		
		// no longer show score, since we are in symbol font.
		if (set) {
			sb.append("| ");
			sb.append(Formatter.convert(value));
		}
		sb.append("}");
		return sb.toString(); 
	}

	/** To properly draw Alpha/Beta in symbol font. */
	public String fontName() {
// When formatting becomes available again
//		
//		if (Formatter.isSymbol(alpha) || Formatter.isSymbol(beta)) {
//			return "Symbol";
//		}
		
		return null;  // nothing special.
	}

	/** Default font size to use is ok. */
	public int fontSize() {
		return 0;
	}	
}
