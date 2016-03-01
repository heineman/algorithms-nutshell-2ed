/**
 * @file allPairsShortest.cxx   Floyd-Warshall implementation
 * @brief 
 *   Contains the Floyd-Warshall implementation which solves the All Pairs
 *   Shortest Path problem. Operates over 
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include <iostream>
#include "Graph.h"

/** 
 * Useful debugging method to show dist,pred values. Also used to generate
 * useful figures for the execution of the algorithm. 
 * \param  dist    output dist[][] array for shortest distance from each vertex
 * \param  pred    output pred[][] array to be able to recompute shortest paths
 */
void debug(vector< vector<int> > &dist,
	   vector< vector<int> > &pred) {

  cout << "    dist  " << "\t" << "    pred  " << endl;
  int n = pred.size();
  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      cout << dist[i][j] << " ";
    }
    cout << "\t";
    for (int j = 0; j < n; j++) {
      cout << pred[i][j] << " ";
    }
    cout << endl;
  }
}

/**
 * Given directed, weighted graph, compute shortest distance to all vertices
 * in graph (dist).
 * \param  graph   contains the graph instance
 * \param  dist    output dist[][] array for shortest distance from each vertex
 * \param  pred    output pred[][] array to be able to recompute shortest paths
 */
void allPairsShortest(Graph const &graph,             /* in */
		      vector< vector<int> > &dist,    /* out */
		      vector< vector<int> > &pred) {  /* out */
  int n = graph.numVertices();

  // initialize dist[][] with 0 on diagonals, INFINITY where no edge
  // exists, and the weight of edge (u,v) placed in dist[u][v]. pred
  // initialized in corresponding way.
  for (int u = 0; u < n; u++) {
    dist[u].assign(n, numeric_limits<int>::max());
    pred[u].assign(n, -1);
    dist[u][u] = 0;
    for (VertexList::const_iterator ci = graph.begin(u);
	 ci != graph.end(u); ++ci) {
      int v = ci->first;
      dist[u][v] = ci->second;
      pred[u][v] = u;
    }
  }

  for (int k = 0; k < n; k++) {
    for (int i = 0; i < n; i++) {
      if (dist[i][k] == numeric_limits<int>::max()) { continue; }

      // If an edge is found to reduce distance, update dist[][].
      // Compute using longs to avoid overflow of Infinity distance.
      for (int j = 0; j < n; j++) {
	long newLen = dist[i][k];
	newLen += dist[k][j];

	if (newLen < dist[i][j]) {
	  dist[i][j] = newLen;
	  pred[i][j] = pred[k][j];
	}
      }
    }
  }
}

/**
 * Output path as list of vertices from s to t given the pred results
 * from an allPairsShortest execution. Note that s and t must be valid
 * integer vertex identifiers. If no path is found between s and t, then an
 * empty path is returned.
 * \param  s       desired source vertex
 * \param  t       desired target vertex
 * \param  pred    output pred[][] array computed by allPairsShortest.
 * \param  path    list which contains the path, should it exist.
 */
void constructShortestPath(int s, int t,                        /* in */
			   vector< vector<int> > const &pred,   /* in */
			   list<int> &path) {                   /* out */
  path.clear();
  if (t < 0 || t >= (int) pred.size() || s < 0 || s >= (int) pred.size()) {
    return;
  }

  // construct path until we hit source 's' or -1 if there is no path.
  path.push_front(t);
  while (t != s) {
    t = pred[s][t];
    if (t == -1) { path.clear(); return; }

    path.push_front(t);
  }
}

