#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait 
set output '2-5.ps'

set xlabel "n = number of digits"
set ylabel "Execution Time (in ms)" 
set title "GCD performance"

set key top left 
set key box

set size 1.0,0.5

set xrange [ 0 : 128 ] noreverse nowriteback

set xtics 20.0

plot 'table2-5.dat' using 1:2 index 0 title "modgcd" with lines,'table2-5.dat' using 1:3 index 0 title "gcd" with lines

#    EOF
