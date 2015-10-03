/**
 * @file testFS.c   Driver to simply test the loading of a graph.
 * @brief 
 *   Loads up a graph and outputs the number of vertices.
 * 
 * @author George Heineman
 * @date 6/15/08
 */
#include <stdio.h>

#include "Graph.h"

/** Launch the application. */
int main (int argc, char **argv) {
  if (argc == 1) {
    printf ("usage: ./testFS <graph>\n");
  } else {
    Graph gp (0);
    gp.load(argv[1]);
    printf ("loaded graph with %d vertices\n", gp.numVertices());
  }
  
}
