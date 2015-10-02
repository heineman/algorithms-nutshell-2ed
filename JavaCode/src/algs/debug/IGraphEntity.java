package algs.debug;

/**
 * All graphical state drawing depends on the individual nodes to be able to return a labeled
 * string to represent itself.
 * 
 * We distribute the logic to the individual entities themselves, which simplifies
 * the drawing of debugging information at the expense of adding this extra method
 * to classes which otherwise should be unaware of the debugging infrastructure.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IGraphEntity {

	/** 
	 * Return string label for this entity.
	 * @return label associated with entity. 
	 */
	String nodeLabel();
}
