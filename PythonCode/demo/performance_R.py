
import random
import timeit
from adk.R import RTree
from adk.region import Region

def randomRectangle():
    """Random rectangle in unit square whose width/height is no greater than 0.5"""
    xmin = 0.5*random.random()
    ymin = 0.5*random.random()
    xmax = xmin + 0.5*random.random()
    ymax = ymin + 0.5*random.random()
    return Region(xmin, ymin, xmax, ymax)

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
from adk.R import RTree
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
    bt.contains(i)
    bt.contains(i+.01)
'''
            search_total += min(timeit.Timer(trials, setup=setup).repeat(5,numTrials))
            
        print ("%d %5.4f %5.4f %5.4f" % (n, 1000*add_total/numTrials, 1000*remove_total/numTrials, 1000*search_total/numTrials))

        n *= 2
        


def performanceR():
    """Demonstrate RTree stores all rectangles."""
    n = 1024
    while n <= 131072:
        rt = RTree()
        added = []
        for _ in range(n):
            new = randomRectangle()
            if rt.search(new) is None:
                rt.add(new)
                added.append(new)

        random.shuffle(added)
        
        for r in added:
            if rt.search(r) is None:
                print ("Cannot find:" + r)
                assert False
            
        print ("pass " + str(n))

        n *= 2


if __name__ == '__main__':
    performanceR()
    #performance()
        