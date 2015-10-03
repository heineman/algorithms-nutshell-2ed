/**
 * @file moveToFront.c    Task to perform searches in unordered array and move to front when found.
 * @brief 
 *    Load up an array of strings and perform number of unordered
 *    searches. No check for NULL is used. Move to Front of array on 
 *    a successful find.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <string.h>

#include "report.h"

/** Storage of string array. */
static char **ds;

/** Number of strings in the array 'ds'. */
static int dsSize;

/** Position into 'ds' into which the next string will be inserted. */
static int dsIdx;

/** construct the initial instance. Allocate array of 'n' elements for 'ds'. */
void construct (int n) {
  ds = (char **) calloc (n, sizeof (char **));
  dsSize = n;
  dsIdx = 0;
}


/** insert strings one at a time to the next position within the array. */
void insert (char *s) {
  ds[dsIdx++] = s;
}


/** Search for the target within the array. No Check for NULL. Move to Front when found via block moves. */
int search (char *target, int(*cmp)(const void *,const void *)) {

  int i;

  for (i = 0; i < dsIdx; i++) {
    char *copy;
    if (!cmp(ds[i], target)) {

      if (i == 0) return 1;  /*  nothing to move! */

      copy = ds[i];
      bcopy (&ds[0], &ds[1], i*sizeof(ds[i]));
      ds[0] = copy;

      return 1;
    }
  }
  
  return 0;  /* nope */
}
