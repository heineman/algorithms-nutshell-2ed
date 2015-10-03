/**
 * @file baseQsort.c   Quicksort implementation
 * @brief 
 *   Complete Quicksort implementation optimized to switch to Insertion 
 *   Sort when problem sizes are less than or equal to minSize in length.
 *   For pure QuickSort, set minSize to 0.
 *   
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <sys/types.h>

#include "report.h"

/** Problem size below which to use insertion sort. */
extern int minSize;

/** Code to select a pivot index around which to partition ar[left, right]. */
extern int selectPivotIndex (void **vals, int left, int right);

/**
 * In linear time, group the sub-array ar[left, right) around a pivot
 * element pivot=ar[pivotIndex] by storing pivot into its proper location,
 * store, within the sub-array (whose location is returned by this
 * function) and ensuring that all ar[left,store) <= pivot and all
 * ar[store+1,right) > pivot.
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
  void *tmp, *pivot;
  int idx, store;

  pivot = ar[pivotIndex];

  /*  move pivot to the end of the array*/
  tmp = ar[right];
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

/**  proper insertion sort, optimized */
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


/** Implement SelectionSort if one wants to see timing difference if this is used instead of Insertion Sort. */
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
 * Sort array ar[left,right] using Quicksort method.
 * The comparison function, cmp, is needed to properly compare elements.
 */
void do_qsort (void **ar, int(*cmp)(const void *,const void *),
	       int left, int right) {
  int pivotIndex;
  if (right <= left) { return; }
  
  /* partition */
  pivotIndex = selectPivotIndex (ar, left, right);
  pivotIndex = partition (ar, cmp, left, right, pivotIndex);
  
  if (pivotIndex-1-left <= minSize) {
    insertion (ar, cmp, left, pivotIndex-1);
  } else {
    do_qsort (ar, cmp, left, pivotIndex-1);
  }
  if (right-pivotIndex-1 <= minSize) {
    insertion (ar, cmp, pivotIndex+1, right);
  } else {
    do_qsort (ar, cmp, pivotIndex+1, right);
  }
}

/** Sort by using Quicksort. */
void
sortPointers (void **vals, int total_elems, 
	       int(*cmp)(const void *,const void *)) {

  do_qsort (vals, cmp, 0, total_elems-1);
}

/* debugging method. */
void output (char **ar, int n) {
  int i;
  for (i = 0; i < n; i++) {
    printf ("%d. %s\n", i, ar[i]);
  }
}
