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

/** consider how much is saved by avoiding parameters. */
static void **ar;
static int(*cmp)(const void *,const void *);


/** force minsize to zero for even comparison with parallel version. */
int minSize= 0;

/** Code to select a pivot index around which to partition ar[left, right]. */
extern int selectPivotIndex (void **vals, int left, int right);

/**
 * In linear time, group the sub-array ar[left, right) around a pivot
 * element pivot=ar[right] by storing pivot into its proper location,
 * store, within the sub-array (whose location is returned by this
 * function) and ensuring that all ar[left,store) <= pivot and all
 * ar[store+1,right) > pivot.
 * 
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (exclusive)
 * @return             location of the pivot index properly positioned.
 */
int partition (int left, int right) {
	       
  void *tmp, *pivot;
  int idx, store;

  pivot = ar[right];

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
void insertion (int low, int high) {
		
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
void selectionSort (int low, int high) {
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
void do_qsort (int left, int right) {
	       
  int pivotIndex;
  if (right <= left) { return; }
  
  /* partition */
  pivotIndex = partition (left, right);
  
  if (pivotIndex-1-left <= minSize) {
    insertion (left, pivotIndex-1);
  } else {
    do_qsort (left, pivotIndex-1);
  }
  if (right-pivotIndex-1 <= minSize) {
    insertion (pivotIndex+1, right);
  } else {
    do_qsort (pivotIndex+1, right);
  }
}

/** Sort by using Quicksort. */
void
sortPointers (void **vals, int total_elems, 
	       int(*c)(const void *,const void *)) {

  ar = vals;
  cmp = c;
  do_qsort (0, total_elems-1);
}

/* debugging method. */
void output (int n) {
  int i;
  for (i = 0; i < n; i++) {
    printf ("%d. %s\n", i, (char*)ar[i]);
  }
}
