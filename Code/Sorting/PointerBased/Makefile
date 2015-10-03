# should we count each comparison between two elements? 
# report will include these results. Turning off compare counts
# for best
#COUNTCOMPARE = -DCOUNT

# timing contains report.h and other necessary filesn
INC = -I../../Timing

# standard settings (turn of debugging and optimize instead)
CC = gcc

DEBUG=-O3
#DEBUG = -g

CFLAGS = -Wall -pedantic $(DEBUG) $(COUNTCOMPARE)

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

QSortCode= Linux-2.6.11-rc5-lib-qsort.o \
	NonRecursiveQsort.o \
	baseQsort.o \
	stripped_baseQsort.o \
	parallelQsort.o \
	invertedInsertionQsort.o \
	revisedPartition_baseQsort.o

MinSizeCode= minSize0.o \
	minSize1.o \
	minSize2.o \
	minSize3.o \
	minSize4.o \
	minSize5.o \
	minSize6.o \
	minSize7.o \
	minSize8.o \
	minSize9.o \
	minSize10.o \
	minSize11.o \
	minSize12.o \
	minSize13.o \
	minSize14.o \
	minSize15.o \
	minSize16.o \
	minSize17.o \
	minSize18.o \
	minSize19.o \
	minSize20.o \
	minSize30.o

PivotCode = pivotRandom.o \
	pivotMedianOfThree.o \
	pivotMedianOfMedians.o \
	pivotFirst.o \
	pivotLast.o

IntroSortCode = introSort.o

SelectCode = selectKth.o \
	selectKthWorstLinear.o \
	selectKthWorstLinearThree.o \
	selectKthWorstLinearFour.o \
	selectKthWorstLinearFive.o \
	selectKthRecursive.o

MergeCode = merge.o

HeapCode = heapSort.o \
	straight_HeapSort.o

InsertionCode = insertionPtr.o binaryInsertionPtr.o

BubbleCode = bubblePtr.o

BucketCode = bucketArraySortPtr.o \
	bucketLinkedListSortPtr.o

SelectionCode = selectionSort.o

MedianCode = medianSort.o \
	medianMinSort.o

CODE= $(InsertionCode) $(QSortCode) $(MinSizeCode) $(PivotCode) \
	$(SelectCode) $(HeapCode) $(BubbleCode) $(BucketCode) \
	$(SelectionCode) $(MedianCode) $(IntroSortCode) $(MergeCode)

all: $(CODE)

test:
	@echo "No tests for Sorting"

clean:
	rm -f $(CODE:.c=.o)
	rm -f *~



