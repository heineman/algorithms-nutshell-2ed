/**
 * @file binarySearchFileInteger.c    Task to perform number of binary search operations on a disk file with integers.
 * @brief 
 *    Receive integers one by one (must be in sorted order!) to be written
 *    to disk where a file "input.dat" is first created and then it is used
 *    as the source for a disk-based binary search of integers.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "report.h"

/** Current integer to be added to the generated input file. */
static int idx = 0;

/** The number of elements to be generated/found in the input file. */
static int num = 0;

/** Record whether the input file has been created or not. */
static int reported = 0;

/** handle to the file being generated/read. */
static FILE *fp;

/** 
 * Location of file that is created during 'construction' phase and which 
 * is loaded during the 'search' phase.
 */
static char *input = "input.dat";

/** construct the initial instance. Creates a file on disk into which binary integer values are to be written. */
void construct (int n) {
  if (reported) { return; }
  num = n;

  fp = fopen (input, "w");
  if (fp == 0) {
    reported = 1;
  }
}

/** 
 * Insert values one at a time into the search structure. 
 *
 * In our case, we are receiving the values ALREADY IN SORTED ORDER
 * in which case we simply write into the FileHandle associated with 
 * the generated file.
 *
 * \param value   Value to be inserted.
 */
void insert (int value) {
  fwrite (&value, sizeof(int), 1, fp);
  idx++;
  if (idx >= num) {
    fflush (fp);    /* flush once created. */
    fclose (fp);
    fp = fopen (input, "r");
  }
}

/**
 * Search for the desired target within the search structure. 
 *
 * \param target   the desired target
 * \param cmp      the comparison function between two string elements.
 */
int search (int target, int(*cmp)(const int,const int)) {

  int low = 0, high = (num - 1);
  while (low <= high) {
    int val, rc;
    int ix = (low + high)/2;

    /* seek and read four bytes. */
    fseek (fp, ix*sizeof(int), SEEK_SET);
    fread (&val, sizeof(int), 1, fp);

    rc = cmp(target, val);

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
