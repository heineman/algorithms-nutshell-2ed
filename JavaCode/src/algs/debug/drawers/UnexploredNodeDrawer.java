package algs.debug.drawers;

import algs.debug.IGraphEntity;
import algs.debug.INodeDrawer;

/**
 * Capable of drawing unexplored nodes in the DOTTY debugging output.
 * <p>
 * Outlined node, filled with light gray, line color black.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class UnexploredNodeDrawer implements INodeDrawer {

	/** 
	 * 60% drawn color with fonts in 20% color and a background fill to show
	 * that this node is unexplored.
	 * 
	 * @param n   entity to be represented
	 */
	public String draw(IGraphEntity n) {
		return  "[style=filled,color=\"gray60\" fontcolor=\"gray20\" fillcolor=\"gray80\" label=\"" + n.nodeLabel() + "\"]";
	}
}
