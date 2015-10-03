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

TESTS = FigureHeapSort DotFigure4-8 DotFigure4-9 DotBaseQsort

DotCode = dot.o \
	dot_medianSort.o \
	dot_baseQsort.o \
	figure4-9.o \
	figure4-10.o \
	figure4-heapsort.o \
	figure4-qsort.o

EXEC = DotFigure4-9 DotFigure4-8 DotBaseQsort FigureHeapSort Figure4-10 Figure4-16

FIGS = figure4-12.dotty figure4-13.dotty figure4-9.dotty figure4-8.dotty

all: $(DotCode) $(EXEC) $(FIGS)

figure4-8.dotty:
	./DotFigure4-8 > figure4-8.dotty

figure4-9.dotty:
	./DotFigure4-9 > figure4-9.dotty

figure4-12.dotty:
	./DotBaseQsort > figure4-12.dotty

figure4-13.dotty:
	./DotBaseQsort 12345 > figure4-13.dotty

FigureHeapSort: figure4-heapsort.o $(PTR)/straight_HeapSort.o 
	$(CC) -o $@ $+

DotFigure4-8: dot_medianSort.o dot.o figure4-8.o
	$(CC) -o $@ $+

DotFigure4-9: dot_medianSort.o dot.o figure4-9.o
	$(CC) -o $@ $+

Figure4-10: figure4-10.c
	$(CC) -g -o $@ $+

Figure4-16: figure4-16.c
	$(CC) -g -o $@ $+

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
