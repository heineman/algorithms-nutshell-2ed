all : numTranspositions

CC = gcc
CFLAGS = -Wall -pedantic -g

numTranspositions: numTranspositions.o
	$(CC) -o $@ $+

test:
	@echo "No tests for Chapter4"

clean:
	rm -f *.o *~
	rm -f numTranspositions

