#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait 
set output '2ed-10-9.ps'

set key top left 
set key box

# ignore 1st one (4096) and bring in second one (131072)
# --------------------
set size 1,.5
set origin 0,0

set xlabel "Number of dimensions in input data" 
set xtics 2
set ylabel "Execution time (in milliseconds)
set title "Performance of Nearest Neighbor for 128 searches over n=131,072 points"

set xrange [ 0 : 32 ] noreverse nowriteback


plot '2ed-10-9.dat' index 1 using 1:2 title "kd-build" with lines,'2ed-10-9.dat' index 1 using 1:3 title "kd-search" with lines,'2ed-10-9.dat' index 1 using 1:4 title "Brute Force search" with linespoints


#EOF

