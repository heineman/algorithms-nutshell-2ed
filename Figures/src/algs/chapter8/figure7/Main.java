package algs.chapter8.figure7;

import java.util.ArrayList;

import algs.model.network.DFS_SearchArray;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetworkArray;
import algs.model.network.FordFulkerson;
import algs.model.network.ShortestPathArray;
import algs.model.network.debug.CreateImage;

/**
 * New example for book.
 * 
 * @author George Heineman
 *
 */
public class Main {

	public static void main (String []args) {
		// this information is shown in figure 8-7.
		testSimple();
	}
	
	public static void testSimple() {

		ArrayList<EdgeInfo> edges = new ArrayList<EdgeInfo>();

		edges.add(new EdgeInfo(0, 1,   300));  // CHI
		edges.add(new EdgeInfo(0, 2,   300));  // DC

		// these drain all into a single target node (6)
		edges.add(new EdgeInfo(3, 5,   300));  // HOU
		edges.add(new EdgeInfo(4, 5,   300));  // BOS
		
		edges.add(new EdgeInfo(1, 3,   200, 7));  // CHI - HOU
		edges.add(new EdgeInfo(1, 4,   200, 6));  // CHI - BOS

		edges.add(new EdgeInfo(2, 3,   280, 4));  // DC - HOU
		edges.add(new EdgeInfo(2, 4,   350, 6));  // DC - BOS
		
		FlowNetworkArray network = new FlowNetworkArray (6, 0, 5, edges.iterator());
		FordFulkerson ff = new FordFulkerson (network, new DFS_SearchArray(network));
		ff.compute();
		new CreateImage().outputGraph(network);
		
		assert (3600 == network.getCost());
		assert (600 == network.getFlow());
		
		// if paying attention to costs as well, then this is the best that
		// we can do (if we minimize costs and maximize throughput).
		network = new FlowNetworkArray (6, 0, 5, edges.iterator());
		ff = new FordFulkerson (network, new ShortestPathArray(network));
		ff.compute();
		new CreateImage().outputGraph(network);
		
		assert (3300 == network.getCost());
		assert (600 == network.getFlow());
		System.out.println(network);
	}
	
	public static void testMoreComplex() {

		ArrayList<EdgeInfo> edges = new ArrayList<EdgeInfo>();

		edges.add(new EdgeInfo(0, 1,   400));  // CHI
		edges.add(new EdgeInfo(0, 2,   400));  // DC

		// these drain all into a single target node (6)
		edges.add(new EdgeInfo(3, 6,   300));  // HOU
		edges.add(new EdgeInfo(4, 6,   200));  // ATL
		edges.add(new EdgeInfo(5, 6,   300));  // BOS
		
		edges.add(new EdgeInfo(1, 5,   200, 6));  // CHI - BOS
		edges.add(new EdgeInfo(1, 4,   350, 3));  // CHI - ATL
		edges.add(new EdgeInfo(1, 3,   200, 7));  // CHI - HOU

		edges.add(new EdgeInfo(2, 1,   200, 4));  // DC - CHI
		edges.add(new EdgeInfo(2, 3,   280, 4));  // DC - HOU
		edges.add(new EdgeInfo(2, 4,   200, 7));  // DC - HOU   (change to 2 for difference)
		edges.add(new EdgeInfo(2, 5,   350, 6));  // DC - BOS
		
		edges.add(new EdgeInfo(4, 1,   200, 3));  // ATL - CHI
		
		FlowNetworkArray network = new FlowNetworkArray (7, 0, 6, edges.iterator());
		FordFulkerson ff = new FordFulkerson (network, new DFS_SearchArray(network));
		ff.compute();
		
		assert (4200 == network.getCost());
		assert (800 == network.getFlow());
		
		network = new FlowNetworkArray (7, 0, 6, edges.iterator());
		ff = new FordFulkerson (network, new ShortestPathArray(network));
		ff.compute();
		
		assert (3660 == network.getCost());
		assert (800 == network.getFlow());
	}
}
