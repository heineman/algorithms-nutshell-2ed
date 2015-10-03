#include <string.h>
#include <stdio.h>

/**
 * Execute a binary probe on sorted array front half and then execute
 * a block move of pointers. Over time, this should require only log(n)
 * probes and replace O(n) swaps with a single block move.
 */
void sortPointers (char **ar, int n) {
  int j;

  for (j = 1; j < n; j++) {

    /** invariant: ar[0, j) is sorted. */

    /** Search for the desired target within the search structure. */
    int low = 0, high = j-1, ix, rc, sz;
    char *target = ar[j];

    while (low <= high) {
      ix = (low + high)/2;
      rc = strcmp(target, ar[ix]);

      if (rc < 0) {
	/* target is less than ar[i] */
	high = ix - 1;
      } else if (rc > 0) {
	/* target is greater than ar[i] */
	low = ix + 1;
      } else {
	/* found the item. */
	break;
      }
    }

    /** low determines index value in to which it should be inserted (not ix as stated on p. 116) */
    /** only move if we are not already properly in place. */
    if (low != j) {
      sz = (j-low)*sizeof(char *);
      memmove (&ar[low+1], &ar[low], sz);
      ar[low] = target;
    }
  }
}
