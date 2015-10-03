#!/usr/bin/gnuplot -persist
set terminal postscript monochrome "TimesNewRomanPSMT" 10
set output '7-14.ps'

set key top left 
set key box

set multiplot
#top-left one first

set size .5,.5
set origin 0,.5

set xlabel "n = number of moves" 
set ylabel "Search tree size" 
set title "Comparing search tree size for different algorithms\nfrom random initial positions of n moves"
set xrange [ 0 : 15 ] noreverse nowriteback
set yrange [ 0 : 15000 ] noreverse nowriteback

set xtics 1.0
set ytics 1500.0

plot 'table7-4.dat' using 1:3 title "Breadth-First" with linespoints,'table7-4.dat' using 1:4 title "Depth-First(n)" with linespoints,'table7-4.dat' using 1:5 title "Depth-First(2n)" with linespoints,'table7-4.dat' using 1:2 title "A*" with linespoints

set size .5,.5
set origin 0,0

set xlabel "n = number of moves" 
set ylabel "Solution size" 
set title "Comparing quality of solutions found by different algorithms"
set xrange [ 0 : 15 ] noreverse nowriteback
set yrange [ 0 : 30 ] noreverse nowriteback

set xtics 5
set ytics 5

plot 'table7-4.dat' using 1:7 title "Breadth-First" with linespoints,'table7-4.dat' using 1:8 title "Depth-First(n)" with linespoints,'table7-4.dat' using 1:9 title "Depth-First(2n)" with linespoints,'table7-4.dat' using 1:6 title "A*Search" with linespoints

#EOF
