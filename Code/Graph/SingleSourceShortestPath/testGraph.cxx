/**
 * @file Graph/SingleSourceShortestPath/testGraph.cxx    test case 
 * @brief 
 * 
 *   A test case
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <cstdio>
#include <cstring>
#include <cstdlib>
#include <getopt.h>
#include <sys/time.h>

#include "singleSourceShortest.h"

#include "Graph.h"
#include "report.h"

static struct timeval before;     /** Time before process starts.   */
static struct timeval after;      /** Time after process completes. */

/**
 * Run program to load graph from file and operate in verbose mode. This
 * program also has the option to invoke the Dense variation of Dijkstra's
 * algorithm as requested by the user.
 */
int main (int argc, char **argv) {
  int idx;
  bool verbose = 0;
  char c;
  char *fileName = 0;
  bool dense = false;

  opterr = 0;   // disable errors
  while ((c = getopt(argc, argv, "df:v")) != -1) {
    idx++;
    switch (c) {

    case 'd':
      dense = true;
      break;

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
    printf ("Usage: [-v] [-d] -f file\n");
    exit (-1);
  }

  Graph graph(0);
  graph.load (fileName);

  int n = graph.numVertices();

  if (dense) {
    int **weight = new int*[n];
    for (int i = 0; i < n; i++) {
      weight[i] = new int[n];
      for (int k = 0; k < n; k++) {
	weight[i][k] = 0;
      }
    }
    
    int *pred = new int [n];
    int *dist = new int [n];

    for (int u = 0; u < n; u++) {
      for (VertexList::const_iterator ci = graph.begin(u);
	   ci != graph.end(u); ++ci) {
	int v = ci->first;          // the neighbor v
	weight[u][v] = ci->second;  // extract weight
      }
    }
    
    gettimeofday(&before, (struct timezone *) NULL);    // begin time
    singleSourceShortestDense(n, weight, 0, dist, pred);
    gettimeofday(&after, (struct timezone *) NULL);     // end time

    long usecs = diffTimer (&before, &after);           // show results
    printf("%s\n", timingString(usecs));

    if (verbose) {
      for (int k = 0; k < n; k++) {
	printf ("%d. %d %d\n", k, dist[k], pred[k]);
      }
    }

    for (int i = 0; i < n; i++) {
      delete [] weight[i];
    }
    delete [] weight;
    delete [] dist;
    delete [] pred;

  } else {
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
	printf ("n\tdist[]\tpred[]\n");
	for (i = 0; i < n; i++) {
	  printf ("%d\t%d\t%d\n", i, dist[i], pred[i]);
	}
      }
    } catch (char const *s) {
      printf ("%s\n", s);
    }
  }
}
