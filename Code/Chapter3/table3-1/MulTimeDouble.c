/**
 * @file MulTimeDouble.c    Task to create two double numbers for muliplication.
 * @brief 
 *    Execute for a fixed number of iterations the multiplication of two random double numbers.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include "problem.h"


/** First random double. */
static double m;

/** Second random double. */
static double n;

/** Number of times to compute multiplication. */
static int psize;

/** Select two random numbers in the range 1..2^32. m is integral while n is not. */
void prepareInput(int inputSize) {
  m = 1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0)));
  m = 0.5 + 1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0)));
  psize = inputSize;
}

/** Execute by invoking multiplication 'psize' number of times. */
int execute() {
  int i;
  double res = 0;

  /* change the m,n each time to thwart potential optimizers? */
  for (i = 0; i < psize; i++) {
    res = m*n;
    m++;
    n++;
    res = res -1;
  }

  return (int) res;
}

/** No postprocessing needed. */
void postInputProcessing() { 
}

/** No specific problem usage. */
void problemUsage() {
}
