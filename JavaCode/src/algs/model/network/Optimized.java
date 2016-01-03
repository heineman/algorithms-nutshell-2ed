package algs.model.network;

import java.util.Iterator;

import algs.model.list.DoubleLinkedList;

/**
 * Optimized implementation of Ford-Fulkerson that uses arrays for all storage.
 * 
 * Ignore typed parameterizations of FlowNetwork since they aren't used in this
 * implementation.
 * 
 * @author George Heineman
 */
public class Optimized extends FlowNetwork<Object> {
	int[][] capacity;	// Contains all capacities.
	int[][] flow;		// Contain all flows.
	int[] previous;		// Contains predecessor information of path.
	int[] visited;		// Visited during augmenting path search.
	
	final int QUEUE_SIZE; // Size of queue will never be greater than n
	final int queue[];    // Use circular queue in implementation
	
    /** 
     * Load up information for this network problem. 
     * @param n   number of vertices
     * @param s   source vertex
     * @param t   target vertex
     * @param edges  iterator of edge information 
     */
	public Optimized (int n, int s, int t, Iterator<EdgeInfo> edges) {
		// Have superclass initialize first.
		super (n, s, t);
		
		queue = new int[n];
		QUEUE_SIZE = n;
		capacity = new int[n][n];
		flow = new int[n][n];
		previous = new int[n];
		visited = new int [n];
		
		// Initially, the flow is set to zero. Pull info from input.
		while (edges.hasNext()) {
			EdgeInfo ei = edges.next();
			capacity[ei.start][ei.end] = ei.capacity;
		}
	}

	/**
	 * Compute and return the maxFlow.
	 * @param source   source vertex to use
	 * @param sink     sink vertex for computation
	 * @return         integer computed as max flow.
	 */
	public int compute (int source, int sink) {
		int maxFlow = 0;
		while (search(source, sink)) { maxFlow += processPath(source, sink); }
		return maxFlow;
	}

	/**
	 * Augment flow within the network along the path found from source to sink.
	 *  
	 * @param source   source vertex in computation
	 * @param sink     sink vertex in computation
	 * @return         integer maximum flow computed
	 */
	protected int processPath(int source, int sink) {
		// Determine amount by which to increment the flow. Equal to
		// minimum over the computed path from sink to source.
		int increment = Integer.MAX_VALUE;
		int v = sink;
		while (previous[v] != -1) {
			int unit = capacity[previous[v]][v] - flow[previous[v]][v];
			if (unit < increment) { increment = unit; }
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
	
	/**
	 * Locate augmenting path in the Flow Network from source to sink
	 * 
	 * @param source  source vertex to use
	 * @param sink    sink vertex to use
	 * @return        true if an augmenting path was discovered
	 */
	public boolean search (int source, int sink) {
		// clear visiting status. 0=clear, 1=actively in queue, 2=visited
		for (int i = 0 ; i < numVertices; i++) { visited[i] = 0; }
		
		// create circular queue to process search elements
		queue[0] = source;
		int head = 0, tail = 1;
		previous[source] = -1;    // make sure we terminate here.
		visited[source] = 1;   	  // actively in queue     
		while (head != tail) {
			int u = queue[head]; head = (head + 1) % QUEUE_SIZE;
			visited[u] = 2;
			
			// add to queue unvisited neighboring vertices of u with enough capacity.
			for (int v = 0; v < numVertices; v++) {
				if (visited[v] == 0 && capacity[u][v] > flow[u][v]) {
					queue[tail] = v;  
					tail = (tail + 1) % QUEUE_SIZE;
					visited[v] = 1;     // actively in queue   
					previous[v] = u;
				}
			}
		}
		
		return visited[sink] != 0;  // did we make it to the sink?
	}

	@Override
	public EdgeInfo edge(int start, int end) {
		throw new RuntimeException ("No information provided via edge Structure. ");
	}

	/** Expect this to be unused. */
	@Override
	public Object getEdgeStructure() {
		throw new RuntimeException ("No information provided via edge Structure. ");
	}

	@Override
	public int getFlow() {
		int totalSum = 0;
		
		for (int v = 0; v < numVertices; v++) {
			totalSum += flow [sourceIndex][v];
		}
		
		return totalSum;	
	}

	/**
	 * The optimized Ford-Fulkerson is not able to process costs.
	 * 
	 * This subclass is a specialized optimization and it is not a problem
	 * that this method has no proper implementation.
	 */
	@Override
	public int getCost () {
		return 0;
	}
	
	@Override
	public DoubleLinkedList<EdgeInfo> getMinCut() {
		DoubleLinkedList<EdgeInfo> dl = new DoubleLinkedList<EdgeInfo>();
		
		// All edges from unvisited vertex labeled to one that  
		// is visited labeled belong in the min cut.
		for (int u = 0; u < numVertices; u++) {
			if (visited[u] != 2) { continue; }
			
			// Find unvisited 'v'. (FORWARD EDGE)
			for (int v = 0; v < numVertices; v++) {
				if (visited[v] == 2) { continue; }
					
				// v not visited. See if edge from u to v
				if (capacity[u][v] > 0) {
					// a forward edge. Add to min-cut
					dl.insert(new EdgeInfo (u, v, capacity[u][v]));
				}
			}
		}
		return dl;
	}

	@Override
	public void validate() throws IllegalStateException {
		for (int u = 0; u < numVertices; u++) {
			for (int v = 0; v < numVertices; v++) {
				
				// bounded flow by capacity
				assert (flow[u][v] <= capacity[u][v]) : "Flow of edge (" + u + "," + v + ") is greater than capacity:" + flow[u][v] + ">" + capacity[u][v];
				
				// reverse must be inverses
				assert (flow[u][v] == -flow[v][u]) : "Flow of edge (" + u + "," + v + ") is not the opposite of edge (" + v + "," + u + ")";
			}
		}
		
		// handle flow conservation
		for (int u = 0; u < numVertices; u++) {
			if (u == sourceIndex || u == sinkIndex) { continue; }
			
			int totalSum = 0;
			for (int v = 0; v < numVertices; v++) {
				if (u == v) { continue; }
				
				totalSum += flow[u][v];
				totalSum -= flow[v][u];
			}
			
			// validate conservation principle
			assert (totalSum == 0) : "Flow conservation not maintained by vertex " + u;
		}
	}
	
	/** Useful for debugging. */
	public String toString () {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				if (j == i) continue;

				if (capacity[i][j] > 0) {
					sb.append (i + " -> " + j + "[" + capacity[i][j] + "]").append('\n');
				}
			}
		}
		
		return sb.toString();
	}
}