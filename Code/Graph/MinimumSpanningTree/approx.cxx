/**
 * @file approx.cxx   Driver for using MST to compute TSP approx
 * @brief 
 *
 *
 * @author George Heineman
 * @date 3/25/2011
 */

#include <sys/time.h>

#include <cstdlib>
#include <cstdio>
#include <cstring>
#include <unistd.h>

#include "mst.h"
#include "report.h"


/** Time before process starts.   */
static struct timeval before;

/** Time after process completes. */
static struct timeval after;

void find_tour (Graph *pot, int u, vector<int> &tour) {
  int v = 0;
  bool done = false;
  while (!done) {
    done = true;
    for (VertexList::const_iterator ei = pot->begin(u); ei != pot->end(u); ++ei) {
      v = ei->first;
      done = false;
      break;
    }

    if (!done) {
      	pot->removeEdge (u, v);
	find_tour (pot, v, tour);
    }
  }

  tour.push_back(u);
}

/**
 * Launch the application to time MST. 
 *
 * Command line options include:
 * <ul>
 * <li> <code>-f FILE</code> to load up the graph from a file.
 * <li> <code>-v</code> to output information in verbose mode.
 * </ul>
*/
int main (int argc, char **argv) {
  int idx;
  bool verbose = 0;
  char c;
  char *fileName = 0;
  bool tsp = false;

  opterr = 0;   // disable errors
  while ((c = getopt(argc, argv, "f:tv")) != -1) {
    idx++;
    switch (c) {

    case 't':
      tsp = true;
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
    printf ("Usage: [-t] [-v] -f file\n");
    exit (-1);
  }

  Graph *graph = new Graph(0);
  if (tsp) {
    graph = tsp_load(fileName);
  } else {
    graph->load (fileName);
  }

  int n = graph->numVertices();
  int i;
  vector<int> pred(n);

  printf ("loaded graph with %d vertices\n", n);
  try {
    gettimeofday(&before, (struct timezone *) NULL);    // begin time

    // http://en.wikipedia.org/wiki/Travelling_salesman_problem#Heuristic_and_approximation_algorithms

    // compute MST
    mst_prim(*graph, pred);

    // for all edges (u,v) in graph add to DG pot. Traverse 'pred' for each
    // vertex to find these undirected edges.

    Graph *pot = new Graph (n, true);

    // double edges (directed wise)
    double totalDistance = 0;
    for (i = 0; i < n; i++) {
      int prev = pred[i];
      if (prev != -1) {
	int weight = graph->edgeWeight(prev, i);
	totalDistance += weight;
	printf ("%d - %d, %d\n", prev, i, weight);

	pot->addEdge (prev, i, weight);
	pot->addEdge (i, prev, weight);
      }
    }
    printf("Total MST:%f\n", totalDistance);

    // now find cycle
    // http://en.wikipedia.org/wiki/Eulerian_path
    vector<int> tour(0);
    find_tour (pot, 0, tour);

    // now convert cycle into Hamiltonian one.  Convert the Eulerian cycle
    // into the Hamiltonian one in the following way: walk along the
    // Eulerian cycle, and each time you are about to come into an already
    // visited vertex, skip it and try to go to the next one (along the
    // Eulerian cycle).
    vector<bool> visited(pot->numVertices());

    vector<int> hamiltonian(0);
    for (i = 0; i < (int) tour.size(); i++) {
      if (visited[tour[i]]) { continue; }
      visited[tour[i]] = true;
      hamiltonian.push_back(tour[i]);
    }


    totalDistance = 0;
    for (i = 0; i < (int) hamiltonian.size()-1; i++) {
      //      printf ("%d - %d\n", hamiltonian[i], hamiltonian[i+1]);
      totalDistance += graph->edgeWeight(hamiltonian[i], hamiltonian[i+1]);
    }
    printf("Total:%f\n", totalDistance);

    gettimeofday(&after, (struct timezone *) NULL);     // end time

    long usecs = diffTimer (&before, &after);           // show results
    printf("%s\n", timingString(usecs));

    if (verbose) {
      for (i = 0; i < n; i++) {
	printf ("%d. %d\n", i, pred[i]);
      }
    }
  } catch (char const *s) {
    printf ("%s\n", s);
  }
}
