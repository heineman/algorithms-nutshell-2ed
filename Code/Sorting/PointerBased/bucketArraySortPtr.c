/**
 * @file bucketArraySortPtr.c   Define core driver for Bucket Sort, pending the proper hash() and numBuckets() implementations, using arrays.
 * @brief 
 *   To employ Bucket Sort you only need to (a) provide a meaningful hash ()
 *   method to return an integer given an element; and (b) provide a method
 *   to compute the number of buckets to use. This code acts as the driver
 *   and relies on external code to provide the domain-specific coding.
 *
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <stdio.h>
#include "report.h"

#include "bucketArraySortPtr.h"

/** Allocation of buckets and the number of buckets allocated */
static BUCKET *buckets = 0;

/** Number of buckets. */
static int num = 0;

/**
 * Insert into bucket and extend as needed by doubling the size of the bucket
 * array storage.
 */
static void insert (BUCKET *bucket, void *elt) {
  if (bucket->size == 0) {
    bucket->size = BUCKETSIZE;
    bucket->ar = (void **) calloc (BUCKETSIZE, sizeof (void *));
  }

  /* extend by doubling the size of the array each time.*/
  if (bucket->count == bucket->size) {
    bucket->ar = (void **) realloc (bucket->ar, sizeof (void *) * (bucket->count*2));
    bucket->size *= 2;
  }

  bucket->ar[bucket->count++] = elt;
}

/** Use Insertion Sort to sort the pointers in the bucket array. */
static void insertionSortPointers (void **ar, int n, 
				   int(*cmp)(const void *,const void *)) {
  int j;
  for (j = 1; j < n; j++) {
    int i = j-1;
    void *value = ar[j];
    while (i >= 0 && cmp(ar[i], value)> 0) {
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */

      ar[i+1] = ar[i];
      i--;
    } 
   ar[i+1] = value;
  }
}


/** One by one remove and overwrite ar with proper values. */
void extract (BUCKET buckets[], int(*cmp)(const void *,const void *),
	      void **ar) {
  int i, j;
  int idx = 0;

  for (i = 0; i < num; i++) {
    if (buckets[i].count == 0) continue;   /* empty bucket */

    if (buckets[i].count == 1) {
      ar[idx++] = buckets[i].ar[0];
      buckets[i].ar[0] = 0;
      free (buckets[i].ar);
      buckets[i].ar = 0;
      continue;
    }

    /* insertion sort within each bucket and then extract */
    insertionSortPointers (buckets[i].ar, buckets[i].count, cmp);

    for (j = 0; j < buckets[i].count; j++) {
      ar[idx++] = buckets[i].ar[j];
      buckets[i].ar[j] = 0;
    }
    free (buckets[i].ar);
    buckets[i].ar = 0;
  }
}


/**
 * Invoke BucketSort on the given array. 
 */
void sortPointers (void **ar, int n, 
		   int(*cmp)(const void *,const void *)) {
  int i;

  num = numBuckets(n);
  buckets = (BUCKET *) calloc (num, sizeof (BUCKET));

  for (i = 0; i < n; i++) {
    int bucket = hash(ar[i]);
    insert (&buckets[bucket], ar[i]);
  }

  /*  now read out and overwrite */
  extract (buckets, cmp, ar);

  free (buckets);
}

/**
 * Debug routine. Output useful information.
 */
void debugBucket() {
  int i;
  int numEmpty = 0;
  int numOne = 0;
  int total = 0;
  float avg = 0.0;
  int numNonEmpty = 0;

  for (i = 0; i < num; i++) {
    if (buckets[i].count == 0) { numEmpty++; continue; }
    if (buckets[i].count == 1) { numOne++; }

    total += buckets[i].count;
    numNonEmpty++;
  }

  avg += total;
  avg /= numNonEmpty;
  printf ("%d buckets, %d empty , %d with 1, Average: %f \n",
	  num, numEmpty, numOne, avg);
}
