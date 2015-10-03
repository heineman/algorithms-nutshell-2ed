/**
 * @file buildDoubleBasedInput.c    Driver to construct array of doubles for sorting.
 * @brief 
 *    Build up a pointer-based array of Doubles to use as a basis for testing
 *    the various sorting algorithms.
 * 
 * Required Input:
 * <ol>
 *   <li>int numElements
 *   <li>int verbose
 * </ol>
 *
 * Input flags:
 * <ol>
 *   <li>-a   Numbers are sorted in ascending order
 *   <li>-b   Use BellCurve distribution for random data
 *   <li>-k   Killer 'median-of-3' as proposed by Musser, 1997
 *   <li>-d   Numbers are sorted in descending order
 *   <li>-g   Grouping size for worst-cast linear median-of-medians algorithm
 *   <li>-u n,o When paired with [a/d] determines number of pairs out of order,
 *          and distance of entity with which to swap
 * </ol>
 * External API
 * <ol>
 *   <li>void problemUsage()           shows what is expected
 *   <li>void postInputProcessing()    performs post-solution processing
 *   <li>void prepareInput()           prepare the input
 *   <li>void execute()                begin the problem
 * </ol>
 * 
 * @author George Heineman
 * @date 6/15/08
 */
#include <stdlib.h>
#include <string.h>
#include <getopt.h>
#include <stdio.h>
#include <math.h>

#include "buildDoubleBasedInput.h"
#include "problem.h"
#include "report.h"

/** information stored in memory. */
double **values; 

/** grouping size for worst-case linear median-of-medians algorithm. */
int groupingSize = 5;  /* default value is 5. */

/** comparator function for double elements. */
int doubleComp (double *a1, double *a2) {
#ifdef COUNT
ADD_COMP;
#endif  /* COUNT */
  double loc = *a1;
  double loc2 = *a2;
  if (loc < loc2) {
    return -1;
  } else if (loc == loc2) { 
    return 0; 
  }

  return 1;
}

/** comparator function for ascending order. */
int ascending (double *a1, double *a2) {
  double loc = *a1;
  double loc2 = *a2;
  if (loc < loc2) {
    return -1;
  } else if (loc == loc2) { 
    return 0; 
  }

  return 1;
}

/** comparator function for descending order. */
int descending (double *a1, double *a2) {
  double loc = *a2; /*  backwards */
  double loc2 = *a1;
  if (loc < loc2) {
    return -1;
  } else if (loc == loc2) { 
    return 0; 
  }

  return 1;
}


/**
 * Implementation of killer-of-three median derived from:
 *     http://ralphunden.net/content/tutorials/a-guide-to-introsort/
 *
 * Note that the data is contiguous and we must swap as required large
 * sequence of bytes (oh well; it won't count against costs).
 */
void killer (double *combined, int numElements) {
  int i, k;
  double *swap;
  if (numElements %2 == 1) {
    printf ("You can only use the \"-k\" killer data set with an even number of elements\n");
    exit(-1);
  }

  k =numElements/2;
  swap = (double *) calloc (numElements, ELEMENT_SIZE);

  for (i=1; i<=k; i++) {
    if (i%2) {
      swap[i-1] = combined[i];
      swap[i] = combined[k+i];
    }
    swap[k+i-1] = combined[2*i];
  }

  /* now swap accordingly */
  for (i = 0; i < numElements; i++) {
    combined[i] = swap[i];
  }
  free (swap);
}


/**
 * Create the input set: an array of given size with random doubles
 *
 * use character-swapping algorithm from strfry.c in glibc
 * 
 * http://www.koders.com/c/fidBD83E492934F9F671DE79B11E6AC0277F9887CF5.aspx
 *
 */
void prepareInput (int size, int argc, char **argv) {
  int i;
  char c, *p;
  int sorted = 0;
  int numOutOfOrder = 0;
  int distance = 0;
  int distribution = 0;

  while ((c = getopt(argc, argv, "abdku:")) != -1) {
    switch (c) {
    case 'a':
      sorted = 1;
      break;

    case 'b':
      distribution = 1;
      break;

    case 'k':
      sorted = -2;
      break;

    case 'd':
      sorted = -1;
      break;
      
    case 'g':
      groupingSize = atoi(optarg);
      break;

    case 'u':
      p = strstr(optarg, ",");
      *p = '\0';
      numOutOfOrder = atoi(optarg);
      distance = atoi(p+1);
      *p = ',';
      break;

    default:
      break;
    }
  }
  optind = 0;  /* reset getopt for next time around. */

  /* create numElements copies of a random double */
  values = (double **) calloc (size, sizeof (double *));

  if (verbose) {
    printf ("Constructing array of %d doubless\n", size);
  }

  if (distribution == 0) {
    /* Generate uniformly between [0,1) */
    double den = RAND_MAX;
    for (i = 0; i < size; i++) {
      values[i] = calloc (1, sizeof (double));
      *values[i] = random();
      *values[i] /= den;    
    }
  } else {
    /* Generate random numbers with mean 0 and std deviation of 1. */
    /* Uses the Polar Box Muller method. Must normalize all values. */
    /* to be between 0 and 1. To do this, we compute min and max values */
    /* and adjust all values, which leads to a mean of 0.5. */
    double den = RAND_MAX;
    double min = RAND_MAX;
    double max = -RAND_MAX;
    double ratio;

    i = 0;
    while (i < size) {
      double u1, u2, w;
      do {
	u1 = random();
	u1 /= den;

	u2= random();
	u2 /= den;

	u1 = 2*u1 - 1;
	u2 = 2*u2 - 1;

	w = u1*u1 + u2*u2;
      } while (w >= 1 || w == 0);

      w = sqrt((-2*log(w))/w);

      values[i] = calloc (1, sizeof (double));
      *values[i] = u1*w;
      if (*values[i] < min) {
	min = *values[i];
      }
      if (*values[i] > max) {
	max = *values[i];
      }

      i++;

      if (i < size) {
	values[i] = calloc (1, sizeof (double));
	*values[i] = u2*w; 

	if (*values[i] < min) {
	  min = *values[i];
	}
	if (*values[i] > max) {
	  max = *values[i];
	}
	i++;
      }
    }

    /* normalize to fit within [0,1). Since we can't be exactly 1, we */
    /* tighten up the ratio by adding one to the denominator. */
    ratio = 1/(1 + max - min);
    for (i = 0; i < size; i++) {
      *values[i] = (*values[i]-min)*ratio;
    }
  }

  if (sorted) {
    double *combined;
    if (verbose) { printf ("sorting first...\n"); }

    /* convert pointer-based values into contiguous value expected for
     * the internal qsort. */
    combined = (double *) calloc (numElements, ELEMENT_SIZE);

    /* copy into the combined */
    for (i = 0; i < numElements; i++) {
      combined[i] = *values[i];
    }

    if (sorted == 1) {
      qsort (combined, numElements, ELEMENT_SIZE,
	     (int (*)(const void *,const void *)) ascending);
    } else if (sorted == -1) {
      qsort (combined, numElements, ELEMENT_SIZE,
	     (int (*)(const void *,const void *)) descending);
    } else if (sorted == -2) {
      qsort (combined, numElements, ELEMENT_SIZE,
	     (int (*)(const void *,const void *)) ascending);
      killer (combined, numElements);
    }

    /* move everything back into position now. */
    for (i = 0; i < numElements; i++) {
      *values[i] = combined[i];
    }

    free (combined); /* done */

    /* now make numOutOfOrder swaps... */
    while (numOutOfOrder-- > 0) {
      double *tmp;
      int j;
      /* Compute random value */
      i = 1 + (int) (numElements * (rand() / (RAND_MAX + 1.0)));
      i %= numElements;

      /* find distance (either up or down) from i. */
      if (distance == 0) {
	j = 1 + (int) (numElements * (rand() / (RAND_MAX + 1.0)));
	j %= numElements;
      } else {
	j = i + distance;
	if (j > numElements-1) { j = i - distance; if (j < 0) { j = 0;}} 
      }

      /* swap values. */
      if (verbose) { printf ("swap %d with %d\n", i, j); }
      tmp = values[i];
      values[i] = values[j];
      values[j] = tmp;
    }
  }

  if (verbose) {
    printf ("Done constructing array of %d entries\n", size);
  }
}

/** After sorting, can check whether sort succeeded. */
void postInputProcessing() {
  int i, top;

  if (verbose) {
    printf ("validate sorting worked. Here are first 10 entries\n");
    top = 10;
    if (numElements < top) { top = numElements; } 
    for (i = 0; i < top; i++) {
      printf ("%f\n", *values[i]);
    }

    /* check sorted criteria */
    for (i = 0; i < numElements-1; i++) {
      if (*values[i] > *values[i+1]) {
	printf ("Sort failed at position %d: %f,%f\n", i,
		*values[i], *values[i+1]);
	break;
     }
    } 
  }
}

/** Sort the array of numElement doubles. */
void execute() {
  sortPointers (values, numElements, doubleComp);
}

/** Declares the special command-line flags supported by this driver. */
void problemUsage() {
  printf ("   -b declares that the array should be generated from bell curve distribution around 0.0\n");
  printf ("   -d declares that the array should be sorted ascending for worst case behavior. Incompatible with -a\n");
  printf ("   -k declares that the array should be prepared according to the median-of-3 killer algorithm, Musser (1997)\n");
  printf ("   -a declares that the array should be sorted ascending for worst case behavior. Incompatible with -d\n");
  printf ("   -u #,# determines number of pairs out of order, if sorted, and distance with which to swap.\n");
}

