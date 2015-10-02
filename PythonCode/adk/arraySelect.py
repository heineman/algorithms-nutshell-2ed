"""
    Provide O(n) selectKth functionality, an essential operation for a number
    of algorithms.
"""

def selectPivotIndex (ar, left, right, comparator):
    """Select pivot index for ar[left,right] inclusive using comparator."""
    midIndex = (left + right)//2
    lowIndex = left

    if comparator(ar[lowIndex], ar[midIndex]) >= 0:
        lowIndex = midIndex
        midIndex = left

    # when we get here, ar[lowIndex] < ar[midIndex]

    # select middle of [low,mid] and ar[right]
    if comparator(ar[right], ar[lowIndex]) <= 0:
        return lowIndex   # right .. low .. mid     so we return 
    elif comparator(ar[right], ar[midIndex]) <= 0:
        return right      # low .. right .. mid

    return midIndex       # reasonable default


def partition (ar, left, right, pivotIndex, comparator):
    """
    In linear time, group an array into two parts, those less than a
    certain value (left), and those greater than or equal to a certain value
    (right). left and right are inclusive.
    """
    pivot = ar[pivotIndex]

    # move pivot to the end of the array
    ar[right],ar[pivotIndex] = ar[pivotIndex],ar[right]
    store = left
    for idx in range(left, right):
        if comparator(ar[idx], pivot) <= 0:
            ar[idx],ar[store] = ar[store],ar[idx]
            store += 1

    # move pivot to its final place
    ar[right],ar[store] = ar[store],ar[right]
    return store

def selectKth (ar, k, left, right, comparator):
    """
    Select the kth value in an array (1 <= k <= right-left+1) through
    recursive partitioning. Note that ar is altered during the execution
    of this method. left must be <= right and external comparator is used.
    """
    while True:
        idx = selectPivotIndex(ar, left, right, comparator)
        pivotIndex = partition (ar, left, right, idx, comparator)

        if left+k-1 == pivotIndex:
            return ar[pivotIndex]

        # continue the loop, narrowing range as appropriate
        if left+k-1 < pivotIndex:
            # we are within the left-hand side of the pivot. k can stay the same
            right = pivotIndex - 1
        else:
            # we are within the right-hand side of the pivot. k must decrease by 
            # the size being removed.
            k -= (pivotIndex-left+1)
            left = pivotIndex + 1            
