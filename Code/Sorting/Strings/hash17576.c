/**
 * @file hash17576.c      Create a Hash table to support 26^3 elements for Bucket Sort.
 * @brief
 *  Enable Bucket Sort over a hash table with 26^3 buckets.
 *
 * 
 * @author George Heineman
 * @date 6/15/08
 */

/** Number of buckets to use. */
int numBuckets(int numElements) {
  return 26*26*26;
}

/**
 * Hash function to identify bucket number from element. Customized
 * to properly encode elements in order within the buckets. 
 */
int hash(void *elt) {
  return (((char*)elt)[0] - 'a')*676 +
         (((char*)elt)[1] - 'a')*26 +
         (((char*)elt)[2] - 'a');
}
