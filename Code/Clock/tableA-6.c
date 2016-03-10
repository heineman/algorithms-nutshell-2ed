/**
 * @file Clock/tableA-6.c   Show timing example in both milliseconds and nanonseconds.
 * @brief 
 *   Show the ability on Unix systems to time both using milliseconds and
 *   nanosecond timers.
 *
 *   Must be compiled with the -lrt libraries.
 * 
 * In first edition we used [1,000,000 thru 5,000,000]. Processors have
 * improved so much we now use [8,000,000 thru 14,000,000 by 2M].
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <sys/time.h>

#ifdef __MACH__  /** OS X does not have clock_gettime **/
#include <mach/clock.h>
#include <mach/mach.h>
#endif

#include <math.h>

#include "report.h"

/** Number of rows to compute. */
#define R 5

/** For each row, the number of trials to execute. */
#define T 30

/** Raw information regarding millisecond results. */
long MStimes[R][T];

/** Raw information regarding nanosecond results. */
long NStimes[R][T];

/** Time (in nanoseconds) after process completes. */
struct timespec before;

/** Time (in nanoseconds) after process completes. */
struct timespec after;

/** Time before process starts.   */
struct timeval beforeV;

/** Time after process completes. */
struct timeval afterV;

/** More than enough scratch room. */
char buf[1024]; 

/**
 * Build a row for the output by computing average and stdev after discarding
 * the lowest and highest results.
 */
char *buildRow(long n, long times[R][T]) {
  long sum = 0, min, max;
  int i, ct;
  double mean;
  double calc;
  int minIdx = 0;
  int maxIdx = 0;

  min = max = sum = times[n][0];
  
  for (i = 1; i < T; i++) {
    long t = times[n][i];
    if (t < min) { min = t; minIdx = i;}
    if (t > max) { max = t; maxIdx = i;}
    sum += t;
  }
  
  /* throw away lowest and highest. */
  sum = sum - min - max;
  ct = T - 2;

  mean = sum;
  mean = mean / ct;
  calc = 0;
  for (i = 0; i < T; i++) {
    if (i == minIdx || i == maxIdx) continue;
    calc += (times[n][i] - mean)*(times[n][i] - mean);
  }
  /*sqrt((1/[n-1])*sum[(xi-mean)^2]) FORMULA FROM EXCEL SPREADSHEET */
  calc /= (ct-1);
  calc = sqrt(calc);

  sprintf(buf, "%ld\t%f\t%ld\t%ld\t%f\t%d",
	  n, mean, min, max, calc, ct);
  return buf;
}

void buildTable(long times[R][T]) {
  int i;
  for (i = 0; i < R; i++) {
    printf ("%s\n", buildRow (i, times));
  }
  printf ("\n");
}

void port_gettime (struct timespec *ts) {
#ifdef __MACH__  /** OS X does not have clock_gettime **/
  clock_serv_t cclock;
  mach_timespec_t mts;
  host_get_clock_service(mach_host_self(), CALENDAR_CLOCK, &cclock);
  clock_get_time(cclock, &mts);
  mach_port_deallocate(mach_task_self(), cclock);
  ts->tv_sec = mts.tv_sec;
  ts->tv_nsec = mts.tv_nsec;
#else
  clock_gettime(CLOCK_PROCESS_CPUTIME_ID, ts); 
#endif /* __MACH __ */
}

/**
 * Compute the difference between two time values in usecs. 
 */
long diffNanoTimer (struct timespec *before, struct timespec *after) {
  long ds = after->tv_sec - before->tv_sec;
  long nds = after->tv_nsec - before->tv_nsec;

  /* if secs are same, then return simple delta */
  if (ds == 0) {
    return nds;
  }

  /* ok, so we are turned over. Something like: */
  /* before: 1179256745 744597                  */
  /* after:  1179256746 73514                   */

  /* if we have 'turned over' then account properly */
  if (nds < 0) {
    ds = ds - 1;
    nds += 1000000000;
  }

  return 1000000000*ds + nds;
}

/**
 * Compute the addition of numbers in range 1,000,000 to 5,000,000 for 
 * a fixed number of trials. Using this information we create a histogram
 * of sorts and output the reults.
 */
int main (int argc, char **argv) {
  long len;
  int i, x;

  int i1=0;
  long min   =  8000000;
  long max   = 16000000;
  long delta =  2000000; 

  for (len = min; len <= max; len += delta, i1++) {
    for (i = 0; i < T; i++) {
      long sum;

      gettimeofday(&beforeV, 0);    /* begin time */
      port_gettime(&before);

      sum = 0;
      for (x = 0; x < len; x++) { sum += x; }

      gettimeofday(&afterV, 0);    /* begin time */
      port_gettime(&after);

      /** Without this, loop is optimized away. */
      if (sum == 1) {
	printf ("%ld\n", sum);
      }

      MStimes[i1][i] = diffTimer (&beforeV, &afterV);
      NStimes[i1][i] = diffNanoTimer (&before, &after);
    }
  }

  printf ("Table A-4\n");
  printf ("n\taverage\t\tmin\tmax\tstdev\t\t#\n");
  buildTable(MStimes);

  printf ("Table A-6\n");
  printf ("n\taverage\t\tmin\tmax\tstdev\t\t#\n");
  buildTable(NStimes);

  return 0;
}


