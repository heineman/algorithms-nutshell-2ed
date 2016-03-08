#!/usr/bin/gnuplot -persist
set terminal postscript monochrome portrait 
set output '2ed-10-12.ps'

# The vertical keyword is not present in GnuPlot 4.0 so you might
# have to remove it
set key bottom vertical at 20,-3500

set multiplot

set xrange [ 0 : 15 ] noreverse nowriteback

# must alter these values based upon the actual values in the data 
# so we can "normalize" visually across all graphs.
set yrange [ 0 : 400 ] noreverse nowriteback
set ytic 100

# first one
# --------------------
set size .5,.175
set origin 0,.825

set xlabel ""
set ylabel ""
set noxtics
set ytics nomirror
set title  "KD-tree: Ratio=.23"

plot '2ed-10-12.dat' using 1:2 index 0 title "n=4096" with lines,'2ed-10-12.dat' using 1:3 index 0 title "n=8192" with lines,'2ed-10-12.dat' using 1:4 index 0 title "n=16384" with lines,'2ed-10-12.dat' using 1:5 index 0 title "n=32768" with lines,'2ed-10-12.dat' using 1:6 index 0 title "n=65536" with lines,'2ed-10-12.dat' using 1:7 index 0 title "n=131072" with lines

# never again.
set nokey

# second one
# --------------------
set size .5,.175
set origin .5,.825

set title "Brute-Force: Ratio=.23"

plot '2ed-10-12.dat' using 1:8 index 0 title "n=4096" with lines,'2ed-10-12.dat' using 1:9 index 0 title "n=8192" with lines,'2ed-10-12.dat' using 1:10 index 0 title "n=16384" with lines,'2ed-10-12.dat' using 1:11 index 0 title "n=32768" with lines,'2ed-10-12.dat' using 1:12 index 0 title "n=65536" with lines,'2ed-10-12.dat' using 1:13 index 0 title "n=131072" with lines

# third one
# --------------------
set size .5,.175
set origin 0,.65

set xlabel ""
set title  "KD-tree: Ratio=.115"

plot '2ed-10-12.dat' using 1:2 index 1 title "n=4096" with lines,'2ed-10-12.dat' using 1:3 index 1 title "n=8192" with lines,'2ed-10-12.dat' using 1:4 index 1 title "n=16384" with lines,'2ed-10-12.dat' using 1:5 index 1 title "n=32768" with lines,'2ed-10-12.dat' using 1:6 index 1 title "n=65536" with lines,'2ed-10-12.dat' using 1:7 index 1 title "n=131072" with lines


# fourth one
# --------------------
set size .5,.175
set origin .5,.65

set xlabel ""
set title "Brute-Force: Ratio=.115"

plot '2ed-10-12.dat' using 1:8 index 1 title "n=4096" with lines,'2ed-10-12.dat' using 1:9 index 1 title "n=8192" with lines,'2ed-10-12.dat' using 1:10 index 1 title "n=16384" with lines,'2ed-10-12.dat' using 1:11 index 1 title "n=32768" with lines,'2ed-10-12.dat' using 1:12 index 1 title "n=65536" with lines,'2ed-10-12.dat' using 1:13 index 1 title "n=131072" with lines


# ----------------------------------------------------------------------

# fifth one
# --------------------
set size .5,.175
set origin 0,.475

set xlabel ""
set title  "KD-tree: Ratio=.0575"

plot '2ed-10-12.dat' using 1:2 index 2 title "n=4096" with lines,'2ed-10-12.dat' using 1:3 index 2 title "n=8192" with lines,'2ed-10-12.dat' using 1:4 index 2 title "n=16384" with lines,'2ed-10-12.dat' using 1:5 index 2 title "n=32768" with lines,'2ed-10-12.dat' using 1:6 index 2 title "n=65536" with lines,'2ed-10-12.dat' using 1:7 index 2 title "n=131072" with lines

# sixth one
# --------------------
set size .5,.175
set origin .5,.475

set xlabel ""
set title "Brute-Force: Ratio=.0575"

plot '2ed-10-12.dat' using 1:8 index 2 title "n=4096" with lines,'2ed-10-12.dat' using 1:9 index 2 title "n=8192" with lines,'2ed-10-12.dat' using 1:10 index 2 title "n=16384" with lines,'2ed-10-12.dat' using 1:11 index 2 title "n=32768" with lines,'2ed-10-12.dat' using 1:12 index 2 title "n=65536" with lines,'2ed-10-12.dat' using 1:13 index 2 title "n=131072" with lines

# ----------------------------------------------------------------------

# seventh one
# --------------------
set size .5,.175
set origin 0,.3

set xlabel ""
set title  "KD-tree: Ratio=0.02875"


plot '2ed-10-12.dat' using 1:2 index 3 title "n=4096" with lines,'2ed-10-12.dat' using 1:3 index 3 title "n=8192" with lines,'2ed-10-12.dat' using 1:4 index 3 title "n=16384" with lines,'2ed-10-12.dat' using 1:5 index 3 title "n=32768" with lines,'2ed-10-12.dat' using 1:6 index 3 title "n=65536" with lines,'2ed-10-12.dat' using 1:7 index 3 title "n=131072" with lines

# eighth one
# --------------------
set size .5,.175
set origin .5,.3

set xlabel ""
set title "Brute-Force: Ratio=0.02875"

plot '2ed-10-12.dat' using 1:8 index 3 title "n=4096" with lines,'2ed-10-12.dat' using 1:9 index 3 title "n=8192" with lines,'2ed-10-12.dat' using 1:10 index 3 title "n=16384" with lines,'2ed-10-12.dat' using 1:11 index 3 title "n=32768" with lines,'2ed-10-12.dat' using 1:12 index 3 title "n=65536" with lines,'2ed-10-12.dat' using 1:13 index 3 title "n=131072" with lines

# ninth one
# --------------------
set size .5,.21
set origin 0,.1

set xlabel "#dimensions in input data" 
set title  "KD-tree: Ratio=0.014375"

set xtics nomirror

plot '2ed-10-12.dat' using 1:2 index 4 title "n=4096" with lines,'2ed-10-12.dat' using 1:3 index 4 title "n=8192" with lines,'2ed-10-12.dat' using 1:4 index 4 title "n=16384" with lines,'2ed-10-12.dat' using 1:5 index 4 title "n=32768" with lines,'2ed-10-12.dat' using 1:6 index 4 title "n=65536" with lines,'2ed-10-12.dat' using 1:7 index 4 title "n=131072" with lines

# tenth one
# --------------------
set size .5,.21
set origin .5,.1

set xlabel "#dimensions in input data" 
set xtics nomirror
set title "Brute-Force: Ratio=0.014375"

plot '2ed-10-12.dat' using 1:8 index 4 title "n=4096" with lines,'2ed-10-12.dat' using 1:9 index 4 title "n=8192" with lines,'2ed-10-12.dat' using 1:10 index 4 title "n=16384" with lines,'2ed-10-12.dat' using 1:11 index 4 title "n=32768" with lines,'2ed-10-12.dat' using 1:12 index 4 title "n=65536" with lines,'2ed-10-12.dat' using 1:13 index 4 title "n=131072" with lines


#EOF





