/**
 * @file Memory/sample.c    Program to show addresses in memory
 * @brief 
 *    Small program to show internal memory addresses.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>

int f(char *newArray) {
  char temp[132];
  int i;

  for (i=0; i<132; i++) {
    newArray[i] = temp[i];
  }

  printf ("-----\n");
  printf (" newArray [%u] i [%u] j[%u]\n", &newArray, &temp, &i);
}

int main (int argc, char **argv) {
  char *newArray  = (char *) malloc(132);
  int i = 17, j;

  f (newArray);

  printf ("-----\n");
  printf (" argc [%u] argv [%u]\n", &argc, &argv);
  printf (" newArray [%u] i [%u] j[%u]\n", newArray, &i, &j);

}
