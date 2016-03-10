/**
 * @file PointerBased/merge.c   Merge Sort implementation
 * @brief 
 *   Contains Merge Sort implementation.
 *
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <sys/types.h>
#include <stdlib.h>
#include <string.h>
#include "report.h"

int(*merge_cmp)(const void *,const void *);

/** Merge sort A[start,end) into result. */
void mergesort_ptr (void **A, void **result, int start, int end) {
  int i, j, idx, mid;

  if (end - start < 2) { return; }

  if (end - start == 2) {
    if (merge_cmp(result[start], result[start+1]) > 0) {
#ifdef COUNT
    ADD_SWAP;
#endif 
      void *tmp = result[start];
      result[start] = result[start+1];
      result[start+1] = tmp;
      return;
    }
  }

  mid = (end + start)/2;
  mergesort_ptr (result, A, start, mid);
  mergesort_ptr (result, A, mid, end);

  /* merge A left- and right- side */
  i = start;
  j = mid;
  idx = start;

  while (idx < end) {
    if ((j >= end) || (i < mid && merge_cmp(A[i], A[j]) < 0)) {
#ifdef COUNT
    ADD_SWAP;
#endif 
      result[idx] = A[i];
      i++;
    } else {
#ifdef COUNT
    ADD_SWAP;
#endif 
      result[idx] = A[j];
      j++;
    }

    idx++;
  }
}



/** Execute Merge Sort to sort the pointers in the array. */
void sortPointers (void **ar, int n, 
		   int(*cmp)(const void *,const void *)) {
  void **copy = (void **) malloc (n * sizeof(char *));

  merge_cmp = cmp;

  memmove (copy, ar, n*sizeof (char *));
  mergesort_ptr (copy, ar, 0, n);

  free(copy);
}
