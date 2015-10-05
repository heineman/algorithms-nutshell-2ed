package algs.model.tests.network;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.junit.Test;

import algs.model.network.Assignment;
import algs.model.network.DisjointPairs;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetworkArray;
import algs.model.network.BipartiteMatchingMinCost;
import algs.model.network.ShortestPathArray;
import algs.model.network.FordFulkerson;
import algs.model.network.Transportation;
import algs.model.network.Transshipment;

import junit.framework.TestCase;

/**
 * http://people.brunel.ac.uk/~mastjjb/jeb/or/netflow.html
 * 
 * Test of the assignment problem.
 */
public class MaxFlowMinCostTest extends TestCase {

	/** Validate sample backflow. */
	@Test
	public void testBackflow() {
		ArrayList<EdgeInfo> edges = new ArrayList<EdgeInfo>();

		edges = new ArrayList<EdgeInfo>();
		edges.add(new EdgeInfo(0, 1,   3));
		edges.add(new EdgeInfo(0, 2,   2));

		edges.add(new EdgeInfo(1, 4,   2));
		edges.add(new EdgeInfo(2, 3,   2));
		edges.add(new EdgeInfo(2, 4,   3));
		edges.add(new EdgeInfo(1, 3,   2));

		edges.add(new EdgeInfo(3, 5,   3));		
		edges.add(new EdgeInfo(4, 5,   2));

		FlowNetworkArray network = new FlowNetworkArray (6, 0, 5, edges.iterator());
		FordFulkerson ff = new FordFulkerson (network, new ShortestPathArray(network));
		ff.compute();
		assertEquals (0, network.getCost());
		assertEquals (5, network.getFlow());		
	}

	/** http://people.brunel.ac.uk/~mastjjb/jeb/or/netflow.html */
	@Test
	public void testTransportation() {

		// suppliers and demanders
		int sup[] = new int[]   {60,70,80};
		int dem[] = new int[]   {40, 35, 25, 20, 60, 30};


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

		// Variable production cost per ton is 11.3, 11.0 and 10.8 (in pounds) at
		// factories A, B and C
		int vpc [] = new int[] {113, 110, 108};



		//        Production and transportation 
		//        cost (in pounds) per ton to customer
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

		Transportation ts = new Transportation (sup, dem, d);
		FordFulkerson ffa = new FordFulkerson(ts, new ShortestPathArray(ts));
		ffa.compute();
		assertEquals (27265, ts.getCost());
		assertEquals (210, ts.getFlow());
	}

	/**
	 * http://www.me.utexas.edu/~jensen/models/network/net8.html
	 */
	public void testTransportationProblem2() {
		int sup[] = new int[]   {5,7,3};
		int dem[] = new int[]   {7,3,5};
		int d[][] = new int[][]{{3,1,Integer.MAX_VALUE},
				{4,2,4},
				{Integer.MAX_VALUE,3,3}};

		Transportation ts = new Transportation (sup, dem, d);
		FordFulkerson ffa = new FordFulkerson(ts, new ShortestPathArray(ts));
		ffa.compute();
		assertEquals (46, ts.getCost());
		assertEquals (15, ts.getFlow());
	}

	/** http://people.brunel.ac.uk/~mastjjb/jeb/or/netflow.html */
	@Test
	public void testTransshipment1() {

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

		// Variable production cost per ton is 11.3, 11.0 and 10.8 (in pounds) at
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
		//        cost (in pounds) per ton to customer
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
		//        cost (in pounds) per ton to customer through warehouse
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
		assertEquals (26765, ts.getCost());
		assertEquals (210, ts.getFlow());		
	}

	/** http://people.brunel.ac.uk/~mastjjb/jeb/or/netflow.html */
	@Test
	public void testTransshipment2() {

		// suppliers and demanders
		int sup[] = new int[]   {60,70,80};
		int dem[] = new int[]   {40, 35, 25, 20, 60, 30};

		// costs at warehouses (different limit).
		int w = 1;
		int warehouse_costs[] = new int[]{7};
		int warehouse_limits[] = new int[]{100};

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

		// Variable production cost per ton is 11.3, 11.0 and 10.8 (in pounds) at
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
		//        cost (in pounds) per ton to customer
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
		//        cost (in pounds) per ton to customer through warehouse
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
		FordFulkerson ff = new FordFulkerson (ts, new ShortestPathArray(ts));
		ff.compute();
		assertEquals (26780, ts.getCost());
		assertEquals (210, ts.getFlow());  // note equals 60+70+80
		
		// validate something comes out...
		String out = ts.output();
		assertFalse (null == out);
		
		// empty warehouses are allowed. Just not null.
		ts = new Transshipment (sup, dem, sup_transship, 
				new int[]{}, new int[]{}, dem_transship, d);
		ff = new FordFulkerson (ts, new ShortestPathArray(ts));
		ff.compute();
		assertEquals (27265, ts.getCost()); // costs go up a bit
		assertEquals (210, ts.getFlow());  // note equals 60+70+80
		
		// deal with various error cases. All should fail.
		try {
			new Transshipment (new int[]{60,70,80,999}, dem, sup_transship, warehouse_costs,
					warehouse_limits, dem_transship, d);
			fail ("shouldn't allow bad inputs.");
		} catch (Exception e) {
			// success
		}
		
		// deal with various error cases. All should fail.
		try {
			new Transshipment (sup, new int[]{60,70,80,999}, sup_transship, warehouse_costs,
					warehouse_limits, dem_transship, d);
			fail ("shouldn't allow bad inputs.");
		} catch (Exception e) {
			// success
		}
		
		// deal with various error cases. All should fail.
		try {
			new Transshipment (sup, dem, new int[13][7], warehouse_costs,
					warehouse_limits, dem_transship, d);
			fail ("shouldn't allow bad inputs.");
		} catch (Exception e) {
			// success
		}
		
		// deal with various error cases. All should fail.
		try {
			new Transshipment (sup, dem, sup_transship, warehouse_costs,
					warehouse_limits, new int[13][7], d);
			fail ("shouldn't allow bad inputs.");
		} catch (Exception e) {
			// success
		}
		
		// deal with various error cases. All should fail.
		try {
			new Transshipment (sup, dem, sup_transship, new int[13],
					warehouse_limits, dem_transship, d);
			fail ("shouldn't allow bad inputs.");
		} catch (Exception e) {
			// success
		}
		
		// costs to ship from warehouse(s) to demanders 
		int dem_transshipBad[][] = new int[][]{{7,9,11,8,6,9,99}};
		try {
			new Transshipment (sup, dem, sup_transship, warehouse_costs,
					warehouse_limits, dem_transshipBad, d);
			fail ("shouldn't allow bad inputs.");
		} catch (Exception e) {
			// success
		}
		
		try {
			new Transshipment (sup, dem, new int[sup.length][w+1], warehouse_costs,
					warehouse_limits, dem_transship, d);
			fail ("shouldn't allow bad inputs.");
		} catch (Exception e) {
			// success
		}
		
	}

	/**
	 * http://people.brunel.ac.uk/~mastjjb/jeb/or/netflow.html
	 */
	@Test 
	public void testAssignment() {

		int d[][] = new int[][]{{22,30,26,16,25},
				{27,29,28,20,32},
				{33,25,21,29,23},
				{24,24,30,19,26},
				{30,33,32,37,31}};

		Assignment as = new Assignment (d);
		FordFulkerson ff = new FordFulkerson (as, new ShortestPathArray(as));
		ff.compute();

		// Should be able to have as many assignments as required.
		assertEquals (d.length, as.getFlow());
		assertEquals (118, as.getCost());
	}

	/**
	 * http://people.brunel.ac.uk/~mastjjb/jeb/or/netflow.html
	 */
	@Test 
	public void testAssignmentError() {

		int d[][] = new int[][]{{22,30,26,16,25},
				{27,29,28,20,32,44},  // ragged input
				{33,25,21,29,23},
				{24,24,30,19,26},
				{30,33,32,37,31}};

		try {
			new Assignment (d);
			fail("Assignment array must be well-formed.");
		} catch (IllegalArgumentException iae) {
			// success
		}
	}

	/**
	 * http://www.me.utexas.edu/~jensen/models/network/net9.html
	 */
	@Test
	public void testAssignment2() {

		int d[][] = new int[][]{{Integer.MAX_VALUE,8,6,12,1},
				{15,12,7,Integer.MAX_VALUE,10},
				{10,Integer.MAX_VALUE,5,14,Integer.MAX_VALUE},
				{12,Integer.MAX_VALUE,12,16,15},
				{18,17,14,Integer.MAX_VALUE,13}};

		Assignment as = new Assignment (d);
		FordFulkerson ff = new FordFulkerson (as, new ShortestPathArray(as));
		ff.compute();
		assertEquals (d.length, as.getFlow());
		assertEquals (51, as.getCost());

	}

	/** Cormen Example: p. 581, First edition.*/
	@Test
	public void testMaximumFlow() {
		ArrayList<EdgeInfo> edges = new ArrayList<EdgeInfo>();
		edges.add(new EdgeInfo(0, 1,   16));
		edges.add(new EdgeInfo(0, 2,   13));

		edges.add(new EdgeInfo(1, 2,   10));
		edges.add(new EdgeInfo(1, 3,   12));

		edges.add(new EdgeInfo(2, 1,   4));
		edges.add(new EdgeInfo(2, 4,   14));

		edges.add(new EdgeInfo(3, 2,   9));
		edges.add(new EdgeInfo(3, 5,   20));

		edges.add(new EdgeInfo(4, 3,   7));
		edges.add(new EdgeInfo(4, 5,   4));

		FlowNetworkArray network = new FlowNetworkArray (6, 0, 5, edges.iterator());
		FordFulkerson ff = new FordFulkerson (network, new ShortestPathArray(network));
		ff.compute();
		assertEquals (23, network.getFlow());
		assertEquals (0, network.getCost());
	}

	/** http://www.mcs.csuhayward.edu/~simon/handouts/4245/hall.html */
	@Test
	public void testBipartiteMatching() {
		String pairings[] = new String[] {
				"Ann Adam",
				"Ann Bob",
				"Beth Adam",
				"Beth Carl",
				"Christina Dan",
				"Christina Erik",
				"Christina Frank",
				"Dorothy Bob",
				"Evelyn Adam",
				"Evelyn Dan",
				"Fiona Frank"};

		DisjointPairs<String,String> dp = new DisjointPairs<String,String>();
		for (String s : pairings) {
			StringTokenizer st = new StringTokenizer(s);
			String v1 = st.nextToken();
			String v2 = st.nextToken();

			// ok to add
			dp.add(v1,v2);
		}
		
		// Prevent duplicate edges being added.
		assertFalse (dp.add("Ann", "Adam"));

		// ensure you don't try to add non-bipartite entity
		try {
			dp.add("Bob", "Ann");
			fail ("Shouldn't be able to add given edge because that would break bipartite consideration.");
		} catch (IllegalArgumentException iae) {

		}

		BipartiteMatchingMinCost mm = new BipartiteMatchingMinCost(dp);
		FordFulkerson ff = new FordFulkerson (mm, new ShortestPathArray(mm));
		ff.compute();
		System.out.println ("cost:" + mm.getCost());
		mm.output();

		// ensure you can't add more pairs.
		dp.add("TEST", "ANOTHER");
	}


	/**
	 * Show how to use MaxFlow to solve Hitchchock transportation problem
	 * 
	 * Taken from Ford-Fulkerson, 1962, pp. 96
	 * 
	 * Assume m=4 sources and n=6 targets.
	 * 
	 *   Dem:3  3  6  2  1  2
	 * Sup:
	 * 4     5  3  7  3  8  5
	 * 5     5  6 12  5  7 11
	 * 3     2  8  3  4  8  2
	 * 9     9  6 10  5 10  9
	 * 
	 * To simulate as a Maximum Flow, Minimum Cost problem, we set the capacity
	 * for all internal edges to be +INF. We create two new vertices, a new 
	 * source, s, and a new target, t. The edges from s to the suppliers have
	 * capacity equal to the supply of these suppliers; the edges from the 
	 * demanders to t have capacity equal to their demand.   
	 * 
	 * @author George Heineman
	 *
	 */
	public void testHitchcock() {
		int sup[] = new int[]   {4, 5, 3, 9};
		int dem[] = new int[]   {3, 3, 6, 2, 1, 2};
		int d[][] = new int[][]{{5,  3,  7,  3,  8,  5},
				{5,  6, 12,  5,  7, 11},
				{2,  8,  3,  4,  8,  2},
				{9,  6, 10,  5, 10,  9}};

		Transportation tr = new Transportation(sup, dem, d);
		FordFulkerson ff = new FordFulkerson (tr, new ShortestPathArray(tr));
		ff.compute();

		assertEquals (17, tr.getFlow());
		assertEquals (93, tr.getCost());
	}

	/**
	 * Taken from JStor paper (http://www.jstor.org/pss/168052)
	 * 
	 * A Dual Labelling Method for the Hitchcock Problem
	 * F. E. A. Briggs
	 * Operations Research, Vol. 10, No. 4 (Jul. - Aug., 1962), pp. 507-517   (article consists of 11 pages)
	 * Published by: INFORMS
	 * 
	 * @author George Heineman
	 *
	 */
	public void testHitchcock2() {
		int sup[] = new int[]   {7,4,5,3,1,2,6,5,5,4};
		int dem[] = new int[]   {1,3,5,2,9,8,7,1,2,4};


		int d[][] = new int[][]{{8,6,4,6,5,7,8,9,5,8},
				{9,8,10,12,5,6,4,8,5,9},
				{11,8,8,10,8,8,8,10,8,10},
				{12,12,12,13,8,10,9,12,11,13},
				{10,7,8,9,5,4,5,7,8,9},
				{11,8,7,12,7,8,10,8,10,11},
				{11,9,9,11,12,10,10,10,8,13},
				{14,10,12,11,10,6,10,14,10,13},
				{13,10,9,14,8,10,10,9,8,11},
				{12,6,8,6,8,1,4,4,8,8}};

		Transportation tr = new Transportation(sup, dem, d);
		FordFulkerson ff = new FordFulkerson (tr, new ShortestPathArray(tr));
		ff.compute();

		assertEquals (42, tr.getFlow());
		assertEquals (271, tr.getCost());
	}


}

