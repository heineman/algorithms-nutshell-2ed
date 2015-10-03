/**
 * @file buildValueBasedInput.c    Driver to construct contiguous string block for sorting.
 * @brief 
 *    Build up a value-based array of strings to use as a basis for testing
 *    the various sorting algorithms.
 *
 * Build up a value-based array of Strings. This means that a fixed
 * elementSize 
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
 *   <li>-u n,o  When paired with [a/d] determines number of pairs out of order,
 *           and distance of entity with which to swap [if o=0 then random]
 * </ol>
 * External API
 * <ol>
 *   <li> void problemUsage()           shows what is expected
 *   <li> void postInputProcessing()    performs post-solution processing
 *   <li> void prepareInput()           prepare the input
 *   <li> void execute()                begin the problem
 * </ol>
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <getopt.h>
#include <stdio.h>
#include <string.h>
#include "buildValueBasedInput.h"
#include "problem.h"
#include "report.h"

/** information stored in memory. */
struct strElement *strings;

/** comparator function for comparing string based upon ELEMENT_SIZE. */
int stringComp (char *a1, char *a2) {
#ifdef COUNT
ADD_COMP;
#endif  /* COUNT */
  return strncmp (a1, a2, ELEMENT_SIZE);
}

/** comparator function for ascending order. */
int ascending (const void *a1, const void *a2) {
  return strncmp ((char *) a1, (char *) a2, ELEMENT_SIZE);
}

/** comparator function for descending order. */
int descending (const void *a1, const void *a2) {
  return strncmp ((char *) a2, (char *) a1, ELEMENT_SIZE);
}

/**
 * Take sorted/ascending set and permute into killer-of-median-quicksort
 * order.
 *
 * implementation derived from:
 *     http://ralphunden.net/content/tutorials/a-guide-to-introsort/
 *
 *   Note that the data is contiguous and we must swap as required large
 *   sequence of bytes (oh well; it won't count against costs).
 */
void killer (struct strElement *combined, int numElements) {

  int i, k;
  char *swap;
  if (numElements %2 == 1) {
    printf ("You can only use the \"-k\" killer data set with an even number of elements\n");
    exit(-1);
  }

  k =numElements/2;
  swap = (char *) calloc (numElements, ELEMENT_SIZE+1);

  for (i=1; i<=k; i++) {
    if (i%2) {
      memcpy (swap+(i-1)*ELEMENT_SIZE, combined+(i-1)*ELEMENT_SIZE,ELEMENT_SIZE);
      memcpy (swap+(i)*ELEMENT_SIZE, combined+(k+i-1)*ELEMENT_SIZE,ELEMENT_SIZE);
    }
    memcpy (swap+(k+i-1)*ELEMENT_SIZE, combined+(2*i-1)*ELEMENT_SIZE,ELEMENT_SIZE);
  }

  /* now swap accordingly */
  memcpy (combined, swap, numElements*ELEMENT_SIZE+1);
  free (swap);
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
  int asc=1, desc=-1, killerOrder=-2;
  int i, s;
  char c, *p;
  char *baseString = "abcdefghijklmnopqrstuvwxyz";
  int sorted = 0;
  int numOutOfOrder = 0;
  int distance = 0;

  while ((c = getopt(argc, argv, "adku:")) != -1) {
    switch (c) {
    case 'a':
      sorted = asc;
      break;

    case 'd':
      sorted = desc;
      break;
      
    case 'k':
      sorted = killerOrder;
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
  strings = (struct strElement *) calloc (size, sizeof (struct strElement));

  if (verbose) {
    printf ("Constructing array of %d entries\n", size);
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

    strncpy (strings[i].s, mixed, ELEMENT_SIZE-1);
    strings[i].s[ELEMENT_SIZE-1] = '\0';  /* cut-off */
    free (mixed);
  }

  if (sorted) {
    if (verbose) { printf ("sorting first...\n"); }
    if (sorted == asc) {
      qsort (strings, numElements, ELEMENT_SIZE, ascending);
    } else if (sorted == desc) {
      qsort (strings, numElements, ELEMENT_SIZE, descending);
    } else if (sorted == killerOrder) {
      killer (strings, numElements);
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

      /*  swap characters. */
      if (verbose) { printf ("swap %d with %d\n", i, j); }
      for (s = 0; s < ELEMENT_SIZE; s++) {
	c = strings[i].s[s];
	strings[i].s[s] = strings[j].s[s];
	strings[j].s[s] = c;
      }
    }
  }
}

/** After sorting, can check whether sort succeeded. */
void postInputProcessing() {
  int i, top;

  if (verbose) {
    printf ("validate sorting worked. Here are first 10 entries\n");
    top = 10;
    if (numElements < top) { top = numElements; } 
    for (i = 0; i < top; i++) {
      printf ("%s\n", strings[i].s);
    }

    /* check sorted criteria */
    for (i = 0; i < numElements-1; i++) {
      if (stringComp(strings[i].s, strings[i+1].s) > 0) {
	printf ("Sort failed at position %d: %s,%s\n", i,
		strings[i].s, strings[i+1].s);
	break;
     }
    } 
  }
}

/** Sort the array of numElement doubles. */
void execute() {
  sortValues (strings, numElements, sizeof(struct strElement), stringComp);
}

/** Define command-line arguments for problem. */
void problemUsage() {
  printf ("   -a declares that the array should be sorted ascending for worst case behavior. Incompatible with -d\n");
  printf ("   -d declares that the array should be sorted ascending for worst case behavior. Incompatible with -a\n");
  printf ("   -u #,# determines number of pairs out of order, if sorted, and distance with which to swap [if o=0 then random].\n");
  printf ("   -w instead of random permutations of alphabet string, draw words from 160,136 English word dictionary\n");
}

