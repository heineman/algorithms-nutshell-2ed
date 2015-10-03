APPS = process process_tsp tsp_approx

TESTS = smallTest testCormen

all: $(TESTS) $(APPS)

test: $(TESTS)
	for i in $(TESTS); do ./$${i}; if [ $$? -ne 0 ]; then exit -1; fi; done

CC = g++

INCLUDE=-I../BinaryHeap -I.. -I../../Timing

TIMING = ../../Timing/report.o

## for debugging, use "-g -DREPORT"
DEBUG=-g -DREPORT
#DEBUG=-O3

CFLAGS= $(DEBUG) -Wall -pedantic 

GRAPH = ../graphCode.a 
BHEAP = ../BinaryHeap/BinaryHeap.o

%.o: %.c
	$(CC) -c $(CFLAGS) $(INCLUDE) $<

%.o: %.cxx
	$(CC) -c $(CFLAGS) $(INCLUDE) $<

process: process.o mst.o msttsp.o
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) $(TIMING)

tsp_approx: approx.o msttsp.o mst.o
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) $(TIMING)

process_tsp: process.o msttsp.o mst.o
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) $(TIMING)

testCormen: testCormen.o $(GRAPH) mst.o msttsp.o
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) 

smallTest: smallTest.o $(GRAPH) mst.o msttsp.o
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) 

clean:
	rm -f *~ core
	rm -f *.o
	rm -f $(TESTS) $(APPS)
