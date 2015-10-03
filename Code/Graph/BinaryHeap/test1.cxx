/**
 * @file Graph/BinaryHeap/test1.cxx   Test Case for Binary Heap
 * @brief 
 *   A test case
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <cassert>
#include <iostream>

using namespace std;

#include "BinaryHeap.h"

/** Launch the test. */
int main () {

  BinaryHeap heap(10);
  assert (heap.isEmpty()); // "empty Not working"
  heap.insert (0, 10);
  assert (!heap.isEmpty()); //  "empty Not working";

  heap.insert (1, 5);
  assert (heap.smallest() == 1);  //  "min not working";
  assert (heap.smallest() == 0);  //  "min not working";
  assert (heap.isEmpty());        //  "empty Not working";

  heap.insert (2, 9);
  heap.insert (3, 2);
  heap.insert (4, 15);
  heap.insert (5, 11);
  heap.insert (6, 1);

  assert (heap.smallest() == 6); // "min not working";
  assert (heap.smallest() == 3); // "min not working";
  heap.decreaseKey (4, 3);
  assert (heap.smallest() == 4); // "min not working";
  assert (heap.smallest() == 2); // "min not working";
  assert (heap.smallest() == 5); // "min not working";
  assert (heap.isEmpty());       // "empty Not working";

  cout << "Tests passed\n";
}
