#!/usr/bin/gnuplot -persist
set terminal postscript monochrome "TimesNewRomanPSMT" 10
set output '9-5.ps'

set key top left 
set key box

set multiplot

# first one
# --------------------
set size .5,.5
set origin 0,0

set xlabel "n = number of initial points" 
set ylabel "execution time (in milliseconds) 
set title "Performance of Convex Hull variations on Uniform Distribution"

set xrange [ 512 : 131072 ] noreverse nowriteback

plot 'table9-5.dat' using 1:2 index 1 title "Andrew" with linespoints,'table9-5.dat' using 1:3 index 1 title "Heap" with linespoints,'table9-5.dat' using 1:4 index 1 title "Balanced" with linespoints,'table9-5.dat' using 1:5 index 1 title "Bucket" with linespoints,'table9-5.dat' using 1:6 index 1 title "Sorting Only" with linespoints

# second one
# --------------------
set size .5,.5
set origin .5,0

set xlabel "n = number of initial points" 
set ylabel "execution time (in milliseconds) 
set title "Performance of Convex Hull variations on Circle Distribution"

plot 'table9-5.dat' using 1:2 index 2 title "Andrew" with linespoints,'table9-5.dat' using 1:3 index 2 title "Heap" with linespoints,'table9-5.dat' using 1:4 index 2 title "Balanced" with linespoints,'table9-5.dat' using 1:5 index 2 title "Bucket" with linespoints,'table9-5.dat' using 1:6 index 2 title "Sorting Only" with linespoints

# third one
# --------------------
set size .5,.5
set origin 0,.5

set xlabel "n = number of initial points" 
set ylabel "execution time (in milliseconds) 
set title "Performance of Convex Hull variations on Slice Distribution"

set xrange [ 512 : 2048 ] noreverse nowriteback

plot 'table9-5.dat' using 1:2 index 0 title "Andrew" with linespoints,'table9-5.dat' using 1:3 index 0 title "Heap" with linespoints,'table9-5.dat' using 1:4 index 0 title "Balanced" with linespoints,'table9-5.dat' using 1:5 index 0 title "Bucket" with linespoints,'table9-5.dat' using 1:6 index 0 title "Sorting Only" with linespoints


#EOF

