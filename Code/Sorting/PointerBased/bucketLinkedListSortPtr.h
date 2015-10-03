/**
 * @file bucketLinkedListSortPtr.h   Define interface for accessing Bucket Sort
 * @brief 
 *   To employ Bucket Sort you only need to (a) provide a meaningful hash ()
 *   method to return an integer given an element; and (b) provide a method
 *   to compute the number of buckets to use. This implementation stores the
 *   elements of the bucket in linked lists.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _BUCKET_LINKED_LIST_SORT_PTR_H_
#define _BUCKET_LINKED_LIST_SORT_PTR_H_

/**
 * \typedef bucketLinkedListSortPtr/ENTRY
 *
 * linked list of elements in each bucket. 
 */
typedef struct entry {
  /** The element being stored. */
  void          *element;

  /** The next element in the bucket list. */
  struct entry  *next;
} ENTRY;


/**
 * \typedef bucketLinkedListSortPtr/BUCKET
 * 
 * Maintain count of entries in each bucket and pointer to its first entry 
 */
typedef struct b {
  /** The number of buckets. */
  int        size; 

  /** Array of these bucket pointers. */
  ENTRY      *head;
} BUCKET;

/** Determine the means by which elements are converted to bucket indices. */
extern int hash(void *elt);

/**
 * The number of buckets to use given the number of elements.
 * \param numElements   number of elements being sorted.
 */
extern int numBuckets(int numElements);

#endif  /* _BUCKET_LINKED_LIST_SORT_PTR_H_ */
