/**
 * @file selectKthWorstLinearFive.c      Optimized implementation of BFPRT with groupingSize=5 to select kth smallest from an array in worst-case linear time.
 * @brief
 *  A recursive implementation of BFPRT to select the kth smallest element 
 *  within ar[left, right] in worst-case linear time. Customized to group
 *  the elements in groups of 5.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

/** Partition ar[left, right] around the value stored in ar[idx]. */
extern int partition (void **ar, int(*cmp)(const void *,const void *),
		      int left, int right, int idx);

/** specialized insertion sort up to five elements with spaced gap. */
static void _insertion (void **ar, int(*cmp)(const void *,const void *),
                 int low, int right, int gap) {
  int loc;
  for (loc = low+gap; loc <= right; loc += gap) {
    int i = loc-gap;
    void *value = ar[loc];
    while (i >= low && cmp(ar[i], value)> 0) {
      ar[i+gap] = ar[i];
      i -= gap;
    }
    ar[i+gap] = value;
  }
}


/**
 * Find suitable pivotIndex to use for ar[left,right] with closed bound
 * on both sides.
 *
 * 1. Divide the elements into floor(n/4) groups of 4 elements and
 *    use _insertion on each group 
 * 2. Pick median from each sorted groups (3rd element).
 * 3. Use select recursively to find median x of floor(n/4) medians
 *    found in step 2. This is known as the median-of-medians.
 */
static int medianOfMedians (void **ar, int(*cmp)(const void *,const void *),
		     int left, int right, int gap) {
  int s, num;
  int span = 5*gap;

  /* less than five? Insertion sort and return median.  */
  num = (right - left + 1) / span;
  if (num == 0) {
    _insertion (ar, cmp, left, right, gap);
    num = (right - left + 1)/gap;
    return left + gap*(num-1)/2;
  }

  /* set up all median values of groups of 5 elements */
  for (s = left; s+span < right; s += span) {
    _insertion (ar, cmp, s, s + span-1, gap);
  }

  /* Recursively apply to subarray [left, s-1] with increased gap 
   * if more than five groupings remain. */
  if (num < 5) {
    /* find median of this reduced set. BASE CASE */
    _insertion (ar, cmp, left+span/2, right, span);
    return left + num*span/2;
  } else {
    return medianOfMedians (ar, cmp, left+span/2, s-1, 5*gap);
  }
}

/** Linear worst-case time algorithm to find median in ar[left,right]. The 
 * comparison function, cmp, is needed to compare elements. */
int selectMedian (void **ar, int(*cmp)(const void *,const void *),
                  int left, int right) {
  int k = 1 + (right-left+1)/2;
  while (k > 0) {
    /* Make guess */
    int idx = medianOfMedians (ar, cmp, left, right, 1);

    /**
     * Partition input array around the median of medians x. If kth
     * largest is found, return absolute index; otherwise narrow to
     * find kth smallest in A[left,pivotIndex-1] or (k-p)-th
     * in A[pivotIndex+1,right].
     *
     */
    int pivotIndex = partition (ar, cmp, left, right, idx);

    /* Note that k is in range 1 <=k <= right-left+1 while the returned
       pivotIndex is in range left <= pivotIndex <= right. */
    int p = left+k-1;
    if (p == pivotIndex) { 
      return pivotIndex;
    } else if (p < pivotIndex) {
      right = pivotIndex-1;
    } else {
      k = k - (pivotIndex-left+1);
      left = pivotIndex+1;
    }
  }

  /* If we get here, then left=right, so just return one as median. */
  return left;
}

