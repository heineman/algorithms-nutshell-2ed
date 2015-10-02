package algs.model.kdtree;

import algs.model.FloatingPoint;
import algs.model.IPoint;
import algs.model.IRectangle;
import algs.model.twod.TwoDRectangle;

/**
 * Represents a node in the 2D-tree that partitions the space by means of a vertical
 * line at the given x-coordinate.
 * <p>
 * This class is intended as a simpler, optimized implementation of {@link DimensionalNode}
 * for two dimensional KD trees. 
 * <p>
 * Ancestors via the left son are those points which are to the left of the point 
 * represented by this node. Ancestors via the right son are those points which are to 
 * the right of the point represented by this node.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class VerticalNode extends TwoDNode {

	/**
	 * X-coordinate is taken from the IPoint.
	 * 
	 * @param point   Point being stored.
	 */
	public VerticalNode(IPoint point) {
		super (point.getX(), point);
	}

	/**
	 * @see TwoDNode#isVertical()
	 */
	@Override
	public boolean isVertical() {
		return true;
	}

	/**
	 * This method constructs the node of the appropriate class based upon the 
	 * vertical property of this node.
	 * <p>
	 * In short, this acts as a factory for the nodes in the next level of the tree.
	 * 
	 * @param value {@link IPoint} object to be stored with the constructed node
	 * @return    Horizontal node to be the next level in the TwoD tree.
	 */
	public TwoDNode construct(IPoint value) {
		return new HorizontalNode (value);
	}

	/**
	 * @see TwoDNode#split(TwoDNode, boolean)
	 */
	@Override
	protected void split (TwoDNode child, boolean above) {
		// we "close off" the 'above/right' area, since node is below. 
		child.region = new TwoDRectangle (region);
		if (above) {
			child.region.setLeft(coord);
		} else {
			child.region.setRight(coord);
		}
	}
	
	/**
	 * @see TwoDNode#inBelowRange(IRectangle)
	 */
	@Override
	protected boolean inBelowRange(IRectangle r) {
		return FloatingPoint.lesser(r.getLeft(), coord);
	}
	
	/**
	 * @see TwoDNode#inAboveRange(IRectangle)
	 */
	@Override
	protected boolean inAboveRange(IRectangle r) {
		return FloatingPoint.greater(r.getRight(), coord);
	}
	
	/**
	 * @see TwoDNode#isBelow(IPoint)
	 */
	@Override
	public boolean isBelow(IPoint point) {
		return FloatingPoint.lesser (point.getX(), coord); 
	}

	@Override
	double perpendicularDistance(IPoint target) {
		return Math.abs(coord - target.getX());
	}
}
