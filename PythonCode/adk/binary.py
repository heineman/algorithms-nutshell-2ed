"""
    Demonstrates Binary Search over ordered list
"""


def contains(collection, target):
    """Determine whether collection contains target."""
    return target in collection

def bs_contains(ordered, target):
    """Return index of target in ordered or -(p+1) where to insert it."""

    low = 0
    high = len(ordered)-1
    while low <= high:
        mid = (low + high) // 2
        if target < ordered[mid]:
            high = mid-1
        elif target > ordered[mid]:
            low = mid+1
        else:
            return mid

    return -(low + 1)

def bs_insert(ordered, target):
    """Inserts target into it proper location if not already present."""
    idx = bs_contains(ordered, target)
    if idx < 0: 
        ordered.insert(-(idx + 1), target)

