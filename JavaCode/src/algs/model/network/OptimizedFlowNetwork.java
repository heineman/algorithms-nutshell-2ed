package algs.model.network;

import java.util.Iterator;

import algs.model.list.DoubleLinkedList;

/**
 * Store information regarding the graph in a two-dimensional matrix.
 * <p>
 * This optimized implementation computes the Ford-Fulkerson algorithm
 * entirely using arrays. The improved efficiency can be benchmarked by
 * comparing its performance with the {@link FordFulkerson} implementation.
 * @author George Heineman
 */
public class OptimizedFlowNetwork {
	
	/** Number of vertices. */
	int n;
	
	/** source & target. */
	int source;
	int sink;
	
	/** Contains all capacities. */
	int[][] capacity;
	
	/** Contain all flows. */
	int[][] flow;
	
	/** Upon completion of findAugmentingPath, contains the predecessor information. */
	int[] previous;
	
	/** Visited during augmenting path search. */
	int[] visited;
	
	final int QUEUE_SIZE = 1000;
	
	int queue[] = new int[QUEUE_SIZE];
   
	/** Declares a path in the augmenting path follows a forward edge. */
	public static final boolean FORWARD = true;

	/** Declares a path in the augmenting path follows a backward edge. */
	public static final boolean BACKWARD = false;
	
	/**
	 * Construct an instance of the FlowNetwork problem using optimized array
	 * structure to solve problem.
	 * <p>
	 * We use the same input representation so we can properly compare the performance
	 * of this implementation.
	 * 
	 * @param numVertices    the number of vertices in the Flow Network
	 * @param srcIndex       the index of the source vertex
	 * @param sinkIndex      the index of the sink vertex
	 * @param edges          an iterator of EdgeInfo objects representing edge capacities
	 *
	 * @see FlowNetwork#FlowNetwork(int, int, int)
	 */
	public OptimizedFlowNetwork (int numVertices, int srcIndex, int sinkIndex, Iterator<EdgeInfo> edges) {
		n = numVertices;
		capacity = new int[n][n];
		flow = new int[n][n];
		previous = new int[n];
		visited = new int [n];
		source = srcIndex;
		sink = sinkIndex;
		
		// Note that initially, the flow is set to zero. Pull info from input.
		while (edges.hasNext()) {
			EdgeInfo ei = edges.next();
			capacity[ei.start][ei.end] = ei.capacity;
		}
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
	 * @param source   the index of the source vertex
	 * @param sink     the index of the sink vertex
	 * @return         integer representing the maximal flow
	 */
	public int compute (int source, int sink) {
		int maxFlow = 0;
		while (findAugmentingPath(source, sink)) {
			maxFlow += processPath(source, sink);
		}
		
		return maxFlow;
	}

	/**
	 * Augments the flows within the network along the path found from source to sink.
	 * <p>
	 * Walking backwards from the sink vertex to the source vertex, this updates the flow
	 * values for the edges in the augmenting path.
	 * <p>
	 * Note that the available units in the sinkIndex are used as the additive value (for
	 * forward edges) and the subtractive value (for backward edges).
	 * 
	 * @param source    source index of the augmenting path
	 * @param sink      sink index of the augmenting path
	 */
	protected int processPath(int source, int sink) {
		int v = sink;
		// Determine the amount by which we can increment the flow. Equal to minimum
		// over the computed path from sink to source.
		int increment = Integer.MAX_VALUE;
		while (previous[v] != -1) {
			int unit = capacity[previous[v]][v] - flow[previous[v]][v];
			if (unit < increment) {
				increment = unit; 
			}
			v = previous[v];
		}
		
		// push minimal increment over the path
		v = sink;
		while (previous[v] != -1) {
			flow[previous[v]][v] += increment;  // forward edges.
			flow[v][previous[v]] -= increment;  // don't forget back edges

			v = previous[v];
		}
		
		return increment;
	}
	
	public DoubleLinkedList<EdgeInfo> getMinCut() {
		DoubleLinkedList<EdgeInfo> dl = new DoubleLinkedList<EdgeInfo>();
		
		// All edges from a vertex that is labeled to one that is not labeled belong in the min cut.
		for (int u = 0; u < n; u++) {
			if (visited[u] != 0) {
				// labeled u. Find unlabeled 'v'. (FORWARD EDGE)
				for (int v = 0; v < n; v++) {
					if (u == v) { continue; }
					if (visited[v] != 0) { continue; }
					
					// v not visited. See if edge from u to v
					if (flow[u][v] > 0) {
						dl.insert(new EdgeInfo (u, v, capacity[u][v]));
					}
				}
			}
		}
		return dl;
	}
	
	/**
	 * Locate an augmenting path in the Flow Network from the source vertex 
	 * to the sink.
	 * <p>
	 * Path is stored within the vertices[] array by traversing in reverse order
	 * from the sinkIndex.
	 * <p>
	 * Assume path always fits within queue size of length QUEUE_SIZE
	 * 
	 * @param source    index of source vertex
	 * @param sink      index of sink vertex
	 * @return true if an augmented path was located; false otherwise.
	 */
	public boolean findAugmentingPath (int source, int sink) {
		// clear visiting status. 0=clear, 1=actively in queue, 2=visited
		for (int i = 0 ; i < n; i++) { visited[i] = 0; }
		
		// create circular queue to process search elements
		queue[0] = source;
		int head = 0, tail = 1;
		previous[source] = -1;    // make sure we terminate here.
		visited[source] = 1;   // visited.      
		while (head != tail) {
			int u = queue[head]; head = (head + 1) % QUEUE_SIZE;
			visited[u] = 2;
			
			// find all unvisited vertices connected to u by edges. 
			for (int v = 0; v < n; v++) {
				if (v == u) continue;       // can't visit self.
				if (visited[v] != 0) continue;   // has already been visited.
				
				if (capacity[u][v] > flow[u][v]) {
					queue[tail] = v;  tail = (tail + 1) % QUEUE_SIZE;
					visited[v] = 1;
					previous[v] = u;
				}
			}
		}
		
		return visited[sink] != 0;  // did we make it to the sink?
	}

	public int getSink() {
		return sink;
	}

	public int getSource() {
		return source;
	}
	
	/** Useful for debugging. */
	public String toString () {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j == i) continue;

				if (capacity[i][j] > 0) {
					sb.append (i + " -> " + j + "[" + capacity[i][j] + "]").append('\n');
				}
			}
		}
		
		return sb.toString();
	}
}
