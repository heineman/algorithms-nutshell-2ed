/**
 * @file Timing/report.c    Common reporting code
 * @brief 
 *    Implements a standard reporting approach used throughout the repository.
 *
 *
 * @author George Heineman
 * @date 6/15/08
 */


#include <stdio.h>
#include <string.h>

#include "report.h"


#ifndef DEFINED_VARS
#define DEFINED_VARS
long __compTotal = 0;
long __compNilTotal = 0;
long __swapTotal = 0;
#endif /* DEFINED_VARS */

/**
 * Compute the difference between two time values in usecs. 
 * \param before   Time before a computation started
 * \param after   Time when a computation completed
 * \return        difference of these two times in long microseconds.
 */
long diffTimer (struct timeval *before, struct timeval *after) {
  long ds = after->tv_sec - before->tv_sec;
  long uds = after->tv_usec - before->tv_usec;

  /* if secs are same, then return simple delta */
  if (ds == 0) {
    return uds;
  }

  /* ok, so we are turned over. Something like: */
  /* before: 1179256745 744597                  */
  /* after:  1179256746 73514                   */

  /* if we have 'turned over' then account properly */
  if (uds < 0) {
    ds = ds - 1;
    uds += 1000000;
  }

  return 1000000*ds + uds;
}

/* more than enough for packing integers. */
static char packed[64];

/**
 * Convert microseconds into string showing seconds and microseconds.
 *
 * \param usecs    absolute time amount in microseconds
 * \return         a char* string representing "N.M" seconds.
 */
char *timingString(long usecs) {
  int i;
  long secs = usecs / 1000000;
  usecs = usecs - 1000000*secs;
  sprintf (packed, "%ld.%6ld secs", secs, usecs);
  for (i = 0; i < 10; i++) {
    /* pad with zeroes. */
    if (packed[i] == ' ') { packed[i] = '0'; }
  }
  return packed;
}

/** 
 * Print the time difference.
 */
void printDiffTimer (long usecs) {
  printf ("%s\n", timingString(usecs));
}


#ifdef COUNT
void printComparisons() {
  printf ("There were a total of %ld comparisons\n", COMP_COUNT);
  printf ("There were a total of %ld Nil comparisons\n", NIL_COUNT);
  printf ("Resulting in %ld element swaps\n", SWAP_COUNT);
}
#endif /* COUNT */


/**
 * Standard reporting system.
 * \param usecs   absolute time amount.
 */
void report(long usecs) {
  printDiffTimer(usecs);

#ifdef COUNT
  printComparisons();
#endif /* COUNT */
}

/** No special usage. */
void reportUsage() {

}

