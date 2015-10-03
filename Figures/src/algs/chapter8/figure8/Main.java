package algs.chapter8.figure8;
import algs.model.network.ShortestPathArray;
import algs.model.network.FordFulkerson;
import algs.model.network.Transshipment;

/**
 * http://people.brunel.ac.uk/~mastjjb/jeb/or/netflow.html
 * 
 * Test of the assignment problem.
 */
public class Main {

	
	/** http://people.brunel.ac.uk/~mastjjb/jeb/or/netflow.html */
	public static void main (String []args) {

		// suppliers and demanders
		int sup[] = new int[]   {60,70,80};
		int dem[] = new int[]   {40, 35, 25, 20, 60, 30};
		
		// costs at warehouses (limit on throughput can be MAX_VALUE if no limits.).
		int w = 1;
		int warehouse_costs[] = new int[]{7};
		int warehouse_limits[] = new int[]{Integer.MAX_VALUE};
		
		// costs to ship from warehouse(s) to demanders 
		int dem_transship[][] = new int[][]{{7,9,11,8,6,9}};
				
		//                Transportation cost (in pounds)
		//                per ton to customer
		//                 1   2   3   4   5   6
		//  From factory A 1.5 1.8 3.1 4.2 2.5 3.0
		//               B 2.2 4.6 3.5 2.4 1.8 4.0
		//               C 3.6 4.8 1.6 4.4 2.8 2.0
		//
		// normalize to integers.
		int tc[][] = new int[][]{{15, 18, 31, 42, 25, 30},
				{22,46,35,24,18,40},
				{36,48,16,44,28,20}};
		
		// Variable production cost per ton is 11.3, 11.0 and 10.8 (pounds)
		// factories A, B and C
		int vpc [] = new int[] {113, 110, 108};
		
		// arcs from the sources (factories) to warehouses of capacity equal to the factory
		// production capacity and cost equal to the combined production and transportation 
		// (factory to depot) cost, i.e.
		// costs to ship from suppliers to warehouse(s)
		int warehouse_shipping[][] = new int[][]{{1},
				{3},
				{7}};
		
		//        Production and transportation 
		//        cost (pounds) per ton to customer
		//               1    2    3    4    5    6
		//From factory A 12.8 13.1 14.4 15.5 13.8 14.3
		//             B 13.2 15.6 14.5 13.4 12.8 15.0
		//             C 14.4 15.6 12.4 15.2 13.6 12.8		
		int d[][] = new int[sup.length][dem.length];
		for (int i = 0; i < sup.length; i++) {
			for (int j = 0 ; j < dem.length; j++) {
				d[i][j] = vpc[i] + tc[i][j];
			}
		}
		
		//        Production and transportation 
		//        cost (pounds) per ton to customer through warehouse
		//		arc A,D1 capacity 60 cost 11.3 + 0.1 = 11.4
		//		arc B,D1 capacity 70 cost 11.0 + 0.3 = 11.3
		//		arc C,D1 capacity 80 cost 10.8 + 0.7 = 11.5
		int sup_transship[][] = new int[sup.length][w];
		for (int i = 0 ; i < sup.length; i++) {
			for (int j = 0; j < w; j++) {
				sup_transship[i][j] = vpc[i] + warehouse_shipping[i][j];
			}
		}

		Transshipment ts = new Transshipment (sup, dem, sup_transship, 
				warehouse_costs, warehouse_limits, dem_transship, d);
		FordFulkerson ffa = new FordFulkerson(ts, new ShortestPathArray(ts));
		ffa.compute();
		
		System.out.println(ts);	
		
		System.err.println(ts.output());
	}
}
