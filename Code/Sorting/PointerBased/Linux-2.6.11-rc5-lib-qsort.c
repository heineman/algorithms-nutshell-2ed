/* modified to be pointer-based, rather than value-based by Heineman. */

/* linux-2.6.11-rc5/lib/qsort.c */
/* Copyright (C) 1991, 1992, 1996, 1997, 1999 Free Software Foundation, Inc.
 This file is part of the GNU C Library.
 Written by Douglas C. Schmidt (schmidt@ics.uci.edu).

 The GNU C Library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 The GNU C Library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with the GNU C Library; if not, write to the Free
 Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 02111-1307 USA. */

/* If you consider tuning this algorithm, you should consult first:
 Engineering a sort function; Jon Bentley and M. Douglas McIlroy;
 Software - Practice and Experience; Vol. 23 (11), 1249-1265, 1993. */

#include "report.h"

#include <sys/types.h>

#ifndef min
#define min(x,y) (x) < (y) ? (x):(y)
#endif /* min */


/* MODULE_LICENSE("GPL"); */

#ifndef COUNT /* TIMING */
/* Byte-wise swap two items of size SIZE. */
#define SWAP(p, a, b) \
do { \
 void *tmp = p[a];\
 p[a] = p[b]; \
 p[b] = tmp; \
} while (0)
#endif /* COUNT */

#ifdef COUNT /* TIMING */
/* Byte-wise swap two items of size SIZE. */
#define SWAP(p, a, b) \
do { \
 void *tmp = p[a];\
 p[a] = p[b]; \
 p[b] = tmp;  \
ADD_SWAP; \
} while (0)
#endif /* COUNT */


/* Discontinue quicksort algorithm when partition gets below this size.
 This particular magic number was chosen to work best on a Sun 4/260. */
#define MAX_THRESH 4

/* Stack node declarations used to store unfulfilled partition obligations. */
typedef struct
 {
 char *lo;
 char *hi;
 } stack_node;

/* The next 5 #defines implement a very fast in-line stack abstraction. */
/* The stack needs log (total_elements) entries (we could even subtract
 log(MAX_THRESH)). Since total_elements has type size_t, we get as
 upper bound for log (total_elements):
 bits per byte (CHAR_BIT) * sizeof(size_t). */
#define CHAR_BIT 8
#define STACK_SIZE (CHAR_BIT * sizeof(size_t))
#define PUSH(low, high) ((void) ((top->lo = (low)), (top->hi = (high)), ++top))
#define POP(low, high) ((void) (--top, (low = top->lo), (high = top->hi)))
#define STACK_NOT_EMPTY (stack < top)

/* Order size using quicksort. This implementation incorporates
 four optimizations discussed in Sedgewick:

 1. Non-recursive, using an explicit stack of pointer that store the
 next array partition to sort. To save time, this maximum amount
 of space required to store an array of SIZE_MAX is allocated on the
 stack. Assuming a 32-bit (64 bit) integer for size_t, this needs
 only 32 * sizeof(stack_node) == 256 bytes (for 64 bit: 1024 bytes).
 Pretty cheap, actually.

 2. Chose the pivot element using a median-of-three decision tree.
 This reduces the probability of selecting a bad pivot value and
 eliminates certain extraneous comparisons.

 3. Only quicksorts TOTAL_ELEMS / MAX_THRESH partitions, leaving
 insertion sort to order the MAX_THRESH items within each partition.
 This is a big win, since insertion sort is faster for small, mostly
 sorted array segments.

 4. The larger of the two sub-partitions is always pushed onto the
 stack first, with the algorithm then concentrating on the
 smaller partition. This *guarantees* no more than log (total_elems)
 stack size is needed (actually O(1) in this case)! */

void
sortPointers (void **pbase, size_t total_elems,
 int(*cmp)(const void *,const void *))
{
 register char **base_ptr = (char **) pbase;

 if (total_elems == 0)
 /* Avoid lossage with unsigned arithmetic below. */
 return;

 if (total_elems > MAX_THRESH)
 {
 int  lo = 0;
 int  hi = total_elems-1;
 stack_node stack[STACK_SIZE];
 stack_node *top = stack + 1;

 while (STACK_NOT_EMPTY)
 {
   int left;
   int right;

 /* Select median value from among LO, MID, and HI. Rearrange
 LO and HI so the three values are sorted. This lowers the
 probability of picking a pathological pivot value and
 skips a comparison for both the LEFT_PTR and RIGHT_PTR in
 the while loops. */

 int mid = (hi - lo) >> 1;

 if ((*cmp) ((void *) base_ptr[mid], (void *) base_ptr[lo]) < 0)
   SWAP (base_ptr, mid, lo);


 if ((*cmp) ((void *) base_ptr[hi], (void *) base_ptr[mid]) < 0)
   SWAP (base_ptr, mid, hi);
 else
 goto jump_over;
 if ((*cmp) ((void *) base_ptr[mid], (void *) base_ptr[lo]) < 0)
   SWAP (base_ptr, mid, lo);
 jump_over:;

 left = lo+1;
 right = hi-1;

 /* Here's the famous ``collapse the walls'' section of quicksort.
 Gotta like those tight inner loops! They are the main reason
 that this algorithm runs much faster than others. */
 do
 {
 while ((*cmp) ((void *) base_ptr[left], (void *) base_ptr[mid]) < 0)
   left++;

 while ((*cmp) ((void *) base_ptr[mid], (void *) base_ptr[right]) < 0)
   right--;

 if (left < right)
 {
   SWAP (base_ptr, left, right);
   if (mid == left)
     mid = right;
   else if (mid == right)
     mid = left;
   left++;
   right--;
 }
 else if (left == right)
 {
   left++;
   right--;
   break;
 }
 }
 while (left <= right);

 /* Set up pointers for next iteration. First determine whether
 left and right partitions are below the threshold size. If so,
 ignore one or both. Otherwise, push the larger partition's
 bounds on the stack and continue sorting the smaller one. */

 if ((size_t) (right - lo) <= MAX_THRESH)
 {
 if ((size_t) (hi - left) <= MAX_THRESH)
 /* Ignore both small partitions. */
 POP (lo, hi);
 else
 /* Ignore small left partition. */
 lo = left;
 }
 else if ((size_t) (hi - left) <= MAX_THRESH)
 /* Ignore small right partition. */
 hi = right;
 else if ((right - lo) > (hi - left))
 {
 /* Push larger left partition indices. */
 PUSH (lo, right);
 lo = left;
 }
 else
 {
 /* Push larger right partition indices. */
 PUSH (left, hi);
 hi = right;
 }
 }
 }

 /* Once the BASE_PTR array is partially sorted by quicksort the rest
 is completely sorted using insertion sort, since this is efficient
 for partitions below MAX_THRESH size. BASE_PTR points to the beginning
 of the array to sort, and END_PTR points at the very last element in
 the array (*not* one beyond it!). */

 {
   int end = total_elems - 1;
   int tmp = 0;
   int thresh = min(end, MAX_THRESH);
   int run = 0;

 /* Find smallest element in first threshold and place it at the
 array's beginning. This is the smallest array element,
 and the operation speeds up insertion sort's inner loop. */

   for (run = tmp + 1; run <= thresh; run++)
     if ((*cmp) ((void *) base_ptr[run], (void *) base_ptr[tmp]) < 0)
       tmp = run;

   if (tmp != 0) {
     int t2 = tmp;
     SWAP (base_ptr, t2, 0);
   }

 /* Insertion sort, running from left-hand-side up to right-hand-side. */
 /* run -n 200 -s 12345 fails. */
   run = 1;
   while (++run <= end)
     {
       tmp = run-1;
       /* guaranteed to stop since smallest element is in 0th spot from above */
       while ((*cmp) ((void *) base_ptr[run], (void *) base_ptr[tmp]) < 0)
	 if (--tmp<0) break;

       tmp++;
       if (tmp != run) 
	 {
	   int trav, hi, lo;
	   void *cpy;

	   trav = run;
	   cpy = base_ptr[trav];
	   for (hi = lo = trav; (lo -= 1) >= tmp; hi = lo) 
	     base_ptr[hi] = base_ptr[lo];
	   base_ptr[hi] = cpy;
	 }
     }
 }
}

