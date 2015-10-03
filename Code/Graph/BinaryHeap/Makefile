TESTS= test1 test2

all: $(TESTS)

CC = g++

DEBUG=-O3
#DEBUG = -g

CFLAGS = $(DEBUG) -Wall -pedantic

# default rule to build C files
%.o: %.cxx
	$(CC) -c $(CFLAGS) $(INCLUDE) $*.cxx

test: $(TESTS)
	for i in $(TESTS); do ./$${i}; if [ $$? -ne 0 ]; then exit -1; fi; done

test1: test1.o BinaryHeap.o
	$(CC) -o $@ $+ 

test2: test2.o BinaryHeap.o 
	$(CC) -o $@ $+ 

clean:
	rm -f $(TESTS) *.o
	rm -f *~ core
