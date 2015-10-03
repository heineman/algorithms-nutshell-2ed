/**
 * @file testCountingSort.c    Test the Counting Sort algorithm on small example.
 * @brief
 *    A test case for Counting Sort
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <assert.h>
#include <stdio.h>

/** Definition of counting sort. */
extern void countingSort (int *ar, int n, int k);

/** sample size. */
#define NUM 7

/** Sample code for Figure 4-17 Counting Sort fact sheet. */
int main (int argc, char **argv) {
  int i;
  int ar[NUM] = { 3, 0, 2, 0, 0, 2, 2};
  
  countingSort(ar, NUM, 4);

  for (i = 0; i < NUM-1; i++) {
    printf ("%d ", ar[i]);
  }
  printf ("\n");

  for (i = 0; i < NUM-1; i++) {
    assert (ar[i] <= ar[i+1]);
  }
  return 0;
}
