/**
 * @file modifiedQsort.c
 * @brief
 *
 *    Find crossover where InsertionSort is slower than FullQsort using
 *    MedianOfThree as pivot and collapsing walls as partition.
 *
 * @author George Heineman
 * @date 4/15/09
 */

#include <getopt.h>
#include <stdio.h>
#include <stdlib.h>
#include "problem.h"
#include "report.h"

#undef VALIDATE
#ifndef COUNT
#define ADD_COMP
#define ADD_SWAP
#endif /* COUNT */

int minSize = 0;

void problemUsage () {
  /* nothing special */
}

void postInputProcessing () {
  /* nothing special */
}


/**
 * In linear time, group the sub-array ar[left, right) around a pivot
 * element ar[right] by storing pivot into its proper location,
 * store, within the sub-array (whose location is returned by this
 * function) and ensuring that all ar[left,store) <= pivot and all
 * ar[store+1,right) > pivot.
 * 
 * ASSUMES THAT pivot value is already placed in ar[right] and that
 * ar[left] <= ar[mid] <= ar[right].
 * 
 * @param ar           array of elements to be sorted.
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (exclusive)
 * @return             location of the pivot index properly positioned.
 */
int partition (int *ar, int left, int right) {
  int tmp, pivot, store;

  store = right--;
  pivot = ar[store];
  left++;

  do {
    while (ar[left] < pivot) { left++; ADD_COMP; }
    while (pivot < ar[right]) { right--; ADD_COMP; }
    ADD_COMP;
    ADD_COMP;

    if (left < right) {
      ADD_COMP;
      ADD_SWAP;
      tmp = ar[left];
      ar[left] = ar[right];
      ar[right] = tmp;
      left++;
      right--;
    } else if (left == right) {
      ADD_COMP;
      ADD_COMP;
      break;
    } else {
      ADD_COMP;
      ADD_COMP;
    }
  } while (left <= right);
  
  ADD_SWAP;
  ar[store] = ar[left];
  ar[left] = pivot;
  return left;
}

/**  proper insertion sort, optimized */
void insertion (int *ar, int low, int high) {
  int loc;
  for (loc = low+1; loc <= high; loc++) {
    int i = loc-1;
    int value = ar[loc];
    while (i >= 0 && ar[i] > value) {
      ADD_SWAP;
      ADD_COMP;
      ar[i+1] = ar[i];
      i--;
    } 
#ifdef COUNT
    if (i >= 0) { ADD_COMP; }
#endif

  ar[i+1] = value;
  }
}


/**
 * Select pivot index to use in partition based on median of three. 
 * Places smallest value in vals[left] and the mdeian value in vals[right].
 * The largest of the three is actually placed in vals[mid]
 * 
 * Inline code using macro to set value
 * 
 * \param vals    the array of elements.
 * \param left    the left end of the subarray range
 * \param right   the right end of the subarray range
 * \return        int in the range [left, right] to use in partition.
 */
#define selectPivotIndex(vals,left,right)    \
{                                            \
  int mid;                                   \
  int c0, c1, c2;                            \
  c0 = vals[left];                           \
  mid = (left+right+1)/2;                    \
  c1 = vals[mid];                            \
                                             \
  /* actually order these three */           \
  if (c0 > c1) {                             \
    ADD_COMP;                                \
    ADD_SWAP;                                \
    vals[left] = c1;                         \
    vals[mid] = c0;                          \
    c0 = vals[left]; c1 = vals[mid];         \
  }                                          \
                                             \
  /** protect against size two arrays. */    \
  if (mid != right) {                        \
    c2 = vals[right];                        \
    ADD_COMP;                                \
    if (c1 > c2) {                           \
      /* now we know largest is in [mid]. We must now place median in [right]. */ \
      ADD_COMP;                              \
      if (c0 > c2) {                         \
	ADD_SWAP;                            \
	vals[left] = c2;                     \
	vals[right] = c0;                    \
      }                                      \
    } else {                                 \
      ADD_SWAP;                              \
      vals[mid] = c2;                        \
      vals[right] = c1;                      \
    }                                        \
  }                                          \
}


/**
 * Sort array ar[left,right] using Quicksort method.
 */
void do_qsort (int *ar, int left, int right) {
  int pivotIndex, ct;
  if (right <= left) { return; }
  
  /* locate median (also placeds it in ar[right]. */
  selectPivotIndex(ar,left,right);
  pivotIndex = partition (ar, left, right);
  
  ct = pivotIndex - left;
  if (ct > minSize && ct > 1) {
    do_qsort (ar, left, pivotIndex-1);
  } else if (ct > 1) {
    insertion (ar, left, pivotIndex - 1);
  }

  ct = right - pivotIndex;
  if (ct > minSize && ct > 1) {
    do_qsort (ar, pivotIndex+1, right);
  } else if (ct > 1) {
    insertion (ar, pivotIndex+1, right);
  }

}

/** 1000 set of elements to be sorted (unless set). */
int **vals;
int numSets = 1000;

/** sort method (0=quickSort, 1=insertionSort). */
int sortMethod = -1;

void prepareInput (int size, int argc, char **argv) {
  int i, j, descend, ascend;
  char c;

  ascend = descend = 0;
  while ((c = getopt(argc, argv, "adiqs:m:")) != -1) {
    switch (c) {
    case 'a':
      ascend = 1;
      break;

    case 'd':
      descend = 1;
      break;

    case 'i':
      sortMethod = 1;
      break;

    case 'q':
      sortMethod = 0;
      break;

    case 's':
      numSets = atoi(optarg);
      break;

    case 'm':
      minSize = atoi(optarg);
      break;

    default:
      break;
    }
  }
  optind = 0;  /*  reset getopt for next time around. */

  /** Make 1000 sample arrays. */
  /* draw from 1..numElements^2 */
  vals = (int **) calloc (numSets, sizeof (int *));
  for (i = 0; i < numSets; i++) {
    vals[i] = (int *) calloc (numElements, sizeof(int));
    for (j = 0; j < numElements; j++) {
      if (ascend) {
	vals[i][j] = j;
      } else if (descend) {
	vals[i][j] = numElements - j;
      } else {
	vals[i][j] = 1 + (int) (numElements * numElements *(rand()/(RAND_MAX + 1.0)));
      }
    }
  }
}

void output (int *ar, int n) {
  int i;
  for (i = 0; i < n; i++) {
    printf ("%d. %d\n", i, ar[i]);
  }
}

/**
 * numElements is there
 */
void execute() {
  int i;
#ifdef VALIDATE
  int j;
#endif

  for (i = 0; i < numSets; i++) {
    switch (sortMethod) {

    case 0:
      do_qsort (vals[i], 0, numElements-1); 
      break;

    case 1:
      insertion (vals[i], 0, numElements-1); 
      break;
    }

#ifdef VALIDATE
    /* validate! */
    for (j = 0; j < numElements-1; j++) {
      if (vals[i][j] > vals[i][j+1]) {
	printf ("failed on trial %d\n", j);
	return;
      }
    }
#endif

  }
}
