/**
 * @file testFigure.cxx    test case for Fact Sheet figure.
 * @brief 
 * 
 *   A test case
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <cassert>
#include <iostream>

#include "singleSourceShortest.h"

/** figure in the fact sheets */
int main () {
  int n = 5;
  Graph g (n, true);

  g.addEdge (0, 1, 2); 
  g.addEdge (0, 4, 4); 
  g.addEdge (1, 2, 3); 
  g.addEdge (2, 4, 1); 
  g.addEdge (2, 3, 5); 
  g.addEdge (4, 3, 7); 
  g.addEdge (3, 0, 8); 

  vector<int> dist(n);
  vector<int> pred(n);

  singleSourceShortest(g, 0, dist, pred);

  for (int i = 0; i < n; i++) {
    cout << i << ". " << dist[i] << " " << pred[i] << "\n";
  }

  assert (dist[0] == 0);
  assert (dist[1] == 2);
  assert (dist[2] == 5);
  assert (dist[3] == 10);
  assert (dist[4] == 4);
  cout << "test checks out.\n";  

}


