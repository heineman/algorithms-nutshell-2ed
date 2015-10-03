/**
 * @file Sorting/ValueBased/insertion.c   Insertion Sort implementation over value-based arrays. Optimized for bulk moves.
 * @brief 
 *   Contains Insertion Sort implementation for value-based arrays.  Optimized
 *   to use memmove for bulk moves.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <sys/types.h>
#include <stdlib.h>
#include <string.h>
#include "report.h"

/** Sort the value-based array using Insertion Sort. */
void sortValues (void *base, int n, int s,
		 int(*cmp)(const void *,const void *)) {
  int j;
  void *saved = malloc (s);
  for (j = 1; j < n; j++) {
    /* start at end, work backward until smaller element or i < 0. */
    int i = j-1;
    void *value = base + j*s;
    while (i >= 0 && cmp(base + i*s, value) > 0) { i--; } 

    /* If already in place, no movement needed. Otherwise save value to be
     * inserted and move as a LARGE block intervening values.  Then insert
     * into proper position. */
    if (++i == j) continue;

    memmove (saved, value, s);
    memmove (base+(i+1)*s, base+i*s, s*(j-i));
    memmove (base+i*s, saved, s);

#ifdef COUNT
    ADD_SWAP;
#endif 
  }
  free (saved);
}
