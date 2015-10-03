/**
 * @file selectionSort.c      Contains Selection Sort implementation.
 * @brief
 *  Straight Selection Sort implementation.
 *
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include "report.h"

/** Select maximum from ar[left,right] to be used in Selection Sort. */
static int selectMax (void **ar, int left, int right,
		   int (*cmp)(const void *,const void *)) {

  int  maxPos = left;
  int  i = left;
  while (++i <= right) {
    if (cmp(ar[i], ar[maxPos])> 0) {
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */
      maxPos = i;
    }
  } 

  return maxPos;
}

/** Sort using Selection Sort. */
void sortPointers (void **ar, int n, 
		   int(*cmp)(const void *,const void *)) {
  int i;

  /* repeatedly select maximum in sub-array and swap with proper position */
  for (i = n-1; i >=1; i--) {
    int maxPos = selectMax (ar, 0, i, cmp);
    if (maxPos != i) {
      void *tmp = ar[i];
      ar[i] = ar[maxPos];
      ar[maxPos] = tmp;
    }
  }
}
