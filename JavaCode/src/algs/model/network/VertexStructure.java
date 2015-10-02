package algs.model.network;

import java.util.Iterator;

import algs.model.list.List;

/**
 * Records information about a vertex's forward (Outgoing) edges and backward (Incoming)
 * edges. 
 * 
 * @author George Heineman
 */
public class VertexStructure {
	
	/**
	 * Construct an array of VertexStructure objects, where each element is
	 * pre-initialized to a new VertexStructure.
	 * 
	 * @param numVertices     the size of the network problem
	 * @return                structure to use during NetworkFlow
	 */
	public static VertexStructure[] construct (int numVertices) {
		VertexStructure struct[] = new VertexStructure [numVertices];
		for (int i = 0; i < numVertices; i++) {
			struct[i] = new VertexStructure();
		}
		
		return struct;
	}
	
	/** 
	 * Each entry info[i] is a linked list of EdgeInfo representing 
	 * the outgoing edges of the vertex.
	 */
	List<EdgeInfo> forward = new List<EdgeInfo>();
	
	/** 
	 * Each entry info[i] is a linked list of EdgeInfo representing 
	 * the incoming edges of the vertex.
	 */
	List<EdgeInfo> backward = new List<EdgeInfo>();

	/**
	 * Return iterator over forward edges.
	 * @return iterator of EdgeInfo for forward edges.
	 */
	public Iterator<EdgeInfo> forward() {
		return forward.iterator();
	}
	
	/**
	 * Return iterator over backward edges.
	 * @return iterator of EdgeInfo for backward edges.
	 */
	public Iterator<EdgeInfo> backward() {
		return backward.iterator();
	}
	
	/**
	 * Add the given edge into the list of forward edges.
	 * 
	 * @param ei   The designated edge with capacity information.
	 */
	public void addForward(EdgeInfo ei) {
		forward.append(ei);		
	}

	/**
	 * Add the given edge into the list of backward edges.
	 * 
	 * @param ei   The designated edge with capacity information.
	 */
	public void addBackward(EdgeInfo ei) {
		backward.append(ei);		
	}
	
	/** 
	 * Useful for debugging.
	 * @return meaningful string representation 
	 */
	public String toString () {
		return "forward:" + forward + ", backward:" + backward;
	}
}
