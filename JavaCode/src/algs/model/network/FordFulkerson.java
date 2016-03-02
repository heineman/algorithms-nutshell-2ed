package algs.model.network;

import java.util.Arrays;

/**
 * Given a flow network (see definition below) with known capacities on 
 * each directed edge, compute the maximal flow between the source vertex
 * and the sink vertex.
 * <p>
 * A Flow Network is a directed graph G=(V,E) with two special vertices,
 * source and sink. Each edge (u, v) has an associated capacity c(u,v) &ge; 0,
 * and the resulting computation leaves f(u,v) flow values with each edge.
 * Since it is likely that the problem data is stored in a rich set of classes,
 * this algorithm assumes that the multi-dimensional data needed by the algorithm
 * is provided up-front.
 * <p>
 * The structure of the data is as follows:
 * Each edge (i,j) is represented by an EdgeInfo object containing:
 *    <ul><li>capacity: The capacity of the edge (or zero if that edge doesn't exist)
 *        <li>flow    : The current flow over the edge (or zero if no flow)
 *        <li>cost    : The cost per-unit for the commodity flowing over the edge (or zero if no cost)
 *        <li>start   : The start index i of the edge
 *        <li>end     : The end index j of the edge
 *    </ul>
 * Each vertex is represented by a VertexInfo object containing:
 *    <ul><li>previous:  The index of the previous vertex in the path
 *        <li>available: The amount of flow which can be transported from source
 *        <li>forward:   Is this vertex part of a forward edge or a backward edge
 *    </ul>
 * 
 * Note that available is the running total (regardless of path) as we 
 * compute along.
 * 
 * While the FlowNetwork and Search classes are parameterized, at this 
 * level of detail there is no need to know what its generic parameter is,
 * which is why we suppress warnings about generics.
 * <p>
 * @author George Heineman
 */
public class FordFulkerson {
	/** Represents the FlowNetwork problem. */
	FlowNetwork<?> network;
	
	/** Search method to use. */
	Search searchMethod;	
	
	/** 
	 * Construct instance to compute maximum flow across the given network,
	 * using the given search method to find an augmenting path.
	 * 
	 * @param network   The FlowNetwork
	 * @param method    The method for locating augmenting paths within the network. 
	 */
	public FordFulkerson (FlowNetwork<?> network, Search method) {
		this.network = network;
		this.searchMethod = method;
	}

	/**
	 * Compute the Maximal flow for the given flow network.
	 * <p>
	 * The results of the computation are stored within the network object. Specifically, 
	 * the final labeling of vertices computes the set S of nodes which is disjoint from 
	 * the unlabeled vertices which form the set T. Together, S and T represent the disjoint
	 * split of the graph into two sets whose connecting edges for the min-cut, in other words,
	 * the forward edges from S to T all have flow values which equal their capacity. Thus no
	 * additional augmentations are possible.
	 * 
	 * @return true if an augmenting path was found; false otherwise.
	 */
	public boolean compute () {
		boolean augmented = false;
		while (searchMethod.findAugmentingPath (network.vertices)) {
			processPath (network.vertices);
			augmented = true;
		}
		return augmented;
	}
	
	/**
	 * Augments the flows within the network along the path found from source to sink.
	 * <p>
	 * Walking backwards from the sink vertex to the source vertex, this updates the flow
	 * values for the edges in the augmenting path by finding the most constraining edge
	 * in the augmenting path. This minimum amount is the maximum amount that can be 
	 * augmented over the augmenting path. Special care is required for backward 
	 * edges which can "give up" only as much flow is going over the forward direction
	 * of the edge.
	 * <p>
	 * Note that the available units in the sinkIndex are used as the additive value (for
	 * forward edges) and the subtractive value (for backward edges).
	 * 
	 * @param vertices   Stores information about the computed augmented path 
	 */
	protected void processPath (VertexInfo []vertices) {
		int v = network.sinkIndex;
		
		// Determine the amount. Goal is to find the smallest 
		int delta = Integer.MAX_VALUE;
		while (v != network.sourceIndex) {
			int u = vertices[v].previous;

			// Over a forward edge, 
			int flow;
			if (vertices[v].forward) {
				// Forward edges can be adjusted by remaining capacity on edge
				flow = network.edge(u, v).capacity - network.edge(u, v).flow;
			} else {
				// Backward edges can be reduced only by their existing flow
				flow = network.edge(v, u).flow;
			}
			
			// have found a smaller candidate flow.
			if (flow < delta) { delta = flow; }
			
			v = u;  // follow reverse path to source
		}
		
		// Adjust path (forward is added, backward is reduced) with delta
		v = network.sinkIndex;
		while (v != network.sourceIndex) {
			int u = vertices[v].previous;

			if (vertices[v].forward) {
				network.edge(u, v).flow += delta;
			} else {
				network.edge(v, u).flow -= delta;
			}
			
			v = u;  // follow reverse path to source
		}

		Arrays.fill(network.vertices, null);   // reset for next iteration.
	}
}
