package algs.model.network;

/**
 * Flow Network algorithms depend on locating an augmenting path through 
 * the network. This parent class simplifies the implementation of 
 * {@link FordFulkerson} by providing an abstract parent class that
 * defines the API for identifying augmenting paths.  
 * <p>
 * Appropriate subclasses use either BreadthFirstSearch or DepthFirstSearch 
 * to locate the path. In addition, the subclasses are implemented using 
 * two-dimensional arrays or adjacency lists.
 * 
 * @author George Heineman
 */
public abstract class Search {
	
	/** Index of the source vertex. */
	protected int sourceIndex;
	
	/** Index of the sink vertex. */
	protected int sinkIndex;
	
	/** Number of vertices in the graph. */
	protected int numVertices;

	/** Declares a path in the augmenting path follows a forward edge. */
	public static final boolean FORWARD = true;

	/** Declares a path in the augmenting path follows a backward edge. */
	public static final boolean BACKWARD = false;
	
	/**
	 * Configure this augmenting path search with the information it needs to process.
	 * <p>
	 * Note that information about the edges are omitted since that knowledge is
	 * deferred to the relevant subclasses. We don't care about the parameterization
	 * of FlowNetwork either, and that is deferred to the relevant subclasses.
	 * 
	 * @param network   Contains information about the FlowNetwork
	 */
	public Search (FlowNetwork<?> network) {
		this.sourceIndex = network.sourceIndex;
		this.sinkIndex = network.sinkIndex;
		this.numVertices = network.numVertices; 
	}
	
	/**
	 * Locate an augmenting path in the Flow Network determined by the edge
	 * structure information and knowledge of the vertices (such as the number, which
	 * one is source and which one is the sink).
	 * <p>
	 * Returns true if augmenting path was found; false otherwise. The resulting path
	 * can be computed from the vertices array (which is altered to contain this 
	 * information) by traversing in reverse order from the sinkIndex.
	 * 
	 * @param vertices    initially an empty array of Vertex Info; afterwards, contains the 
	 *                    path which can be determined by traversing backwards from the sink index.
	 * @return            true if an augmented path was located; false otherwise.
	 */
	public abstract boolean findAugmentingPath (VertexInfo[] vertices);
}
