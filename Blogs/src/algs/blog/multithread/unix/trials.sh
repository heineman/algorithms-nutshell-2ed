#!/bin/bash

# Point to existing ADK installation. FIX ME!
ADK_ROOT=../../../../../..

T=1
while [ $T -lt 50 ]
do
  # construct config for testing
  cp baseinfo trial.rc
  echo "EXTRAS=-g $T" >> trial.rc

  echo "T = $T"
  $ADK_ROOT/Code/bin/suiteRun.sh trial.rc

  T=$((T+1))
done

# now try one more time with T=MAXINT to get never value.
T=2147483647
cp baseinfo trial.rc
echo "EXTRAS=-g $T" >> trial.rc
echo "T = $T"
$ADK_ROOT/Code/bin/suiteRun.sh trial.rc
