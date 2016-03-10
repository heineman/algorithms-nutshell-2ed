/**
 * @file parallelQsort.c   Multithread Quicksort implementation
 * @brief 
 *
 *   Complete Multithread Quicksort implementation using PThreads implementation.
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

/* ************ An implementation of Barriers for MAC that lacks them
******** */

#ifdef __MACH__
#include <errno.h>

typedef struct {
  pthread_mutex_t mut;
  pthread_cond_t cond;
  unsigned int count, max, iteration;
} barrier_t;

extern int barrier_init(barrier_t *b, unsigned int max);
extern int barrier_destroy(barrier_t *b);
extern int barrier_wait(barrier_t *b);

#ifndef PTHREAD_BARRIER_SERIAL_THREAD

/** HACK **/
#define BARRIER_SERIAL_THREAD 1
#define PTHREAD_BARRIER_SERIAL_THREAD BARRIER_SERIAL_THREAD
#define pthread_barrier_init(barrier, attr, count) \
  barrier_init((barrier), (count))
#define pthread_barrier_destroy barrier_destroy
#define pthread_barrier_wait barrier_wait
#endif

int barrier_init(barrier_t *b, unsigned int max)
{
    if (max == 0) return EINVAL;

    if (pthread_mutex_init(&b->mut, NULL)) {
      return -1;
    }

    if (pthread_cond_init(&b->cond, NULL)) {
      int err = errno;
      pthread_mutex_destroy(&b->mut);
      errno = err;
      return -1;
    }

    b->count = 0;
    b->iteration = 0;
    b->max = max;

    return 0;
}

int barrier_destroy(barrier_t *b)
{
  if (b->count > 0) return EBUSY;

  pthread_cond_destroy(&b->cond);
  pthread_mutex_destroy(&b->mut);

  return 0;
}

/* when barrier is passed, all threads except one return 0 */
int barrier_wait(barrier_t *b)
{
    int ret, it;

    pthread_mutex_lock(&b->mut);
    b->count++;
    it = b->iteration;
    if (b->count >= b->max) {
      b->count = 0;
      b->iteration++;
      pthread_cond_broadcast(&b->cond);
      ret = BARRIER_SERIAL_THREAD;
    }
    else {
      while (it == b->iteration) pthread_cond_wait(&b->cond, &b->mut);
      ret = 0;
    }
    pthread_mutex_unlock(&b->mut);

    return ret;
}
#endif


/**
 * Thread data.
 *
 * Note that master thread has helper thread context.
 */
typedef struct info {
  int           id;                         /* ID of thread. */
  void          **ar;                       /* array being sorted. */
  int(*cmp)(const void *,const void *);     /* comparison operator. */
  int           left;                       /* for thread, left bound. */
  int           right;                      /* for thread, right bound. */
  int           threshold;                  /* threshold for decision. */
  struct info   *other;                     /* other thread context. */
} THREAD_INFO;


#include "report.h"

/** Problem size below which to use insertion sort. */
int minSize= 0;

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
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */

      tmp = ar[idx];
      ar[idx] = ar[store];
      ar[store] = tmp;
      store++;
    }
  }
  
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */

  tmp = ar[right];
  ar[right] = ar[store];
  ar[store] = tmp;
  return store;
}

/**  proper insertion sort, optimized */
void insertion (void **ar, int(*cmp)(const void *,const void *),
		int low, int high) {
  int loc;
  for (loc = low+1; loc <= high; loc++) {
    int i = loc-1;
    void *value = ar[loc];
    while (i >= 0 && cmp(ar[i], value)> 0) {
#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */
      ar[i+1] = ar[i];
      i--;
    } 

#ifdef COUNT /* TIMING  */
ADD_SWAP;    /* TIMING */
#endif /*COUNT*/ /* TIMING */
  ar[i+1] = value;
  }
}

/* debugging method. */
void output (void **ar, int n) {
  int i;
  for (i = 0; i < n; i++) {
    printf ("%d. %s\n", i, (char*)ar[i]);
  }
}


#ifdef __MACH__
  barrier_t barrier;
#else
  /**
   * Synchronize multiple threads using this barrier. Ensures all launch
   * at the right time. 
   */
  pthread_barrier_t barrier;
#endif

/** Are we done? */
int done = 0;

/** Has helper thread been asked to work? */
int helpRequested = 0;


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
  if (left < right)
    {
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
		THREAD_INFO *helper,
		int left, int right) {
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

  pthread_barrier_wait(&barrier);

  /** When we get here, all threads are synchronized. */
  quickSort2(context->ar, context->cmp, context->other,
	     0, numElements-1);
  done=1;

  /** Now wait for all threads again before exiting. */
  pthread_barrier_wait(&barrier);
}

/**
 * Helper thread that executes single-thread QuickSort in its own thread.
 *
 * Function must return SOMETHING. NULL will do.
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
  return NULL;
}


/** Sort by using Quicksort. */
void
sortPointers (void **vals, int total_elems,
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

  /* launch helper thread first (and it will wait). */
  helper_context = (THREAD_INFO *) malloc(sizeof(THREAD_INFO));
  helper_context->id = 1;
  helper_context->ar = vals;
  helper_context->cmp = cmp;
  helper_context->threshold = numElements/4;
  helper_context->other = NULL;

  if (pthread_create (&thread, NULL, quickSort1, helper_context)) {
    fprintf(stderr, "failed to create thread for QuickSort example.");
    return;
  }

  /* primary thread. */
  context = (THREAD_INFO *) malloc(sizeof(THREAD_INFO));
  context->id = 1;
  context->ar = vals;
  context->cmp = cmp;
  context->threshold = numElements/4;
  context->other = helper_context;

  quickSortEntry(context);
}

