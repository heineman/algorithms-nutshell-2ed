/**
 * @file dot_medianSort.c    Generate MedianSort DOT graph
 * @brief
 *    Implementation that uses dot.h interface to produce on stdout the
 *    DOT commands for a MedianSort execution.
 *
 *    This code is not intended to be used for sorting. Rather, it
 *    generates DOTTY output that shows the behavior of Median Sort. You
 *    have been warned.  Specifically, to compute the median, this code
 *    calls qsort. Ridiculous?  You bet! But it simplifies the creation
 *    of the Dotty output.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <stdlib.h>

#include "dot.h"

/**
 * Sort using mediansort method, generating Dotty output to generate a 
 * visualization of the sort algorithm.
 * 
 * @param id       identifier of the graph node generated for this recursive invocation
 * @param ar       array of values
 * @param cmp      comparison function to use
 * @param left     The left-bounds within which to sort (0 <= left < ar.length)
 * @param right    The right-bounds within which to sort (0 <= right < ar.length)
 */
int mediansort (int id, long *ar, int(*cmp)(const long *,const long *),
		    int left, int right) {
  DOT_FORMAT_PTR fmt = 0;
  int sameL, sameR;
  int i, ct, mid, me = -1;
  long *copy, med;
  if (right <= left) { return 0; }
  
  /* go backwards to avoid null cases. avoid trivial first case */
  if (id/2 > 0) {
    dot_add_undir_edge (2000+id/2, id);
  }

  /* get midpoint and median element position (note 1<=k<=right-left-1). */
  /* compute in copy so we don't alter the elements. Note the allocation. */
  mid = ((right + left)/2)-left; 
  copy = malloc ((right-left+1)*sizeof (long));
  for (i = left; i <= right; i++) {
    copy [i-left] = ar[i];
  }

  /* return position */
  qsort (copy, right-left+1, sizeof (long), (int(*)())cmp);
  med = copy[mid];
  free (copy);

  /* find where 'med' is within 'ar' */
  for (i = left; i <= right; i++) {
    if (ar[i] == med) {
      me = i;
      break;
    }
  }

  /* output line with BLACK median */
  fmt = dot_format_list();
  dot_add_format (fmt, dot_format_type (left+mid, BLACK));/* ADD BLACK first */
  dot_add_format (fmt, dot_format_type (me, GRAY));       /* gray comes next */
  dot_node (ar, id, left, right, fmt);
  dot_release (fmt);
  fmt = 0;

  /* swap median/me */
  if (me != left+mid) {
    long tmp = ar[me];
    ar[me] = ar[left+mid];
    ar[left+mid] = tmp;
  }

  /* now identify all higher-out-of-place (BUT DON'T SWAP) */
  fmt = dot_format_list();
  dot_add_format (fmt, dot_format_type (left+mid, BLACK));
  ct = 0;
  for (i = left; i < left+mid ; i++) {
    int j;
    int dct = ct;  /* count-down since not swapping */
    /* an element left of median greater than it? Find one to swap */
    if (ar[i] > ar[left+mid]) {
      dot_add_format (fmt, dot_format_type (i, GRAY));
      for (j = left+mid+1; j <= right; j++) {
	if (ar[j] <= ar[left+mid] && --dct<0) {
	  dot_add_format (fmt, dot_format_type (j, GRAY));
	  ct++;
	  break;
	}
      }
    }
  }

  /* intermediate nodes are 1000+ */
  dot_node (ar, 1000+id, left, right, fmt);
  dot_release (fmt);

  /* tie together. */
  dot_add_undir_edge (id, 1000+id);

  /* now swap all higher-out-of-place */
  fmt = dot_format_list();
  dot_add_format (fmt, dot_format_type (left+mid, BLACK));
  for (i = left; i < left+mid ; i++) {
    int j;
    /* an element left of median greater than it? Find one to swap */
    if (ar[i] > ar[left+mid]) {
      for (j = left+mid+1; j <= right; j++) {
	if (ar[j] <= ar[left+mid]) {
	  long tmp = ar[i];
	  ar[i] = ar[j];
	  ar[j] = tmp;
	  break;
	}
      }
    }
  }

  /* secondary intermediate nodes are 2000+ */
  dot_node (ar, 2000+id, left, right, fmt);
  dot_release (fmt);

  /* tie together. */
  dot_add_undir_edge (1000+id, 2000+id);

  sameL = mediansort (id*2, ar, cmp, left, left+mid-1);
  sameR = mediansort (id*2+1, ar, cmp, left+mid+1, right);

  if (sameL && sameR) {
    printf ("{rank=same; ");
    dot_nodeid(id*2);
    printf (" ");
    dot_nodeid(id*2+1);
    printf ("}\n");
  }

  return 1;
}

