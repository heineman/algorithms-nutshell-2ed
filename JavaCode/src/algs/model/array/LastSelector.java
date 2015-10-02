package algs.model.array;

/**
 * Quicksort selector during partition that selects rightmost element.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LastSelector implements IPivotIndex {
	
	/**
	 * Select rightmost index of ar[left,right] as the pivot index. 
	 */
	public int selectPivotIndex(Comparable<?>[] ar, int left, int right) {
		return right;
	}

}
