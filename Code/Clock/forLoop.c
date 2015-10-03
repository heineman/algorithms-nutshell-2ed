/**
 * @file forLoop.c   Task to add the integers from 1..numElements.
 * @brief 
 *   Task to add the integers from 1..numElements. This can be timed using
 *   the timing infrastructure.
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include <stdio.h>

/** Set by the command line -n argument. */
extern int numElements;

/** No preprocessing. */
void prepareInput() { }

/** Execute the task of adding numbers from 1..numElements */
void execute() {
  int x;
  long sum = 0;
  for (x = 1; x <= numElements; x++) { sum += x; }

  /** output sum to be sure is correct. */
  printf ("sum of first %d is %ld\n", numElements, sum);
}

/** No postprocessing. */
void postInputProcessing() { }

/** No special problem usage. */
void problemUsage() { }
