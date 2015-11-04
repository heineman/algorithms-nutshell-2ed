/**
 * @file dfs.cxx    Contains the Depth-First Search implementation
 * @brief 
 *   Implementation of Depth First Search algorithm over a graph.
 *
 * @author George Heineman
 * @date 8/30/15
 */

#include "dfs.h"

/** 
 * Visit a vertex, u, in the graph and update information.
 * \param graph    the graph being searched.
 * \param u        the vertex being visited.
 * \param pred     array of previous vertices in the depth-first search tree.
 * \param color    array of vertex colors in the depth-first search tree.
 */
void dfsVisit (Graph const &graph, int u,                  /* in */
         vector<int> &pred, vector<vertexColor> &color) {  /* out */
  color[u] = Gray;

  // process all neighbors of u.
  for (VertexList::const_iterator ci = graph.begin(u);
       ci != graph.end(u); ++ci) {
    int v = ci->first;

    // Explore unvisited vertices immediately and record pred[].
    // Once recursive call ends, backtrack to adjacent vertices.
    if (color[v] == White) {
      pred[v] = u;
      dfsVisit (graph, v, pred, color);
    }
  }

  color[u] = Black;  // our neighbors are complete; now so are we.
}

/**
 * Perform Depth First Search starting from vertex s and compute pred[u],
 * the predecessor vertex to u in resulting depth-first search forest.
 *
 * \param graph    the graph being searched.
 * \param s        the vertex to use as the source vertex.
 * \param pred     array of previous vertices in the depth-first search tree.
 */
void dfsSearch (Graph const &graph, int s,      /* in */
                vector<int> &pred)              /* out */
{
  // initialize pred[] array and mark all vertices White
  // to signify unvisited.
  const int n = graph.numVertices();
  vector<vertexColor> color (n, White);
  pred.assign(n, -1);

  // Search starting at the source vertex; when done, visit any
  // vertices that remain unvisited.
  dfsVisit (graph, s, pred, color);

  // These are optional operations that complete the search on other
  // unvisited vertices. These are omitted from the description in the book.
  for (int u = 0; u < n; u++) {
    if (color[u] == White) {
      dfsVisit (graph, u, pred, color);
    }
  }
}
