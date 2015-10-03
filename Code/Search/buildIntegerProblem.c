/**
 * @file buildIntegerProblem.c    Driver that populates a search structure by invoking insert on integers in sorted order.
 * @brief 
 *    Populates a search structure by first inserting numElements integers,
 *    in sorted order. Thereafter, performs a number of search operations as
 *    dictated by the command line arguments.
 * 
 * Build up an array of Integers. Done to eliminate costs of comparisons
 * from the equation
 *
 * Required Input:
 * <ol>  
 *   <li>seed     (-s n)
 *   <li>verbose  (-v)    if you want expressive output
 * </ol>
 * Overridden Input:
 * <ol>
 *   <li>int numElements (-n x)   actually is the number of queries, not elements.
 * </ol>
 *
 * Expected methods 
 * <ol>
 *   <li>extern void construct (int);   -- construct initial collection storage
 *   <li>extern void insert (int val);  -- add integer to collection
 *   <li>extern int search (int val, int(*cmp)(const char *,const char *));
   </ol>                                -- function to initiate search
 * Search considerations
 * <dl>
 *   <dt>Search domain will be {a,b}^k which guarantees the size of |S| will
 *   be a power of 2.
 * </dl>
 * Input flags:
 * <ol>
 *   <li>-p n Ratio in range [0,1] of available elements in target [default: .25]
 *           This reflects the strings from the domain that are to be
 *           searched for. If p=0 then the target strings are never found
 *           in the list. If p=1 then the target strings are always found
 *           in the list. Value of p in between reflect ratio of behavior.
 *
 *   <li>-s   use same failed id (-1) [default: use different ids starting -2, -3, ...]
 *
 *   <li> -f   Always search for the first element [default: search without constraint]
 *
 *   <li>-z n Number of elements in the collection
 * </ol>
 *
 * External API
 * <ol>
 *   <li>void problemUsage()           shows what is expected
 *   <li>void prepareInput()           construct target instance DS by repeated invocation of insert(s)
 *   <li>void postInputProcessing()    performs post-solution processing
 *   <li>void execute()                begin the problem and invoke search methods
 * </ol>
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <stdio.h>
#include <getopt.h>
#include <math.h>
#include <string.h>

#include "problem.h"
#include "report.h"

/** Determine whether output is to be printed as the computation progresses. */
extern int verbose;

/** Method to construct the initial search structure to contain 'sz' elements. */
extern void construct (int sz);

/** Method to insert an integer element into the search structure. These are always inserted in sorted order already. */
extern void insert (int value);

/** Method to search for an integer element in the search structure. */
extern int search (int target, int(*cmp)(const int,const int));

/** Information will be stored as pointer of integers. */
int *searchList;

/** Likelihood that target will be in the collection being searched. */
static float p = 0.25;

/** Should the target always be the first element. */
static int first = 0;

/** Collection Size: default is 50. */
static int z = 50;

/** Computed number found. */
static int numFound = 0;

/** comparator function to use for all searches. */
int intComp (const int a1, const int a2) {
#ifdef COUNT
ADD_COMP;
#endif  /* COUNT */
 return a1 - a2;
}

/**
 * Create the input set: an array of given size with random strings. 
 *
 * use character-swapping algorithm from strfry.c in glibc
 * 
 * http://www.koders.com/c/fidBD83E492934F9F671DE79B11E6AC0277F9887CF5.aspx
 */
void prepareInput (int size, int argc, char **argv) {
  int i, failed, failedIdx, cycleIdx;
  char ch;
  int *integers;
  int sameId = 0;
  int numInCollection = 1000;

  int done = 0;
  opterr = 0;   /* disable errors */
  while (!done && (ch = getopt(argc, argv, "op:fsz:")) != -1) {
    switch (ch) {
      
    case 'p':
      p = atof(optarg);
      break;

    case 's':
      sameId = 1;
      break;

    case 'f':
      first = 1;
      break;

    case 'z':
      numInCollection = atoi(optarg);
      break;

    case '?':
      done = 1;
      break;
    }
  }

  optind = 0;  /* reset getopt for next time around */

  /* -n is the number of queries. */

  /* create integer collection, populated from range [0,numInCollection). */
  integers = calloc (numInCollection, sizeof (int));
  for (i = 0; i < numInCollection; i++) {
    integers[i] = i;
  }

  /* create searchList by cycling through all numbers */
  cycleIdx = 0;
  searchList = (int *) calloc (numElements, sizeof(int));
  for (i = 0; i < numElements; i++) {
    searchList[i] = cycleIdx++;
    if (cycleIdx >= numInCollection) {
      cycleIdx = 0;
    }
  }

  /* now go through and ensure proper number are failed. */
  failed = (int)((1-p)*numElements);
  failedIdx = -1;
				     
  for (i = 0; i < failed; i++) {
    searchList[i] = failedIdx;
    if (sameId) { failedIdx--;  }
  }

  /* Ensure all queries are first. */
  if (first) {
    for (i = 0; i < numElements; i++) {
      searchList[i] = integers[0];
    }
  }

  construct (numInCollection);
  for (i = 0; i < numInCollection; i++) {
    insert(integers[i]);
  }
}

/**
 * Report properly formatted for table
 */
void postInputProcessing(long usecs) {
  int i, top;

  if (verbose) {
    printf ("\n Program Arguments\n");

    printf ("args: %d, %d, %f, %s, %d[%d], %d\n",
	    z, numElements, p, timingString (usecs),
#ifdef COUNT	  
	  COMP_COUNT, NIL_COUNT,
#else
	  0, 0,
#endif
	  numFound);

    printf ("Sample of elements searched for...\n");
    top = numElements;
    if (top > 10) { top = 10; }
    for (i = 0; i < top; i++) {
      printf ("%d\n", searchList[i]);
    }
  }
}

/** Execute the 'numElements' number of searches of targets. */
void execute() {
  int i;

  for (i = 0; i < numElements; i++) {
    numFound += search (searchList[i], intComp);
  }
}

/** Declare the special flags to use for this driver. */
void problemUsage() {
  printf ("\n");
  printf ("Note that problem size (-n) is the NUMBER of searches to execute.\n\n");
  printf ("   -p n Ratio in range [0,1] of available elements in target [default: .25] \n");
  printf ("   -f   Always search for the first element in list [default: 0]\n");
  printf ("   -s   Use same failed id with each failed query [default: false]\n");
  printf ("   -v   Verbose. Print all elements once done [default: no]\n");

  printf ("   -z n Number of elements in the collection. [default: 1000]\n");
}

