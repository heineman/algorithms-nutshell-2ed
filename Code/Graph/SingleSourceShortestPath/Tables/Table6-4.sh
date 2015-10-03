#!/bin/bash

# Execute from parent directory
EVAL=../../../bin/eval

OUTPUT=Table6-4.out
rm -f $OUTPUT

# Run for 100 trials
NUM_TRIALS=100

# store partial results here...
ACTIVE=/tmp/result.$$

echo "GRAPH,PQ,DG,optimized,Bellman-Ford" >> $OUTPUT
for i in ../SparseGraphs/*.dat
do
  echo "$i..." 

  # Start off the trial: Dijkstra's algorithm with PQ
  # ------------------------------------------------------
  echo $NUM_TRIALS                                  > $ACTIVE
  T=0
  while [ $T -lt $NUM_TRIALS ]
  do
     ../testGraph -f $i |tail -1 | sed 's/secs//'   >> $ACTIVE

     T=$((T+1))
  done
  Z_PQ=`$EVAL < $ACTIVE | cut -f1 -d' '`

  # Start off the trial: Dijkstra's algorithm for DG
  # ------------------------------------------------------
  echo $NUM_TRIALS                                  > $ACTIVE
  T=0
  while [ $T -lt $NUM_TRIALS ]
  do
     ../testDense -f $i |tail -1 | sed 's/secs//'   >> $ACTIVE

     T=$((T+1))
  done
  Z_DG=`$EVAL < $ACTIVE | cut -f1 -d' '`

  # Start off the trial with optimized dense
  # ------------------------------------------------------
  echo $NUM_TRIALS                                  > $ACTIVE
  T=0
  while [ $T -lt $NUM_TRIALS ]
  do
     ../testGraph -d -f $i |tail -1 | sed 's/secs//'>> $ACTIVE

     T=$((T+1))
  done
  Z_RAW=`$EVAL < $ACTIVE | cut -f1 -d' '`

  # Start off the trial for Bellman-Ford
  # ------------------------------------------------------
  echo $NUM_TRIALS                                        > $ACTIVE
  T=0
  while [ $T -lt $NUM_TRIALS ]
  do
     ../testBellmanFord -f $i |tail -1 | sed 's/secs//'>> $ACTIVE

     T=$((T+1))
  done
  Z_BF=`$EVAL < $ACTIVE | cut -f1 -d' '`

  LINE=`grep undirected $i | sed 's/ undirected//g' | sed 's/ /,/g'`
  echo "$LINE,$Z_PQ,$Z_DG,$Z_RAW,$Z_BF"             >> $OUTPUT

done

