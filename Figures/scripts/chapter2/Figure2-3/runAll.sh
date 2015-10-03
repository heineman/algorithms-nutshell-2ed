#!/bin/bash

HERE=`pwd`
SUITE=../bin/suiteRun.sh

for i in *.rc
do
  echo "run $i"
  OUT=`echo $i | cut -f1 -d'.'`

  (cd ../../../../Code/Sorting/; $SUITE $HERE/$i) > $OUT.output
done

# now concatenate to create a single file (out.dat)
# take header from 1.output
tail -n +2 1.output | head -1 | sed 's/,/	/g' > out.dat


for i in `ls *.output | sort -n`
do
  tail -1 $i | sed 's/,/	/g' >> out.dat
done





