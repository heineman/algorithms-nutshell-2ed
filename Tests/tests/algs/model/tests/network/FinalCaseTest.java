package algs.model.tests.network;

import java.util.ArrayList;

import org.junit.Test;

import algs.model.network.BFS_SearchArray;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetworkArray;
import algs.model.network.FordFulkerson;
import algs.model.network.ShortestPathArray;

import junit.framework.TestCase;

/**
 * http://www.me.utexas.edu/~jensen/ORMM/models/unit/network/subunits/distribution/index.html
 * 
 * @author George
 *
 */
public class FinalCaseTest extends TestCase {

	@Test
	public void testDistributionExample() {

		ArrayList<EdgeInfo> edges = new ArrayList<EdgeInfo>();

		edges.add(new EdgeInfo(0, 1,   700));  // PHO
		edges.add(new EdgeInfo(0, 7,   200));  // AUS
		edges.add(new EdgeInfo(0, 8,   200));  // GAIN

		// these drain all into a single target node (9)
		edges.add(new EdgeInfo(2, 9,   200));  // CHI
		edges.add(new EdgeInfo(3, 9,   200));  // LA
		edges.add(new EdgeInfo(6, 9,   250));  // NY
		edges.add(new EdgeInfo(4, 9,   300));  // DAL
		edges.add(new EdgeInfo(5, 9,   150));  // ATL
		
		edges.add(new EdgeInfo(1, 2,   200, 6));  // PHO - CHI
		edges.add(new EdgeInfo(1, 3,   200, 3));  // PHO - LA
		edges.add(new EdgeInfo(1, 4,   200, 3));  // PHO - DAL
		edges.add(new EdgeInfo(1, 5,   200, 7));  // PHO - ATL

		edges.add(new EdgeInfo(7, 3,   200, 7));  // AUS - LA
		edges.add(new EdgeInfo(7, 4,   200, 2));  // AUS - DAL
		edges.add(new EdgeInfo(7, 5,   200, 5));  // AUS - ATL
		
		edges.add(new EdgeInfo(8, 4,   200, 6));  // GAIN - DAL
		edges.add(new EdgeInfo(8, 5,   200, 4));  // GAIN - ATL
		edges.add(new EdgeInfo(8, 6,   200, 7));  // GAIN - NY

		edges.add(new EdgeInfo(4, 3,   200, 5));  // DAL - LA
		edges.add(new EdgeInfo(4, 2,   200, 4));  // DAL - CHI
		edges.add(new EdgeInfo(4, 6,   200, 6));  // DAL - NY

		edges.add(new EdgeInfo(5, 6,   200, 5));  // ATL - NY
		edges.add(new EdgeInfo(5, 2,   200, 4));  // ATL - CHI
		
		edges.add(new EdgeInfo(4, 5,   200, 2));  // DAL - ATL
		edges.add(new EdgeInfo(5, 4,   200, 2));  // ATL - DAL
		
		FlowNetworkArray network = new FlowNetworkArray (12, 0, 9, edges.iterator());
		FordFulkerson ff = new FordFulkerson (network, new BFS_SearchArray(network));
		ff.compute();
		
		// without attention to costs...
		assertEquals (5350, network.getCost());
		assertEquals (1100, network.getFlow());
		
		network = new FlowNetworkArray (12, 0, 9, edges.iterator());
		ff = new FordFulkerson (network, new ShortestPathArray(network));
		ff.compute();
		assertEquals (5300, network.getCost());
		assertEquals (1100, network.getFlow());
		
		System.out.println(network);

	}
}
