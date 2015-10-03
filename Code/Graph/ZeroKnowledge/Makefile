INCLUDE = -I..

CC = g++

DEBUG=-O3
#DEBUG = -g

CFLAGS = $(DEBUG) -Wall -pedantic

all: sample

GRAPH = ../graphCode.a 

# default rule to build C files
%.o: %.cxx
	$(CC) -c $(CFLAGS) $(INCLUDE) $*.cxx

sample: sample.o 
	$(CC) -o $@ $+ $(GRAPH) -lm

test:
	@echo "No tests for ZeroKnowledge"

clean:
	rm -f *~ core sample
	rm -f *.o
