#!/bin/bash

# Script to generate all required *.rc files that govern the wide variety
# of cases we consider. Once generated, then run the 'runAll.sh' script.

# configure to use these BINS
T="BINS=../Strings/QSortWorstCase"

# starting valu
G=3

while [ $G -le 20 ]
do
   echo "constructing $G..."
   echo $T > $G.rc
   echo "" >> $G.rc

   echo "EXTRAS=-g $G"            >> $G.rc
   echo "TRIALS=1000"             >> $G.rc
   echo "LOW=256"                 >> $G.rc
   echo "HIGH=262144"             >> $G.rc
   echo "INCREMENT=*2"            >> $G.rc

   G=$((G+1))
done

