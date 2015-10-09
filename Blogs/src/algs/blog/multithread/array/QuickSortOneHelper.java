package algs.blog.multithread.array;

import algs.model.array.IPivotIndex;

/**
 * Provide class to experiment with 'selectPivotIndex' and different minimum
 * size problems for which InsertionSort is used instead.
 * <p>
 * Note that race conditions may exist on helpRequested, but they are harmless
 * because they only cause qsort to miss an opportunity to launch a separate
 * thread in the unlikely event that a thread is ending just as the primary
 * code inspects the value of helpRequested. We can be assured of this behavior
 * since only the primary thread sets the value of helpRequested to true and 
 * only the secondary helper thread sets the value to false. 
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class QuickSortOneHelper<E extends Comparable<E>> {

	/** Elements to be sorted. */
	final E ar[];

	/** Pivot index method. */
	IPivotIndex pi;

	/** Fix work size below which we use separate thread. */
	int threshold;

	/** Set ratio to use (defaults to 5). */
	int ratio = 5;
	
	/** Determines if help has been requested. */
	volatile transient boolean helpRequested = false;

	public QuickSortOneHelper (E ar[]) {
		this.ar = ar;

		// defaults
		threshold = ar.length/ratio;
	}

	/**
	 * Set the threshold ratio below which a separate thread is used
	 * on problem sizes.
	 * 
	 * @param t   new threshold
	 */
	public void setThresholdRatio (int r) {
		this.ratio = r;
		threshold = ar.length/ratio;
	}

	/** Determine the method used to select a pivot index. */
	public void setPivotMethod (IPivotIndex ipi) {
		this.pi = ipi;
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
		E tmp;

		// move pivot to the end of the array
		if (pivotIndex != right) {
			tmp = ar[right];
			ar[right] = ar[pivotIndex];
			ar[pivotIndex] = tmp;
		}
		
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
	 * Sort using quicksort method with separate helper thread.
	 * <p>
	 * 
	 * @param left     The left-bounds within which to sort (0 <= left < ar.length)
	 * @param right    The right-bounds within which to sort (0 <= right < ar.length)
	 */
	public void qsort (final int left, final int right) {
		qsort2 (left, right);
		
		// wait until helper is done (if it is still executing).
		while (helpRequested) {	}
	}
	
	/**
	 * Sort using quicksort method with separate helper thread.
	 * <p>
	 * If subarrays to be sorted are smaller in size than 'minSize' then
	 * use Insertion Sort as coded in {@link #insertion(int, int)}.
	 * 
	 * @param left     The left-bounds within which to sort (0 <= left < ar.length)
	 * @param right    The right-bounds within which to sort (0 <= right < ar.length)
	 */
	public void qsort2 (final int left, final int right) {
		if (right <= left) { return; }

		// partition
		int p = pi.selectPivotIndex (ar, left, right);
		final int pivotIndex = partition (left, right, p);

		// Is helper already working? or is the problem too big? Continue with recursion
		int n = pivotIndex - left;
		if (helpRequested || n >= threshold) {
			qsort2 (left, pivotIndex-1);
		} else {
			helpRequested = true;
			
			// complete in separate thread
			new Thread () {
				public void run () {
					qsort2 (left, pivotIndex - 1);
					helpRequested = false;
				}
			}.start();
		}

		// Is helper already working? or is the problem too big? Continue with recursion
		n = right - pivotIndex;
		if (helpRequested || n >= threshold) {
			qsort2 (pivotIndex+1, right);
		} else {
			// complete in separate thread
			helpRequested = true;
			
			new Thread () {
				public void run () {
					qsort2 (pivotIndex+1, right);
					helpRequested = false;
				}
			}.start();
		}
	}

}
