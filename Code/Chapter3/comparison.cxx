/**
 * @file comparison.cxx   Time 100,000 allocations of a table of 100 strings.
 * @brief 
 *   Allocate 100,000 tables of char* strings and time the result. Used as
 *   a baseline for comparing against Comparison.java
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include <iostream>
#include <cstdio>
#include <cstring>
#include <sys/time.h>

#include "report.h"

/** \def MAX
 * Size of the table of strings to be allocated.
 */
#define MAX 100

/** Time before process starts.   */
static struct timeval before;     

/** Time after process completes. */
static struct timeval after;     

/**
 * Allocate a table of MAX strings, each containing string 'george'.
 * \return table of strings as char ** pointer.
 */
char **strings () {
  char **update = new char* [MAX];
  for (int i = 0; i < MAX; i++) {
    update[i] = new char [7];
    strncpy (update[i], "george", 6);
  }

  return update;
}

/** Launch the application and time the performance. */
int main () {
  long sum=0;
  int i;
  gettimeofday(&before, (struct timezone *) NULL);    /* begin time */

  for (i = 0; i < 100000; i++) {
    char **str = strings();
    sum += str[0][0];
    delete [] str;
  }
  gettimeofday(&after, (struct timezone *) NULL);     /* after time */

  long usecs = diffTimer (&before, &after);           /* show results */

  printf ("%ld\n", usecs);
  printf ("%ld\n", sum);

}
