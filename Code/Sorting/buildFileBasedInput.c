/**
 * @file buildFileBasedInput.c    Driver to construct array of Strings on disk
 * @brief 
 *    Build up a file on disk representing an array of strings to test 
 *    the various sorting algorithms.
 *
 * Required Input:
 * <ol>
 *   <li>int numElements
 *   <li>int verbose
 * </ol>
 *
 * Input flags:
 * <ol>
 *   <li>-a      Numbers are sorted in ascending order
 *   <li>-d      Numbers are sorted in descending order
 *   <li>-f      File into which data is written
 *   <li>-u n,o  When paired with [a/d] determines number of pairs out of order,
 *           and distance of entity with which to swap [if o=0 then random]
 * </ol>
 * External API
 * <ol>
 *   <li>void problemUsage()           shows what is expected
 *   <li>void postInputProcessing()    performs post-solution processing
 *   <li>void prepareInput()           prepare the input
 *   <li>void execute()                begin the problem
 * </ol>
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <string.h>

#include "buildFileBasedInput.h"
#include "problem.h"
#include "report.h"

/** information is stored on disk accessed via FILE handle. */
FILE *strings;

/** comparator function, by offset into file and AGAINST string in memory. */
int fileCompString (const long pos, const char *a2) {
  int idx;
#ifdef COUNT
ADD_COMP;
#endif  /* COUNT */

 fseek (strings, pos, SEEK_SET);
 idx = 0;
 while (idx < ELEMENT_SIZE) {
   char ch = fgetc(strings);
   if (ch > a2[idx]) { return ch - a2[idx]; }
   if (ch < a2[idx]) { return ch - a2[idx]; }
   idx++;
 }

 return 0; /* same */
}

/** comparator function for the ascending case. */
int ascending (const long pos, const void *a2) {
  return fileCompString (pos, a2);
}

/** comparator function for the descending case. */
int descending (const long pos, const void *a2) {
  return -fileCompString (pos, a2);
}


/**
 * Create the input set: an array of given size with random strings. 
 *
 * use character-swapping algorithm from strfry.c in glibc
 * 
 * http://www.koders.com/c/fidBD83E492934F9F671DE79B11E6AC0277F9887CF5.aspx
 *
 */
void prepareInput (int size, int argc, char **argv) {
  int i, s;
  char c, *p;
  char *baseString = "abcdefghijklmnopqrstuvwxyz";
  char *sn = "default.in";
  int sorted = 0;
  int numOutOfOrder = 0;
  int distance = 0;

  while ((c = getopt(argc, argv, "adf:u:")) != -1) {
    switch (c) {
    case 'a':
      sorted = 1;
      break;

    case 'd':
      sorted = -1;
      break;
      
    case 'f':
      sn = strdup (optarg);
      break;

    case 'u':
      p = strstr(optarg, ",");
      *p = '\0';
      numOutOfOrder = atoi(optarg);
      distance = atoi(p+1);
      *p = ',';
      break;

    default:
      break;
    }
  }
  optind = 0;  /* reset getopt for next time around. */

  /* create numElements copies of a random string */
  strings = fopen (sn, "r+");
  if (strings == 0) {
    strings = fopen (sn, "w+");
  }

  if (verbose) {
    printf ("Constructing file of %d entries\n", size);
  }

  for (i = 0; i < size; i++) {
    char *mixed = strdup(baseString);

    for (s = 0; s < ELEMENT_SIZE; s++) {
      int j;
      char c;

      /* Compute random value */
      j = 1 + (int) (ELEMENT_SIZE * (rand() / (RAND_MAX + 1.0)));
      j %= ELEMENT_SIZE;

      c = mixed[s];
      mixed[s] = mixed[j];
      mixed[j] = c;
    }

    fwrite (mixed, ELEMENT_SIZE, 1, strings);
    fflush(strings);
    free (mixed);
  }

  if (sorted) {
    if (verbose) { printf ("sorting first...\n"); }
    if (sorted == 1) {
      /* sort ascending */
      printf ("SHOULD sort ascending here...");
    } else {
      printf ("SHOULD sort descending here...");
    }

    /* now make numOutOfOrder swaps... */
    while (numOutOfOrder-- > 0) {
      int j;
      /* Compute random value */
      i = 1 + (int) (numElements * (rand() / (RAND_MAX + 1.0)));
      i %= numElements;

      /* find distance (either up or down) from i. */
      if (distance == 0) {
	j = 1 + (int) (numElements * (rand() / (RAND_MAX + 1.0)));
	j %= numElements;
      } else {
	j = i + distance;
	if (j > numElements-1) { j = i - distance; if (j < 0) { j = 0;}} 
      }

      /* swap characters. */
      if (verbose) { printf ("swap %d with %d\n", i, j); }
      for (s = 0; s < ELEMENT_SIZE; s++) {
	char *copy_i, *copy_j;

	fseek (strings, i*ELEMENT_SIZE, SEEK_SET);
	copy_i = (char *) calloc (ELEMENT_SIZE, sizeof(char));
	copy_j = (char *) calloc (ELEMENT_SIZE, sizeof(char));
	fread (copy_i, ELEMENT_SIZE, 1, strings);
	fseek (strings, j*ELEMENT_SIZE, SEEK_SET);
	fread (copy_j, ELEMENT_SIZE, 1, strings);

	fseek (strings, i*ELEMENT_SIZE, SEEK_SET);
	fwrite (copy_j, ELEMENT_SIZE, 1, strings);
	fflush(strings);
	fseek (strings, j*ELEMENT_SIZE, SEEK_SET);
	fwrite (copy_i, ELEMENT_SIZE, 1, strings);
	fflush(strings);
	free (copy_i);
	free (copy_j);
      }
    }
  }

  /*  make sure all is written. Though file is open still..  */
  fflush (strings);
}


/** Validate that sorting was successful. */
void postInputProcessing() {
  int i, top;

  if (verbose) {
    char *copy;
    printf ("validate sorting worked. Here are first 10 entries\n");
    top = 10;
    if (numElements < top) { top = numElements; } 
    fseek (strings, 0, SEEK_SET);
    
    copy = (char *) calloc (ELEMENT_SIZE+1, sizeof(char));
    for (i = 0; i < top; i++) {
      fseek (strings, i*ELEMENT_SIZE, SEEK_SET);
      fread (copy, ELEMENT_SIZE, 1, strings);
      copy[ELEMENT_SIZE] = 0;
      printf ("%s\n", copy);
    }
    free (copy);
  }
}

/** Sort the file using the given comparator string function. */
void execute() {
  sortFile (strings, numElements, ELEMENT_SIZE, fileCompString);
}

/** Report special command-line arguments for this driver. */
void problemUsage() {
  printf ("   -f declares that the output should be stored in given file [default is 'default.in'.\n");
  printf ("   -d declares that the array should be sorted ascending for worst case behavior. Incompatible with -a\n");
  printf ("   -a declares that the array should be sorted ascending for worst case behavior. Incompatible with -d\n");
  printf ("   -u #,# determines number of pairs out of order, if sorted, and distance with which to swap [if o=0 then random].\n");
}

