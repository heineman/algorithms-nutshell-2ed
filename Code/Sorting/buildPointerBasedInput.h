/**
 * @file buildPointerBasedInput.h    Define interface for sorting algorithms over pointer-based information.
 * @brief 
 *    Build up a pointer-based array of Strings to use as a basis for testing
 *    the various sorting algorithms.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef BUILD_POINTER_BASED_INPUT_H
#define BUILD_POINTER_BASED_INPUT_H

/** Fixed element size as macro. */
#define ELEMENT_SIZE 26

/**
 * Interface to sort the pointer-based information found in strings.
 *
 * \param strings      Array of pointer-based strings in memory.
 * \param numElements  Number of strings in this array.
 * \param stringComp   function to compare two string values.
 */
extern void sortPointers (char **strings, int numElements, 
			  int (*stringComp) (char *a1, char *a2));

#endif  /** BUILD_POINTER_BASED_INPUT_H **/
