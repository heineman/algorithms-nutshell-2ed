#!/bin/bash

for i in raefsky*/*.dat
do
  for j in `echo "1 2 3 4 5 6 7 8 9 10"`
  do
    echo "PQ" >> sparse.Bench 
    echo "$i" >> sparse.Bench 
    ./testGraph -f $i >> sparse.Bench 

    echo "DG" >> sparse.Bench
    echo "$i" >> sparse.Bench
    ./testDense -f $i >> sparse.Bench

    echo "DG-opt" >> sparse.Bench 
    echo "$i" >> sparse.Bench 
    ./testGraph -d -f $i >> sparse.Bench 
  done
done

