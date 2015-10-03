# should we count each comparison between two elements? 
# report will include these results. We no longer use these
# with timing, so these are removes.
COUNTCOMPARE = -DCOUNT

# timing contains report.h and other necessary filesn
INC = -I../../Timing

# standard settings. Turn off debugging for timing comparisons
CC = gcc

#DEBUG = -O3
DEBUG= -g

CFLAGS = -Wall -pedantic $(DEBUG) $(COUNTCOMPARE)

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

APPS = testCountingSort heapSort quickSort testSmallArrays timeSmallArrays timeModifiedQsort

all: $(APPS)

TESTS = testCountingSort

# timing routines found here
TIMING = ../../Timing/timing.o \
	 ../../Timing/report.o

testCountingSort: countingSort.o testCountingSort.o
	$(CC) -o $@ $+

timeSmallArrays: timeSmallArrays.o $(TIMING)
	$(CC) -o $@ $+

timeModifiedQsort: modifiedQsort.o $(TIMING)
	$(CC) -o $@ $+

testSmallArrays: testSmallArrays.o $(TIMING)
	$(CC) -o $@ $+

heapSort: heapSort.o fileLoad.o
	$(CC) -o $@ $+

quickSort: quickSort.o fileLoad.o
	$(CC) -o $@ $+

test:
	for i in $(TESTS); do ./$${i}; if [ $$? -ne 0 ]; then exit -1; fi; done

clean:
	rm -f *.o
	rm -f $(APPS)
	rm -f core *~
