package algs.model.array;

/**
 * Select median of first/middle/last.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MedianSelector implements IPivotIndex {

	/**
	 * Compute median of three elements, ar[left], ar[mid], ar[right] to
	 * use as the pivot. 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int selectPivotIndex(Comparable[] ar, int left, int right) {
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
}
