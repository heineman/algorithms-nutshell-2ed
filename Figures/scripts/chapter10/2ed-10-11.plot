#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait 
set output '2ed-10-11.ps'

set key top left 
set key box

set xlabel "d = number of dimensions" 
set ylabel "execution time (projected)
set title  "Projected performance of O(n^(1-1/d)) algorithm"

set size 1,.5

set xrange [0 : 32] noreverse nowriteback
set yrange [0 : 131072] noreverse nowriteback

set xtics 8

plot [0:32] 4096**(1-1/x) title "n=4,096" with lines, 8192**(1-1/x) title "n=8,192" with lines, 16384**(1-1/x) title "n=16,384" with lines, 32768**(1-1/x) title "n=32,768" with lines, 65536**(1-1/x) title "n=65,536" with lines, 131072**(1-1/x) title "n=131,072"


#EOF
