/**
 * @file fileLoad.c   Driver that sorts integer array loaded up from disk
 * 
 * @brief
 *    The file to be loaded is a sequence of integers, one per line. The 
 *    first line contains the number of integers to be found in the file.
 *    The remaining lines contain these integer values, one per line. Once
 *    the file is loaded into memory, it is sorted, and validated that the
 *    sort properly ordered the elements.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

/** sort n numbers in array. */
extern void sort (int *ar, int n);

/** maximum size of each integer to be read. */
#define LEN 32

/** Launch the sorting application. */
int main (int argc, char **argv) {
  int *vals;
  int i;
  char buf[LEN];
  FILE *fp = 0;
  int num;

  if (argc < 2) {
    printf ("usage: %s <file>\n", argv[0]);
    printf ("  first line of <file> contains # of integers in file, one per line.\n");
    exit (-1);
  }

  /* file name is found in argv[1]. */
  fp = fopen (argv[1], "r");
  fgets ((char*) &buf, LEN, fp);
  num = atoi(buf);

  vals = malloc (num*sizeof(int *));
  for (i = 0; i < num; i++) {
    fgets ((char*) &buf, LEN, fp);
    vals[i] = atoi (buf);
  }
  fclose(fp);

  sort (vals, num);

  for (i = 0; i < num-1; i++) {
    assert (vals[i] <= vals[i+1]);
  }
  free (vals);
  
  printf ("Integers properly sorted.\n");
  return 0;
}
