/**
 * @file Graph/ZeroKnowledge/sample.cxx   Driver to show size of complex graphs that are possible
 * @brief 
 * 
 *   In Chapter 10 of the book, a problem is posed regarding zero knowledge
 *   proofs. The key to the approach is the ability to construct graphs for
 *   which two problems -- GraphIsomorphism and HamiltonianCycle -- are
 *   known to be NP-complete (i.e., Extremely hard). While we don't solve
 *   these two problems here, we show the construction of the Graph that
 *   would be used.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <cassert>
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <math.h>

#include "Graph.h"

/**
 * Output graph to a file.
 *  
 * File contains:

 *     Header
 *     V E
 *     v1,v2
 *     v1,v5
 *     ...
 */
void output (Graph const &graph, const char *fileName, const char *header)  {
  FILE *fp = fopen (fileName, "w");

  int n = graph.numVertices();

  // compute number of edges.
  int numEdges = 0;
  for (int i = 0; i < n-1; i++) {
    for (int j = 0; j < n; j++) {
      if (graph.isEdge (i,j)) {
	numEdges++;
      }
    }
  }

  fprintf (fp, "%s\n", header);
  fprintf (fp, "%d %d undirected\n", n, numEdges);
  
  for (int i = 0; i < n-1; i++) {
    for (int j = 0; j < n; j++) {
      if (graph.isEdge (i,j)) {
	fprintf (fp, "%d %d\n", i, j);
      }
    }
  }
  
  fclose(fp);
}

/** Generate the sample large graph. */
int main (int argc, char **argv) {
  int n = 16384;

  Graph g (n);

  // randomly create cycle using all vertices in a random order. Start
  // by constructing a random ordering of all vertices and then chain 
  // a single path through the graph.
  vector<int> vertices(n);
  for (int i = 0; i < n; i++) {
    vertices[i] = i;
  }
  for (int i = 0; i < n/2; i++) {
    int v1 = (int) (n * (rand() / (RAND_MAX + 1.0)));
    int v2 = (int) (n * (rand() / (RAND_MAX + 1.0)));

    int tmp = vertices[v1];
    vertices[v2] = vertices[v1];
    vertices[v1] = tmp;
  }

  // create cycle.
  for (int i = 0; i < n; i++) {
    g.addEdge (vertices[i], vertices[i+1]);
  }
  g.addEdge (vertices[n-1], vertices[0]);

  // now, randomly construct n*log n random edges over this graph
  // it's alright that we use log_e since we only need a rough number
  // of edges to confuse the original cycle. 
  int numToAdd = (int)(log(n)*n);
  for (int i = 0; i < numToAdd; i++) {
    int v1, v2;
    do {
      v1 = (int) (n * (rand() / (RAND_MAX + 1.0)));
      v2 = (int) (n * (rand() / (RAND_MAX + 1.0)));

    } while (g.isEdge (vertices[v1],vertices[v2]));

    g.addEdge (vertices[v1], vertices[v2]);
  }
  
  output (g, "sample.graph", "private key");
}

