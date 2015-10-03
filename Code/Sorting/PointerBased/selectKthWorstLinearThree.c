/**
 * @file selectKthWorstLinearThree.c      Optimized implementation of BFPRT with groupingSize=3 to select kth smallest from an array in worst-case linear time.
 * @brief
 *  A recursive implementation of BFPRT to select the kth smallest element 
 *  within ar[left, right] in worst-case linear time. Customized to group
 *  the elements in groups of 3.
 * 
 * @author George Heineman
 * @date 6/15/08
 */


/** Partition ar[left, right] around the value stored in ar[idx]. */
extern int partition (void **ar, int(*cmp)(const void *,const void *),
		      int left, int right, int idx);

/** Macro to swap two elements in the array. */
#define SWAP(a,p1,p2,type) { \
    type _tmp__ = a[p1];     \
    a[p1] = a[p2];           \
    a[p2] = _tmp__;          \
  }

/** determine median of three elements in array
 *    ar[left], ar[left+gap], ar[left+gap*2]
 * and ensure that ar[left+gap] contains thie median value once done.
 */
static void medianOfThree(void **ar, int left, int gap, int(*cmp)(const void *,const void *)) {
  int pos1=left, pos2, pos3;
  void *a1 = ar[pos1];
  void *a2 = ar[pos2=pos1+gap];
  void *a3 = ar[pos3=pos2+gap];

  if (cmp(a1, a2) <= 0) {
    if (cmp(a2, a3) <= 0) {
      /* done. result is already in position 2. */
    } else {
      if (cmp(a1,a3) <= 0) {
	SWAP(ar,pos2,pos3,void *);
      } else {
	SWAP(ar,pos2,pos1,void *);
      }
    }
  } else {
    if (cmp (a2,a3) <= 0) {
      if (cmp (a1,a3) <= 0) {
	SWAP(ar,pos2,pos1,void *);
      } else {
	SWAP(ar,pos2,pos3,void *);
      }
    } else {
      /* done. result is already in position 2. */
    }
  }
}


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
 * on both sides. Goal is to consider groups of size b. In this code, b=3.
 * In the original BFPRT algorithm b=5.
 * 
 * 1. Divide the elements into floor(n/b) groups of b elements and
 *    find median value of each of these groups. Consider this set of
 *    all medians to be the set M.
 * 
 * 2. If |M| > b, then  recursively apply until <=b groups are left
 * 
 * 3. In the base case of the recursion, simply use INSERTION SORT to sort
 *    remaining <=b median values and choose the median of this sorted set.
 */
static int medianOfMedians (void **ar, int(*cmp)(const void *,const void *),
			    int left, int right, int gap) {
  int s, num;
  int span = 3*gap;

  /* less than group size? Insertion sort and return median.  */
  num = (right - left + 1) / span;
  if (num == 0) {
    _insertion (ar, cmp, left, right, gap);
    num = (right - left + 1)/gap;
    return left + gap*(num-1)/2;
  }

  /* set up all median values of groups of elements */
  for (s = left; s+span < right; s += span) {
    medianOfThree(ar, s, gap, cmp);
  }

  /* Recursively apply to subarray [left, s-1] with increased gap 
   * if more than 3 groupings remain. */
  if (num < 3) {
    /* find median of this reduced set. BASE CASE */
    _insertion (ar, cmp, left+span/2, right, span);
    return left + num*span/2;
  } else {
    return medianOfMedians (ar, cmp, left+span/2, s-1, span);
  }
}

/** Linear worst-case time algorithm to find median in ar[left,right]. The 
 * comparison function, cmp, is needed to compare elements. */
int selectMedian (void **ar, int(*cmp)(const void *,const void *),
                  int left, int right) {
  int k = (right-left+1)/2;
  while (k > 0) {
    /* Make guess */
    int idx = medianOfMedians (ar, cmp, left, right, 1);

    /**
     * Partition input array around the median of medians x. If kth
     * largest is found, return absolute index; otherwise narrow to
     * find kth smallest in A[left,pivotIndex-1] or (k-p)-th
     * in A[pivotIndex+1,right].
     */
    int pivotIndex = partition (ar, cmp, left, right, idx);

    /* Note that k is in range 0 <=k <= right-left while the returned
       pivotIndex is in range left <= pivotIndex <= right. */
    int p = left+k;
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

