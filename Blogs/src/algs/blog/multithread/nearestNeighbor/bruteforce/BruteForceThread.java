package algs.blog.multithread.nearestNeighbor.bruteforce;

public class BruteForceThread extends Thread {
	/** Input points are converted into raw format. */
	final double[][] points;
	
	/** Target point. */
	final double []target;
	
	/** Start at given offset. */
	final int offset;
	
	/** Number to compute. */
	final int len;
	
	/** Shortest distance. */
	double best;
	
	/** Index of shortest point. */
	int bestIndex;
	
	/** Construct the thread with input points and target. */
	public BruteForceThread (double[][] points, double[] target, 
			int offset, int len) {
		this.points = points;
		this.target = target;
		this.offset = offset;
		this.len = len;
	}
	
	/**
	 * Process points in points[offset, offset+len) and update best and bestIndex
	 * with computed results.
	 */
	public void run () {
		double dist = Integer.MAX_VALUE;
		int idx = -1;
		int targetLen = target.length;
		int end = offset + len;
		
		for (int i = offset; i < end; i++) {
			double d = 0;
			
			for (int j = 0; j < targetLen; j++) {
				double delta = target[j] - points[i][j];
				d +=  delta*delta;
			}
			d = Math.sqrt(d);
			
			if (d < dist) {
				dist = d;
				idx = i;
			}
		}
	
		// set thread values with results of search.
		best = dist;
		bestIndex = idx;
	}
}
