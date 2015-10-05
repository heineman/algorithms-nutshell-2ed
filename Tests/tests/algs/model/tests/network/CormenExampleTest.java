package algs.model.tests.network;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.list.DoubleLinkedList;
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
import algs.model.network.ShortestPathArray;

/** Test the code on the Cormen example. */
public class CormenExampleTest extends TestCase {
	
	ArrayList<EdgeInfo> edges;
	EdgeInfo[] edgesOut;
	EdgeInfo[] edgesIn;
	
	/**
	 * Example drawn from [Cormen], p. 581]
	 * 
	 */
	public void setUp() {
		edgesOut = new EdgeInfo[2];
		edgesIn = new EdgeInfo[2];
		
		edges = new ArrayList<EdgeInfo>();
		edges.add(edgesOut[0] = new EdgeInfo(0, 1,   16));
		edges.add(edgesOut[1] = new EdgeInfo(0, 2,   13));
		
		edges.add(new EdgeInfo(1, 2,   10));
		edges.add(new EdgeInfo(1, 3,   12));
		
		edges.add(new EdgeInfo(2, 1,   4));
		edges.add(new EdgeInfo(2, 4,   14));
		
		edges.add(new EdgeInfo(3, 2,   9));
		edges.add(edgesIn[0] = new EdgeInfo(3, 5,   20));
		
		edges.add(new EdgeInfo(4, 3,   7));
		edges.add(edgesIn[1] = new EdgeInfo(4, 5,   4));
	}
	
	public void validate(FlowNetwork<?> network) {
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
		assertEquals (23, inSum);
		
		// ensure minCut is satisfied.
		
		// just for testing purposes.
		Hashtable<EdgeInfo,Integer> edges = new Hashtable<EdgeInfo,Integer>();
		EdgeInfo res[] = new EdgeInfo[] { new EdgeInfo (1, 3, 12),
				new EdgeInfo (4, 3, 7),
				new EdgeInfo (4, 5, 4)
		};
		for (EdgeInfo ei : res) {
			edges.put (ei, 1);
		}
		DoubleLinkedList<EdgeInfo> s = network.getMinCut();
		java.util.Iterator<EdgeInfo> it = s.iterator();
		while (it.hasNext()) {
			EdgeInfo ei = it.next();
			assertEquals (ei + " is not found", new Integer(1), edges.get(ei)); 
			edges.remove(ei);
		}
		assertEquals (0, edges.size());
	}
	
	@Test
	public void testFulkersonShortestPath () {
		FlowNetworkArray network = new FlowNetworkArray (6, 0, 5, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new ShortestPathArray(network));
		ffa.compute();
		validate (network);
		assertEquals (23, network.getFlow());

		DoubleLinkedList<EdgeInfo> edges = network.getMinCut();
		for (Iterator<EdgeInfo> it = edges.iterator(); it.hasNext(); ) {
			System.out.println (it.next());
		}
	}
	
	@Test
	public void testFulkersonDFS () {
		FlowNetworkArray network = new FlowNetworkArray (6, 0, 5, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new DFS_SearchArray(network));
		ffa.compute();
		validate (network);

	}
	
	@Test
	public void testFulkersonBFS () {
		FlowNetworkArray network = new FlowNetworkArray (6, 0, 5, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new BFS_SearchArray(network));
		ffa.compute();
		validate (network);
		assertEquals (23, network.getFlow());
	}
	
	@Test
	public void testFulkersonBFSList () {
		FlowNetworkAdjacencyList network = new FlowNetworkAdjacencyList(6, 0, 5, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new BFS_SearchList(network));
		ffa.compute();
		validate (network);
	}

	@Test
	public void testFulkersonDFSList () {
		FlowNetworkAdjacencyList network = new FlowNetworkAdjacencyList(6, 0, 5, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new DFS_SearchList(network));
		ffa.compute();
		validate (network);
	}
	
	@Test
	public void testMoreOptimized () {
		Optimized network = new Optimized(6, 0, 5, edges.iterator());
		int maxFlow = network.compute(0,5);
		assertEquals (23, maxFlow);
		network.validate();
	}
}
