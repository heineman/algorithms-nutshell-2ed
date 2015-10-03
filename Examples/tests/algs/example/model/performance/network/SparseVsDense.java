package algs.example.model.performance.network;

import org.junit.Test;

import algs.example.model.network.generator.FlowNetworkGenerator;
import algs.model.network.BFS_SearchArray;
import algs.model.network.BFS_SearchList;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetwork;
import algs.model.network.FordFulkerson;
import algs.model.network.Optimized;
import algs.model.network.VertexStructure;
import algs.model.tests.common.TrialSuite;

public class SparseVsDense {
	
	@Test
	public void testBFSTiming () {
		TrialSuite suiteArray = new TrialSuite();
		TrialSuite suiteList = new TrialSuite();
		TrialSuite suiteOptimal = new TrialSuite();
		
		// all experiments run with 1263 as seed
		long seed = 1263;
		
		int NUM_TRIALS = 8;
		int MIN_FANIN = 2;
		int MAX_FANIN = 2;
		int MIN_CAPACITY = 1;
		int MAX_CAPACITY = 160;
		int totalFlow = 0;

		System.out.println ("BFS on ARRAY implementation");
		for (int n = 32; n <= 1024; n *= 2) {
			System.out.println (n);
			
			for (int k = 0; k < NUM_TRIALS; k++) {
				System.out.print(".." + k);
				FlowNetworkGenerator.setSeed(seed+k);
				FlowNetwork<EdgeInfo[][]> networkA = FlowNetworkGenerator.generateArray(n, MIN_FANIN, MAX_FANIN, MIN_CAPACITY, MAX_CAPACITY);
				FordFulkerson ffa = new FordFulkerson(networkA, new BFS_SearchArray(networkA));
				System.gc();
				
				// array
				long now = System.currentTimeMillis();
				ffa.compute();
				long end = System.currentTimeMillis();
				int checkSum = networkA.getFlow();
				suiteArray.addTrial(n, now, end);
				
				// list
				FlowNetworkGenerator.setSeed(seed+k);
				FlowNetwork<VertexStructure[]> networkL = FlowNetworkGenerator.generateList(n, MIN_FANIN, MAX_FANIN, MIN_CAPACITY, MAX_CAPACITY);
				ffa = new FordFulkerson(networkL, new BFS_SearchList(networkL));
				System.gc();
				
				now = System.currentTimeMillis();
				ffa.compute();
				end = System.currentTimeMillis();
				suiteList.addTrial(n, now, end);
				if (checkSum != networkL.getFlow()) {
					System.err.println ("DIFFERENT RESULTS!");
				}
				
				// optimized.
				FlowNetworkGenerator.setSeed(seed+k);
				Optimized ofn = FlowNetworkGenerator.generateOptimized(n, MIN_FANIN, MAX_FANIN, MIN_CAPACITY, MAX_CAPACITY);
				System.gc();
				
				now = System.currentTimeMillis();
				int maxF = ofn.compute(0, n-1);
				end = System.currentTimeMillis();
				suiteOptimal.addTrial(n, now, end);
				if (checkSum != maxF) {
					System.err.println ("DIFFERENT RESULTS for OPTIMIZED!");
				}
				
				totalFlow += maxF;
			}
		}
		
		System.out.println ("Array");
		System.out.println (suiteArray.computeTable());
		
		System.out.println ("List");
		System.out.println (suiteList.computeTable());
		
		System.out.println ("Optimized");
		System.out.println (suiteOptimal.computeTable());
		
		System.out.println ("Total flow:" + totalFlow);
	}
	
}
