#!/bin/bash

for i in bench*.large
do
  for j in `echo "1 2 3 4 5 6 7 8 9 10"`
  do
    echo "DENSE" >> testLarge.Bench
    echo "$i"  >> testLarge.Bench
    ./testGraph -d -f $i >> testLarge.Bench

    echo "REGULAR" >> testLarge.Bench
    echo "$i" >> testLarge.Bench
    ./testGraph  -f $i >> testLarge.Bench
  done
done

