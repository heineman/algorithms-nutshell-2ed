/**
 * @file figure6_12.cxx    Sample figure showing Breadth-First Search for book
 * @brief 
 *   Implementation of figure for book.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <cassert>
#include <iostream>

#include "bfs.h"
#include "helper.h"

/** Counter to mimic the one in figure 6_10. */
int ctr = 0;

/**
 * Perform breadth-first search on graph from vertex s, and compute BFS
 * distance and pred vertex for all vertices in the graph.
 */
void bfs_search (Graph const &graph, int s,             /* in */
		 vector<int> &dist, vector<int> &pred)  /* out */
{
  // initialize dist and pred to mark vertices as unvisited. Begin at s
  // and mark as Gray since we haven't yet visited its neighbors.
  const int n = graph.numVertices();
  pred.assign(n, -1);
  dist.assign(n, numeric_limits<int>::max());
  vector<vertexColor> color (n, White);

  dist[s] = 0;
  color[s] = Gray;

  queue<int> q;
  q.push(s);
  while (!q.empty()) {
    int u = q.front();

    // record where we are
    trace[++traceIdx] = u;
    ++ctr;

    // Explore neighbors of u to expand the search horizon
    for (VertexList::const_iterator ci = graph.begin(u);
	 ci != graph.end(u); ++ci) {
      int v = ci->first;
      if (color[v] == White) {
	dist[v] = dist[u]+1;
	pred[v] = u;
	color[v] = Gray;
	q.push(v);
      }
    }

    q.pop();
    color[u] = Black;

    // increment here to keep in sync with DFS example
    if (--countBlackNodes == 0) {
      vector<int> empty(0);

      generateFigure (graph, dist, empty, pred, color);
    }
  }
}


/**  Example to use in Chapter 6 */
int main (int argc, char **argv) {
  int n = 16;
  Graph g (16);

  g.addEdge (0, 8,  7);
  g.addEdge (0, 6,  3);
  g.addEdge (0, 1,  7);
  g.addEdge (1, 3,  2);
  g.addEdge (1, 2,  2);
  g.addEdge (2, 11, 8);
  g.addEdge (2, 10, 1);

  g.addEdge (3, 12, 4);
  g.addEdge (3, 4,  3);

  g.addEdge (4, 13, 1);
  g.addEdge (4, 5,  2);

  g.addEdge (5, 9,  1);
  g.addEdge (5, 6,  3);
  g.addEdge (6, 7,  3);
  g.addEdge (7, 9,  3);
  g.addEdge (7, 8,  1);
  g.addEdge (8, 14, 2);
  g.addEdge (9, 15, 13);

  // prepare output values.
  vector<int> d(n);
  vector<int> pred(n);

  // compute and report state of graph once 5 nodes are completed.
  cout << "The following graph is different than Figure 6-12 in the\n";
  cout << "first edition of the book. We have chosen now to output\n";
  cout << "the progress of the algorithm after a fixed number of nodes\n";
  cout << "are colored black. In this way, we can more accurately compare\n";
  cout << "Breadth-first search and Depth-first search.\n\n";

  countBlackNodes = 5;
  bfs_search (g, 0, d, pred);
  
  cout << "\nThe Final Table is:\n\nn\tpred\tdist\n";
  for (int i = 0; i < n; i++) {
    outV(i);
    cout << "\t";
    outV(pred[i]);
    cout << "\t" << d[i] << "\n";
  }

  // obvious ones, but good enough.
  assert (pred[1] == 0);
  assert (d[1] == 1);

  assert (pred[15] == 9);
  assert (d[15] == 4);
}

