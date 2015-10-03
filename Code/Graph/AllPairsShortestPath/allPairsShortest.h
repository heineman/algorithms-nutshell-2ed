/**
 * @file allPairsShortest.h   Defines the All Pairs Shortest Problem interface
 * @brief 
 *
 *   Can be implemented by a variety of implementations, including Floyd
 *   Warshall
 *
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _ALL_PAIRS_SHORTEST_PATH_
#define _ALL_PAIRS_SHORTEST_PATH_

#include "Graph.h"

/**
 * interface to all-pairs shortest path 
 * \param  graph   contains the graph instance
 * \param  dist    output dist[][] array for shortest distance from each vertex
 * \param  pred    output pred[][] array to be able to recompute shortest paths
 */
void allPairsShortest(Graph const &graph,
		      vector< vector<int> > &dist,
		      vector< vector<int> > &pred);

/**
 * interface to compute shortest path between <s,t>. Placed in path 
 * \param  s       desired source vertex
 * \param  t       desired target vertex
 * \param  pred    output pred[][] array computed by allPairsShortest.
 * \param  path    list which contains the path, should it exist.
 */
void constructShortestPath(int s, int t, 
			   vector< vector<int> > const &pred,
			   list<int> &path);

#endif /* _ALL_PAIRS_SHORTEST_PATH_ */
