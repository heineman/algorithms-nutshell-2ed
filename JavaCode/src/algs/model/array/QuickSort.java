package algs.model.array;

/**
 * Provide class to experiment with 'selectPivotIndex' and different minimum
 * size problems for which InsertionSort is used instead.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class QuickSort<E extends Comparable<E>> {
	
	/** Elements to be sorted. */
	final Comparable<E>[] ar;
	
	/** Pivot index method. */
	IPivotIndex pi;

	/** Problem size below which to use insertion sort. */
	int minSize = 0;
	
	public QuickSort (Comparable<E> ar[]) {
		this.ar = ar;
	}
	
	/** 
	 * Set the minimum problem size at and below which InsertionSort is used. 
	 *
	 * @param ms   Minimum problem size below which InsertionSort is used.
	 */
	public void setMinimumSize (int ms) {
		this.minSize = ms;
	}
	
	/** 
	 * Determine the method used to select a pivot index. 
	 *
	 * @param ipi    pivot method to use.
	 */
	public void setPivotMethod (IPivotIndex ipi) {
		this.pi = ipi;
	}
	
	/**
	 * In linear time, group an array into two parts, those less than a certain value 
	 * (left), and those greater than or equal to a certain value (right).
	 * 
	 * @param left         lower bound index position    
	 * @param right        upper bound index position
	 * @param pivotIndex   index around which the partition is being made.
	 * @return             location of the pivot index properly positioned.
	 */
	@SuppressWarnings("unchecked")
	public int partition (int left, int right, int pivotIndex) {
		Comparable<E> pivot = ar[pivotIndex];
		Comparable<E> tmp;
		
		// move pivot to the end of the array
		tmp = ar[right];
		ar[right] = ar[pivotIndex];
		ar[pivotIndex] = tmp;
		
		int store = left;
		for (int idx = left; idx < right; idx++) {
			if (ar[idx].compareTo((E)pivot) <= 0) {
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
	
	/** Private code to use InsertionSort on the range ar[low,high]. */
	@SuppressWarnings("unchecked")
	private void insertion (int low, int high) {
		if (high <= low) { return; }
		for (int t = low; t < high; t++) {
			for (int i = t+1; i <= high; i++) {
				if (ar[i].compareTo((E)ar[t]) < 0) {
					Comparable<E> c = ar[t];
					ar[t] = ar[i];
					ar[i] = c;
				}
			}
		}
	}
	
	/**
	 * Sort using quicksort method.
	 * <p>
	 * If subarrays to be sorted are smaller in size than 'minSize' then
	 * use Insertion Sort as coded in {@link #insertion(int, int)}.
	 * 
	 * @param left     The left-bounds within which to sort (0 &le; left &lt; ar.length)
	 * @param right    The right-bounds within which to sort (0 &le; right &lt; ar.length)
	 */
	public void qsort (int left, int right) {
		if (right <= left) { return; }
		
		// partition
		int pivotIndex = pi.selectPivotIndex (ar, left, right);
		pivotIndex = partition (left, right, pivotIndex);
		
		if (pivotIndex-1-left <= minSize) {
			insertion (left, pivotIndex-1);
		} else {
			qsort (left, pivotIndex-1);
		}
		if (right - pivotIndex - 1 <= minSize) {
			insertion (pivotIndex+1, right);
		} else {
			qsort (pivotIndex+1, right);
		}
	}

}
