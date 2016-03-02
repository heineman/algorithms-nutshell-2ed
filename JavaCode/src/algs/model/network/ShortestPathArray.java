package algs.model.network;

import java.util.Arrays;

import algs.model.heap.BinaryHeap;

/**
 * Encapsulate algorithm by which the augmenting path for a Minimal Cost, Maximal
 * Flow network is found.
 * <p>
 * Use priority queue.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ShortestPathArray extends Search {
	/** Edge information stored in 2d Array. */
	final EdgeInfo info[][];

	/** Need source. */
	final int sourceIndex;
	
	/** Need sink. */
	final int sinkIndex;
	
	/** Computed distances. */
	final int dist[];        
	
	/**
	 * @see Search#Search(FlowNetwork)
	 * @param  network    initial Flow Network.
	 */
	public ShortestPathArray (FlowNetwork<EdgeInfo[][]> network) {
		super (network);
		
		info = network.getEdgeStructure();
		dist = new int [network.numVertices];
		sourceIndex = network.sourceIndex;
		sinkIndex = network.sinkIndex;
	}
	
	/** 
	 * Determine whether an augmenting path was found.
	 * 
	 * If the sink Index has a non-infinite distance, then some augmenting path
	 * was indeed found.
	 * 
	 * @see Search#findAugmentingPath(VertexInfo[])
	 */
	@Override
	public boolean findAugmentingPath (VertexInfo[] vertices) {
		Arrays.fill(vertices, null);   // reset for iteration
		
		// Construct queue using BinaryHeap. The inqueue[] array avoids
		// an O(n) search to determine if an element is in the queue. 
		int n = vertices.length;
		BinaryHeap<Integer> pq = new BinaryHeap<Integer> (n); 
		boolean inqueue[] = new boolean [n];
		
		// initialize dist[] array. Use INT_MAX when edge doesn't exist.
		for (int u = 0; u < n; u++) {
			if (u == sourceIndex) { 
				dist[u] = 0;
				pq.insert(sourceIndex, 0);
				inqueue[u] = true;
			} else {
				dist[u] = Integer.MAX_VALUE;
			}
		}
		
		while (!pq.isEmpty()) {
			int u = pq.smallestID();
			inqueue[u] = false;
			
			// When reach sinkIndex we are done.
			if (u == sinkIndex) { break; }
			
			for (int v = 0; v < n; v++) {
				if (v == sourceIndex || v == u) continue;
				
				// forward edge with remaining capacity if cost is better.
				EdgeInfo cei = info[u][v];
				if (cei != null && cei.flow < cei.capacity) {
					int newDist = dist[u] + cei.cost;
					if (0 <= newDist && newDist < dist[v]) {
						vertices[v] = new VertexInfo (u, Search.FORWARD);
						dist[v] = newDist;
						if (inqueue[v]) {
							pq.decreaseKey(v, newDist);
						} else {
							pq.insert(v, newDist);
							inqueue[v] = true;
						}
					}
				}
				
				// backward edge with at least some flow if cost is better
				cei = info[v][u];
				if (cei != null && cei.flow > 0) {
					int newDist = dist[u] - cei.cost;
					if (0 <= newDist && newDist < dist[v]) {
						vertices[v] = new VertexInfo (u, Search.BACKWARD);
						dist[v] = newDist;
						if (inqueue[v]) {
							pq.decreaseKey(v, newDist);
						} else {
							pq.insert(v, newDist);
							inqueue[v] = true;
						}
					}
				}
			}
		}
		
		return dist[sinkIndex] != Integer.MAX_VALUE;
	}
}
