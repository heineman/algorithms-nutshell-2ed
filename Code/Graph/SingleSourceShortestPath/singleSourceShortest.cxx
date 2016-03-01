/**
 * @file singleSourceShortest.cxx   Dijsktra's Algorithm Implementation 
 * @brief 
 *   Contains implementatino of Dijkstra's Algorithm.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <iostream>
#include <cstdlib>
#include <cstdio>

#include "BinaryHeap.h"
#include "Graph.h"

/** Useful debugging function. */
void output (int n, 
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
 * Given directed, weighted graph, compute shortest distance to vertices
 * (dist) and record predecessor links (pred) for all vertices. 
 * \param g     the graph to be processed.
 * \param s     the source vertex from which to compute all paths.
 * \param dist  array to contain shortest distances to all other vertices.
 * \param pred  array to contain previous vertices to be able to recompute paths.
 */
void singleSourceShortest(Graph const &g, int s,                  /* in */
                          vector<int> &dist, vector<int> &pred) { /* out */

  // initialize dist[] and pred[] arrays. Start with vertex s by setting
  // dist[] to 0. Priority Queue PQ contains all v in G.
  const int n = g.numVertices();
  pred.assign(n, -1);
  dist.assign(n, numeric_limits<int>::max());
  dist[s] = 0;
  BinaryHeap pq(n);
  for (int u = 0; u < n; u++) { pq.insert (u, dist[u]); }

  // find vertex in ever-shrinking set, V-S, whose dist[] is smallest.
  // Recompute potential new paths to update all shortest paths
  while (!pq.isEmpty()) {
    int u = pq.smallest();

    // For neighbors of u, see if newLen (best path from s->u + weight
    // of edge u->v) is better than best path from s->v. If so, update
    // in dist[v] and readjust binary heap accordingly. Compute using 
    // long to avoid overflow error.
    for (VertexList::const_iterator ci = g.begin(u); ci != g.end(u); ++ci) {
      int v = ci->first;
      long newLen = dist[u];
      newLen += ci->second;
      if (newLen < dist[v]) {
        pq.decreaseKey (v, newLen);
        dist[v] = newLen;
        pred[v] = u;
      }
    }
  }
}
