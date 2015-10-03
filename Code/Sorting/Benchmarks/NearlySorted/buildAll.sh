#!/bin/bash

# Script to generate all required *.rc files that govern the wide variety
# of cases we consider. Once generated, then run the 'runAll.sh' script.

# configure to use these BINS
T="BINS=../InsertionPtr ../HeapSort ../NonRecursiveQsort ../MedianMinSort* ../MedianSort ../BaseQsort ../StraightHeapSort ../BaseQSort ../QSortMin10Random ../RevisedBaseQSort ../DotBaseQsort ../BucketPtr ../MedianRecursiveSort ../QSortMin1Random ../QSortMin2Random ../QSortMin3Random ../QSortMin4Random ../QSortMin5Random ../QSortMin6Random ../QSortMin7Random ../QSortMin8Random ../QSortMin9Random ../QSortMin11Random ../QSortMin12Random ../QSortMin13Random ../QSortMin14Random ../QSortMin15Random ../QSortMin16Random ../QSortMin17Random ../QSortMin18Random ../QSortMin19Random ../QSortMin20Random "

# percentage 1/p
P=4

# distance to move
DELTA=4

# starting valu
B=256

while [ $B -le 8388608 ]
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

