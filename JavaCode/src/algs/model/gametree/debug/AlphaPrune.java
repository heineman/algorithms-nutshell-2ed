package algs.model.gametree.debug;

import algs.debug.IGraphEntity;

/**
 * This node is used when depicting debugging information when the AlphaBeta
 * search algorithm prunes the search via an AlphaPrune.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AlphaPrune implements IGraphEntity {
	
	/** Return label for node. */
	public String nodeLabel() {
		return "prune search";
	}
}
