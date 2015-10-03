/**
 * @file SqrtTimeFloat.c    Task to create floating point number for sqrt.
 * @brief 
 *    Execute for a fixed number of iterations the square root of a float.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <math.h>
#include "problem.h"

/** Randomly selected number. */
static float m;

/** Number of times to compute square root. */
static int psize;

/** Select a random numbers in the range 1..2^32 */
void prepareInput(int inputSize) {
  m = (float) (1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0))));
  psize = inputSize;
}

/** Execute by invoking sqrt 'psize' number of times. */
int execute() {
  int i;
  float res = 0.0;

  /* change the m each time to thwart potential optimizers... */
  for (i = 0; i < psize; i++) {
    res = sqrtf(m++);
  }
  
  return (int) res;
}

/** No postprocessing needed. */
void postInputProcessing() { 
}

/** No specific problem usage. */
void problemUsage() {
}
