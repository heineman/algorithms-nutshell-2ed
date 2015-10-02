package algs.model.network;

import java.util.Iterator;

import algs.model.list.DoubleLinkedList;

/**
 * Store information regarding the graph in a two-dimensional matrix. The
 * matrix may be either sparse or dense, depending upon the problem at hand.
 * <p>
 * @author George Heineman
 */
public class FlowNetworkArray extends FlowNetwork<EdgeInfo[][]> {
	
	/** Contains all edges together with their capacities. */
	EdgeInfo[][]  info;
	
	/**
	 * Construct an instance of the FlowNetwork problem using a two-dimensional array to 
	 * store the edge information.
	 * 
	 * For sanity sake, clear out any existing flow
	 * 
	 * @param numVertices    the number of vertices in the Flow Network
	 * @param srcIndex       the index of the vertex designated to be the source
	 * @param sinkIndex       the index of the vertex designated to be the sink
	 * @param edges          an iterator of EdgeInfo objects representing edge capacities
	 *
	 * @see FlowNetwork#FlowNetwork(int, int, int)
	 */
	public FlowNetworkArray (int numVertices, int srcIndex, int sinkIndex, Iterator<EdgeInfo> edges) {
		super (numVertices, srcIndex, sinkIndex);
		
		populate (edges);
	}
	
	/**
	 * Helper method to populate edges.
	 * 
	 * Useful for subclasses as well. Note that this allocates the matrix storage
	 * so it effectively replaces the entire edge structure found in the graph.
	 * Be careful when using this!
	 * 
	 * @param edges   Iterator of all edges in the network.
	 */
	protected void populate(Iterator<EdgeInfo> edges) {
		info = new EdgeInfo[numVertices][numVertices];
		
		// Note that initially, the flow is set to zero.
		while (edges.hasNext()) {
			EdgeInfo ei = edges.next();
			ei.flow = 0;                      // for the sake of sanity, clear out
			info[ei.start][ei.end] = ei;
		}
	}
	
	/** 
	 * Minimal constructor for use by subclasses.
	 * 
	 * @param sourceIndex   the sourceIndex to use for network (typically 0)
	 * @param sinkIndex     the sinkIndex to use for network
	 * @param numVertices   the number of vertices
	 */
	protected FlowNetworkArray (int sourceIndex, int sinkIndex, int numVertices) { 
		super (sourceIndex, sinkIndex, numVertices);
	}
	
	@Override
	public EdgeInfo[][] getEdgeStructure() {
		return info;
	}
	
	@Override
	public EdgeInfo edge(int start, int end) {
		return info[start][end];
	}
	
	@Override
	public DoubleLinkedList<EdgeInfo> getMinCut() {
		DoubleLinkedList<EdgeInfo> dl = new DoubleLinkedList<EdgeInfo>();
		
		// All edges from a vertex that is labeled to one that is not labeled belong in the min cut.
		for (int u = 0; u < numVertices; u++) {
			if (vertices[u] != null) {
				// labeled u. Find unlabeled 'v'. (FORWARD EDGE)
				for (int v = 0; v < numVertices; v++) {
					if (u == v) { continue; }
					if (vertices[v] != null) { continue; }
					
					// v not visited. See if edge from u to v
					EdgeInfo inf;
					if ((inf = info[u][v]) != null) {
						// a forward edge. Add to min-cut
						dl.insert(inf);
					}
				}
			}
		}
		return dl;
	}
	

	@Override
	public int getFlow() {
		int totalSum = 0;
		
		for (int v = 0; v < numVertices; v++) {
			EdgeInfo ei = info[sourceIndex][v];
			if (ei != null) {
				totalSum += ei.getFlow();
			}
		}
		
		return totalSum;
	}
	
	@Override
	public int getCost () {
		int cost = 0;
		
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				if (i == j) continue;
				
				EdgeInfo cei = info[i][j];
				if (cei != null) {
					cost += cei.flow * cei.cost;
				}
			}
		}
		
		return cost;
	}
	
	@Override
	public void validate () throws IllegalStateException {
		for (int u = 0; u < numVertices; u++) {
			for (int v = 0; v < numVertices; v++) {
				if (u == v) { continue; }
				
				EdgeInfo ei = info[u][v];
				int flow = 0;
				
				if (ei != null) {
					flow = ei.flow;
					
					assert (flow <= ei.capacity) : "Flow of edge (" + u + "," + v + ") is greater than capacity:" + flow + ">" + ei.capacity;
				}
				
				EdgeInfo rei = info[v][u];
				// By default, an empty edge is implied to have the negative flow of the [u,v]
				if (rei == null || ei==null) {
					continue;
				}
				
				assert (ei.flow == -rei.flow) : "Flow of edge (" + u + "," + v + ") is not the opposite of edge (" + v + "," + u + ")";
			}
		}
		
		// handle flow conservation
		for (int u = 0; u < numVertices; u++) {
			if (u == sourceIndex || u == sinkIndex) { continue; }
			
			int totalSum = 0;
			for (int v = 0; v < numVertices; v++) {
				if (u == v) { continue; }
				
				EdgeInfo ei = info[u][v];
				EdgeInfo rei = info[v][u];
				if (ei != null) {
					totalSum += ei.flow;
				}
				if (rei != null) {
					totalSum -= rei.flow;
				}
			}
			
			assert (totalSum == 0) : "Flow conservation not maintained by vertex " + u;
		}
	}
	
	/** Useful for debugging. */
	public String toString () {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				if (j == i) continue;

				EdgeInfo inf = info[i][j];
				if (inf != null) {
					sb.append (inf).append('\n');
				}
			}
		}
		
		return sb.toString() + "\ncost:" + getCost() + "\nflow:" + getFlow();
	}

}
