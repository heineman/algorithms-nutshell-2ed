#!/bin/bash

N=1
while [ $N -lt 32768 ]
do

  ./timeSmallArrays -n $N -q | grep -v Nil > /tmp/res.$$
  COMP_P1=`grep comparison /tmp/res.$$ | cut -f6 -d' '`
  SWAP_P1=`grep swap /tmp/res.$$ | cut -f3 -d' '`
  rm /tmp/res.$$

  ./timeModifiedQsort -n $N -q | grep -v Nil > /tmp/res.$$
  COMP_P2=`grep comparison /tmp/res.$$ | cut -f6 -d' '`
  SWAP_P2=`grep swap /tmp/res.$$ | cut -f3 -d' '`
  rm /tmp/res.$$

  ./timeSmallArrays -n $N -i | grep -v Nil > /tmp/res.$$
  COMP_I=`grep comparison /tmp/res.$$ | cut -f6 -d' '`
  SWAP_I=`grep swap /tmp/res.$$ | cut -f3 -d' '`
  rm /tmp/res.$$

  echo "$N,$COMP_P1,$SWAP_P1,$COMP_P2,$SWAP_P2,$COMP_I,$SWAP_I"
  N=$((N*2))
done
