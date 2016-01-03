package algs.model.kdtree;

import algs.model.IHypercube;
import algs.model.IMultiPoint;
import algs.model.nd.Hypercube;

/**
 * Represents a node in the KD-tree that partitions the space by means of a plane that
 * splits the hyperspace into an "above" and a "below" based upon orientation.
 * <p>
 * Ancestors via the above son are those points which are "above" of the point 
 * represented by this node. Ancestors via the below son are those points which
 * are "below" of the point represented by this node.
 * <p>
 * Each dimensional node stores a Region for which it is responsible. Note that only the
 * Root is boundless in all directions (i.e., [-INF, +INF, -INF, +INF]. Thereafter, child
 * nodes are able to maintain their regions. When a dimensional node is inserted into the
 * tree, its SPACE is properly updated based upon the parent, and dimension to which it
 * is being added.
 * <p>
 * Because the original MultiPoint may have lots of dimensions, we store the values
 * here for much faster access then simply accessing values through its methods.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DimensionalNode {
	
	public static int numDoubleRecursions = 0;  /* stats */
	public static int numRecursions = 0;        /* stats */

	/** Dimensional-coordinate. */
	public final IMultiPoint point;
	
	/** When processing point, use cached values. */
	private final double[] cached;

	/** Which dimension is being represented (1 &le; d &le; max). */
	public final int dimension;
	
	/** Maximum dimension. */
	public final int max;
	
	/** Coordinate in this dimension from the multipoint. */
	public final double coord;
	
	/** Region for which we are "responsible" */
	protected Hypercube region;
	
	/** Node below this one. */
	protected DimensionalNode below;
	
	/** Node above this one. */
	protected DimensionalNode above;
	
	/**
	 * Dimensional-coordinate is passed in as the value, together with its dimension.
	 * <p>
	 * By default, region being managed is boundless on all dimensions.
	 * 
	 * @param dimension     which dimension does this node represent
	 * @param pt            the multi-dimensional point
	 */
	public DimensionalNode(int dimension, IMultiPoint pt) {
		this.dimension = dimension;
		this.point = pt;
		this.max = pt.dimensionality();
		this.region = new Hypercube(max);
		
		cached = new double[max];
		for (int i = 1; i <= max; i++) {
			cached[i-1] = pt.getCoordinate(i);
			region.setLeft(i, Double.NEGATIVE_INFINITY);
			region.setRight(i, Double.POSITIVE_INFINITY);
		}
		
		this.coord = cached[dimension-1];
	}
	
	/** 
	 * Determine if node has a boundless region associated with it.
	 * <p>
	 * Will only be true for root node in KD-tree or newly instantiated nodes that have
	 * yet to be added to a KD tree.
	 * 
	 * @return true if node has boundless region associated with it.
	 */
	public boolean isBoundless() {
		for (int i = 1; i <= max; i++) {
			if (!Double.isInfinite(region.getLeft(i))) { return false; }
			if (!Double.isInfinite(region.getRight(i))) { return false; }
		}
		
		return true;
	}

	/** 
	 * Determines if node is a leaf node (i.e., has no children). 
	 * @return true if node is a leaf node; false otherwise. 
	 */
	public boolean isLeaf() {
		if (below != null) { return false; }
		if (above != null) { return false; }
		return true;
	}
	
	/** 
	 * Return node "Below" this one. 
	 * @return node below this one in kd-tree 
	 */
	public DimensionalNode getBelow() {
		return below;
	}
	
	/** 
	 * Return region being managed. 
	 * @return region associated with node.
	 */
	public IHypercube region () {
		return region;
	}
	
	/** 
	 * Return node "Above" this one. 
	 * @return node above this one in kd-tree 
	 */
	public DimensionalNode getAbove() {
		return above;
	}
	
	/** 
	 * Set the node "Below" this one.
	 * <p>
	 * If setting to null, there is no knowledge of dimension coordinates.
	 *
	 * @param node    Next node to be inserted into the tree.
	 */
	public void setBelow(DimensionalNode node) {
		if (node == null) {
			this.below = null;
			return;
		}
		
		// Node belongs here, and we update node's region accordingly.
		if ((this.dimension == max && node.dimension == 1) ||
			(this.dimension +1 == node.dimension)) {
			this.below = node;
			
			// we "close off" the 'above/right' area, since node is below. 
			node.region = new Hypercube (region);
			node.region.setRight(dimension, coord);
			return;
		}
		
		throw new IllegalArgumentException ("Can only set as children nodes whose dimensionality is one greater.");
	}
	
	
	/** 
	 * Set the node "Above" this one.
	 * <p> 
	 * If setting to null, there is no knowledge of dimension coordinates.
	 * 
	 * @param node    Next node to be inserted into the tree.
	 */
	public void setAbove(DimensionalNode node) {
		if (node == null) {
			this.above = null;
			return;
		}
		
		// Node belong here, and we update node's region accordingly.
		if ((this.dimension == max && node.dimension == 1) ||
			(this.dimension +1 == node.dimension)) {
			this.above = node;			
			
			// we "close off" the 'below/left' area, since node is above.
			node.region = new Hypercube (region);
			node.region.setLeft(dimension, coord);
			return;
		}
		
		throw new IllegalArgumentException ("Can only set as children nodes whose dimensionality is one greater.");
	}
	
	/**
	 * Returns whether the point is below the line represented by this node.
	 * <p>
	 * Calculation assumes a multi-dimensional point being compared within the
	 * given dimension 'dimension'
	 *
	 * @param pt    Point being compared
	 * @return      true if pt is "below" us in our dimension
	 */
	public boolean isBelow (IMultiPoint pt) {
		return pt.getCoordinate(dimension) < coord;
	}

	/**
	 * This method constructs the node of the appropriate class based upon the 
	 * dimensional property of this node.
	 * <p>
	 * In short, this acts as a factory for the nodes in the next level of the tree.
	 * 
	 * @param value    value to be inserted
	 * @return         appropriate node whose dimensionality is next in line.
	 */
	protected DimensionalNode construct(IMultiPoint value) {
		if (this.dimension == max) {
			return new DimensionalNode (1, value);
		} else {
			return new DimensionalNode (dimension+1, value);
		}
	}
	

	/**
	 * Given the target in optimized form, determine if node is closer than min.
	 * 
	 * @param   rawTarget    optimized double[] form of target
	 * @param   min          shortest distance so far.
	 * @return  -1 if not closer; otherwise returns a number [0, min) if closer.
	 */
	protected double shorter (double[] rawTarget, double min) {
		// we compare distance by subtracting squares from the current min. If we
		// ever fall below ZERO then we are too far, otherwise we return Sqrt.
		double minsq = min*min;
		double maxV = minsq;
		
		for (int i = 0; i < this.max; i++) {
			double d = rawTarget[i]-cached[i];
			if ((maxV -= d*d) < 0) {
				return -1;  // leave NOW! can't be shorter.
			}
		}
		return Math.sqrt(minsq-maxV);
	}
	
	/**
	 * In sub-tree rooted at node, see if one of its descendants is closer to
	 * rawTarget than min[0].
	 * <p>
	 * If no descendant improves on the min[] result then null is returned.
	 *
	 * @param min         minimum distance found so far
	 * @param rawTarget   the target in raw optimized form
	 * @return            existing point in tree that is closest.
	 */
	protected IMultiPoint nearest (double[] rawTarget, double min[]) {
	    // Update minimum if we are closer.
		IMultiPoint result = null;
		
		// double d = target.distance(node.point); O(d) computation
		// if shorter, update minimum
		double d = shorter(rawTarget, min[0]);
		if (d >= 0 && d < min[0]) {
			min[0] = d;
			result = point;
		}
		
		// determine if we must dive into the subtrees by computing direct 
		// perpendicular distance to the axis along which node separates
		// the plane. If d is smaller than the current smallest distance, 
		// we could "bleed" over the plane so we must check both.
		double dp = Math.abs(coord - rawTarget[dimension-1]);
		IMultiPoint newResult = null;

		int numDblRec = 0; /* stats */
		if (dp < min[0]) {
			// must dive into both. Return closest one.
			if (above != null) {
				numDblRec++; /* stats */
				newResult = above.nearest (rawTarget, min); 
				if (newResult != null) { result = newResult; }
			}
			
			if (below != null) {
				numDblRec++; /* stats */
				newResult = below.nearest(rawTarget, min);
				if (newResult != null) { result = newResult; }
			}
			if (numDblRec == 2) {          /* stats */
				numDoubleRecursions++;     /* stats */
			} else if (numDblRec == 1) {   /* stats */
				numRecursions++;           /* stats */
			}
		} else {
			// only need to go in one! Determine which one now.
			numRecursions++;               /* stats */
			if (rawTarget[dimension-1] < coord) {
				if (below != null) {
					newResult = below.nearest (rawTarget, min); 
				}
			} else {
				if (above != null) {
					newResult = above.nearest (rawTarget, min); 
				}
			}
			
			// Use smaller result, if found.
			if (newResult != null) { return newResult; }
		}
		return result;
	}
	
	/**
	 * Locate all points within the KDTree that fall within the given hypercube.
	 * <p>
	 * Speedup occurs when an entire range is found to exist within the target space, thus
	 * all points in the subtree rooted here can be drained and added.
	 * 
	 * @param space     non-null region within which search occurs.
	 * @param results   non-null KDSearchResults into which located points are inserted.
	 * @exception       NullPointerException if space is null or results is null
 	 */
	public void range (IHypercube space, KDSearchResults results) {
		// Wholly contained? Take all descendant points		
		if (space.contains (region)) {
			results.add(this);
			return;
		}
	
		// Is our point at least contained?
		if (space.intersects (cached)) {
			results.add(point);
		}

		// Recursively progress along both ancestral trees, if demanded. 
		// The cost in manipulating space to be "cropped" to the proper 
		// structure is excessive, so leave alone and is still correct.
		if (space.getLeft(dimension) < coord) {
			if (below != null) { below.range (space, results); }
		}
		if (coord < space.getRight(dimension)) {
			if (above != null) { above.range (space, results); }
		}
	}

	/**
	 * Locate all points within the KDTree that fall within the given hyperspace and use
	 * given visitor as the computation to perform on that node.
	 * <p>
	 * Speedup occurs when an entire range is found to exist within the target space, thus
	 * all points in the subtree rooted here can be added.
     * <p>
     * If we are a leaf, then report the point if it is contained within space. 
     * Perhaps our region is wholly contained within space. If so, then we can safely
     * add self and all points from our left and right subtrees. Otherwise, we have 
     * to do some computation to see whether we go down left and/or right subtrees.
     *
	 * @param space     non-null space inside which search occurs.
	 * @param visitor   visitor to perform computation on the node
	 * @exception       NullPointerException if space is null or visitor is null.
 	 */
	public void range (IHypercube space, IVisitKDNode visitor) {
		// Wholly contained? Take all descendant points
		if (space.contains (region)) {
			this.drain(visitor);
			return;
		}
		
		// OK. Is our point, at least contained?
		if (space.intersects (cached)) {
			visitor.visit(this);
		}
		
		// recursively progress along both ancestral trees, if demanded. Note that
		// the cost in manipulating space to be "cropped" to the proper structure
		// is excessive and leaving it alone has no bearing on the computation.
		if (space.getLeft(dimension) < coord) {
			if (below != null) { below.range(space, visitor); }
		}
		if (coord < space.getRight(dimension)) {
			if (above != null) { above.range(space, visitor); }
		}
	}		
	
	/** Helper method to visit all descendant nodes in the tree rooted at given node. */
	private void drain(IVisitKDNode visitor) {
		if (below != null) { below.drain (visitor); }
		visitor.drain(this);
		if (above != null) { above.drain (visitor); }
	}

	/** Count number of nodes in the tree. Helper method. */
	int count () {
		int ct = 0;
		if (below != null) { ct += below.count(); }
		ct ++;
		if (above != null) { ct += above.count(); }
		return ct;
	}
	
	/** Compute the max height of tree. Helper method. */
	int height () {
		int maxHeight = 1;
		if (below != null) { int h = 1+below.height(); if (h > maxHeight) { maxHeight = h;}}
		if (above != null) { int h = 1+above.height(); if (h > maxHeight) { maxHeight = h;}}
		return maxHeight;
	}
	
	/** Reasonable toString method. */
	public String toString () {
		return dimension + ":<" + point + ">";
	}

	/**
	 * Propagate region on through this node into children.
	 * <p>
	 * This method is used only by the KDFactory which needs to maintain the regions properly because 
	 * its algorithm constructed the KDTree from the bottom up. It is marked as package private to 
	 * ensure the integrity of the tree. 
	 * <p>
	 * 
	 * @param region   designated region for this node.
	 */
	void propagate(Hypercube region) {
		this.region = region;
		
		// we "close off" the 'above/right' area, since node is below.
		if (below != null) {
			Hypercube child = new Hypercube (region);
			child.setRight(dimension, coord);
			below.propagate(child);
		}
		
		// we "close off" the 'below/left' area, since node is above.
		if (above != null) {
			Hypercube child = new Hypercube (region);
			child.setLeft(dimension, coord);
			above.propagate(child);
		}
	}
}
