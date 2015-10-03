def sequentialSearch(collection, t):
    """Demonstrate search for target t in collection."""
    for e in collection:
        if e == t:
            return True
    return False
  