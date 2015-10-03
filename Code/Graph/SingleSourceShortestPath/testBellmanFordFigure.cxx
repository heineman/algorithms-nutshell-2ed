/**
 * @file testBellmanFordFigure.cxx    test case for Bellman Ford
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

/** Compute the results of the figure. */
int main () {
  int n = 4;
  Graph g2 (n, true);
  g2.addEdge (0,1,4);
  g2.addEdge (0,2,3);
  g2.addEdge (2,1,1);
  g2.addEdge (3,0,5);
  g2.addEdge (3,1,10);
  g2.addEdge (3,2,7);

  vector<int> dist2(n);
  vector<int> pred2(n);

  singleSourceShortest(g2, 0, dist2, pred2);

  assert (dist2[0] == 0);
  assert (dist2[1] == 4);
  assert (dist2[2] == 3);
  assert (dist2[3] == numeric_limits<int>::max());

  // try another one
  singleSourceShortest(g2, 3, dist2, pred2);

  assert (dist2[0] == 5);
  assert (dist2[1] == 8);
  assert (dist2[2] == 7);
  assert (dist2[3] == 0);

  // detect negative cycle
  n = 3;
  Graph g3 (n, true);
  g3.addEdge (0,1,-3);
  g3.addEdge (1,2,-2);
  g3.addEdge (2,0,-1);

  vector<int> dist3(n);
  vector<int> pred3(n);

  try {
    singleSourceShortest(g3, 0, dist3, pred3);
    assert (0 == 1);  // FAILURE CASE
  } catch (...) {
    cout << "caught proper exception\n";
  }
}
