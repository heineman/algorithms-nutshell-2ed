/**
 * @file tsplib.c   Test driver code that understands TSP data formatn
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

#include "singleSourceShortest.h"
#include "Graph.h"

extern int gettimeofday(struct timeval *tp, void *tzp);

/** Time before process starts.   */
static struct timeval before;

/** Time after process completes. */
static struct timeval after;

/** Key constant for converting TSP formats into ones we can deal with. */
float RRR = 6378.388;

/** Should this operation be verbose? */
int verbose = 0;

/**
 * taken from TSP description 
 *
 * http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/TSPFAQ.html
 */
int dist (int i, int j, float **values) {
  double q1 = cos( values[i][0] - values[j][0] );
  double q2 = cos( values[i][1] - values[j][1] );
  double q3 = cos( values[i][1] + values[j][1] );
  int dij = (int) ( RRR * acos( 0.5*((1.0+q1)*q2 - (1.0-q1)*q3) ) + 1.0); 
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
void process (char *fileName, bool dense) {
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
    if (verbose) {
      printf ("%s", buf); 
    }
  }
  
  Graph graph (n, false);

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

  if (dense) {
    printf ("Using dense implementation\n");
    int **weight = new int*[n];
    for (int k = 0; k < n; k++) {
      weight[k] = new int[n];
    }

    int *pred = new int [n];
    int *d = new int [n];

    // convert into distances for each pair
    for (i = 0; i < n-1; i++) {
      for (j = i+1; j < n; j++) {
	if (i != j) {
	  int d = dist (i, j, values);
	  weight[i][j] = d;
	  weight[j][i] = d;
	}
      }
    }

    gettimeofday(&before, (struct timezone *) NULL);    // begin time
    singleSourceShortestDense(n, weight, 0, d, pred);
    gettimeofday(&after, (struct timezone *) NULL);     // end time

    if (verbose) {
      for (int i = 0; i < n; i++) {
	printf ("%d. %d\n", i, d[i]);
      }
    }
  } else {
    // convert into distances for each pair
    for (i = 0; i < n-1; i++) {
      for (j = i+1; j < n; j++) {
	if (i != j) {
	  int d = dist (i, j, values);
	  graph.addEdge (i, j, d);
	}
      }
    }
    
    vector<int> dist(n);
    vector<int> pred(n);
    
    gettimeofday(&before, (struct timezone *) NULL);    // begin time
    singleSourceShortest(graph, 0, dist, pred);
    gettimeofday(&after, (struct timezone *) NULL);     // end time

    if (verbose) {
      for (int i = 0; i < n; i++) {
	printf ("%d. %d\n", i, dist[i]);
      }
    }
  }

  long usecs = diffTimer (&before, &after);           // show results

  printDiffTimer(usecs);
}


/** 
 * Load up sample graph from TSP file and compute time to perform single
 * source shortest path. User has option to select the dense graph processing
 * or the traditional Dijsktra's Algorithm.
 */
int main (int argc, char **argv) {
  int idx = 0;
  char c;
  char *fileName = 0;
  bool dense = false;

  verbose = 0;
  opterr = 0;   // disable errors
  while ((c = getopt(argc, argv, "df:v")) != -1) {
    idx++;
    switch (c) {

    case 'd':
      dense = true;
      break;

    case 'f':
      fileName = strdup (optarg);
      break;

    case 'v':
      verbose = 1;
      break;

    default:
      break;
    }
  }
  optind = 0;  // reset getopt for next time around.

  if (fileName == 0) {
    printf ("Usage: [-v] [-d] -f file.tsp\n");
    exit (-1);
  } 

  if (strncmp (fileName + strlen(fileName)-4, ".tsp", 4)) {
    printf ("Only works on TSP extensions.\n");
    printf ("Usage: [-v] [-d] -f file.tsp\n");
    exit (-1);
  }

  process (fileName, dense);

}
