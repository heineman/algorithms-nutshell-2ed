package algs.chapter11.table4;

/**
 * Compute Knuth's walk for n-queen problem estimation.
 * <p>
 * Executes 100 individual queries: most will be zero.
 * 
 * @author George Heineman
 */
public class SingleQuery {

	/** Generate table. */
	public static void main (String []args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(i + ": " + estimate(19));
		}
	}
	
	public static long estimate(int n) {
		Board b = new Board(n);

		int r = 0;
		long lastEstimate = 1;
		while (r < n) {
			int numChildren = b.numChildren (r);

			// no more to go, so no solution found.
			if (!b.randomNextBoard (r)) {
				lastEstimate = 0;
				break; 
			}

			// compute estimate based on ongoing tally and advance
			lastEstimate = lastEstimate*numChildren;
			r++;
		}
		
		return lastEstimate;
	}
	
}
