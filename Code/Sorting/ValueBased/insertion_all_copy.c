/**
 * @file insertion_all_copy.c   Insertion Sort implementation over value-based arrays.
 * @brief 
 *   Contains Insertion Sort implementation for value-based arrays. No 
 *   special optimizations, so each transposition results in a single swap
 *   of elements.
 *
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <string.h>
#include "report.h"

/** copy value to be inserted into this spot so it won't be overwritten */
static char *scratch;


#ifdef COUNT
/* Byte-wise copy two items of size SIZE from b into a. */
#define COPY(a, b, size) \
 do \
 { \
ADD_SWAP; \
 register size_t __size = (size); \
 register char *__a = (a), *__b = (b); \
 do \
 { \
 *__a++ = *__b++; \
 } while (--__size > 0); \
 } while (0)
#endif 

#ifndef COUNT 
/* Byte-wise copy two items of size SIZE from b into a. */
#define COPY(a, b, size) \
 do \
 { \
 register size_t __size = (size); \
 register char *__a = (a), *__b = (b); \
 do \
 { \
 *__a++ = *__b++; \
 } while (--__size > 0); \
 } while (0)
#endif 


/**
 * Insertion Sort (from Wikipedia entry).
 * 
 *  insert(array a, int length, value) {
 *      int i = length - 1;
 *      while (i >= 0 && a[i] > value) {
 *         a[i + 1] = a[i];
 *         i--;
 *      }
 *         
 *      a[i + 1] := value;
 *   }
 *  
 *   insertionSort(array a, int length) {
 *      for (int i = 1; i < length; i++) insert(a, i, a[i]);
 *   }
 *
 */
void insert (char *const pbase, size_t total_elems, size_t size,
	     char *value,
	     int(*cmp)(const void *,const void *)) {

  int found = 0;

  int i = total_elems-1;
  while (i >= 0 && cmp(pbase + i*size, value) > 0) {
    if (!found) { found = 1; strncpy (scratch, value, size); value = scratch; }

    COPY (pbase+(i+1)*size, pbase+i*size, size);
    i--;
  } 

  if (!found) { return; } /* nothing needs to be done. already in place */

  COPY (pbase+(i+1)*size, scratch, size);
}

/** Sort the values using naive Insertion Sort implementation. */
void
sortValues (void *const pbase, size_t total_elems, size_t size,
	       int(*cmp)(const void *,const void *)) {
  int i;

  register char *base_ptr = (char *) pbase;
  scratch  = calloc (size+1, sizeof(char *));

  for (i = 1; i < total_elems; i++) {
    insert (base_ptr, i, size, base_ptr + i*size, cmp);
  }

  free (scratch);
}
