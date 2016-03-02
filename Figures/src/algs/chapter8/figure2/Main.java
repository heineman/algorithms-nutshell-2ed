package algs.chapter8.figure2;

import java.util.ArrayList;

import algs.model.network.DFS_SearchArray;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetwork;
import algs.model.network.FlowNetworkArray;
import algs.model.network.FordFulkerson;

/** Test the code on another example. */
public class Main {
	
	static ArrayList<EdgeInfo> edges;
	static EdgeInfo[] edgesOut;
	static EdgeInfo[] edgesIn;
	
	/**
	 * Example used for Figure 2 
	 */
	public static void main (String[] args) {
		edgesOut = new EdgeInfo[3];
		edgesIn = new EdgeInfo[2];
		
		edges = new ArrayList<EdgeInfo>();
		edges.add(edgesOut[0] = new EdgeInfo(0, 1,   10));
		edges.add(edgesOut[1] = new EdgeInfo(0, 2,   5));
		edges.add(edgesOut[2] = new EdgeInfo(0, 3,   4));
		
		edges.add(new EdgeInfo(1, 4,   5));
		edges.add(new EdgeInfo(1, 2,   3));
		
		edges.add(new EdgeInfo(2, 4,   2));
		edges.add(new EdgeInfo(2, 3,   5));
		
		edges.add(new EdgeInfo(3, 5,   8));
		
		// JUST confusing and actually may cause problems.
		// this was in 1st edition.
		//edges.add(new EdgeInfo(4, 2,   4));
		
		edges.add(new EdgeInfo(5, 2,   3));
		
		edges.add(edgesIn[0] = new EdgeInfo(4, 7,   7));
		edges.add(edgesIn[1] = new EdgeInfo(5, 7,   11));
		
		FlowNetworkArray network = new FlowNetworkArray (8, 0, 7, edges.iterator());
		FordFulkerson ffa = new FordFulkerson(network, new DFS_SearchArray(network));
		ffa.compute();
		validate (network);
		System.out.println("DONE");
		
		System.out.println(network.toString());
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
		
		if (inSum != outSum) {
			System.out.println("Error: different IN and OUT sums.");
		}
		
		if (inSum != 15) {
			System.out.println("Example should have had maxFlow of 15.");
		}
	}
}
