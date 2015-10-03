/**
 * @file dot_baseQsort.c    Generate QuickSort DOT graph
 * @brief
 *    Implementation that uses dot.h interface to produce on stdout the
 *    DOT commands for a QuickSort execution.
 * 
 *    This code is not intended to be used for sorting. Rather, it
 *    generates DOTTY output that shows the behavior of QuickSort. You
 *    have been warned.  
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <stdlib.h>

#include "dot.h"

/** Problem size below which to use insertion sort. */
extern int minSize;

/** Code to select the pivot index is found elsewhere. */
extern int selectPivotIndex (long *vals, int left, int right);

/**
 * In linear time, group the sub-array ar[left, right) around a pivot
 * element pivot=ar[pivotIndex] by storing pivot into its proper location,
 * store, within the sub-array (whose location is returned by this
 * function) and ensuring that all ar[left,store) <= pivot and all
 * ar[store+1,right) > pivot.
 * 
 * @param ar           array of values
 * @param cmp          comparison function to use
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (exclusive)
 * @param pivotIndex   index around which the partition is being made.
 * @return             location of the pivot index properly positioned.
 */
int partition (long *ar, int(*cmp)(const long,const long),
	       int left, int right, int pivotIndex) {
  int idx, store;
  long tmp;
  long pivot = ar[pivotIndex];

  /* move pivot to the end of the array */
  tmp = ar[right];
  ar[right] = ar[pivotIndex];
  ar[pivotIndex] = tmp;
  
  /* all values <= pivot are moved to front of array and pivot inserted
   * just after them. */
  store = left;
  for (idx = left; idx < right; idx++) {
    if (cmp(ar[idx], pivot) <= 0) {
      tmp = ar[idx];
      ar[idx] = ar[store];
      ar[store] = tmp;
      store++;
    }
  }
  
  tmp = ar[right];
  ar[right] = ar[store];
  ar[store] = tmp;
  return store;
}

/** proper insertion sort, optimized */
void insertion (long *ar, int(*cmp)(const long,const long),
		int low, int high) {
  int loc;
  for (loc = low+1; loc <= high; loc++) {
    int i = loc-1;
    long value = ar[loc];
    while (i >= 0 && cmp(ar[i], value)> 0) {
      ar[i+1] = ar[i];
      i--;
    } 
    ar[i+1] = value;
  }
}


/** perform a selection sort. */
void selectionSort (long **ar, int(*cmp)(const void *,const void *),
		int low, int high) {
  int t, i;

  if (high <= low) { return; }
  for (t = low; t < high; t++) {
    for (i = t+1; i <= high; i++) {
      long *c;
      if (cmp(ar[i], ar[t]) <= 0) {
	c = ar[t];
	ar[t] = ar[i];
	ar[i] = c;
      }
    }
  }
}

/**
 * Sort array ar[left,right] using QuickSort method.
 * The comparison function, cmp, is needed to properly compare elements.
 */
void do_qsort (int id, long *ar, int(*cmp)(const long,const long),
	       int left, int right) {
  DOT_FORMAT_PTR fmt;
  int pivotIndex;
  if (right <= left) { return; }
  
  /* go backwards to avoid null cases. avoid trivial first case */
  if (id/2 > 0) {
    dot_add_undir_edge (1000+id/2, id);
  }

  /* show array */
  fmt = dot_format_list();
  dot_node (ar, id, left, right, fmt);
  dot_release (fmt);

  /* partition */
  pivotIndex = selectPivotIndex (ar, left, right);
  pivotIndex = partition (ar, cmp, left, right, pivotIndex);
  fmt = dot_format_list();
  dot_add_format (fmt, dot_format_type (pivotIndex, BLACK));/*ADD BLACK first*/
  dot_node (ar, 1000+id, left, right, fmt);
  dot_release (fmt);

  /* tie together. */
  dot_add_undir_edge (id, 1000+id);

  if (pivotIndex-1-left <= minSize) {
    insertion (ar, cmp, left, pivotIndex-1);
  } else {
    do_qsort (id*2, ar, cmp, left, pivotIndex-1);
  }
  if (right - pivotIndex - 1 <= minSize) {
    insertion (ar, cmp, pivotIndex+1, right);
  } else {
    do_qsort (id*2+1, ar, cmp, pivotIndex+1, right);
  }
}

