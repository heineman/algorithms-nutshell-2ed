#!/bin/bash

# compare sorts on sizes from 1 through 4096
SIZE=1
NUMT=10
MAX=16384
EXTRA1=-a
EXTRA2=-u
echo "ascending with n/4 pairs out of position by 4 spots"
while [ $SIZE -le $MAX ]
do

  DIFF=$((SIZE/4))
  EXTRA3=$DIFF,4

  REPORT=/tmp/Report.$$
  rm -f $REPORT
  ./trials.sh $SIZE $NUMT $EXTRA1 $EXTRA2 $EXTRA3 | awk 'BEGIN{p=0} \
      {if(p) { print $0; }} \
      /Host:/{p=1}' | cut -d' ' -f2 >> $REPORT

  # concatenate with , all entries
  VALS=`awk 'BEGIN{s=""}\
      {s = s "," $0 }\
      END{print s;}' $REPORT`
  rm -f $REPORT

  echo $SIZE $VALS

  SIZE=$((SIZE*2))
done

