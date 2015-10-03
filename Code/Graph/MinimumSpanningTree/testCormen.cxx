/**
 * @file testCormen.cxx   Small test case for MST
 * @brief 
 *   A test case.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <cassert>
#include <iostream>
#include "mst.h"

/** Cormen (2nd edition), p. 571 */
int main (int argc, char **argv) {
  int n = 9;
  Graph g(n, false);

  g.addEdge (0,1,4);
  g.addEdge (0,7,8);
  g.addEdge (1,2,8);
  g.addEdge (1,7,11);
  g.addEdge (2,8,2);
  g.addEdge (2,3,7);
  g.addEdge (2,5,4);
  g.addEdge (3,4,9);
  g.addEdge (4,5,10);
  g.addEdge (5,6,2);
  g.addEdge (6,7,1);
  g.addEdge (6,8,6);
  g.addEdge (7,8,7);

  vector<int> pred(n);

  mst_prim (g, pred);

  assert (pred[0] == -1);
  assert (pred[1] == 0);
  assert (pred[2] == 1);
  assert (pred[3] == 2);
  assert (pred[4] == 3);
  assert (pred[5] == 2);
  assert (pred[6] == 5);
  assert (pred[7] == 6);
  assert (pred[8] == 2);

  cout << "Tests passed\n";
}

