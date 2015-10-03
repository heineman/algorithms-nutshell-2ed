/**
 * @file Graph/AllPairsShortestPath/test1.cxx   Test Case for Floyd-Warshall
 * @brief 
 *   A test case
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <iostream>
#include <cassert>

#include "allPairsShortest.h"

/** Helper method for asserting a path from expected values. */
void assertPath(int s, int t, 
		vector< vector<int> > const &pred, int expect[]) {

  list<int> path(0);
  constructShortestPath(s, t, pred, path);

  int i = 0;
  for (list<int>::const_iterator ci = path.begin();
       ci != path.end(); ++ci) {
    assert (expect[i++] == *ci);
  }
}

/** test case from Cormen, 2nd edition, p. 626 */
int main () {
  int n = 5;
  Graph g (n, true);

  // cormen counts from 1. we count from 0.
  g.addEdge (0, 1, 3);
  g.addEdge (0, 2, 8);
  g.addEdge (0, 4, -4);
  g.addEdge (1, 4, 7);
  g.addEdge (1, 3, 1);
  g.addEdge (2, 1, 4);
  g.addEdge (3, 0, 2);
  g.addEdge (3, 2, -5);
  g.addEdge (4, 3, 6);

  vector< vector<int> > dist(n, vector<int>(n));
  vector< vector<int> > pred(n, vector<int>(n));

  allPairsShortest(g, dist, pred);

  int results[5][5] = {
    {0,1,-3,2,-4},
    {3,0,-4,1,-1},
    {7,4,0,5,3},
    {2,-1,-5,0,-2},
    {8,5,1,6,0}};

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      //      printf ("%d %d %d %d\n", i, j, dist[i][j], results[i][j]);
      assert (dist[i][j] == results[i][j]);
    }
  }

  // validate some shortest paths.
  int res[] = {4,3,2,1};
  assertPath (4, 1, pred, res);

  int res2[] = {4,3,0};
  assertPath (4, 0, pred, res2);

  cout << "Test passed.\n";
}


