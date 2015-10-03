/**
 * @file problem.h    Define interface to problem statement
 * @brief 
 *    Each problem statement is defined by four methods:
 *    <ol>
 *    <li> void prepareInput (int, int, char **)
 *    <li> void execute()
 *    <li> void postInputProcessing ()
 *    <li> void problemUsage()
 *    </ol>
 * 
 *
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _PROBLEM_H
#define _PROBLEM_H

/**
 * To use this timing library, your code must provide the following methods:
 *
 *   void problemUsage()           
 *
 *         shows what is expected
 *    
 *   void prepareInput (int inputSize, int argc, char **argv);
 * 
 *         prepare the input and process extra command line arguments
 *         make sure that you reset optind to 0 if you process args.
 *          
 *
 *   void postInputProcessing()    
 *
 *         performs post-solution processing
 *
 *   void execute()                
 * 
 *         begin the problem
 *
 */

/** Whether run is to be executing in verbose mode. */
extern int verbose;

/** Most important parameter is the number of elements selected. */
extern int numElements;

/** Ensure that COUNT macro adds checks for nil check. */
#ifdef COUNT
#define isNil(p) (p==0&ADD_NIL_COMP)
#else
#define isNil(p) (p==0)
#endif  /* COUNT */


#endif /* _PROBLEM_H */
