package algs.model.network;

import java.util.Stack;

/**
 * Encapsulate algorithm by which the augmenting path for a flow network
 * is found.
 * <p>
 * Use depth-first search as implemented using a Stack.
 * 
 * parameterized to work with EdgeInfo[][] array structure.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DFS_SearchArray extends Search {
	/** Edge information stored in 2d Array. */
	EdgeInfo info[][];

	/**
	 * @see Search#Search(FlowNetwork)
	 * @param network     represents flow network
	 */
	public DFS_SearchArray (FlowNetwork<EdgeInfo[][]> network) {
		super (network);
		
		info = network.getEdgeStructure();
	}

	/** 
	 * @see Search#findAugmentingPath(VertexInfo[])
	 */
	@Override
	public boolean findAugmentingPath (VertexInfo []vertices) {
	    // Begin the potential augmenting path at source with as much flow as possible.
		vertices[sourceIndex] = new VertexInfo (-1);
		Stack<Integer> path = new Stack<Integer>();
		path.push (sourceIndex);
		
		while (!path.isEmpty()) {
			int u = path.pop();
			
			// find all unvisited vertices connected to u by edges. 
			for (int v = 0; v < numVertices; v++) {
				EdgeInfo inf;
				if (v == u) continue;                // can't visit self.
				if (vertices[v] != null) continue;   // has already been visited.
				
				if (((inf = info[u][v]) != null) && (inf.capacity > inf.flow)) {
					// increase forward flow, if the edge exists and capacity still allows
					vertices[v] = new VertexInfo (u, FORWARD);
				} else if (((inf = info[v][u]) != null)) {
					 if (inf.flow > 0) {
						// decrease reverse flow, if the reverse edge exists and units are flowing
						vertices[v] = new VertexInfo (u, BACKWARD);
					 }
				}
				
				if (vertices[v] != null) {
					if (v == sinkIndex) {
						// we now have a path from source to sink.
						return true;
					} else {
						// continue looking for a path with this vertex
						path.push(v);
					}
				}
			}
		}
		
		// nothing
		return false;
	}
}
