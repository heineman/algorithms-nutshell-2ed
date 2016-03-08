#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait
set output '2ed-11-2.ps'

# Note: The gnuplot commands are not compatible with GnuPlot 4.0 but
#       are available on GnuPlot 4.6


## Warning: This cannot be automated since you need input from
## multiple computers. Also, the constants in the plot commands
## are drawn from the data itself.


set multiplot
set origin 0,0.5

set palette defined (0 'gray', 1 'white')
unset cbtics
set cblabel 'Speedup'
unset key

set size 1,0.5
set xlabel "R values"
set ylabel "Number Threads"
set title "Quad core speedup for varying R and NumThreads"

plot 'quad_table_nr.dat' matrix with image, '' matrix using 1:2:(sprintf('%.2f', 550.125/$3)) with labels font ',16'

set origin 0,0
set title "Dual core speedup for varying R and NumThreads"

plot 'dual_table_nr.dat' matrix with image, '' matrix using 1:2:(sprintf('%.2f', 433.50/$3)) with labels font ',16'
