package algs.chapter8.figure3;

import java.util.ArrayList;

import algs.model.network.BFS_SearchArray;
import algs.model.network.DFS_SearchArray;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetwork;
import algs.model.network.FlowNetworkArray;
import algs.model.network.FordFulkerson;

public class Main {

	static ArrayList<EdgeInfo> edges;
	static EdgeInfo[] edgesOut;
	static EdgeInfo[] edgesIn;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		setUp();
		testFulkersonDFS();
		
		setUp();
		testFulkersonBFS();
		
	}

	/** Not really a JUnit test case, but keep code honest. */
	static void guarantee (boolean value) {
		if (!value) {
			throw new RuntimeException("Failed to meet guarantee");
		}
	}
	
	/**
	 * Example from Figure 8-3
	 */
	public static void setUp() {
		edgesOut = new EdgeInfo[2];
		edgesIn = new EdgeInfo[2];
		
		edges = new ArrayList<EdgeInfo>();
		edges.add(edgesOut[0] = new EdgeInfo(0, 1,   3));
		edges.add(edgesOut[1] = new EdgeInfo(0, 2,   2));
		
		edges.add(new EdgeInfo(1, 3,   2));
		edges.add(new EdgeInfo(1, 4,   2));
		
		edges.add(new EdgeInfo(2, 3,   2));
		edges.add(new EdgeInfo(2, 4,   3));
		
		edges.add(edgesIn[0] = new EdgeInfo(3, 5,   3));
		edges.add(edgesIn[1] = new EdgeInfo(4, 5,   2));
	}
	
	private static void validate(FlowNetwork<?> network) {
		network.validate();
		
		int outSum = 0;
		int inSum = 0;
		for (int i = 0; i < edgesIn.length; i++) {
			inSum += edgesIn[i].getFlow();
		}
		for (int i = 0; i < edgesOut.length; i++) {
			outSum += edgesOut[i].getFlow();
		}
		
		guarantee (inSum == outSum);
		guarantee (5 == inSum);
		
		guarantee (5 == network.getFlow());
		

		guarantee (3 == network.edge(0, 1).getFlow());
		guarantee (2 == network.edge(0, 2).getFlow());
	
		guarantee (2 == network.edge(1, 3).getFlow());
		guarantee (1 == network.edge(1, 4).getFlow());
		guarantee (1 == network.edge(2, 3).getFlow());
		guarantee (1 == network.edge(2, 4).getFlow());
		
		guarantee (3 == network.edge(3, 5).getFlow());
		guarantee (2 == network.edge(4, 5).getFlow());
	}
	
	/** Run in debugger to validate augmenting paths... */
	public static void testFulkersonDFS () {
		FlowNetworkArray network = new FlowNetworkArray (6, 0, 5, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new DFS_SearchArray(network));
		ffa.compute();
		validate (network);
	}
	
	/** Run in debugger to validate augmenting paths... */
	public static void testFulkersonBFS () {
		FlowNetworkArray network = new FlowNetworkArray (6, 0, 5, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new BFS_SearchArray(network));
		ffa.compute();
		validate (network);
	}
}