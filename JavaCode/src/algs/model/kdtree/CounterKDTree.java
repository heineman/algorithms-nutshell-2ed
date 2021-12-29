package algs.model.kdtree;

/**
 * Helper class to simply keep track of the number of selected nodes that are 
 * visited by a traversal.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class CounterKDTree implements IVisitKDNode {

	/** Number found. */
	int number;
	
	/** Instantiate node that records number of traversals. */
	public CounterKDTree() {
		number = 0;
	}
	
	/** Each drain requests increments count. */
	public void drain(DimensionalNode node) {
		number++;
	}

	/** Visit will increment count. */
	public void visit(DimensionalNode node) {
		number++;
	}

	/** Reset the counter. */
	public void reset() {
		number = 0;
	}
	
	/** 
	 * Retrieve the counter value.
	 * @return count after all visitors have been processed. 
	 */
	public int getCount() {
		return number;
	}
}
