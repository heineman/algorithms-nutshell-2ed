/**
 * @file insertionPtr.c   Insertion Sort implementation
 * @brief 
 *   Contains Insertion Sort implementation.
 *
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include "report.h"

/** Execute InsertionSort to sort the pointers in the array. */
void sortPointers (void **ar, int n, 
		   int(*cmp)(const void *,const void *)) {
  int j;
  for (j = 1; j < n; j++) {
    int i = j-1;
    void *value = ar[j];
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
