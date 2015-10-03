/**
 * @file newton.c   Example program using Newton's method for root finding.
 * @brief 
 *   Show the execution of Newton's method on a small example.
 *   
 *   Make sure to compile with '-lm' for trigonometric functions.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <math.h>
#include <stdio.h>

/** The function whose roots are to be computed. */
double f(double x) {
  return x*sin(x)-5*x-cos(x);
}

/** The derivative of f. */
double fd(double x) {
  return x*cos(x)+2*sin(x)-5;
}

/** Launch Newton's method. */
int main() {
  double x = 0;
  int i;
  for (i = 0; i < 10; i++) {
    printf ("%16.16f\n", x);
    x = x - f(x)/fd(x);
  }

  return 0;
}
