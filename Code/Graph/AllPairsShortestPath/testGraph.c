/**
 * @file testGraph.c   Test Case for All Pairs Shortest Path
 * @brief 
 *   Loads up a sample graph and computes All Pairs Shortest Path.
 *   If source and target vertices are given as input then the 
 *   shortest path between the vertices is reported.
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include <iostream>
#include <cstdlib>
#include "allPairsShortest.h"

int main (int argc, char **argv) {
  if (argc == 1) {
    cout << "usage: ./testGraph <graph.dat> [<source> <target>]\n";
    exit (-1);
  }

  Graph  graph (0);

  graph.load (argv[1]);
  int n = graph.numVertices();
  cout << "loaded graph with " << n << "vertices\n";

  vector< vector<int> > dist(n, vector<int>(n));
  vector< vector<int> > pred(n, vector<int>(n));

  allPairsShortest(graph, dist, pred);

  // if multiple arguments, use.
  if (argc > 2) {
    int src = atoi(argv[2]);
    int tgt = atoi(argv[3]);

    cout << "Shortest path from " << src << " to " << tgt << " is " << 
      dist[src][tgt] << "\n";

    list<int> path(0);
    constructShortestPath (src, tgt, pred, path);

    for (list<int>::const_iterator ci = path.begin();
	 ci != path.end(); ++ci) {
      cout << *ci << " ";
    }
    cout << "\n";
  } else {
    int i,j;

    // output matrix
    for (i = 0; i < n; i++) {
      for (j = 0; j < n; j++) {
	cout << dist[i][j] << " ";
      }
      cout << "\n";
    }
  }

}
