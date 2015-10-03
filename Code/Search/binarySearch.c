/**
 * @file binarySearch.c    Task to perform number of binary search operations over a binary tree.
 * @brief 
 *    Load up a number of strings into a non-balancing binary tree and define
 *    the search task for locating desired elements from within the tree.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <string.h>

#include "report.h"

/**
 * Standard Binary tree data structure to use.
 */
typedef struct node {
  /** Value stored at node. */
  char *value;

  /** Left child. */
  struct node *lson;

  /** Right child. */
  struct node *rson;

} NODE, *NODE_PTR;

/** Head of binary tree. */
static NODE_PTR root = NULL;

/**
 * Construct the initial instance. No work done since binary tree is 
 * constructed on the fly via insert(char *) method invocations.
 * \param n   the total number of elements to be inserted.
 */
void construct (int n) {
  
}


/** Helper method to populate tree with actual nodes. */
static void insertNode (NODE_PTR node, char *value) {

  if (strcmp (value, node->value) <= 0) {
    if (!node->lson) {
      node->lson = calloc(1,sizeof (NODE));
      node->lson->value = value;
    } else {
      insertNode(node->lson, value);
    }
  } else {
    if (!node->rson) {
      node->rson = calloc(1,sizeof (NODE));
      node->rson->value = value;
    } else {
      insertNode(node->rson, value);
    }
  }
} 


/** 
 * Insert values one at a time into the search structure. 
 *
 * In our case, we insert the elements into a non-balancing tree.
 *
 * \param s   Value to be inserted.
 */
void insert (char *s) {
  if (!root) {
    root = calloc (1, sizeof (NODE));
    root->value = s;
    return;
  }

  insertNode (root, s);
}


/**
 * Search for the desired target within the search structure. 
 *
 * \param target   the desired target
 * \param cmp      the comparison function between two string elements.
 */
int search (char *target, int(*cmp)(const void *,const void *)) {

  NODE_PTR tmp;

  if (!root) return 0; /* empty. */

  tmp = root;
  while (tmp) {
    int comparison = cmp(target, tmp->value);
    if (comparison == 0) {
      return 1;
    } else if (comparison < 0) {
      tmp = tmp->lson;
    } else {
      tmp = tmp->rson;
    }
  }
  
  return 0;  /* nope. */
}
