SUBDIRS= PointerBased \
	ValueBased \
	FileBased \
	Doubles \
	Longs \
	Ints \
	Strings

# should we count each comparison between two elements? 
# report will include these results. Turning off comparison checking
#COUNTCOMPARE = -DCOUNT

# compiler to use
CC = gcc

# replace -g for true debugging.
#DEBUG = -g
DEBUG = -O3
CFLAGS = -Wall -pedantic $(DEBUG) -DDICTIONARY="\"$(PWD)/Matrix.sort\"" $(COUNTCOMPARE)

INC = -I ../Timing

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

TIMING = ../../Timing/timing.o \
	 ../../Timing/report.o

BUILDS = buildValueBasedInput.o buildPointerBasedInput.o buildFileBasedInput.o buildDoubleBasedInput.o

all: $(BUILDS)
	for i in $(SUBDIRS); do (cd "$${i}"; $(MAKE);  if [ $$? -ne 0 ]; then exit -1; fi;) done

test: $(TESTS)
	for i in $(TESTS); do ./$${i}; if [ $$? -ne 0 ]; then exit -1; fi; done
	for i in $(SUBDIRS); do (cd "$${i}"; $(MAKE) test; if [ $$? -ne 0 ]; then exit -1; fi;) done

clean:
	rm -f $(BUILDS)
	rm -f *~
	rm -f core 
	for i in $(SUBDIRS); do (cd "$${i}"; $(MAKE) clean;) done

