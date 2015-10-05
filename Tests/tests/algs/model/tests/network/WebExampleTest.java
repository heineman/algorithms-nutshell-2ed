package algs.model.tests.network;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.network.BFS_SearchArray;
import algs.model.network.BFS_SearchList;
import algs.model.network.DFS_SearchArray;
import algs.model.network.DFS_SearchList;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetwork;
import algs.model.network.FlowNetworkAdjacencyList;
import algs.model.network.FlowNetworkArray;
import algs.model.network.FordFulkerson;
import algs.model.network.Optimized;

/** Test the code on another example. */
public class WebExampleTest extends TestCase {
	
	ArrayList<EdgeInfo> edges;
	EdgeInfo[] edgesOut;
	EdgeInfo[] edgesIn;
	
	/**
	 * Example drawn from 
	 *
	 * http://www.me.utexas.edu/~jensen/models/network/net11.html
	 * 
	 */
	public void setUp() {
		edgesOut = new EdgeInfo[3];
		edgesIn = new EdgeInfo[3];
		
		edges = new ArrayList<EdgeInfo>();
		edges.add(edgesOut[0] = new EdgeInfo(0, 1,   15));
		edges.add(edgesOut[1] = new EdgeInfo(0, 2,   10));
		edges.add(edgesOut[2] = new EdgeInfo(0, 3,   12));
		
		edges.add(new EdgeInfo(1, 4,   5));
		edges.add(new EdgeInfo(1, 5,   5));
		edges.add(new EdgeInfo(1, 6,   5));
		
		edges.add(new EdgeInfo(2, 4,   6));
		edges.add(new EdgeInfo(2, 5,   6));
		edges.add(new EdgeInfo(2, 6,   6));
		
		edges.add(new EdgeInfo(3, 4,   12));
		
		edges.add(edgesIn[0] = new EdgeInfo(4, 7,   10));
		edges.add(edgesIn[1] = new EdgeInfo(5, 7,   15));
		edges.add(edgesIn[2] = new EdgeInfo(6, 7,   15));
	}
	
	private void validate(FlowNetwork<?> network) {
		network.validate();
				
		int outSum = 0;
		int inSum = 0;
		for (int i = 0; i < edgesIn.length; i++) {
			inSum += edgesIn[i].getFlow();
		}
		for (int i = 0; i < edgesOut.length; i++) {
			outSum += edgesOut[i].getFlow();
		}
		
		assertEquals (inSum, outSum);
		assertEquals (30, inSum);		
	}
	
	@Test
	public void testFulkersonDFS () {
		FlowNetworkArray network = new FlowNetworkArray (8, 0, 7, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new DFS_SearchArray(network));
		ffa.compute();
		validate (network);
	}
	
	@Test
	public void testFulkersonBFS () {
		FlowNetworkArray network = new FlowNetworkArray (8, 0, 7, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new BFS_SearchArray(network));
		ffa.compute();
		validate (network);
	}

	@Test
	public void testFulkersonBFSList () {
		FlowNetworkAdjacencyList network = new FlowNetworkAdjacencyList (8, 0, 7, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new BFS_SearchList(network));
		ffa.compute();
		validate (network);
	}
	
	@Test
	public void testFulkersonDFSList () {
		FlowNetworkAdjacencyList network = new FlowNetworkAdjacencyList (8, 0, 7, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new DFS_SearchList(network));
		ffa.compute();
		validate (network);
	}
	
	@Test
	public void testMoreOptimized () {
		Optimized network = new Optimized(8, 0, 7, edges.iterator());
		int maxFlow = network.compute(0,7);
		assertEquals (30, maxFlow);
	}
}
