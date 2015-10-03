#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait 
set output '2-4.ps'

set ylabel "Execution Time (in ms)" 
set xlabel "n = number of digits"
set title "Multiplication performance"

set key top left 
set key box

set size 1,0.5

set xrange [ 0 : 512 ] noreverse nowriteback

set xtics 100.0

plot 'table2-4.dat' using 1:3 index 0 title "mult" with lines,'table2-4.dat' using 1:4 index 0 title "times" with lines

#    EOF
