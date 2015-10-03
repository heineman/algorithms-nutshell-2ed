/**
 * @file selectKthRecursive.c      Recursive implementation to select the kth element of ar[left, right] 
 * @brief
 *  A recursive implementation to select the kth smallest element from
 *  the subarray ar[left, right].
 *
 * 
 * @author George Heineman
 * @date 6/15/08
 */

/** Code to select a pivot index around which to partition ar[left, right]. */
extern int selectPivotIndex (void **vals, int left, int right);

/** Partition ar[left, right] around the value stored in ar[idx]. */
extern int partition (void **ar, int(*cmp)(const void *,const void *),
		      int left, int right, int idx);

/**
 * Average-case linear time recursive algorithm to find position of kth
 * element in ar, which is modified as this computation proceeds.  Note 1
 * <= k <= right-left+1.  The comparison function, cmp, is needed to
 * properly compare elements. Worst-case is quadratic, O(n^2).
 */
int selectKth (void **ar, int(*cmp)(const void *,const void *),
	       int k, int left, int right) {
  int idx = selectPivotIndex (ar, left, right);
  int pivotIndex = partition (ar, cmp, left, right, idx);
  if (left+k-1 == pivotIndex) { return pivotIndex; } 

  /* continue the loop, narrowing the range as appropriate. If we are within
   * the left-hand side of the pivot then k can stay the same. */
  if (left+k-1 < pivotIndex) {
    return selectKth (ar, cmp, k, left, pivotIndex - 1);
  } else {
    return selectKth (ar, cmp, k - (pivotIndex-left+1), pivotIndex+1, right);
  }
}

