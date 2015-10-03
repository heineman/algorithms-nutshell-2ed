/**
 * @file pivotMedianOfMedians.c    Code to select median of medians as pivot index
 * @brief
 *  Given array vals[left,right] select left as the pivot index. 
 *  
 * Only works if a proper defaultComp is provided. This function is used
 * as the default comparison between two elements in the array.
 *
 * @author George Heineman
 * @date 6/15/08
 */

/** default ascending operator provided externally */
extern int ascending(const void *,const void *);

/** Provided externally for selecting median as based on BFPRT algorithm. */
extern int selectMedian (void **ar, int(*cmp)(const void *,const void *),
			 int left, int right);

/**
 * Select pivot index to use in partition. Select median of medians.
 *
 * \param vals    the array of elements.
 * \param left    the left end of the subarray range
 * \param right   the right end of the subarray range
 * \return        int in the range [left, right] to use in partition.
 */
int selectPivotIndex (void **vals, int left, int right) {
  return selectMedian (vals, &ascending, left, right);
}
