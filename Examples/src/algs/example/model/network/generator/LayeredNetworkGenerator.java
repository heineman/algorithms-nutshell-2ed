package algs.example.model.network.generator;

import java.util.ArrayList;
import java.util.Random;

import algs.model.network.EdgeInfo;
import algs.model.network.FlowNetwork;
import algs.model.network.FlowNetworkAdjacencyList;
import algs.model.network.FlowNetworkArray;
import algs.model.network.Optimized;
import algs.model.network.VertexInfo;
import algs.model.network.VertexStructure;

/**
 * Try to randomly create a network that is composed of distinct layers, with edges emanating
 * from left to right, and occasionally down within the same layer, but never to the left.
 * <p>
 * Parameters include (a) the number of layers L; and (b) the number of vertices K to place in
 * the first layer; and (c) fanout which must be 1 < f < K. For the first L/2 layers, an 
 * increasing number of vertices are created starting at K and increasing by 4 for each 
 * layer. For the last L/2 layers the number decreases back down to K. 
 * <p>
 * L must be divisible by two.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LayeredNetworkGenerator {
	
	// random generator.
	static Random rnd;
	
	public static void setSeed(long seed) {
		rnd = new Random(seed);
	}
	
	// compute structure.
	private static VertexInfo[] generateVertices(int L, int K) {
		// total vertices: 1 + 2L(L/2-1)+ N*L + 1
		int numVertices = 1 + 2*L*(L/2-1) + K*L + 1;
		return new VertexInfo[numVertices];
	}

	
	public static FlowNetwork<EdgeInfo[][]> generateArray (int L, int K, int fanout, boolean backwardEdges, int minCapacity, int maxCapacity) {
		if (L %2 != 0) { throw new IllegalArgumentException ("NumLayers must be even!"); }
		VertexInfo[] vertices = generateVertices (L, K);
		ArrayList<EdgeInfo> edges = generateEdges (vertices, L, K, fanout, backwardEdges, minCapacity, maxCapacity);
		
		return new FlowNetworkArray (vertices.length, 0, vertices.length-1, edges.iterator());
	}

	public static FlowNetwork<VertexStructure[]> generateList (int L, int K, int fanout, boolean backwardEdges, int minCapacity, int maxCapacity) {
		if (L %2 != 0) { throw new IllegalArgumentException ("NumLayers must be even!"); }
		VertexInfo[] vertices = generateVertices (L, K);
		ArrayList<EdgeInfo> edges = generateEdges (vertices, L, K, fanout, backwardEdges, minCapacity, maxCapacity);
		
		return new FlowNetworkAdjacencyList (vertices.length, 0, vertices.length-1, edges.iterator());
	}
	
	public static Optimized generateOptimized (int L, int K, int fanout, boolean backwardEdges, int minCapacity, int maxCapacity) {
		if (L %2 != 0) { throw new IllegalArgumentException ("NumLayers must be even!"); }
		VertexInfo[] vertices = generateVertices (L, K);
		ArrayList<EdgeInfo> edges = generateEdges (vertices, L, K, fanout, backwardEdges, minCapacity, maxCapacity);
		
		return new Optimized (vertices.length, 0, vertices.length-1, edges.iterator());
	}
	
	/** 
	 * return id of the first vertex in the ith layer for the structure defined by K,L. 
	 * 
	 *  1..K
	 *  K+1 .. 2K+4
	 *  2K+5 .. 3K+12
	 *  3K+13 .. 4K+24
	 *  4K+25 ..  5K+40
	 *  5K+41
	 */
	public static int firstVertex(int L, int K, int i) {
		// Assume i in the range 0 .. L-1
		int base = 1;
		int increment = 0;
		for (int j = 0; j < L/2; j++) {
			if (j == i) { return base; }
			base += K + increment;
			increment += 4;
		}
	
		
		for (int j = L/2; j < L; j++) {
			if (j == i) { return base; }
			increment -= 4;
			base += K + increment;
		}
		
		// must be this one? Not sure.
		return base;		
	}
	
	/** 
	 * return id of the lase vertex in the ith layer for the structure defined by K,L. 
	 * 
	 *  1..K
	 *  K+1 .. 2K+4
	 *  2K+5 .. 3K+12
	 *  3K+13 .. 4K+24
	 *  4K+25 ..  5K+40
	 *  5K+41
	 */
	public static int lastVertex(int L, int K, int i) {
		// Assume i in the range 0 .. L-1
		int base = 1;
		int range = K;
		int increment = 0;
		for (int j = 0; j < L/2; j++) {
			if (j == i) { return base + range - 1; }
			base += K + increment;
			increment += 4;
			range += 4;
		}
		range -= 4;
		increment -= 4;
		
		for (int j = L/2; j < L; j++) {
			if (j == i) { return base + range - 1; }
			base += K + increment;
			increment -= 4;
			range -= 4;
		}
		
		// must be this one? Not sure.
		return base + range - 1;
	}
	
	/**
	 *  edges are from left to right
	 *  
	 *  1  K   K+4   K+8  ...    K+8  K+4   K   1
	 *  
	 *  vertices labeled
	 *  
	 *  0
	 *  1..K
	 *  K+1 .. 2K+4
	 *  2K+5 .. 3K+12
	 *  3K+13 .. 4K+24
	 *  4K+25 ..  5K+40
	 *  5K+41
	 *  
	 */
	private static ArrayList<EdgeInfo> generateEdges(VertexInfo[] vertices, 
			int L, int K, int fanout, boolean backwardEdges, int minCapacity, int maxCapacity) {
		ArrayList<EdgeInfo> edges = new ArrayList<EdgeInfo>();
		
		// add edges from source to first K nodes.
		int last = lastVertex(L, K, 0);
		for (int f = firstVertex(L, K, 0); f <= last; f++) {
			int cap = (int)(rnd.nextDouble()*(maxCapacity - minCapacity));
			EdgeInfo ei = new EdgeInfo (0, f, minCapacity + cap);
			edges.add(ei);
		}
		
		// for each vertex in layer Li, construct fanout edges to a vertex in layer Li+1.
		ArrayList<Integer> nextOnes = new ArrayList<Integer>();
		
		for (int i = 0; i < L/2; i++) {
			int id = firstVertex(L, K, i);   // find first vertex
			last = lastVertex (L, K, i);
			while (id <= last) {
				nextOnes.clear();
				int nextFirst = firstVertex(L, K, i+1);
				int nextLast = lastVertex (L, K, i+1);
				
				// select set of f vertices from the next layer [except for sink]
				int t = -1;
				do {
					t = nextFirst + (int)(rnd.nextDouble () * (nextLast - nextFirst + 1));
					if (nextOnes.contains(t)) {
						continue;
					}
					nextOnes.add(t);
				} while (nextOnes.size() != fanout);
				
				// add the f edges.
				for (int f = 0; f < nextOnes.size(); f++) {
					int cap = (int)(rnd.nextDouble()*(maxCapacity - minCapacity));
					EdgeInfo ei = new EdgeInfo (id, nextOnes.get(f), minCapacity + cap);
					edges.add(ei);
				}
				
				id++;
			}
		}
		
		// decreasing set now.
		for (int i = L/2; i < L-1; i++) {
			int id = firstVertex(L, K, i);   // find first vertex
			last = lastVertex (L, K, i);
			while (id <= last) {
				nextOnes.clear();
				int nextFirst = firstVertex(L, K, i+1);
				int nextLast = lastVertex (L, K, i+1);
				
				// select set of f vertices from the next layer [except for sink]
				int t = -1;
				do {
					t = nextFirst + (int)(rnd.nextDouble () * (nextLast - nextFirst + 1));
					if (nextOnes.contains(t)) {
						continue;
					}
					nextOnes.add(t);
				} while (nextOnes.size() != fanout);
				
				// add the f edges.
				for (int f = 0; f < nextOnes.size(); f++) {
					int cap = (int)(rnd.nextDouble()*(maxCapacity - minCapacity));
					EdgeInfo ei = new EdgeInfo (id, nextOnes.get(f), minCapacity + cap);
					edges.add(ei);
				}
				
				id++;
			}
		}
		
		// add backward ones as well. These go from layers 2 .. L and use the same fanout.
		if (backwardEdges) {
			for (int i = 2; i < L/2; i++) {
				int id = firstVertex(L, K, i);   // find first vertex
				last = lastVertex (L, K, i);
				while (id <= last) {
					nextOnes.clear();
					int nextFirst = firstVertex(L, K, i-1);
					int nextLast = lastVertex (L, K, i-1);
					
					// select set of f vertices from the next layer [except for sink]
					int t = -1;
					do {
						t = nextFirst + (int)(rnd.nextDouble () * (nextLast - nextFirst + 1));
						if (nextOnes.contains(t)) {
							continue;
						}
						nextOnes.add(t);
					} while (nextOnes.size() != fanout);
					
					// add the f edges.
					for (int f = 0; f < nextOnes.size(); f++) {
						int cap = (int)(rnd.nextDouble()*(maxCapacity - minCapacity));
						EdgeInfo ei = new EdgeInfo (id, nextOnes.get(f), minCapacity + cap);
						edges.add(ei);
					}
					
					id++;
				}
			}
			
			// decreasing set now.
			for (int i = L/2; i < L; i++) {
				int id = firstVertex(L, K, i);   // find first vertex
				last = lastVertex (L, K, i);
				while (id <= last) {
					nextOnes.clear();
					int nextFirst = firstVertex(L, K, i-1);
					int nextLast = lastVertex (L, K, i-1);
					
					// select set of f vertices from the next layer [except for sink]
					int t = -1;
					do {
						t = nextFirst + (int)(rnd.nextDouble () * (nextLast - nextFirst + 1));
						if (nextOnes.contains(t)) {
							continue;
						}
						nextOnes.add(t);
					} while (nextOnes.size() != fanout);
					
					// add the f edges.
					for (int f = 0; f < nextOnes.size(); f++) {
						int cap = (int)(rnd.nextDouble()*(maxCapacity - minCapacity));
						EdgeInfo ei = new EdgeInfo (id, nextOnes.get(f), minCapacity + cap);
						edges.add(ei);
					}
					
					id++;
				}
			}
			
		}
		
		// last one all get added to the sink.
		// add edges from source to first K nodes.
		last = lastVertex(L, K, L-1);
		for (int f = firstVertex(L, K, L-1); f <= last; f++) {
			int cap = (int)(rnd.nextDouble()*(maxCapacity - minCapacity));
			EdgeInfo ei = new EdgeInfo (f, vertices.length-1, minCapacity + cap);
			edges.add(ei);
		}
		
		
		return edges;
	}

}
