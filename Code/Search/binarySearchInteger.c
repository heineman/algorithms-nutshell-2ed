/**
 * @file binarySearchInteger.c    Task to perform number of binary search operations on an array
 * @brief 
 *    Receive integers one by one (in sorted order) and create an array over
 *    which binary searches are performed.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <string.h>

#include "report.h"

/** Ultimate storage destination of elements in an array. */
static int *ar;

/** Current location into which element is to be inserted. */
static int idx = 0;

/** Size of the element storage, ar. */
static int num = 0;

/** construct the initial instance. Allocate space for an array of integers. */
void construct (int n) {
  num = n;
  ar = malloc (n*sizeof(int));
  idx = 0;
}

/** 
 * Insert values one at a time into the search structure. 
 *
 * These are handed to us in source order, so we simply place the integer
 * in the next slot location.
 * 
 * \param value   Value to be inserted.
 */
void insert (int value) {
  ar[idx++] = value;
}


/**
 * Search for the desired target within the search structure. 
 *
 * \param target   the desired target
 * \param cmp      the comparison function between two string elements.
 */
int search (int target, int(*cmp)(const int,const int)) {

  int low = 0, high = num - 1;
  while (low <= high) {
    int ix = (low + high)/2;
    int rc = cmp(target, ar[ix]);

    if (rc < 0) {
      /* target is less than collection[i] */
      high = ix - 1;
    } else if (rc > 0) {
      /* target is greater than collection[i] */
      low = ix + 1;
    } else {
      /* found the item. */
      return 1;
    }
  }

  return 0;  /* nope. */
}
