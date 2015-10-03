/**
 * @file Graph.h   Defines the Graph concept
 * @brief 
 *   A Graph=(V,E) and is a central data structure for numerous algorithms.
 *   Each edge (u,v) has a weight; if no edge weights are assigned then the
 *   default value of '1' is used.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _GRAPH_H_
#define _GRAPH_H_

#include <limits>
#include <list>
#include <vector>

using namespace std;

// For vertex u, store information about (v,w) where edge (u,v)
// has the designated edge weight w. The first value is the id
// for the vertex v while the second value is the weight w.
typedef pair<int,int>          IntegerPair;

// Adjacency list for a vertex
typedef list<IntegerPair>    VertexList;

/** Types of vertes colorings used by the DepthFirst and BreadthFirst searches. */
enum vertexColor { White, Gray, Black };

/** Types of computed edge types during DepthFirstSearch. */
enum edgeType { Tree, Backward, Forward, Cross };

/**
 * Graph superclass to define common interface for Graph objects and
 * provide ability to load up from a file.
 *
 * @author George Heineman
 * @date 6/15/08
 */
class Graph {

public:

  // creation interface
  // ------------------
  //
  /** Create empty undirected graph. */
  Graph () : n_(0), directed_(false) {
    // by standard we can't create zero-element array, so 
    // we create with one element since it won't matter.
    vertices_ = new VertexList[1];
  }

  /** Create directed graphs with n vertices. */
  Graph (int n, bool d) : n_(n), directed_(d) {
    vertices_ = new VertexList[n];
  }

  /** Create undirected graph with n vertices. */
  Graph (int n) : n_(n), directed_(false) {
    vertices_ = new VertexList[n];
  }

  /** Release graph memory. */
  ~Graph () { n_ = -1; delete [] vertices_; }

  /** Load up the graph encoded in the file. */
  void load (char *fileName);

  // read-only information about graph
  // ----------------------------
  const int numVertices() const { return n_; }
  bool directed() const { return directed_; }
  bool isEdge (int, int) const;
  bool isEdge (int, int, int &) const;
  int edgeWeight (int, int) const;

  VertexList::const_iterator begin(int u) const {
    return vertices_[u].begin();
  }

  VertexList::const_iterator end(int u) const {
    return vertices_[u].end();
  } 

  // update edge structure of graph (when no weight, assumed to be 1).
  // ---------------------------------------------------------------
  void addEdge (int u, int v) { addEdge (u, v, 1); }
  void addEdge (int, int, int);
  bool removeEdge (int, int);

 protected:

  VertexList  *vertices_;  // each vertex has list.
  int  n_;                 // size of graph 
  bool directed_;          // is graph directed?
};


#endif /* _GRAPH_H_ */

