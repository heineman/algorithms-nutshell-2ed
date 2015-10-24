#!/bin/bash
#
# This script provides the core engine for executing repeated trials of
# the timing code. With no arguments, it looks for the file "config.rc"
# in the current directory; with an argument, it loads up the information
# from that file.
#
# Key configuration requests include:
#
#   BINS        what binaries to execute
#   TRIALS      how many trials to run
#   LOW         what is the low value for n
#   HIGH        what is the high value for n
#   INCREMENT   how does n increase from LOW to HIGH (typically + or *)
#

CODE=`dirname $0`

# if no args then use default config file, otherwise expect it
if [ $# -eq 0 ]
then
  CONFIG="config.rc"
else
  CONFIG=$1
  echo "Using configuration file $CONFIG..."
fi

# export so it will be picked up by compare.sh
export CONFIG

# pull out information
if [ -f $CONFIG ]
then
   BINS=`grep "BINS=" $CONFIG | cut -f2- -d'='`
   TRIALS=`grep "TRIALS=" $CONFIG | cut -f2- -d'='`
   LOW=`grep "LOW=" $CONFIG | cut -f2- -d'='`
   HIGH=`grep "HIGH=" $CONFIG | cut -f2- -d'='`
   INCREMENT=`grep "INCREMENT=" $CONFIG | cut -f2- -d'='`
else
  echo "Configuration file ($CONFIG) unable to be found."
  exit -1
fi 

# headers
HB=`echo $BINS | tr ' ' ','`
echo "n,$HB"

# compare trials on sizes from LOW through HIGH
SIZE=$LOW
REPORT=/tmp/Report.$$
while [ $SIZE -le $HIGH ]
do
  # one per $BINS entry
  $CODE/compare.sh $SIZE $TRIALS | awk 'BEGIN{p=0} \
      {if(p) { print $0; }} \
      /Host:/{p=1}' | cut -d' ' -f2 > $REPORT

  # concatenate with , all entries ONLY the average. The stdev is 
  # going to be ignored
  # ------------------------------------------------------------
  VALS=`awk 'BEGIN{s=""}\
      {s = s "," $0 }\
      END{print s;}' $REPORT`
  rm -f $REPORT

  echo $SIZE $VALS

  # $INCREMENT can be "+ NUM" or "* NUM", it works in both cases.
  SIZE=$(($SIZE$INCREMENT))
done

