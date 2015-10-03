/**
 * @file figure4-heapsort.c    Generate Small example(s) for heapsort.
 * @brief
 *    Several test cases for heapsort. Used in debug mode (step by step)
 *    when manually generating the figures in the book.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <assert.h>

/** defined here so we can reuse code compiled for use with benchmarking. */
long __swapTotal;

/** externally defined routine for sorting pointer-based values. */
extern void sortPointers (long *ar, int num, int (*cmp) (const long *a, const long *b));

/** Comparison function for ascending long order. */
int cmp (const long *a, const long *b) {
  if (a < b) {
    return -1;
  } else if (a > b) {
    return +1;
  }

  return 0;
}

/** Useful debugging function. */
void debug (long *a, int n) {
  int i = 0;
  for (i = 0; i < n; i++) {
    printf ("%ld ", a[i]);
  }
  printf ("\n");
}


/** Launch the test cases. */
int main (int argc, char **argv) {
  long ar[] = {15, 9,8,1,4,11,7,12,13,6,5,3,16,2,10,14};
  long ar2[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
  long br[] = {5,3,16,2,10,14};
  int i;

  sortPointers (ar, 16, cmp);
  debug (ar, 16);
  for (i = 0; i < 15; i++) {
    assert (ar[i] <= ar[i+1]);
  }

  sortPointers (ar2, 16, cmp);
  debug (ar2, 16);
  for (i = 0; i < 15; i++) {
    assert (ar2[i] <= ar2[i+1]);
  }

  sortPointers (br, 6, cmp);
  debug (br, 6);
  for (i = 0; i < 5; i++) {
    assert (br[i] <= br[i+1]);
  }

  return 0;
}
