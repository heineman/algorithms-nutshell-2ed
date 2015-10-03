package algs.example.model.network.mincostmaxflow;


import algs.model.network.Assignment;
import algs.model.network.ShortestPathArray;
import algs.model.network.FordFulkerson;

/**
 * Example of applying FordFulkerson.
 * 
 * http://people.brunel.ac.uk/~mastjjb/jeb/or/netflow.html
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TestMaxFlowMinCost {
	
	/**
	 * http://www.me.utexas.edu/~jensen/models/network/net9.html
	 */
	public static void main(String[] args) {
		
		int d[][] = new int[][]{{Integer.MAX_VALUE,8,6,12,1},
			{15,12,7,Integer.MAX_VALUE,10},
			{10,Integer.MAX_VALUE,5,14,Integer.MAX_VALUE},
			{12,Integer.MAX_VALUE,12,16,15},
			{18,17,14,Integer.MAX_VALUE,13}};
		
		Assignment as = new Assignment (d);
		FordFulkerson ff = new FordFulkerson (as, new ShortestPathArray(as));
		ff.compute();
		
		assert (118 == as.getCost());
		assert (5 == as.getFlow());
	}
	
}

