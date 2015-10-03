/**
 * @file selectKth.c      Without recursion, Select the kth element of ar[left, right] 
 * @brief
 *  A non-recursive implementation to select the kth smallest element from
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
 * Average-case linear time algorithm to find position of kth element in
 * ar, which is modified as this computation proceeds. Note 1 <= k <=
 * right-left+1.  The comparison function, cmp, is needed to properly
 * compare elements.
 *
 * @param ar           array of elements to be sorted.
 * @param cmp          comparison function to order elements.
 * @param k            kth smallest element to select.
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (inclusive)
 */
int selectKth (void **ar, int(*cmp)(const void *,const void *),
	       int k, int left, int right) {
  do {
    int idx = selectPivotIndex (ar, left, right);
    
    int pivotIndex = partition (ar, cmp, left, right, idx);
    if (left+k-1 == pivotIndex) {
      return pivotIndex;
    } 

    /* continue the loop, narrowing the range as appropriate. If we are within
     * the left-hand side of the pivot then k can stay the same. */
    if (left+k-1 < pivotIndex) {
      right = pivotIndex - 1;
    } else {
      /* otherwise k must decrease by the size being removed. */
      k -= (pivotIndex-left+1);
      left = pivotIndex + 1;
    }
  } while (1);
}

