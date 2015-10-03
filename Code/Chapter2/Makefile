# should we count each comparison between two elements?
# report will include these results.
#
# To generate report, type 'make FullReport'

JAVA = java
JAVAC = javac
CC = gcc
CFLAGS = -O3 -Wall -pedantic

REPORT = -I../Timing ../Timing/report.o

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $*.c

all: AdditionExample.class 

AdditionExample.class: AdditionExample.java
	$(JAVAC) $<


# need math libraries
Newton: newton.o
	$(CC) $< -lm -o $@

FullReport: AdditionExample.class
	$(CC) -O3 $(REPORT) addTest.c -o addTest
	./addTest > report.O3

	$(CC) -g $(REPORT) addTest.c -o addTest
	./addTest > report.g

	$(CC) $(REPORT) addTest.c -o addTest
	./addTest > report.none

	$(JAVA) AdditionExample > report.java

test:
	@echo "No tests for Chapter2"

clean:
	rm -f *.o *.class
	rm -f *~
	rm -f report.*
	rm -f a.out core addTest 
