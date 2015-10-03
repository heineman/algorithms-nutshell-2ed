#!/bin/bash

for i in bench*.dat
do
  for j in `echo "1 2 3 4 5 6 7 8 9 10"`
  do
    echo "$i" >> testGraph.Bench
    ./testGraph -f $i >> testGraph.Bench

    echo "$i" >> testDense.Bench
    ./testDense -f $i >> testDense.Bench

    echo "$i" >> testBellmanFord.Bench
    ./testBellmanFord -f $i >> testBellmanFord.Bench
  done
done

