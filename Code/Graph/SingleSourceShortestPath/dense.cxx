/**
 * @file dense.cxx    Dijkstra's implementation for dense graphs
 * @brief 
 * 
 *   Contains implementation of Dijkstra's Algorithm for solving Single
 *   Source Shortest Path problems. Targeted primarily for Dense graphs.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <cstdio>

#include "Graph.h"

/** Useful debugging function. */
void outputDense (int n, 
	     vector<int> &dist, vector<int> &pred) { /* out */
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
 * Given directed, weighted graph, compute shortest distance to all
 * vertices in graph (dist) and record predecessor links for all vertices
 * (pred) to be able to recreate these paths. Suitable for Dense Graphs
 * Only.
 */
void singleSourceShortest(Graph const &graph, int s,              /* in */
			  vector<int> &dist, vector<int> &pred) { /* out */

  // initialize dist[] and pred[] arrays. Start with vertex s by setting
  // dist[] to 0.
  const int n = graph.numVertices();
  pred.assign(n, -1);
  dist.assign(n, numeric_limits<int>::max());
  vector<bool> visited(n);
  dist[s] = 0;

  // find vertex in ever-shrinking set, V-S, whose dist value is smallest
  // Recompute potential new paths to update all shortest paths
  while (true) {
    // find shortest distance so far in unvisited vertices
    int u = -1;
    int sd = numeric_limits<int>::max();   // assume not reachable
    for (int i = 0; i < n; i++) {
      if (!visited[i] && dist[i] < sd) {
	sd = dist[i];
	u = i;
      }
    }
    if (u == -1) { break; }    // no more progress to be made

    // For neighbors of u, see if length of best path from s->u + weight
    // of edge u->v is better than best path from s->v.
    visited[u] = true;
    for (VertexList::const_iterator ci = graph.begin(u);
	 ci != graph.end(u); ++ci) {
      int v = ci->first;                   // the neighbor v
      long newLen = dist[u];               // compute as long
      newLen += ci->second;                // sum with (u,v) weight
      if (newLen < dist[v]) {
	dist[v] = newLen; 
	pred[v] = u;
      }
    }
  }
}
