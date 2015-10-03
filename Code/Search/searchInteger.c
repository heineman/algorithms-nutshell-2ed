/**
 * @file searchInteger.c    Task to perform searches in unordered array
 * @brief 
 *    Load up an array of integers and perform number of unordered
 *    searches.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include "report.h"

/** Array to contain final search structure. */
static int *ds;

/** Size of the array. */
static int dsSize;

/** Position into the array into which the next string is to be inserted. */
static int dsIdx;

/** construct the initial instance from the number of elements. */
void construct (int n) {
  ds = (int *) calloc (n, sizeof (int));
  dsSize = n;
  dsIdx = 0;
}


/** insert ints one at a time to the proper position in the array. */
void insert (int s) {
  ds[dsIdx++] = s;
}


/** Search for the target within the array. */
int search (int target, int(*cmp)(const int, const int)) {
  int i;
  for (i = 0; i < dsIdx; i++) {
    if (!cmp(ds[i], target)) {
      return 1;
    }
  }
  return 0;  /* nope */
}
