import timeit
import random

from adk.region import Region
from adk.R import RTree

BUILD  = 1
SEARCH = 2
DELETE = 3

def singleTrial(n, items, m=2, M=4):
    """Conduct timing trials for sample values of m,M given n."""
        
    numReps = 1
    numTrials = 1
        
    itemSet = 'items=[]\n'
    for item in items:
        itemSet = itemSet + 'items.append(Region' + str(item) + ')\n'
            
    searchSet= '''
from adk.R import RTree
from adk.region import Region
''' + itemSet + '''
rt = RTree(m=''' + str(m) + ', M=' + str(M) + ')' + '''
for r in items:
    rt.add(r)
'''
    totalSearch = itemSet + '''            
for r in items:
    rt.search(r)
'''
    totalDelete = itemSet + '''
for r in items:
    rt.remove(r)
'''

    s = min(timeit.Timer(totalSearch, setup=searchSet).repeat(numReps,numTrials))
    d = min(timeit.Timer(totalDelete, setup=searchSet).repeat(numReps,numTrials))
    return (s,d)

def trials(n, items, tableType, label):
    """Conduct timing trials for sample values of m,M given n."""
        
    M = 4
    maxM = 12
    numReps = 1
    numTrials = 1
    print (label, "(", n, ")")
    line = ''
    for v in range(2,1+maxM//2):
        line = line + 'm=' + str(v) + '\t'
    print ('M\t' + line)
    while M <= maxM:
        result = {}
        for m in range(2, M//2+1):
            itemSet = 'items=[]\n'
            for item in items:
                itemSet = itemSet + 'items.append(Region' + str(item) + ')\n'
            
            if tableType == BUILD:
                buildSet = '''
from adk.R import RTree
from adk.region import Region
''' + itemSet 
        
                executeBuild = '''
from adk.R import RTree
from adk.region import Region
rt = RTree(m=''' + str(m) + ', M=' + str(M) + ')' + '''
for r in items:
    rt.add(r)
'''
                result[m] = min(timeit.Timer(executeBuild, setup=buildSet).repeat(numReps,numTrials))
            else:
                searchSet= '''
from adk.R import RTree
from adk.region import Region
''' + itemSet + '''
rt = RTree(m=''' + str(m) + ', M=' + str(M) + ')' + '''
for r in items:
    rt.add(r)
'''
                if tableType == SEARCH:
                    totalSearch = itemSet + '''            
for r in items:
    rt.search(r)
'''
                    result[m] = min(timeit.Timer(totalSearch, setup=searchSet).repeat(numReps,numTrials))
                elif tableType == DELETE:
                    totalDelete = itemSet + '''
for r in items:
    rt.remove(r)
'''
                    result[m] = min(timeit.Timer(totalDelete, setup=searchSet).repeat(numReps,numTrials))
                         
        line = ''
        for v in range(1,maxM):
            if v in result:
                s = "{:.2f}".format(result[v])
                line = line + s + '\t'
            else:
                line = line + '\t'
                
        print (M, line)
        M = M + 1
        
def conductTrial(label, items):
    samples = 128
    n = len(items)
    rt = RTree(m=2, M=4)
    for r in items:
        rt.add(r)
    
    box = rt.root.region
    width = box.x_max - box.x_min
    height = box.y_max - box.y_min
    
    # can compute density for any tree, samples at 128 random spots
    ct = 0
    for _ in range(samples):
        x = box.x_min + random.random()*width
        y = box.y_min + random.random()*height
        for triple in rt.range(Region(x,y,x,y)):
            assert(triple[2] == False)
            ct += 1
           
    print ("Density", n, "{:.2f}%".format(100*ct/(n*samples)), "intersect random point in ", str(box))
    
    trials(n, items, BUILD,  "Build")
    trials(n, items, SEARCH, "Search")
    trials(n, items, DELETE, "Delete")
            
            
def table(n):
    """Generate tables for book."""
    items = []
    for r in range(int(n**0.5)):
        for c in range(int(n**0.5)):
            items.append(Region(c*10, r*10, c*10+7, r*10+7))
            
    conductTrial("non intersecting rectangles", items)
    
    items = []
    for _ in range(n):
        items.append(Region(random.random(), random.random(), random.random(), random.random()))
     
    conductTrial("random rectangles in unit square", items)
            
if __name__ == '__main__':
    
    print ("Information for tables 10-4, 10-5 and 10-6")
    table(8100)
    
    print ("Information for table 10-7")
    n = 128
    while n <= 131072:
        items = []
        for _ in range(n):
            x = random.randint(1,10000)
            y = random.randint(1,10000)
            items.append(Region(x, y, x+10, y+10))
        
        sparseS, sparseD = singleTrial(n, items, m=2, M=4)  
        
        print (n, "{:.6f}".format(sparseS/n), "{:.6f}".format(sparseD/n))
        n *= 2
    