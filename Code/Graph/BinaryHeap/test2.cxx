/**
 * @file Graph/BinaryHeap/test2.cxx   Test Case for Binary Heap
 * @brief 
 *   A test case
 *
 * @author George Heineman
 * @date 6/15/08
 */
#include <cassert>
#include <iostream>
#include <climits>

using namespace std;
#include "BinaryHeap.h"

/** Launch the test. */
int main () {
  int id;

  BinaryHeap heap(8);
  assert (heap.isEmpty());  //  "empty Not working");
  heap.insert(0, 1);
  assert (!heap.isEmpty());  //  "empty Not working");

  heap.insert(1, INT_MAX);
  heap.insert(2, INT_MAX);
  heap.insert(3, INT_MAX);
  heap.insert(4, INT_MAX);
  heap.insert(5, INT_MAX);
  heap.insert(6, INT_MAX);
  heap.insert(7, 1);

  
  id = heap.smallest();
  assert (id == 7); // "min not working";
  id = heap.smallest();
  assert (id == 0); // "min not working";

  cout << "Tests passed\n";
}
