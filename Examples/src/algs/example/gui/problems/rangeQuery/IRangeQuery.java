package algs.example.gui.problems.rangeQuery;

import algs.model.IRectangle;

/**
 * Interface that defines a range query computation.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IRangeQuery<E extends ISelectable> {
	
	/**
	 * Process the query and return the number of underlying entities found
	 * that fall within the two-dimensional query rectangle.
	 * 
	 * @param query    Two-dimensional rectangle for query
	 */
	int compute (IRectangle query);
	
	/** Return the computed time to complete task. */
	long time();
	
}
