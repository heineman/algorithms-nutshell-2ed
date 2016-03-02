package algs.model.network;

import java.util.Iterator;
import algs.model.list.DoubleLinkedList;

/**
 * Encapsulate algorithm by which the augmenting path for a flow network is found when the underlying
 * data structure is represented as an adjacency list.
 * <p>
 * Use Breadth-first search as implemented using DoubleLinkedList as a queue.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BFS_SearchList extends Search {
	/** Store network over which to search. */
	FlowNetwork<VertexStructure[]> network;
	
	/**
	 * @see Search#Search(FlowNetwork)
	 * @param network   a representation of the FlowNetwork.
	 */
	public BFS_SearchList (FlowNetwork<VertexStructure[]> network) {
		super (network);
		
		this.network = network;
	}

	/** 
	 * @see Search#findAugmentingPath(VertexInfo[])
	 */
	@Override
	public boolean findAugmentingPath (VertexInfo[] vertices) {
	    // Begin potential augmenting path at source with as much flow as possible.
		vertices[sourceIndex] = new VertexInfo (-1);
		DoubleLinkedList<Integer> path = new DoubleLinkedList<Integer>();
		path.insert(sourceIndex);

		// Process forward edges from u; then try backward edges
		VertexStructure struct[] = network.getEdgeStructure();
		while (!path.isEmpty()) {
			int u = path.removeFirst();

			// edges out from u
			Iterator<EdgeInfo> it = struct[u].forward();
			while (it.hasNext()) {
				EdgeInfo ei = it.next();
				int v = ei.end;

				// if not yet visited AND has unused capacity? Plan to increase.
				if (vertices[v] == null && ei.capacity > ei.flow) {
					vertices[v] = new VertexInfo (u, FORWARD);

					if (v == sinkIndex) { return true; }	// path is complete.
					path.insert (v);						// otherwise append to queue
				}
			}

			// backward edges into u
			it = struct[u].backward();
			while (it.hasNext()) {
				// try to find an incoming edge into u whose flow can be reduced.
				EdgeInfo rei = it.next();
				int v = rei.start;  

				// Not yet visited (can't be sink!) AND has flow to be decreased?
				if (vertices[v] == null && rei.flow > 0) {
					vertices[v] = new VertexInfo (u, BACKWARD);
					path.insert(v);							// append to queue
				}
			}
		}

		return false;		// no augmented path located
	}
}
