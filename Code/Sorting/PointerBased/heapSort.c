/**
 * @file Sorting/PointerBased/heapSort.c   Generic Heap Sort implementation
 * @brief 
 *   Contains an optimized Heap Sort implementation for sorting strings.
 *   Implementation taken from:
 *
 * http://en.wikibooks.org/wiki/Algorithm_implementation/Sorting/Heapsort
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include "report.h"


/** Execute Heap Sort on the string array. */
void sortPointers (char **arr, int N, 
		   int(*cmp)(const void *,const void *))
{
  unsigned int n = N, i = n/2, parent, child;
  char *t;
 
  for (;;) { /* Loops until arr is sorted */
    if (i > 0) { /* First stage - Sorting the heap */
      i--;           /* Save its index to i */
      t = arr[i];    /* Save parent value to t */
    } else {     /* Second stage - Extracting elements in-place */
      n--;           /* Make the new heap smaller */
      if (n == 0) return; /* When the heap is empty, we are done */
      t = arr[n];    /* Save last value (it will be overwritten) */
      arr[n] = arr[0]; /* Save largest value at the end of arr */
#ifdef COUNT
ADD_SWAP;
#endif /* COUNT */
    }
 
    parent = i; /* We will start pushing down t from parent */
    child = i*2 + 1; /* parent's left child */
 
    /* Sift operation - pushing the value of t down the heap */
    while (child < n) {
      if (child + 1 < n  &&  cmp(arr[child + 1], arr[child]) > 0) {
	child++; /* Choose the largest child */
      }
      if (cmp(arr[child], t)> 0) { /* If any child is bigger than the parent */
#ifdef COUNT
ADD_SWAP;
#endif /* COUNT */
	arr[parent] = arr[child]; /* Move the largest child up */
	parent = child; /* Move parent pointer to this child */
	child = parent*2 + 1; /* Find the next child */
      } else {
	break; /* t's place is found */
      }
    }
    arr[parent] = t; /* We save t in the heap */
  }
}
