/**
 * @file ValueBased/modifiedQsort.c
 * @brief
 *
 *    See how well optimized Qsort compares with qsort() unix library
 *    
 *
 * @author George Heineman
 * @date 4/15/09
 */

#include <getopt.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "problem.h"
#include "report.h"

#undef VALIDATE
#ifndef COUNT
#define ADD_COMP
#define ADD_SWAP
#endif /* COUNT */

int minSize = 0;

#define swapcode(s, parmi, parmj) {  \
 /* copy sizeof(int) */  	     \
 register int *pi = (int *) (parmi); \
 register int *pj = (int *) (parmj); \
 int ct = s;                         \
 ADD_SWAP;                           \
while (ct > sizeof (int)) { \
 register int t = *pi;      \
 *pi++ = *pj;               \
 *pj++ = t;                 \
 ct -= sizeof(int);         \
}                           \
                            \
 /* Now do individual bytes */  \
 if (ct !=0) {              \
   register char *ci = (char*)pi;\
   register char *cj = (char*)pj;\
   while (ct-- > 0) {       \
     register char t = *ci; \
     *ci++ = *cj;           \
     *cj++ = t;             \
   }			    \
 }                          \
}



/**
 * In linear time, group the sub-array ar[left, right) around a pivot
 * element ar[right] by storing pivot into its proper location,
 * store, within the sub-array (whose location is returned by this
 * function) and ensuring that all ar[left,store) <= pivot and all
 * ar[store+1,right) > pivot.
 * 
 * ASSUMES THAT pivot value is already placed in ar[right] and that
 * ar[left] <= ar[mid] <= ar[right].
 * 
 * @param pbase        pointer to base of array of elements to be sorted.
 * @param n            number of elements in the array.
 * @param s            fixed size of each element.
 * @param cmp          comparator function.
 * @return             location of the pivot index properly positioned.
 */
void *partition (void *const pbase, size_t n, size_t s, 
	       int(*cmp)(const void *,const void *)) {
  char *store = ((char*)pbase+(n-1)*s);
  char *left =  ((char*)pbase+s);
  char *right = ((char*)store-s);

  do {
    while (cmp(left,store) < 0) { left += s; ADD_COMP; }
    while (cmp(store,right) < 0) { right -= s; ADD_COMP; }

    ADD_COMP;
    ADD_COMP;

    if (left < right) {
      ADD_COMP;
      swapcode(s,left,right);
      left += s;
      right -= s;
    } else if (left == right) {
      ADD_COMP;
      ADD_COMP;
      break;
    } else {
      ADD_COMP;
      ADD_COMP;
    }
  } while (left <= right);
  
  swapcode(s, left, store);
  return left;
}

/** Sort the value-based array using Insertion Sort. */
void insertion (void *base, int n, int s,
		 int(*cmp)(const void *,const void *)) {
  int j;
  void *saved = malloc (s);
  for (j = 1; j < n; j++) {
    /* start at end, work backward until smaller element or i < 0. */
    int i = j-1;
    void *value = (void*)(((char*)base) + j*s);
    while (i >= 0 && cmp((void*)((char*)base + i*s), value) > 0) { i--; } 

    /* If already in place, no movement needed. Otherwise save value to be
     * inserted and move as a LARGE block intervening values.  Then insert
     * into proper position. */
    if (++i == j) continue;

    memmove (saved, value, s);
    memmove ((void*)((char*)base+(i+1)*s), (void*)((char*)base+i*s), s*(j-i));
    memmove ((void*)((char*)base+i*s), saved, s);

#ifdef COUNT
    ADD_SWAP;
#endif 
  }
  free (saved);
}


/**
 * Select pivot index to use in partition based on median of three. 
 * Places smallest value in vals[left] and the median value in vals[right].
 * The largest of the three is actually placed in vals[mid]
 * 
 * Inline code using macro to set value
 * 
 * \param vals    the array of elements.
 * \param n       the number of elements in the array.
 * \param s       the size in bytes of each value.
 * \param cmp     the comparator function.
 * \return        int in the range [left, right] to use in partition.
 */
#define selectPivotIndex(vals,n,s,cmp)       \
{                                            \
  int mid;                                   \
  void *c0, *c1, *c2;                        \
  c0 = vals;                                 \
  mid = n/2;                                 \
  c1 = (void*)((char*)vals+mid*s);	     \
                                             \
  /* actually order these three */           \
  if (cmp(c0,c1) > 0) {   		     \
    ADD_COMP;                                \
    swapcode(s,c0,c1);                       \
  }                                          \
                                             \
  /** protect against size two arrays. */    \
  if (n > 2) {                               \
    c2 = (void*)((char*)vals+(n-1)*s);	     \
    ADD_COMP;                                \
    if (cmp(c1,c2) > 0) {                    \
      /* now we know largest is in [mid]. We must now place median in [right]. */ \
      ADD_COMP;                              \
      if (cmp(c0,c2) > 0) {                  \
        swapcode(s,c0,c2);                   \
      }                                      \
    } else {                                 \
      ADD_SWAP;                              \
      swapcode(s,c1,c2);		     \
    }                                        \
  }                                          \
}


/**
 * Sort array ar[left,right] using Quicksort method.
 */
void do_qsort (void *const pbase, size_t n, size_t s, 
	       int(*cmp)(const void *,const void *)) {
  char *pp;
  long ct, num;

  if (n <= 1) { return; }
  
  /* locate median (also placeds it in pbase[n-1]. */
  selectPivotIndex(pbase,n,s,cmp);
  pp = partition (pbase,n,s,cmp);
  
  ct = (long)pp - (long)pbase;
  num = ct / s;
  if (ct > minSize*s && ct > s) {
    do_qsort (pbase, num, s, cmp);
  } else if (ct > 1) {
    insertion (pbase, num, s, cmp);
  }

  ct = (long)pbase + (n-1)*s - (long)pp;
  num = ct / s;
  if (ct > minSize*s && ct > s) {
    do_qsort (pp+s, num, s, cmp);
  } else if (ct > 1) {
    insertion (pp+s, num, s, cmp);
  }

}

/** 1000 set of elements to be sorted (unless set). */
int **vals;
int numSets = 1000;

/** sort method (0=quickSort, 1=insertionSort). */
int sortMethod = -1;

void output (char *pbase, int n, int s) {
  int i;
  for (i = 0; i < n; i++) {
    printf ("%d. %s\n", i, pbase);
    pbase += s;
  }
}

/**
 * numElements is there
 */
void sortValues (void *const pbase, size_t total_elems, size_t size,
 int(*cmp)(const void *,const void *))
{
  do_qsort (pbase, total_elems, size, cmp);

#ifdef VALIDATE
    /* validate! */
    for (j = 0; j < numElements-1; j++) {
      if (cmp (pbase +j*size, pbase+(j+1)*size) > 0) {
	printf ("failed on trial %d\n", j);
	return;
      }
    }
#endif

  }

