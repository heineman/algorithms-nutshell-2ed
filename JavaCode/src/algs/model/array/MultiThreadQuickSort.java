package algs.model.array;


/**
 * Implement a multi-threaded QuickSort that allows any number of helper threads
 * to help the computation.
 * 
 * <p>Given a specific threshold size, {@link #threshold}, this implementation will
 * endeavor to spawn a new thread to solve sorting sub-problems less than this
 * threshold size, should there be more threads available. The total number of 
 * threads will never exceed {@link #numThreads}.</p>
 * 
 * <p>A mutex, {@link #helpRequestedMutex}, guards the number of helpers working. 
 * We assume that all of the working threads complete successfully, which enables 
 * the {@link #qsort(int, int)} method to delay its return until 
 * helpersWorking == 0.</p>
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class MultiThreadQuickSort<E extends Comparable<E>> {

	/** Elements to be sorted. */
	final E[] ar;

	/** Pivot index method. */
	IPivotIndex pi;

	/** Fix work size below which we use separate thread. */
	int threshold;

	/** Set number of helper threads to use. Default is one helper. */
	int numThreads = 1;

	/** Records the number of helper threads actively working. */
	volatile transient int helpersWorking;

	/** 
	 * Mutex for accessing helpRequested. To avoid delaying the primary
	 * computation, this mutex is only accessed within the helper threads.
	 */
	volatile transient Object helpRequestedMutex = new Object();

	/** Set ratio to use (defaults to 5). */
	int ratio = 5;
	
	/**
	 * Construct an instance to solve Quicksort and initialize the threshold
	 * to be 20% of the original array size.
	 * 
	 * @param ar    array to be sorted.
	 */
	public MultiThreadQuickSort (E ar[]) {
		this.ar = ar;

		// defaults
		threshold = ar.length/ratio;
		helpersWorking = 0;
	}

	/**
	 * Set the threshold ratio below which a separate thread is used
	 * on problem sizes.
	 * 
	 * @param r   new threshold ratio
	 */
	public void setThresholdRatio (int r) {
		this.ratio = r;
		threshold = ar.length/ratio;
	}

	/** 
	 * Determine the method used to select a pivot index.
	 * @param ipi     method to use for selecting pivot. 
	 */
	public void setPivotMethod (IPivotIndex ipi) {
		this.pi = ipi;
	}

	/**
	 * Set number of helper threads (may be zero).
	 * <p>
	 * Behavior is unspecified if this method is invoked while the qsort 
	 * method is in progress.
	 * 
	 * @param nht    Number of helper threads to use
	 */
	public void setNumberHelperThreads (int nht) {
		numThreads = nht;
	}

	/**
	 * In linear time, group an array into two parts, those less than or equal
	 * to a certain value (left), and those greater than a certain value (right).
	 * 
	 * @param left         lower bound index position    
	 * @param right        upper bound index position
	 * @param pivotIndex   index around which the partition is being made.
	 * @return             location of the pivot index properly positioned.
	 */
	public int partition (int left, int right, int pivotIndex) {
		E pivot = ar[pivotIndex];
		
		// move pivot to the end of the array
		E tmp = ar[right];
		ar[right] = ar[pivotIndex];
		ar[pivotIndex] = tmp;

		int store = left;
		for (int idx = left; idx < right; idx++) {
			if (ar[idx].compareTo(pivot) <= 0) {
				tmp = ar[idx];
				ar[idx] = ar[store];
				ar[store] = tmp;
				store++;
			}
		}

		tmp = ar[right];
		ar[right] = ar[store];
		ar[store] = tmp;
		return store;		
	}

	/**
	 * Sort using quicksort method.
	 * 
	 * @param left     The left-bounds within which to sort (0 &le; left &lt; ar.length)
	 * @param right    The right-bounds within which to sort (0 &le; right &lt; ar.length)
	 */
	public void qsort (final int left, final int right) {
		qsort2(left, right);

		// wait until all helper threads have processed. This condition will
		// work so long as threads are not interrupted.
		while (helpersWorking > 0) { }
	}

	/**
	 * Single-thread sort using Quicksort method.
	 * 
	 * @param left     The left-bounds within which to sort (0 &le; left &lt; ar.length)
	 * @param right    The right-bounds within which to sort (0 &le; right &lt; ar.length)
	 */
	public void qsortSingle (int left, int right) {
		if (right <= left) { return; }

		// partition
		int pivotIndex = pi.selectPivotIndex (ar, left, right);
		pivotIndex = partition (left, right, pivotIndex);

		qsortSingle (left, pivotIndex-1);
		qsortSingle (pivotIndex+1, right);		
	}

	/**
	 * Helper method to spawn thread for subtask or to use existing
	 * thread if problem size is too big or if all helpers are being used.
	 * 
	 * @param left     The left-bounds within which to sort (0 &le; left &lt; ar.length)
	 * @param right    The right-bounds within which to sort (0 &le; right &lt; ar.length)
	 */
	private void qsortThread(final int left, final int right) {
		// are all helper threads working OR is problem too big? 
		// Continue with recursion if so.
		int n = right + 1 - left;
		if (helpersWorking == numThreads || n >= threshold) {
			qsort2 (left, right);
		} else {
			// otherwise, complete in separate thread
			synchronized (helpRequestedMutex) {
				helpersWorking++;
			}
			
			new Thread () {
				public void run () {
					// invoke single-thread qsort
					qsortSingle (left, right);

					synchronized (helpRequestedMutex) {
						helpersWorking--;
					}
				}
			}.start();
		}
	}
	
	/**
	 * Multi-threaded quicksort method entry point.
	 * 
	 * @param left     The left-bounds within which to sort (0 <= left < ar.length)
	 * @param right    The right-bounds within which to sort (0 <= right < ar.length)
	 */
	void qsort2 (int left, int right) {
		if (right <= left) { return; }

		// partition
		int pivotIndex = pi.selectPivotIndex (ar, left, right);
		pivotIndex = partition (left, right, pivotIndex);
		
		qsortThread (left, pivotIndex-1);
		qsortThread (pivotIndex+1,right);
	}

}
