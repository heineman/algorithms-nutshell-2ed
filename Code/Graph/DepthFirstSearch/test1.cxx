/**
 * @file Graph/DepthFirstSearch/test1.cxx   Test Case for Depth-First Search
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

/** cormen example, p. 479, first edition  */
int main (int argc, char **argv) {
  int n = 6;
  int i;
  Graph g(n,true);

  g.addEdge (0, 3);
  g.addEdge (0, 1);
  g.addEdge (1, 4);
  g.addEdge (2, 5);
  g.addEdge (2, 4);
  g.addEdge (3, 1);
  g.addEdge (4, 3);
  g.addEdge (5, 5);

  vector<int> pred(g.numVertices());

  dfsSearch (g, 0, pred);

  for (i = 0; i < n; i++) {
    cout << i << ": " << pred[i] << "\n";
  }

  cout << "Passed test\n";

}

