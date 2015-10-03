#!/bin/bash

for i in *.tsp
do
  for j in `echo "1 2 3 4 5 6 7 8 9 10"`
  do
    echo "$i" >> tspOptimized.Bench
    ./tsp -f $i -d >> tspOptimized.Bench
  done
done

