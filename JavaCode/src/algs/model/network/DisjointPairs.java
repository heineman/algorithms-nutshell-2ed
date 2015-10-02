package algs.model.network;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * Helper class to record pairing information for the Maximum Matching problem.
 * <p>
 * Ensures that sets A and B are disjoint while they are being created.
 * <p> 
 * Once the network is generated, no more pairings can be added. 
 *
 * @param <A>  Type of object in set A
 * @param <B>  Type of object in set B
 */
public class DisjointPairs<A,B> {

	/** Elements belong to A set. */
	Hashtable<A, Integer> setA = new Hashtable<A,Integer>();
	
	/** Reverse map for lookup into A. */
	Hashtable<Integer, A> mapA = new Hashtable<Integer,A>();
	
	/** Elements belong to B set. */
	Hashtable<B, Integer> setB = new Hashtable<B,Integer>();
	
	/** Reverse map for lookup into B. */
	Hashtable<Integer, B> mapB = new Hashtable<Integer,B>();

	/**
	 * Known valid edges: If map to true then we are a solution pair, otherwise 
	 * the edge is a fictitious one meant to model the problem.
	 */
	Hashtable<EdgeInfo, Boolean> pairings = new Hashtable<EdgeInfo, Boolean>();
	
	/** Counter for vertices. */
	int ctr = 0;
	
	/**
	 * Add a pairing (a,b) to the set.
	 * <p>
	 * If element a is an element of B or b is an element of A then this throws an 
	 * IllegalArgumnetException. 
	 * <p>
	 * Attempts to add the same pairing (which already exists) have no effect and false is
	 * returned when such an attempt is detected.
	 * 
	 * @param a    first element in paired tuple (a,b)
	 * @param b    second element in paired tuple (a,b)
	 * @return     true if able to add this disjoint pairing; false otherwise.
	 */
	public boolean add (A a, B b) {
		if (setB.containsKey(a) || setA.containsKey(b)) {
			throw new IllegalArgumentException ("Non Bipartite Graph would be formed by adding edge (" + a + "," + b + ")");
		}
		
		if (!setA.containsKey(a)) {
			setA.put(a, ++ctr);
			mapA.put(ctr, a);
		}
		if (!setB.containsKey(b)) {
			setB.put(b, ++ctr);
			mapB.put(ctr, b);
		}
		
		EdgeInfo ei = new EdgeInfo(setA.get(a), setB.get(b), 1);
		
		if (pairings.containsKey(ei)) { return false; }
		pairings.put(ei, true);
		return true;
	}
	
	/**
	 * Return an array-based implementation of FlowNetwork based upon information contained within
	 * the bipartite graph.
	 * 
	 * 0th vertex is the new source.
	 * @return iterator of {@link EdgeInfo} containing information about the Flow network.
	 */
	public Iterator<EdgeInfo> getEdges () {
		
		// add edges from new source to all in setA.
		for (Integer id : setA.values()) {
			EdgeInfo ei = new EdgeInfo (0, id, 1);
			pairings.put(ei, false);
		}
		
		// add edges from all in setB to new target.
		for (Integer id : setB.values()) {
			EdgeInfo ei = new EdgeInfo (id, ctr+1, 1);
			pairings.put(ei, false);
		}
		
		return pairings.keySet().iterator();
	}
	
	public int sourceIndex() {
		return 0;
	}
	
	public int sinkIndex() { 
		return ctr+1;
	}
	
	public int numVertices() {
		return ctr+2;
	}

	public void output() {
		for (EdgeInfo ei : pairings.keySet()) {
			// make sure we are a valid pairing (not fictitious one) and there is
			// a flow on the edge. This represents a matching.
			if (pairings.get(ei) && ei.flow > 0) {
				System.out.println (mapA.get(ei.start) + " <-> " + mapB.get(ei.end));
			}
		}
		
	}
}
