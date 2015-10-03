/**
 * @file Sorting/Longs/dot.c    Implementation of interface to DOT for sorting graphs.
 * @brief
 *    Enable DOT graphs (http://www.graphviz.org) to be generatd from the
 *    invocation of specially crafted sort routines. This code is used to
 *    show the progress of MedianSort and QuickSort in the book.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <stdio.h>
#include "dot.h"

DOT_FORMAT_PTR dot_format_list() {
  DOT_FORMAT_PTR df = (DOT_FORMAT_PTR) malloc (sizeof (DOT_FORMAT));
  df->type = -1;
  df->id = -1;
  df->next = 0;
  return df;
}

DOT_FORMAT_PTR dot_format_type(int id, int i) {
  DOT_FORMAT_PTR df = (DOT_FORMAT_PTR) malloc (sizeof (DOT_FORMAT));
  df->id = id;
  df->type =i;
  df->next = 0;
  return df;
}

void dot_add_format (DOT_FORMAT_PTR fp, DOT_FORMAT_PTR n) {
  while (fp->next != 0) {
    fp = fp->next;
  }

  fp->next = n;
}

void dot_release (DOT_FORMAT_PTR dp) {
  while (dp != 0) {
    DOT_FORMAT_PTR n = dp->next;
    free (dp);
    dp = n;
  }
}

void dot_header(char *s) {
  printf ("graph %s {\n", s);
  printf ("ranksep=\"0.25 equally\";\n");
  printf ("node [shape=plaintext, fontname=\"courier\"]\n");
}

int dot_format_font (int i, DOT_FORMAT_PTR fp) {
  while (fp != 0) {
    if (fp->id == i) {
      if (fp->type == BLACK) {
	printf ("<font color=\"#FFFFFF\">");
	return 1;
      }

      return 0;
    }

    /* next one? */
    fp = fp->next;
  }

  return 0;
}

int dot_format_td (int i, DOT_FORMAT_PTR fp) {
  while (fp != 0) {
    if (fp->id == i) {
      if (fp->type == GRAY) {
	printf (" bgcolor=\"#C0C0C0\"");
      } else if (fp->type == BLACK) {
	printf (" bgcolor=\"#000000\"");	
      }
      return 1;
    }

    /*  next one? */
    fp = fp->next;
  }

  return 0;
}

void dot_trailer() {
  printf ("}\n");
}


void dot_nodeid(int id) {
  printf ("n%d", id);
} 

void dot_add_edge (int id, int id2) {
  dot_nodeid(id);
  printf (" -> ");
  dot_nodeid(id2);  
  printf (";\n");
}

void dot_add_undir_edge (int id, int id2) {
  dot_nodeid(id);
  printf (" -- ");
  dot_nodeid(id2);  
  printf (";\n");
}

void dot_node(long *ar, int id, int left, int right, DOT_FORMAT_PTR fmt) {
  int i;

  printf ("n%d [label=< <table cellborder=\"1\" cellspacing=\"0\" cellpadding=\"2\">\n", id);
    printf ("<tr style=\"font-family: courier;\">\n");
    for (i = left; i <= right; i++) {
      int rc;
      printf ("<td");
      /* bgcolor information for <td> */
      dot_format_td (i, fmt);
      printf (">");
      rc = dot_format_font (i, fmt);
      printf ("%2ld", (long) ar[i]);
      if (rc) { printf ("</font>"); }
      printf ("</td>\n");
    }
    printf ("</tr>\n");
  printf ("</table>>];\n");
}
