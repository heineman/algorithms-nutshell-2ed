/**
 * @file searchOrdered.c    Task to perform searches in ordered array
 * @brief 
 *    Load up an array of strings and perform number of ordered
 *    searches. No check for NULL is used.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <string.h>

#include "report.h"

/**
 * Problem: Linear search in an ordered array.
 *
 *   Use insertion sort to properly order the elements.
 *   No check for NULL is used
 *
 */

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


/** insert strings one at a time into its proper ordered spot within array. */
void insert (char *s) {
  int i;
  for (i = 0; i < dsIdx; i++) {
    if (strcmp (ds[i], s) >= 0) {
      /* insert here. */
      memcpy (&ds[i+1], &ds[i], (dsIdx - i + 1)*sizeof(ds[i]));
      ds[i] = s;
      dsIdx++;
      return;
    }
  }

  /* add at the end!  */
  ds[dsIdx++] = s;
}


/** Search for the target within the linked list. */
int search (char *target, int(*cmp)(const void *,const void *)) {

  int i;

  for (i = 0; i < dsIdx; i++) {
    int c = cmp(ds[i], target);
    if (c == 0) {
      return 1;
    }

    if (c == +1) { return 0;} /* too far! */
  }
  
  return 0;  /* nope */
}
