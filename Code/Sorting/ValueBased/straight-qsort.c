/**
 * @file straight-qsort.c   Straight Quicksort access to qsort() unix call.
 * @brief 
 *   Simply invoke the underlying Unix qsort() library method to sort the
 *   value based array.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <sys/types.h>
#include <stdlib.h>

/** Sort the value-based array using built-in qsort() method. */
void
sortValues (void *const pbase, size_t total_elems, size_t size,
 int(*cmp)(const void *,const void *))
{
  qsort (pbase, total_elems, size, cmp);
}
