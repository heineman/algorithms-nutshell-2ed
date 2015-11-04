/**
 * @file Graph/DepthFirstSearch/test3.cxx   Test Case for Depth-First Search
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

/** Within debugger, call this function to output table in figures. */
void output(vector<int> &pred, vector<int> &d, vector<int> &f) {
  int vals[] = { 0, 1, 2, 11, 10, 3, 12, 4, 13, 5, 6, 7, 8, 14, 9, 15};
  for (int i = 0; i < 16; i++) {
    int idx = vals[i];
    cout << idx << "\t" << pred[idx] << "\t" << d[idx] << "\t" << f[idx] << "\n";
  }
}



/** My example to use in Chapter 6 */
int main (int argc, char **argv) {
  int n = 16;
  int i;
  Graph g (n,false);

  g.addEdge (0, 6,  3);
  g.addEdge (0, 8,  7);
  g.addEdge (0, 1,  7);
  g.addEdge (1, 3,  2);
  g.addEdge (1, 2,  2);
  g.addEdge (2, 10, 1);
  g.addEdge (2, 11, 8);

  g.addEdge (3, 4,  3);
  g.addEdge (3, 12, 4);
  g.addEdge (4, 5,  2);
  g.addEdge (4, 13, 1);
  g.addEdge (5, 9,  1);
  g.addEdge (5, 6,  3);
  g.addEdge (7, 9,  3);
  g.addEdge (8, 14, 2);
  g.addEdge (7, 8,  1);
  g.addEdge (6, 7,  3);


  g.addEdge (9, 15, 13);

  vector<int> pred(g.numVertices());

  dfsSearch (g, 0, pred);

  for (i = 0; i < n; i++) {
    cout << i << ": " << pred[i] << "\n";
  }

  cout << "Passed test\n";

}

