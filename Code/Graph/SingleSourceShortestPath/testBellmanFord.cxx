/**
 * @file testBellmanFord.cxx    test case for Bellman Ford
 * @brief 
 * 
 *   A test case
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <cstdio>
#include <cstdlib>
#include <string.h>
#include <getopt.h>
#include <sys/time.h>

#include "singleSourceShortest.h"

#include "Graph.h"
#include "report.h"

/** Time before process starts.   */
static struct timeval before;

/** Time after process completes. */
static struct timeval after;

/** Launch program to load graph from a file an operate in verbose mode if needed. */
int main (int argc, char **argv) {
  int idx;
  bool verbose = 0;
  char c;
  char *fileName = 0;

  opterr = 0;   // disable errors
  while ((c = getopt(argc, argv, "f:v")) != -1) {
    idx++;
    switch (c) {

    case 'f':
      fileName = strdup (optarg);
      break;

    case 'v':
      verbose = 1;
      break;

    default:
      break;
    }
  }
  optind = 0;  // reset getopt for next time around.

  if (fileName == 0) {
    printf ("Usage: [-v] -f file\n");
    exit (-1);
  }

  Graph graph(0);
  graph.load (fileName);

  int n = graph.numVertices();

  int i;
  
  vector<int> dist(n);
  vector<int> pred(n);
  
  printf ("loaded graph with %d vertices\n", n);
  try {
    gettimeofday(&before, (struct timezone *) NULL);    // begin time
    singleSourceShortest(graph, 0, dist, pred);
    gettimeofday(&after, (struct timezone *) NULL);     // end time
    
    long usecs = diffTimer (&before, &after);           // show results
    printf("%s\n", timingString(usecs));
    
    if (verbose) {
      for (i = 0; i < n; i++) {
	printf ("%d. %d %d\n", i, dist[i], pred[i]);
      }
    }
  } catch (char const *s) {
    printf ("%s\n", s);
  }
}

