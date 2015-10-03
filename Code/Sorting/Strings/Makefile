# should we count each comparison between two elements? 
# report will include these results. We no longer use these
# with timing, so these are removes.
#COUNTCOMPARE = -DCOUNT

# timing contains report.h and other necessary filesn
INC = -I../../Timing

# standard settings. Turn off debugging for timing comparisons
CC = gcc
#DEBUG=-O3
DEBUG=-g
CFLAGS = -Wall -pedantic $(DEBUG) $(COUNTCOMPARE)

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

COMMON = ../buildPointerBasedInput.o

# all algorithm source code is found here
PTR = ../PointerBased

# timing routines found here
TIMING = ../../Timing/timing.o \
	 ../../Timing/report.o

QuickSortVariations = Qsort \
	StrippedQsort \
	ParallelQsort \
	NonRecursiveQsort \
	BaseQsort \
	QSortMin10Random \
	InvertedQsort \
	RevisedBaseQsort \
	BaseQsortMedianOfThree \
	QSortMin1Random \
	QSortMin2Random \
	QSortMin3Random \
	QSortMin4Random \
	QSortMin5Random \
	QSortMin6Random \
	QSortMin7Random \
	QSortMin8Random \
	QSortMin9Random \
	QSortMin11Random \
	QSortMin12Random \
	QSortMin13Random \
	QSortMin14Random \
	QSortMin15Random \
	QSortMin16Random \
	QSortMin17Random \
	QSortMin18Random \
	QSortMin19Random \
	QSortMin20Random \
	QSortWorstCaseFive \
	QSortWorstCaseFour \
	QSortWorstCaseThree \
	QSortWorstCase

MergeSort = MergePtr

InsertionSort = InsertionPtr BinaryInsertionPtr

SelectionSort = SelectionSort

BubbleSort = BubblePtr

HeapSortVariations = HeapSort StraightHeapSort 

MedianSortVariations = MedianSort \
	MedianRecursiveSort \
	MedianMinSort1 \
	MedianMinSort2 \
	MedianMinSort3 \
	MedianMinSort4 \
	MedianMinSort5 \
	MedianMinSort6 \
	MedianMinSort7 \
	MedianMinSort8 \
	MedianMinSort9 \
	MedianMinSort10 \
	MedianMinSort20 \
	MedianMinSort30 \
	MedianSortWorstCaseFive \
	MedianSortWorstCaseFour \
	MedianSortWorstCaseThree \
	MedianSortLeftPivot \
	MedianSortMedianOfThree

IntroSort = IntroSort0 IntroSort16

BucketSort = BucketArraySort17576Ptr \
	BucketLinkedListSort26Ptr \
	BucketLinkedListSort676Ptr \
	BucketLinkedListSort17576Ptr

all: $(QuickSortVariations) $(InsertionSort) $(SelectionSort) $(BubbleSort) $(HeapSortVariations) $(MedianSortVariations) $(BucketSort) $(IntroSort) $(MergeSort)


# Stripped Variation for comparison when less params are passed
StrippedQsort: $(PTR)/stripped_baseQsort.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lpthread

# Parallel Variation
ParallelQsort: $(PTR)/parallelQsort.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lpthread

# QuickSort Variations
Qsort: $(PTR)/Linux-2.6.11-rc5-lib-qsort.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

# non recursive quicksort
NonRecursiveQsort: $(PTR)/NonRecursiveQsort.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

BaseQsort: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize0.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

BaseQsortMedianOfThree: $(PTR)/baseQsort.o $(PTR)/pivotMedianOfThree.o $(PTR)/minSize0.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

RevisedBaseQsort: $(PTR)/revisedPartition_baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize0.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianSortMedianOfThree: $(PTR)/medianSort.o $(PTR)/pivotMedianOfThree.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianSortLeftPivot: $(PTR)/medianSort.o $(PTR)/pivotFirst.o $(PTR)/selectKth.o 
	gcc -o $@ $+ $(COMMON) $(TIMING)

# special compilation handling....
MedianSortWorstCaseFour: $(PTR)/selectKthWorstLinearFour.o
	gcc $(INC) -c $(FLAGS) -DMEDIAN $(PTR)/medianSort.c -o medianSortFour.o
	gcc -o $@ $+ medianSortFour.o $(COMMON) $(TIMING)
	rm medianSortFour.o

MedianSortWorstCaseThree: $(PTR)/selectKthWorstLinearThree.o
	gcc $(INC) -c $(FLAGS) -DMEDIAN $(PTR)/medianSort.c -o medianSortThree.o
	gcc -o $@ $+ medianSortThree.o $(COMMON) $(TIMING)
	rm medianSortThree.o

MedianSortWorstCaseFive: $(PTR)/selectKthWorstLinearFive.o
	gcc $(INC) -c $(FLAGS) -DMEDIAN $(PTR)/medianSort.c -o medianSortFive.o
	gcc -o $@ $+ medianSortFive.o $(COMMON) $(TIMING)
	rm medianSortFive.o

MedianMinSort10: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize10.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort20: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize20.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort30: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize30.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianSort: $(PTR)/medianSort.o $(PTR)/pivotRandom.o $(PTR)/minSize0.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianRecursiveSort: $(PTR)/medianSort.o $(PTR)/pivotRandom.o $(PTR)/selectKth.o $(PTR)/minSize0.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin10Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize10.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

BucketLinkedListSort26Ptr: $(PTR)/bucketLinkedListSortPtr.o hash26.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

BucketLinkedListSort676Ptr: $(PTR)/bucketLinkedListSortPtr.o hash676.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

BucketArraySort17576Ptr: $(PTR)/bucketArraySortPtr.o hash17576.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

BucketLinkedListSort17576Ptr: $(PTR)/bucketLinkedListSortPtr.o hash17576.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

HeapSort: $(PTR)/heapSort.o 
	gcc -o $@ $+ $(COMMON) $(TIMING)

StraightHeapSort: $(PTR)/straight_HeapSort.o 
	gcc -o $@ $+ $(COMMON) $(TIMING)

BubblePtr: $(PTR)/bubblePtr.o 
	gcc -o $@ $+ $(COMMON) $(TIMING)

IntroSort0: $(PTR)/introSort.o 
	gcc -o $@ $+ $(COMMON) $(TIMING) $(PTR)/pivotMedianOfThree.o $(PTR)/minSize0.o -lm

IntroSort16: $(PTR)/introSort.o 
	gcc -o $@ $+ $(COMMON) $(TIMING) $(PTR)/pivotMedianOfThree.o $(PTR)/minSize16.o -lm

InsertionPtr: $(PTR)/insertionPtr.o 
	gcc -o $@ $+ $(COMMON) $(TIMING)

MergePtr: $(PTR)/merge.o 
	gcc -o $@ $+ $(COMMON) $(TIMING)

BinaryInsertionPtr: $(PTR)/binaryInsertionPtr.o 
	gcc -o $@ $+ $(COMMON) $(TIMING)

SelectionSort: $(PTR)/selectionSort.o 
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort1: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize1.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort2: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize2.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort3: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize3.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort4: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize4.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort5: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize5.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort6: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize6.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort7: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize7.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort8: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize8.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

MedianMinSort9: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize9.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

# select 4 as the base case
InvertedQsort: $(PTR)/invertedInsertionQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize4.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin20Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize20.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin19Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize19.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin18Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize18.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin17Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize17.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin16Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize16.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin15Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize15.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin14Random:$(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize14.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin13Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize13.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin12Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize12.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin11Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize11.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin9Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize9.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin8Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize8.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin7Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize7.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin6Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize6.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin5Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize5.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin4Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize4.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin3Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize3.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin2Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize2.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortMin1Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize1.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

# special compilation handling....
QSortWorstCase: $(PTR)/baseQsort.o $(PTR)/minSize0.o $(PTR)/pivotMedianOfMedians.o $(PTR)/selectKthWorstLinear.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortWorstCaseThree: $(PTR)/baseQsort.o $(PTR)/minSize3.o $(PTR)/pivotMedianOfMedians.o $(PTR)/selectKthWorstLinearThree.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortWorstCaseFour: $(PTR)/baseQsort.o $(PTR)/minSize4.o $(PTR)/pivotMedianOfMedians.o $(PTR)/selectKthWorstLinearFour.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

QSortWorstCaseFive: $(PTR)/baseQsort.o $(PTR)/minSize5.o $(PTR)/pivotMedianOfMedians.o $(PTR)/selectKthWorstLinearFive.o
	gcc -o $@ $+ $(COMMON) $(TIMING)

test:
	@echo "No tests for Strings"

clean:
	rm -f *~
	rm -f hash26.o hash676.o hash17576.o
	rm -f Linux-2.6.11-rc5-lib-qsort.o
	rm -f core
	rm -f $(QuickSortVariations)
	rm -f $(MergeSort)
	rm -f $(InsertionSort)
	rm -f $(BubbleSort)
	rm -f $(SelectionSort)
	rm -f $(HeapSortVariations)
	rm -f $(MedianSortVariations)
	rm -f $(BucketSort)
	rm -f $(IntroSort)
