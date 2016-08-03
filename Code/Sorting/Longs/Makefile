# should we count each comparison between two elements? 
# report will include these results. We no longer use these
# with timing, so these are removes.
#COUNTCOMPARE = -DCOUNT

# timing contains report.h and other necessary filesn
INC = -I../../Timing

# standard settings. Turn off debugging for timing comparisons
CC = gcc
#DEBUG= -g
DEBUG= -O3
CFLAGS = -Wall -pedantic $(DEBUG) $(COUNTCOMPARE)

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

PTR = ../PointerBased

COMMON = ../buildPointerBasedInput.o

TESTS = FigureHeapSort DotFigure4-7 DotFigure4-8 DotBaseQsort

DotCode = dot.o \
	dot_medianSort.o \
	dot_baseQsort.o \
	figure4-10.o \
	figure4-heapsort.o \
	figure4-qsort.o

# Figure4-6
EXEC =  DotBaseQsort FigureHeapSort Figure4-10 

FIGS = figure4-7.dotty figure4-8.dotty 

all: $(DotCode) $(EXEC) $(FIGS)


# run with no seed for base behavior
figure4-7.dotty:
	./DotBaseQsort > figure4-7.dotty

# run this time with different seed to generate different result
figure4-8.dotty:
	./DotBaseQsort 12345 > figure4-8.dotty

FigureHeapSort: figure4-heapsort.o $(PTR)/straight_HeapSort.o 
	$(CC) -o $@ $+

Figure4-10: figure4-10.c
	$(CC) -g -o $@ $+

#Figure4-6: figure4-6.c
#	$(CC) -g -o $@ $+

DotBaseQsort: dot_baseQsort.c dot.o figure4-qsort.o \
	$(PTR)/pivotRandom.o $(PTR)/minSize0.o $(PTR)/selectKth.o	
	$(CC) -o $@ $+

test:
	for i in $(TESTS); do ./$${i}; done	

clean:
	rm -f $(DotCode)
	rm -f $(EXEC)
	rm -f $(FIGS)
	rm -f core *~ *.o
