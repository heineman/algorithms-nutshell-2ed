#!/bin/bash

# Launch a set of experimental runs, sampling from 1 .. 16384. We run a 
# set of experiments and toss out best and worst performing, to compute
# remaining average.

DIR=`dirname $0`
TYPE=$1
if [ "x$TYPE" = "x" ]
then
  echo "USAGE: $DIR/execute.sh [UP/DOWN/SCATTERED]"
  exit 1
fi

if [ $TYPE != "UP" -a $TYPE != "DOWN" -a $TYPE != "SCATTERED" ]
then
  echo "Invalid Argument \"$TYPE\" to script.";
  exit 1
fi

NUM_TRIALS=100

LOW_N=1
HIGH_N=2048

N=$LOW_N
while [ $N -le $HIGH_N ] 
do
  REPORT=/tmp/report.$N
  rm -f $REPORT

  T=0
  while [ $T -lt $NUM_TRIALS ]  
  do
    $DIR/tester $TYPE $N >> $REPORT
    T=$((T+1))
  done
  RES=`awk -f $DIR/awk.proc < $REPORT`
  rm -f $REPORT

  echo "$N,$RES"

  # advance
  N=$((N+1))
done


