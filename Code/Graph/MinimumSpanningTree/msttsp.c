/**
 * @file msttsp.c   Test driver code that understands TSP data formatn
 * @brief 
 *   Driver that can load up dense graphs whose input is stored using 
 *   the TSP format as recognized by the community.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <getopt.h>
#include <string.h> 
#include <sys/time.h>

#include "report.h"

#include "Graph.h"
#include "mst.h"

/** Key constant for converting TSP formats into ones we can deal with. */
float RRR = 6378.388;

/**
 * taken from TSP description 
 *
 * http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/TSPFAQ.html
 */
int dist (int i, int j, float **values) {
  //  double q1 = cos( values[i][0] - values[j][0] );
  //  double q2 = cos( values[i][1] - values[j][1] );
  //  double q3 = cos( values[i][1] + values[j][1] );
  //  int dij = (int) ( RRR * acos( 0.5*((1.0+q1)*q2 - (1.0-q1)*q3) ) + 1.0); 
  float dx = values[i][0] - values[j][0];
  float dy=  values[i][1] - values[j][1];
  int dij = (int) sqrt(dx*dx+dy*dy);
  return dij;
}

/** Scratch buffer. */
static char buf[4096];

/**
 * load up TSP data file.a as stored within a file. Process graph using 
 * dense Dijkstra's algorithm, if requested.

 <pre>
 NAME : ym7663
 COMMENT : 7663 locations in Yemen
 COMMENT : Derived from National Imagery and Mapping Agency data
 TYPE : TSP
 DIMENSION : 7663
 EDGE_WEIGHT_TYPE : EUC_2D
 NODE_COORD_SECTION
 ...
 EOF
 </pre>
 *
 */
Graph *tsp_load (char *fileName) {
  FILE *fp = fopen (fileName, "r");
  int i,j,n=0;

  // read header as a whole line, extracting dimension for constructor
  buf[0] = '\0';
  while (strncmp (buf, "NODE_COORD_SECTION", 18)) {
    int nr, i;
    char *ss = fgets (buf, 4096, fp);
    if (ss == NULL) {
      break;
    }

    nr = sscanf (buf, "DIMENSION : %d", &i);
    if (nr == 1) {
      n = i;
    }
  }
  
  Graph *graph = new Graph(n, false);

  // load values
  float **values = (float **) calloc (n, sizeof (float *));
  for (i = 0; i < n; i++) {
    values[i] = (float *) calloc (2, sizeof (float));
  }

  i = n;
  while (i-- > 0) {
    int j; 
    float longit, latit;
    int nr = fscanf (fp, "%d %f %f\n", &j, &longit, &latit);
    if (nr != 3) {
      printf("invalid line format.\n");
    }

    values[j-1][0] = longit;
    values[j-1][1] = latit;
  }

  fclose(fp);

  // convert into distances for each pair
  for (i = 0; i < n-1; i++) {
    for (j = i+1; j < n; j++) {
      if (i != j) {
	int d = dist (i, j, values);
	graph->addEdge (i, j, d);
      }
    }
  }
    
  return graph;
}


