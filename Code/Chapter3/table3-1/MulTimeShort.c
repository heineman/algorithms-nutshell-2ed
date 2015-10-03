/**
 * @file MulTimeShort.c    Task to create two short numbers for muliplication.
 * @brief 
 *    Execute for a fixed number of iterations the multiplication of two random short numbers.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include "problem.h"

/** First random short. */
static short m;

/** Second random short. */
static short n;

/** Number of times to compute multiplication. */
static int psize;

/** Select two random numbers in the range 1..255 */
void prepareInput(int inputSize) {
  m = (1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0)))) % 256;
  n = (1 + (int) (2147483648 * (rand() / (RAND_MAX + 1.0)))) % 256;

  psize = inputSize;
}

/** Execute by invoking multiplication 'psize' number of times. */
int execute() {
  int i;
  short res = 0;

  /* change the m,n each time to thwart potential optimizers. */
  for (i = 0; i < psize; i++) {
    res = m*n;
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
