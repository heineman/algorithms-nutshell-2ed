# Make file for Multi-threaded Quicksort (Unix)
#
# ASSUMES the following ADK_ROOT variable is set to the installed
# and compiled ADK base, as per its original instructions. You know
# that ADK_ROOT is properly set if the directory $(ADK_ROOT)/Code exists.

# Point to existing ADK installation. FIX ME!
ADK_ROOT=../../../../../..


# Bring in timing code
TIMING = $(ADK_ROOT)/Code/Timing/timing.o \
	 $(ADK_ROOT)/Code/Timing/report.o

# bring in the code to build up input sets
COMMON = $(ADK_ROOT)/Code/Sorting/buildPointerBasedInput.o

# timing contains report.h and other necessary filesn
INC = -I$(ADK_ROOT)/Code/Timing

# standard settings (turn of debugging and optimize instead)
CC = gcc

# to debug, set to -g, otherwise optimize with -O3
DEBUG=-O3

CFLAGS = -Wall -pedantic $(DEBUG) 

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

all: MultithreadQsort

MultithreadQsort: multithreadQsort.o
	gcc -o $@ multithreadQsort.o $(COMMON) $(TIMING) -lpthread

test:
	@echo "No tests for Multithread QuickSort"

clean:
	rm -f *.o
	rm -f *~
	rm -f MultithreadQsort 


