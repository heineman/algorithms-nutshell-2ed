CC = g++

#DEBUG=-O3
DEBUG=-g

CFLAGS = $(DEBUG) -Wall -pedantic -I..

CODE = ../../../../Code

INC = -I$(CODE)/Graph \
	-I$(CODE)/Graph/DepthFirstSearch \
	-I$(CODE)/Graph/BreadthFirstSearch

GRAPH = ../../../../Code/Graph/graphCode.a 

# default rule to build C files
%.o: %.cxx
	$(CC) -c $(CFLAGS) $(INC) $*.cxx

FIGS = figure6-8 figure6-10

all: $(FIGS)

figure6-8: figure6-8.o helper.o
	$(CC) -o $@ $+ $(GRAPH) 

figure6-10: figure6-10.o helper.o
	$(CC) -o $@ $+ $(GRAPH) 

clean:
	rm -f *~ core
	rm -f $(FIGS)
	rm -f *.o
