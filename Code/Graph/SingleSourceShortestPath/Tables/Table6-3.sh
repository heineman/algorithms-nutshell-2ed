#!/bin/bash

# Execute from parent directory
EVAL=../../../bin/eval

OUTPUT=Table6-3.out
rm -f $OUTPUT

# Only run BellmanFord for 5 trials, otherwise 100 trials
NUM_TRIALS=100
NUM_BELLMAN_TRIALS=5

# store partial results here...
ACTIVE=/tmp/result.$$

echo "GRAPH,PQ,DG,optimized,Bellman-Ford" >> $OUTPUT
for i in ../TSP/*.tsp
do
  echo "$i..." 

  # Start off the trial: Dijkstra's algorithm with PQ
  # ------------------------------------------------------
  echo $NUM_TRIALS                                  > $ACTIVE
  T=0
  while [ $T -lt $NUM_TRIALS ]
  do
     ../tsp -f $i |tail -1 | sed 's/secs//'         >> $ACTIVE

     T=$((T+1))
  done
  Z_PQ=`$EVAL < $ACTIVE | cut -f1 -d' '`

  # Start off the trial: Dijkstra's algorithm for DG
  # ------------------------------------------------------
  echo $NUM_TRIALS                                  > $ACTIVE
  T=0
  while [ $T -lt $NUM_TRIALS ]
  do
     ../tspDense -f $i |tail -1 | sed 's/secs//'    >> $ACTIVE

     T=$((T+1))
  done
  Z_DG=`$EVAL < $ACTIVE | cut -f1 -d' '`

  # Start off the trial with optimized dense
  # ------------------------------------------------------
  echo $NUM_TRIALS                                  > $ACTIVE
  T=0
  while [ $T -lt $NUM_TRIALS ]
  do
     ../tsp -d -f $i |tail -1 | sed 's/secs//'>> $ACTIVE

     T=$((T+1))
  done
  Z_RAW=`$EVAL < $ACTIVE | cut -f1 -d' '`

  # Start off the trial for Bellman-Ford
  # ------------------------------------------------------
  echo $NUM_BELLMAN_TRIALS                          > $ACTIVE
  T=0
  while [ $T -lt $NUM_BELLMAN_TRIALS ]
  do
     ../tspBellmanFord -f $i |tail -1 | sed 's/secs//'>> $ACTIVE
  
     T=$((T+1))
  done
  Z_BF=`$EVAL < $ACTIVE | cut -f1 -d' '`

  # files have hidden ^M within them that must be stripped out
  LINE=`grep DIMENSION $i | cut -f2 -d: | sed 's///g' | sed 's/ //g'`
  EDGE=$((LINE*(LINE-1)/2))
  echo "$LINE,$EDGE,$Z_PQ,$Z_DG,$Z_RAW,$Z_BF"             >> $OUTPUT

done

