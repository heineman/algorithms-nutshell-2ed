/**
 * @file Sorting/ValueBased/merge.c   Merge Sort implementation over value-based arrays.
 * @brief 
 *   Contains Merge Sort implementation for value-based arrays. 
 * 
 * @author George Heineman
 * @date 6/9/15
 */

#include <sys/types.h>
#include <stdlib.h>
#include <string.h>
#include "report.h"

char *mergeSortBuf;

int(*merge_cmp)(const void *,const void *) = NULL;

/** Merge sort A[start,end) into result. */
void mergesort_array (char *A, char *result, int start, int end, int s) {
  int mid;
  char *Ai, *Aj, *Amid, *Aend, *resultEnd;

  if (end - start < 2) { return; }

  if (end - start == 2) {
    char *left = result + start*s;
    char *right = left + s;
    if (merge_cmp(left, right) > 0) {
#ifdef COUNT
    ADD_SWAP;
#endif 
      memmove (mergeSortBuf, left, s);
      memmove (left, right, s);
      memmove (right, mergeSortBuf, s);
      return;
    }
  }

  mid = (end + start)/2;
  mergesort_array (result, A, start, mid, s);
  mergesort_array (result, A, mid, end, s);

  /* merge A left- and right- side */
  Ai = A + start*s;
  Aj = A + mid*s;
  Amid = A + mid*s;
  Aend = A + end*s;
  resultEnd = result + end*s;

  result  += start*s;
  while (result < resultEnd) {
    if ((Aj >= Aend) || (Ai < Amid && merge_cmp(Ai, Aj) < 0)) {
#ifdef COUNT
    ADD_SWAP;
#endif 
      memcpy(result, Ai, s);
      Ai += s;
    } else {
#ifdef COUNT
    ADD_SWAP;
#endif 
      memcpy(result, Aj, s);
      Aj += s;
    }

    result += s;
  }
}

/** Sort the value-based array using Merge Sort. */
void sortValues (void *base, int n, int s,
		 int(*cmp)(const void *,const void *)) {
  char *copy = (char *) malloc (n*s);

  merge_cmp = cmp;

  mergeSortBuf = (char *) malloc (s);
  memmove (copy, base, n*s);

  mergesort_array ((char *) copy,  (char *) base, 0, n, s);

  free (copy);
}

