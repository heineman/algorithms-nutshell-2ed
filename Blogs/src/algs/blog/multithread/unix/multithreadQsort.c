/**
 * @file baseQsort.c   Multithread Quicksort implementation
 * @brief 
 *
 *   Complete Multithread Quicksort implementation using PThreads 
 *   implementation.
 * 
 *   Aided and abetted by lecture notes found at:
 *
 *      http://reptar.uta.edu/NOTES4351/02notes.pdf
 * 
 * @author George Heineman
 * @date 5/30/09
 */

#include <stdio.h>
#include <sys/types.h>

#include <stdlib.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <pthread.h>

#include "report.h"

/** Defined by '-g' and defaults to 5. */
extern int groupingSize;

/**
 * Thread data.
 *
 * Note that master thread has pointer to helper thread context.
 */
typedef struct info {
  void          **ar;                       /* array being sorted. */
  int(*cmp)(const void *,const void *);     /* comparison operator. */
  int           left;                       /* for thread, left bound. */
  int           right;                      /* for thread, right bound. */
  int           threshold;                  /* threshold for decision. */
  struct info   *helper;                    /* helper thread context. */
} THREAD_INFO;

/** Default to 4 unless externally specified. */
#ifndef RATIO
#define RATIO 4
#endif

/**
 * Synchronize multiple threads using this barrier. Ensures all 
 * threads launch at the same time. 
 */
pthread_barrier_t barrier;

/** Are we done? */
int done = 0;

/** Has helper thread been asked to work? */
int helpRequested = 0;

/**
 * In linear time, group the sub-array ar[left, right) around a pivot
 * element pivot=ar[right] by storing pivot into its proper location,
 * store, within the sub-array (whose location is returned by this
 * function) and ensuring that all ar[left,store) <= pivot and all
 * ar[store+1,right) > pivot.
 * 
 * @param ar           array of elements to be sorted.
 * @param cmp          comparison function
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (inclusive)
 * @return             location of the pivot index properly positioned.
 */
int partition (void **ar, int(*cmp)(const void *,const void *),
	       int left, int right) {
  void *tmp, *pivot;
  int idx, store;

  /* use right-most element as pivot */
  pivot = ar[right];

  /* all values <= pivot are moved to front of array and pivot inserted
   * just after them. */
  store = left;
  for (idx = left; idx < right; idx++) {
    if (cmp(ar[idx], pivot) <= 0) {

      tmp = ar[idx];
      ar[idx] = ar[store];
      ar[store] = tmp;
      store++;
    }
  }
  
  tmp = ar[right];
  ar[right] = ar[store];
  ar[store] = tmp;
  return store;
}

/** 
 * Straight quicksort where partition uses right-most element as the 
 * pivot index.
 * 
 * @param ar           array to be sorted.
 * @param cmp          comparison function.
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (inclusive)
 */
void quickSort(void **ar, int(*cmp)(const void *,const void *),
	       int left, int right) {
  if (left < right) {
    int p = partition(ar, cmp, left, right);
    quickSort(ar, cmp, left, p-1);
    quickSort(ar, cmp, p+1, right);
  }
}

/**
 * Quicksort that delegates to helper thread the sorting of a sub-array
 * should a thread be available and if the problem size is sufficiently
 * large enough to warrant such an action.
 *
 * @param ar           array being sorted
 * @param cmp          comparison function to use
 * @param helper       context for helper thread
 * @param left         lower bound index position  (inclusive)    
 * @param right        upper bound index position  (inclusive) 
 */
void quickSort2(void **ar, int(*cmp)(const void *,const void *),
		THREAD_INFO *helper, int left, int right) {
  int p,n;

  if (left < right) {
    p = partition(ar, cmp, left, right);
    n = p - left;

    /** If helper already requested to help or problem too big, recurse. */
    if (helpRequested || n >= helper->threshold) {
      quickSort2(ar, cmp, helper, left, p-1);
    } else  {
      /** initialize helper variables and ask for help. */
      helper->left = left;
      helper->right = p-1;
      
      helpRequested = 1;
    }
    
    n = right - p;
    if (helpRequested || n >= helper->threshold) {
      quickSort2(ar, cmp, helper, p+1, right);
    } else {
      /** initialize helper variables and ask for help. */
      helper->left = p+1;
      helper->right = right;
      
      helpRequested = 1;
    }
  }
}

/**
 * Entry point for primary thread.
 * 
 * Once synchronized, launches the primary sort routine.
 * 
 * @param arg    thread context
 */
void quickSortEntry(void *arg) {
  THREAD_INFO *context = (THREAD_INFO *) arg;

  /** Wait until all threads ready to go. */
  pthread_barrier_wait(&barrier);

  /**
   * When we get here, all threads are synchronized. Note that
   * numElements is a global int which stores number of elements.
   */
  quickSort2(context->ar, context->cmp, context->helper,
	     0, numElements-1);

  /** Stop Helper thread. */
  done=1;

  /** Now wait for all threads again before exiting. */
  pthread_barrier_wait(&barrier);
}

/**
 * Helper thread that executes single-thread QuickSort in its own thread.
 *
 * @param arg    thread context
 */
void *quickSort1(void *arg) {
  THREAD_INFO *context = (THREAD_INFO *) arg;

  pthread_barrier_wait(&barrier);

  /** Tight spin loop. Only do work if requested. */
  while (!done || helpRequested) {
    if (helpRequested) {
      quickSort(context->ar, context->cmp, context->left, context->right);

      helpRequested = 0;
    }
  }

  pthread_barrier_wait(&barrier);
  return NULL;  /* not required because this method executes in thread. */
}


/**
 * Sort by using Multithread Quicksort. 
 *
 * use "-g R" option to set the ratio used for setting threshold. If R=1
 * then the threshold is set to the size of the problem, and each attempt
 * to launch a helper thread succeeds. If R=16777216 (or any high number)
 * then the threshold is set to 0 and no thread is ever spawned. Naturally
 * in-between values are useful and the optimum value depends upon your
 * specific computer architecture.
 *
 * Threshold is set to total_elems/R
 */
void sortPointers (void **vals, int total_elems,
		   int(*cmp)(const void *,const void *)) {

  pthread_t       thread;
  THREAD_INFO     *context, *helper_context;

  /* Helper thread for sub-problems */
  done = 0;

  if (pthread_barrier_init (&barrier, 0, 2)) {
    /** nothing good can come of this. */
    fprintf (stderr, "Unable to execute multi-threaded QuickSort.");
    return;
  }

  /* Launch helper thread first (and it will wait). */
  helper_context = (THREAD_INFO *) malloc(sizeof(THREAD_INFO));
  helper_context->ar = vals;
  helper_context->cmp = cmp;
  helper_context->threshold = total_elems/groupingSize;
  helper_context->helper = NULL;

  if (pthread_create (&thread, NULL, quickSort1, helper_context)) {
    fprintf(stderr, "failed to create thread for QuickSort example.");
    return;
  }

  /* Primary thread knows of the helper thread. */
  context = (THREAD_INFO *) malloc(sizeof(THREAD_INFO));
  context->ar = vals;
  context->cmp = cmp;
  context->threshold = total_elems/groupingSize;
  context->helper = helper_context;

  quickSortEntry(context);
}
