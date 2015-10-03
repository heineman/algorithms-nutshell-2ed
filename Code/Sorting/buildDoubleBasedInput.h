/**
 * @file buildDoubleBasedInput.h    Define interface for sorting algorithms over pointer-based information
 * @brief 
 *    Build up a pointer-based array of Doubles to use as a basis for testing
 *    the various sorting algorithms.
 *
 * @author George Heineman
 * @date 05/15/08
 */

#ifndef BUILD_DOUBLE_BASED_INPUT_H
#define BUILD_DOUBLE_BASED_INPUT_H

/** where input is located in memory. */
extern double **values;

/**
 * Interface to sort the pointer-based information found in values. 
 * \param values      Array of strings in memory.
 * \param n           number of strings in this array.
 * \param doubleComp  function to compare two double values. 
 */
extern void sortPointers (double **values, int n, 
			  int (*doubleComp) (double *a1, double *a2));

/** 
 * Defines the base size of each element as a MACRO
 */
#define ELEMENT_SIZE sizeof(double)

#endif  /** BUILD_DOUBLE_BASED_INPUT_H **/
