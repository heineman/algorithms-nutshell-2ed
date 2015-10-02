package algs.model.array;

/**
 * Interface describing behavior for selecting a pivotIndex for partition.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IPivotIndex {
	
	/** 
	 * Select a pivot from the given subarray ar[left,right].
	 * 
	 * @param ar        base array of elements.
	 * @param left      left index of sub-array
	 * @param right     right index of sub-array
	 * @return          an integer index value in the range [left,right]
	 *                  around which to pivot.
	 */
	int selectPivotIndex(Comparable<?> ar[], int left, int right);
}
