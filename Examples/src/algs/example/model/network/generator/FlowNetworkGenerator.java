package algs.example.model.network.generator;

import java.util.ArrayList;
import java.util.Random;

import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetwork;
import algs.model.network.FlowNetworkAdjacencyList;
import algs.model.network.FlowNetworkArray;
import algs.model.network.Optimized;
import algs.model.network.VertexStructure;

/**
 * Generates random FlowNetwork problems.
 * 
 * Key parameters that control the generation are:
 * 
 * <ul><li>minFanOut -- The minimum degree of nodes in the network. Must
 *      be &gt; 0
 *     <li>maxFanOut -- The maximum degree. Must be &ge; minFanIn
 *     <li>minCapacity -- Minimum capacity to assign to an edge
 *     <li>maxCapacity -- Maxium capacity to assign to an edge.  Must be &ge; minCapacity
 *     <li>numVertices -- The number of vertices (other than source/sink)
 * </ul>
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class FlowNetworkGenerator {
	
	// random generator.
	static Random rnd;
	
	public static void setSeed(long seed) {
		rnd = new Random(seed);
	}
	
	public static FlowNetwork<EdgeInfo[][]> generateArray (int numVertices, int minFanOut, int maxFanOut, int minCapacity, int maxCapacity) {
		ArrayList<EdgeInfo> edges = generateEdges (numVertices, minFanOut, maxFanOut, minCapacity, maxCapacity);
		
		return new FlowNetworkArray (numVertices, 0, numVertices-1, edges.iterator());
	}


	public static FlowNetwork<VertexStructure[]> generateList (int numVertices, int minFanOut, int maxFanOut, int minCapacity, int maxCapacity) {
		ArrayList<EdgeInfo> edges = generateEdges (numVertices, minFanOut, maxFanOut, minCapacity, maxCapacity);
		
		return new FlowNetworkAdjacencyList (numVertices, 0, numVertices-1, edges.iterator());
	}
	
	public static Optimized generateOptimized (int numVertices, int minFanOut, int maxFanOut, int minCapacity, int maxCapacity) {
		ArrayList<EdgeInfo> edges = generateEdges (numVertices, minFanOut, maxFanOut, minCapacity, maxCapacity);
		
		return new Optimized (numVertices, 0, numVertices-1, edges.iterator());
	}
	
	private static ArrayList<EdgeInfo> generateEdges(int numVertices,
			int minFanOut, int maxFanOut, int minCapacity, int maxCapacity) {
		ArrayList<EdgeInfo> edges = new ArrayList<EdgeInfo>();
		
		int num = maxFanOut - minFanOut + 1;
		// create random edges.
		int vertices[][] = new int[numVertices][];
		for (int i = 0; i < numVertices; i++) {
			vertices[i] = new int [minFanOut+num-1];
		}
		
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < vertices[i].length; j++) {
				vertices[i][j] = -1;
			}
		}
		
		// makes sure each edge is unique within itself. These do not involve either
		// the source {0} or the sink {numVertices-1}.
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < vertices[i].length; j++) {
				boolean duplicate;
				int t = -1;
				do {
					t = 1 + (int)(rnd.nextDouble () * (numVertices-2));
					duplicate = false;
					
					for (int k = 0; k < j; k++) {
						if (vertices[i][k] == t) {
							duplicate = true; 
							break;
						}
					}
					
				} while ((t==i) || (duplicate));
				vertices[i][j] = t;
			}
		}
		

		// for the SINK, we note that no edge current can connect to it, since the random
		// generator was restricted. So we grab the edges *FROM* sink and act as if they
		// were coming into it. We overwrite one of those other nodes' links without any effect.
		for (int j = 0; j < vertices[0].length; j++) {
			int other = vertices[numVertices-1][j];
			
			vertices[other][0] = numVertices-1;
		}
		
		// construct edges from this information. Randomly assign capacity
		// values in the given range. Note that the source and sink will be
		// confirmed to have the max capacity just to make it interesting.
		for (int i = 1; i < numVertices-1; i++) {
			for (int j = 0; j < vertices[i].length; j++) {
				int cap = (int)(rnd.nextDouble()*(maxCapacity - minCapacity));
				EdgeInfo ei = new EdgeInfo (i, vertices[i][j], minCapacity + cap);
				edges.add(ei);
			}
		}
		
		// add source/sink
		for (int j = 0; j < vertices[0].length; j++) {
			EdgeInfo ei = new EdgeInfo (0, vertices[0][j], maxCapacity);
			edges.add(ei);
		}
		
		return edges;
	}

}
