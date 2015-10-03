/**
 * @file buildFileBasedInput.h    Define interface for sorting algorithms over information stored on disk
 * @brief 
 *    Build up a file of strings of fixed-size to use as input for the
 *    various sorting algorithms. This code shows the overhead implicit
 *    in secondary storage.
 *
 * @author George Heineman
 * @date 05/15/08
 */

#ifndef BUILD_FILE_INPUT_H
#define BUILD_FILE_INPUT_H

#include <stdlib.h>

/**
 * Interface to sort the file of strings.
 *
 * \param strings         File of strings on disk
 * \param numElements     Number of strings in this file.
 * \param size            The fixed size of the strings in the file.
 * \param fileCompString  Special comparator function that compares string in memory (a2) against location on disk (long pos).
 */
extern void sortFile (FILE *strings, int numElements, int size,
		      int (*fileCompString) (const long pos, const char *a2));

/** fixed size of strings. */
#define ELEMENT_SIZE 26


#endif  /** BUILD_FILE_INPUT_H **/
