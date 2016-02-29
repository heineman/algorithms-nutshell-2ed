#!/bin/sh

# Sequential search times for n>=131,072 were too costly. We skipped 
# these; your mileage may vary

CODE=../../../../Code
BIN=$CODE/bin

NT=100
SZ=524288
P="1.0 0.5 0.0"
Z="4096 8192 16384 32768 65536 131072 262144 524288"

REPORT=table5-2.output
rm -f $REPORT

CONFIG=config5-2.rc
for z in $Z 
do 
  for p in $P
  do
    echo "# generated"                >  $CONFIG

    # Just BinaryTree for >= 131072, otherwise both
    if [ $z -ge 131072 ]
    then
      echo "BINS=$CODE/Search/binarySearchInteger"     >> $CONFIG
    else
      echo "BINS=$CODE/Search/binarySearchInteger $CODE/Search/searchInteger"     >> $CONFIG
    fi

    echo "TRIALS=$NT"                 >> $CONFIG
    echo "LOW=$SZ"                    >> $CONFIG
    echo "HIGH=$SZ"                   >> $CONFIG
    echo "INCREMENT=*2"               >> $CONFIG
    echo "EXTRAS=-p $p -z $z"         >> $CONFIG
  
    LINE=`$BIN/suiteRun.sh $CONFIG | tail -1 | sed 's/,/	/g' | awk '{print $2 "	" $3; }'`

    echo "$z	$p	$LINE"                >> $REPORT
  done
done

awk -f table5-2.awk $REPORT
rm -f $REPORT $CONFIG


