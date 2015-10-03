/**
 * @file rawTest.cxx    test case for optimized implementation
 * @brief 
 * 
 *   A test case
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <iostream>
#include <cassert>

#include "singleSourceShortest.h"

/** Test the raw implementation of Dijkstra's algorithm. */
int main () {
  int n = 5;
  Graph g(n, true);

  g.addEdge (0, 1, 2);
  g.addEdge (1, 2, 3); 
  g.addEdge (0, 4, 4);
  g.addEdge (2, 4, 1); 
  g.addEdge (3, 0, 8); 
  g.addEdge (4, 3, 7);
  g.addEdge (2, 3, 5);

  int **weight = new int*[n];
  for (int i = 0; i < n; i++) {
    weight[i] = new int[n];
    for (int j = 0; j < n; j++) {
      weight[i][j] = numeric_limits<int>::max();
    }
  }

  int *pred = new int [n];
  int *dist = new int [n];

  for (int u = 0; u < n; u++) {
    for (VertexList::const_iterator ci = g.begin(u);
	 ci != g.end(u); ++ci) {
      int v = ci->first;          // the neighbor v
      weight[u][v] = ci->second;  // extract weight
    }
  }

  singleSourceShortestDense(n, weight, 0, dist, pred);

  for (int i = 0; i < n; i++) {
    cout << i << ". " << dist[i] << " " << pred[i] << "\n";
  }

  assert (dist[0] == 0);
  assert (dist[1] == 2);
  assert (dist[2] == 5);
  assert (dist[3] == 10);
  assert (dist[4] == 4);
  cout << "test checks out.\n";

  for (int i = 0; i < n; i++) {
    delete [] weight[i];
  }
  delete [] weight;
  delete [] dist;
  delete [] pred;
}
