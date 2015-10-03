/**
 * @file pivotLast.c    Code to select rightmost element as pivot index
 * @brief
 *  Given array vals[left,right] select right as the pivot index. 
 *  
 *
 * @author George Heineman
 * @date 6/15/08
 */

/**
 * Select pivot index to use in partition. Select the rightmost one
 *
 * \param vals    the array of elements.
 * \param left    the left end of the subarray range
 * \param right   the right end of the subarray range
 * \return        int in the range [left, right] to use in partition.
 */
int selectPivotIndex (void **vals, int left, int right) {
  return right;
}
