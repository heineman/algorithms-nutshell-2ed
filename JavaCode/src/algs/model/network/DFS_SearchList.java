package algs.model.network;

import java.util.Iterator;
import java.util.Stack;

/**
 * Encapsulate algorithm by which the augmenting path for a flow network is found.
 * <p>
 * Use depth-first search as implemented using a Stack.
 * 
 * Parameterized to work with an edge structure based on VertexStructure array.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DFS_SearchList extends Search {
	
	/** Store relevant network to operate on. */
	FlowNetwork<VertexStructure[]> network;
	
	/**
	 * @see Search#Search(FlowNetwork)
	 * @param network   A representation of the Flow Network.
	 */
	public DFS_SearchList (FlowNetwork<VertexStructure[]> network) {
		super (network);
		
		this.network = network;
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

		// Process forward edges from u; then try backward edges
		VertexStructure struct[] = network.getEdgeStructure();
		while (!path.isEmpty()) {
			int u = path.pop();
			
			// try to make forward progress first...
			Iterator<EdgeInfo> it = struct[u].forward();
			while (it.hasNext()) {
				EdgeInfo ei = it.next();
				int v = ei.end;
				
				// not yet visited AND has unused capacity? Plan to increase.
				if (vertices[v] == null && ei.capacity > ei.flow) {
					vertices[v] = new VertexInfo (u, FORWARD);

					if (v == sinkIndex) { return true; }  // we have found one!
					path.push (v);
				}
			}
			
			// try backward edges
			it = struct[u].backward();
			while (it.hasNext()) {
				// try to find an incoming edge into u whose flow can be reduced.
				EdgeInfo rei = it.next();
				int v = rei.start;  
				
				// now try backward edge not yet visited (can't be sink!)
				if (vertices[v] == null && rei.flow > 0) {
					vertices[v] = new VertexInfo (u, BACKWARD);
					path.push(v);
				}
			}
		}
		
		// nothing
		return false;
	}
}
