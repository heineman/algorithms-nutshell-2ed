#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait
set output '2ed-11-1.ps'

set key top left
set key box

set xlabel "R values"
set ylabel "performance (in milliseconds)
set title "Multithread QuickSort with rightmost pivot for different values of n and r"

plot '2ed-11-1.dat' using 1:2 index 0 title "65,536" with linespoints,'2ed-11-1.dat' using 1:2 index 1 title "131,072" with linespoints,'2ed-11-1.dat' using 1:2 index 2 title "262,144" with linespoints,'2ed-11-1.dat' using 1:2 index 3 title "525,288" with linespoints,'2ed-11-1.dat' using 1:2 index 4 title "1,048,576" with linespoints
