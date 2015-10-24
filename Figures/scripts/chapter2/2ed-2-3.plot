#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait 
set output '2ed-2-3.ps'

set ylabel "Execution Time (in ms)" 
set title "Nearly sorted data where n/4 entries are\n randomly shifted to be 4 away from their proper position" 

set key top left
set key box

set size 1,0.5
set xrange [ 0 : 18000 ] noreverse nowriteback

set xtics 5000.00
set ytics 1.0

set xlabel "Size of input set n"

plot '2ed-figure2-3.dat' using 1:2 title "Sort-1" with linespoints,'2ed-figure2-3.dat' using 1:3 title "Sort-2" with linespoints,'2ed-figure2-3.dat' using 1:4 title "Sort-3" with linespoints,'2ed-figure2-3.dat' using 1:5 title "Sort-4" with linespoints
#    EOF
