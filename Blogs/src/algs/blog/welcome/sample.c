#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/** Max line size. */
#define MAX 1024

#ifdef COUNT
/** Must define here since we don't use default reporting. */
long __swapTotal = 0;
#endif /* COUNT */

/** Enable strcmp to be the comparison function. */
extern void sortPointers (void **, int,
               int(*cmp)(const char *,const char *));

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

  /* Use sortPtrs interface using strcmp. */
  sortPointers (ptrs, count, strcmp);

  for (i = 0; i < count; i++) {
    fputs(ptrs[i], stdout);
  }

  return 0;
}
