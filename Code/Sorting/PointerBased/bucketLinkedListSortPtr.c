/**
 * @file bucketLinkedListSortPtr.c   Define core driver for Bucket Sort, pending the proper hash() and numBuckets() implementations, using linked lists.
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
#include "report.h"

#include "bucketLinkedListSortPtr.h"

/* Allocation of buckets and the number of buckets allocated */
static BUCKET *buckets = 0;

/** Number of buckets. */
static int num = 0;

/** One by one remove and overwrite ar */
void extract (BUCKET *buckets, int(*cmp)(const void *,const void *),
	      void **ar, int n) {
  int i, low;
  int idx = 0;

  for (i = 0; i < num; i++) {
    ENTRY *ptr, *tmp;
    if (buckets[i].size == 0) continue;   /* empty bucket */

    ptr = buckets[i].head;
    if (buckets[i].size == 1) {
      ar[idx++] = ptr->element;
      free (ptr);
      buckets[i].size = 0;
      continue;
    }

    /* insertion sort where elements are drawn from linked list and 
     * inserted into array. Linked lists are released. */
    low = idx;
    ar[idx++] = ptr->element;
    tmp = ptr;
    ptr = ptr->next;
    free (tmp);
    while (ptr != NULL) {
      int i = idx-1;
      while (i >= low && cmp (ar[i], ptr->element) > 0) {
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */
	ar[i+1] = ar[i];
	i--;
      }
      ar[i+1] = ptr->element;
      tmp = ptr;
      ptr = ptr->next;
      free(tmp);
      idx++;
    }
    buckets[i].size = 0;
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
    int k = hash(ar[i]);

    /** Insert each element and increment counts */
    ENTRY *e = (ENTRY *) calloc (1, sizeof (ENTRY));
    e->element = ar[i];
    if (buckets[k].head == NULL) {
      buckets[k].head = e;
    } else {
      e->next = buckets[k].head;
      buckets[k].head = e;
    }

    buckets[k].size++;
  }

  /* now read out and overwrite ar. */
  extract (buckets, cmp, ar, n);

  free (buckets);
}
