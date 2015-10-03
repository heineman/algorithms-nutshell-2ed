/**
 * @file DivTimeInt.c    Task to create two int numbers for division.
 * @brief 
 *    Execute for a fixed number of iterations the division of two random int numbers.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include "problem.h"

/** First random int. */
static int m;

/** Second random double. */
static int n;

/** Number of times to compute division. */
static int psize;

/** Select two random numbers in the range 1..2^32. m and n are integral. */
void prepareInput(int inputSize) {
  m = 1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0)));
  n = 1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0)));

  psize = inputSize;
}

/** Execute by invoking division 'psize' number of times. */
int execute() {
  int i;
  int res = 0;

  /* change the m,n each time to thwart potential optimizers? */
  for (i = 0; i < psize; i++) {
    res = m/n;
    m++;
    n++;
  }

  return res;
}

/** No postprocessing needed. */
void postInputProcessing() { 
}

/** No specific problem usage. */
void problemUsage() {
}
