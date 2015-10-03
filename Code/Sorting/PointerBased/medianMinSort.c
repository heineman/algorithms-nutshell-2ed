/**
 * @file medianMinSort.c    Contains Median Sort implementation with Min Size optimization
 * @brief 
 *   Contains Median Sort implementation that switches to Insertion Sort when
 *   the problem size is small enough.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include "report.h"

/** Minimum size beyond which to use Insertion Sort. */
extern int minSize;

/** Externally provided method to select Kth element from subarray. */
extern int selectKth (void **ar, int(*cmp)(const void *,const void *),
		      int k, int left, int right);

/**
 * In linear time, group the sub-array ar[left, right) around a pivot
 * element pivot=ar[pivotIndex] by storing pivot into its proper 
 * location, store, within the sub-array (whose location is returned 
 * by this function) and ensuring that all ar[left,store) <= pivot and 
 * all ar[store+1,right) > pivot.
 * 
 * @param ar           array of elements to be sorted.
 * @param cmp          comparison function to order elements.
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (exclusive)
 * @param pivotIndex   index around which the partition is being made.
 * @return             location of the pivot index properly positioned.
 */
int partition (void **ar, int(*cmp)(const void *,const void *),
	       int left, int right, int pivotIndex) {
  int idx, store;
  void *pivot = ar[pivotIndex];

  /* move pivot to the end of the array */
  void *tmp = ar[right];
  ar[right] = ar[pivotIndex];
  ar[pivotIndex] = tmp;
  
  /* all values <= pivot are moved to front of array and pivot inserted
   * just after them. */
  store = left;
  for (idx = left; idx < right; idx++) {
    if (cmp(ar[idx], pivot) <= 0) {
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */

      tmp = ar[idx];
      ar[idx] = ar[store];
      ar[store] = tmp;
      store++;
    }
  }
  
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */

  tmp = ar[right];
  ar[right] = ar[store];
  ar[store] = tmp;
  return store;
}

/** Perform insertion sort over the given subarray. */
void insertion (void **ar, int(*cmp)(const void *,const void *),
		int low, int high) {
  int loc;
  for (loc = low+1; loc <= high; loc++) {
    int i = loc-1;
    void *value = ar[loc];
    while (i >= 0 && cmp(ar[i], value)> 0) {
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */
      ar[i+1] = ar[i];
      i--;
    } 

#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */
  ar[i+1] = value;
  }
}

/** By contrast, perform Selection Sort over subarray. */
void selectionSort (void **ar, int(*cmp)(const void *,const void *),
		int low, int high) {
  int t, i;

  if (high <= low) { return; }
  for (t = low; t < high; t++) {
    for (i = t+1; i <= high; i++) {
      void *c;
      if (cmp(ar[i], ar[t]) <= 0) {
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */
        c = ar[t];
	ar[t] = ar[i];
	ar[i] = c;
      }
    }
  }
}


/**
 * Sort using medianSort method.
 * 
 * @param ar           array of elements to be sorted.
 * @param cmp          comparison function to order elements.

 * @param left     The left-bounds within which to sort (0 <= left < ar.length)
 * @param right    The right-bounds within which to sort (0 <= right < ar.length)
 */
void mediansort (void **ar, int(*cmp)(const void *,const void *),
		 int left, int right) {
  int me, mid;

  /* if the subarray to be sorted has 1 (or fewer!) elements, done. */
  if (right <= left) { return; }
  
  /* get midpoint and median element position (note 1<=k<=right-left-1). */
  mid = (right - left + 1)/2; 
  me = selectKth (ar, cmp, mid+1, left, right);
  
  /* at this point, me should be equal to mid+1. */
  if (mid-1 <= minSize) {
    insertion (ar, cmp, left, left+mid-1);
  } else {
    mediansort (ar, cmp, left, left+mid-1);
  }

  if (right-left-mid-1 <= minSize) {
    insertion (ar, cmp, left+mid+1, right);
  } else {
    mediansort (ar, cmp, left+mid+1, right);
  }
}

/** Sort the pointers via median sort. */
void
sortPointers (void **vals, int total_elems, 
	       int(*cmp)(const void *,const void *)) {

  mediansort (vals, cmp, 0, total_elems-1);
}
