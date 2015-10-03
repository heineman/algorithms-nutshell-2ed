/**
 * @file Graph/AllPairsShortestPath/figure.c   Compute the results used by Figure in book.
 * @brief 
 *   Computes the AllPairsShortestPath for sample graph.
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include "allPairsShortest.h"

/** Run AllPairsShortestPath on sample graph. */
int main () {
  int n = 5;
  Graph *g = new GraphList(n);

  g->addEdge (0, 1, 10); 
  g->addEdge (0, 3, 5); 
  g->addEdge (1, 2, 1); 
  g->addEdge (1, 3, 2); 
  g->addEdge (2, 4, 4); 
  g->addEdge (3, 1, 3); 
  g->addEdge (3, 2, 9); 
  g->addEdge (3, 4, 2); 
  g->addEdge (4, 0, 7); 
  g->addEdge (4, 2, 6); 

  int **dist = (int **) calloc (n, sizeof (int *));
  for (int i = 0; i < n; i++) {
    dist[i] = (int *) calloc (n, sizeof (int));
  }

  allPairsShortest(g, 0, dist);

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
      printf ("%d (%d) ", i, dist[i][j]);
    }
    printf ("\n");
  }
}


