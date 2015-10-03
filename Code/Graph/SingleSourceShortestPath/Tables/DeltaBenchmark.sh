#!/bin/bash

for i in bench*.dat
do
  for j in `echo "1 2 3 4 5 6 7 8 9 10"`
  do
    echo "$i" >> testRawDenseGraph.Bench
    ./testGraph -d -f $i >> testRawDenseGraph.Bench
  done
done

