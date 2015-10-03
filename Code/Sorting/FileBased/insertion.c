/**
 * @file Sorting/FileBased/insertion.c    Define code to produce a file containing array of strings which are to be sorted.
 * 
 * @brief
 *    Implementation of file-based sorting algorithm, where input is stored
 *    on disk in a contiguous block of n*s bytes where n is the number of 
 *    elements in the disk and s is the length of each one (no null-terminated
 *    values used to determine string length).
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#include "report.h"

/** Insert in-place, where value already exists in list at location loc. */
static void insert (FILE *strings, int loc, int s, char *saved,
		    int (*cmp)(const long,const char *)) {

  // start at end, and work backwards until find a smaller element or i < 0.
  long i = (loc-1)*s;
  int numToMove = 0;
  while (i >= 0 && cmp(i, saved) > 0) {
    i-=s;
    numToMove+=s;
  } 

  // If already in place, no movement needed. Otherwise save value to be
  // inserted and move as a LARGE block intervening values.  then insert
  // into proper position.
  i += s;
  if (i == loc*s) { return; }

  // try to allocate all in memory
  // and blast out again.
  char *sn = (char *) calloc (numToMove, sizeof(char));
  fseek (strings, i, SEEK_SET);
  fread (sn, numToMove, 1, strings);

  fseek (strings, i+s, SEEK_SET);
  fwrite (sn, numToMove, 1, strings);
  fflush(strings);

  fseek (strings, i, SEEK_SET);
  fwrite (saved, s, 1, strings);
  fflush(strings);

#ifdef COUNT
ADD_SWAP;
#endif 
}

/**
 * Sort the given file of n elements (each of which is of size s) using given comparator function.
 *
 * \param strings File on disk containing n strings of size s.
 * \param n       Number of strings in this file.
 * \param s       The fixed size of the strings in the file.
 * \param cmp     Special comparator function that compares string in memory (a2) against location on disk (long pos).
 */
void sortFile (FILE *strings, int n, int s,
	       int(*cmp)(const long,const char *)) {

  int i;
  char *saved  = calloc (s, sizeof(char));
  for (i = 1; i < n; i++) {
    int fs = fseek (strings, i*s, SEEK_SET);
    int rt = fread (saved, s, 1, strings);

    insert (strings, i, s, saved, cmp);
  }
  free (saved);
}
