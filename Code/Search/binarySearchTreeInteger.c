/**
 * @file binarySearchTreeInteger.c    Task to perform number of binary search operations on a non-balanced binary tree
 * @brief 
 *    Receive integers one by one (in sorted order) and create a binary
 *    tree which means the tree heavily leans to the right and ultimately
 *    forms a linked list.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdlib.h>
#include <string.h>

#include "report.h"

/**
 * \typedef binarySearchTreeInteger/NODE
 * 
 * Standard Binary tree data structure of integers.
 */
typedef struct node {
  /** Value of the node. */
  int value;

  /** The left child. */
  struct node *lson;

  /** The right child. */
  struct node *rson;

} NODE, *NODE_PTR;


/** Root of the binary search tree. */
static NODE_PTR root = NULL;

/**
 * Construct the initial instance. No work done since binary tree is 
 * constructed on the fly via insert(char *) method invocations.
 * \param n   the total number of elements to be inserted.
 */
void construct (int n) {
  
}


/** Helper method to populate tree with actual nodes. */
static void insertNode (NODE_PTR node, int value) {

  if (value < node->value) {
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
void insert (int s) {
  if (!root) {
    root = calloc (1, sizeof (NODE));
    root->value = s;
    return;
  }

  insertNode (root, s);
}


/* conduct the search. */
int search (int target, int(*cmp)(const int,const int)) {

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
