#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait 
set output '2ed-7-19.ps'

set key top left 
set key box

#set multiplot
#top-left one first

set size 1,.5
#set size .5,.5
#set origin 0,.5

set xlabel "d = depth bound" 
set ylabel "search tree size" 
set title "Comparing search tree size from three initial positions (N1, N2, N3)"
set xrange [ 0 : 30 ] noreverse nowriteback

set xtics 5
set ytics 5000.00

plot '2ed-7-19.dat' using 1:2 title "N1" with linespoints,'2ed-7-19.dat' using 1:3 title "N2" with linespoints,'2ed-7-19.dat' using 1:4 title "N3" with linespoints

set size .5,.5
set origin 0,0

set xlabel "d = depth bound" 
set ylabel "search tree size" 
set title "Comparing search tree size from three initial positions (N1, N2, N3)"
set xrange [ 0 : 30 ] noreverse nowriteback
set xtics 5

#trend1(x)=0.3429*x**2.6978
#trend2(x)=.2403*x**3.2554
#trend3(x)=0.2814*x**3.044

trend1(x) = m1*(x**b1)
trend2(x) = m2*(x**b2)
trend3(x) = m3*(x**b3)
fit trend1(x) '2ed-7-19.dat' using 1:2 via m1,b1;
fit trend2(x) '2ed-7-19.dat' using 1:3 via m2,b2;
fit trend3(x) '2ed-7-19.dat' using 1:4 via m3,b3;

plot trend1(x) title "trend-N1" with lines,trend2(x) title "trend-N2" with lines,trend3(x) title "trend-N3" with lines

#EOF
