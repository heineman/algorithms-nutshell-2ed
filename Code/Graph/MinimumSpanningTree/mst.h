/**
 * @file mst.h    Defines the interface to Minimum Spanning Tree
 * @brief 
 *   Defines the interface to minimum spanning tree problem
 *
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _MST_H_
#define _MST_H_

#include "Graph.h"

/**
 * Compute the Minimum Spanning Tree for the graph and leave results of 
 * computation within the computed pred[] array for each vertex.
 * \param graph    Graph to be used as basis for computation.
 * \param pred     pred[] array to contain the previous vertex for each vertex.
 */
void mst_prim (Graph const &graph,     /* in */
	       vector<int> &pred);     /* out */


/** Load up TSP from file. */
Graph *tsp_load (char *fileName);
#endif /* _MST_H_ */
