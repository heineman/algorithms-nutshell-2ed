package algs.debug.drawers;

import algs.debug.IGraphEntity;
import algs.debug.INodeDrawer;

/**
 * Capable of drawing the initial node in the DOTTY debugging output.
 * <p>
 * Outlined node, filled with light gray, line color black.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class InitialNodeDrawer implements INodeDrawer {

	/** 
	 * Half-grayscale colored nodes. 
	 * 
	 * @param  n   Node to be drawn.
	 */
	public String draw(IGraphEntity n) {
		return  "[style=filled,fillcolor=\"gray50\" label=\"" + n.nodeLabel() + "\"]";
	}
}
