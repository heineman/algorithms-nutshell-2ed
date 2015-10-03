/**
 * @file SqrtTimeDouble.c    Task to create double floating point number for sqrt.
 * @brief 
 *    Execute for a fixed number of iterations the square root of a double.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <math.h>
#include "problem.h"

/** Randomly selected number. */
static double m;

/** Number of times to compute square root. */
static int psize;

/** Select a random number in the range 1..2^32 */
void prepareInput(int inputSize) {
  m = 1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0)));
  psize = inputSize;
}

/** Execute by invoking sqrt 'psize' number of times. */
int execute() {
  int i;
  float res = 0.0;

  /* change the m each time to thwart potential optimizers. */
  for (i = 0; i < psize; i++) {
    res = sqrt(m++);
  }
  
  return res;
}

/** No postprocessing needed. */
void postInputProcessing() { 
}

/** No specific problem usage. */
void problemUsage() {
}
