/**
 * @file testSmallArrays.c    Test speed InsertionSort vs. FullQsort
 * @brief
 *    Find crossover where InsertionSort is slower than FullQsort using
 *    MedianOfThree as pivot
 *
 * @author George Heineman
 * @date 4/15/09
 */

#include <getopt.h>
#include <stdio.h>
#include <stdlib.h>
#include "problem.h"

#undef VALIDATE

void problemUsage () {
  /* nothing special */
}

void postInputProcessing () {
  /* nothing special */
}


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
int partition (int *ar, int left, int right, int pivotIndex) {
  int tmp, pivot;
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
    if (ar[idx] <= pivot) {
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
void insertion (int *ar, int low, int high) {
  int loc;
  for (loc = low+1; loc <= high; loc++) {
    int i = loc-1;
    int value = ar[loc];
    while (i >= 0 && ar[i] > value) {
      ar[i+1] = ar[i];
      i--;
    } 

  ar[i+1] = value;
  }
}


/**
 * Select pivot index to use in partition.
 * 
 * Inline code using macro to set value
 * 
 * \param vals    the array of elements.
 * \param left    the left end of the subarray range
 * \param right   the right end of the subarray range
 * \return        int in the range [left, right] to use in partition.
 */
#define selectPivotIndex(vals,left,right,pivot)\
{                                       \
  int mid;                              \
  int c0, c1, c2;                       \
  c0 = vals[left];                      \
  mid = (left+right+1)/2;               \
  c1 = vals[mid];                       \
  c2 = vals[right];                     \
                                        \
  if (c0 < c1) {                        \
    if (c1 <= c2) {                     \
      pivot = mid;                      \
    } else if (c0 < c2) {               \
      pivot = right;                    \
    } else {                            \
      pivot = left;                     \
    }                                   \
  } else if (c0 < c2) {                 \
    pivot = left;                       \
  } else if (c1 < c2) {                 \
    pivot = right;                      \
  } else {                              \
    pivot = mid;                        \
  }                                     \
}


/**
 * Sort array ar[left,right] using Quicksort method.
 */
void do_qsort (int *ar, int left, int right) {
  int pivotIndex;
  if (right <= left) { return; }
  
  /* partition */
  selectPivotIndex(ar,left,right,pivotIndex)
  pivotIndex = partition (ar, left, right, pivotIndex);
  
  do_qsort (ar, left, pivotIndex-1);
  do_qsort (ar, pivotIndex+1, right);

}

/** Sort by using Quicksort. */
void
sortPointers (int *vals, int total_elems) {
  do_qsort (vals, 0, total_elems-1);
}

/** Number of elements. */
int n;

/** Running state of permutation. */
int *permutation;

/** Elements to be permuted */
int *elements;

/** Current permutation. */
int *ar;

/** Start permutation. */
void initPermutation(int *vals, int num) {
  int i;

  elements = (int *) calloc (num, sizeof (int));
  ar = (int *) calloc (num, sizeof (int));
  for (i = 0; i < num; i++) {
    elements[i] = vals[i];
    ar[i] = vals[i];
  }

  permutation = (int *) calloc (num+1, sizeof(int));
  for (i=0; i<num+1; i++) {
    permutation [i]=i;
  }
}

int more = 1;

int hasNext() {
  return more;
}

void swap (int i, int j) {
  int x = permutation[i];
  permutation[i] = permutation [j];
  permutation[j] = x;
}


void formNextPermutation () {
  int i;
  for (i=0; i<numElements; i++) {
    /* i+1 because perm[0] always = 0  */
    /* perm[]-1 because the numbers 1..size are being permuted */
    ar[i] = elements[permutation[i+1]-1];
  }
}



void next() {
  int i, j, r, s;
  formNextPermutation ();  

  i = numElements-1;
  while (permutation[i]>permutation[i+1])
    i--;

  if (i==0) {
    more = 0;
    for (j=0; j<numElements+1; j++) {
      permutation [j]=j;
    }
    return;
  }

  j = numElements;

  while (permutation[i]>permutation[j]) j--;
  swap (i,j);
  r = numElements;
  s = i+1;
  while (r>s) { swap(r,s); r--; s++; }
}


/** sort method (0=quickSort, 1=insertionSort). */
int sortMethod = -1;

void prepareInput (int size, int argc, char **argv) {
  int *vals, i;
  char c;

  while ((c = getopt(argc, argv, "qi")) != -1) {
    switch (c) {
    case 'q':
      sortMethod = 0;
      break;

    case 'i':
      sortMethod = 1;
      break;

    default:
      break;
    }
  }
  optind = 0;  /*  reset getopt for next time around. */

  vals = (int *) calloc (numElements, sizeof(int));
  for (i = 0; i < numElements; i++) {
    vals[i] = i+1;
  }
  
  initPermutation(vals, numElements);
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
  int trials = 0;
#ifdef VALIDATE
  int i;
#endif

  while (hasNext()) {
    trials++;
    next();

    switch (sortMethod) {

    case 0:
      do_qsort (ar, 0, numElements-1); 
      break;

    case 1:
      insertion (ar, 0, numElements-1); 
      break;
    }

#ifdef VALIDATE
    /* validate! */
    for (i = 0; i < numElements-1; i++) {
      if (ar[i] > ar[i+1]) {
	printf ("failed on trial %d\n", trials);
	return;
      }
    }
#endif
  }

  printf ("%d trials\n", trials);
}
