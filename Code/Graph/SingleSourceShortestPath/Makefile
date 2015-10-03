CC = g++

DEBUG=-O3
#DEBUG=-g -DREPORT


CFLAGS= $(DEBUG) -Wall -pedantic 

INCLUDE=-I../BinaryHeap -I.. -I../../Timing

# need reporting code
TIMING = ../../Timing/report.o

GRAPH = ../graphCode.a 
BHEAP = ../BinaryHeap/BinaryHeap.o

%.o: %.c
	$(CC) -c $(CFLAGS) $(INCLUDE) $<

%.o: %.cxx
	$(CC) -c $(CFLAGS) $(INCLUDE) $<

APPS=tsp generateBench tspBellmanFord tspDense testDense testGraph testBellmanFord

TRIALS=2 4 8 16 32 64 128
TESTS=testSmall testFigure testCaseBellmanFord rawTest 

all: $(TESTS) $(APPS) Benchmarks

Benchmarks: 
	@mkdir Benchmarks
	(cd Benchmarks;	for i in $(TRIALS); do ../generateBench $${i} > bench-1.$${i}.dat; echo "created benchmark $${i}"; done)


test: $(TESTS)
	for i in $(TESTS); do ./$${i}; if [ $$? -ne 0 ]; then exit -1; fi; done

tsp: tsplib.o singleSourceShortest.o rawDense.o
	$(CC) $(CFLAGS) $(INCLUDE) -o $@ $+ $(BHEAP) $(GRAPH) $(TIMING)

tspBellmanFord: tsplib.o bellmanFord.o rawDense.o
	$(CC) $(CFLAGS) $(INCLUDE) -o $@ $+ $(GRAPH) $(TIMING)

tspDense: tsplib.o dense.o rawDense.o
	$(CC) $(CFLAGS) $(INCLUDE) -o $@ $+ $(BHEAP) $(GRAPH) $(TIMING)

testFigure: singleSourceShortest.o testFigure.o 
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) 

testSmall: singleSourceShortest.o test.o 
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) 

testDense: testGraph.o dense.o rawDense.o
	$(CC) -o $@ $+ $(GRAPH) $(TIMING)

generateBench: generateBench.o
	$(CC) -o $@ $+ $(GRAPH)

rawTest: rawTest.o rawDense.o
	$(CC) -o $@ $+ $(GRAPH)

testBellmanFord: testBellmanFord.o bellmanFord.o $(GRAPH)
	$(CC) -o $@ $+ $(GRAPH) $(TIMING)

testCaseBellmanFord: bellmanFord.o testCaseBellmanFord.o
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) 

testGraph: testGraph.o rawDense.o $(GRAPH) singleSourceShortest.o
	$(CC) -o $@ $+ $(BHEAP) $(GRAPH) $(TIMING)

clean:
	rm -f *~ core
	rm -f *.o
	rm -f Benchmarks/bench-*.dat
	if [ -d Benchmarks ]; then rmdir Benchmarks; fi
	rm -f $(TESTS) $(APPS)

