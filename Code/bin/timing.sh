#!/bin/bash

# Usage:
#  
#    ./timing.sh -n=# [Full-Unix-Command]
#
#        -n is the number of trials to run (defaults to 10)
# Output:
#
#    MEAN stdev=STDEV, COMMAND
#
#    Where COMMAND is the unix command, MEAN is the calculated MEAN
#    value where the best and worst trials are thrown out, and STDEV
#    represents the standard deviation for the remaining trials.
#
# Function:
#
# Run a set of ten trials of the given Full-Unix-Command and produce
# a report of the mean time and standard deviation where the best
# and worst times are discarded.
#
# Author:
#
#    George Heineman 

# Wherever we are, that is where you will find 'alone.sh' and 'eval'
# -----------------------------------------------------------------
BASE=`dirname $0`

# Determine the number of trials
NUM_TRIALS=10
ARG1=$1
NOPT="${ARG1#\-n=*}"
if [ "$1" != "$NOPT" ]
then
  NUM_TRIALS=$NOPT
  shift
fi

NUM=$NUM_TRIALS
REPORT=/tmp/timing$$
echo $NUM_TRIALS >> $REPORT
while [ $NUM -gt 0 ]
do
  NUM=$(($NUM-1))

  # Takes as input the command to be executed within a time loop.
  # returns as stdout the response.
  # --------------------------------------------------
  bash $BASE/alone.sh $* 2>/tmp/output.$$ > /dev/null

  REAL=`cat /tmp/output.$$ | grep real | cut -f2 -d' '`
  USER=`cat /tmp/output.$$ | grep user | cut -f2 -d' '`
   SYS=`cat /tmp/output.$$ | grep  sys | cut -f2 -d' '`

  echo $REAL >> $REPORT
  rm -f /tmp/output.$$
done

RES=`$BASE/eval < $REPORT`
rm -f $REPORT

echo "$RES, $*"
