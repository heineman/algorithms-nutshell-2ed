#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait 
set output '2ed-9-5.ps'

set key top left 
set key box

set multiplot

# first one
# --------------------
set size 1,.5
set origin 0,.5

set xlabel "n = number of initial points" 
set ylabel "execution time (in milliseconds) 
set title "Performance of Convex Hull variations on Uniform Distribution"

set xrange [ 512 : 131072 ] noreverse nowriteback

plot '2ed-9-5.dat' using 1:2 index 1 title "Andrew" with linespoints,'2ed-9-5.dat' using 1:3 index 1 title "Heap" with linespoints,'2ed-9-5.dat' using 1:4 index 1 title "Balanced" with linespoints,'2ed-9-5.dat' using 1:5 index 1 title "Bucket" with linespoints

# second one
# --------------------
set size 1,.5
set origin 0,0

set xlabel "n = number of initial points" 
set ylabel "execution time (in milliseconds) 
set title "Performance of Convex Hull variations on Circle Distribution"

plot '2ed-9-5.dat' using 1:2 index 2 title "Andrew" with linespoints,'2ed-9-5.dat' using 1:3 index 2 title "Heap" with linespoints,'2ed-9-5.dat' using 1:4 index 2 title "Balanced" with linespoints,'2ed-9-5.dat' using 1:5 index 2 title "Bucket" with linespoints

#EOF
