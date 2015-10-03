/**
 * @file Sorting/Ints/quickSort.c    Use native qsort() function to sort integer array.
 * 
 * @brief
 *    This implementation simply invokes the qsort() function available from
 *    the Unix operating system. Note that on some system variants, the qsort
 *    routine is actually implemented using Heap Sort.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>

/** Comparator function for comparing integer values. */
int cmp (const void *n1, const void *n2) {
  int r1 = * (const int *) n1;
  int r2 = *(const int *) n2;
  
  /* satisfies interface */
  return r1 - r2;
}

/** Natively invoke qsort routine. */
void sort (int *ar, int n) {
  qsort (ar, n, sizeof(int), &cmp);
}
