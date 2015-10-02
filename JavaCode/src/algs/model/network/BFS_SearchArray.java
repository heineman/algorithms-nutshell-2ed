package algs.model.network;

import algs.model.list.DoubleLinkedList;

/**
 * Encapsulate algorithm by which the augmenting path for a flow network
 * is found.
 * <p>
 * Use Breadth-first search as implemented using DoubleLinkedList as a queue.
 * Only works with FlowNetwork classes parameterized to use EdgeInfo[][] as 
 * the edge structure.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BFS_SearchArray extends Search {
	/** Edge information stored in 2d Array. */
	EdgeInfo info[][];
	
	/**
	 * @see Search#Search(FlowNetwork)
	 * @param network      encodes the FlowNetwork problem
	 */
	public BFS_SearchArray (FlowNetwork<EdgeInfo[][]> network) {
		super (network);
		
		info = network.getEdgeStructure();
	}

	/** 
	 * @see Search#findAugmentingPath(VertexInfo[])
 	 * @param vertices      computes whether augmenting path exists
 	 * @return  true if augmenting path was found.
	 */
	@Override
	public boolean findAugmentingPath (VertexInfo[] vertices) {
	    // Begin the potential augmenting path at source with as much flow as possible.
		vertices[sourceIndex] = new VertexInfo (-1);
		DoubleLinkedList<Integer> path = new DoubleLinkedList<Integer>();
		path.insert(sourceIndex);
		
		while (!path.isEmpty()) {
			int u = path.removeFirst();
			
			// find all unvisited vertices connected to u by edges. 
			for (int v = 0; v < numVertices; v++) {
				EdgeInfo inf;
				if (v == u) continue;                // can't visit self.
				if (vertices[v] != null) continue;   // has already been visited.
				
				if (((inf = info[u][v]) != null) && (inf.capacity > inf.flow)) {
					// increase forward flow, if the edge exists and capacity still allows
					vertices[v] = new VertexInfo (u, FORWARD);
				} else if (((inf = info[v][u]) != null) && (inf.flow > 0)) {
					// decrease reverse flow, if the reverse edge exists and units are flowing
					vertices[v] = new VertexInfo (u, BACKWARD);
				}
				
				if (vertices[v] != null) {
					if (v == sinkIndex) {
						// we now have a path from source to sink.
						return true;
					} else {
						// continue looking for a path with this vertex
						path.insert(v);
					}
				}
			}
		}
		
		// nothing
		return false;
	}
}
