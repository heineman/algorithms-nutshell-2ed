package algs.model.array;

import java.util.Comparator;

/**
 * Helper class to locate selected values from an Array of Comparable.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Selection {

	/**
	 * Swap the two locations. 
	 * <p>
	 * Does nothing if pos1 == pos2
	 * 
	 * @param ar    An array of objects
	 * @param pos1  position of first element to swap
	 * @param pos2  position of second element to swap
	 * @exception   NullPointerException if ar is null
	 * @exception   ArrayIndexOutOfBoundsException if either pos1 or pos2 is invalid
	 */
	public static void swap (Object[]ar, int pos1, int pos2) {
		if (pos1 == pos2) { return; }
		
		Object tmp = ar[pos1];
		ar[pos1] = ar[pos2];
		ar[pos2] = tmp;
	}
	
	/**
	 * Locate the minimum value from an array of {@link Comparable} objects.
	 * 
	 * @param ar    An array of {@link Comparable} objects
	 * @return      The minimum element from the array
	 * @exception   NullPointerException if ar is null
	 * @exception   ArrayIndexOutOfBoundsException if ar is an empty array
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Comparable<?> min (Comparable<?>[]ar) {
		Comparable ret = ar[0];
		for (int i = 1; i < ar.length; i++) {
			if (ret.compareTo(ar[i]) > 0) {
				ret = ar[i];
			}
		}
		
		return ret;
	}
	
	/**
	 * Locate the maximum value from an array of Comparables.
	 * 
	 * @param ar    An array of Comparable objects
	 * @return      The maximum element from the array
	 * @exception   NullPointerException if ar is null or references an empty array.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Comparable<?> max (Comparable<?>[]ar) {
		Comparable ret = ar[0];
		for (int i = 1; i < ar.length; i++) {
			if (ret.compareTo(ar[i]) < 0) {
				ret = ar[i];
			}
		}
		
		return ret;
	}
	
	/**
	 * In linear time, group an array into two parts, those less than a certain value 
	 * (left), and those greater than or equal to a certain value (right).
	 * 
	 * @param ar           An array of Comparable objects
	 * @param left         lower bound index position    
	 * @param right        upper bound index position
	 * @param pivotIndex   index around which the partition is being made.
	 * @return             location of the pivot index properly positioned.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static int partition (Comparable ar[], int left, int right, int pivotIndex) {
		Comparable pivot = ar[pivotIndex];
		swap (ar, right, pivotIndex);         // move pivot to the end of the array
		
		int store = left;
		for (int idx = left; idx < right; idx++) {
			if (ar[idx].compareTo(pivot) <= 0) {
				swap (ar, idx, store);
				store++;
			}
		}
		
		swap (ar, right, store);              // move pivot to its final place
		return store;		
	}
	
	/**
	 * In linear time, group an array into two parts, those less than a certain value 
	 * (left), and those greater than or equal to a certain value (right).
	 * 
	 * @param ar           An array of Comparable objects
	 * @param left         lower bound index position (0 &le; left &lt; ar.length)
	 * @param right        upper bound index position (0 &le; left &lt; ar.length)
	 * @param pivotIndex   index around which the partition is being made.
	 * @param comparator   Externalize the comparison of two objects into this method. 
	 * @return             location of the pivot index properly positioned.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static int partition (Object ar[], int left, int right, int pivotIndex, Comparator comparator) {
		Object pivot = ar[pivotIndex];
		swap (ar, right, pivotIndex);         // move pivot to the end of the array
		
		int store = left;
		for (int idx = left; idx < right; idx++) {
			if (comparator.compare(ar[idx], pivot) <= 0) {
				swap (ar, idx, store);
				store++;
			}
		}
		
		swap (ar, right, store);              // move pivot to its final place
		return store;		
	}
	
	/**
	 * Select an appropriate pivot within the [left, right] range.
	 * 
	 * For now, pick 'middle' of three values [left, mid, right]
	 * 
	 * @param ar       Array of Comparable objects
	 * @param left     The left-bounds within which to search (0 &le; left &lt; ar.length)
	 * @param right    The right-bounds within which to search (0 &le; right &lt; ar.length)
	 * @return         index of the pivot within the ar[] array (0 &le; index &lt; ar.length)
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static int selectPivotIndex (Comparable ar[], int left, int right) {
		int midIndex = (left+right)/2;
		
		int lowIndex = left;
		
		if (ar[lowIndex].compareTo(ar[midIndex]) >= 0) {
			lowIndex = midIndex;
			midIndex = left;
		} 
		
		// when we get here, we know that ar[lowIndex] < ar[midIndex]
		
		// select middle of [low,mid] and ar[right]
		if (ar[right].compareTo(ar[lowIndex]) <= 0) {
			return lowIndex;  // right .. low .. mid     so we return 
		} else if (ar[right].compareTo(ar[midIndex]) <= 0) {
			return midIndex;  // low .. mid .. right 
		}
		
		return right; // why not
	}
	
	/**
	 * Select an appropriate pivot within the [left, right] range.
	 * 
	 * For now, pick 'middle' of three values [left, mid, right]
	 * 
	 * @param ar          Array of Comparable objects
	 * @param left        The left-bounds within which to search (0 &le; left &lt; ar.length)
	 * @param right       The right-bounds within which to search (0 &le; right &lt; ar.length)
	 * @param comparator  Externalize the comparison of two objects into this method. 
	 * @return            index of the pivot within the ar[] array (0 &le; index &lt; ar.length)
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static int selectPivotIndex (Object ar[], int left, int right, Comparator comparator) {
		int midIndex = (left+right)/2;
		
		int lowIndex = left;
		
		if (comparator.compare(ar[lowIndex], ar[midIndex]) >= 0) {
			lowIndex = midIndex;
			midIndex = left;
		} 
		
		// when we get here, we know that ar[lowIndex] < ar[midIndex]
		
		// select middle of [low,mid] and ar[right]
		if (comparator.compare(ar[right], ar[lowIndex]) <= 0) {
			return lowIndex;  // right .. low .. mid     so we return 
		} else if (comparator.compare(ar[right], ar[midIndex]) <= 0) {
			return right;  // low .. right .. mid
		}
		
		return midIndex; // why not
	}
	
	/**
	 * Select the kth value in an array (1 &le; k &le; right-left+1) through recursive partitioning.
	 * 
	 * Note that ar[] is altered during the execution of this method. left must be &le; right.
	 * Built in comparator is used.
	 * 
	 * @param ar       Array of Comparable objects
	 * @param k        The position in sorted order of the desired location (1 &le; k &le; right-left+1)
	 * @param left     The left-bounds within which to search (0 &le; left &lt; ar.length)
	 * @param right    The right-bounds within which to search (0 &le; right &lt; ar.length)
	 * @return         The Comparable object which is the kth in sorted order.
	 */
	@SuppressWarnings("rawtypes")
	public static Comparable select (Comparable[]ar, int k, int left, int right) {
		do {
			int idx = selectPivotIndex (ar, left, right);
			
			int pivotIndex = partition (ar, left, right, idx);
			if (left+k-1 == pivotIndex) {
				return ar[pivotIndex];
			} 

			// continue the loop, narrowing the range as appropriate.
			if (left+k-1 < pivotIndex) {
				// we are within the left-hand side of the pivot. k can stay the same
				right = pivotIndex - 1;
			} else {
				// we are within the right-hand side of the pivot. k must decrease by 
				// the size being removed.
				k -= (pivotIndex-left+1);
				left = pivotIndex + 1;
			}
		} while (true);
	}
	
	/**
	 * Select the kth value in an array (1 &le; k &le; right-left+1) through recursive partitioning.
	 * 
	 * Note that ar[] is altered during the execution of this method. left must be &le; right.
	 * Externally provided comparator is used.
	 * 
	 * @param ar          Array of objects
	 * @param k           The position in sorted order of the desired location (1 &le; k &le; right-left+1)
	 * @param left        The left-bounds within which to search (0 &le; left &lt; ar.length)
	 * @param right       The right-bounds within which to search (0 &le; right &lt; ar.length)
	 * @param comparator  Externalize the comparison of two objects into this method.
	 * @return            The Comparable object which is the kth in sorted order.
	 */
	@SuppressWarnings("rawtypes")
	public static Object select (Object[]ar, int k, int left, int right, Comparator comparator) {
		do {
			int idx = selectPivotIndex (ar, left, right, comparator);
			int pivotIndex = partition (ar, left, right, idx, comparator);

			if (left+k-1 == pivotIndex) {
				return ar[pivotIndex];
			} 

			// continue the loop, narrowing the range as appropriate.
			if (left+k-1 < pivotIndex) {
				// we are within the left-hand side of the pivot. k can stay the same
				right = pivotIndex - 1;
			} else {
				// we are within the right-hand side of the pivot. k must decrease by 
				// the size being removed.
				k -= (pivotIndex-left+1);
				left = pivotIndex + 1;				
			}
		} while (true);
	}
	
	/**
	 * Sort using Quicksort method.
	 * 
	 * left must be strictly less than right.
	 * 
	 * @param ar       Array of Comparable objects
	 * @param left     The left-bounds within which to sort (0 &le; left &lt; ar.length)
	 * @param right    The right-bounds within which to sort (0 &le; right &lt; ar.length)
	 * @return false if invalid arguments passed in; true otherwise on success.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean qsort (Comparable[]ar, int left, int right) {
		if (right <= left) { return false; }
		
		// partition
		int pivotIndex = selectPivotIndex (ar, left, right);
		pivotIndex = partition (ar, left, right, pivotIndex);
		
		qsort (ar, left, pivotIndex-1);
		qsort (ar, pivotIndex+1, right);
		return true;
	}
	
	/**
	 * Sort using Quicksort method.
	 * 
	 * left must be strictly less than right.
	 * 
	 * @param ar          Array of Comparable objects
	 * @param left        The left-bounds within which to sort (0 &le; left &lt; ar.length)
	 * @param right       The right-bounds within which to sort (0 &le; right &lt; ar.length)
	 * @param comparator  Externalize the comparison of two objects into this method.
	 * @return false if invalid arguments passed in; true otherwise on success.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean qsort (Object[]ar, int left, int right, Comparator comparator) {
		if (right <= left) { return false; }
		
		// partition
		int pivotIndex = selectPivotIndex (ar, left, right, comparator);
		pivotIndex = partition (ar, left, right, pivotIndex, comparator);
		
		qsort (ar, left, pivotIndex-1, comparator);
		qsort (ar, pivotIndex+1, right, comparator);
		
		return true;
	}
}
