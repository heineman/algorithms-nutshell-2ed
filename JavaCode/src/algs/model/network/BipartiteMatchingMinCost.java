package algs.model.network;

/**
 * A graph represents provide nodes that are to be matched with an equal number
 * of requirer nodes. Capacity for each edge is 1 and flow determines if match
 * was selected.
 * 
 * Enables the code to compute maxFlow/MinCost to operate immediately on 
 * Bipartite Matching problems.
 */
public class BipartiteMatchingMinCost extends FlowNetworkArray {
	
	/** Store pairing for generating proper output. */
	DisjointPairs<?,?> pr;
	
	/**
	 * Construct a Bipartite Matching problem instance from the information
	 * contained within the DisjointPairs class. 
	 * 
	 * @param pr   the set of disjoint pairs
	 */
	public BipartiteMatchingMinCost(DisjointPairs<?,?> pr) {
		super(pr.numVertices(), pr.sourceIndex(), pr.sinkIndex());
		
		this.populate(pr.getEdges());

		this.pr = pr;
	}
	
	/**
	 * Show the matching.
	 */
	public void output () {
		int maxPairs = getFlow();
		System.out.println("There are " + maxPairs + " pairs matched.");
		
		pr.output();
	}

}
