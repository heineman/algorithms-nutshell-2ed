/**
 * @file smallTest.cxx   Small test case for MST
 * @brief 
 *   A test case.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <cassert>
#include <iostream>
#include "mst.h"

/** Example to use in Chapter 6 */
int main (int argc, char **argv) {
  int n = 5;
  Graph g(n, false);

  g.addEdge (0, 1,  2);
  g.addEdge (0, 4,  4);
  g.addEdge (0, 3,  8);
  g.addEdge (1, 2,  3);
  g.addEdge (2, 3,  5);
  g.addEdge (2, 4,  1);
  g.addEdge (3, 4,  7);

  vector<int> pred(n);

  mst_prim (g, pred);

  assert (pred[0] == -1);
  assert (pred[1] == 0);
  assert (pred[2] == 1);
  assert (pred[3] == 2);
  assert (pred[4] == 2);

  cout << "Tests passed\n";
}

