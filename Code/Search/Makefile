# should we count each comparison between two elements? 
# report will include these results. Turning off compare counts
# for best
#COUNTCOMPARE = -DCOUNT

# timing contains report.h and other necessary filesn
INC = -I../Timing

# standard settings (turn of debugging and optimize instead)
CC = gcc

DEBUG=-O3
#DEBUG = -g

CFLAGS = -Wall -pedantic $(DEBUG) $(COUNTCOMPARE)
LDFLAGS = -lm

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c



APPS = search searchNull searchOrdered moveToEnd binarySearch moveToFront moveUp linkedList linkedListMoveToEnd linkedListMoveToFront searchInteger binarySearchInteger binarySearchTreeInteger binarySearchFileInteger

JAVACODE = Search.class SearchNull.class 

all: $(APPS) $(JAVACODE)

TIMING = ../Timing/timing.o \
	 ../Timing/report.o

linkedList: linkedList.o buildProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

linkedListMoveToEnd: linkedListMoveToEnd.o buildProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

linkedListMoveToFront: linkedListMoveToFront.o buildProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

searchOrdered: searchOrdered.o buildProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

binarySearch: binarySearch.o buildProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

moveUp: moveUp.o buildProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

moveToFront: moveToFront.o buildProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

moveToEnd: moveToEnd.o buildProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

binarySearchInteger: binarySearchInteger.o buildIntegerProblem.o
	$(CC) -o $@ $+ $(LDFLAGS) $(TIMING)

binarySearchTreeInteger: binarySearchTreeInteger.o buildIntegerProblem.o
	$(CC) -o $@ $+ $(LDFLAGS) $(TIMING)

binarySearchFileInteger: binarySearchFileInteger.o buildIntegerProblem.o
	$(CC) -o $@ $+ $(LDFLAGS) $(TIMING)

searchInteger: searchInteger.o buildIntegerProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

search: search.o buildProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

searchNull: searchNull.o buildProblem.o
	gcc -o $@ $+ $(LDFLAGS) $(TIMING)

Search.class : Search.java
	javac Search.java

SearchNull.class : SearchNull.java
	javac SearchNull.java

test:
	@echo "No tests for Search"

clean:
	rm -f *.o *~ core
	rm -f $(JAVACODE)
	rm -f $(APPS)
