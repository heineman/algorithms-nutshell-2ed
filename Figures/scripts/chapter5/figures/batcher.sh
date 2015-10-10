#!/bin/sh

Z=1000
NT=30

LOW=131072
HIGH=131072

# for each of the 100 values between 0 and 1, perform test in random ordering
#
P="0.98 1 0.45 0.24 0.9 0.16 0.33 0.31 0.27 0.89 0.2 0.32 0.53 0.74 0.56 0.15 0.26 0.55 0.21 0.63 0.54 0.81 0.59 0.75 0.64 0.84 0.99 0.58 0.22 0.52 0.36 0.39 0.4 0.72 0.8 0.11 0.04 0.08 0.79 0.5 0.51 0.86 0.29 0.06 0.28 0 0.3 0.48 0.41 0.96 0.46 0.65 0.87 0.83 0.03 0.67 0.18 0.47 0.34 0.57 0.71 0.88 0.78 0.94 0.14 0.37 0.82 0.62 0.92 0.69 0.35 0.23 0.02 0.44 0.93 0.61 0.25 0.17 0.43 0.76 0.95 0.73 0.07 0.91 0.1 0.38 0.42 0.77 0.13 0.01 0.09 0.85 0.68 0.66 0.97 0.49 0.19 0.6 0.05 0.12 0.7"


REPORT=fractional_sequential.output
rm $REPORT

for i in $P
do
  echo "# generated"                >  config.rc
  echo "BINS=../searchInteger"      >> config.rc
  echo "TRIALS=$NT"                 >> config.rc
  echo "LOW=$LOW"                   >> config.rc
  echo "HIGH=$HIGH"                 >> config.rc
  echo "INCREMENT=*2"               >> config.rc
  echo "EXTRAS=-p $i -z $Z"         >> config.rc

  echo "$Z,$T,$i"                   >> $REPORT
  ../../bin/suiteRun.sh config.rc   >> $REPORT
done


