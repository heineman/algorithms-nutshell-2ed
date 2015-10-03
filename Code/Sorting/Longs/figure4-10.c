/**
 * @file figure4-10.c    Enable output for Figure 4-10.
 * @brief
 *
 *    Sample test case for partition to enable one to walkthrough
 *    the debugging output.
 * 
 * @author George Heineman
 * @date 8/31/10
 */

#include <stdio.h>
#include <assert.h>

/**
 * Output header of Figure 4-10.
 */
void outputHeader(long *ar, int left, int pivotIndex, int right) {
  
  int i;
  int median = (right - left)/2;
  printf (" ");
  for (i = left; i <= right; i++) {
    if (i == left) {
      printf ("left");
    } else if (i == right) {
      printf ("rt. "); 
    } else if (i == median) {
      printf ("med "); 
    } else if (i == pivotIndex) {
      printf ("pivt");
    } else {
      printf ("    ");
    }

    printf (" ");
  }
  printf ("\n");

  for (i = left; i <= right; i++) {
    printf ("| %02ld ", ar[i]);
  }
  printf ("|\n");
}

/**
 * Output each line
 */
void outputLine(long *ar, int left, int store, int idx, int right) {
  
  int i;
  for (i = left; i <= right; i++) {
    printf("|");
    if (i == store) {
      printf ("s");
    } else if (i == idx) {
      printf ("i"); 
    } else {
      printf (" ");
    }

    printf ("%02ld ", ar[i]);
  }
  printf ("|\n");
}

/**
 * In linear time, group the sub-array ar[left, right) around a pivot
 * element pivot=ar[pivotIndex] by storing pivot into its proper 
 * location, store, within the sub-array (whose location is returned 
 * by this function) and ensuring that all ar[left,store) <= pivot and 
 * all ar[store+1,right) > pivot.
 * 
 * @param ar           array of elements to be sorted.
 * @param cmp          comparison function to order elements.
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (exclusive)
 * @param pivotIndex   index around which the partition is being made.
 * @return             location of the pivot index properly positioned.
 */
int partition (long *ar, int(*cmp)(const long,const long),
	       int left, int right, int pivotIndex) {
  int idx, store;
  long pivot = ar[pivotIndex], tmp;

  outputHeader(ar, left, pivotIndex, right);

  /* move pivot to the end of the array */
  tmp = ar[right];
  ar[right] = ar[pivotIndex];
  ar[pivotIndex] = tmp;
  
  /* all values <= pivot are moved to front of array and pivot inserted
   * just after them. */
  store = left;
  for (idx = left; idx < right; idx++) {
    if (cmp(ar[idx], pivot) <= 0) {
      outputLine (ar, left, store, idx, right);
      tmp = ar[idx];
      ar[idx] = ar[store];
      ar[store] = tmp;
      store++;
    }
  }
  
  tmp = ar[right];
  ar[right] = ar[store];
  ar[store] = tmp;

  outputLine (ar, left, store, -1, right);
  return store;
}


/** used only for longs for ascending sorting. */
int cmp (const long a, const long b) {
  return a - b;
}

/** Always use median sort. */
int minSize = 0;

/** Useful function for debugging. */
void debug (long *a, int n, int idx) {
  int i = 0;
  for (i = 0; i < n; i++) {
    if (i == idx) { printf ("["); }
    printf ("%ld ", a[i]);
    if (i == idx) { printf ("]"); }
  }
  printf ("\n");
}


/** Run the test case. */
int main (int argc, char **argv) {
  long ar[] = {15,9,8,1,4,11,7,12,13,6,5,3,16,2,10,14};
  int i;
  int n = 16;

  int k = partition ((long *) ar, &cmp, 0, 15, 9);

  for (i = 0; i < k; i++) {
    assert (ar[i] <= ar[k]);
  }
  for (i = k+1; i < n; i++) {
    assert (ar[i] >= ar[k]);
  }

  return 0;
}
