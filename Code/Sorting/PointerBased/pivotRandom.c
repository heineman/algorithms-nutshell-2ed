/**
 * @file pivotRandom.c    Code to select random index as pivot index
 * @brief
 *  Given array vals[left,right] select random index within this range
 *  to use for partition.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>

/**
 * Select pivot index to use in partition. Select a random index from [left,right].
 *
 * \param vals    the array of elements.
 * \param left    the left end of the subarray range
 * \param right   the right end of the subarray range
 * \return        int in the range [left, right] to use in partition.
 */
int selectPivotIndex (void **vals, int left, int right) {
  return left + (int) ((right-left+1) * (rand() / (RAND_MAX + 1.0)));
}
