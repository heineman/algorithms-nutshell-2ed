/**
 * @file generateBench.c  Program to generate a bunch of large (sparse) sample graphs.
 * @brief 
 *
 *   Computes a stylized sparse graph given parameter n. Final graph will
 *   have n^2+2 vertices and n^3 - n^2 + 2n edges. Each edge will have a 
 *   random weight greater than zero and less than n*n.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <stdlib.h>

/** Computed maximum weight to use. */
int maxWeight;

/** Generate random weight over the graph. */
int randomWeight () {
  return 1 + (int) (maxWeight * (rand() / (RAND_MAX + 1.0)));
}

/**
 * Generate benchmark graph 1.
 *
 * Given argument n, generate graph with n^2+2 vertices.
 * 
 * Imagine a square nxn of vertices viewed as a set of n columns. All
 * vertices in column i are connected to all vertices i column i+1, for
 * 0 <= i < n.  Then vertex s are connected to all vertices in column 0
 * while all vertices in column n-1 are connected to target vertex t.
 *
 * All edges are given a random weight in the range 1..n^2
 * 
 * Total edges = n + n^2(n-1) + n = n^3-n^2+2n
 */
int main (int argc, char **argv) {
  int i, j, k;

  if (argc < 2) {
    printf ("Usage: ./generateBench n\n");
    printf ("       parameter n is used to generate graph with n^2+2 vertices.\n");
    return 0;
  }

  int n = atoi (argv[1]);
  printf ("Benchmark 1 [%d]\n", n);

  maxWeight = n*n;

  int e = n*n*n-n*n+2*n;
  printf ("%d %d directed\n", n*n+2, e);
  
  // output s --> vi and vi --> t
  for (i = 1; i <= n; i++) {
    printf ("%d,%d,%d\n", 0, i, randomWeight());
  }
  for (i = n*n-n+1; i <= n*n; i++) {
    printf ("%d,%d,%d\n", i, n*n+1, randomWeight());
  }

  // output inner edges for the square
  i = 1;
  while (i < n*(n-1)) {

    for (j = 0; j < n; j++) {
      for (k = 0; k < n; k++) {
	printf ("%d,%d,%d\n", i+j, i+n+k, randomWeight());
      }
    }

    i += n;
  }

} 
