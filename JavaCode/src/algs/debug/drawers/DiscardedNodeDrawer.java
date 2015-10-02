package algs.debug.drawers;

import algs.debug.IGraphEntity;
import algs.debug.INodeDrawer;

/**
 * Capable of drawing discarded nodes in the DOTTY debugging output.
 * <p>
 * Not used to date.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DiscardedNodeDrawer implements INodeDrawer {

	/** 
	 * Discarded nodes are drawn in grayscale.
	 * 
	 * @param n   node to be drawn as discarded.
	 */
	public String draw(IGraphEntity n) {
		return  "[fillcolor=\".8 .8 .8\" label=\"" + n.nodeLabel() + "\"]";
	}

}
