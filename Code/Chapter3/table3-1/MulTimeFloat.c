/**
 * @file MulTimeFloat.c    Task to create two float numbers for muliplication.
 * @brief 
 *    Execute for a fixed number of iterations the multiplication of two random float numbers.
 * 
 * @author George Heineman
 * @date 6/15/08
 */
#include <stdlib.h>
#include "problem.h"

/** First random float. */
static float m;

/** Second random floatl. */
static float n;

/** Number of times to compute multiplication. */
static int psize;

/** Select two random numbers in the range 1..2^32 */
void prepareInput(int inputSize) {
  m = (float) (1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0))));
  n = (float) (1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0))));
  psize = inputSize;
}

/** Execute by invoking multiplication 'psize' number of times. */
int execute() {
  int i;
  float res = 0.0;

  /* change the m,n each time to thwart potential optimizers? */
  for (i = 0; i < psize; i++) {
    res = m*n;
    m++;
    n++;
  }

  return (int) res;
}

/** No postprocessing needed. */
void postInputProcessing() { 
}

/** No specific problem usage. */
void problemUsage() {
}
