/**
 * @file buildProblem.c    Driver that populates a search structure by invoking insert on strings in random order.
 * @brief 
 *    Populates a search structure by first inserting numElements strings,
 *    in random order. Thereafter, performs a number of search operations as
 *    dictated by the command line arguments.
 * 
 * Build up an array of Strings.
 *
 * Required Input:
 * <ol>  
 *   <li>seed     (-s n)
 *   <li>verbose  (-v)    if you want expressive output
 * </ol>
 * Overridden Input:
 * <ol>
 *   <li>int numElements (-n x)   If this is set, we override to 24*z
 * </ol>
 *
 * Expected methods 
 * <ol>
 *   <li>extern void construct (int)   -- construct initial collection storage
 *   <li>extern void insert (char *s)  -- add string to collection
 *   <li>extern int search (char *target, int(*cmp)(const char *,const char *));
   </ol>                                -- function to initiate search
 * Search considerations
 * <dl>
 *   <dt>Search domain will be {a,b}^k which guarantees the size of |S| will
 *   be a power of 2.
 * </dl>
 * Input flags:
 * <ol>
 *   <li>-p n Ratio in range [0,1] of available elements in target [default: .25]
 *           This reflects the strings from the domain that are to be
 *           searched for. If p=0 then the target strings are never found
 *           in the list. If p=1 then the target strings are always found
 *           in the list. Value of p in between reflect ratio of behavior.
 * 
 *   <li>-d s Distribution type ('u' for uniform, 'w' for weighted) [default: uniform]
 *   <li>-k n Size of the element Strings drawn from {a,b}* [default: 6]
 *   <li>-s   use same failed id (-1) [default: use different ids starting -2, -3, ...]
 *
 *   <li> -f  Always search for the first element [default: search without constraint]
 *   <li>-z n Size of the target data structure. [default: 50] Note that the
 *        numElements is to be set to 24*z, overriding any previous value.
 * </ol>
 *
 * External API
 * <ol>
 *   <li>void problemUsage()           shows what is expected
 *   <li>void prepareInput()           construct target instance DS by repeated invocation of insert(s)
 *   <li>void postInputProcessing()    performs post-solution processing
 *   <li>void execute()                begin the problem and invoke search methods
 * </ol>
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include <stdlib.h>
#include <stdio.h>
#include <getopt.h>
#include <math.h>
#include <string.h>

#include "problem.h"
#include "report.h"

/** Determine whether output is to be printed as the computation progresses. */
extern int verbose;

/** Method to construct the initial search structure to contain 'sz' elements. */
extern void construct (int sz);

/** Method to insert an integer element into the search structure. */
extern void insert (char *s);

/** Method to search for an integer element in the search structure. */
extern int search (char *target, int(*cmp)(const char *,const char *));

/**  information will be stored as pointer of strings. */
char **searchList;

/** Uniform distribution of random strings. */
#define UNIFORM 1

/** Weighted distribution of random strings (not implemented). */
#define WEIGHTED 2

/** Element size of strings to be used. */
static int elementSize = 6;

/** Likelihood that target will be in the collection being searched. */
static float p = 0.25;

/** Distribution method to use (UNIFORM is only supported for now). */
static int distrib = UNIFORM;

/** Should the target always be the first element. */
static int first = 0;

/** Collection Size: default is 50. */
static int z = 50;

/** Computed number found. */
static int numFound = 0;

/** Record the first string inserted, to use with -f option. */
static char *firstInserted = 0;

/** Build up a collection of strings known to be in the collection. */
static char **elementsInC;

/** comparator function to use for all searches. */
int stringComp (const char *a1, const char *a2) {
#ifdef COUNT
ADD_COMP;
#endif  /* COUNT */
  return strcmp (a1, a2);
}

/** generate the kth element from s. */
static char *elt(int size, int k) {
  char *s = calloc (size+1, sizeof (char));
  int idx;
  
  int i = 0;
  for (i = 0; i < size; i++) {
    s[i] = 'a';
  }

  /* now fill-in  */
  idx = size-1;
  while (k != 0) {
    if (k % 2 == 1) {
      s[idx] = 'b';
    }

    k = k/2;
    idx--;
  }

  s[size] = '\0';
  return s;
}

/**
 * Materialize in order the full set of strings in the n = 2^k set.
 */
static char **materializeStrings() {
  int i;
  int n = (int) pow (2, elementSize);
  char **strings = calloc (n, sizeof (char *));

  for (i = 0; i < n; i++) {
    strings[i] = elt(elementSize, i);
  }

  return strings;
}

/**
 * Given a string, rotate from the right, rolling over at 254, back to 1
 * 
 * Ensures that str[0] is never 'a' or 'b'.
 */
void rotate(char *str) {
  int idx = elementSize-1;

  while (idx >= 0) {
    if (++str[idx] == (char) 254) {
      str[idx] = (char) 1;
      idx--;
    } else {
      return;
    }
  }

  /* fix just in case. Should never happen. */
  if (str[0] == 'a') str[0] = 'c';
  if (str[0] == 'b') str[0] = 'c';
}

/**
 * Construct search list based on distribution type.
 *
 * Here n=numElements is equal to z^2 and we intend to fill up the 
 * elements of searchList with all elements from ds. Draw the elements
 * themselves from elementsInC.
 *
 * p can be one of {0.0,0.25,0.5,0.75,1.0} and we deal with accordingly
 * in this method. Note there is no need to be random about anything since
 * we are going to aggregate over all elements in the set C and everyone
 * will eventually be searched for.
 *
 * the searchlist is randomly shuffled 12*z times to ensure some bit of 
 * randomness...
 */
static void constructSearchList() {
  int i,j,k;
  searchList = (char **) calloc (numElements, sizeof(char *));
  
  if (distrib == UNIFORM) {
    int ct = 0;
    int toReplace = (1-p)*numElements;
    int max_k_minus_one = (int) pow (2, elementSize-1); /* strings not in Sk */
    int numToShuffle = 12*z;

    /* populate all 24*z with elements of C */
    for (i = 0; i < 24; i++) {
      for (j = 0; j < z; j++) {
	searchList[ct++] = elementsInC[j];
      }
    }

    /* Now take 'p' into account and switch p*24*z with a random string from
     * the set S_{k-1}. */
    for (i = 0; i < toReplace; i++) {
      j = (int) (max_k_minus_one * (rand() / (RAND_MAX + 1.0)));
      searchList[i] = elt(elementSize-1,j);
    }

    /* now randomly shuffle 12*z points within the 24*z array. */
    for (i = 0; i < numToShuffle; i++) {
      char *t;
      j = (int) (numElements * (rand() / (RAND_MAX + 1.0)));
      k = (int) (numElements * (rand() / (RAND_MAX + 1.0)));

      t = searchList[j];
      searchList[j] = searchList[k];
      searchList[k] = t;
    }

  } else {
    printf ("** Only UNIFORM distribution implemented to date **");
  }
}

/**
 * Create the input set: an array of given size with random strings. 
 *
 * use character-swapping algorithm from strfry.c in glibc
 * 
 * http://www.koders.com/c/fidBD83E492934F9F671DE79B11E6AC0277F9887CF5.aspx
 *
 */
void prepareInput (int size, int argc, char **argv) {
  int j, idx, ct, pk, numPad;
  char ch;
  char **strings, *paddedString;

  int done = 0;
  opterr = 0;   /* disable errors */
  while (!done && (ch = getopt(argc, argv, "p:d:k:fz:")) != -1) {
    switch (ch) {
      
    case 'p':
      p = atof(optarg);
      break;

    case 'd':
      if (!strcmp ("u", optarg)) {
	distrib=UNIFORM;
      } else if (!strcmp ("w", optarg)) {
	distrib=WEIGHTED;
      }
      break;

    case 'k':
      elementSize = atoi(optarg);
      break;
	
    case 'f':
      first = 1;
      break;

    case 'z':
      z = atof(optarg);
      break;

    case '?':
      done = 1;
      break;
    }
  }
  optind = 0;  /* reset getopt for next time around */

  strings = materializeStrings();

  /* construct DS collection: This is the linear list over which we are
   * searching.  the goal is to ensure that this collection has exactly z
   * elements.  if z = 2^k then all strings in the domain are present in
   * C. If z < 2^k then some legal strings are simply not present. If z >
   * 2^k then the collection contains extra strings of length k that are
   * guaranteed not to belong to the domain Sk (for example, if k=6 then
   * one of these extra strings could be "c00001").
   */
  pk = (int) pow (2, elementSize);
  numPad = 0;
  if (z > pk) {
    /* we will have to pad with known failures missing strings.
     * note that this will have to be a random act. Tricky. */
    numPad = z-pk;
  }

  construct(z);
  elementsInC = (char **) calloc (z, sizeof (char *));

  /* make sure we have 24*z as number of elements */
  numElements = 24*z; 

  idx = pk;
  ct = 0;

  /* create a string that can't belong in Sk. Each time this string is
   * accessed, we bump up the ctr from the right, even if we roll over to
   * the first char, we will never get back to the 'a'. */
  paddedString = calloc(elementSize+1, sizeof(char));
  paddedString[0]='c';
  for (j = 1; j < elementSize; j++) { paddedString[j]=(char)1;}

  while (ct < z) {
    char *ins = 0;
    /* add a real string or a padded string? Randomly choose, unless we are
     * out of real strings (idx==0) */

    if ((numPad > 0 && ((float)rand())/RAND_MAX < 0.5) || (idx==0)) {
      /* padded string */
      ins = strdup(paddedString);
      insert (ins);

      numPad--;
      rotate(paddedString);
   } else {
     /* real string */
     j = (int) (idx * (rand() / (RAND_MAX + 1.0)));

     /* each search problem must provide its own insert method. */
     ins = strings[j];
     insert (ins);

     strings[j] = strings[idx-1];
     strings[idx-1] = '\0';
     idx--;
   }

    /* record for later, if -f is an option selected */
    if (firstInserted == 0) {
      firstInserted = strdup (ins);
    }

    if (verbose) {
      printf ("ins:%s\n", ins);
    }

    /* maintain copy for later use in constructing SL, regardless of
     * what underlying set is using it for.  */
    elementsInC[ct++] = strdup (ins);
  }

  /* These are the items that we are looking for. Only create if 'first'
   * was not selected, otherwise fill that entire list with firstInserted */
  if (first) {
    int i;
    searchList = (char **) calloc (numElements, sizeof(char *));
    for (i = 0; i < numElements; i++) {
      searchList[i] = firstInserted;
    }
  } else {
    constructSearchList();
  }
}

/**
 * Report properly formatted for table
 */
void postInputProcessing(long usecs) {
  int i;

  if (verbose) {
    printf ("\n Program Arguments\n");

    printf ("args: %d, %d, %f, %s, %d[%d], %d\n",
	    z, numElements, p, timingString (usecs),
#ifdef COUNT	  
	  COMP_COUNT, NIL_COUNT,
#else
	  0, 0,
#endif
	  numFound);

    for (i = 0; i < numElements; i++) {
      printf ("%s\n", searchList[i]);
    }
  }
}

/** Execute the 'numElements' number of searches of targets. */
void execute() {
  int i;

  for (i = 0; i < numElements; i++) {
    numFound += search (searchList[i], stringComp);
  }
}

/** Declare the special flags to use for this driver. */
void problemUsage() {
  printf ("\n");
  printf ("Note that problem size is the NUMBER of searches to execute.\n\n");
  printf ("   -p n Ratio in range [0,1] of available elements in target [default: .25] \n");
  printf ("   -d s Distribution type ('u' for uniform, 'w' for weighted) [default: u]\n");
  printf ("   -e n Size of the element Strings drawn from {a,b}* [default: 6]\n");

  printf ("   -f   Always search for the first element in list [default: 0]\n");
  printf ("   -v   Verbose. Print all elements once done [default: no]\n");

  printf ("   -z n Size of the linear list being searched. [default: 50]\n");
}

