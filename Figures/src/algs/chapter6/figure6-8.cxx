/**
 * @file figure6_10.cxx    Contains Figure for book.
 * @brief 
 *   Sample graph to be used to generate figure in book.
 *
 *   Modify dfs_visit code to output table as it appears in the book.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <iostream>

#include "dfs.h"
#include "helper.h"

/** 
 * Visit a vertex, u, in the graph and update information.
 * \param graph    the graph being searched.
 * \param u        the vertex being visited.
 * \param d        array of counter values when each vertex is discovered.
 * \param f        array of counter values when each vertex is finished.
 * \param pred     array of previous vertices in the depth-first search tree.
 * \param color    array of vertex colors in the depth-first search tree.
 * \param ctr      counter to use for assigning d[] and f[] values.
 * \param labels   structure to store all edge labels.
 */
void dfs_visit (Graph const &graph, int u,               /* in */
	 vector<int> &d, vector<int> &f,                 /* out */
	 vector<int> &pred, vector<vertexColor> &color,  /* out */
	 int &ctr, list<EdgeLabel> &labels) {            /* out */
  color[u] = Gray;
  d[u] = ++ctr;

  // record where we are
  trace[++traceIdx] = u;

  // process all neighbors of u.
  for (VertexList::const_iterator ci = graph.begin(u);
       ci != graph.end(u); ++ci) {
    int v = ci->first;

    // Compute edgeType and add to labelings. Default to cross
    edgeType type = Cross;
    if (color[v] == White) { type = Tree; }
    else if (color[v] == Gray) { type = Backward; }
    else { if (d[u] < d[v]) type = Forward; }
    labels.push_back(EdgeLabel (u, v, type));

    // Explore unvisited vertices immediately and record pred[].
    // Once recursive call ends, backtrack to adjacent vertices.
    if (color[v] == White) {
      pred[v] = u;
      dfs_visit (graph, v, d, f, pred, color, ctr, labels);
    }
  }

  color[u] = Black;  // our neighbors are complete; now so are we.
  f[u] = ++ctr;

  if (--countBlackNodes == 0) {
    generateFigure(graph, d, f, pred, color);
  }

}

/**
 * Perform Depth First Search starting from vertex s, and compute the
 * values d[u] (when vertex u was first discovered), f[u] (when all
 * vertices adjacent to u have been processed), pred[u] (the predecessor
 * vertex to u in resulting depth-first search forest), and label edges
 * according to their type.
 *
 * \param graph    the graph being searched.
 * \param s        the vertex to use as the source vertex.
 * \param d        array of counter values when each vertex is discovered.
 * \param f        array of counter values when each vertex is finished.
 * \param pred     array of previous vertices in the depth-first search tree.
 * \param labels   structure to store all edge labels.
 */
void dfs_search (Graph const &graph, int s,          /* in */
	 vector<int> &d, vector<int> &f,             /* out */
	 vector<int> &pred, list<EdgeLabel> &labels) /* out */
{
  // initialize d[], f[], and pred[] arrays. Mark all vertices White
  // to signify unvisited. Clear out edge labels.
  int ctr = 0;
  const int n = graph.numVertices();
  vector<vertexColor> color (n, White);
  d.assign(n, -1);
  f.assign(n, -1);
  pred.assign(n, -1);
  labels.clear();

  // Search starting at the source vertex; when done, visit any
  // vertices that remain unvisited.
  dfs_visit (graph, s, d, f, pred, color, ctr, labels);
  for (int u = 0; u < n; u++) {
    if (color[u] == White) {
      dfs_visit (graph, u, d, f, pred, color, ctr, labels);
    }
  }
}


/** Example to use in Chapter 6 */
int main (int argc, char **argv) {
  int n = 16;
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

  // prepare output values.
  vector<int> d(g.numVertices());
  vector<int> f(g.numVertices());
  vector<int> pred(g.numVertices());
  list<EdgeLabel> labels; 

  // compute and report state of graph once 5 nodes are completed.
  cout << "The following graph is different than Figure 6-10 in the\n";
  cout << "First edition of the book. We have chosen now to output\n";
  cout << "The progress of the algorithm after a fixed number of nodes\n";
  cout << "are colored black. In this way, we can more accurately compare\n";
  cout << "Breadth-first search and Depth-first search.\n\n";

  countBlackNodes = 5;
  dfs_search (g, 0, d, f, pred, labels);

  cout << "\nThe Final Table is:\n\nn\tpred\td\tf\n";
  for (int i = 0; i < n; i++) {
    outV(i);
    cout << "\t";
    outV(pred[i]);
    cout << "\t" << d[i] << "\t" << f[i] << "\n";
  }


  cout << "Note that this graph is undirected which means that\n";
  cout << "All edges appear twice in the graph\n";
  // the order of the edges reflects the order of the search
  for (list<EdgeLabel>::const_iterator it = labels.begin();
       it != labels.end(); ++it) {
    EdgeLabel ei = (*it);
    cout << ei.describe() << "\n";
  }
}


