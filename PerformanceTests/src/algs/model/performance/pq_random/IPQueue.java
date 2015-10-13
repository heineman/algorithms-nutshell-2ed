package algs.model.performance.pq_random;

public interface IPQueue {
	
	/** Insert item into PQ with given priority. */
	void insert (double priority, int item);
	
	/** Remove element with minimum priority. */
	int minimum ();
	
}
