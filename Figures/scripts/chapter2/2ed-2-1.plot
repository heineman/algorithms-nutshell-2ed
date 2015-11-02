#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait 
set output '2ed-2-1.ps'

set key top left
set key box

# two graphs one on top of the other...
set multiplot

# top one first
set size 1,.5
set origin 0,.5

set xlabel "Size of input set n"
set ylabel "Execution Time (in microseconds)" 
set title "Average of 48 trials of sorting small data sets"

set xrange [ 0 : 128 ] noreverse nowriteback

set xtics 16.0

plot '2ed-figure2-1.dat' using 1:2 index 0 title "Sort-1" with lines,'2ed-figure2-1.dat' using 1:3 index 0 title "Sort-2" with lines,'2ed-figure2-1.dat' using 1:4 index 0 title "Sort-3" with lines,'2ed-figure2-1.dat' using 1:5 index 0 title "Sort-4" with lines

# now bottom one
set size 1, .5
set origin 0,0

set key top left
set key box

set xlabel "Size of input set n"
set ylabel "Execution Time (in microseconds)" 
set title "Average of 48 trials of sorting small data sets"

set xrange [ 0 : 512 ] noreverse nowriteback

set xtics 64.0

plot '2ed-figure2-1.dat' using 1:2 index 0 title "Sort-1" with lines,'2ed-figure2-1.dat' using 1:3 index 0 title "Sort-2" with lines,'2ed-figure2-1.dat' using 1:4 index 0 title "Sort-3" with lines,'2ed-figure2-1.dat' using 1:5 index 0 title "Sort-4" with lines

#    EOF
