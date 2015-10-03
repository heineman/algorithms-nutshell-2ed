/**
 * @file buildInt.c    Task to create integers to use for comparison
 * @brief 
 *    Execute for a fixed number of iterations the comparison of two random
 *    integers.
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include "problem.h"

/** First random integer. */
static int m;

/** Second int against which to check; will contain same value as m. */
static int n;

/** Number of times to compare. */
static int psize;

/** Select a random number in the range 1..2^32 */
void prepareInput(int inputSize) {
  m = 1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0)));
  n = m;
  psize = inputSize;
}

/** Execute by invoking psize comparisons. */
int execute() {
  int i;
  int cmp = 0;

  for (i = 0; i < psize; i++) {
    cmp = (m == n);
  }
  
  return cmp;
}

/** No postprocessing needed. */
void postInputProcessing() { 
}

/** No specific problem usage. */
void problemUsage() {
}

