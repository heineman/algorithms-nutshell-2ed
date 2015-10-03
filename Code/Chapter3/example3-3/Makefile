all: stackbust heapbust

CC = gcc

DEBUG=-g

CFLAGS = -Wall -pedantic $(DEBUG)

# default rule to build C files
.c.o: 
	$(CC) -c $(CFLAGS) $(INC) $*.c

stackbust: stackbust.o
	$(CC) $< -o $@

report: stackbust 
	echo "stackbust"
	gdb stackbust -batch -x cmd.stackbust

heapbust: heapbust.o
	$(CC) $< -o $@

test:
	@echo "No tests for Memory"

clean:
	rm -f *~ *.o
	rm -f core heapbust stackbust
