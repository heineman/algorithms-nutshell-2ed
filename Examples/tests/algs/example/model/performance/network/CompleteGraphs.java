package algs.example.model.performance.network;

import org.junit.Test;

import algs.example.model.network.generator.FlowNetworkGenerator;
import algs.model.network.BFS_SearchArray;
import algs.model.network.BFS_SearchList;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetwork;
import algs.model.network.FordFulkerson;
import algs.model.network.VertexStructure;
import algs.model.tests.common.TrialSuite;

public class CompleteGraphs {
	@Test
	public void testBFSTiming () {
		TrialSuite suiteArray = new TrialSuite();
		TrialSuite suiteList = new TrialSuite();
		
		long seed = 1263;
		
		int NUM_TRIALS = 8;
		int MIN_CAPACITY = 15;
		int MAX_CAPACITY = 20;

		System.out.println ("BFS on ARRAY implementation");
		for (int n = 8; n <= 256; n *= 2) {
			System.out.println (n);
			
			for (int k = 0; k < NUM_TRIALS; k++) {
				System.out.print(".." + k);
				FlowNetworkGenerator.setSeed(seed+k);
				FlowNetwork<EdgeInfo[][]> networkA = FlowNetworkGenerator.generateArray(n, n-3, n-3, MIN_CAPACITY, MAX_CAPACITY);
				FordFulkerson ffa = new FordFulkerson(networkA, new BFS_SearchArray(networkA));
				System.gc();
				
				long now = System.currentTimeMillis();
				ffa.compute();
				long end = System.currentTimeMillis();
				int checkSum = networkA.getFlow();
				suiteArray.addTrial(n, now, end);
				
				FlowNetworkGenerator.setSeed(seed+k);
				FlowNetwork<VertexStructure[]> networkL = FlowNetworkGenerator.generateList(n, n-3, n-3, MIN_CAPACITY, MAX_CAPACITY);
				ffa = new FordFulkerson(networkL, new BFS_SearchList(networkL));
				System.gc();
				
				now = System.currentTimeMillis();
				ffa.compute();
				end = System.currentTimeMillis();
				suiteList.addTrial(n, now, end);
				if (checkSum != networkL.getFlow()) {
					System.err.println ("DIFFERENT RESULTS!");
				}
			}
		}
		
		System.out.println ("Array");
		System.out.println (suiteArray.computeTable());
		
		System.out.println ("List");
		System.out.println (suiteList.computeTable());
	}
	
}
