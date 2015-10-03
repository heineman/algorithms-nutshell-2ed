/**
 * @file counter_bfs.cxx    Extends Breadth First Search with counter
 * @brief 
 *   A breadth-first search implementation that increments a counter with
 *   each vertex found, to be able to compare results against corresponding
 *   depth-first search implementations.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include "bfs.h"

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
  int ctr = 0;
  pred.assign(n, -1);
  dist.assign(n, numeric_limits<int>::max());
  vector<vertexColor> color (n, White);

  dist[s] = 0;
  color[s] = Gray;
  ctr++;

  queue<int> q;
  q.push(s);
  while (!q.empty()) {
    int u = q.front();

    // Explore neighbors of u to expand the search horizon
    for (VertexList::const_iterator ci = graph.begin(u);
	 ci != graph.end(u); ++ci) {
      int v = ci->first;
      if (color[v] == White) {
	dist[v] = dist[u]+1;
	pred[v] = u;
	color[v] = Gray;
	ctr++;
	q.push(v);
      }
    }

    q.pop();
    color[u] = Black;
    ctr++;
  }
}
