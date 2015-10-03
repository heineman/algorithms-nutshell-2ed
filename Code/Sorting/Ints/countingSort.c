/**
 * @file countingSort.c   Implementation of CountingSort over integer array
 * 
 * @brief
 *    Implementation of counting-sort using integer array where each element
 *    is drawn from the range [0,k).
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>

/** Sort the n elements in ar, drawn from the values [0,k). */
void countingSort (int *ar, int n, int k) {
  int i, idx = 0;
  int *B = calloc (k, sizeof (int));

  for (i = 0; i < n; i++) {
    B[ar[i]]++;
  }

  for (i = 0; i < k; i++) {
    while (B[i]-- > 0) {
       ar[idx++] = i;
    }
  }

  free(B);
}

