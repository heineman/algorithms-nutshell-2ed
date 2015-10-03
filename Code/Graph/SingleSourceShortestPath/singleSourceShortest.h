/**
 * @file singleSourceShortest.h   Defines the Single Pair Shortest Path Problem interface
 * @brief 
 *
 *   Can be implemented by a variety of implementations, including Dijkstra's algorithm.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _SINGLESOURCESHORTEST_H_
#define _SINGLESOURCESHORTEST_H_

#include "Graph.h"

/**
 * Interface to single source Shortest Path problem.
 * 
 * \param g       Graph to be computed.
 * \param s       the source vertex from which to compute paths. 
 * \param dist    computed dist[] array to all other vertices. 
 * \param pred    computed pred[] array to contain back references for paths.
 */
void singleSourceShortest(Graph const &g, int s,
			  vector<int> &dist, vector<int> &pred);  /* out */

/**
 * Interface to dense version of single source Shortest Path problem.
 * 
 * \param n       number of vertices in graph
 * \param weight  two-dimensional array of edge weights (INF means no edge).
 * \param s       the source vertex from which to compute paths. 
 * \param dist    computed dist[] array to all other vertices. 
 * \param pred    computed pred[] array to contain back references for paths.
 */
void singleSourceShortestDense(int n, int ** const weight, int s,  /* in */
			       int *dist, int *pred);              /* out */

#endif /*  _SINGLESOURCESHORTEST_H_ */
