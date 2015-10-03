#!/bin/bash
#
#
#  Expects $BINS to be the set of binaries to be tested
#
#  Arguments to this function include 
#
#     SIZE       [$1]     If not present, defaults to 20
#     NumTrials  [$2]     If not present, defaults to 10
#     Extra      [$3-5]   If not present, defaults to ""
#
# note that '-a or -d' MUST be after -n/-s flags
#

CODE="../../bin"

SIZE=20
NUM_TRIALS=10
if [ $# -ge 1 ]
then
  SIZE=$1
  NUM_TRIALS=$2
  EXTRA1=$3
  EXTRA2=$4
  EXTRA3=$5
  EXTRA4=$6
fi

BINS="./Insertion ./Qsort_2_6_11 ./Qsort_2_6_6 ./Qsort_straight"

echo "Report: $BINS on size $SIZE"
echo "Date: `date`"
echo "Host: `hostname`"

for b in $BINS
do
    TRIALS=$NUM_TRIALS
    RESULTS=/tmp/compare.$$
    rm -f $RESULTS

    # start with number of trials
    echo $NUM_TRIALS >> $RESULTS

    # followed by (one per line) totalsn
    while [ $TRIALS -ge 1 ]
    do
      $b -n $SIZE -s $TRIALS $EXTRA1 $EXTRA2 $EXTRA3 $EXTRA4 $EXTRA5 | grep secs | sed 's/secs//' >> $RESULTS
      TRIALS=$((TRIALS-1))
    done

    # compute average/stdev
    RES=`cat $RESULTS | $CODE/eval`

    echo "$b $RES"
    
    rm -f $RESULTS
done

