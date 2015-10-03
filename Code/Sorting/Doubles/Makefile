# should we count each comparison between two elements? 
# report will include these results. We no longer use these
# with timing, so these are removes.
#COUNTCOMPARE = -DCOUNT

# timing contains report.h and other necessary filesn
INC = -I../../Timing

# standard settings. Turn off debugging for timing comparisons
CC = gcc
DEBUG=-O3
#DEBUG=-g
CFLAGS = -Wall -pedantic $(DEBUG) $(COUNTCOMPARE)

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

COMMON = ../buildDoubleBasedInput.o

# all algorithm source code is found here
PTR = ../PointerBased

# timing routines found here
TIMING = ../../Timing/timing.o \
	 ../../Timing/report.o

QuickSortVariations = Qsort \
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
	QSortWorstCaseThree \
	QSortWorstCaseFour \
	QSortWorstCaseFive


InsertionSort = InsertionPtr

IntroSort = IntroSort0 IntroSort16

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

BucketSort = BucketArraySortPtr \
	BucketLinkedListSortPtr

all: $(QuickSortVariations) $(InsertionSort) $(SelectionSort) $(BubbleSort) $(HeapSortVariations) $(MedianSortVariations) $(BucketSort) $(IntroSort)


# QuickSort Variations
Qsort: $(PTR)/Linux-2.6.11-rc5-lib-qsort.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

# non recursive quicksort
NonRecursiveQsort: $(PTR)/NonRecursiveQsort.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

BaseQsort: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize0.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

IntroSort0: $(PTR)/introSort.o $(PTR)/pivotMedianOfThree.o $(PTR)/minSize0.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

IntroSort16: $(PTR)/introSort.o $(PTR)/pivotMedianOfThree.o $(PTR)/minSize16.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

BaseQsortMedianOfThree: $(PTR)/baseQsort.o $(PTR)/pivotMedianOfThree.o $(PTR)/minSize0.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

RevisedBaseQsort: $(PTR)/revisedPartition_baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize0.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianSortMedianOfThree: $(PTR)/medianSort.o $(PTR)/pivotMedianOfThree.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianSortLeftPivot: $(PTR)/medianSort.o $(PTR)/pivotFirst.o $(PTR)/selectKth.o 
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

# special compilation handling....
MedianSortWorstCaseFive: $(PTR)/selectKthWorstLinearFive.o
	gcc $(INC) -c $(FLAGS) -DMEDIAN $(PTR)/medianSort.c -o medianSortFive.o
	gcc -o $@ $+ medianSortFive.o $(COMMON) $(TIMING) -lm
	rm medianSortFive.o

MedianSortWorstCaseFour: $(PTR)/selectKthWorstLinearFour.o
	gcc $(INC) -c $(FLAGS) -DMEDIAN $(PTR)/medianSort.c -o medianSortFour.o
	gcc -o $@ $+ medianSortFour.o $(COMMON) $(TIMING) -lm
	rm medianSortFour.o

MedianSortWorstCaseThree: $(PTR)/selectKthWorstLinearThree.o
	gcc $(INC) -c $(FLAGS) -DMEDIAN $(PTR)/medianSort.c -o medianSortThree.o
	gcc -o $@ $+ medianSortThree.o $(COMMON) $(TIMING) -lm
	rm medianSortThree.o

MedianMinSort10: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize10.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort20: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize20.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort30: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize30.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianSort: $(PTR)/medianSort.o $(PTR)/pivotRandom.o $(PTR)/minSize0.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianRecursiveSort: $(PTR)/medianSort.o $(PTR)/pivotRandom.o $(PTR)/selectKth.o $(PTR)/minSize0.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin10Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize10.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

HeapSort: $(PTR)/heapSort.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

StraightHeapSort: $(PTR)/straight_HeapSort.o 
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

BubblePtr: $(PTR)/bubblePtr.o 
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

InsertionPtr: $(PTR)/insertionPtr.o 
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

BucketLinkedListSortPtr: $(PTR)/bucketLinkedListSortPtr.o hash.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

BucketArraySortPtr: $(PTR)/bucketArraySortPtr.o hash.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

SelectionSort: $(PTR)/selectionSort.o 
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort1: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize1.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort2: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize2.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort3: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize3.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort4: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize4.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort5: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize5.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort6: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize6.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort7: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize7.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort8: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize8.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

MedianMinSort9: $(PTR)/medianMinSort.o $(PTR)/pivotRandom.o $(PTR)/minSize9.o $(PTR)/selectKth.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

# select 4 as the base case
InvertedQsort: $(PTR)/invertedInsertionQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize4.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin20Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize20.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin19Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize19.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin18Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize18.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin17Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize17.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin16Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize16.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin15Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize15.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin14Random:$(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize14.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin13Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize13.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin12Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize12.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin11Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize11.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin9Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize9.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin8Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize8.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin7Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize7.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin6Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize6.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin5Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize5.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin4Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize4.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin3Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize3.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin2Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize2.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortMin1Random: $(PTR)/baseQsort.o $(PTR)/pivotRandom.o $(PTR)/minSize1.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

# special compilation handling....
QSortWorstCaseThree: $(PTR)/baseQsort.o $(PTR)/pivotMedianOfMedians.o $(PTR)/selectKthWorstLinearThree.o $(PTR)/minSize3.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortWorstCaseFour: $(PTR)/baseQsort.o $(PTR)/pivotMedianOfMedians.o $(PTR)/selectKthWorstLinearFour.o $(PTR)/minSize4.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm

QSortWorstCaseFive: $(PTR)/baseQsort.o $(PTR)/pivotMedianOfMedians.o $(PTR)/selectKthWorstLinearFive.o $(PTR)/minSize5.o
	gcc -o $@ $+ $(COMMON) $(TIMING) -lm


test:
	@echo "No tests for Doubles"

clean:
	rm -f *~
	rm -f hash.o minSize0.o pivotRandom.o
	rm -f core
	rm -f $(QuickSortVariations)
	rm -f $(InsertionSort)
	rm -f $(BubbleSort)
	rm -f $(SelectionSort)
	rm -f $(HeapSortVariations)
	rm -f $(MedianSortVariations)
	rm -f $(BucketSort)
	rm -f $(IntroSort)
