#!/bin/bash
#
#  This script expects TWO arguments:
#
#     $1  -- size of problem n
#     $2  -- number of trials to execute
#
#  This script reads its parameters from the $CONFIG configuration file
#
#    BINS    set of executables to execute
#    EXTRAS  extra command line arguments to use when executing them
#
CODE=`dirname $0`

SIZE=20
NUM_TRIALS=10
if [ $# -ge 1 ]
then
  SIZE=$1
  NUM_TRIALS=$2
fi

if [ "x$CONFIG" = "x" ]
then
  echo "No Configuration file (\$CONFIG) defined"
  exit 1
fi

if [ "x$BINS" = "x" ]
then
  if [ -f $CONFIG ]
  then
     BINS=`grep "BINS=" $CONFIG | cut -f2- -d'='`
   EXTRAS=`grep "EXTRAS=" $CONFIG | cut -f2- -d'='`
  fi 

  if [ "x$BINS" = "x" ]
  then
     echo "no \$BINS variable and no $CONFIG configuration "
     echo "Set \$BINS to a space-separated set of executables"
  fi
fi

echo "Report: $BINS on size $SIZE"
echo "Date: `date`"
echo "Host: `hostname`"

RESULTS=/tmp/compare.$$
for b in $BINS
do
    TRIALS=$NUM_TRIALS

    # start with number of trials followed by totals (one per line)
    echo $NUM_TRIALS > $RESULTS
    while [ $TRIALS -ge 1 ]
    do
      $b -n $SIZE -s $TRIALS $EXTRAS | grep secs | sed 's/secs//' >> $RESULTS
      TRIALS=$((TRIALS-1))
    done

    # compute average/stdev
    RES=`cat $RESULTS | $CODE/eval`
    echo "$b $RES"
    
    rm -f $RESULTS
done
