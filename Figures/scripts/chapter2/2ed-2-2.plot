#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait
set output '2ed-2-2.ps'

set key top left 
set key box


# multiple graphs per image
set multiplot

# top one first
set size 1,.5
set origin 0,.5

set xlabel "Size of input set n" 
set ylabel "Execution Time (in microseconds)" 
set title "Sorted data"
set xrange [ 0 : 18000 ] noreverse nowriteback
set yrange [ 0 : 4000 ] noreverse nowriteback

set xtics 5000.00


plot '2ed-figure2-2.dat' using 1:2 index 0 title "Sort-1" with linespoints,'2ed-figure2-2.dat' using 1:3 index 0 title "Sort-2" with linespoints,'2ed-figure2-2.dat' using 1:4 index 0 title "Sort-3" with linespoints,'2ed-figure2-2.dat' using 1:5 index 0 title "Sort-4" with linespoints

# bottom one second
set size 1, .5
set origin 0,0

set xlabel "Size of input set n" 
set ylabel "Execution Time (in microseconds)"
set title "Sorted data with 32 elements out of position" 
set xrange [ 0 : 18000 ] noreverse nowriteback
set yrange [ 0 : 4000 ] noreverse nowriteback

set xtics 5000.00


plot '2ed-figure2-2.dat' using 1:2 index 1 title "Sort-1" with linespoints,'2ed-figure2-2.dat' using 1:3 index 1 title "Sort-2" with linespoints,'2ed-figure2-2.dat' using 1:4 index 1 title "Sort-3" with linespoints,'2ed-figure2-2.dat' using 1:5 index 1 title "Sort-4" with linespoints

#    EOF
