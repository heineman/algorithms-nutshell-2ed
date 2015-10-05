package algs.model.tests.interval;

import algs.model.IBinaryTreeNode;
import algs.model.interval.IConstructor;
import algs.model.interval.SegmentTreeNode;

public class SpecialSegmentTreeNode<T extends IBinaryTreeNode<T>> extends SegmentTreeNode<T> {
	
	/** Store String. */
	String lowValue;
	
	/** Store another thing for testing. */
	String highValue;

	/** Singleton constructor. */
	private static IConstructor<?> constructor = null;
	
	/**
	 * Store additional information as a test.
	 * 
	 * @param left
	 * @param right
	 */
	SpecialSegmentTreeNode(int left, int right) {
		super(left, right);
	}

	void setLow(String low) { this.lowValue = low; }
	void setHigh(String high) { this.highValue = high; }
	
	/**
	 * Reasonable extension to toString() method.
	 */
	public String toString () {
		return super.toString() + "<" + lowValue + ":" + highValue + ">";
	}

	/**
	 * Return default constructor for this node type.
	 * 
	 * @return default constructor for this node type.
	 */
	@SuppressWarnings("rawtypes")
	public static IConstructor<?> getConstructor() {
		if (constructor == null) {
			constructor = new IConstructor() {

				public SegmentTreeNode<?> construct(int left, int right) {
					return new SpecialSegmentTreeNode (left, right);
				}
			};
		}
		
		return constructor;
	}
	
}
