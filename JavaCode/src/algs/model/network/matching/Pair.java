package algs.model.network.matching;

/**
 * Represents a matching from the potential set of vertices in S and T.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Pair {

	/** Element from set S. */
	public final Object element;
	
	/** Matching element from set T. */
	public final Object match;
	
	/** 
	 * Construct the (element, match) relationship.
	 * 
	 * @param element    element from set S
	 * @param match      matched element from set T
	 */
	public Pair (Object element, Object match) {
		this.element = element;
		this.match = match;
	}
	
	/**
	 * Standard equals method.
	 * 
	 * @param o    the Pair object being compared against.
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		if (o instanceof Pair) {
			Pair p = (Pair) o;
			return p.element.equals (element) && p.match.equals (match);
		}
		
		return false;
	}
	
	/** Standard toString method. */
	public String toString () {
		return "(" + element + "," + match + ")";
	}
}
