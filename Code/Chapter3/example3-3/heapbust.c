/**
 * @file heapbust.c    Task to show code that crashes a C program.
 * @brief 
 *    Provide code that exhausts all available memory and crashes. Make
 *    sure that you don't execute this program on a shared machine since
 *    it might force that machine to be rebooted.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/** Compute the number of times the program executes. When loading up core file after the crash, this will store the number of times program loop executed. */
int n;

/** Return string "abcdef" as "fabcde". */
char *cycle (char *s) {
  int n = strlen(s);
  char *u = malloc (n+1);
  strncpy (u, s+1, n-1);
  u[n-1] = s[0];
  u[n] = '\0';
  return u;
}

/** launch this program which may crash your machine. */
int main (int argc, char **argv) {
  char *s = strdup ("ThisStringHas25Characters");

  for (;;) {
    s = cycle(s);
    n++;
  }
}
