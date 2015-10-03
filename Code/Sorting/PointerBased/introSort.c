/**
 * @file introSort.c   IntroSort implementation
 * @brief 
 *
 *   Complete IntroSort implementation optimized to switch to Insertion 
 *   Sort when problem sizes are less than or equal to minSize in length.
 *   For pure QuickSort, set minSize to 0.
 *   
 *   When depth of recursion goes beyond fixed "reasonable" limit, this
 *   algorithm switches to HeapSort.
 * 
 * @author George Heineman
 * @date /26/09
 */

#include <sys/types.h>
#include <math.h>

#include "report.h"

/** Problem size below which to use insertion sort. */
extern int minSize;

/** 
 * Take over -g G option to enable us to evaluate depth limits of
 * 10/G for the threshold.
 */
extern int groupingSize;

/** Code to select a pivot index around which to partition ar[left, right]. */
extern int selectPivotIndex (void **vals, int left, int right);

/** Heapify the subarray ar[0,max). */
static void heapify (void **ar, int(*cmp)(const void *,const void *),
		     int idx, int max) {
  int left = 2*idx + 1;
  int right = 2*idx + 2;
  int largest;

  /* Find largest element of A[idx], A[left], and A[right]. */
  if (left < max && cmp (ar[left], ar[idx]) > 0) {
    largest = left;
  } else {
    largest = idx;
  }

  if (right < max && cmp(ar[right], ar[largest]) > 0) {
    largest = right;
  }

  /* If largest is not already the parent then swap and propagate. */
  if (largest != idx) {
     void *tmp;
#ifdef COUNT
ADD_SWAP;
#endif /* COUNT */

     tmp = ar[idx];
     ar[idx] = ar[largest];
     ar[largest] = tmp;
      
     heapify(ar, cmp, largest, max);
   }
}

/** Build the heap from the given array by repeatedly invoking heapify. */
static void buildHeap (void **ar, int(*cmp)(const void *,const void *),
		       int n) {
  int i;
  for (i = n/2-1; i>=0; i--) {
    heapify (ar, cmp, i, n);
  }
}

/** Sort the array using Heap Sort implementation. */
void heapSort (void **ar, int left, int right,
	       int(*cmp)(const void *,const void *))
{
  int i;
  buildHeap (&ar[left], cmp, right-left+1);
  for (i = right; i > left; i--) {
   void *tmp;
#ifdef COUNT
ADD_SWAP;
#endif /* COUNT */
   tmp = ar[left];
   ar[left] = ar[i];
   ar[i] = tmp;

   heapify (ar, cmp, left, i);
  }
}


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


/**
 * Sort array ar[left,right] using Quicksort method.
 * The comparison function, cmp, is needed to properly compare elements.
 */
void introSort (void **ar, int(*cmp)(const void *,const void *),
		int left, int right, int maxDepth) {
  int pivotIndex;

  /* nifty way to sort right side of array, then left, as
     well as defer to insertion sort when too small. */
  while (right - left > minSize) {

    if (maxDepth == 0) {
      /* int xx; */

      heapSort (ar, left, right, cmp);
      /** DEBUG */
      /* printf ("--------------\n");
      for (xx = left; xx <= right; xx++) {
	printf ("%s\n", (char*)ar[xx]);
	}*/
      return;
    }
  
    /* partition */
    pivotIndex = selectPivotIndex (ar, left, right);
    pivotIndex = partition (ar, cmp, left, right, pivotIndex);
    maxDepth--;
    introSort (ar, cmp, pivotIndex, right, maxDepth);

    /** now sort left side. */
    right = pivotIndex;
  }
}

/** Sort by using Quicksort. */
void
sortPointers (void **vals, int total_elems, 
	       int(*cmp)(const void *,const void *)) {

  /**
   * Empirically determed by Musser, 2*log(n) is ideal. We use "-g gs"
   * option to evaluate this. When not set, defaults to 5. We can thus
   * test a variety of ranges.
   */
  double maxDepth = 10.0*log(total_elems)/groupingSize;
  
  introSort (vals, cmp, 0, total_elems-1, (int) (maxDepth * 2));

  /* All deferred "too small" array sub-regions are now sorted. */
  insertion (vals, cmp, 0, total_elems-1);
}
