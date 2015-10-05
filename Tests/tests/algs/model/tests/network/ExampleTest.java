package algs.model.tests.network;

import java.util.ArrayList;

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
import algs.model.network.OptimizedFlowNetwork;

/** Test FlowNetwork code */
public class ExampleTest extends TestCase {
	
	ArrayList<EdgeInfo> edges;
	EdgeInfo[] edgesOut;
	EdgeInfo[] edgesIn;
	
	/**
	 * Example drawn from [Drozdek, Algorithms in Java], p. 391]
	 * 
	 */
	public void setUp() {
		edgesOut = new EdgeInfo[3];
		edgesIn = new EdgeInfo[3];
		
		edges = new ArrayList<EdgeInfo>();
		edges.add(edgesOut[0] = new EdgeInfo(0, 1,   2));
		edges.add(edgesOut[1] = new EdgeInfo(0, 2,   4));
		edges.add(edgesOut[2] = new EdgeInfo(0, 3,   1));
		
		edges.add(new EdgeInfo(1, 4,   5));
		
		edges.add(new EdgeInfo(2, 1,   2));
		edges.add(new EdgeInfo(2, 6,   3));
		
		edges.add(new EdgeInfo(3, 5,   2));
		edges.add(new EdgeInfo(3, 6,   3));
		
		edges.add(edgesIn[0] = new EdgeInfo(4, 7,   3));
		
		edges.add(new EdgeInfo(5, 4,   5));
		
		edges.add(edgesIn[1] = new EdgeInfo(5, 7,   2));
		edges.add(edgesIn[2] = new EdgeInfo(6, 7,   1));
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
		assertEquals (5, inSum);
		
		assertEquals (5, network.getFlow());
		assertEquals (0, network.getCost());
		
		// output mincut
		DoubleLinkedList<EdgeInfo> dl = network.getMinCut();
		EdgeInfo ei;
		while (!dl.isEmpty()) {
			ei = dl.removeFirst();
			System.out.println (ei);
		}

		assertEquals (2, network.edge(0, 1).getFlow());
		assertEquals (2, network.edge(0, 2).getFlow());
		assertEquals (1, network.edge(0, 3).getFlow());
		assertEquals (3, network.edge(1, 4).getFlow());
		assertEquals (1, network.edge(2, 1).getFlow());
		assertEquals (1, network.edge(2, 6).getFlow());
		assertEquals (1, network.edge(3, 5).getFlow());
		assertEquals (0, network.edge(3, 6).getFlow());
		assertEquals (3, network.edge(4, 7).getFlow());
		assertEquals (0, network.edge(5, 4).getFlow());
		assertEquals (1, network.edge(5, 7).getFlow());
		assertEquals (1, network.edge(6, 7).getFlow());
	

		assertFalse (null == network.toString());
	}
	
	@Test
	public void testFulkersonDFS () {
		// (int numVertices, Iterator<EdgeInfo> edges, int srcIndex, int tgtIndex) {
		FlowNetworkArray network = new FlowNetworkArray (8, 0, 7, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new DFS_SearchArray(network));
		ffa.compute();
		validate (network);
	}
	
	@Test
	public void testFulkersonBFS () {
		// (int numVertices, Iterator<EdgeInfo> edges, int srcIndex, int tgtIndex) {
		FlowNetworkArray network = new FlowNetworkArray (8, 0, 7, edges.iterator());
		assertEquals (8, network.numVertices);
		assertEquals (0, network.sourceIndex);
		assertEquals (7, network.sinkIndex);
		FordFulkerson ffa = new FordFulkerson(network, new BFS_SearchArray(network));
		ffa.compute();
		validate (network);
	}

	@Test
	public void testDefaultFulkersonBFS () {
		// (int numVertices, Iterator<EdgeInfo> edges, int srcIndex, int tgtIndex) {
		FlowNetworkArray network = new FlowNetworkArray (8, 0, 7, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new DFS_SearchArray(network));
		ffa.compute();
		validate (network);
	}

	
	@Test
	public void testFulkersonBFSList () {
		// (int numVertices, Iterator<EdgeInfo> edges, int srcIndex, int tgtIndex) {
		FlowNetworkAdjacencyList network = new FlowNetworkAdjacencyList (8, 0, 7, edges.iterator());
		
		// invalid edge requests are dealt with
		assertTrue (null == network.edge(2, 5)); // this edge doesn't exist.

		FordFulkerson ffa = new FordFulkerson(network, new BFS_SearchList(network));
		ffa.compute();
		validate (network);
	}
	
	@Test
	public void testFulkersonDFSList () {
		// (int numVertices, Iterator<EdgeInfo> edges, int srcIndex, int tgtIndex) {
		FlowNetworkAdjacencyList network = new FlowNetworkAdjacencyList (8, 0, 7, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new DFS_SearchList(network));
		ffa.compute();
		validate (network);
	}
	
	@Test
	public void testMoreOptimized () {
		Optimized network = new Optimized(8, 0, 7, edges.iterator());
		
		// validate inability to get real info
		try {
			network.edge(0, 1);
			fail ("There is no edge info to retrieve from Optimized via edge method.");
		} catch (RuntimeException rte) {
			
		}

		try {
			network.getEdgeStructure();
			fail ("There is no edge structure to retrieve from Optimized via edge method.");
		} catch (RuntimeException rte) {
			
		}
		
		int maxFlow = network.compute(0,7);
		assertEquals (5, maxFlow);
		assertEquals (5, network.getFlow());
		
		OptimizedFlowNetwork arrayBased = new OptimizedFlowNetwork(8, 0, 7, edges.iterator());
		assertEquals (0, arrayBased.getSource());
		assertEquals (7, arrayBased.getSink());
		maxFlow = arrayBased.compute(0,7);
		assertEquals (5, maxFlow);
		
		// compare min cuts
		DoubleLinkedList<EdgeInfo> one = network.getMinCut();
		DoubleLinkedList<EdgeInfo> two = arrayBased.getMinCut();
		assertEquals (one.size(), two.size());
		while (one.size()>0) {
			assertEquals (one.removeFirst(), two.removeFirst());
		}
		
		// network model knows about cost. just confirm this
		assertEquals (0, network.getCost());
		
		// validate same graphs result
		assertEquals (network.toString(), arrayBased.toString());
	}
}
