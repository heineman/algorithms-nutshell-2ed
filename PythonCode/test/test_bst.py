import unittest

from adk.bst import BinaryTree
import random

class TestBSTMethods(unittest.TestCase):

    def setUp(self):
        self.bst = BinaryTree()
        
    def tearDown(self):
        self.bst = None
        
    def test_basic(self):
        self.bst.add(10)
        self.assertTrue(10 in self.bst)
        
    def test_adding(self):
        for _ in range(1000):
            # make sure these are all even numbers
            n = random.randint(1,100000)
            if n % 2 == 1: 
                n += 1
            if not n in self.bst:
                self.bst.add(n)
                self.assertTrue(n in self.bst)        # n is there
                self.assertFalse((n+1) in self.bst)   # (n+1) is not since odd
           
    def test_degenerate(self):
        for _ in range(500):   # can't be too high otherwise exceed recursion depth
            if not _ in self.bst:
                self.bst.add(_)
                self.assertTrue(_ in self.bst)      
           

if __name__ == '__main__':
    unittest.main()    