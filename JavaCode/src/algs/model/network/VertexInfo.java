package algs.model.network;

/**
 * Stored information by augmenting flow algorithms as it progresses.
 * <p>
 * Previous records the backreference to mark the unique path to the source.
 * 
 * @author George Heineman
 */
public class VertexInfo {
	/** Store vertex index from which path came. */
	final int previous;
	
	/** flow type. */
	final boolean forward;
	
	/**
	 * Constructs a vertex in the augmenting path, where previous records the prior
	 * vertex in the augmenting path while forward stores its orientation.
	 * <p>
	 * If forward is false, then the algorithm has located a backward edge whose
	 * flow is to be reduced. If forward is true, then the algorithm has located a 
	 * forward edge whose flow is to be increased. 
	 * 
	 * @param previous    prior vertex in augmenting path
	 * @param forward     determines whether this is a forward path (true) or backward (false)
	 */
	public VertexInfo (int previous, boolean forward) {
		this.previous = previous;
		this.forward = forward;
	}
	
	/**
	 * By default the vertex info in the path is forward-looking.
	 *  
	 * @see VertexInfo#VertexInfo(int, boolean)
	 * @param previous   prior vertex in augmenting path, assumed to be forward
	 */
	public VertexInfo (int previous) {
		this (previous, true);
	}
	
	/**
	 * Returns meaningful string representation.
	 * @return human readable string.
	 */
	public String toString() {
		return "[previous: " + previous+ ", forward: " + forward + "]";
	}
}