/**
 * @file Graph/SingleSourceShortestPath/test.cxx   Test Case for Single Source
 * @brief 
 *   A test case
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <iostream>
#include <cassert>

#include "singleSourceShortest.h"

/** Launch a test case. */
int main () {
  int n = 7;
  Graph g(n);

  g.addEdge (0, 1, 10); 
  g.addEdge (0, 3, 5); 
  g.addEdge (1, 2, 1); 
  g.addEdge (1, 3, 2); 
  g.addEdge (2, 4, 4); 
  g.addEdge (3, 1, 3); 
  g.addEdge (3, 2, 9); 
  g.addEdge (3, 4, 2); 
  g.addEdge (4, 0, 7); 
  g.addEdge (4, 2, 6); 
  g.addEdge (5, 6, 2); 

  vector<int> dist(n);
  vector<int> pred(n);

  singleSourceShortest(g, 0, dist, pred);

  for (int i = 0; i < n; i++) {
    cout << i << ". " << dist[i] << " " << pred[i] << "\n";
  }

  assert (dist[0] == 0);
  assert (dist[1] == 7);
  assert (dist[2] == 8);
  assert (dist[3] == 5);
  assert (dist[4] == 7);
  assert (dist[5] == numeric_limits<int>::max());
  assert (dist[6] == numeric_limits<int>::max());

  cout << "tests passed\n";

}
