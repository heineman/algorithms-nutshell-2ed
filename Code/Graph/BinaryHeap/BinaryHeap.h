/**
 * @file BinaryHeap.h    Defines the BinaryHeap concept
 * @brief 
 *   A BinaryHeap is a core data structure that represents a heap within
 *   a fixed array of size n elements. In this implementation, the elements
 *   are all integers.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#ifndef _BHEAP_H_
#define _BHEAP_H_

#ifdef REPORT
/** If defined, computes number of times min() was invoked. */
  #define INC_SMALL _numSmallest++
/** If defined, computes number of times elements were compared. */
  #define INC_COMP  _numComparisons++
/** If defined, computes number of times elements were swapped. */
  #define INC_SWAP  _numSwaps++
/** If defined, computes number of times an element was inserted. */
  #define INC_INSERT  _numInsert++
/** If defined, computes number of times the priority for an element was decreased. */
  #define INC_DECREASE  _numDecrease++
#else
  #define INC_INSERT  
  #define INC_SMALL
  #define INC_COMP
  #define INC_SWAP
  #define INC_DECREASE
#endif /* REPORT */

/**
 * Element stored within the Binary Heap.
 */
typedef struct elt {

  /** user-defined information to be stored by id */
  int     id;            

  /** Key which represents the priority. */
  int     priority;
} ELEMENT, *ELEMENT_PTR;

/**
 * A BinaryHeap is to be used as a priority queue.
 */
class BinaryHeap {

 public:
  BinaryHeap (int);
  ~BinaryHeap ();

  bool isEmpty() { return (_n == 0); }
  int smallest();
  void insert (int, int); 
  void decreaseKey (int, int);

 private:
  int          _n;           // number of elements in binary heap
  ELEMENT_PTR  _elements;    // values in the heap
  int          *_pos;        // pos[i] is index into elements for ith value    

  long         _numComparisons;
  long         _numInsert;
  long         _numSwaps;
  long         _numSmallest;
  long         _numDecrease;

};

#endif /* _BHEAP_H_ */
