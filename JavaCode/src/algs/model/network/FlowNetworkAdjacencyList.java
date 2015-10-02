package algs.model.network;

import java.util.Iterator;

import algs.model.list.DoubleLinkedList;
import algs.model.list.List;
import algs.model.list.Node;

/**
 * Store information regarding the graph as an adjacency list. 
 * <p>
 * Several key steps in this algorithm depend on identifying both the forward edges
 * leaving a vertex Vi as well as the incoming edges into a vertex. If we only stored 
 * the forward directed edges, we would have to scan all remaining vertices to locate 
 * any that have outgoing edges into Vi. 
 * <p>
 * For this reason, we maintain two separate forward and backward edge lists.  
 * This means we have twice as many edges as are needed. The structure used to
 * maintain both lists is {@link VertexStructure}.
 * 
 * @author George Heineman
 */
public class FlowNetworkAdjacencyList extends FlowNetwork<VertexStructure[]> {
	
	/** 
	 * Each entry info[i] maintains separate list of the incoming edges and outgoing
	 * edges for each vertex.
	 */
	VertexStructure info[];

	/**
	 * Construct an instance of the FlowNetwork problem using a two-dimensional array to 
	 * store the edge information.
	 * 
	 * @param numVertices    the number of vertices in the Flow Network
	 * @param srcIndex       the index of the vertex designated to be the source
	 * @param tgtIndex       the index of the vertex designated to be the sink
	 * @param edges          an iterator of EdgeInfo objects representing edge capacities
	 *
	 * @see FlowNetwork#FlowNetwork(int, int, int)
	 */
	public FlowNetworkAdjacencyList (int numVertices, int srcIndex, int tgtIndex, Iterator<EdgeInfo> edges) {
		super (numVertices, srcIndex, tgtIndex);
		
		// Note that initially, the flow is set to zero. Populate adjacency lists.
		info = VertexStructure.construct(numVertices);
		while (edges.hasNext()) {
			EdgeInfo ei = edges.next();

			info[ei.start].addForward(ei);
			info[ei.end].addBackward(ei);
		}
	}
	
	/**
	 * @see FlowNetwork#getEdgeStructure()
	 */
	@Override
	public VertexStructure[] getEdgeStructure() {
		return info;
	}
	
	/**
	 * Given an adjacency list for vertex 'n', find the edge (if it exists) from start to end.
	 * 
	 * @param start     index of start vertex
	 * @param end       index of end vertex.
	 * 
	 * @return edge if it exists as a directed edge; <code>null</code> otherwise. 
	 */
	@Override
	public EdgeInfo edge(int start, int end) {
		List<EdgeInfo> list = info[start].forward;
		if (list == null) { return null; }
		
		// find if available.
		Node<EdgeInfo> n = list.head();
		while (n != null) {
			EdgeInfo inf = n.value();
			if (inf.end == end) {
				return inf;
			}
			
			n = n.next();
		}
		
		return null;
	}

	@Override
	public DoubleLinkedList<EdgeInfo> getMinCut() {
		DoubleLinkedList<EdgeInfo> dl = new DoubleLinkedList<EdgeInfo>();
		
		// All edges from a vertex that is labeled to one that is not labeled belong in the min cut.
		for (int u = 0; u < numVertices; u++) {
			if (vertices[u] == null) { continue; }
			
			// labeled u. Find unlabeled 'v'. (FORWARD EDGE)
			VertexStructure struct = info[u];
			for (Iterator<EdgeInfo> it = struct.forward(); it.hasNext(); ) {
				EdgeInfo ei = it.next();

				// v was not visted (since null in vertices entry) so we can add to mincut list.
				if (vertices[ei.end] == null) {
					dl.insert(ei);
				}
			}
		}
		
		return dl;
	}
	

	@Override
	public int getFlow() {
		int totalSum = 0;
		
		VertexStructure struct = info[sourceIndex];
		for (Iterator<EdgeInfo> it = struct.forward(); it.hasNext(); ) {
			EdgeInfo ei = it.next();

			totalSum += ei.getFlow();
		}
		
		return totalSum;
	}
	
	@Override
	public int getCost () {
		int totalCost = 0;
		
		VertexStructure struct = info[sourceIndex];
		for (Iterator<EdgeInfo> it = struct.forward(); it.hasNext(); ) {
			EdgeInfo ei = it.next();

			totalCost += ei.getFlow() * ei.cost;
		}
		
		return totalCost;
	}
	
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
	 */
	public void validate () throws IllegalStateException {
		for (int u = 0; u < numVertices; u++) {
			VertexStructure struct = info[u];
			
			// has no forward edges.
			if (struct.forward.isEmpty()) {
				continue; 
			}   
				
			Node<EdgeInfo> n = struct.forward.head();
			while (n != null) {
				EdgeInfo ei = (EdgeInfo) n.value();
				int flow = ei.flow;
				int v = ei.start;

				// make sure flow is no greater than capacity.
				assert (flow <= ei.capacity) : "Flow of edge (" + u + "," + v + ") is greater than capacity:" + flow + ">" + ei.capacity;
				
				EdgeInfo rei = edge (v, u);
				if (rei != null) {
					// at least one exists.  note 'flow' contains flow for [u][v]. OK if the
					// forward flow is zero for the reverse edge not to exist. If it does exist,
					// however, then its flow must be zero also.
					assert (flow == -rei.flow) : "Flow of edge (" + u + "," + v + ") is not the opposite of edge (" + v + "," + u + ")";
				}
				
				n = n.next();
			}
		}
		
		// handle flow conservation. Incoming flow(s) must equal outgoing flow(s). Outgoing flows
		// determined by info[u]. Incoming flows must be searched through all adjacency lists. This
		// is not an efficient operation with adjacency lists. Fortunately, this operation is only
		// a litmus test for validating a flow (and is optional).
	
		for (int u = 0; u < numVertices; u++) {
			// because we can ignore source and sink index, there is no need to check whether
			// a node's set of forward edges is empty.
			if (u == sourceIndex || u == sinkIndex) { continue; }
			VertexStructure struct = info[u];
			
			int totalSum = 0;
			
			// outgoing flows
			Node<EdgeInfo> n = struct.forward.head();
			while (n != null) {
				totalSum += n.value().flow;
				
				n = n.next();
			}
			
			// incoming flows.
			n = struct.backward.head();
			while (n != null) {
				totalSum -= n.value().flow;
				
				n = n.next();
			}

			// validate conservation principle.
			assert (totalSum == 0) : "Flow conservation not maintained by vertex " + u;
		}
	}
	
	/** Useful for debugging. */
	public String toString () {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numVertices; i++) {
			if (!info[i].forward.isEmpty()) {
				Node<EdgeInfo> n = info[i].forward.head();
				while (n != null) {
					sb.append (n.value()).append('\n');
					n = n.next();
				}
			}
		}
		
		return sb.toString() + "\ncost:" + getCost() + "\nflow:" + getFlow();
	}

	
}
