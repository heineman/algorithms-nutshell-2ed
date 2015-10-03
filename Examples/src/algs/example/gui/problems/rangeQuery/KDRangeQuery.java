package algs.example.gui.problems.rangeQuery;

import algs.example.gui.model.IRetrieveKDTree;
import algs.example.gui.problems.rangeQuery.model.Model;
import algs.example.gui.problems.rangeQuery.model.SelectableMultiPoint;
import algs.model.IRectangle;
import algs.model.kdtree.CounterKDTree;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hypercube;

/**
 * Process the range query over the entities which are known to be of the
 * type SelectableMultiPoint.
 * <p>
 * Uses the provided KD tree to accomplish the task.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class KDRangeQuery implements IRangeQuery<SelectableMultiPoint> {

	/** Model with information. */
	Model<SelectableMultiPoint> model;
	
	/** KD tree. */
	KDTree tree;
	
	// time storage
	private long now;
	private long then;
	
	/** 
	 * Construct with the model to be able to perform query.
	 * 
	 * @param model   contains the elements over which the query operates.
	 * @exception     IllegalArgumentException if the model object does not
	 *                 support the IRetrieveKDTree interface.
	 */
	KDRangeQuery(Model<SelectableMultiPoint> model) throws IllegalArgumentException {
		this.model = model;
		
		if (model instanceof IRetrieveKDTree) {
			this.tree = ((IRetrieveKDTree)model).getTree(); 
		}
	}
	
	public int compute(IRectangle query) {
		System.gc();
		then = System.currentTimeMillis();
		// convert into a hypercube for processing.
		Hypercube hc = new Hypercube(new double[]{query.getLeft(), query.getBottom()},
									 new double[]{query.getRight(), query.getTop()});
		
		model.deselectAll();
		CounterKDTree ct = new CounterKDTree() {

			/** Mark as being drained. */
			public void drain(DimensionalNode node) {
				super.drain(node);
				((SelectableMultiPoint)node.point).select(1);
			}

			/** Mark as routine selection. */
			public void visit(DimensionalNode node) {
				super.visit(node);
				((SelectableMultiPoint)node.point).select(true);
			}
			
		};

		tree.range(hc, ct); 		
		now = System.currentTimeMillis();
		return ct.getCount();
	}

	public long time() {
		return now - then;
	}

}
