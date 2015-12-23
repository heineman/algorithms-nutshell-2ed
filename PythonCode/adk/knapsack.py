"""

Unbounded knapsack: *(http://www.mathcs.emory.edu/~cheung/Courses/323/Syllabus/DynProg/knapsack2.html

"""
class Item:
    def __init__(self, value, weight):
        """Create item with given value and weight. """
        self.value = value
        self.weight = weight

    def __str__(self):
        return "(weight=" + str(self.weight) + ", value=" + str(self.value) + ")"

class ApproximateItem(Item):
    """
    Extends Item by storing the normalized value and the original position of the item
    before sorting.
    """
    def __init__(self, item, idx):
        Item.__init__(self, item.value, item.weight)
        self.normalizedValue = item.value/item.weight
        self.index = idx

# Not to be exported. This exists for timeit code in test_knapsack.
_best = []

def record_best(val = None):
    """Helper method for performance knapsack. Placed here so timeit code in test_knapsack will work."""
    global _best
    if val:
        _best.append(val)
    else:
        rc = _best
        _best = []
        return rc
    
def knapsack_approximate (items, W):
    """
    Compute approximation to knapsack problem using Dantzig approach.
    """
    approxItems = []
    n = len(items)
    for idx in range(n):
        approxItems.append(ApproximateItem(items[idx], idx))

    approxItems.sort (key=lambda x:x.normalizedValue, reverse=True)
    
    selections = [0] * n
    w = W
    total = 0
    for idx in range(n):
        item = approxItems[idx]
        
        if w == 0:
            break
        
        # find out how many fit
        numAdd = w // item.weight
        if numAdd > 0:
            selections[item.index] += numAdd
            w -= numAdd * item.weight
            total += numAdd * item.value

    return (total, selections)
    
def knapsack_unbounded (items, W):
    """
    Compute unbounded knapsack solution (any number of each item is available)
    for set of items with corresponding weights and values. Return total
    weight and selection of items.
    """
    n = len(items)
    progress = [0] * (W+1)
    progress[0] = -1
    m = [0] * (W + 1)
    for j in range(1, W+1):
        progress[j] = progress[j-1]
        best = m[j-1]
        for i in range(n):
            remaining = j - items[i].weight
            if remaining >= 0 and m[remaining] + items[i].value > best:
                best = m[remaining] + items[i].value
                progress[j] = i
            m[j] = best
            
    selections = [0] * n
    i = n
    w = W
    while w >= 0:
        choice = progress[w]
        if choice == -1:
            break
        selections[choice] += 1
        w -= items[progress[w]].weight
            
    # Uncomment this code if you want to see the computed m[] matrix
    #out = ''
    #for _ in m:
    #    out = out + "," + str(_)
    #print (out)
    return (m[W], selections)
    

def knapsack_01 (items, W):
    """
    Compute 0/1 knapsack solution (just one of each item is available)
    for set of items with corresponding weights and values. Return total weight
    and selection of items.
    """
    n = len(items)
    m = [None] * (n+1)
    for i in range(n+1):
        m[i] = [0] * (W+1)
    
    for i in range(1,n+1):
        for j in range(W+1):
            if items[i-1].weight <= j:
                valueWithItem = m[i-1][j-items[i-1].weight] + items[i-1].value
                m[i][j] = max(m[i-1][j], valueWithItem)
            else:
                m[i][j] = m[i-1][j]

    selections = [0] * n
    i = n
    w = W
    while i > 0 and w >= 0:
        if m[i][w] != m[i-1][w]:
            selections[i-1] = 1
            w -= items[i-1].weight
        i -= 1
    
    # Uncomment this code if you want to see the computed m[] matrix
    #out = ''
    #for i in range(n+1):
    #    for w in range(W+1):
    #        out = out + "," + str(m[i][w])
    #    out = out + "\n"
    #print (out)
    
    return (m[n][W], selections)
    

