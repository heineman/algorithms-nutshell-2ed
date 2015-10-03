/**
 * @file bfs.h    Defines the interface to Breadth-First Search
 * @brief 
 *   Defines the interface to breadth-first search
 *
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _BFS_H_
#define _BFS_H_

#include <queue>
#include "Graph.h"

/**
 * Given (directed or undirected) graph, perform a breadth first search
 * starting from the provided source vertex, and compute the BFS distance
 * and predecessor vertex for all vertices in the graph.
 * \param graph    the graph to be processed.
 * \param s        the initial vertex (0 <= s < n) from which to search.
 * \param dist     the computed dist[] array for each vertex from s.
 * \param pred     the computed pred[] array to be able to recover path.
 */
void bfs_search (const Graph &graph, int s,             /* in */
		 vector<int> &dist, vector<int> &pred); /* out */


#endif  /* _BFS_H_ */
