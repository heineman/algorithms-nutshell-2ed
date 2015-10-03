/**
 * @file Graph/testGraph.cxx   Sample (and Simple!) graph tests.
 * @brief 
 *   Just a few test cases. Not too much here, really.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <iostream>
#include <assert.h>
#include "Graph.h"

/** Launch the tests. */
int main () {

  Graph g (3,false);
  g.addEdge (0, 1);
  g.addEdge (0, 2);
  g.addEdge (1, 2);

  assert (g.isEdge (0,1));
  g.removeEdge (1,0);
  assert (!g.isEdge (0,1));
  g.addEdge (0, 1);
  g.addEdge (0, 1);
  assert (g.isEdge (0,1));
  g.removeEdge (0,1);
  assert (!g.isEdge (0,1));

  cout << "Passed Test\n";
}
