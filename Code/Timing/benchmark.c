/**
 * @file benchmark.c    Task to sleep for fixed length of time
 * @brief 
 *    Simple example to show accuracy of timing code when the task at hand
 *    does nothing more than sleep for a fixed length of time.
 * 
 * http://www.faqs.org/faqs/unix-faq/faq/part4/section-6.html
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <time.h>
#include <sys/select.h>

/** used for delaying factor. */
static struct timeval delay;

/** Provided method by the operating system. */
extern int        select();

/** Sleep for a fixed length of delay time. */
int nap() {
  return select( 0, (fd_set *)0, (fd_set *)0, (fd_set *)0, &delay );
}

/** Length of time is based on the externally provided numElements. */
extern int numElements;

/** Prepare the input by building structure to sleep for designated time. */
void prepareInput() { 
  long sleepTime = numElements;
  sleepTime *= 1000;

  delay.tv_sec = sleepTime / 1000000L;
  delay.tv_usec = sleepTime % 1000000L;
}

/** Execute by sleeping. */
void execute () {
  nap();
}

/** Nothing special. */
void postInputProcessing() { }

/** Nothing special. */
void problemUsage() { }
