/**
 * @file tester.c   Driver for testing allocation/deallocation mechanisms.
 * @brief 
 *   Allocate REALLY large number of allocations and then time the
 *   cost to deallocate just one of these (the last one).
 * 
 *   Note that each trial must be a separate execution since we don't want
 *   to mistakenly compare (i) what happens when you allocate memory for
 *   the first time; with (ii) what happens when you allocate memory 
 *   where some previously allocated memory has been free'd.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <sys/time.h>

#include "report.h"

/** \def FREEERROR
    Invalid Strategy. 
*/
#define FREEERROR      0

/** \def FREEUP
    Strategy to deallocate in order. 
*/
#define FREEUP         1

/** \def FREEDOWN       
    Strategy to deallocate in reverse order. 
*/
#define FREEDOWN       2

/** \def FREESCATTERED  
    Strategy to deallocate in random order. 
*/
#define FREESCATTERED  3

/** Known allocation types (1-3 are valid). */
static char *types[4] = {"", "UP", "DOWN", "SCATTERED" };

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
 * Generate a table of memory and release with given strategy
 *
 * \param strategy   UP, DOWN, or SCATTERED as the strategy to use.
 */
void generateTable(int strategy) {
  int i, j, k;
  void **pointers, **scattered;
  int NUM_CHUNKS = 4096;

  /* times. */
  long allocateT;
  long freeT = 0; /* to compile cleanly */

  /* memory will be here... */
  pointers = calloc (NUM_CHUNKS, sizeof (void *));

  /* for scattered. */
  scattered = calloc (NUM_CHUNKS, sizeof (void *));

  /* Time the allocation of memory */
  /* ----------------------------- */
  gettimeofday(&before, (struct timezone *) NULL);   
  for (i = 0; i < NUM_CHUNKS; i++) {
    pointers[i] = malloc (n);
  }
  gettimeofday(&after, (struct timezone *) NULL);   
  allocateT = diffTimer (&before, &after);
  
  /* prepare scattered array by randomly shuffling n times. */
  for (i = 0; i < NUM_CHUNKS; i++) {
    scattered[i] = pointers[i];
  }
  for (i = 0; i < NUM_CHUNKS; i++) {
    void *tmp;
    j = (int) (NUM_CHUNKS*(rand() / (RAND_MAX + 1.0)));
    k = (int) (NUM_CHUNKS*(rand() / (RAND_MAX + 1.0)));
    tmp = scattered[j];
    scattered[j] = scattered[k];
    scattered[k] = tmp;
  }
  
  /* determine which one to apply. */
  switch (strategy) {
    
  case FREEUP:
    /* time deallocation of memory, based on strategy */
    gettimeofday(&before, (struct timezone *) NULL);   
    for (i = 0; i < NUM_CHUNKS; i++) {
      free (pointers[i]);
    }
    gettimeofday(&after, (struct timezone *) NULL);   
    freeT = diffTimer (&before, &after);
    break;
    
  case FREEDOWN:
    /* time deallocation of memory, based on strategy */
    gettimeofday(&before, (struct timezone *) NULL);   
    for (i = NUM_CHUNKS-1; i >=0; i--) {
      free (pointers[i]);
    }
    gettimeofday(&after, (struct timezone *) NULL);   
    freeT = diffTimer (&before, &after);
    break;
    
  case FREESCATTERED:
    /* time deallocation of memory, based on strategy */
    gettimeofday(&before, (struct timezone *) NULL);   
    for (i = 0; i < NUM_CHUNKS; i++) {
      free (scattered[i]);
    }
    gettimeofday(&after, (struct timezone *) NULL);   
    freeT = diffTimer (&before, &after);
    break;
  }

  printf ("%d\t%ld\t%ld\n", n, allocateT, freeT);
}
  
int main (int argc, char **argv) {
  int type = FREEERROR; /** Error case. */
  int i;

  if (argc < 3) {
    printf ("Usage: ./test {UP/DOWN/SCATTERED} n\n");
    exit (-1);
  }

  for (i = 1; i <= 3; i++) {
    if (!strcmp (types[i], argv[1])) {
      type = i;
    }
  }
  
  if (type == FREEERROR) {
    printf ("Usage: ./test {UP/DOWN/SCATTERED} n\n");
    exit (-1);
  }

  n = atoi (argv[2]);
  generateTable(type);

  exit (0);
}


