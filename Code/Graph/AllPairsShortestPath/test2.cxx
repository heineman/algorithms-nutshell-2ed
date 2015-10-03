/**
 * @file Graph/AllPairsShortestPath/test2.cxx   Test Case for Floyd-Warshall
 * @brief 
 *   A test case
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include <iostream>
#include <cassert>
#include <climits>

#include "allPairsShortest.h"

/** Helper method for asserting a path from expected values. */
#define INF numeric_limits<int>::max()
void assertPath(int s, int t, vector< vector<int> > &pred,
		int e, int expect[]) {
  list<int> path(0);
  constructShortestPath(s, t, pred, path);

  int i = 0;
  for (list<int>::const_iterator ci = path.begin();
       ci != path.end(); ++ci) {
    e--;
    assert (expect[i++] == (*ci));
  }

  assert (e == 0);
}

/** test case from Cormen, 2nd edition, p. 626 */
int main () {
  int n = 4;
  Graph g (n, true);

  // cormen counts from 1. we count from 0.
  g.addEdge (0, 1, 3);
  g.addEdge (2, 0, 8);
  g.addEdge (3, 0, 3);
  g.addEdge (3, 1, 1);
  g.addEdge (2, 3, 5);

  vector< vector<int> > dist(n, vector<int>(n));
  vector< vector<int> > pred(n, vector<int>(n));

  allPairsShortest(g, dist, pred);

  int results[5][5] = {
    {0,3,INF,INF},
    {INF,0,INF,INF},
    {8,6,0,5},
    {3,1,INF,0}};

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      assert (dist[i][j] == results[i][j]);
    }
  }

  // validate some invalid paths
  assertPath (4, 1, pred, 0, NULL);
  assertPath (1, 4, pred, 0, NULL);  // bad ids

  int res2[] = {2,3,1};
  assertPath (2, 1, pred, 3, res2);

  assertPath (0, 2, pred, 0, NULL);

  assertPath (1, 2, pred, 0, NULL);

  cout << "Test passed.\n";
}


