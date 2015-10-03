/**
 * @file figure.cxx    Contains Helper code to generate figures in book.
 * @brief 
 *
 * Helper functions to report information as shown in Figures 6-10 & 6-12
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <iostream>
#include "Graph.h"

/**
 * store vertices in order of search, for final report
 */
vector<int> trace(16);

/**
 * Current index within this trace.
 */
int traceIdx = -1;

/**
 * Output stats once the given number of black nodes have been colored.
 * If you set this to the size of the graph, then full output will show.
 */
int countBlackNodes;


/** Size of sample graph. */
int n = 16;

/** Helper function to output 's' or 't' or index for vertex. */
void outV(const int u) {
    if (u == 0) {
      cout << "s"; 
    } else if (u == 15) {
      cout << "t"; 
    } else {
      cout << u; 
    }
}

/**
 * Output table and color information for Figure 6-10.
 */
void generateFigure(Graph const &graph,                            /* in */
	 vector<int> const &d, vector<int> const &f,               /* in */
	 vector<int> const &pred, vector<vertexColor> const &color /* in */
	 ) {
  cout << "n\tpred\td\tf\t\n";
  for (int i = 0; i <= traceIdx; i++) {
    int u = trace[i];

    // show 's' for source and 't' for target; otherwise proper index
    outV(u);

    cout << "\t";
    outV(pred[u]);
    cout << "\t";

    if (d[u] == -1) { cout << "*"; } else { cout << d[u]; }

    // might not have any f values...
    if (f.size() != 0) {
      cout << "\t";
      if (f[u] == -1) { cout << "*"; } else { cout << f[u]; }
    }
    cout << "\n";
  }

  cout << "\nValues shown as '*' reflect future computations.\n";

  cout << "\nAssigned Vertex colors are:\n";

  cout << "  Black: ";
  for (int i = 0; i < graph.numVertices(); i++) {
    if (color[i] == Black) { outV(i); cout << " "; }
  }

  cout << "\n  Gray: ";
  for (int i = 0; i < graph.numVertices(); i++) {
    if (color[i] == Gray) { outV(i); cout << " "; }
  }

  cout << "\n  White: ";
  for (int i = 0; i < graph.numVertices(); i++) {
    if (color[i] == White) { outV(i); cout << " "; }
  }

  cout << "\n\n";


}
