package algs.model.tests.network;

import java.util.ArrayList;

import org.junit.Test;

import algs.model.network.DFS_SearchArray;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetworkArray;
import algs.model.network.FordFulkerson;
import algs.model.network.ShortestPathArray;
import algs.model.network.debug.CreateImage;

import junit.framework.TestCase;

/**
 * New example for book.
 * 
 * @author George Heineman
 *
 */
public class Figure8_7Test extends TestCase {

	@Test
	public void testSimple() {

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
		
		assertEquals (3600, network.getCost());
		assertEquals (600, network.getFlow());
		
		network = new FlowNetworkArray (6, 0, 5, edges.iterator());
		ff = new FordFulkerson (network, new ShortestPathArray(network));
		ff.compute();
		new CreateImage().outputGraph(network);
		
		assertEquals (3300, network.getCost());
		assertEquals (600, network.getFlow());
		System.out.println(network);
	}
	
	/**
	 * Confirm results using following maple script:
	 *
	 * Lines below are the maple commands 
	   
		# validate new test case (Figure8_7.java)
		with(simplex);

		Constraints := [

		# conservation of units at each node
		e13+e14+e41+e15 = 400, # CHI
		e21+e23+e24+e25 = 400, # DC

		e13+e23     = 300, # HOU
		e14+e41+e24 = 200, # ATL
		e15+e25     = 300, # BOS

		# maximum flow on individual edges
		0 <= e13, e13 <= 200,
		0 <= e14, e14 <= 350,
		0 <= e41, e41 <= 200,
		0 <= e21, e21 <= 200,
		0 <= e23, e23 <= 280,
		0 <= e15, e15 <= 200,
		0 <= e24, e24 <= 200,
		0 <= e25, e25 <= 350
		];
	
		Cost := 3*e41 + 7*e13 + 3*e14 + 4*e21 + 4*e23 + 7*e24 + 6*e15 + 6*e25;

		# Invoke linear programming to solve problem
		minimize (Cost, Constraints, NONNEGATIVE);

	 * Using the above commands you can verify the flow over the edges, and thus
	 * the computed minimum cost, maximum flow example.
	 */
	@Test
	public void testMoreComplex() {

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
		edges.add(new EdgeInfo(2, 4,   200, 7));  // DC - ATL   (change to 2 for difference)
		edges.add(new EdgeInfo(2, 5,   350, 6));  // DC - BOS
		
		edges.add(new EdgeInfo(4, 1,   200, 3));  // ATL - CHI
		
		FlowNetworkArray network = new FlowNetworkArray (7, 0, 6, edges.iterator());
		FordFulkerson ff = new FordFulkerson (network, new DFS_SearchArray(network));
		ff.compute();
		
		assertEquals (4600, network.getCost());
		assertEquals (800, network.getFlow());
		
		network = new FlowNetworkArray (7, 0, 6, edges.iterator());
		ff = new FordFulkerson (network, new ShortestPathArray(network));
		ff.compute();
		
		assertEquals (3660, network.getCost());
		assertEquals (800, network.getFlow());
		System.out.println(network);
	}
}
