#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "report.h"

/** Max line size. */
#define MAX 1024

/** Enable strcmp to be the comparison function. */
extern void sortPointers (void **, int,
               int(*cmp)(const char *,const char *));


/**
 * Comparator function for comparing two strings. If 
 * counting number of comparisons, wrap strcmp, otherwise
 * use natively.
 */
#ifdef COUNT
int stringComp (const char *a1, const char *a2) {
ADD_COMP;
  return strcmp (a1, a2);
}
#else
#define stringComp strcmp
#endif  /* COUNT */


/**
 * Sample program to load the contents of a file (supplied
 * by the user) and sort it. The result is then written to
 * standard output.
 *
 * Each input line is terminated by a '\n' character. No
 * single line of input exceeds a fixed MAX amount.  If it
 * does, the line is split into multiple input lines.
 */
int main (int argc, char **argv) {
  char buf[MAX];
  FILE *in;
  void **ptrs;
  int  size = 1000;
  int  count = 0;
  int  i;
  struct timeval before;   /** Time before start.  */
  struct timeval after;    /** Time after completion. */
  long usecs;
  double actual;

  if (argc < 2) {
    printf ("Usage: %s InFile\n", argv[0]);
    exit (1);
  }

  in = fopen (argv[1], "r");
  if (in == NULL) {
    printf ("Unable to open input file %s\n", argv[1]);
    exit (1);    
  }

  /** Allocate an initial batch. */
  ptrs = malloc (size*sizeof(char *));

  while (fgets(buf, MAX, in) != NULL) {
    ptrs[count++] = strdup (buf);

    /** When capacity fills, double and keep going on... */
    if (count == size) {
      ptrs = realloc (ptrs, (size*2)*sizeof(char *));
      size = size*2;
    }
  }

  fclose(in);

  /* Use sortPointers interface using strcmp. */
  gettimeofday(&before, (struct timezone *) NULL);
  sortPointers(ptrs, count, stringComp);
  gettimeofday(&after, (struct timezone *) NULL); 
  usecs = diffTimer (&before, &after);

  for (i = 0; i < count; i++) {
    fputs(ptrs[i], stdout);
  }

  actual = usecs;
  actual /= 1000000;
  sprintf (buf, "%f seconds\n", actual);
  fputs (buf, stderr);

#ifdef COUNT
  sprintf (buf, "%ld comparisons, %ld swaps\n",
	   COMP_COUNT, SWAP_COUNT);
  fputs (buf, stderr);
#endif /* COUNT */

  return 0;
}
