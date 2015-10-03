/**
 * @file figure4-16    Generate Figure showing Small example(s) for heapsort.
 * @brief
 *    Generate figure contents automatically
 *    
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <assert.h>

/** record swaps. */
static int swaps[16];

/** Useful debugging function. */
void debug (long *a, int n) {
  int i = 0;
  for (i = 0; i < n; i++) {
    printf ("|");
    if (swaps[i]) {
      printf ("*");
    } else {
      printf (" ");
    }
    printf ("%02ld", a[i]);
    if (swaps[i]) {
      printf ("*");
    } else {
      printf (" ");
    }
    swaps[i] = 0;
  }
  printf ("|\n");
}

/** Heapify the subarray ar[0,max). */
static void heapify (long *ar, int(*cmp)(const long,const long),
		     int idx, int max) {
  int left = 2*idx + 1;
  int right = 2*idx + 2;
  int largest;

  /* Find largest element of A[idx], A[left], and A[right]. */
  if (left < max && cmp (ar[left], ar[idx]) > 0) {
    largest = left;
  } else {
    largest = idx;
  }

  if (right < max && cmp(ar[right], ar[largest]) > 0) {
    largest = right;
  }

  /* If largest is not already the parent then swap and propagate. */
  if (largest != idx) {
     long tmp;
     swaps[idx] = 1;
     swaps[largest] = 1;
     tmp = ar[idx];
     ar[idx] = ar[largest];
     ar[largest] = tmp;
      
     heapify(ar, cmp, largest, max);
   }
}

/** Build the heap from the given array by repeatedly invoking heapify. */
static void buildHeap (long *ar, int(*cmp)(const long,const long),
		       int n) {
  int i;
  for (i = n/2-1; i>=0; i--) {
    printf ("%d  ", i);
    heapify (ar, cmp, i, n);
    debug (ar, n);
  }
}

/** Comparison function for ascending long order. */
int cmp (const long a, const long b) {
  if (a < b) {
    return -1;
  } else if (a > b) {
    return +1;
  }

  return 0;
}

/** Launch the test cases. */
int main (int argc, char **argv) {
  long ar[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};

  int st = 16/2 - 1;
  printf ("start at position %d (value is %ld).\n", st, ar[st]);
  buildHeap (ar, cmp, 16);

  return 0;
}
