/**
 * @file NonRecursiveQsort.c    A Quicksort implementation without recursion.
 * @brief
 *  
 *  Lost the original link by which I found this code. You might find 
 *  similar code snippets here:
 *
 *    http://lkml.org/lkml/2005/1/24/191
 *  
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>

#include "report.h"

/** Non-recursive quicksort implementation. */
int sortPointers(void **base, int num, 
		 int (*cmp)(const void *, const void *))
{
  void *t;
  int i, p;
  int l = 0, r = num;
  struct stack {
    int l, r;
  } *stack, *top;

  /* allocate a depth of 512. Good luck if you go over. */
  stack = top = calloc(512, sizeof(struct stack));
  if (!stack)
    return -1;

  do {
    if (l+1 >= r) {
      /* empty sub-array, pop */
      l = top->l;
      r = top->r;
      --top;
    } else {
#ifdef PROBLEMSIZE
      printf ("%d\n", (r-l+1));
#endif /* PROBLEMSIZE */
      /* position the pivot element */
      for(i = l + 1, p = l; i != r; i ++)
	if (cmp(base[i], base[l]) < 0) {
	  p++;
#ifdef COUNT
  ADD_SWAP;
#endif /* COUNT */
	  t =  base[i];
	  base[i] = base[p];
	  base[p] = t;
	}

#ifdef COUNT
  ADD_SWAP;
#endif /* COUNT */
      t =  base[l];
      base[l] = base[p];
      base[p] = t;

      /* save the bigger half on the stack */
      top++;
      if (p - l < r - p) {
	top->l = p + 1;
	top->r = r;
	r = p;
      } else {
	top->l = l;
	top->r = p;
	l = p + 1;
      }
    }
  } while (top >= stack);

  free(stack);
  return 0;
}
