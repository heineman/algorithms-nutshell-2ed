/**
 * @file Sorting/Doubles/hash.c    Define hash to use for Bucket Sort of doubles drawn from the range [0,1].
 * @brief
 *    Bucket Sort succeeds with uniform distribution since the hashing function
 *    can partition the input set of n values into n bins. The number of 
 *    elements in the collection (numElements) is used to define a set of
 *    'num' bins
 *
 * @author George Heineman
 * @date 6/15/08
 */

/** Computed number of bins to use for BucketSort. */
static int num;

/**
 * Number of buckets to use is the same as the number of elements. 
 * \param numElements   number of elements in the collection to be sorted.
 */
int numBuckets(int numElements) {
  num = numElements;
  return numElements;
}

/**
 * Hash function to identify bucket number from element. Customized 
 * to properly encode elements in order within the buckets.
 * 
 * When range of numbers is distributed within [0,1) we subdivide
 * into buckets of size 1/num. Thus bucket = num * (*d). Note that uniform
 * distribution will perform better than normal distribution.
 *
 * \param d     value to be sorted is uniformly drawn from [0,1).
 */
int hash(double *d) {
  int bucket = num*(*d);
  return bucket;
}
