import timeit
from adk.knapsack import record_best, Item

def trials():
    """
    Search small space to determine input set to knapsack that offers greatest
    difference between dynamic programming and approximate. Once computed, use
    these values in adk.book.chapter11.py 
    """
    # 83 250 4 2 457
    # 103 207 4 2 523
    a = 23
    b = 56
    c = 8
    d = 5
    
    maxBestTotal = 0
    for a in range(23, 113, 10):
        for b2 in range(1, 8):
            b = a*b2 + 1
            for c in [4, 8, 16, 32, 64]:
                for d in range(2, 7):
                    
                    diffTotal = 0
                    W = 10
                    numReps = 1
                    numTrials = 1
                    while W <= 65536:
                        
                        items = []
                        # Item (Value, Weight)
                        for i in range(a,b,c):
                            items.append(Item(i,i))
                            items.append(Item(d*i+1,d*i+1))
                                       
                        itemSet = 'items=[]\n'
                        for item in items:
                            itemSet = itemSet + 'items.append(Item(' + str(item.value) + ',' + str(item.weight) + '))\n'
                            #itemSet = itemSet + 'items.append(Item(' + str(W//4) + "," + str(W//4) + '))\n'
                        setup= '''
from adk.knapsack import knapsack_unbounded, knapsack_01, knapsack_approximate, Item, record_best
import random\n''' + itemSet + '''
'''
                        executeUnbound = '''
record_best (knapsack_unbounded(items,''' + str(W) + ''')[0])
'''
                        totalUnbound = min(timeit.Timer(executeUnbound, setup=setup).repeat(numReps,numTrials))
                        
                        executeApproximate = '''
record_best (knapsack_approximate(items,''' + str(W) + ''')[0])
'''
                        totalApproximate = min(timeit.Timer(executeApproximate, setup=setup).repeat(numReps,numTrials))
                             
                        #print (W, totalUnbound, totalApproximate, record_best())
                        best2 = record_best()
                        if len(best2) > 0:
                            diffTotal += best2[0] - best2[1]
                        W = W * 2 + 1
                    
                    if diffTotal > maxBestTotal:
                        print (a,b,c,d,diffTotal)
                        maxBestTotal = diffTotal
                    
if __name__ == '__main__':
    trials()
    