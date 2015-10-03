CC = g++

#DEBUG=-g
DEBUG=-O3

CFLAGS= $(DEBUG) -Wall -pedantic -I../BinaryHeap -I..

APPS=testGraph 

TESTS = test1 test2

all: $(APPS) $(TESTS)

test: $(TESTS)
	for i in $(TESTS); do ./$${i}; if [ $$? -ne 0 ]; then exit -1; fi; done

GRAPH = ../graphCode.a 
BHEAP = ../BinaryHeap/BinaryHeap.o

%.o: %.c
	$(CC) -c $(CFLAGS) $(INCLUDE) $<

%.o: %.cxx
	$(CC) -c $(CFLAGS) $(INCLUDE) $<

test1: allPairsShortest.o test1.o 
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) 

figure: allPairsShortest.o figure.o 
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) 

test2: test2.o allPairsShortest.o
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) 

testGraph: testGraph.o allPairsShortest.o
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) 
clean:
	rm -f *~ core
	rm -f *.o
	rm -f $(APPS) $(TESTS)
