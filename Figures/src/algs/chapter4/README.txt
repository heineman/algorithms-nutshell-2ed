Version: 2.0
Date: 3-6-2016

Some images found in this chapter are generated. If the figure is 
drawn manually then it is not included in this list. For examples, 
we show where in the code repository to locate the code.

Table 4-1: Stable sort of airport terminal information

  hand-drawn

Table 4-2: Criteria for choosing a sorting algorithm

  hand-drawn

Example 4-1: Insertion Sort with pointer-based values

  taken from Code/Sorting/PointerBased/insertionPtr.c
  
Example 4-2: Insertion Sort using value-based information

  taken from Code/Sorting/ValueBased/insertion.c
  
In-text-reference

  The numTranspositions program referenced within this text can
  be executed as:
  
    cd Code/Chapter4
    ./numTranspositions n      (where n is an integer in range [1,12]

Table 4-3: Insertion Sort bulk move vs. Insertion Sort (in seconds) [MAKE]

    cd Code/Sorting/Chapter-4-Figures
    ../../bin/suiteRun.sh table4-3.rc

Example 4-3: Selection Sort implementation in C

    cd Code/Sorting/PointerBased
    code found in selectionSort.c

Figure 4-4: Heap Sort Example

    designed by hand
    
Figure 4-5: Sample heap stored in an array

    cd Code/Sorting/Long
    ./Figure4-6 and loop at final row.

Figure 4-6: buildHeap operating on an initially sorted array [MAKE]

    cd Code/Sorting/Long
    ./Figure4-6
        
Example 4-4: Heap Sort implementation in C

    cd Code/Sorting/PointerBased
    code found in straight_HeapSort.c
    
    It is worth comparing the code (both readability and performance)
    with an optimized, non-recursive implementation of Heap Sort found 
    in heapSort.c in the same directory.
    
Table 4-4: Performance comparison of non-recursive variation (in seconds) [MAKE]

    cd Code/Sorting/Chapter-4-Figures
    ../../bin/suiteRun.sh table4-4.rc    
    
Example 4-5: C implementation to partition ar[left,right] around a 
    given pivot element.
    
    cd Code/Sorting/PointerBased
    code exists within various implementations, such as baseQsort.c

Example 4-6: Quicksort implementation in C

    cd Code/Sorting/PointerBased
    code found in baseQsort.c
    
Figure 4-7: Sample Quicksort execution [MAKE]

    cd Code/Sorting/Longs
    make
    Take the 'figure4-8.dotty' file and use as input to the DOT
      program (freely available from www.graphviz.org). Make sure
      that you use version 2-16.1 or higher. For example,
      
       dot -Tps figure4-8.dotty > 4-8.ps
       convert 4-8.ps 4-8.png
        
Figure 4-8: A different Quicksort execution [MAKE]

    cd Code/Sorting/Longs
    make
    Take the 'figure4-8.dotty' file and use as input to the DOT
      program (freely available from www.graphviz.org). Make sure
      that you use version 2-16.1 or higher. For example,
      
       dot -Tps figure4-9.dotty > 4-9.ps
       convert 4-9.ps 4-9.png

Figure 4-9: small example demonstrating Bucket Sort

   done by hand

Example 4-7: Bucket Sort implementation in C

    cd Code/Sorting/PointerBased
    code found in bucketLinkedListSortPtr.c
    
    An alternative implementation using arrays that are redimensionalized
    as needed is found in the same directory inside bucketArraySortPtr.c
    
Example 4-9 hash and numBuckets functions for [0,1) range

    cd Code/Sorting/Doubles
    code found in hash.c

Claim that linked list implementation is 30-40% faster: [MAKE]

    cd Code/Sorting/Chapter-4-Figures
    ../../bin/suiteRun.sh compare-bucket-array-vs-list.rc
    
Example 4-13: hash and numBuckets functions for Hash Sort

    cd Code/Sorting/Strings
    code found in hash17576.c
    
    Alternate possible hash functions are found in hash676.c and hash26.c
    in this same directory.

Table 4-5: Sample performance for Bucket Sort with different numbers [MAKE]
           of buckets, compared with Quicksort (in seconds)
           
    cd Code/Sorting/Chapter-4-Figures
    ../../bin/suiteRun.sh table4-5.rc

Example 4-10 MergeSort implementation in Python

    cd PythonCode/adk
	code found in mergesort.py
    
Example 4-11 External MergeSort implementation in Java

    cd JavaCode/algs/model/sort
    code found in MergeSortFileMapped.java

Tables 4-6 through 4-8 are generated from a number of configuration files [MAKE]

    cd Code/Sorting/Chapter-4-Figures/
    ../../bin/suiteRun.sh table4-6.rc
    ../../bin/suiteRun.sh table4-7.rc
    ../../bin/suiteRun.sh table4-8.rc


    