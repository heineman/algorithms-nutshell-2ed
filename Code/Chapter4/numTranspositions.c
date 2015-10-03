/**
 * @file numTranspositions.c   Compute average number of transpositions in Insertion Sort of n elements.
 * @brief 
 *   Exhaustively permute all n objects and compute the total number of
 *   transpositions when using Insertion Sort. The average is computed by 
 *   dividing this total by the number of permutations. Really only useful
 *   for table sizes of less than 12.
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include <stdlib.h>
#include <stdio.h>

/** Size of array. */
int n;

/** The array in memory. */
int *A;

/** Processing storage, no greater than (n-1)*(n-1) */
int *TT;

/** Output array for debugging. */
void out () {
#ifdef DEBUG
  int i;
  for (i = 0; i < n; i++) {
    printf ("%d", A[i]);
  }
  printf("\n");
#endif /* DEBUG */
}

/** Permutation number. */
int ctr = 0;

/** count tranpositions. Every 100,000 iterations a status update is printed. */
void eval() {
  int i, dist = 0;
  for (i = 0; i < n; i++) {
    if (A[i] < i) {
      dist += i-A[i];
    } else {
      dist += A[i] - i;
    }
  }

  if (((++ctr) % 100000) == 0) {
    out();
  }
  TT[dist]++;
}

/** Permute the input array. */
void permute (int pos) {
  int i,j;
  for (i = 0; i < n; i++) {
    /* see if number already in the array? */
    int taken = 0;
    for (j = 0; j < pos; j++) {
      if (A[j] == i) { taken = 1; break; }
    }

    if (taken) continue;  /* try another one. */

    /* put in place */
    A[pos] = i;
    if (pos == n-1) {
      eval();
    } else {
      permute (pos+1);
    }
  }
}

/** Launch the program. */
int main (int argc, char **argv) {
  int i;
  double avg;
  long weightedTotal, ct;

  if (argc <2) {
    printf ("usage: ./numTranspositions n\n");
    printf ("    where 0 < n < 12\n");

    return 0;
  }

  printf ("Computing all possible orderings of n values and determine\n");
  printf ("the average number of transpositions for each element. As \n");
  printf ("n increases, the resulting average approaches n/3.\n");

  n = atoi(argv[1]);
  A = (int *) calloc (n, sizeof (int));
  TT = (int *) calloc (n*n, sizeof (int));

  /* compute each of the n! permutations... */
  permute(0);

  /* ... and compute number of tranpositions to move. */
  weightedTotal = 0;
  ct = 0;
  for (i = 0; i < n*n; i++) {
    weightedTotal += TT[i]*i;
    ct += TT[i];
  }

  avg = weightedTotal;
  avg /= ct;
  avg /= n;

  printf ("avg. num of transpositions for each element: %f\n", avg);
  
  free (A);
  free (TT);
  return 0;
}
