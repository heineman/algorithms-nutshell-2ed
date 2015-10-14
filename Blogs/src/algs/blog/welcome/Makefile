# Set this to the location of ADK, which should be reached relatively in this way.
ADKHOME = ../../../../..

# Should we count comparisons and swaps?
#COUNTCOMPARE = -DCOUNT

# standard settings. To debug, change "-O3" to "-g".
# To include count checks, include -DCOUNT in DEBUG
CC = gcc
DEBUG=-O3
CFLAGS = -Wall -pedantic $(DEBUG) -I$(ADKHOME)/Code/Timing $(COUNTCOMPARE)

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

APPS = sample quicksort modified_quicksort bucketsort heapsort

all: $(APPS)

# To use the baseQSort example, define minSize, that is,
# the problem size below which we switch to InsertionSort
# for subtasks. Quicksort also demands an implementation
# of 'selectPivotIndex'. We select a random pivot
# selection implementation.
QSORT   =$(ADKHOME)/Code/Sorting/PointerBased/baseQsort.o
MINSIZE =$(ADKHOME)/Code/Sorting/PointerBased/minSize10.o
PIVOT   =$(ADKHOME)/Code/Sorting/PointerBased/pivotRandom.o
REPORT  =$(ADKHOME)/Code/Timing/report.o
BUCKET  =$(ADKHOME)/Code/Sorting/PointerBased/bucketLinkedListSortPtr.o
HEAPSORT=$(ADKHOME)/Code/Sorting/PointerBased/straight_HeapSort.o

sample: sample.o $(QSORT) $(MINSIZE) $(PIVOT)
	gcc -o $@ $+ 

bucketsort: timing.o hash.o $(BUCKET) $(REPORT) 
	gcc -o $@ $+ 

heapsort: timing.o $(HEAPSORT) $(REPORT)
	gcc -o $@ $+ 

quicksort: timing.o  $(QSORT) $(MINSIZE) $(PIVOT) $(REPORT)
	gcc -o $@ $+ 

modified_quicksort: timing.o  modified_baseQsort.o $(REPORT)
	gcc -o $@ $+ 

CONFIG = compare.rc

compare:
	@echo "# Compare sorting algorithms on different input" > $(CONFIG)
	@echo "BINS=$(ADKHOME)/Code/Sorting/Strings/QSortMin10Random $(ADKHOME)/Code/Sorting/Strings/StraightHeapSort $(ADKHOME)/Code/Sorting/Strings/BucketLinkedListSort17576Ptr" >> $(CONFIG)
	@echo "# configure trial suite" >> $(CONFIG)
	@echo "TRIALS=20"               >> $(CONFIG)
	@echo "LOW=12041"               >> $(CONFIG)
	@echo "HIGH=12041"              >> $(CONFIG)
	@echo "INCREMENT=+1"            >> $(CONFIG)
	@echo "Running 20 trials of Quicksort, Heap Sort, Bucket Sort"
	$(ADKHOME)/Code/bin/suiteRun.sh $(CONFIG)

DICTIONARY = dictionary.rc

wordCompare:
	@echo "# Compare sorting algorithms on english words" > $(DICTIONARY)
	@echo "BINS=$(ADKHOME)/Code/Sorting/Strings/QSortMin10Random $(ADKHOME)/Code/Sorting/Strings/StraightHeapSort $(ADKHOME)/Code/Sorting/Strings/BucketLinkedListSort17576Ptr" >> $(DICTIONARY)
	@echo "# configure trial suite" >> $(DICTIONARY)
	@echo "EXTRAS=-w"               >> $(DICTIONARY)
	@echo "TRIALS=20"               >> $(DICTIONARY)
	@echo "LOW=12041"               >> $(DICTIONARY)
	@echo "HIGH=12041"              >> $(DICTIONARY)
	@echo "INCREMENT=+1"            >> $(DICTIONARY)
	@echo "Running 20 trials of Quicksort, Heap Sort, Bucket Sort on english words"
	$(ADKHOME)/Code/bin/suiteRun.sh $(DICTIONARY)

dictionaryCompare:
	@echo "# Compare sorting algorithms on 156533 english words" > $(DICTIONARY)
	@echo "BINS=$(ADKHOME)/Code/Sorting/Strings/QSortMin10Random $(ADKHOME)/Code/Sorting/Strings/StraightHeapSort $(ADKHOME)/Code/Sorting/Strings/BucketLinkedListSort17576Ptr" >> $(DICTIONARY)
	@echo "# configure trial suite" >> $(DICTIONARY)
	@echo "EXTRAS=-w"               >> $(DICTIONARY)
	@echo "TRIALS=20"               >> $(DICTIONARY)
	@echo "LOW=156533"              >> $(DICTIONARY)
	@echo "HIGH=156533"             >> $(DICTIONARY)
	@echo "INCREMENT=+1"            >> $(DICTIONARY)
	@echo "Running 20 trials of Quicksort, Heap Sort, Bucket Sort on english words"
	$(ADKHOME)/Code/bin/suiteRun.sh $(DICTIONARY)

clean:
	rm -f *~ a.out core
	rm -fr *.o $(APPS) $(CONFIG) $(DICTIONARY)
