#!/bin/sh

CODE=../../../../Code
BIN=$CODE/bin

Z=1000
NT=100
LOW=4096
HIGH=131072
P="1.0 0.5 0.25 0.0"

# intermediate output
REPORT=table5-1.output
rm -f $REPORT

for i in $P
do
  echo "# generated"                       >  config.rc
  echo "BINS=$CODE/Search/searchInteger"   >> config.rc
  echo "TRIALS=$NT"                        >> config.rc
  echo "LOW=$LOW"                          >> config.rc
  echo "HIGH=$HIGH"                        >> config.rc
  echo "INCREMENT=*2"                      >> config.rc
  echo "EXTRAS=-p $i -z $Z"                >> config.rc

  $BIN/suiteRun.sh config.rc | sed 's/,/	/g' | tail -n +3 | awk -v t=$i '{print $1 "	" t "	" $2}'        >> $REPORT
done

# convert into proper format
awk -f table5-1.awk $REPORT

# cleanup
rm -f config.rc $REPORT
