/**
 * @file BinaryHeap.cxx    Contains the implementation of a BinaryHeap
 * @brief 
 *   A BinaryHeap is a core data structure that represents a heap within
 *   a fixed array of size n elements. In this implementation, the elements
 *   are all integers.
 *
 * @author George Heineman
 * @date 6/15/08
 */


#include <stdlib.h>
#include <stdio.h>
#include "BinaryHeap.h"

/** allocate heap of n elements */
BinaryHeap::BinaryHeap (int i) {
  _n = 0;  // initially none in the heap

  // simplify algorithm to consider position 1 as being the 'root'
  _elements = (ELEMENT *) calloc (i+1, sizeof (ELEMENT));
  _pos      = (int *) calloc (i+1, sizeof (int));
}

/** Destructor frees all internal storage. */
BinaryHeap::~BinaryHeap () {
  free (_elements);
  free (_pos);
  _n = -1;

#ifdef REPORT
  printf ("Num Smallest: %d\n", _numSmallest);
  printf ("Num Decreases: %d\n", _numDecrease);
  printf ("Num Insert: %d\n", _numInsert);
  printf ("Num Swaps: %d\n", _numSwaps);
  printf ("Num Comparisons: %d\n", _numComparisons);
#endif
}


/**
 * Return the vertex identifier of the smallest vertex in heap and 
 * readjust the heap.
 */
int BinaryHeap::smallest () {
  int id = _elements[1].id;
  int pIdx;

  INC_SMALL;

  // heap will have one less entry, and we want to place leftover one
  // in proper location.
  ELEMENT last = _elements[_n];
  _n--;

  _elements[1] = last;

  pIdx = 1;
  int child = pIdx*2;
  while (child <= _n) {
    // select smaller of two children
    ELEMENT sm = _elements[child];
    if (child < _n) {
      INC_COMP;
      if (sm.priority >  _elements[child+1].priority) {
	sm = _elements[++child];
      }
    }

    // are we in the right spot? Leave now
    INC_COMP;
    if (last.priority <= sm.priority) { break; }

    // otherwise, swap and move up
    INC_SWAP;
    _elements[pIdx] = sm;
    _pos[sm.id] = pIdx;

    pIdx = child;
    child = 2*pIdx;
  }

  // insert into spot vacated by moved element (or last one)
  _elements[pIdx] = last;
  _pos[last.id] = pIdx;
  return id;
}


/**
 * Insert the given value into the tree with priority. Ties are broken
 * in favor of insert.
 * \param id        id of information to be stored
 * \param priority  priority to associate with this id.
 */
void BinaryHeap::insert (int id, int priority) {
  int i;

  INC_INSERT;

  // add to end of the heap. If 1 then the first element.
  i = ++_n;
  while (i > 1) {
    int       pIdx = i/2;
    ELEMENT   p    = _elements[pIdx];

    // are we in the right spot? Leave now
    INC_COMP;
    if (priority > p.priority) { break; }

    // otherwise, swap and move up
    INC_SWAP;
    _elements[i] = p;
    _pos[p.id] = i;
    i = pIdx;
  }

  // insert into spot vacated by moved element (or last one)
  _elements[i].id = id;
  _elements[i].priority = priority;
  _pos[id] = i;
}


/**
 * Find the vertex with identifier [id] and reduce its priority to the
 * given value. It is the responsibility of the caller to ensure that
 * this function is only invoked when newPriority is indeed smaller than
 * the existing priority associated with the id.
 * \param  id            information to have priority reduced.
 * \param  newPriority   priority which must be smaller than existing priority.
 */
void BinaryHeap::decreaseKey (int id, int newPriority) {
  int size = _n;

  INC_DECREASE;

  // truncate heap (temporarily) and act like the binary heap up to
  // but not including this one is all that exists (cute, huh?) 
  _n = _pos[id] - 1;

  // now we insert and the binary heap is shuffled appropriately
  insert(id, newPriority);

  // since the newPriority must be lower, we can expand back and 
  // we still have a working binary heap
  _n = size;
}

