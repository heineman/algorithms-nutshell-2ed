/**
 * @file baseQsort.c   Quicksort implementation
 * @brief 
 *   Complete Quicksort implementation optimized to switch to Insertion 
 *   Sort when problem sizes are less than or equal to minSize in length.
 *   For pure QuickSort, set minSize to 0.
 *   
 * @author George Heineman
 * @date 6/15/08
 *
 *   MODIFIED: For November O'Reilly Blog, modified to fix the comparison
 *             functions and pivot selection methods.
 * 
 * @author George Heineman
 * @date 10/05/08
 */

#include <sys/types.h>
#include <stdlib.h>
#include <string.h>

/** Problem size below which to use insertion sort. FIX AT 10. */
#define minSize 10

/**
 * In linear time, group the sub-array ar[left, right) around a pivot
 * element pivot=ar[pivotIndex] by storing pivot into its proper location,
 * store, within the sub-array (whose location is returned by this
 * function) and ensuring that all ar[left,store) <= pivot and all
 * ar[store+1,right) > pivot.
 * 
 * @param ar           array of elements to be sorted.
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (exclusive)
 * @param pivotIndex   index around which the partition is being made.
 * @return             location of the pivot index properly positioned.
 */
int partition (char **ar, int left, int right, int pivotIndex) {
  char *tmp, *pivot;
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
    if (strcmp(ar[idx], pivot) <= 0) {
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

/**  proper insertion sort, optimized */
void insertion (char **ar, int low, int high) {
  int loc;
  for (loc = low+1; loc <= high; loc++) {
    int i = loc-1;
    char *value = ar[loc];
    while (i >= 0 && strcmp(ar[i], value)> 0) {
      ar[i+1] = ar[i];
      i--;
    } 

  ar[i+1] = value;
  }
}


/** Implement SelectionSort if one wants to see timing difference if this is used instead of Insertion Sort. */
void selectionSort (void **ar, int low, int high) {
  int t, i;

  if (high <= low) { return; }
  for (t = low; t < high; t++) {
    for (i = t+1; i <= high; i++) {
      void *c;
      if (strcmp(ar[i], ar[t]) <= 0) {
        c = ar[t];
	ar[t] = ar[i];
	ar[i] = c;
      }
    }
  }
}

/**
 * Sort array ar[left,right] using Quicksort method.
 */
void do_qsort (char **ar, int left, int right) {
  int pivotIndex;
  if (right <= left) { return; }
  
  /* partition. INLINE THE PIVOT SELECTION METHOD. */
  pivotIndex = left + (int) ((right-left+1) * (rand() / (RAND_MAX + 1.0)));
  pivotIndex = partition (ar, left, right, pivotIndex);
  
  if (pivotIndex-1-left <= minSize) {
    insertion (ar, left, pivotIndex-1);
  } else {
    do_qsort (ar, left, pivotIndex-1);
  }
  if (right-pivotIndex-1 <= minSize) {
    insertion (ar, pivotIndex+1, right);
  } else {
    do_qsort (ar, pivotIndex+1, right);
  }
}

/** Sort by using Quicksort. */
void
sortPointers (char **vals, int total_elems) {
  do_qsort (vals, 0, total_elems-1);
}
