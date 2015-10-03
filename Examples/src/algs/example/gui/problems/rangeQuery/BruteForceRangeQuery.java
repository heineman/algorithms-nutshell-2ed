package algs.example.gui.problems.rangeQuery;

import algs.example.gui.problems.rangeQuery.model.Model;
import algs.example.gui.problems.rangeQuery.model.SelectableMultiPoint;
import algs.model.IMultiPoint;
import algs.model.IRectangle;
import algs.model.nd.Hypercube;

/**
 * Process the range query over the entities which are known to be of the
 * type SelectableMultiPoint.
 * <p>
 * This brute-force implementation applies the query rectangle to all points
 * stored by the model.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BruteForceRangeQuery implements IRangeQuery<SelectableMultiPoint> {

	/** Model with information. */
	Model<SelectableMultiPoint> model;
	
	// time storage
	private long now;
	private long then;
	
	/** 
	 * Construct with the model to be able to perform query.
	 * 
	 * @param model   contains the elements over which the query operates.
	 */
	public BruteForceRangeQuery(Model<SelectableMultiPoint> model) {
		this.model = model;
	}
	
	public int compute(IRectangle query) {
		System.gc();
		then = System.currentTimeMillis();
		int num = 0;

		// convert into a hypercube for processing.
		if (query != null) {
			// make sure the arguments are properly set up in this invocation. It seems
			// counter-intuitive but it conforms to the Hypercube interface.
			Hypercube hc = new Hypercube(new double[]{query.getLeft(), query.getBottom()},
										 new double[]{query.getRight(), query.getTop()});
			if (model.items() != null) {
				for (IMultiPoint imp : model.items()) {
					SelectableMultiPoint smp = (SelectableMultiPoint) imp;
					if (hc.intersects(smp)) {
						num++;
						smp.select(true);
					} else {
						smp.select(false);
					}
				}
			}
		}
		
		now = System.currentTimeMillis();
		return num;
	}

	public long time() {
		return now - then;
	}

}
