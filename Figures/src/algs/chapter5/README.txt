Version: 2.0
Date: 3-6-2016

Example 5-1: Sequential Search in Python

  find in Figures/chapter5.example1.search

Example 5-2: Sequential Search in Java

  find in algs.model.search.SequentialSearch

Table 5-1: Sequential Search Performance (in seconds) [Makefile]

Example 5-3: Binary Search implementation in Java

  find in algs.model.search.BinarySearch
  
Table 5-2: In-memory execution of 525,288 searches using Binary Search compared to Sequential Search [Makefile]
  
Table 5-3: Secondary-storage Binary Search performance for 524,288 searches (in seconds) [Makefile]
  
Example 5-4: Python search-or-insert variation

  find in PythonCode/adk.binary
  
Figure 5-1: General approach to hashing

  drawn by hand

Example 5-5: Sample Java hashCode

  find in Figures/algs.chapter5.example5.SimpleString
  
Table 5-4: Hash distribution using Java String.hashCode() method as key with b=262,143 [Makefile]

  java algs.chapter5.table4.Main
  
  To determine which pairs of strings have identical key values, run
  
  java algs.chapter5.example5.DuplicateHashcode
    
Example 5-6: Loading the hash table	

  The code from ListHashTable was altered while preparing this example,
  for simplicity.

  find in algs.model.search.ListHashTable

Table 5-5: Statistics of hash tables created by example code [Makefile]
  
Table 5-6: Search time (in ms) for various hash table sizes [Makefile]

Table 5-7: Comparable times (in ms) to build hash table [Makefile]
 
Figure 5-2: Open addressing

  drawn by hand
  
Figure 5-3: The expected number of probes for a search

Example 5-8: Python implementation of open addressing hash table
  
  find in PythonCode/adk.hashtable
  
Example 5-9: Open addressing delete method

  find in PythonCode/adk.hashtable

Figure 5-4: Performance of open addressing [Makefile]

Figure 5-5: Bloom Filter example 

  drawn by hand

Example 5-10: Python Bloom Filter

  find in Pythoncode/adk.bloom
  
Figure 5-6: Bloom Filter example [Makefile]

Figure 5-7. A simple binary search tree (by hand)

Figure 5-8: A degenerate binary search tree (by hand)

Example 5-11: Python Binary Search Tree class definition

  find in PythonCode/adk.bst
  
  This is a straight non-balanced binary search tree, not to be used in practice.

Example 5-12: BinaryTree contains method

  find in PythonCode/adk.bst

Figure 5-9: Unbalanced AVL tree (by hand)

Figure 5-10: Balanced AVL tree (by hand) 

Figure 5-11: Balanced AVL tree with subtrees (by hand)

Figure 5-12: Four unbalanced scenarios (by hand)

Figure 5-13: Rebalancing the Left-Right scenario (by hand)

Example 5-13: add methods in BinaryTree and BinaryNode

  find in PythonCode/adk.avl
  
Example 5-14: rotateRight and rotateRightLeft methods

  find in PythonCode/adk.avl

Example 5-15: rotateLeft and rotateLeftRight methods

  find in PythonCode/adk.avl

Figure 5-14: Locating largest descendant in left subtree (by hand)

Example 5-16: BinaryNode remove and removeFromParent methods

  find in PythonCode/adk.avl

Example 5-17: Support for in-order traversal

  find in PythonCode/adk.avl

Example 5-18: Iterating over the values in a BinaryTree
 
  sample Python code (by hand) for accessing BinaryTree elements
  

