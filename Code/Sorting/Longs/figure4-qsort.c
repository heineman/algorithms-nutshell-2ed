/**
 * @file figure4-qsort.c    Generate Small example(s) for Quicksort.
 * @brief
 *    Several test cases for Quicksort. Used in debug mode (step by step)
 *    when manually generating the figures in the book.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include "dot.h"

/** externally defined implementation. Can't be 'qsort' since that is a standard unix library call. */
extern void do_qsort (int id, long *ar, 
		      int (*cmp) (const long a, const long b),
		      int left, int right);

/** Comparison function for longs. */
int cmp (const long a, const long b) {
  return a - b;
}

/** Useful debugging code. */
void debug (long *a, int n) {
  int i = 0;
  for (i = 0; i < n; i++) {
    printf ("%ld ", a[i]);
  }
  printf ("\n");
}


int main (int argc, char **argv) {
  long ar[] = {15,9,8,1,4,11,7,12,13,6,5,3,16,2,10,14};
  int i;
  int n = 16;
  
  if (argc >1) {
    srandom (atoi(argv[1]));
  }

  dot_header("QuickSort");
  do_qsort (1, ar, cmp, 0, n-1);
  dot_trailer();


  for (i = 0; i < 15; i++) {
    assert (ar[i] <= ar[i+1]);
  }

  return 0;
}
