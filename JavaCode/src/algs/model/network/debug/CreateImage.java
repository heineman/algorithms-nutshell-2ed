package algs.model.network.debug;

import java.util.Hashtable;

import algs.debug.DottyDebugger;
import algs.debug.IGraphEntity;
import algs.model.list.DoubleLinkedList;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetwork;

/**
 * Given a FlowNetwork object, use the DottyDebugger to represent the flow 
 * graph.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class CreateImage {
	
	/** Default vertex pool that will appear in the output. */
	Hashtable<Integer, IGraphEntity> nodes = new Hashtable<Integer, IGraphEntity>();
	
	/** Debugger to use, which defaults to having directed edges shown. */
	protected final DottyDebugger dd = new DottyDebugger() {
		/** 
		 * Default to having nodes with complex record shapes.
		 */
		public String edgeType() {
			return "dir=forward";
		}
	};
	
	/** 
	 * Output to stdout a dotty representation without cut. 
	 * 
	 * @param fn   The flow network
	 */
	public void outputGraph (FlowNetwork<?> fn) {
		outputGraph(fn, false);
	}

	/** 
	 * Output to stdout a dotty representation and include the minCut.
	 * 
	 * @param fn              the flow network
	 * @param includeCut      determine whether to include the cut
	 */
	public void outputGraph (FlowNetwork<?> fn, boolean includeCut) {
		System.out.println (computeDotty(fn, includeCut));
	}

	/** 
	 * Compute dotty representation and include the minCut.
	 * 
	 * @param fn              the flow network
	 * @param includeCut      determine whether to include the cut
	 * @return string to use for DOT execution.
	 */
	public String computeDotty (FlowNetwork<?> fn, boolean includeCut) {
		
		// get cut information
		DoubleLinkedList<EdgeInfo> mincut = null;
		if (includeCut) { mincut = fn.getMinCut(); }
		
		// make left to right.
		dd.rank("LR");
		
		assignVertices(fn);
		
		// mark source and sink
		dd.markStart(get(fn.sourceIndex));
		dd.markGoal(get(fn.sinkIndex));
		
		// for all edges, these are directed.
		for (int i = 0; i < fn.numVertices; i++) {
			for (int j = 0; j < fn.numVertices; j++) {
				EdgeInfo ei = fn.edge(i, j);
				if (ei != null) {
					String label = "\"" + ei.getFlow() +
					               "/" + ei.capacity;
					if (ei.cost>0) { label += "@" + ei.cost; } 
					label += "\"";
					dd.visitEdge(get(i), get(j), label);
				}
				
				if (includeCut) {
					if (mincut.contains(ei) != null) {
						dd.markEdge(get(i), get(j));
					}
				}
			}
		}
		
		return (dd.getInputString());
	}

	/** 
	 * Get the node identified by the integer index. 
	 * 
	 * @param idx
	 */
	protected IGraphEntity get(int idx) {
		return nodes.get(idx);
	}

	/** 
	 * Set the node to be associated with the integer index.
	 * 
	 * @param idx     unique index value
	 * @param ge      graph entity to be associated with that index.
	 */
	protected void set(int idx, IGraphEntity ge) {
		nodes.put(idx, ge);
	}
	
	/**
	 * Assign node entities for all nodes in the graph according to their 
	 * unique indices.
	 * 
	 * Can be overridden for specialized displaying of nodes
	 * 
	 * @param fn   the flow network problem.
	 */
	protected void assignVertices(FlowNetwork<?> fn) {
		for (int i = 0; i < fn.numVertices; i++) {
			final int id = i;
			IGraphEntity node = new IGraphEntity() {

				public String nodeLabel() {
					return "" + id;
				}
			};
			
			nodes.put(i, node);
		}
		
		// now visit each one.
		for (int i = 0; i < fn.numVertices; i++) {
			dd.visitNode(nodes.get(i));
		}
	}

}
