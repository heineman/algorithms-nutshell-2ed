package algs.model.kdtree;

import algs.model.FloatingPoint;
import algs.model.IPoint;
import algs.model.IRectangle;
import algs.model.twod.TwoDRectangle;

/**
 * Represents a node in the KD-tree that partitions the space by means of a vertical
 * line at the given y-coordinate.
 * <p>
 * This class is intended as a simpler, optimized implementation of {@link DimensionalNode}
 * for two dimensional KD trees. 
 * <p>
 * Ancestors via the left son are those points which are above the point represented by 
 * this node. Ancestors via the right son are those points which are below the point 
 * represented by this node.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class HorizontalNode extends TwoDNode {
	
	/**
	 * Y-coordinate is taken from the point.
	 * 
	 * @param point     Point being stored.
	 */
	public HorizontalNode(IPoint point) {
		super(point.getY(), point);
	}
	
	/**
	 * @see TwoDNode#isVertical()
	 */
	@Override
	public boolean isVertical() {
		return false;
	}
	
	/**
	 * This method constructs the node of the appropriate class based upon the vertical property of
	 * this node.
	 * <p>
	 * In short, this acts as a factory for the nodes in the next level of the tree.
	 * 
	 * @param value  point to be added to the TwoDTree.
	 * @return       a VerticalNode, which is the next in line in the TwoDTree.
	 */
	public TwoDNode construct(IPoint value) {
		return new VerticalNode (value);
	}
	
	/**
	 * @see TwoDNode#split(TwoDNode, boolean)
	 */
	@Override
	protected void split (TwoDNode child, boolean above) {
		// we "close off" the 'above/right' area, since node is below. 
		child.region = new TwoDRectangle (region);
		if (above) {
			child.region.setBottom(coord);
		} else {
			child.region.setTop(coord);
		}
	}

	/**
	 * @see TwoDNode#inBelowRange(IRectangle)
	 */
	@Override
	protected boolean inBelowRange(IRectangle r) {
		return FloatingPoint.lesser(r.getBottom(), coord);
	}
	
	/**
	 * @see TwoDNode#inAboveRange(IRectangle)
	 */
	@Override
	protected boolean inAboveRange(IRectangle r) {
		return FloatingPoint.greater(r.getTop(), coord);
	}
	
	/**
	 * @see TwoDNode#isBelow(IPoint)
	 */
	@Override
	public boolean isBelow(IPoint point) {
		return FloatingPoint.lesser (point.getY(), coord);
	}

	@Override
	double perpendicularDistance(IPoint target) {
		return Math.abs(coord - target.getY());
	}
}
