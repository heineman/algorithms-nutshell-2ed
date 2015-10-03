#!/bin/bash

for i in bench*.large
do
  for j in `echo "1 2 3 4 5 6 7 8 9 10"`
  do
    echo "$i"  >> testDenseLarge.Bench
    ./testDense -f $i >> testDenseLarge.Bench
  done
done

