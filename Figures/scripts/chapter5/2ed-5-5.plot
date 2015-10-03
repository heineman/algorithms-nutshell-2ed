#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait
set output '2ed-figure5-5.ps'

set key top right
set key box

set size 1,0.5

set xlabel "Size of m bits in Bloom Filter"
set ylabel "False Positive Rate"
set title "False Positive Rate with changing m"

set xrange [ 100000 : 2100000 ] noreverse nowriteback
set yrange [ 0 : 1 ] noreverse nowriteback
set format x "%.0f"

set xtics 500000

plot '2ed-figure5-5.dat' using 1:2 index 0 title "words FP rate" with points,'2ed-figure5-5.dat' using 1:3 index 0 title "random FP rate" with points, '2ed-figure5-5.dat' using 1:4 index 0 title "calculated FP rate" with linespoints

#EOF

