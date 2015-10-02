package algs.model.network;

/**
 * Given a network having sources S, demanders D, edge capacities c(i,j), edge costs 
 * costs[i][j] with m=|S| suppliers and n=|D| demanders, construct a flow, if one exists, 
 * which minimizes costs. 
 * <p>
 * The resulting Flow Network has m + n + 2 nodes. Note that the shipping 
 * "cost" from the virtual new source and new target are zero. All edges store the shipping 
 * cost, in addition to the flow and capacity constraints. 
 * <p>
 * It is assumed that the dimensions of costs matches the respective dimensions
 * of the sup and dem vectors; if not, an exception is thrown.
 * <p>
 * If a particular shipment is not possible (or simply not selected) then the cost
 * should be Integer.MAX_VALUE.
 */
public class Transportation extends Transshipment {

	/**
	 * Problem reduction is immediate since there are no intermediate warehouses.
	 * 
	 * @param sup      supplier nodes
	 * @param dem      demand nodes
	 * @param costs    costs to ship from supplier node i to demand node j
	 */
	public Transportation(int[] sup, int[] dem, int[][] costs) {
		super(sup, dem, new int[0][0], new int[0], new int[0], new int[0][0], costs);
	}
}
