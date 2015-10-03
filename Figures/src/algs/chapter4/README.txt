Some images found in this chapter are generated. If the figure is 
drawn manually then it is not included in this list. For examples, 
we show where in the code repository to locate the code.

Example 4-1: Insertion Sort with pointer-based values

  taken from Code/Sorting/PointerBased/insertionPtr.c
  
Example 4-2: Insertion Sort using value-based information

  taken from Code/Sorting/ValueBased/insertion.c
  
Footnote 2

  The numTranspositions program referenced within this footnote can
  be executed as:
  
    cd Code/Chapter4
    ./numTranspositions n      (where n is an integer in range [1,12]

Table 4-1: Insertion Sort bulk move vs. Insertion Sort (in seconds) [MAKE]

    cd Code/Sorting/Chapter-4-Figures
    ../../bin/suiteRun.sh table4-1.rc

Figure 4-9: Median Sort in action on small array [MAKE]

    cd Code/Sorting/Longs
    make
    Take the 'figure4-9.dotty' file and use as input to the DOT
      program (freely available from www.graphviz.org). Make sure
      that you use version 2-16.1 or higher

Example 4-3: C implementation to partition ar[left,right] around a 
    given pivot element.
    
    cd Code/Sorting/PointerBased
    code exists within various implementations, such as medianMinSort.c
    
Figure 4-10: partition(0,15,9) returns 5 and updates A accordingly [MAKE]

    This figure is drawn by hand using information from debugging session:

      cd Code/Sorting/Longs

    code found in figure4-10.c can be executed within Figure4-10. The figure in
    the printed book (October 2008) is slightly different from the output of this
    code.
    
    
Example 4-4: selectKth recursive implementation in C
    
    cd Code/Sorting/PointerBased
    code found in selectKthRecursive.c
    
Example 4-5: Median Sort implementation in C

    cd Code/Sorting/PointerBased
    code found in medianSort.c
    
Table 4-2: Performance (in seconds) of Median Sort in the worst case [MAKE]

    cd Code/Sorting/Chapter-4-Figures
    ../../bin/suiteRun.sh table4-2.rc
    
    To generate the trend line, use the maple program table4-2.maple
    which can be found in Code/Maple/Chapter-4. Execute as follows:
    
      maple < table4-2.maple
    
    You can complete the computations as shown to derive the n*log n 
    equations listed in the text. Alternatively, load up the table 
    into Excel and compute the trend line from the Graph of the data.
    
Table 4-3: Performance (in seconds) of Median Sort in average case [MAKE]

    cd Code/Sorting/Chapter-4-Figures
    ../../bin/suiteRun.sh table4-3.rc
    
Example 4-6: Blum-Floyd-Pratt-Rivest-Tarjan implementation in C

    cd Code/Sorting/PointerBased/
    code found in selectKthWorstLinearFour.c
    
Figure 4-12: Sample Quicksort execution [MAKE]

    cd Code/Sorting/Longs
    make
    Take the 'figure4-12.dotty' file and use as input to the DOT
      program (freely available from www.graphviz.org). Make sure
      that you use version 2-16.1 or higher
        
Figure 4-13: A different Quicksort execution [MAKE]

    cd Code/Sorting/Longs
    make
    Take the 'figure4-13.dotty' file and use as input to the DOT
      program (freely available from www.graphviz.org). Make sure
      that you use version 2-16.1 or higher
        
Example 4-7: Quicksort implementation in C

    cd Code/Sorting/PointerBased
    code found in baseQsort.c
    
Example 4-8: Selection Sort implementation in C

    cd Code/Sorting/PointerBased
    code found in selectionSort.c

Figure 4-16: buildHeap operating on an initially sorted array [MAKE]

    cd Code/Sorting/Long
    ./Figure4-16
        
Example 4-9: Heap Sort implementation in C

    cd Code/Sorting/PointerBased
    code found in straight_HeapSort.c
    
    It is worth comparing the code (both readability and performance)
    with an optimized, non-recursive implementation of Heap Sort found 
    in heapSort.c in the same directory.
    
Table 4-4: Performance comparison of non-recursive variation (in seconds) [MAKE]

    cd Code/Sorting/Chapter-4-Figures
    ../../bin/suiteRun.sh table4-4.rc

Example 4-10: Counting Sort implementation in C

    cd Code/Sorting/Ints
    code found in countingSort.c
    
Example 4-11: Bucket Sort implementation in C

    cd Code/Sorting/PointerBased
    code found in bucketLinkedListSortPtr.c
    
    An alternative implementation using arrays that are redimensionalized
    as needed is found in the same directory inside bucketArraySortPtr.c
    
Example 4-12: hash and numBuckets functions for [0,1) range

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

Tables 4-7 through 4-16 are generated from a number of configuration files [MAKE]


    