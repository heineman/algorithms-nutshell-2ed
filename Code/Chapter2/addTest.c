/**
 * @file addTest.c   Driver for timing a variety of add implementations.
 * @brief 
 *   Four separate add implementations are provided and they are all executed
 *   to compare their performance.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <stdio.h>

#include "report.h"

/** Time before process starts.   */
static struct timeval before;

/** Time after process completes. */
static struct timeval after;      

/** Size of problem. */
int n;

/** Add implementation 1. */
void add(int *n1, int *n2, int *sum) {
  int b = n-1;
  int carry = 0;
  while (b >= 0) {
    int s = n1[b] + n2[b] + carry;
    sum[b+1] = s % 10;
    if (s > 9) { carry = 1; } else { carry = 0; }
    b--;
  }
  
  sum[0] = carry;
}
	
/** Add implementation 2. */
void add2(int *n1, int *n2, int *sum) {
  int b = n-1;
  /* set carry bit */
  sum[b+1]=0;  
  while (b >= 0) {
    int s = n1[b] + n2[b] + sum[b+1];
    sum[b+1] = s % 10;
    if (s > 9) { sum[b] = 1; } 
    b--;
  }
}
	
/** Add implementation 3. */
void alt(int *n1, int *n2, int *sum) {
  int b = n;
  /* set carry bit. */
  sum[b]=0; 
  while (--b >= 0) {
    int s = n1[b] + n2[b] + sum[b+1];
    sum[b+1] = s % 10;
    sum[b] = s/10;
  }
}
	
/** Add implementation 4. */
void last(int *n1, int *n2, int *sum) {
  int b = n;
  int carry = 0;
  while (--b >= 0) {
    int s = n1[b] + n2[b] + carry;
    if (s > 9) {
      sum[b+1] = s-10;
      carry = 1;
    } else {
      sum[b+1] = s;
      carry = 0;
    }
  }
  
  sum[0] = carry;
}
	
/** 
 * Generate random number of size n directly into num
 * 
 * @param num 
 */
void randomNumber (int *num ) {
  int j;
  for (j = 0;j < n; j++) {
    num[j] = (int) (10*(rand() / (RAND_MAX + 1.0)));
  }
}
	
/** Generate full table. */
void generateTable() {
  int i;
  long base, addT, altT, lastT;
  long checkSum = 0;

  /* larger trials (n=262144 to 2097152) if desired. */

  /* Trials */
  int MAX_SIZE = 1048576;
  int NUM_TRIALS = 10000;
  n = 256;
		
  while (n <= MAX_SIZE) {
    int *n1, *n2, *sum, *copy1, *copy2;
    printf ("Trying %d...\n", n);

    /* generate numbers and space for storage */
    n1 = calloc (n, sizeof (int));
    n2 = calloc (n, sizeof (int));
    randomNumber(n1);
    randomNumber(n2);
    sum = calloc (n+1, sizeof (int));

    copy1= calloc (n, sizeof (int));
    copy2= calloc (n, sizeof (int));

    bcopy (n1, copy1, n);
    bcopy (n2, copy2, n);

    /** Timing as follows:
     *
     * gettimeofday(&before, (struct timezone *) NULL);    BEGIN
     *    OP HERE
     * gettimeofday(&after, (struct timezone *) NULL);     END
     *
     * long usecs = diffTimer (&before, &after);           SHOW RESULTS
     * report (usecs);
     */

    /*  BASELINE*/
    gettimeofday(&before, (struct timezone *) NULL);   

    for (i = 0; i < NUM_TRIALS; i++) {
      int c;

      /* NOP */
      checkSum += n1[0];
				
      /* circular shift (n1 left, n2 right). */
      c = n1[0];
      bcopy (n1+1, n1, n-1);
      n1[n-1] = c;

      c = n2[n-1];
      bcopy (n2, n2+1, n-1);
      n2[0] = c;
    }

    gettimeofday(&after, (struct timezone *) NULL);   
    base = diffTimer (&before, &after);
			
    /* ADD */
    bcopy (copy1, n1, n);
    bcopy (copy2, n2, n);

    gettimeofday(&before, (struct timezone *) NULL);   
    for (i = 0; i < NUM_TRIALS; i++) {
      int c;
      add(n1,n2,sum);
      checkSum += sum[0];

      /* circular shift (n1 left, n2 right). */
      c = n1[0];
      bcopy (n1+1, n1, n-1);
      n1[n-1] = c;

      c = n2[n-1];
      bcopy (n2, n2+1, n-1);
      n2[0] = c;
    }

    gettimeofday(&after, (struct timezone *) NULL); 
    addT = diffTimer (&before, &after);
			
    /* ALT */
    bcopy (copy1, n1, n);
    bcopy (copy2, n2, n);
    for (i = 0; i < NUM_TRIALS; i++) {
      int c;
      alt(n1,n2,sum);
      checkSum += sum[0];
				
      /* circular shift (n1 left, n2 right). */
      c = n1[0];
      bcopy (n1+1, n1, n-1);
      n1[n-1] = c;

      c = n2[n-1];
      bcopy (n2, n2+1, n-1);
      n2[0] = c;
    }
    gettimeofday(&after, (struct timezone *) NULL);   
    altT = diffTimer (&before, &after);
    
			
    /* LAST */
    bcopy (copy1, n1, n);
    bcopy (copy2, n2, n);
    gettimeofday(&before, (struct timezone *) NULL);   
    for (i = 0; i < NUM_TRIALS; i++) {
      int c;
      last(n1,n2,sum);
      checkSum += sum[0];
				
      /* circular shift (n1 left, n2 right). */
      c = n1[0];
      bcopy (n1+1, n1, n-1);
      n1[n-1] = c;

      c = n2[n-1];
      bcopy (n2, n2+1, n-1);
      n2[0] = c;
    }
    gettimeofday(&after, (struct timezone *) NULL);   
    lastT = diffTimer (&before, &after);
    report (lastT);

			
    printf("%d,Base:%ld,ms.\n", n, base/1000);
    printf("%d,Add*:%ld,ms.\n", n, (addT-base)/1000);
    printf("%d,Alt*:%ld,ms.\n", n, (altT-base)/1000);
    printf("%d,Last*:%ld,ms.\n", n, (lastT-base)/1000);

    /* advance */
    n = n * 2;
    free(n1);
    free(n2);
    free(copy1);
    free(copy2);
    free(sum);
  }
  printf ("Checksum:%ld\n", checkSum);
}

/** Launch program by generating table. */
int main (int argc, char **argv) {
  generateTable();
  exit (0);
}

/** Useful debugging function. */
void output (int *n1) {
  int i;
  for (i = 0; i < n; i++) {
    printf ("%d", n1[i]);
  }
  printf ("\n");
}
