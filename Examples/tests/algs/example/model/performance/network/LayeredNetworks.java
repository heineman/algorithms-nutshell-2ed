package algs.example.model.performance.network;

import junit.framework.TestCase;

import org.junit.Test;

import algs.example.model.network.generator.LayeredNetworkGenerator;
import algs.model.network.BFS_SearchArray;
import algs.model.network.BFS_SearchList;
import algs.model.network.DFS_SearchArray;
import algs.model.network.DFS_SearchList;
import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetwork;
import algs.model.network.FordFulkerson;
import algs.model.network.Optimized;
import algs.model.network.VertexStructure;
import algs.model.network.debug.CreateImage;
import algs.model.tests.common.TrialSuite;

/**
 * Try to randomly create a network that is composed of distinct layers, with edges emanating
 * from left to right, and occasionally down within the same layer, but never to the left.
 * <p>
 * Parameters include (a) the number of layers L; and (b) the number of vertices K to place in
 * the first layer. For the first L/2 layers, an increasing number of vertices are created
 * starting at K and increasing by 4 for each layer. For the last L/2 layers the number decreases
 * back down to K. 
 * 
 * @author George Heineman
 */
public class LayeredNetworks extends TestCase {
	
	@Test
	public void testBFSTiming () {
		TrialSuite suiteBFSArray = new TrialSuite();
		TrialSuite suiteBFSList = new TrialSuite();
		TrialSuite suiteBFSOptimal = new TrialSuite();
		TrialSuite suiteDFSArray = new TrialSuite();
		TrialSuite suiteDFSList = new TrialSuite();
		
		// all experiments run with 1263 as seed
		long seed = 1263;
		
		int NUM_TRIALS = 8;
		int MIN_CAPACITY = 1;
		int MAX_CAPACITY = 160;
		int fanout = 7;
		int K = 9;
		int totalFlow = 0;
		boolean backwardEdges = true;

		System.out.println ("BFS on ARRAY implementation");
		for (int L = 4; L <= 22; L += 2) {
			System.out.println ("\n" + L);
			
			for (int t = 0; t < NUM_TRIALS; t++) {
				System.out.print(".." + t);
				
				LayeredNetworkGenerator.setSeed(seed+t);
				FlowNetwork<EdgeInfo[][]> networkA = LayeredNetworkGenerator.generateArray(L, K, fanout, backwardEdges, MIN_CAPACITY, MAX_CAPACITY);
				FordFulkerson ffa = new FordFulkerson(networkA, new BFS_SearchArray(networkA));
				System.gc();
				
				// array
				long now = System.currentTimeMillis();
				ffa.compute();
				long end = System.currentTimeMillis();
				int checkSum = networkA.getFlow();
				suiteBFSArray.addTrial(L, now, end);
				
				// list
				LayeredNetworkGenerator.setSeed(seed+t);
				FlowNetwork<VertexStructure[]> networkL = LayeredNetworkGenerator.generateList(L, K, fanout, backwardEdges, MIN_CAPACITY, MAX_CAPACITY);
				FordFulkerson ffl = new FordFulkerson(networkL, new BFS_SearchList(networkL));
				System.gc();
				
				now = System.currentTimeMillis();
				ffl.compute();
				end = System.currentTimeMillis();
				suiteBFSList.addTrial(L, now, end);
				if (checkSum != networkL.getFlow()) {
					System.err.println ("DIFFERENT RESULTS!");
				}
				
				// optimized.
				LayeredNetworkGenerator.setSeed(seed+t);
				Optimized ofn = LayeredNetworkGenerator.generateOptimized(L, K, fanout, backwardEdges, MIN_CAPACITY, MAX_CAPACITY);
				System.gc();
				
				now = System.currentTimeMillis();
				int maxF = ofn.compute(ofn.sourceIndex, ofn.sinkIndex);
				end = System.currentTimeMillis();
				suiteBFSOptimal.addTrial(L, now, end);
				if (checkSum != maxF) {
					System.err.println ("DIFFERENT RESULTS for OPTIMIZED:" + checkSum + "," + maxF);
					new CreateImage().outputGraph(networkL);
					fail ("different results.");
				}
				
				
				LayeredNetworkGenerator.setSeed(seed+t);
				networkA = LayeredNetworkGenerator.generateArray(L, K, fanout, backwardEdges, MIN_CAPACITY, MAX_CAPACITY);
				FordFulkerson ffd = new FordFulkerson(networkA, new DFS_SearchArray(networkA));
				System.gc();
				
				// array
				now = System.currentTimeMillis();
				ffd.compute();
				end = System.currentTimeMillis();
				suiteDFSArray.addTrial(L, now, end);
				if (checkSum != networkA.getFlow()) {
					System.err.println ("DIFFERENT RESULTS!");
				}
				
				// list
				LayeredNetworkGenerator.setSeed(seed+t);
				networkL = LayeredNetworkGenerator.generateList(L, K, fanout, backwardEdges, MIN_CAPACITY, MAX_CAPACITY);
				FordFulkerson ffdl = new FordFulkerson(networkL, new DFS_SearchList(networkL));
				System.gc();
				
				now = System.currentTimeMillis();
				ffdl.compute();
				end = System.currentTimeMillis();
				suiteDFSList.addTrial(L, now, end);
				if (checkSum != networkL.getFlow()) {
					System.err.println ("DIFFERENT RESULTS!");
				}
				
				totalFlow += maxF;
			}
		}
		
		System.out.println ();
		System.out.println ("Array");
		System.out.println (suiteBFSArray.computeTable());
		
		System.out.println ("List");
		System.out.println (suiteBFSList.computeTable());
		
		System.out.println ("Optimized");
		System.out.println (suiteBFSOptimal.computeTable());
		
		System.out.println ("DFS Array");
		System.out.println (suiteDFSArray.computeTable());
		
		System.out.println ("DFS List");
		System.out.println (suiteDFSList.computeTable());
		
		System.out.println ("Total flow:" + totalFlow);
	}
	
}
