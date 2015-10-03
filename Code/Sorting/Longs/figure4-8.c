/**
 * @file figure4-8.c    Generate Figure 4-8 of the book
 * @brief
 *    Example (test case) for median sort and produces image.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>

#include "assert.h"
#include "dot.h"

/* Depends upon external median sort to sort the array. */
extern void mediansort (int id, long *ar, 
			int (*cmp) (const long *a, const long *b),
			int left, int right);

/** used only for longs. */
int cmp (const long *a, const long *b) {
  return *a - *b;
}

/** Debugging output. */
void debug (long *a, int n) {
  int i = 0;
  for (i = 0; i < n; i++) {
    printf ("%ld ", a[i]);
  }
  printf ("\n");
}


/** Launch the application. */
int main (int argc, char **argv) {
  long ar[] = {6,5,8,2,4,1,7,3};
  int i;
  int n = 8;

  dot_header("medianSort");
  mediansort (1, ar, cmp, 0, n-1);
  dot_trailer();

  for (i = 0; i < n-1; i++) {
    assert (ar[i] <= ar[i+1]);
  }

  return 0;
}
