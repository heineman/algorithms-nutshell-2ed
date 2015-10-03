#!/bin/bash

for i in raefsky*/*.dat
do
  echo "$i" >> sparseTest.Bench
  ./testGraph -f $i >> sparseTest.Bench

  echo "$i" >> sparseTestDense.Bench
  ./testDense -f $i >> sparseTestDense.Bench
done

