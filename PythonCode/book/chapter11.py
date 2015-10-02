import timeit
from adk.knapsack import record_best, Item

def trials():
    """Conduct timing trials for sample values of W."""
    # 103 207 4 2 523
    a = 103
    b = 207
    c = 4
    d = 2
    
    items = []
    # Item (Value, Weight)
    for i in range(a,b,c):
        items.append(Item(i,i))
        items.append(Item(d*i+1,d*i+1))
                   
    maxBestTotal = 0

    print ('W', 'KnapsackUnboundedTime', 'KnapsackApproximateTime', 'ActualAnswer', 'ApproximateAnswer')
    diffTotal = 0
    W = 10
    numReps = 1
    numTrials = 1
    while W <= 65536:
        itemSet = 'items=[]\n'
        for item in items:
            itemSet = itemSet + 'items.append(Item(' + str(item.value) + ',' + str(item.weight) + '))\n'
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
             
        print (W, '{0:.5f}'.format(totalUnbound), '{0:.5f}'.format(totalApproximate), record_best())
        W = W * 2 + 1
    
    if diffTotal > maxBestTotal:
        print (a,b,c,d,diffTotal)
        maxBestTotal = diffTotal
                    
if __name__ == '__main__':
    trials()
    