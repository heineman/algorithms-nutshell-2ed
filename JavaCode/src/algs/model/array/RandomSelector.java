package algs.model.array;

/**
 * Quicksort selector during partition that selects random element.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class RandomSelector implements IPivotIndex {
	
	/**
	 * Select random index from the   ar[left,right] as the pivot element. 
	 */
	public int selectPivotIndex(Comparable<?>[] ar, int left, int right) {
		return (int) left + (int) ((right - left + 1) * Math.random());
	}

}
