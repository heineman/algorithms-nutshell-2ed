package algs.model.network;

import algs.model.list.DoubleLinkedList;

/**
 * Parent abstract class for representing a flow network problem to be solved.
 * <p>
 * Subclasses included an array-based implementation and one using adjacency lists.
 * 
 * @param <E>    type parameter to describe Edge Structure used by each algorithm
 *               variation.
 *                
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class FlowNetwork<E> {
	
	/** Index of the source vertex. */
	final public int sourceIndex;
	
	/** Index of the sink vertex. */
	final public int sinkIndex;
	
	/** Number of vertices in the graph. */
	final public int numVertices;
	
	/**
	 * When algorithm completes, the final (failed) pass properly labels those 
	 * vertices which, together with the source s, form a subset of V called S from 
	 * which the MinCut can be computed.
	 * <p>
	 * Specifically, the vertex set V is divided into two disjoint sets S and T, where
	 * T = V - S. The MinCut theorem states that the maximum flow in the network is
	 * exactly equal to the minimum flow cut, where all the edges from S to T are
	 * forward edges and the flow on those edges exactly matches their capacities.
	 */
	final VertexInfo vertices[];
	
	/**
	 * Store relevant information about the FlowNetwork graph.
	 * <p>
	 * 
	 * @param numVertices    the number of vertices in the Flow Network
	 * @param srcIndex       the index of the vertex designated to be the source
	 * @param sinkindex       the index of the vertex designated to be the sink
	 */
	public FlowNetwork (int numVertices, int srcIndex, int sinkindex) {
		this.sourceIndex = srcIndex;
		this.sinkIndex = sinkindex;
		this.numVertices = numVertices;
		this.vertices = new VertexInfo [numVertices];
	}
	
	/**
	 * Subclasses are responsible for interpreting the info in the appropriate 
	 * data structure.
	 * 
	 * @return structure used in the flow network.
	 */
	public abstract E getEdgeStructure();

	/**
	 * Subclasses provide this implementation, based upon their data structures.
	 * <p>
	 * 
	 * @param start    the start index of desired edge.
	 * @param end      the end index of desired edge.
	 * @return         information about the designated forward edge, or null if it doesn't exist
	 */
	public abstract EdgeInfo edge (int start, int end);

	/**
	 * Validate the FlowNetwork.
	 * <p>
	 * A valid flow network satisfies three criteria:
	 * <ol><li>Capacity Constraint -- for all edges e=(u,v) it must be that f(u,v) &le; c(u,v)
	 *     <li>Skew Symmetry -- for all (u,v) it must be that f(u,v) = -f(v,u)
	 *     <li>Flow Conservation -- for all nodes in the Flow Network (other than source
	 *         and sink) it must be that the sum of f(u,v) to all other vertices v
	 *         must be zero.
	 * </ol>
	 * @exception IllegalStateException if any of these criteria is violated.
	 */
	public abstract void validate() throws IllegalStateException;

	/**
	 * After the termination of the network flow algorithms, the last round of labeled vertices (including
	 * the source) can be grouped into a set X while the unlabeled vertices are grouped into X'. The division
	 * between these two sets is known as the min-cut. Given sets X and X', group the edges between them
	 * as follows:
	 * <ol><li>Forward Edges: These are edges from X to X' whose flow is equal to the edges' capacity.
	 *     <li>Backward Edges: These are edges from X' to X whose flow is zero.
	 * </ol>
	 * As a corollary, the sum total of the flow along the forward edges is, in fact, equal to the 
	 * maximum flow through the network. This is no coincidence, and is known as the Max-flow, Min-cut
	 * theorem.
	 * @return    linked list of EdgeInfo representing the minimum cut of the FlowNetwork.
	 */
	public abstract DoubleLinkedList<EdgeInfo> getMinCut();
	
	/**
	 * After the termination of the network flow algorithm, this method returns the final flow 
	 * pushed out of the source.
	 * 
	 * @return   computed integer representing max flow. 
	 */
	public abstract int getFlow();

	/**
	 * Return the cost of the network solution.
	 * <p>
	 * Subclasses are free to override with more efficient implementations, 
	 * so long as they are correct :)
	 * 
	 * @return total cost for network solution. 
	 */
	public abstract int getCost();
}
