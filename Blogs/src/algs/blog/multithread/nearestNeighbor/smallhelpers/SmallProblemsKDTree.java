package algs.blog.multithread.nearestNeighbor.smallhelpers;

import algs.model.IMultiPoint;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.KDTree;

/**
 * Multi-threaded nearest implementation.
 * <p>
 * Determining the size of the small problems is based on comparing the 
 * individual volume of a node's hypercube against the original space in 
 * which it exists. The provided solution does not allow multiple 
 * {@link SmallProblemsKDTree} objects to process simultaneous searches because each
 * may have different threshold values. This limitation was acceptable for this
 * example to make the code easier to understand. 
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class SmallProblemsKDTree extends KDTree {

	/** Can be externally set to alter basic volume parameters. */
	public static double fract = 0.00625;
	
	/**
	 * Extend parent's constructor.
	 * 
	 * @param md
	 */
	public SmallProblemsKDTree(int md) {
		super(md);
	}

	/**
	 * Find the nearest point in the KDtree to the given point while using
	 * multiple threads as helpers.
	 * <p>
	 * Only returns null if the tree was initially empty. Otherwise, must
	 * return some point that belongs to the tree.
	 * <p>
	 * If tree is empty or if the target is <code>null</code> then
	 * <code>null</code> is returned.
	 * 
	 * @param   target    the target of the search. 
	 */
	public IMultiPoint nearest (IMultiPoint target) {
		SmallProblemsKDNode top = ((SmallProblemsKDNode)getRoot());
		if (top == null || target == null) return null;
	
		// find parent node to which target would have been inserted. This is our
	    // best shot at locating closest point; compute best distance guess so far
		DimensionalNode parent = parent(target);
		IMultiPoint result = parent.point;
		double smallest = target.distance(result);
		
		// now start back at the root, and check all rectangles that potentially
		// overlap this smallest distance. If better one is found, return it.
		double best[] = new double[] { smallest };  // computed best distance.
		
		// set minimum threshold value, based on 1/4 of each dimension. Thus
		// for two dimensions, use 1/16th while for three dimensions, use 1/64th
		// In this example, the points are drawn from unit square. Should this 
		// change, your computation for the threshold value will also need to 
		// change.
		double vol = top.volume();
		SmallProblemsKDNode.setThreshold(vol * Math.pow(fract, maxDimension));
		
		double raw[] = target.raw();
		IMultiPoint betterOne = top.nearest (raw, best);
		
		// wait until all helper threads have processed.
		while (SmallProblemsKDNode.helpersWorking > 0) {
			
		}
		
		if (betterOne != null) { return betterOne; }
		return result;
	}
	
}
