/**
 * @file buildPointerBasedInput.c    Driver to construct array of strings for sorting.
 * @brief 
 *    Build up a pointer-based array of strings to use as a basis for testing
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
 *   <li>-k   Killer 'median-of-3' as proposed by Musser, 1997
 *   <li>-w   Use words from dictionary of 161,136 words of 3 or more letters
 *   <li>-d   Numbers are sorted in descending order
 *   <li>-u n,o When paired with [a/d] determines number of pairs out of order,
 *          and distance of entity with which to swap
 *   <li>-g   Grouping size for worst-cast linear median-of-medians algorithm
 * </ol>
 * External API
 * <ol>
 *   <li>void problemUsage()           shows what is expected
 *   <li>void postInputProcessing()    performs post-solution processing
 *   <li>void prepareInput()           prepare the input
 *   <li>void execute()                begin the problem
 * </ol>
 * 
 * For use by other modules:
 * <pre>
 *   int groupingSize = 5;         default value is 5. Used by worst case
 *                                    select Kth algorithm.
 * </pre>
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include <stdlib.h>
#include <getopt.h>
#include <string.h>
#include <stdio.h>

#include "buildPointerBasedInput.h"
#include "problem.h"
#include "report.h"

/** information where input is stored. */
char **strings;

/** grouping size for worst-case linear median-of-medians algorithm. */
int groupingSize = 5;  /* default value is 5. */

/** comparator function for comparing two strings. */
int stringComp (char *a1, char *a2) {
#ifdef COUNT
ADD_COMP;
#endif  /* COUNT */
  return strcmp (a1, a2);
}

/** comparator function for ascending order. */
int ascending (const void *a1, const void *a2) {
  return strcmp ((char *) a1, (char *) a2);
}

/** comparator function for descending order. */
int descending (const void *a1, const void *a2) {
  return strcmp ((char *) a2, (char *) a1);
}

/**
 * Take sorted/ascending set and permute into killer-of-median-quicksort
 * order.
 *
 * implementation derived from:
 *     http://ralphunden.net/content/tutorials/a-guide-to-introsort/
 *
 *   Note that the data is contiguous and we must swap as required large
 *   sequence of bytes (oh well; it won't count against costs).
 */
void killer (char *combined, int numElements) {
  int i, k;
  char *swap;
  if (numElements %2 == 1) {
    printf ("You can only use the \"-k\" killer data set with an even number of elements\n");
    exit(-1);
  }

  k =numElements/2;
  swap = (char *) calloc (numElements, ELEMENT_SIZE+1);

  for (i=1; i<=k; i++) {
    if (i%2) {
      memcpy (swap+(i-1)*ELEMENT_SIZE, combined+(i-1)*ELEMENT_SIZE,ELEMENT_SIZE);
      memcpy (swap+(i)*ELEMENT_SIZE, combined+(k+i-1)*ELEMENT_SIZE,ELEMENT_SIZE);
    }
    memcpy (swap+(k+i-1)*ELEMENT_SIZE, combined+(2*i-1)*ELEMENT_SIZE,ELEMENT_SIZE);
  }

  /* now swap accordingly */
  memcpy (combined, swap, numElements*ELEMENT_SIZE+1);
  free (swap);
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
  int unsorted=0, asc=1, desc=-1, killerOrder=-2;
  int baseAlphabet=1, baseDictionary=2;
  int i, s;
  char c, *p;

  int sorted = unsorted;
  int numOutOfOrder = 0;
  int distance = 0;
  int base = baseAlphabet;

  while ((c = getopt(argc, argv, "adg:ku:w")) != -1) {
    switch (c) {
    case 'a':
      sorted = asc;
      break;

    case 'k':
      sorted = killerOrder;
      break;

    case 'd':
      sorted = desc;
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

    case 'w':
      base = baseDictionary;
      break;

    default:
      break;
    }
  }
  optind = 0;  /*  reset getopt for next time around. */

  /* create numElements copies of a random string */
  strings = (char **) calloc (size, sizeof (char *));

  if (verbose) {
    printf ("Constructing array of %d entries\n", size);
  }

  if (base == baseAlphabet) {
    for (i = 0; i < size; i++) {
      char *mixed = strdup("abcdefghijklmnopqrstuvwxyz");

      for (s = 0; s < ELEMENT_SIZE; s++) {
	int j;
	char c;
	
	/* Compute random value */
	j = 1 + (int) (ELEMENT_SIZE * (rand() / (RAND_MAX + 1.0)));
	j %= ELEMENT_SIZE;
	
	c = mixed[s];
	mixed[s] = mixed[j];
	mixed[j] = c;
      }
      
      strings[i] = mixed;
    }
  } else {
    /*
     * Pull in from dictionary of 160,136 words. Each line in the file
     * has 15 bytes. The first thirteen are potentially the word with
     * spaced padded on the right. The last two are "\n\r" since this
     * Dictionary was created on a PC.
     *
     *  NOTE: it is critical that the wordBuf be an allocated chunk
     *  of memory equal to ELEMENT_SIZE, even though wordSize will
     *  be less than it. Also, wordSize must be less than or equal to 
     *  ELEMENT_SIZE
     */
    FILE *f = fopen (DICTIONARY, "r");
    int dictSize = 160136;
    int wordSize = 13;
    int j;

    for (i = 0; i < size; i++) {
      int entry  = (int) (dictSize * (rand() / (RAND_MAX + 1.0)));      
      fseek (f, entry*15, SEEK_SET);

      /* same size as rest of code assumes */
      strings[i] = calloc (ELEMENT_SIZE+1, sizeof(char));

      fread (strings[i], wordSize, 1, f);

      /** terminate at word boundary, not including spaces. */
      for (j = 0; j < wordSize; j++) {
	if (strings[i][j] == ' ') {
	  strings[i][j] = '\0';
	  break;
	}
      }
    }
    fclose(f);
  }


  if (sorted) {
    char *combined;
    if (verbose) { printf ("sorting first...\n"); }

    /* convert pointer-based values into contiguous value expected for 
     * the internal qsort. */
    combined = (char *) calloc (numElements, ELEMENT_SIZE+1);

    /* copy into the combined */
    for (i = 0; i < numElements; i++) {
      strcpy (combined + i*ELEMENT_SIZE, strings[i]);
    }

    if (sorted == 1) {
      qsort (combined, numElements, ELEMENT_SIZE, ascending);
    } else if (sorted == -1) {
      qsort (combined, numElements, ELEMENT_SIZE, descending);
    } else if (sorted == -2) {
      qsort (combined, numElements, ELEMENT_SIZE, ascending);
      killer (combined, numElements);
    }

    /* move everything back into position now. */
    for (i = 0; i < numElements; i++) {
      bcopy (combined + i*ELEMENT_SIZE, strings[i], ELEMENT_SIZE);
    }

    /* now make numOutOfOrder swaps... */
    while (numOutOfOrder-- > 0) {
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

      /* swap characters. */
      if (verbose) { printf ("swap %d with %d\n", i, j); }
      for (s = 0; s < ELEMENT_SIZE; s++) {
	c = strings[i][s];
	strings[i][s] = strings[j][s];
	strings[j][s] = c;
      }
    }

    free (combined);
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
      printf ("%s\n", strings[i]);
    }

    /* check sorted criteria */
    for (i = 0; i < numElements-1; i++) {
      if (stringComp(strings[i], strings[i+1]) > 0) {
	printf ("Sort failed at position %d: %s,%s\n", i,
		strings[i], strings[i+1]);
	break;
     }
    } 
  }
}

/** Sort the array of numElement strings. */
void execute() {
  sortPointers (strings, numElements, stringComp);
}

/** Declares the special command-line flags supported by this driver. */
void problemUsage() {
  printf ("   -a declares that the array should be sorted ascending for worst case behavior. Incompatible with -d\n");
  printf ("   -d declares that the array should be sorted ascending for worst case behavior. Incompatible with -a\n");
  printf ("   -g define the groupings size for worst-case linear kth selection algorithm.\n");
  printf ("   -k declares that the array should be prepared according to the median-of-3 killer algorithm, Musser (1997)\n");
  printf ("   -u #,# determines number of pairs out of order, if sorted, and distance with which to swap.\n");
  printf ("   -w instead of random permutations of alphabet string, draw words from 160,136 English word dictionary\n");

}
