import random
from adk.avl import BinaryTree

"""
Generate numerous trees of size n and determine range of heights in the AVL trees. 
Not definitive, but indicative of the minimum & maximum height of an AVL tree given n nodes
"""
for n in range(1,511):
    minH = 999
    maxH = 0
    for _ in range(1000):
        bst = BinaryTree()
        for i in range(n):
            val = random.randint(1,100000)
            bst.add(val)
        
        if bst.root.height < minH:
            minH = bst.root.height
        if bst.root.height > maxH:
            maxH = bst.root.height
    
    print (n, minH, maxH)
    