/**
 * @file GraphList.h   Defines Graph concept for linked lists
 * @brief 
 *   Representation of a graph using adjacency lists.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _GRAPH_LIST_H_
#define _GRAPH_LIST_H_

#include "Graph.h"

/**
 * Representation of a graph using adjacency lists.
 * 
 * @author George Heineman
 * @date 6/15/08
 */
class GraphList : public Graph {

public:

  // creation interface
  // ------------------
  GraphList () : Graph() {}
  GraphList (int n) : Graph (n) { allocate(n); }

  ~GraphList () { deallocate(); }

  // read-only information about graph
  // ---------------------------------
  // expose iterator to edges emanating from this vertex
  bool isEdge (int, int);
  int edgeWeight (int, int);

  // update edge structure of graph (when no weight, assumed to be 1).
  // ---------------------------------------------------------------
  void addEdge (int u, int v, int w);
  bool removeEdge (int u, int v);

 protected:
    /** No special allocations. */
    void allocate(int n) {
      
    }

    /** Deallocate by freeing the vertices. */
    void deallocate() {
      delete [] vertices_;
    }

 private:
  void helperRemove (int, int, bool);

};


#endif /* _GRAPH_LIST_H_ */
