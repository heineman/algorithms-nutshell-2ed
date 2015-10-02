package algs.debug.drawers;

import algs.debug.IGraphEntity;
import algs.debug.INodeDrawer;

/**
 * Capable of drawing the goal node in the DOTTY debugging output.
 * <p>
 * Drawn with 50% fill color and the word GOAL is appended to the output
 * to clearly mark it as being special.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class GoalNodeDrawer implements INodeDrawer {

	/** 
	 * Goal node is drawn as a pentagon.
	 * 
	 * @param n   goal node.
	 */
	public String draw(IGraphEntity n) {
		return  "[style=filled fillcolor=gray50 " + 
				"label=\"" + n.nodeLabel() + "|GOAL\"]";
	}

}
