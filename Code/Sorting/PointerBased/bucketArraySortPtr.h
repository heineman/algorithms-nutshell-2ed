/**
 * @file bucketArraySortPtr.h   Define interface for accessing Bucket Sort
 * @brief 
 *   To employ Bucket Sort you only need to (a) provide a meaningful hash ()
 *   method to return an integer given an element; and (b) provide a method
 *   to compute the number of buckets to use.
 *
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _BUCKET_ARRAY_SORT_PTR_H_
#define _BUCKET_ARRAY_SORT_PTR_H_

/** Initial size of each bucket and delta to grow when full */
#define BUCKETSIZE 16

/**
 * Maintain count of entries in each bucket and array of entries. That is,
 * Each bucket will contain an array of elements
 */
typedef struct {
  /** How many actual elements are in the bucket. */
  int               count;

  /** The size of the bucket, which can expand as more as inserted. */
  int               size; 

  /** Memory to store the bucket of elements. */
  void              **ar;
} BUCKET;

/** Determine the means by which elements are converted to bucket indices. */
extern int hash(void *elt);

/**
 * The number of buckets to use given the number of elements.
 * \param numElements   number of elements being sorted.
 */
extern int numBuckets(int numElements);

#endif  /* _BUCKET_ARRAY_SORT_PTR_H_ */
