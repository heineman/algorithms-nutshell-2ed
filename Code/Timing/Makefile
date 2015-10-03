all: timing.o report.o benchmark.o

# should we count each comparison between two elements? 
# report will include these results. Also takes care of
# comparing against NULL
COUNTCOMPARE = -DCOUNT

CC = gcc

DEBUG = -O3
#DEBUG = -g

CFLAGS = -Wall -pedantic $(COUNTCOMPARE) $(DEBUG)

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

benchmark: benchmark.o timing.o report.o
	$(CC) -o $@ $+ timing.o report.o

test:
	@echo "No tests for Timing"

clean:
	rm -f *~ report.o timing.o benchmark.o
	rm -f a.out benchmark
