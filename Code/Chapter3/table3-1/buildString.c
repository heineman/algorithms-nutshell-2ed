/**
 * @file buildString.c    Task to create strings to use for comparison
 * @brief 
 *    Execute for a fixed number of iterations the comparison of two random
 *    strings
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <getopt.h>
#include <stdio.h>
#include <string.h>
#include "problem.h"

/** First random string. */
static char *str1;

/** Second string against which to check; will contains same value as str1. */
static char *str2;

/** Number of times to compare. */
static int psize;

/** string size (default: 10) */
static int ssize = 10;

/**
 * Construct a random string of size ssize and have 'str1' and 'str2' be 
 * allocated strings with the same contents.
 */
void prepareInput(int inputSize, int argc, char **argv) {
  char *map = "ThisIsATest";
  int sz = 11;
  int i, j;
  char c;

  while ((c = getopt(argc, argv, "z:")) != -1) {
    switch (c) {
    case 'z':
      ssize = atoi(optarg);
      break;
      
    default:
      break;
    }
  }
  optind = 0;  /* reset getopt for next time around. */

  str1 = calloc (ssize+1, sizeof (char));
  str2 = calloc (ssize+1, sizeof (char));
  
  /* take characters from this random string */
  j = 0;
  for (i = 0; i < ssize; i++) {
    str1[i] = map[j];
    str2[i] = map[j];
    
    j = (j+1) % sz;
  }

  psize = inputSize;
}

/** Execute by invoking psize strncmp comparisons. */
int execute() {
  int i;
  int cmp = 0;
  for (i = 0; i < psize; i++) {
    cmp = (strncmp(str1,str2,ssize));
  }

  return cmp;
}

/** No postprocessing needed. */
void postInputProcessing() {
}

/** This executable allows [-z size] to alert the default string size. */
void problemUsage() {
  printf ("   -z size of strings (default: 10)\n");
}

