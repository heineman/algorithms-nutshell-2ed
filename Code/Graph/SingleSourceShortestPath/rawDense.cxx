/**
 * @file rawDense.cxx    optimized Dijkstra's implementation for dense graphs 
 * @brief 
 * 
 *   Contains implementation of Dijkstra's Algorithm for solving Single
 *   Source Shortest Path problems as optimized for dense graphs. The input
 *   is a two-dimensional array rather than a graph structure.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <iostream>
#include <cstdio>

#include "Graph.h"

/** Useful for debugging. */
void output (int n, int * const dist, int * const pred) {
  printf ("dist: ");
  for (int i = 0; i < n; i++) {
    printf ("%d ", dist[i]);
  }
  printf ("\npred: ");
  for (int i = 0; i < n; i++) {
    printf ("%d ", pred[i]);
  }
  printf ("\n");
}


/**
 * Given int[][] of edge weights in raw form, compute shortest distance to
 * all vertices in graph (dist) and record predecessor links for all
 * vertices (pred) to be able to recreate these paths. An edge weight of
 * INF means no edge. Suitable for Dense Graphs Only.
 *
 * \param n       number of vertices in graph
 * \param weight  two-dimensional array of edge weights (INF means no edge).
 * \param s       the source vertex from which to compute all paths.
 * \param dist    computed dist[] array to all other vertices.
 * \param pred    computed pred[]array to contain back references for paths.
 */
void singleSourceShortestDense(int n, int ** const weight, int s,  /* in */
			       int *dist, int *pred) {             /* out */   
  // initialize dist[] and pred[] arrays. Start with vertex s by setting
  // dist[] to 0. All vertices are unvisited.
  bool *visited = new bool[n];
  for (int v = 0; v < n; v++) {
    dist[v] = numeric_limits<int>::max();
    pred[v] = -1;
    visited[v] = false;
  }
  dist[s] = 0;

  // find shortest distance from s to all unvisited vertices.  Recompute
  // potential new paths to update all shortest paths. Exit if u remains -1.
  while (true) {
    int u = -1;
    int sd = numeric_limits<int>::max();
    for (int i = 0; i < n; i++) {
      if (!visited[i] && dist[i] < sd) {
	sd = dist[i];
	u = i;
      }
    }
    if (u == -1) { break; }  // exit if no new paths found

    // For neighbors of u, see if best path-length from s->u + weight of
    // edge u->v is better than best path from s->v. Compute using longs.
    visited[u] = true;
    for (int v = 0; v < n; v++) {
      int w = weight[u][v];
      if (v == u) continue;

      long newLen = dist[u];
      newLen += w;
      if (newLen < dist[v]) {
	dist[v] = newLen; 
	pred[v] = u;
      }
    }
  }
  delete [] visited;
}
