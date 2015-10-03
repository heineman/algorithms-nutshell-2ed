/**
 * @file pivotMedianOfThree.c    Code to select random index as pivot index from array of strings
 * @brief
 *  Given array vals[left,right] of strings, select random index within this 
 *  range to use for partition.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <string.h>

/**
 * Select pivot index to use in partition. Select median element of vals[left], vals[mid], and vals[right] to use.
 *
 * \param vals    the array of elements.
 * \param left    the left end of the subarray range
 * \param right   the right end of the subarray range
 * \return        int in the range [left, right] to use in partition.
 */
int selectPivotIndex (void **vals, int left, int right) {
  int mid;

  /**
   * This really needs to have the comparison method passed in! But 
   * I don't want to change the interface, so this will suffice in the
   * short term since all we have are strings in the example.
   */
  char *choices[3];
  choices[0] = vals[left];
  mid = (left+right+1)/2;
  choices[1] = vals[mid];
  choices[2] = vals[right];

  if (strcmp(choices[0], choices[1]) < 0) {
    if (strcmp (choices[1], choices[2]) <= 0) {
      return mid;
    } else if (strcmp (choices[0], choices[2]) < 0) {
      return right;
    } else {
      return left;
    }
  }

  if (strcmp (choices[0], choices[2]) < 0) {
    return left;
  } else if (strcmp (choices[1], choices[2]) < 0) {
    return right;
  } else {
    return mid;
  }
}
