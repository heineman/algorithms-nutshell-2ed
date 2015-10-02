package algs.model.network;

/**
 * This class is used to model the edges of the graph which contains a network
 * flow problem. 
 * <p>
 * Public data members are not a problem since this information is finalized once 
 * created, and the 'flow' value can only be read externally; it can be
 * written only by the algs.model.network package.
 * <p>
 * Note that the 'cost' attribute is needed in only a few of the network flow
 * algorithms but it has been provided here to simplify the modeling of the 
 * various problems being solved by Ford-Fulkerson as well as the Maximum Flow,
 * Minimum Cost problem.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class EdgeInfo {
	
	/** Start of edge. */
	public final int start;
	
	/** End of edge. */
	public final int end;
	
	/** Capacity over the edge. */
	public final int capacity;
	
	/** Shipping cost for this edge. */
	public final int cost;
	
	/** Computed Flow information. */
	int flow;
		
	/** 
	 * Construct EdgeInfo from (start,end) vertices with given capacity.
	 * 
	 * @param start    start vertex 
	 * @param end      end vertex
	 * @param cap      existing capacity
	 */
	public EdgeInfo (int start, int end, int cap) {
		this (start, end, cap, 0);
	}
	
	/** 
	 * Construct EdgeInfo from (start,end) vertices with given capacity.
	 * 
	 * @param start    start vertex 
	 * @param end      end vertex
	 * @param cap      existing capacity
	 * @param cost     shipping cost for this edge
	 */
	public EdgeInfo (int start, int end, int cap, int cost) {
		this.start = start;
		this.end = end;
		this.capacity = cap;
		this.cost = cost;
	}
	
	/** 
	 * Return the flow computed by the algorithm. 
	 * 
	 * @return  integer flow as computed by algorithm. 
	 */
	public int getFlow() {
		return flow;
	}
	
	/** 
	 * Reasonable toString. 
	 * 
	 * @return human readable string 
	 */
	public String toString () {
		return "[" + start + "] -> [" + end + "] " + flow + "/" + capacity + " @ " + cost;
	}
	
	/**
	 * Standard equals method implementation.
	 * 
	 * @param  o   object to compare against.
	 * @return true if object is equal to this.
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		if (o.getClass().equals(EdgeInfo.class)) {
			EdgeInfo ei = (EdgeInfo) o;
			return ei.start == start && ei.end == end && ei.capacity == capacity;
		}
	
		return false;
	}
	
	/**
	 * Support hashCode protocol. 
	 * @return hash code to use for hashtable.
	 */
	public int hashCode () {
		return start*end + capacity;  // something efficient, but not sure how effective.
	}
}
