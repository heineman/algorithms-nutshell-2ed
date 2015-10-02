package algs.model.array;

/**
 * Quicksort selector during partition that selects leftmost element.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class FirstSelector implements IPivotIndex {

	/**
	 * Select leftmost index of ar[left,right] as the pivot index. 
	 */
	public int selectPivotIndex(Comparable<?>[] ar, int left, int right) {
		return left;
	}

}
