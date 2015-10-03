CC = g++

DEBUG=-O3
#DEBUG=-g

CFLAGS = $(DEBUG) -Wall -pedantic -I..

TESTS = test1 test2 test3

GRAPH = ../graphCode.a 

# default rule to build C files
%.o: %.cxx
	$(CC) -c $(CFLAGS) $(INC) $*.cxx

all: $(TESTS)

test: $(TESTS)
	for i in $(TESTS); do ./$${i}; if [ $$? -ne 0 ]; then exit -1; fi; done

test1: dfs.o test1.o
	$(CC) -o $@ $+ $(GRAPH) 

test2: dfs.o test2.o
	$(CC) -o $@ $+ $(GRAPH) 

test3: dfs.o test3.o 
	$(CC) -o $@ $+ $(GRAPH) 

clean:
	rm -f *~ core
	rm -f $(TESTS)
	rm -f *.o
