/**
 * @file revisedPartition_baseQsort.c    Base implementation of Quicksort with leftmost pivot selection and all subcases sorted by do_qsort.
 * @brief
 *  Straight Quicksort implementation with no optimizations.
 *
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include "report.h"

/** Problem size below which to use insertion sort. */
extern int minSize;

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
 * @return             location of the pivot index properly positioned.
 */
int partition (void **ar, int(*cmp)(const void *,const void *),
	       int left, int right) {
  void *pivot = ar[left];

  int i = left - 1;
  int j = right + 1;

  while (1) {
    void *tmp;
    do { j = j-1; } while (cmp(ar[j], pivot) > 0);
    do { i = i+1; } while (cmp(ar[i], pivot) < 0);

    if (i < j) {
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */

      tmp = ar[i];
      ar[i] = ar[j];
      ar[j] = tmp;
    } else {
      return j;
    }
  }
}

/** proper insertion sort, optimized */
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


/** To show worse performance, consider using selectionSort instead of InsertionSort! */
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
  pivotIndex = partition (ar, cmp, left, right);
  
  do_qsort (ar, cmp, left, pivotIndex);
  do_qsort (ar, cmp, pivotIndex+1, right);
}

/** invoke qsort. */
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
