
from adk.knapsack import knapsack_unbounded, knapsack_approximate, knapsack_01, Item
import random

def reportOne():
    """
    To see the generates matrices, modify the knapsack methods temporarily to
    print out the matrices.
    """
    items = [Item(4,4), Item(8,8), Item(9,9), Item(10,10)]
    W = 33
    
    print ("Knapsack Unbounded")
    w_un = knapsack_unbounded(items[:], W);
    print (w_un)
    
    print ("Knapsack Approximate")
    w_ap = knapsack_approximate(items[:], W);
    print (w_ap)
    
    print ("Knapsack 0/1")
    w_01 = knapsack_01(items[:], W);
    print (w_01)
    


def trials():
    """
    Locate knapsack items that has different results for :
       1) knapsack_unbounded
       2) knapsack_01
       3) knapsack_approximate
       
    Found this nice example:
    
    W = 33
    (33, [6, 0, 0, 1]) (32, [8, 0, 0, 0]) (31, [1, 1, 1, 1])

        (val= 4 , weight= 4
        (val= 8 , weight= 8
        (val= 10 , weight= 10
        (val= 9 , weight= 9

    """
    # 103 207 4 2 523
    
    for t in range(10000):
        items = []
        # Item (Value, Weight)
        for _ in range(5):
            i = random.randint(3,11)
            items.append(Item(i,i))
                       
        W = 33
    
        w_un = knapsack_unbounded(items[:], W);
        w_ap = knapsack_approximate(items[:], W);
        w_01 = knapsack_01(items[:], W);
                        
        if w_un[0] != w_ap[0] and w_ap[0] != w_01[0] and w_un[0] != w_01[0]:
            print (w_un, w_ap, w_01)
            for i in items:
                print ('(val=', i.value, ', weight=', i.weight);
            return
            
if __name__ == '__main__':
    # Switch to Trials if you want to locate a new collection with 
    # different results for the three algorithms.
    #trials()
    reportOne()
    
    