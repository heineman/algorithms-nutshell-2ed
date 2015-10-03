# should we count each comparison between two elements? 
# report will include these results. Turning off compare counts
# for best
#COUNTCOMPARE = -DCOUNT

# timing contains report.h and other necessary filesn
INC = -I../../Timing

# standard settings (turn of debugging and optimize instead)
CC = gcc
#DEBUG=-O3
DEBUG = -g
CFLAGS = -Wall -pedantic $(DEBUG) $(COUNTCOMPARE)

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

TIMING = ../../Timing/timing.o \
	 ../../Timing/report.o

COMMON = ../buildValueBasedInput.o

InsertionVariations = Insertion InsertionAllCopy

MergeSort = Mergesort

QuicksortVariations = Qsort_straight Qsort_2_6_6 Qsort_2_6_11 Qsort_modified

CODE = insertion.o insertion_all_copy.o straight-qsort.o \
	Linux-2.6.6-rc2-fs-xfs-support-qsort.o \
	Linux-2.6.11-rc5-lib-qsort.o \
	modifiedQsort.o merge.o

all:  $(QuicksortVariations) $(InsertionVariations) $(MergeSort)


Insertion: insertion.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

InsertionAllCopy: insertion_all_copy.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

Qsort_straight: straight-qsort.o 
	gcc -o $@ $+ $(COMMON) $(TIMING)

Qsort_modified: modifiedQsort.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

Qsort_2_6_6: Linux-2.6.6-rc2-fs-xfs-support-qsort.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

Qsort_2_6_11: Linux-2.6.11-rc5-lib-qsort.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

Mergesort: merge.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

test:
	@echo "No tests for ValueBased"

clean:
	rm -f *~
	rm -f $(CODE)
	rm -f core 
	rm -f $(InsertionVariations)
	rm -f $(QuicksortVariations)
