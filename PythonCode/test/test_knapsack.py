import unittest

from adk.knapsack import Item, knapsack_unbounded, knapsack_01, knapsack_approximate

class TestKnapsackMethods(unittest.TestCase):

    def test_knapsack_01(self):
        # wikipedia example for _01 
        
        # Item (Value, Weight)
        i1 = Item(2,  1)
        i2 = Item(10, 4)
        i3 = Item(1,  1)
        i4 = Item(2,  2)
        i5 = Item(4, 12)
        
        self.assertEqual((15, [1,1,1,1,0]), knapsack_01([i1,i2,i3,i4,i5], 15))
                
    def test_knapsack_unbounded(self):
        # wikipedia example for _unbounded
        i1 = Item(2,  1)
        i2 = Item(10, 4)
        i3 = Item(1,  1)
        i4 = Item(2,  2)
        i5 = Item(4, 12)
        
        self.assertEqual((36,[3,3,0,0,0]), knapsack_unbounded([i1,i2,i3,i4,i5], 15))
        
    def test_knapsack_unbounded_for_approx(self):
        # http://math.stackexchange.com/questions/720001/

        # Item (Value, Weight)
        i1 = Item (90, 9)
        i2 = Item (19, 2)
        i3 = Item (1, 1)
        self.assertEqual((95,[0,5,0]), knapsack_unbounded([i1,i2,i3], 10))
        
        # approximation chooses this one
        self.assertEqual((91,[1,0,1]), knapsack_approximate([i1,i2,i3], 10))
    
    def test_other_example(self):
        # http://www.cs.princeton.edu/~wayne/cs423/lectures/approx-alg-4up.pdf
        
        # Item (Value, Weight)
        i1 = Item(1, 1)
        i2 = Item(6, 2)
        i3 = Item(18,5)
        i4 = Item(22,6)
        i5 = Item(28,7)
        
        self.assertEqual((40,[0,0,1,1,0]), knapsack_01([i1,i2,i3,i4,i5], 11))
