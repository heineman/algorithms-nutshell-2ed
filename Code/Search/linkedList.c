/**
 * @file linkedList.c    Task to perform searches in unordered linked list
 * @brief 
 *    Load up a linked list of strings and perform number of unordered
 *    searches. No check for NULL is used.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <stdlib.h>
#include "report.h"

/**
 * \typedef linkedList/NODE
 *
 * Simple linked list of string elements.
 */
typedef struct node {
  /** The string being stored. */
  char *value;
  
  /** Next one in the list. */
  struct node *next;
} NODE, *NODE_PTR;

/** Head of the linked list of strings. */
static NODE_PTR ds;

/** Tail of the linked list of strings. */
static NODE_PTR last;

/** construct the initial instance. Simply initialize 'ds' and 'last'. */
void construct (int n) {
  last = ds = NULL;
}


/** insert strings one at a time to the end of the linked list. */
void insert (char *s) {
  if (ds == NULL) {
    last = ds = (NODE_PTR) calloc (1, sizeof (NODE));
    ds->value = s;
  } else {
    last->next = (NODE_PTR) calloc (1, sizeof (NODE)); 
    last->next->value = s;
    last = last->next;
  }
}


/** Search for the target within the linked list. */
int search (char *target, int(*cmp)(const void *,const void *)) {

  NODE_PTR tmp = ds;;

  while (tmp != NULL) {
    if (!cmp(tmp->value, target)) {
      return 1;
    }
    
    tmp = tmp->next;
  }

  return 0;  /* nope. */
}
