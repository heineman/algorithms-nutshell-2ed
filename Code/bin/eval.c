/**
 * @file eval.c    Compute averages and stdev over results.
 * @brief  Assuming the number of trials is always greater than two, this program takes in the timing results of a number of 't' trials, throws away the best and the worst performing and computes the average (and stdev) of the remaining t-2 trials.
 * 
   All input from stdin is of the form:
   <pre>
   numberOfTrials
   trial-1
   trial-2
   ...
   trial-n
   </pre>

   eval throws away LOW and HIGH as outliers and outputs on a single line:

   <pre>
   average stdev
   </pre>

 * @author George Heineman
 * @date 6/15/08
 */

/* compute simple stdev over the results */
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <math.h>

/** scratch input, long enough for a single line */
static char buf[1024];

/** prepared format of output. */
static char *expanded = "%.6f %.6f\n";

/** 
 * Launcher for code. 
 *
 * All input comes from stdin.
*/
int main (int argc, char **argv) {
  int num, i;
  double *results;
  double low, high, total, mean, sum, stdev;
  int lowi,highi;
  scanf("%s", buf);
  num = atoi(buf);

  results = calloc (num, sizeof(double));
  for (i = 0; i < num; i++) {
    scanf("%s", buf);
    results[i] = strtod(buf, (char **) NULL);    
  }

  /* remove low and high */
  low = high = results[0];
  lowi = highi = 0;
  total = results[0];
  for (i = 1; i < num; i++) {
    if (results[i] < low) {
      low = results[i];
      lowi = i;
    }
    if (results[i] > high) {
      high = results[i];
      highi = i;
    }
    total += results[i];
  }

  total = total - low - high;
  mean = total/(num-2);

  /* compute stdev */
  sum = 0;
  for (i = 0; i < num; i++) {
    if (i == lowi) { continue; }
    if (i == highi) { continue; }
    sum += (results[i] - mean) * (results[i] - mean);
  }

  stdev = sqrt(sum/(num-2));

  /* if the four digits are 0.0000 then expand outwards to 6  */
  sprintf (buf, "%.4f", mean);
  if (!strncmp (buf, "0.000", 5)) {
    printf (expanded, mean, stdev);
  } else {
    printf ("%.4f %.4f\n", mean, stdev);
  }

  return 0;
}
