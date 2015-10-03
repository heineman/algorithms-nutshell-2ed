# Responsible for subdirectories.
SUBDIRS = BinaryHeap \
	ZeroKnowledge \
	DepthFirstSearch \
	BreadthFirstSearch \
	MinimumSpanningTree \
	AllPairsShortestPath \
	SingleSourceShortestPath

CC = g++

## reporting stats with REPORT defined. For debugging, use "-g -DREPORT"
DEBUG=-O3
#DEBUG=-g

CFLAGS  = -Wall -pedantic $(DEBUG)

# default rule to build C files
%.o: %.cxx
	$(CC) -c $(CFLAGS) $(INCLUDE) $*.cxx


APPS = testFS fsInspector 

TESTS = testGraph 

all : $(APPS) $(TESTS)
	for i in $(SUBDIRS); do (cd "$${i}"; $(MAKE);  if [ $$? -ne 0 ]; then exit -1; fi;) done


# run all local tests and any in subdirectories
test: $(TESTS)
	for i in $(TESTS); do ./$${i}; if [ $$? -ne 0 ]; then exit -1; fi; done
	for i in $(SUBDIRS); do (cd "$${i}"; $(MAKE) test; if [ $$? -ne 0 ]; then exit -1; fi;) done

fsInspector: fsInspector.o
	$(CC) $(CFLAGS) -o $@ $+

graphCode.a: Graph.o Graph.h
	ar rcv graphCode.a Graph.o

testGraph: testGraph.o
	$(CC) -o $@ $+ graphCode.a	

testFS: testFS.o graphCode.a
	$(CC) $(CFLAGS) $(INCLUDE) -o $@ $+

clean:
	rm -f *~ core
	rm -f *.o graphCode.a
	rm -f $(TESTS) $(APPS)
	for i in $(SUBDIRS); do (cd "$${i}"; $(MAKE) clean;) done
