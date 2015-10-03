/**
 * @file example_3_2.c   Sample program that may CRASH your machine.
 * @brief 
 *   Infinite recursion with output to see how long program can execute
 *   before it consumes available resources.
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include <stdio.h>

/**
 * The non-terminating infinite recursion function. 
 *
 * Output the result to the stdout. BE PREPARED WHEN RUNNING THIS PROGRAM
 * THAT YOUR MACHINE MIGHT CRASH BECAUSE IT MIGHT EXHAUST ALL RESOURCES.
 *
 * \param n  how far we have recursed.
 */
int f(int n) {
  printf (" n %d[%lu] \n", n, (unsigned long) &n);
  return f(n+1);
}

/** Launch the dangerous program that may crash machine. */
int main (int argc, char **argv) {
  return f(0);
}
