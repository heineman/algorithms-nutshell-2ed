package algs.debug;

/** 
 * Interface to define functionality for drawing nodes in Dotty format.
 * <p>
 * The following node drawers are defined for Black &amp; White output, suitable for book
 * publication.
 * <ul>
 * <li>InitialNode.     Outlined node, filled with light gray, line color black</li>
 * <li>GoalNode.        Outlined node, filled with light gray, but with peripheries=2, black line</li>
 * <li>UnexploredNode.  Font color is gray, line color is gray</li>
 * <li>DiscardedNode.   Not used</li>
 * <li>DefaultNode.     Normal drawing</li>
 * </ul>
 *   
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface INodeDrawer {
	
	/** 
	 * Return the proper formatting for the given node.
	 * 
	 * @param n  desired node to be drawn.
	 * @return   string representing entity.
	 */
	String draw (IGraphEntity n);
}
