package algs.model.network;

/**
 * Given a bipartite graph of provider nodes P, requirer nodes R, and edge costs c(i,j)
 * for assigning a provider pi to a requirer ri. Goal is to maximize the number of 
 * job assignments while minimizing the overall costs.
 *  
 * The resulting Flow Network has m + n + 2 nodes. Note that the shipping 
 * "cost" from the virtual new source and new target are zero. All edges store the shipping 
 * cost, in addition to the flow and capacity constraints. 
 * <p>
 * If a particular assignment is not possible then the cost should be Integer.MAX_VALUE.
 */
public class Assignment extends Transportation {

	public Assignment(int[][] costs) {
		super(craftSuppliers(costs), craftDemanders(costs), costs);
	}

	/**
	 * Figure out the demanders from the costs matrix and assign 1 for each of them.
	 * 
	 * @param costs
	 */
	private static int[] craftDemanders(int[][] costs) {
		int len = costs[0].length;
		int demanders[] = new int[len];
		
		for (int i = 0; i < costs.length; i++) {
			if (costs[i].length != len) {
				throw new IllegalArgumentException ("Ragged array invalid for Assignment.");
			}
			
			demanders[i] = 1;
		}
		
		return demanders;
	}

	/**
	 * Figure out the suppliers from the costs matrix and assign 1 for each of them.
	 * 
	 * @param costs
	 */
	private static int[] craftSuppliers(int[][] costs) {
		int len = costs.length;
		int suppliers[] = new int[len];
		
		for (int i = 0; i < len; i++) {
			suppliers[i] = 1;
		}
		
		return suppliers;
	}

}
