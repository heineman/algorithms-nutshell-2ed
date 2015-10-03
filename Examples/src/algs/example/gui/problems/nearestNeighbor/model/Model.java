package algs.example.gui.problems.nearestNeighbor.model;

import algs.example.gui.model.IRetrieveKDTree;
import algs.model.IMultiPoint;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hyperpoint;

/**
 * The set of IPoint objects and the KDtree constructed from these points.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Model extends algs.example.gui.model.Model<IMultiPoint> 
  implements IRetrieveKDTree {
	/** Knows about the KD tree to be shown. */
	KDTree tree;

	/** nearest point. */
	IMultiPoint nearest;
	
	/** Search point. */
	IMultiPoint target;
	
	/** Construct model. */
	public Model () {
		super();
		
		tree = new KDTree(2);
	}

	/** Access KDtree in model via its root node. */
	public KDTree getTree() {
		return tree;
	}

	/** 
	 * Return closest point in model.
	 * 
	 * @param tdp  target point  
	 */
	public void computeNearest(IMultiPoint tdp) {
		target = tdp;
		nearest = tree.nearest(tdp);
	}
	
	/** Return the computed nearest point in this model. */
	public IMultiPoint getNearest() {
		return nearest;
	}
	
	/** Return the target point in this model. */
	public IMultiPoint getTarget() {
		return target;
	}
	
	/** 
	 * Store copy of all items. And build tree.
	 */
	@Override
	public void setItems(IMultiPoint[] items) {
		tree.removeAll();
	
		this.items = new IMultiPoint[items.length];
		int idx = 0;
		for (IMultiPoint p: items) {
			this.items[idx++] = new Hyperpoint(p);
			tree.insert(p);
		}
	}

	/** Set the nearest point, as determined by algorithm. */
	public void setNearest(IMultiPoint p) {
		nearest = p;
		
		modelUpdated();
	}	
}
