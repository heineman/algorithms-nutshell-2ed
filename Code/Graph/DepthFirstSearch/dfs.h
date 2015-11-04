/**
 * @file dfs.h    Defines the interface to Depth-First Search
 * @brief 
 *   Defines the interface to depth-first search
 *
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _DFS_H_
#define _DFS_H_

#include <sstream>
#include "Graph.h"

/**
 * Helper class for labeling edges.
 */
class EdgeLabel {
 public:
  EdgeLabel (int src, int target, edgeType type)
    : src_(src), target_(target), type_(type) {}

  /** Source of edge. */
  int src() { return src_; }

  /** Target of edge. */
  int target() { return target_; }

  /** Type of edge. */
  edgeType type() { return type_; }

  /** String representation of edge type. */
  string describe() {

    ostringstream oss;
    oss << "(" << src_ << "," << target_ << ") [";

    if (type_ == Tree) {
      oss << "Tree";
    } else if (type_ == Backward) {
      oss << "Back";
    } else if (type_ == Forward) {
      oss << "Forward";
    } else if (type_ == Cross) {
      oss << "Cross";
    }

    oss << "]";
    
    return oss.str();
  }


 private:
  int        src_;
  int        target_;
  edgeType   type_;
};

/**
 * Perform Depth First Search starting from vertex s, and compute the
 * values d[u] (when vertex u was first discovered), f[u] (when all
 * vertices adjacent to u have been processed), pred[u] (the predecessor
 * vertex to u in resulting depth-first search forest), and label edges
 * according to their type.
 * 
 * \param graph    the graph to be searched.
 * \param s        the source vertex from which to commence search.
 * \param pred     array of previous vertices in the depth-first search tree.
 */
void dfsSearch (Graph const &graph, int s,      /* in */
	vector<int> &pred);                     /* out */

#endif  /* _DFS_H_ */
