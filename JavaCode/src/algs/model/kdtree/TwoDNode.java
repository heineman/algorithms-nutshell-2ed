package algs.model.kdtree;

import algs.model.IPoint;
import algs.model.IRectangle;
import algs.model.twod.TwoDRectangle;

/**
 * Represents the base class of a node in the TwoD tree.
 * <p>
 * This class is intended as a simpler, optimized implementation of {@link DimensionalNode}
 * for two dimensional KD trees. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class TwoDNode {
	
	/** Coordinate. */
	public final double coord;
	
	/** Store the point. */
	public final IPoint point;
	
	/** Store region represented by node. */
	TwoDRectangle region;
	
	/** Node below this one (or null if it doesn't exist). */
	TwoDNode below;
	
	/** Node above this one (or null if it doesn't exist). */
	TwoDNode above;
	
	/**
	 * Stores the coordinate value to be used for dividing the plane either vertically
	 * or horizontally
	 * <p>
	 * Note the initial region associated with this {@link TwoDNode} object is unbounded
	 * and will only be set properly when it is added into the tree.
	 * 
	 * @param coord   coordinate value to be used for dividing plane
	 * @param point   the IPoint object from which coordinate is derived.
	 */
	public TwoDNode (double coord, IPoint point) {
		this.coord = coord;
		this.point = point;
		
		this.region = new TwoDRectangle (Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	/** 
	 * Return node "Below" this one. 
 	 * @return child node below this one 
	 */
	public TwoDNode getBelow() {
		return below;
	}
	
	/** 
	 * Return node "Above" this one. 
	 * @return child node above this one 
	 */
	public TwoDNode getAbove() {
		return above;
	}
	
	/** 
	 * Return region associated with this node. 
	 * @return region associated with node. 
	 */
	public IRectangle getRegion() {
		return region;
	}
		
	/** 
	 * Count number of nodes in the tree. Helper method.
	 * @return count of nodes in tree. 
	 */
	int count () {
		int ct = 0;
		if (below != null) { ct += below.count(); }
		ct ++;
		if (above != null) { ct += above.count(); }
		return ct;
	}
	
	/** 
	 * Set the node "Below" this one.
	 * <p>
	 * Let subclass deal with updating region.
	 * 
	 * @param node   new node to be properly set. 
	 */
	public void setBelow(TwoDNode node) {
		if (node == null) { 
			this.below = null; 
			return; 
		}
		
		if (node.isVertical() == isVertical()) {
			throw new IllegalArgumentException ("Can only set as children nodes whose isVertical() is different.");
		}
		
		this.below = node;
		split(this.below, false);
	}
	
	/** 
	 * Set node "Above" this one.
	 * <p>
	 * Let subclass deal with updating region.
	 * 
	 * @param node   new node to be properly set.
	 */
	public void setAbove(TwoDNode node) {
		if (node == null) { 
			this.above = null; 
			return; 
		}
		
		if (node.isVertical() == isVertical()) {
			throw new IllegalArgumentException ("Can only set as children nodes whose isVertical() is different.");
		}
		
		this.above = node;
		split(this.above, true);
	}
	
	/**
	 * Determines whether node splits plane vertically
	 * 
	 * @return  true if this node represents a VerticalNode in the TwoDTree; false otherwise.
	 */
	public abstract boolean isVertical();
	
	/**
	 * Returns whether the point is below the line represented by this node.
	 * <p>
	 * For vertical nodes, below is clear. For horizontal nodes, a true value for
	 * below is interpreted as being left.
	 *
	 * @param point    query point
	 * @return   true if point is "below" us, based upon our direction
	 */
	public abstract boolean isBelow (IPoint point);
	
	/**
	 * Manipulates child node's region accordingly, based on our own.
	 * <p>
	 * For {@link VerticalNode}, below is clear. For {@link HorizontalNode}, a true value 
	 * for below is interpreted as being left.
	 * 
	 * @param child           child node to be affected by split
	 * @param above           Determines whether to return left- or bottom- side
	 */
	protected abstract void split (TwoDNode child, boolean above);
	
	/**
	 * In sub-tree rooted at node, see if one of its descendants is closer to
	 * target than min[0]; if so return that IPoint and update min[0] accordingly.
	 *
	 *
	 * @param target      the target in raw optimized form
	 * @param min         minimum distance found so far
	 * @return            existing point in tree that is closest; if null, then
	 *                    no point in the tree is closer than min[0] from the given
	 *                    IPoint; if non-null, then min[0] is updated to reflect this
	 *                    closer distance.
	 */
	IPoint nearest (IPoint target, double min[]) {
	    // Update minimum if we are closer.
		IPoint result = null;
		
		double d = TwoDTree.distance(target, point);
		if (d >= 0 && d < min[0]) {
			min[0] = d;
			result = point;
		}
		
		// determine if we must dive into the subtrees by computing direct 
		// perpendicular distance to the axis along which node separates
		// the plane. If d is smaller than the current smallest distance, 
		// we could "bleed" over the plane so we must check both.
		double dp = perpendicularDistance(target);
		IPoint newResult = null;

		if (dp < min[0]) {
			// must dive into both. Return closest one.
			if (above != null) {
				newResult = above.nearest (target, min); 
				if (newResult != null) { result = newResult; }
			}
			
			if (below != null) {
				newResult = below.nearest(target, min);
				if (newResult != null) { result = newResult; }
			}
		} else {
			// only need to go in one! Determine which one now.
			if ((isVertical() && target.getX() < coord) ||
			    (!isVertical() && target.getY() < coord)) {
				if (below != null) {
					newResult = below.nearest (target, min); 
				}
			} else {
				if (above != null) {
					newResult = above.nearest (target, min); 
				}
			}
			
			// Use smaller result, if found.
			if (newResult != null) { return newResult; }
		}
		return result;
	}

	/** 
	 * Compute perpendicular distance to given target point based upon
	 * whether we are a {@link VerticalNode} or {@link HorizontalNode}.
	 * <p>
	 * This logic is delegated to the appropriate subclass. 
	 * 
	 * @param target    point to which distance is computed along perpendicular axis.
	 */
	abstract double perpendicularDistance(IPoint target);
	 
	/**
	 * This method constructs the node of the appropriate class based upon the vertical property of
	 * this node.
	 * <p>
	 * In short, this acts as a factory for the nodes in the next level of the tree.
	 * 
	 * @param value  point to be inserted.
	 * @return       appropriate {@link TwoDNode} subclass (either {@link VerticalNode} or
	 *               {@link HorizontalNode})
	 */
	public abstract TwoDNode construct(IPoint value);
	
	/**
	 * Locate all points within the TwoDTree that fall within the given rectangle.
	 * 
	 * @param space     non-null space within which search occurs.
	 * @param results   non-null TwoDSearchResults object into which located points are inserted.
	 * @exception       NullPointerException if space is null or results is null
 	 */
	public void range (IRectangle space, TwoDSearchResults results) {
		
		// perhaps our respective region is wholly contained within R, then all points in 
		// our entire subtree are to be visited.
		if (space.contains(region)) {
			results.add(this);
			return;
		}
		
		// OK. Is our point, at least contained?
		if (space.intersects (point)) {
			results.add(point);
		}
		
		// recursively progress along both ancestral trees, if demanded. Note that
		// the cost in manipulating space to be "cropped" to the proper structure
		// is excessive and leaving it alone has no bearing on the computation.
		if (inBelowRange(space)) {
			if (below != null) { below.range(space, results); }
		}
		if (inAboveRange(space)) {
			if (above != null) { above.range(space, results); }
		}
	}
	
	/**
	 * Locate all points within the TwoDTree that fall within the given rectangle and use
	 * given visitor as the computation to perform on that node.
	 * 
	 * @param space     non-null space within which search is to be conducted.
	 * @param visitor   visitor to perform computation on the node
	 * @exception       NullPointerException if value is null or visitor is null.
 	 */
	public void range (IRectangle space, IVisitTwoDNode visitor) {

		// perhaps our respective region is wholly contained within R, then all points in 
		// our entire subtree are to be visited.
		if (space.contains(region)) {
			this.drain(visitor);
			return;
		}
		
		// OK. Is our point, at least contained?
		if (space.intersects (point)) {
			visitor.visit(this);
		}
		
		// recursively progress along both ancestral trees, if demanded. Note that
		// the cost in manipulating space to be "cropped" to the proper structure
		// is excessive and leaving it alone has no bearing on the computation.
		if (inBelowRange(space)) {
			if (below != null) { below.range(space, visitor); }
		}
		if (inAboveRange(space)) {
			if (above != null) { above.range(space, visitor); }
		}
	}
	
	/** 
	 * Helper method for search algorithm, implemented in the Horizontal and 
	 * Vertical subclasses.
	 * 
	 * @param    r    query rectangle
	 */
	protected abstract boolean inBelowRange(IRectangle r);
	
	/** 
	 * Helper method for search algorithm, implemented in the Horizontal and 
	 * Vertical subclasses.
	 * 
	 * @param    r    query rectangle
	 */
	protected abstract boolean inAboveRange(IRectangle r);
	
	/** 
	 * Called once the node has its region properly set, and it must propagate
	 * to children (if they exist) in below and above.
	 */ 
	protected void specialUpdateRectangle() {
		if (below != null) {
			split(below, false);
			below.specialUpdateRectangle();
		}
		if (above != null) {
			split(above, true);
			above.specialUpdateRectangle();
		}	
	}
	
	/** Helper method to visit all descendant nodes in the tree rooted at given node. */
	private void drain(IVisitTwoDNode visitor) {
		if (below != null) { below.drain (visitor); }
		visitor.drain(this);
		if (above != null) { above.drain (visitor); }
	}
	
	/** Reasonable toString method. */
	public String toString () {
		String pre = "H";
		if (isVertical()) { pre="V"; }
		return pre + ":<" + point + " region:" + region + ">";
	}
}
