/**
 * @file large.c   Driver for testing allocation/deallocation mechanisms.
 * @brief 
 *   Allocate REALLY large number of allocations and then time the
 *   cost to deallocate just one of these (the last one).
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <sys/time.h>
#include <getopt.h>
#include <stdlib.h>

#include "report.h"

/** Time before process starts.   */
static struct timeval before;     

/** Time after process completes. */
static struct timeval after;      


/** value for number of elements in a trial suite. */
int n;

/** Low value for number of elements in trial suite. */
int lowN;

/** High value for number of elements in trial suite. */
int highN;

/**
 * Generate a table of memory of the given chunk size.
 * \param chunk   size of each allocated object.
 */
void generateTable(int chunk) {
  int i;
  void **pointers;

  /* times. */
  long allocateT;
  long freeT;

  /* memory will be here... */
  pointers = calloc (n, sizeof (void *));

  /* Time the allocation of memory */
  /* ----------------------------- */
  gettimeofday(&before, (struct timezone *) NULL);   
  for (i = 0; i < n; i++) {
    pointers[i] = malloc (chunk);
  }
  gettimeofday(&after, (struct timezone *) NULL);   
  allocateT = diffTimer (&before, &after);
  
  /* Time de-allocation of last one */
  /* ------------------------------ */
  gettimeofday(&before, (struct timezone *) NULL);   
  free (pointers[n/2]);
  gettimeofday(&after, (struct timezone *) NULL);   
  freeT = diffTimer (&before, &after);

  printf ("%d\t%ld\t%ld\n", n, allocateT, freeT);
}
  
/** Run program with chunkSize as command line argument. */
int main (int argc, char **argv) {
  int chunk;

  if (argc < 3) {
    printf ("Usage: ./large [chunkSize] [n]\n");
    exit (0);
  }

  chunk = atoi (argv[1]);
  n = atoi (argv[2]);
  generateTable(chunk);

  exit (0);
}


