/**
 * @file Timing/timing.c    Common driver to launch many experiments.
 * @brief 
 *    Implements a standard driver for processing command line arguments
 *    to retrieve (a) the number of elements; (b) the verbose setting; (c)
 *    the randomized seed to use to enable repeated experiments.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <string.h>
#include <stdlib.h>
#include <sys/time.h>
#include <getopt.h>
#include <stdio.h>

#include "problem.h"
#include "report.h"

extern void problemUsage();
extern void execute();
extern void reportUsage();
extern void report(long usecs);
extern void prepareInput (int inputSize, int argc, char **argv);
extern void postInputProcessing(long usecs);

/**
 * Sets up a timing framework.
 *
 *  To use, you need to provide the following functions:
 *
 *     prepareInput(int inputSize);
 *
 *     execute();
 *
 *     report();
 */

/* Implementation */
/* ---------------*/
void usage() {
  printf ("usage: timing [-n NumElements] [-v]\n");
  printf ("   -n declares the problem size         [default: 100,000]\n");
  printf ("   -v verbose output                    [default: false]\n");
  printf ("   -s # set the seed for random values  [default: no seed]\n"); 
  printf ("   -h print usage information\n"); 

  printf ("problem flags (if any)\n");
  problemUsage();
  printf ("report flags (if any)\n");
  reportUsage();
}

/* Information */
/* ------------*/
int verbose = 0;                  /** Report verbose output or not. */
int numElements = 100000;         /** Default number of elements. */

/**
 * Execute with given flags.
 *
 *   -h     HELP
 *   -n N   Number of elements being processed
 *   -v     Compute in verbose mode
 *   -s S   Set the initial random seed for all computationsn
 */
int main(int argc, char **argv) {
  long usecs;
  char c;
  int i, idx = 0, seeded = 0;
  int done = 0;
  unsigned int seed = 0;
  char **newargv;
  struct timeval before;     /** Time before process starts.   */
  struct timeval after;      /** Time after process completes. */

  opterr = 0;   /* disable errors */
  while (!done && (c = getopt(argc, argv, "hn:s:v")) != -1) {
    idx++;
    switch (c) {

    case 'h':
      usage();
      exit (0);
      break;

    case 'n':
      numElements = atoi(optarg);
      idx++;
      break;

    case 's':
      seeded = 1;
      seed = atoi(optarg);
      idx++;
      break;

    case 'v':
      verbose = 1;
      break;

    case '?':
      /* unknown */
      done=1;
      break;

    default:
      break;
    }
  }
  optind = 0;  /* reset getopt for next time around. */

  /* at this point, 'idx' points to the non-interpreted arguments. */
  /* don't forget to leave room for 'arg[0]'                       */
  newargv = (char **) calloc (argc-idx+1, sizeof (char *));
  newargv[0] = strdup(argv[0]);
  for (i = idx; i < argc; i++) {
    newargv[i-idx+1] = strdup(argv[i]);
  }
  newargv[argc-idx+1] = '\0';

  if (seeded) {
    if (verbose) { printf ("using seed %d\n", seed); }
    srand(seed);
  }

  /* Externally-provided function */
  prepareInput(numElements, argc-idx+1, newargv);

  gettimeofday(&before, (struct timezone *) NULL);    /* begin time */
  execute();
  gettimeofday(&after, (struct timezone *) NULL);     /* end time */

  usecs = diffTimer (&before, &after);           /* show results */
  report (usecs);

  postInputProcessing(usecs);
  return 0;
}
