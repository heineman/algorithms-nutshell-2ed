/**
 * Sample Hash Function for November Blog article.
 *
 * Enable Bucket Sort over a hash table with arbitrary byte
 * values in range 0..255, creating 65,536 buckets.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

/** Number of buckets to use. */
int numBuckets(int numElements) {
  return 256*256;
}

/**
 * Hash function to identify bucket number using only first
 * two characters of the given string.
 */
int hash(char *elt) {
  return ((unsigned char)elt[0])*256 +
         ((unsigned char)elt[1]);
}
