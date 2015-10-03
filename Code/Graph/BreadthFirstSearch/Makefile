INCLUDE = -I../BinaryHeap -I..

CC = g++

DEBUG=-O3
#DEBUG = -g

CFLAGS = $(DEBUG) -Wall -pedantic

all: counter_bfs.o bfs.o

test: $(TESTS)
	for i in $(TESTS); do ./$${i}; if [ $$? -ne 0 ]; then exit -1; fi; done

GRAPH = ../graphCode.a 

# default rule to build C files
%.o: %.cxx
	$(CC) -c $(CFLAGS) $(INCLUDE) $*.cxx

clean:
	rm -f *~ core
	rm -f *.o
