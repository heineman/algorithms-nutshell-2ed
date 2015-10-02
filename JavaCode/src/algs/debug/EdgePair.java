package algs.debug;

/**
 * Represents an edge in the resulting visualization.
 * <p>
 * Not needed outside of this package.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
class EdgePair {
	/** Starting node. */
	final String startKey;
	
	/** Ending node. */
	final String endKey;
	
	/** Is edge marked. Note that field is only accessible within package. */
	boolean marked;
	
	/** Edge's label. Note that field is only accessible within package. */
	String label = null;
	
	/**
	 * Construct a (start, end) edge to appear in a visualization.
	 * 
	 * @param st     key of starting node
	 * @param end    key of ending node
	 */
	EdgePair (String st, String end) {
		this.startKey = st;
		this.endKey = end;
		this.marked = false;
	}
	
	/** 
	 * Mark this edge. 
	 *
	 * A marked edge is drawn specially.
	 */
	void mark() {
		marked = true;
	}
	
	/**
	 * Assign a label to this edge.
	 *  
	 * @param s   desired label.
	 */
	void label(String s) {
		label = s;
	}
	
	/** Determine if this a labeled edge. */
	boolean isLabeled() { return label != null; }
}