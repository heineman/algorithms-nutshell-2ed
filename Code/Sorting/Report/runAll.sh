#!/bin/bash

SUITE=../../bin/suiteRun.sh

for i in *.rc
do
  echo "run $i"
  OUT=`echo $i | cut -f1 -d'.'`

  $SUITE $i | tee -a $OUT.output
done

