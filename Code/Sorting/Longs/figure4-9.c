/**
 * @file figure4-9.c    Generate Figure 4-9 of the book
 * @brief
 *    Example (test case) for median sort and produces image.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <assert.h>

#include "dot.h"

/** externally provided */
extern void mediansort (int id, long *ar, 
			int (*cmp) (const long *a, const long *b),
			int left, int right);

/** used only for longs for ascending sorting. */
int cmp (const long *a, const long *b) {
  return *a - *b;
}

/** Useful function for debugging. */
void debug (long *a, int n) {
  int i = 0;
  for (i = 0; i < n; i++) {
    printf ("%ld ", a[i]);
  }
  printf ("\n");
}


/** Run the test case. */
int main (int argc, char **argv) {
  long ar[] = {15,9,8,1,4,11,7,12,13,6,5,3,16,2,10,14};
  int i;
  int n = 16;

  dot_header("medianSort");
  mediansort (1, ar, cmp, 0, n-1);
  dot_trailer();

  for (i = 0; i < n-1; i++) {
    assert (ar[i] <= ar[i+1]);
  }

  return 0;
}
