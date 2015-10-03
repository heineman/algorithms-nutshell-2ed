CC = gcc

INCLUDE = -I../Timing/

CFLAGS = -O3 -Wall -pedantic $(INCLUDE)

RTLIBS = -lrt

# Mac OS doesn't fully implement POSIX timing
UNAME := $(shell uname)

# Darwin is mac value
ifeq ($(UNAME), Darwin)
  RTLIBS=
endif

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $*.c

REPORT = ../Timing/report.o
TIMING = ../Timing/timing.o  

all: test tableA-6 resolution

tableA-6: tableA-6.o
	$(CC) -o $@ $+ $(RTLIBS) -lm $(REPORT)

test: forLoop.o
	$(CC) -o $@ $+ $(TIMING) $(REPORT)

resolution : tr.o
	$(CC) -o $@ $+ $(RTLIBS)

clean:
	rm -f *~ a.out *.o test sample resolution


