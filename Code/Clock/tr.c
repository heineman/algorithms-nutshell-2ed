/**
 * @file tr.c   Report on the clock resolution for the given machine.
 * @brief 
 *
 *   Small example to access clock resolution information for both
 *   millisecond and nanosecond timers.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <time.h>
#include <sys/time.h>

int main(int argc, char **argv) {
#ifdef __USE_POSIX199309
  int i;
  struct timespec N;

  i = clock_getres(CLOCK_REALTIME, &N);
  if (i == 0)
    printf("Resolution is %ld seconds and  %ld nanoseconds for CLOCK_REALTIME \n",N.tv_sec, N.tv_nsec);
  i = clock_getres(CLOCK_PROCESS_CPUTIME_ID, &N);
  if (i == 0)
    printf("Resolution is %ld seconds and  %ld nanoseconds for CLOCK_PROCESS_CPUTIME_ID\n",N.tv_sec, N.tv_nsec);
  i = clock_getres(CLOCK_THREAD_CPUTIME_ID, &N);
  if (i == 0)
    printf("Resolution is %ld seconds and  %ld nanoseconds for CLOCK_THREAD_CPU_TIME_ID\n",N.tv_sec, N.tv_nsec);
  i = clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &N); 
  if (i == 0) 
    printf("Elapsed time is %ld seconds and  %ld nanoseconds\n",N.tv_sec, N.tv_nsec);
#else
  printf ("POSIX timing not implemented on this machine.");
#endif

  return 0;
}

