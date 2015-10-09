package algs.blog.network;


import java.math.BigInteger;


/**
 * Take the sample Flow Network from figure 8-3 and compute exhaustively all possible
 * flow configurations to find one that satisfies all constraints and which represents
 * a maximum flow possible through the network. It is assumed that [0] is the source 
 * and [n-1] is the sink.
 */
public class BruteForce {

	/** Figure 6-23 (p.201) of Ahuja's network flow book */
	static int[][] network2 = {
		{0, 4, 3, 0, 0, 0, 0},
		{4, 0, 0, 2, 3, 0, 0},
		{3, 0, 0, 2, 0, 0, 0},
		{0, 2, 2, 0, 0, 5, 0},
		{0, 3, 0, 0, 0, 4, 0},
		{0, 0, 0, 5, 4, 0, 8},
		{0, 0, 0, 0, 0, 8, 0}
	};
	
	/** Figure 8-2. */
	static int[][] network = {
		{0, 10, 5, 4, 0, 0, 0},
		{0, 0 , 3, 0, 5, 0, 0},
		{0, 0 , 0, 5, 2, 0, 0},
		{0, 0 , 0, 0, 0, 8, 0},
		{0, 0 , 4, 0, 0, 0, 7},
		{0, 0 , 3, 0, 0, 0, 11},
		{0, 0,  0, 0, 0, 0, 0}
	};
	
	/** Figure 8-3. */
	static int[][] network1 = {
		{0, 3, 2, 0, 0, 0},
		{0, 0, 0, 2, 2, 0},
		{0, 0, 0, 2, 3, 0},
		{0, 0, 0, 0, 0, 3},
		{0, 0, 0, 0, 0, 2},
		{0, 0, 0, 0, 0, 0}
	};
	
	/** Figure 8-7. */
	static int[][] network3 = {
		{0, 300, 300, 0, 0, 0},
		{0, 0, 0, 200, 200, 0},
		{0, 0, 0, 280, 350, 0},
		{0, 0, 0, 0, 0, 600},
		{0, 0, 0, 0, 0, 600},
		{0, 0, 0, 0, 0, 0}
	};
	
	static int n;
	
	/** record flows out of vertex i into j. */
	static int[][] outgoing;
	
	/** Array[][3] with [][0]=source vertex, [][1]=target, [][2] = flow over that edge. */
	static int numFlows = 0;
	static int[][] flows;
	
	/** Counter. */
	static int idx = 0;
	
	/** How many possible. */
	static BigInteger numPossible;
	
	/** How many at a time to measure. */
	static final int chunk = 12000000;
	
	/** Last timestamp. */
	static long timestamp;
	
	/** How many more to do. */
	static BigInteger numLeft;
	
	/** Try a new configuration (return false when no more). */
	private static boolean tryNewConfiguration() {
		
		for (int i = 0; i < numFlows; i++) {
			// we can increment by one. Update global state of incoming/outgoing
			if (++flows[i][2] <= network[flows[i][0]][flows[i][1]]) {
				
				outgoing[flows[i][0]][flows[i][1]] = flows[i][2];
				return true;
			}
			
			// reset and try to advance next one...
			flows[i][2] = 0; 
			outgoing[flows[i][0]][flows[i][1]] = 0;
		}
		
		// if we get here, then we have tried all possible (like an odometer resetting)
		return false;
	}
	
	private static StringBuilder recordConfiguration() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numFlows; i++) {
			if (flows[i][2] > 0) {
				sb.append("(").append(flows[i][0]).append(',').append(flows[i][1]).append(")=").append(flows[i][2]).append(',');
			}
		}
		
		sb.delete(sb.length()-1, sb.length());
		return sb;
	}
	
	/** Return -1 if some flow constraint not met; otherwise return the max flow. */
	private static int computeMaxFlow() {
		int sum;
		
		// for each vertex (other than src/sink), make sure that SUM(in) = SUM(out)
		for (int i = 1; i < n-1; i++) {
			sum = 0;
			
			// incoming into i
			for (int j = 0; j < n; j++) {
				sum = sum + outgoing[j][i] - outgoing[i][j];
			}
			
			// doesn't satisfy property
			if (sum != 0) { return -1; }
		}
		
		// compute sum coming out of the source.
		sum = 0;
		for (int j = 0; j < n; j++) {
			sum = sum + outgoing[0][j];
		}
		return sum;
	}
	
	public static void main(String[] args) {
		/** Compute maximum possible in the network flowing from the source. */
		int max = 0;
		n = network[0].length;
		for (int i = 0; i < n; i++) { max = Math.max(max, network[0][i]); }
		
		outgoing = new int[n][n];
		
		// try to place amounts of [0, max] on all available edges, exhaustively.
		// Record all potential flows within the 'flows' array
		flows = new int[n*n][3];
		numPossible = BigInteger.ONE;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) continue;
				
				if (network[i][j] >0) {
					flows[numFlows][0] = i;
					flows[numFlows][1] = j;
					numPossible = numPossible.multiply(new BigInteger("" + (1+network[i][j])));
					
					numFlows++;
				}
			}
		}
		
		System.out.println(numPossible + " configurations to try...");
		numLeft = numPossible;
		int maxFlow = 0;
		StringBuilder record = new StringBuilder();
		timestamp = System.currentTimeMillis();
		while (true) {
			if (!tryNewConfiguration()) { break; }
			int flow = computeMaxFlow();
			
			if (idx++ == chunk) {
				long now = System.currentTimeMillis();
				long delta = now - timestamp;
				timestamp = now;
				
				idx = 0;
				numLeft = numLeft.subtract(new BigInteger("" + chunk));
				double num = numLeft.doubleValue();
				double denom = numPossible.doubleValue();
				int fract = (int) (100 - (100*num/denom));
				
				System.out.println("  " + numLeft + " configurations remaining. (" + fract + "% complete) in " + delta + " ms.");
			}
			
			if (flow > maxFlow) {
				record = recordConfiguration();
				System.out.println("Found " + flow + " for " + record);
				maxFlow = flow;
			}
		}
		
		System.out.println("Max flow of " + maxFlow + " with configuration: \n" + record);
	}

}
