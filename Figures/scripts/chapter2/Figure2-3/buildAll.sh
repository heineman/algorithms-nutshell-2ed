#!/bin/bash

# Run this script first to generate the variety of *.rc files that are 
# needed. Then execute 'runAll.sh' to see results.

# configure to use these BINS
T="BINS=ValueBased/Qsort_straight ValueBased/Qsort_2_6_6 ValueBased/Qsort_2_6_11 ValueBased/Insertion "

# percentage 1/p
P=4

# distance to move
DELTA=4

# starting value
B=1

while [ $B -le 16384 ]
do
   echo "constructing $B..."
   echo $T > $B.rc
   echo "" >> $B.rc
   T4=$(($B / $P))
   echo "EXTRAS=-a -u $T4,$DELTA" >> $B.rc
   echo "TRIALS=10"               >> $B.rc
   echo "LOW=$B"                  >> $B.rc
   echo "HIGH=$B"                 >> $B.rc
   echo "INCREMENT=*2"            >> $B.rc

   B=$((B*2))
done

