"""
    Demonstrates Binary Search over ordered list
"""


def contains(collection, target):
    """Determine whether collection contains target."""
    return target in collection

def bs_contains(ordered, target):
    """Use binary search to return index position of target in collection."""

    low = 0
    high = len(ordered)-1
    while low <= high:
        mid = (low + high) // 2
        if target == ordered[mid]:
            return mid
        elif target < ordered[mid]:
            high = mid-1
        else:
            low = mid+1

    return -(low + 1)

def bs_insert(ordered, target):
    """Inserts target into it proper location if not already present."""
    idx = bs_contains(ordered, target)
    if idx < 0: 
        ordered.insert(-(idx + 1), target)

