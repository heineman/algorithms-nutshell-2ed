
import random
import timeit
from adk.avl import BinaryTree

def performance():
    """Demonstrate execution performance."""
    n = 4
    numTrials = 100
    print ("n", "Add time", "Remove time", "Search time")
    while n <= 512:
        add_total = remove_total = search_total = 0
        
        for _ in range(numTrials):
            added = list(range(n))
            random.shuffle(added) 
            
        setup= '''
from adk.avl import BinaryTree
added = list(range(''' + str(n) + '''))
bt = BinaryTree()

'''    
        add_total += min(timeit.Timer('for i in added: bt.add(i)', setup=setup).repeat(5,numTrials))
           
        setup= '''
from adk.avl import BinaryTree
added = list(range(''' + str(n) + '''))
bt = BinaryTree()
for i in added:
    bt.add(i)

''' 
        remove_total += min(timeit.Timer('for i in added: bt.remove(i)', setup=setup).repeat(5,numTrials))
            
        trials = '''
for i in added:
    i in bt
    (i+.01) in bt
'''
        search_total += min(timeit.Timer(trials, setup=setup).repeat(5,numTrials))
            
        print ("%d %5.4f %5.4f %5.4f" % (n, 1000*add_total/numTrials, 1000*remove_total/numTrials, 1000*search_total/numTrials))

        n *= 2
        


def performanceAVL():
    """Demonstrate AVL properties always hold by building and deconstructing AVL trees from scratch."""
    n = 4
    
    while n <= 1024:

        bt = BinaryTree()
        added = []
        for i in range(n):
            new = i
            if not new in bt:
                bt.add(new)
                added.append(new)
                
                if not bt.root.assertAVLProperty():
                    print (bt)
                    assert False

        random.shuffle(added)
        
        for i in added:
            bt.remove(i)
            if (bt.root):
                if not bt.root.assertAVLProperty():
                    print (bt)
                    assert False
            assert i not in bt
            
        print ("pass " + str(n))

        n *= 2


if __name__ == '__main__':
    performanceAVL()
    performance()
        