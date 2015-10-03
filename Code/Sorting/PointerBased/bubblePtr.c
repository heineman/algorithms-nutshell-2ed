/**
 * @file bubblePtr.c   Contain Bubble Sort implementation
 * @brief 
 *   Though the book never refers to Bubble Sort (for which we are proud), we
 *   had to just include the code for that ill-fated algorithm to show just
 *   how bad some sorting algorithms can be.
 *
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include "report.h"

/**
 * Sort by using Bubble Sort. 
 *
 * Only included to show just how bad some sorting algorithms can be.
 */
void sortPointers (char **vals, int total_elems, 
		   int(*cmp)(const void *,const void *)) {
  int i, j;

  for (i = 0; i < total_elems-1; i++) {
    for (j = i+1; j < total_elems; j++) {
      if (cmp(vals[i], vals[j]) > 0) {
	char *tmp = vals[i];
	vals[i] = vals[j];
	vals[j] = tmp;
      }
    }
  }

}
