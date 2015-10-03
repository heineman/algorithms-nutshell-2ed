#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait
set output '2ed-figure5-3.ps'

set key top right
set key box

set multiplot

set xlabel "Number of bins in hash table"
set xrange [ 224000 : 640000 ] noreverse nowriteback
set xtics 100000
set ytics
set y2tics 100
set autoscale y
set autoscale y2

# Top 
set origin 0,.5
set size 1,.5

set ylabel "average # of probes" 
set y2label "maximum # of probes" 
set title "Successful Search"
set yrange [ 0 : 4 ] noreverse nowriteback
set y2range [ 0 : 400 ] noreverse nowriteback

plot '2ed-figure5-3.dat' using 1:6 index 0 title "ave. linear" with linespoints axes x1y1,'2ed-figure5-3.dat' using 1:2 index 0 title "ave. quadratic" with linespoints axes x1y1,'2ed-figure5-3.dat' using 1:7 index 0 title "worst linear" with linespoints axes x1y2,'2ed-figure5-3.dat' using 1:3 index 0 title "worst quadratic" with linespoints axes x1y2

# Bottom
set origin 0,0
set size 1,.5

set ylabel "# of probes" 
set title "Failed Search"

plot '2ed-figure5-3.dat' using 1:8 index 0 title "ave. linear" with linespoints axes x1y1,'2ed-figure5-3.dat' using 1:4 index 0 title "ave. quadratic" with linespoints axes x1y1,'2ed-figure5-3.dat' using 1:9 index 0 title "worst linear" with linespoints axes x1y2,'2ed-figure5-3.dat' using 1:5 index 0 title "worst quadratic" with linespoints axes x1y2


#EOF
