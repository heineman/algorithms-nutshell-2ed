/**
 * @file medianSort.c    Contains Median Sort implementation
 * @brief
 *   Contains Median Sort implementation.
 *
 *   If compiled with -DMEDIAN set, then this relies on a selectMedian
 *   externally provided function to select the median; otherwise, it 
 *   falls back on using an externally provided selectKth. 
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include "report.h"

/** Externally provided method to select Kth element from subarray. */
extern int selectKth (void **vals, int(*cmp)(const void *,const void *),
		      int k, int left, int right);

/**
 * Code to select median is externally provided.
 * 
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (inclusive)
 */
extern int selectMedian (void **ar, int(*cmp)(const void *,const void *),
			 int left, int right);

/**

 * In linear time, group the sub-array ar[left, right] around a pivot
 * element pivot=ar[pivotIndex] by storing pivot into its proper ,
 * location store, within the sub-array (whose location is returned 
 * by this function) and ensuring that all ar[left,store) <= pivot and 
 * all ar[store+1,right] > pivot.
 * 
 * @param ar           contents of array
 * @param cmp          comparison function
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (inclusive)
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

/**
 * Sort using mediansort method.
 * 
 * @param ar           array of elements to be sorted.
 * @param cmp          comparison function to order elements.

 * @param left     The left-bounds within which to sort (0 <= left < ar.length)
 * @param right    The right-bounds within which to sort (0 <= right < ar.length)
 */
void mediansort (void **ar, int(*cmp)(const void *,const void *),
		    int left, int right) {
  int me, mid;

  /* if the sub-array to be sorted has 1 (or fewer!) elements, done. */
  if (right <= left) { return; }
  
  /* get midpoint and median element position (1<=k<=right-left-1). */
  mid = (right - left + 1)/2; 
#ifdef MEDIAN
  me = selectMedian (ar, cmp, left, right);
#else
  me = selectKth(ar, cmp, mid+1, left, right);
#endif
  
  mediansort (ar, cmp, left, left+mid-1);
  mediansort (ar, cmp, left+mid+1, right);
}

/** Apply median sort. */
void
sortPointers (void **vals, int n, int(*cmp)(const void *,const void *)) {
  mediansort (vals, cmp, 0, n-1);
}
