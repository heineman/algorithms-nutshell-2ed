# NOT YET OPERABLE. MAY BE DELETED...
all:  Insertion

CC = gcc

# should we count each comparison between two elements? 
# report will include these results.
#COUNTCOMPARE = -DCOUNT

# timing contains report.h and other necessary filesn
INC = -I../../Timing

DEBUG = -O3
#DEBUG = -g

CFLAGS = $(DEBUG) $(COUNTCOMPARE) $(INC)

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

TIMING = ../../Timing/timing.o
REPORT = ../../Timing/report.o


COMMON = ../buildFileBasedInput.o

Insertion: insertion.o 
	$(CC) -o $@ $+ $(COMMON) $(TIMING) $(REPORT)

test:
	@echo "No tests for FileBased"

clean:
	rm -f *.o
	rm -f *~
	rm -f core Qsort_straight Qsort_2_6_6 Qsort_2_6_11
	rm -f Insertion
	rm -f default.in

