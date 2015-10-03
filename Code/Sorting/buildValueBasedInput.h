/**
 * @file buildValueBasedInput.h    Define interface for sorting algorithms over value-based information.
 * @brief 
 *    Build up a value-based array of Strings to use as a basis for testing
 *    the various sorting algorithms.
 *
 * @author George Heineman
 * @date 05/15/08
 */

#ifndef BUILD_STRING_INPUT_H
#define BUILD_STRING_INPUT_H

/** Fixed element size as macro. */
#define ELEMENT_SIZE 26

/**
 * Structure of each individual element is a fixed block of characters.
 * The array of strElement forms that value-based array to be sorted.
 */
struct strElement {
  /** Fixed character array of values. */
  char   s[ELEMENT_SIZE];
};

/**
 * Interface to sort the value-based information found in strings
 * \param strings      Array of value-based strings in memory.
 * \param numElements  Number of strings in this array.
 * \param size         Size of the base string in this array.
 * \param stringComp   function to compare two string values.
 */
extern void  sortValues (struct strElement *strings,
			 int numElements, int size,
			 int (*stringComp) (char *a1, char *a2));


#endif  /** BUILD_STRING_INPUT_H **/
