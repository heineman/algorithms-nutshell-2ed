package algs.model.array;

import java.util.Comparator;

import algs.model.array.IPivotIndex;

/**
 * Implement a multi-threaded QuickSort that allows any number of helper threads
 * to help the computation.
 * <p>
 * Essentially an extension of {@link QuickSort} where the array contains objects
 * that, by default, do not implement {@link Comparable} but there is a method
 * which can be used to compare two elements. This class was designed to let us
 * investigate the ability to use {@link QuickSort} within {@link algs.model.problems.convexhull.andrew.ConvexHullScan}
 * <p>
 * Note that {@link IPivotIndex} was mistakenly specified to take an array of {@link Comparable}
 * so we couldn't use this interface. 
 * 
 * @param E   base element for the set.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class QuickSortExternal<E> {

	/**
	 * Helper interface that does not rely on Comparable.
	 */
	public interface PivotIndex<E> {
		/** 
		 * Select a pivot from the given subarray ar[left,right].
		 * 
		 * @param ar        base array of elements.
		 * @param left      left index of sub-array
		 * @param right     right index of sub-array
		 * @return          an integer index value in the range [left,right]
		 *                  around which to pivot.
		 */
		int selectPivotIndex(E ar[], int left, int right);
	}
	
	/** Elements to be sorted. */
	final E[] ar;

	/** Comparing method to use. */
	Comparator<E> comparator;
	
	/** Pivot index method. */
	QuickSortExternal.PivotIndex<E> pi;

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
	 * Construct an instance to solve quicksort and initialize the threshold
	 * to be 20% of the original array size.
	 * <p>
	 * @param ar    array to be sorted.
	 * @param comp  comparison function to use
	 */
	public QuickSortExternal (E ar[], Comparator<E> comp) {
		this.ar = ar;
		this.comparator = comp;

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
	 *
	 * @param ipi    method for selecting pivot.
	 */
	public void setPivotMethod (QuickSortExternal.PivotIndex<E> ipi) {
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
			if (comparator.compare(ar[idx], pivot) <= 0) {
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
	 * Single-thread sort using quicksort method.
	 * <p>
	 * @param left     The left-bounds within which to sort (0 &le; left &lt; ar.length)
	 * @param right    The right-bounds within which to sort (0 &le; right &lt; ar.length)
	 */
	public void qsortN (int left, int right) {
		if (right <= left) { return; }

		// partition
		int pivotIndex = pi.selectPivotIndex (ar, left, right);
		pivotIndex = partition (left, right, pivotIndex);

		qsortN (left, pivotIndex-1);
		qsortN (pivotIndex+1, right);		
	}

	/**
	 * Multi-threaded quicksort method entry point.
	 * 
	 * @param left     The left-bounds within which to sort (0 &le; left &lt; ar.length)
	 * @param right    The right-bounds within which to sort (0 &le; right &lt; ar.length)
	 */
	private void qsort2 (final int left, final int right) {
		if (right <= left) { return; }

		// partition
		int p = pi.selectPivotIndex (ar, left, right);
		final int pivotIndex = partition (left, right, p);

		// are all helper threads working OR is problem too big? 
		// Continue with recursion if so.
		int n = pivotIndex - left;
		if (helpersWorking == numThreads || n >= threshold) {
			qsort2 (left, pivotIndex-1);
		} else {
			// otherwise, complete in separate thread
			synchronized(helpRequestedMutex) {
				helpersWorking++;
			}
			
			new Thread () {
				public void run () {
					// invoke single-thread qsort
					qsortN (left, pivotIndex - 1);

					synchronized(helpRequestedMutex) {
						helpersWorking--;
					}
				}
			}.start();
		}

		// are all helper threads working OR is problem too big? 
		// Continue with recursion if so.
		n = right - pivotIndex;
		if (helpersWorking == numThreads || n >= threshold) {
			qsort2 (pivotIndex+1, right);
		} else {
			// otherwise, complete in separate thread
			synchronized(helpRequestedMutex) {
				helpersWorking++;
			}
			
			new Thread () {
				public void run () {
					// invoke single-thread qsort
					qsortN (pivotIndex+1, right);
					synchronized(helpRequestedMutex) {
						helpersWorking--;
					}
				}
			}.start();
		}
	}

	/** 
	 * Return a last selector to use. 
	 *
	 * @return select for last element.
	 */
	public PivotIndex<E> lastSelector() {
		return new LastSelector();
	}
	
	/** 
	 * Return a first selector to use. 
	 * 
	 * @return select for first element.
	 */

	public PivotIndex<E> firstSelector() {
		return new FirstSelector();
	}
	
	/**
	 * Return a random selector to use. 
	 *
	 * @return select for random element.
	 */
	public PivotIndex<E> randomSelector() {
		return new RandomSelector();
	}
	
	/**
	 * Helper class to support the last selector for quicksort. 
	 */
	public class LastSelector implements PivotIndex<E> {
		/**
		 * Select rightmost index of ar[left,right] as the pivot index. 
		 */
		public int selectPivotIndex(Object[] ar, int left, int right) {
			return right;
		}
	}
	
	/**
	 * Helper class to support the first selector for quicksort. 
	 */
	public class FirstSelector implements PivotIndex<E> {
		/**
		 * Select leftmost index of ar[left,right] as the pivot index. 
		 */
		public int selectPivotIndex(Object[] ar, int left, int right) {
			return left;
		}
	}
	
	/**
	 * Helper class to support the random selector for quicksort. 
	 */
	public class RandomSelector implements PivotIndex<E> {
		/**
		 * Select random index from the ar[left,right] as the pivot element. 
		 */
		public int selectPivotIndex(E[] ar, int left, int right) {
			return (int) left + (int) ((right - left + 1) * Math.random());
		}
	}
}
