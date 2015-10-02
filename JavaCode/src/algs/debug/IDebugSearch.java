package algs.debug;

/**
 * Used by animation and other purposes to track the status of a Search.
 * <p>
 * This debugger gathers statistics during each visitNode and transition 
 * method calls. Simple debuggers could output information as it happens.
 * More complex debuggers will build a model that will only be output at
 * the completion of the search, which is known after the <code>{@link #complete()}</code>
 * method is invoked.
 * <p>
 * The arguments are all generic, since the debugger that implements this interface
 * is the only one who knows the true type of the information. Naturally, make
 * sure you only attach a proper debugger to the proper information source.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IDebugSearch {

	/**
	 * Visit a node.
	 * 
	 * @param n   node to be visited
	 */
	void visitNode (IGraphEntity n);
	
	/** 
	 * Visit an edge. Only invoke after both nodes have been visited.
	 * 
	 * @param n1   source node
	 * @param n2   target node
	 */
	void visitEdge (IGraphEntity n1, IGraphEntity n2);
	
	/** 
	 * Mark search initial location. Only invoke after Node has been visited.
	 * 
	 * @param n    Node to be marked
	 */
	void markStart(IGraphEntity n);

	/** 
	 * Mark search goal (or null for failed search). Only invoke after Node has been 
	 * visited.
	 * 
	 * @param n    Node to be marked
	 */
	void markGoal(IGraphEntity n);
	
	/** 
	 * Mark node as being unexplored. These are typically nodes placed on the 
	 * OPEN list that were never explored. Since they appear in the output, 
	 * we don't want to give the impression that they were considered.
	 * 
	 * @param n   node to be marked
	 */
	void markUnexplored (IGraphEntity n);
	
	/** 
	 * Mark edge that was part of a solution.
	 * 
	 * Only invoke after edge has been visited.
	 * 
	 * @param n1   source of edge
	 * @param n2   target of edge
	 */
	void markEdge(IGraphEntity n1, IGraphEntity n2);
	
	/** Complete any processing to make this information available. */
	void complete();
}
