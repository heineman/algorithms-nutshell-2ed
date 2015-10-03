/**
 * @file searchNull.c    Task to perform searches in unordered array
 * @brief 
 *    Load up an array of strings and perform number of unordered
 *    searches. Check for NULL is used.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <stdlib.h>

#include "problem.h"   /* for isNil */
#include "report.h"

/** Array to contain final search structure. */
static char **ds;

/** Size of the array. */
static int dsSize;

/** Position into the array into which the next string is to be inserted. */
static int dsIdx;


/** construct the initial instance from the number of elements. */
void construct (int n) {
  ds = (char **) calloc (n, sizeof (char **));
  dsSize = n;
  dsIdx = 0;
}

/** insert strings one at a time to the proper position in the array. */
void insert (char *s) {
  ds[dsIdx++] = s;
}

/** Search for the target within the linked list. Check for NULL each time. */
int search (char *target, int(*cmp)(const void *,const void *)) {
  int i;
  for (i = 0; i < dsSize; i++) {
    if (!isNil(ds[i]) && !cmp(ds[i], target)) {
      return 1;
    }
  }
  
  return 0;  /* nope */
}
