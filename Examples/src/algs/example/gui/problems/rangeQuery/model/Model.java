package algs.example.gui.problems.rangeQuery.model;

import algs.example.gui.model.IActiveRectangle;
import algs.example.gui.model.IRetrieveKDTree;
import algs.example.gui.problems.rangeQuery.ISelectable;
import algs.model.IMultiPoint;
import algs.model.IRectangle;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;

/**
 * Contains the model relevant to the Range Query problem. We extend
 * the NearestNeighbor Model to be able to access the full KD tree.
 * 
 * @param <E>  The entity type of the underlying model.
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Model<E extends ISelectable> extends algs.example.gui.model.Model<SelectableMultiPoint> 
	implements IActiveRectangle, IRetrieveKDTree {

	/** Maintain KD tree for points. */
	protected KDTree tree;

	/** Query rectangle.  */
	protected IRectangle targetRect;

	/** Should tree be balanced. */
	protected boolean balanced = true;

	/** 
	 * Construct a KD tree (balanced or might not be) from the items set.
	 * 
	 * @param items   set of items that make up the input set.
	 */
	@Override
	public void setItems(SelectableMultiPoint[] items) {
		this.items = items;

		// now build balanced KD tree.
		if (balanced) {
			tree = KDFactory.generate(items);
		} else {
			// Code to create tree without balancing.
			tree = new KDTree(2);
			for (IMultiPoint imp: items) {
				tree.insert(imp);
			}
		}
	}	

	/** 
	 * Reset the selection status of all items.
	 */
	public void deselectAll() {
		if (items == null) {
			return;
		}
		for (IMultiPoint imp: items) {
			((ISelectable)imp).select(false);
		}
	}

	/** Set the target query rectangle. */
	public void setActiveRectangle(IRectangle rect) {
		targetRect = rect;

		//alert listeners
		modelUpdated();
	}

	/** Return the target query. */
	public IRectangle getActiveRectangle() {
		return targetRect;
	}

	/** Determine if the constructed KD tree is constructed by balanced algorithm. */
	public void setBalanced (boolean b) {
		this.balanced = b;
	}

	public KDTree getTree() {
		return tree;
	}

}
