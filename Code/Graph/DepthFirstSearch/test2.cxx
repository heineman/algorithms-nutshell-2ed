/**
 * @file Graph/DepthFirstSearch/test2.cxx   Test Case for Depth-First Search
 * @brief 
 *   A test case
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <cassert>
#include <iostream>

#include "Graph.h"
#include "dfs.h"

/** cormen example, p. 481,  first edition */
int main (int argc, char **argv) {
  int n = 8;
  int i;
  Graph g(n,true);

  g.addEdge (0, 4);
  g.addEdge (1, 5);
  g.addEdge (1, 0);
  g.addEdge (4, 1);
  g.addEdge (5, 4);
  g.addEdge (2, 5);
  g.addEdge (2, 1);
  g.addEdge (6, 2);
  g.addEdge (6, 5);
  g.addEdge (3, 7);
  g.addEdge (3, 6);
  g.addEdge (7, 6); 
  g.addEdge (7, 3);

  vector<int> pred(g.numVertices());

  dfsSearch (g, 2, pred);

  for (i = 0; i < n; i++) {
    cout << i << ": " << pred[i] << "\n";
  }

  // spot check some (hardly sufficient! but just a quick check).
  assert (pred[1] == 2);

  cout << "Passed test\n";
}

